package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;
import com.iesaguadulce.mirecetariodecocina.room.IngredienteDao;
import com.iesaguadulce.mirecetariodecocina.room.Receta;
import com.iesaguadulce.mirecetariodecocina.room.RecetaDao;
import com.iesaguadulce.mirecetariodecocina.room.Receta_Ing;
import com.iesaguadulce.mirecetariodecocina.room.Receta_IngDao;
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
 * Pruebas unitarias para el objeto de acceso a datos (DAO) de la entidad {@link Receta}.
 * <p>
 * Estas pruebas se ejecutan en una base de datos en memoria para garantizar que cada test
 * sea independiente y no afecte a los datos reales de la aplicación. Se verifican las
 * operaciones básicas de inserción, consulta, actualización y borrado (CRUD).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetaDao
 */
@RunWith(AndroidJUnit4.class)
public class RecetaDaoUnitTest {

    /** Instancia de la base de datos en memoria para pruebas. */
    private RecetarioCocinaDatabase db;

    /** DAO de recetas para realizar las operaciones de prueba. */
    private RecetaDao recetaDao;

    /** DAO de usuarios para realizar las operaciones de prueba. */
    private UsuarioDao usuarioDao;

    /** DAO de roles para realizar las operaciones de prueba. */
    private RolDao rolDao;

    /** DAO de ingredientes para realizar las operaciones de prueba. */
    private IngredienteDao ingredienteDao;

