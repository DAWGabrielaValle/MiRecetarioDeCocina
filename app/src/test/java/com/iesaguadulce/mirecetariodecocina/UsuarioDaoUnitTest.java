package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.iesaguadulce.mirecetariodecocina.room.RecetarioCocinaDatabase;
import com.iesaguadulce.mirecetariodecocina.room.Rol;
import com.iesaguadulce.mirecetariodecocina.room.RolDao;
import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Pruebas unitarias para el objeto de acceso a datos (DAO) de la entidad {@link Usuario}.
 * <p>
 * Estas pruebas se ejecutan en una base de datos en memoria para garantizar que cada test
 * sea independiente y no afecte a los datos reales de la aplicación. Se verifican las
 * operaciones básicas de inserción, consulta, actualización y borrado (CRUD).
 * </p>
 */
@RunWith(AndroidJUnit4.class)
public class UsuarioDaoUnitTest {

    /** Instancia de la base de datos en memoria para pruebas. */
    private RecetarioCocinaDatabase db;

    /** DAO de usuarios para realizar las operaciones de prueba. */
    private UsuarioDao usuarioDao;

    /** DAO de roles para realizar las operaciones de prueba. */
    private RolDao rolDao;

    /**
     * Configuración previa a cada prueba.
     * <p>
     * Crea una base de datos temporal en memoria y se obtienen los DAO necesarios.
     * Se permite la ejecución de consultas en el hilo principal para facilitar el testeo.
     * </p>
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RecetarioCocinaDatabase.class)
                .allowMainThreadQueries()
                .build();
        usuarioDao = db.getUsuarioDao();
        rolDao = db.getRolDao();
    }

    /**
     * Cierra la base de datos tras la ejecución de cada prueba para liberar recursos.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Verifica que un usuario se inserte correctamente y se pueda recuperar por su nombre.
     *
     * @throws Exception - Si ocurre un error durante la prueba.
     */
    @Test
    public void insertarYObtenerUsuario() throws Exception {
        // 1. Insertamos rol
        long idRol = rolDao.insert(new Rol("Administrador"));
        Usuario usuario = new Usuario("test", (int) idRol, "Usuario Pruebas", "pruebas", "2026-01-01", null);

        //2. Insertamos usuario
        usuarioDao.insert(usuario);

        // 3. Obtenemos el usuario
        Usuario result = usuarioDao.getUsuarioSync("test");

        // 4. Verificamos que el usuario existe
        assertNotNull("El usuario debería haber sido insertado", result);
        assertEquals("Usuario Pruebas", result.getNombre());
        assertEquals("test", result.getIdUsuario());
    }

    /**
     * Verifica que los datos de un usuario existente se actualicen correctamente.
     *
     * @throws Exception - Si ocurre un error durante la prueba.
     */
    @Test
    public void modificarUsuario() throws Exception {
        // 1. Insertamos usuario inicial
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_mod", (int) idRol, "Nombre Original", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);

        // 2. Modificamos el nombre
        Usuario usuarioAModificar = usuarioDao.getUsuarioSync("user_mod");
        Usuario usuarioModificado = new Usuario(
                usuarioAModificar.getIdUsuario(),
                usuarioAModificar.getIdRol(),
                "Nombre Modificado",
                usuarioAModificar.getPswd(),
                usuarioAModificar.getFechaAlta(),
                usuarioAModificar.getFechaFin()
        );
        usuarioDao.update(usuarioModificado);

        // 3. Verificamos el cambio
        Usuario result = usuarioDao.getUsuarioSync("user_mod");
        assertEquals("Nombre Modificado", result.getNombre());
    }

    /**
     * Verifica que un usuario se elimine correctamente de la base de datos.
     *
     * @throws Exception - Si ocurre un error durante la prueba.
     */
    @Test
    public void borrarUsuario() throws Exception {
        // 1. Insertamos usuario
        long idRol = rolDao.insert(new Rol("Usuario"));
        Usuario usuario = new Usuario("user_del", (int) idRol, "A Borrar", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);

        // 2. Comprobamos que existe antes de borrar
        assertNotNull(usuarioDao.getUsuarioSync("user_del"));

        // 3. Borramos
        usuarioDao.delete(usuario);

        // 4. Verificamos que ya no existe
        Usuario result = usuarioDao.getUsuarioSync("user_del");
        assertNull("El usuario debería haber sido borrado", result);
    }
}
