package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Diario} en la base de datos local.
 * <p>
 * Esta clase proporciona métodos para interactuar con la capa de acceso a datos (DAO)
 * de la base de datos local y realizar operaciones CRUD en la entidad {@link Diario}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DiarioRepositorio {
    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Diario} en la base de datos local.
     */
    private DiarioDao diarioDao;
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
    public DiarioRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        diarioDao = database.getDiarioDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros del diario de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Diario}>> - Objeto {@link LiveData} que contiene una lista de registros del diario
     */
    public LiveData<List<Diario>> getAllDiarios() {
        return diarioDao.getAll();
    }

    /**
     * Obtiene todos los registros del diario de un {@link Plan} específico.
     *
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el diario.
     * @return - LiveData<{@link List}<{@link Diario}>> - Objeto {@link LiveData} que contiene una lista de registros del diario.
     */
    public LiveData<List<Diario>> getDiarioByPlan(int idPlan) {
        return diarioDao.getDiarioByPlan(idPlan);
    }

    /**
     * Obtiene un registro del diario por su identificador.
     *
     * @param id - int - Identificador del registro del diario.
     * @return - LiveData<{@link Diario}> - Objeto {@link LiveData} que contiene el registro del diario.
     */
    public LiveData<Diario> getDiario(int id) {
        return diarioDao.getDiario(id);
    }

    /**
     * Obtiene el último registro del diario insertado en la base de datos.
     *
     * @return - LiveData<{@link Diario}> - Objeto {@link LiveData} que contiene el último registro del diario.
     */
    public LiveData<Diario> getLastInsertedDay() {
        return diarioDao.getLastInsertedDay();
    }

    /**
     * Inserta un nuevo registro en el diario.
     *
     * @param diario - {@link Diario} - El registro del diario a insertar.
     * @return - LiveData<{@link Diario}> - Objeto {@link LiveData} que contiene el último registro del diario.
     */
    public LiveData<Diario> insertarDiario(Diario diario) {
        executor.execute(() -> diarioDao.insert(diario));
        return diarioDao.getLastInsertedDay();
    }

    /**
     * Inserta un nuevo registro en el diario.
     *
     * @param diario - {@link Diario} - El registro del diario a insertar.
     */
    public void insert(Diario diario) {
        executor.execute(() -> diarioDao.insert(diario));
    }

    /**
     * Actualiza un registro existente en el diario.
     *
     * @param diario - {@link Diario} - El registro del diario a actualizar.
     */
    public void update(Diario diario) {
        executor.execute(() -> diarioDao.update(diario));
    }

    /**
     * Elimina un registro del diario.
     *
     * @param diario - {@link Diario} - El registro del diario a eliminar.
     */
    public void delete(Diario diario) {
        executor.execute(() -> diarioDao.delete(diario));
    }

    /**
     * Borra todos los registros del diario de un {@link Plan} específico.
     *
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el diario.
     */
    public void deleteDiariosByPlan(int idPlan) {
        executor.execute(() -> diarioDao.deleteDiariosByPlan(idPlan));
    }

}
