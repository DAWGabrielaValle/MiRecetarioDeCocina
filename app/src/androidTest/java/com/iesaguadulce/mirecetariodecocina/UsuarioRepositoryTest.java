package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.iesaguadulce.mirecetariodecocina.room.Rol;
import com.iesaguadulce.mirecetariodecocina.room.RolRepositorio;
import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRepositorio;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Prueba de integración para {@link UsuarioRepositorio}.
 * Comprobamos que el repositorio coordine correctamente las operaciones asíncronas (usando su Executor)
 * y que los cambios realizados en el repositorio se reflejen en el LiveData.
 * <p>
 * Esta prueba comprueba los siguientes aspectos:
 * 1. Entorno real: Se ejecuta en un dispositivo o emulador, usando el contexto real de la
 *    aplicación ({@link ApplicationProvider}).
 * 2. Multihilo: El repositorio utiliza un Executor para coordinar las operaciones asíncronas. Esta
 *    prueba confirma que, aunque la escritura sea en un hilo secundario, el LiveData
 *    notifica correctamente al hilo principal cuando el dato está listo.
 * 3. Flujo completo: Se prueba la cadena completa:
 *    Repositorio -> Executor -> DAO -> SQLite -> LiveData
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuarioRepositorio
 */
@RunWith(AndroidJUnit4.class)
public class UsuarioRepositoryTest {

    /**  */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /** Repositorio de usuarios y roles para las pruebas. */
    private UsuarioRepositorio repositorio;
    private RolRepositorio  rolRepositorio;

    /**
     * Configura los repositorios antes de cada test.
     */
    @Before
    public void setup() {
        Application application = ApplicationProvider.getApplicationContext();
        repositorio = new UsuarioRepositorio(application);
        rolRepositorio = new RolRepositorio(application);
    }

    /**
     * Método auxiliar para insertar un rol y obtener su identificador de forma síncrona para el test.
     *
     * @param nombreRol - String - Nombre del rol a insertar.
     * @return - int - Identificador del rol insertado.
     * @throws InterruptedException - Excepción lanzada si el test tarda demasiado.
     */
    private int getOrCreateRolId(String nombreRol) throws InterruptedException {
        rolRepositorio.insert(new Rol(nombreRol));

        final CountDownLatch latch = new CountDownLatch(1);
        final int[] result = new int[1];

        // Obtenemos el LiveData y lo observamos hasta obtener el identificador del rol
        LiveData<Rol> rolLiveData = rolRepositorio.getRolbyRol(nombreRol);
        Observer<Rol> observer = new Observer<Rol>() {
            @Override
            public void onChanged(Rol rol) {
                if (rol != null) {
                    result[0] = rol.getIdRol();
                    latch.countDown();
                    rolLiveData.removeObserver(this); // Limpiamos el observador
                }
            }
        };

        rolLiveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        return result[0];
    }

    /**
     * Verifica que un usuario se inserte correctamente en el repositorio.
     *
     * @throws InterruptedException - Excepción lanzada si el test tarda demasiado.
     */
    @Test
    public void testInsertAndGetUsuario() throws InterruptedException {
        // Obtenemos el ID del rol Chef
        int idRol = getOrCreateRolId("Chef");

        Usuario nuevoUsuario = new Usuario("integ_test", idRol, "Integracion", "pass", "2026-01-01", null);
        repositorio.insert(nuevoUsuario);

        // CountDownLatch garantiza que los test esperen a que las tareas en segundo plano del repositorio terminen.
        final CountDownLatch latch = new CountDownLatch(1);
        repositorio.getUsuario("integ_test").observeForever(new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    assertEquals("Integracion", usuario.getNombre());
                    latch.countDown();
                }
            }
        });

        assertTrue("Timeout: El usuario no se insertó a tiempo", latch.await(2, TimeUnit.SECONDS));
    }

    /**
     * Verifica que los cambios realizados a través del Executor del repositorio se reflejen en el LiveData.
     *
     * @throws InterruptedException - Excepción lanzada si el test tarda demasiado.
     */
    @Test
    public void testUpdateUsuario() throws InterruptedException {
        // Obtenemos el ID del rol Chef
        int idRol = getOrCreateRolId("Chef");

        // 1. Insertamos usuario inicial
        Usuario usuario = new Usuario("update_test", idRol, "Original", "pass", "2026-01-01", null);
        repositorio.insert(usuario);

        // 2. Modificamos el usuario
        Usuario modificado = new Usuario("update_test", idRol, "Modificado", "pass", "2026-01-01", null);
        repositorio.update(modificado);

        // 3. Verificamos que el LiveData acaba recibiendo el nombre modificado
        // CountDownLatch garantiza que los test esperen a que las tareas en segundo plano del repositorio terminen.
        final CountDownLatch latch = new CountDownLatch(1);
        repositorio.getUsuario("update_test").observeForever(new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                if (u != null && u.getNombre().equals("Modificado")) {
                    latch.countDown();
                }
            }
        });

        assertTrue("Timeout: El usuario no se actualizó", latch.await(2, TimeUnit.SECONDS));
    }

    /**
     * Confirma que tras eliminar un usuario, el LiveData emite 'null' a través de su flujo de datos
     * para indicar que el usuario no existe.
     *
     * @throws InterruptedException - Excepción lanzada si el test tarda demasiado.
     */
    @Test
    public void testDeleteUsuario() throws InterruptedException {
        // Obtenemos el ID del rol Chef
        int idRol = getOrCreateRolId("Chef");

        // 1. Insertamos un usuario para luego borrarlo
        Usuario usuario = new Usuario("delete_test", idRol, "ABorrar", "pass", "2026-01-01", null);
        repositorio.insert(usuario);

        // 2. Esperamos a que el LiveData detecte que el usuario existe primero (opcional pero seguro)
        final CountDownLatch insertLatch = new CountDownLatch(1);
        Observer<Usuario> insertObserver = new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                if (u != null) insertLatch.countDown();
            }
        };
        repositorio.getUsuario("delete_test").observeForever(insertObserver);
        insertLatch.await(2, TimeUnit.SECONDS);

        // 3. Ejecutamos el borrado
        repositorio.delete(usuario);

        // 4. Verificamos que el LiveData emite 'null' (usuario ya no existe)
        // CountDownLatch garantiza que los test esperen a que las tareas en segundo plano del repositorio terminen.
        final CountDownLatch deleteLatch = new CountDownLatch(1);
        repositorio.getUsuario("delete_test").observeForever(new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                if (u == null) {
                    deleteLatch.countDown();
                }
            }
        });

        assertTrue("Timeout: El usuario no se eliminó", deleteLatch.await(2, TimeUnit.SECONDS));
    }
}
