package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.iesaguadulce.mirecetariodecocina.room.Menu;
import com.iesaguadulce.mirecetariodecocina.room.MenuDao;
import com.iesaguadulce.mirecetariodecocina.room.Menu_Rec;
import com.iesaguadulce.mirecetariodecocina.room.Menu_RecDao;
import com.iesaguadulce.mirecetariodecocina.room.Receta;
import com.iesaguadulce.mirecetariodecocina.room.RecetaDao;
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
 * Pruebas unitarias para el objeto de acceso a datos (DAO) de la entidad {@link Menu}.
 * <p>
 * Estas pruebas se ejecutan en una base de datos en memoria para garantizar que cada test
 * sea independiente y no afecte a los datos reales de la aplicación. Se verifican las
 * operaciones básicas de inserción, consulta, actualización y borrado (CRUD).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenuDao
 */
@RunWith(AndroidJUnit4.class)
public class MenuDaoUnitTest {

    /** Instancia de la base de datos en memoria para pruebas. */
    private RecetarioCocinaDatabase db;

    /** DAO de menus para realizar las operaciones de prueba. */
    private MenuDao menuDao;

    /** DAO de roles para realizar las operaciones de prueba. */
    private RolDao rolDao;

    /** DAO de usuarios para realizar las operaciones de prueba. */
    private UsuarioDao usuarioDao;

    /** DAO de recetas para realizar las operaciones de prueba. */
    private RecetaDao recetaDao;

    /** DAO de Menu_Rec para realizar las operaciones de prueba. */
    private Menu_RecDao menuRecDao;

    /**
     * Configuración previa a cada prueba.
     * <p>
     * Crea una base de datos temporal en memoria y se obtienen los DAO necesarios. Se permite
     * la ejecución de consultas en el hilo principal para facilitar el testeo.
     * </p>
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RecetarioCocinaDatabase.class)
                .allowMainThreadQueries()
                .build();
        menuDao = db.getMenuDao();
        rolDao = db.getRolDao();
        usuarioDao = db.getUsuarioDao();
        recetaDao = db.getRecetaDao();
        menuRecDao = db.getMenuRecDao();
    }

    /**
     * Cierra la base de datos tras la ejecución de cada prueba para liberar recursos.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Verifica que un menú con una receta se inserte correctamente y se pueda recuperar por su nombre.
     * <p>
     * Se comprueba que el menú recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que la receta vinculada al menú también se encuentre en la base de datos.
     * Se comprueba que la receta recuperada no sea nula y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * </p>
     */
    @Test
    public void obtenerMenuConRecetas() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Menú
        Menu menu = new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida",
                usuarioResult.getIdUsuario());
        long idMenu = menuDao.insert(menu);

        // 3. Insertamos una receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);

        // 4. Vinculamos receta al menú (Tabla intermedia Menu_Rec)
        Menu_Rec vinculacion = new Menu_Rec((int)idMenu, (int)idReceta);
        menuRecDao.insert(vinculacion);

        // 5. Verificaciones
        Menu resultMenu = menuDao.getMenuByIdSync((int) idMenu);
        assertNotNull("El menú debería existir", resultMenu);
        assertEquals("Menu de prueba", resultMenu.getNombre());

        Menu_Rec resultMenuRec = menuRecDao.getMenuRecByIdSync((int)idMenu, (int)idReceta);
        assertNotNull("El menú vinculado a la receta debería existir", resultMenuRec);

        Receta resultReceta = recetaDao.getRecetaByIdSync((int) idReceta);
        assertNotNull("La receta debería existir", resultReceta);
        assertEquals("Arroz con Pollo", resultReceta.getNombre());
    }

    /**
     * Verifica que los datos de un menú existente se actualicen correctamente.
     * <p>
     * Se inserta un menú con una receta inicial, se modifica su nombre manteniendo el mismo identificador
     * y se comprueba que la base de datos refleja el cambio tras la actualización.
     * Se comprueba que la receta vinculada al menú también se encuentre en la base de datos.
     * Se comprueba que la receta recuperada no sea nula y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * </p>
     */
    @Test
    public void modificarMenu() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Menú
        Menu menu = new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida",
                usuarioResult.getIdUsuario());
        long idMenu = menuDao.insert(menu);

        // 3. Insertamos una receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);

        // 4. Vinculamos receta al menú (Tabla intermedia Menu_Rec)
        Menu_Rec vinculacion = new Menu_Rec((int)idMenu, (int)idReceta);
        menuRecDao.insert(vinculacion);

        // 5. Modificamos el menú
        Menu menuAModificar = menuDao.getMenuByIdSync((int) idMenu);
        Menu menuModificado = new Menu(
                "Menu de prueba Modificado",
                menuAModificar.getDescripcion(),
                menuAModificar.getTipo(),
                menuAModificar.getEtiqueta(),
                menuAModificar.getIdUsuario()
        );
        menuModificado.setIdMenu((int) idMenu);
        menuDao.update(menuModificado);

        // 6. Verificaciones
        Menu resultMenu = menuDao.getMenuByIdSync((int) idMenu);
        assertEquals("Menu de prueba Modificado", resultMenu.getNombre());

        Menu_Rec resultMenuRec = menuRecDao.getMenuRecByIdSync((int)idMenu, (int)idReceta);
        assertNotNull("El menú vinculado a la receta debería existir", resultMenuRec);

        Receta resultReceta = recetaDao.getRecetaByIdSync((int) idReceta);
        assertNotNull("La receta debería existir", resultReceta);
        assertEquals("Arroz con Pollo", resultReceta.getNombre());
    }

    /**
     * Verifica que un menú con una receta se elimine correctamente de la base de datos.
     * <p>
     * Se inserta un menú con una receta, se procede a su eliminación y se confirma que,
     * al intentar recuperarlo de nuevo, el resultado obtenido es {@code null}.
     * Comprobamos que la receta vinculada al menú también se haya borrado.
     * </p>
     */
    @Test
    public void borrarMenu() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Menú
        Menu menu = new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida",
                usuarioResult.getIdUsuario());
        long idMenu = menuDao.insert(menu);

        // 3. Insertamos una receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);

        // 4. Vinculamos receta al menú (Tabla intermedia Menu_Rec)
        Menu_Rec vinculacion = new Menu_Rec((int) idMenu, (int) idReceta);
        menuRecDao.insert(vinculacion);

        // 5. Comprobamos que existe antes de borrar
        assertNotNull(menuDao.getMenuByIdSync((int) idMenu));
        assertNotNull(menuRecDao.getMenuRecByIdSync((int) idMenu, (int) idReceta));
        assertNotNull(recetaDao.getRecetaByIdSync((int) idReceta));

        // 6. Borramos
        menuRecDao.delete(menuRecDao.getMenuRecByIdSync((int) idMenu, (int) idReceta));
        menuDao.delete(menuDao.getMenuByIdSync((int) idMenu));

        // 7. Verificamos que ya no existe
        Menu_Rec resultMenuRec = menuRecDao.getMenuRecByIdSync((int) idMenu, (int) idReceta);
        assertNull("El menú vinculado a la receta debería haber sido borrado", resultMenuRec);

        Menu resultMenu = menuDao.getMenuByIdSync((int) idMenu);
        assertNull("El menú debería haber sido borrado", resultMenu);
    }

}