    /** DAO de receta_ing para realizar las operaciones de prueba. */
    private Receta_IngDao recetaIngDao;

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
        recetaDao = db.getRecetaDao();
        usuarioDao = db.getUsuarioDao();
        rolDao = db.getRolDao();
        ingredienteDao = db.getIngredienteDao();
        recetaIngDao = db.getRecetaIngDao();
    }

    /**
     * Cierra la base de datos tras la ejecución de cada prueba para liberar recursos.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Verifica que una receta con un ingrediente se inserte correctamente y se pueda recuperar por su nombre.
     * <p>
     * Se comprueba que la receta recuperada no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el ingrediente vinculado a la receta también se encuentre en la base de datos.
     * Se comprueba que el ingrediente recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * </p>
     */
    @Test
    public void insertarYObtenerReceta() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");
        
        // 2. Insertamos la receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);
        
        // 3. Insertamos un ingrediente
        Ingrediente ingrediente = new Ingrediente("Pollo", "Pollo troceado", "outline_image_24", "Cárnicos", "Proteína");
        long idIngrediente = ingredienteDao.insert(ingrediente);
        
        // 4. Vinculamos ingrediente a la receta (Tabla intermedia Receta_Ing)
        Receta_Ing vinculacion = new Receta_Ing((int)idReceta, (int)idIngrediente, 500, "gramos");
        recetaIngDao.insert(vinculacion);

        // 5. Verificaciones
        Receta resultReceta = recetaDao.getRecetaByNombre("Arroz con Pollo");
        assertNotNull("La receta debería existir", resultReceta);
        assertEquals("Arroz con Pollo", resultReceta.getNombre());

        Receta_Ing resultRecetaIng = recetaIngDao.getRecetaIngByIdSync((int)idReceta, (int)idIngrediente);
        assertNotNull("El ingrediente vinculado a la receta debería existir", resultRecetaIng);
        assertEquals(500, resultRecetaIng.getCantidad());
        
        Ingrediente resultIng = ingredienteDao.getIngredienteByIdSync((int)idIngrediente);
        assertNotNull("El ingrediente debería existir", resultIng);
        assertEquals("Pollo", resultIng.getNombre());
    }

    /**
     * Verifica que los datos de una receta existente se actualicen correctamente.
     * <p>
     * Se inserta una receta con un ingrediente inicial, se modifica su nombre manteniendo el mismo identificador
     * y se comprueba que la base de datos refleja el cambio tras la actualización.
     * Se comprueba que el ingrediente vinculado a la receta también se encuentre en la base de datos.
     * Se comprueba que el ingrediente recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * </p>
     */
    @Test
    public void modificarReceta() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos la receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);

        // 3. Insertamos un ingrediente
        Ingrediente ingrediente = new Ingrediente("Pollo", "Pollo troceado", "outline_image_24", "Cárnicos", "Proteína");
        long idIngrediente = ingredienteDao.insert(ingrediente);

        // 4. Vinculamos ingrediente a la receta (Tabla intermedia Receta_Ing)
        Receta_Ing vinculacion = new Receta_Ing((int)idReceta, (int)idIngrediente, 500, "gramos");
        recetaIngDao.insert(vinculacion);

        // 5. Modificamos la receta
        Receta recetaAModificar = recetaDao.getRecetaByIdSync((int) idReceta);
        Receta recetaModificada = new Receta(
                "Arroz con Pollo Modificado",
                recetaAModificar.getDescripcion(),
                recetaAModificar.getImagen(),
                recetaAModificar.getTiempoPrep(),
                recetaAModificar.getFamilia(),
                recetaAModificar.getEtiqueta(),
                recetaAModificar.getModoPrep(),
                recetaAModificar.getIdUsuario()
        );
        recetaModificada.setIdReceta((int) idReceta);
        recetaDao.update(recetaModificada);

        // 6. Modificamos la cantidad del ingrediente vinculado a la receta
        Receta_Ing recetaIngAModificar = recetaIngDao.getRecetaIngByIdSync((int) idReceta, (int) idIngrediente);
        Receta_Ing recetaIngModificada = new Receta_Ing(
                recetaIngAModificar.getIdReceta(),
                recetaIngAModificar.getIdIngrediente(),
                1000,
                recetaIngAModificar.getUnidad()
        );
        recetaIngDao.update(recetaIngModificada);

        // 7. Verificaciones
        Receta resultReceta = recetaDao.getRecetaByIdSync((int) idReceta);
        assertEquals("Arroz con Pollo Modificado", resultReceta.getNombre());

        Receta_Ing resultIng = recetaIngDao.getRecetaIngByIdSync((int) idReceta, (int) idIngrediente);
        assertEquals(1000, resultIng.getCantidad());
    }

    /**
     * Verifica que una receta con un ingrediente se elimine correctamente de la base de datos.
     * <p>
     * Se inserta una receta con un ingrediente, se procede a su eliminación y se confirma que,
     * al intentar recuperarlo de nuevo, el resultado obtenido es {@code null}.
     * Comprobamos que el ingrediente vinculado a la receta también se haya borrado.
     * </p>
     */
    @Test
    public void borrarReceta() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos la receta
        Receta receta = new Receta("Arroz con Pollo", "Receta tradicional",
                "outline_image_24", 45, "Arroces",
                "Comida", "Pasos de preparación...", usuarioResult.getIdUsuario());
        long idReceta = recetaDao.insert(receta);

        // 3. Insertamos un ingrediente
        Ingrediente ingrediente = new Ingrediente("Pollo", "Pollo troceado", "outline_image_24", "Cárnicos", "Proteína");
        long idIngrediente = ingredienteDao.insert(ingrediente);

        // 4. Vinculamos ingrediente a la receta (Tabla intermedia Receta_Ing)
        Receta_Ing vinculacion = new Receta_Ing((int) idReceta, (int) idIngrediente, 500, "gramos");
        recetaIngDao.insert(vinculacion);

        // 5. Comprobamos que existe antes de borrar
        assertNotNull(recetaDao.getRecetaByIdSync((int) idReceta));
        assertNotNull(ingredienteDao.getIngredienteByIdSync((int) idIngrediente));
        assertNotNull(recetaIngDao.getRecetaIngByIdSync((int) idReceta, (int) idIngrediente));

        // 6. Borramos
        recetaIngDao.delete(recetaIngDao.getRecetaIngByIdSync((int) idReceta, (int) idIngrediente));
        recetaDao.delete(recetaDao.getRecetaByIdSync((int) idReceta));

        // 7. Verificamos que ya no existe
        Receta_Ing resultRecetaIng = recetaIngDao.getRecetaIngByIdSync((int) idReceta, (int) idIngrediente);
        assertNull("El ingrediente vinculado a la receta debería haber sido borrado", resultRecetaIng);
        Receta resultReceta = recetaDao.getRecetaByIdSync((int) idReceta);
        assertNull("La receta debería haber sido borrada", resultReceta);
    }

}
