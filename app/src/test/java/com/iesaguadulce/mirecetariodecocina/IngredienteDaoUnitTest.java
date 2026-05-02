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
import com.iesaguadulce.mirecetariodecocina.room.RecetarioCocinaDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Pruebas unitarias para el objeto de acceso a datos (DAO) de la entidad {@link Ingrediente}.
 * <p>
 * Estas pruebas se ejecutan en una base de datos en memoria para garantizar que cada test
 * sea independiente y no afecte a los datos reales de la aplicación. Se verifican las
 * operaciones básicas de inserción, consulta, actualización y borrado (CRUD).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredienteDao
 * @see Ingrediente
 */
@RunWith(AndroidJUnit4.class)
public class IngredienteDaoUnitTest {
    
    /** Instancia de la base de datos en memoria para pruebas. */
    private RecetarioCocinaDatabase db;

    /** DAO de ingredientes para realizar las operaciones de prueba. */
    private IngredienteDao ingredienteDao;

    /**
     * Configuración previa a cada prueba.
     * <p>
     * Crea una base de datos temporal en memoria y obtiene el DAO necesario.
     * Se permite la ejecución de consultas en el hilo principal para facilitar el testeo.
     * </p>
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RecetarioCocinaDatabase.class)
                .allowMainThreadQueries()
                .build();
        ingredienteDao = db.getIngredienteDao();
    }

    /**
     * Cierra la base de datos tras la ejecución de cada prueba para liberar recursos.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Verifica que un ingrediente se inserte correctamente y se pueda recuperar por su nombre.
     * <p>
     * Se comprueba que el objeto recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * </p>
     */
    @Test
    public void insertarYObtenerIngrediente() {
        // 1. Insertamos ingrediente
        ingredienteDao.insert(new Ingrediente("Ingrediente de prueba", "Unidad de prueba",
                "outline_image_24", "Familia de prueba", "Etiqueta de prueba"));

        // 2. Obtenemos el ingrediente
        Ingrediente result = ingredienteDao.getIngredienteByNombre("Ingrediente de prueba");

        // 3. Verificamos que el ingrediente existe
        assertNotNull("El ingrediente debería haber sido insertado", result);
        assertEquals("Ingrediente de prueba", result.getNombre());
        assertEquals("Unidad de prueba", result.getDescripcion());
    }

    /**
     * Verifica que los datos de un ingrediente existente se actualicen correctamente.
     * <p>
     * Se inserta un ingrediente inicial, se modifica su nombre manteniendo el mismo identificador
     * y se comprueba que la base de datos refleja el cambio tras la actualización.
     * </p>
     */
    @Test
    public void modificarIngrediente() {
        // 1. Insertamos ingrediente inicial
        long idIngrediente = ingredienteDao.insert(new Ingrediente("Ingrediente de prueba", "Unidad de prueba",
                "outline_image_24", "Familia de prueba", "Etiqueta de prueba"));

        // 2. Modificamos el nombre
        Ingrediente ingredienteAModificar = ingredienteDao.getIngredienteByIdSync((int) idIngrediente);
        Ingrediente ingredienteModificado = new Ingrediente(
                "Ingrediente Modificado",
                ingredienteAModificar.getDescripcion(),
                ingredienteAModificar.getImagen(),
                ingredienteAModificar.getFamilia(),
                ingredienteAModificar.getEtiqueta()
        );
        ingredienteModificado.setIdIngrediente((int) idIngrediente);
        ingredienteDao.update(ingredienteModificado);

        // 3. Verificamos el cambio
        Ingrediente result = ingredienteDao.getIngredienteByIdSync((int) idIngrediente);
        assertEquals("Ingrediente Modificado", result.getNombre());
    }

    /**
     * Verifica que un ingrediente se elimine correctamente de la base de datos.
     * <p>
     * Se inserta un ingrediente, se procede a su eliminación y se confirma que,
     * al intentar recuperarlo de nuevo, el resultado obtenido es {@code null}.
     * </p>
     */
    @Test
    public void borrarIngrediente() {
        // 1. Insertamos
        long idIngrediente = ingredienteDao.insert(new Ingrediente("Ingrediente de prueba", "Unidad de prueba",
                "outline_image_24", "Familia de prueba", "Etiqueta de prueba"));

        // 2. Comprobamos que existe antes de borrar
        assertNotNull(ingredienteDao.getIngredienteByIdSync((int) idIngrediente));

        // 3. Borramos
        ingredienteDao.delete(ingredienteDao.getIngredienteByIdSync((int) idIngrediente));

        // 4. Verificamos que ya no existe
        Ingrediente result = ingredienteDao.getIngredienteByIdSync((int) idIngrediente);
        assertNull("El ingrediente debería haber sido borrado", result);
    }

}
