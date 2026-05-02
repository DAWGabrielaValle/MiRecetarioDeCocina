package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Ingrediente} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class IngredienteRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Ingrediente} en la base de datos local.
     */
    private IngredienteDao ingredienteDao;

    /**
     * Ejecutor para operaciones de base de datos en un hilo separado.
     */
    private Executor executor;

    /**
     * Constructor
     * Crea una instancia del repositorio con el DAO y el executor necesarios.
     *
     * @param application - {@link Application} - La aplicación que utiliza el repositorio.
     */
    public IngredienteRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        ingredienteDao = database.getIngredienteDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros de la entidad {@link Ingrediente} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Ingrediente}>> - Objeto {@link LiveData} que contiene una lista de registros del ingrediente.
     */
    public LiveData<List<Ingrediente>> getAllIngredientes() {
        return ingredienteDao.getAll();
    }

    /**
     * Obtiene un registro de la entidad {@link Ingrediente} por su identificador.
     *
     * @param id - int - Identificador del registro del ingrediente.
     * @return - LiveData<{@link Ingrediente}> - Objeto {@link LiveData} que contiene el registro del ingrediente.
     */
    public LiveData<Ingrediente> getIngrediente(int id) {
        return ingredienteDao.getIngrediente(id);
    }

    /**
     * Obtiene un registro de la entidad {@link Ingrediente} por su nombre.
     *
     * @param nombre - String - Nombre del registro del ingrediente.
     * @return - LiveData<{@link Ingrediente}> - Objeto {@link LiveData} que contiene el registro del ingrediente.
     */
    public LiveData<Ingrediente> getIngredienteByNombre(String nombre) {
        return ingredienteDao.getIngredientByName(nombre);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a insertar.
     */
    public void insert(Ingrediente ingrediente) {
        executor.execute(() -> ingredienteDao.insert(ingrediente));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a actualizar.
     */
    public void update(Ingrediente ingrediente) {
        executor.execute(() -> ingredienteDao.update(ingrediente));
    }

    /**
     * Elimina un registro de la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a eliminar.
     */
    public void delete(Ingrediente ingrediente) {
        executor.execute(() -> ingredienteDao.delete(ingrediente));
    }
}
