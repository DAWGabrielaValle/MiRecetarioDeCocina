package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Receta} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class RecetaRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Receta} en la base de datos local.
     */
    private RecetaDao recetaDao;

    /**
     * Interfaz de acceso a datos (DAO) para la relación entre {@link Receta} y {@link Ingrediente} en la base de datos local
     */
    private Receta_IngDao recetaIngredienteDao;

    /**
     * Ejecutor para operaciones de base de datos en un hilo separado.
     */
    private Executor executor;

    /**
     * Constructor
     * Crea una instancia del repositorio con los DAO y el executor necesarios.
     *
     * @param application - {@link Application} - La aplicación que utiliza el repositorio.
     */
    public RecetaRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        recetaDao = database.getRecetaDao();
        recetaIngredienteDao = database.getRecetaIngDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros de la entidad {@link Receta} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Receta}>> - Objeto {@link LiveData} que contiene una lista de registros de recetas.
     */
    public LiveData<List<Receta>> getAllRecetas() {
        return recetaDao.getAll();
    }

    /**
     * Obtiene un registro de la entidad {@link Receta} por su identificador.
     *
     * @param id - int - Identificador del registro de la receta.
     * @return - LiveData<{@link Receta}> - Objeto {@link LiveData} que contiene el registro de la receta.
     */
    public LiveData<Receta> getReceta(int id) {
        return recetaDao.getReceta(id);
    }

    /**
     * Obtiene un registro de la entidad {@link Receta} por su nombre.
     *
     * @param nombre - String - Nombre del registro de la receta.
     * @return - LiveData<{@link Receta}> - Objeto {@link LiveData} que contiene el registro de la receta.
     */
    public LiveData<Receta> getRecetaByNombre(String nombre) {
        return recetaDao.getRecetaByName(nombre);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a insertar.
     * @return - LiveData<{@link Receta}> - Objeto {@link LiveData} que contiene el registro de la receta.
     */
    public LiveData<Receta> insertarReceta(Receta receta) {
        executor.execute(() -> recetaDao.insert(receta));
        return recetaDao.getRecetaByName(receta.getNombre());
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a insertar.
     */
    public void insert(Receta receta) {
        executor.execute(() -> recetaDao.insert(receta));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a actualizar.
     */
    public void update(Receta receta) {
        executor.execute(() -> recetaDao.update(receta));
    }

    /**
     * Elimina un registro de la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a eliminar.
     */
    public void delete(Receta receta) {
        executor.execute(() -> recetaDao.delete(receta));
    }

    /**
     * Inserta una nueva receta con sus respectivos ingredientes en la base de datos.
     *
     * @param receta       - {@link Receta} - El registro de la receta a insertar.
     * @param ingredientes - {@link List}<{@link RecetaIngrediente}> - La lista de ingredientes asociados a la receta.
     */
    public void insertarRecetaConIngredientes(Receta receta, List<RecetaIngrediente> ingredientes) {
        executor.execute(() -> {
            // 1. Insertamos la receta y obtenemos su ID generado
            long recetaId = recetaDao.insert(receta);

            // 2. Insertamos cada ingrediente usando ese ID
            for (RecetaIngrediente ing : ingredientes) {
                Receta_Ing recetaIng = new Receta_Ing(
                        (int) recetaId,
                        ing.getIdIngrediente(),
                        ing.getCantidad(),
                        ing.getUnidad()
                );
                // Obtenemos acceso al Dao de Receta_Ing para realizar la inserción
                recetaIngredienteDao.insert(recetaIng);
            }
        });
    }

    /**
     * Actualiza una receta con sus respectivos ingredientes en la base de datos.
     *
     * @param receta       - {@link Receta} - El registro de la receta a actualizar.
     * @param ingredientes - {@link List}<{@link RecetaIngrediente}> - La lista de ingredientes asociados a la receta.
     */
    public void actualizarRecetaConIngredientes(Receta receta, List<RecetaIngrediente> ingredientes) {
        executor.execute(() -> {
            // 1. Actualizamos la receta
            recetaDao.update(receta);

            // 2. Eliminamos los ingredientes asociados a la receta
            recetaIngredienteDao.deleteIngredientesByReceta(receta.getIdReceta());

            // 3. Insertamos los nuevos ingredientes
            for (RecetaIngrediente ing : ingredientes) {
                recetaIngredienteDao.insert(new Receta_Ing(
                        receta.getIdReceta(),
                        ing.getIdIngrediente(),
                        ing.getCantidad(),
                        ing.getUnidad()));
            }
        });
    }

    /**
     * Borra una receta con sus respectivos ingredientes en la base de datos.
     *
     * @param receta - {@link Receta} - El registro de la receta a borrar.
     */
    public void borrarReceta(Receta receta) {
        executor.execute(() -> {
            // 1. Eliminamos los ingredientes asociados a la receta
            recetaIngredienteDao.deleteIngredientesByReceta(receta.getIdReceta());

            // 2. Eliminamos la receta
            recetaDao.delete(receta);
        });
    }

}
