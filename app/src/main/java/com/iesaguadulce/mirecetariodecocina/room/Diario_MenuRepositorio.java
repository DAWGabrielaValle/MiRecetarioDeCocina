package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Diario_Menu} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class Diario_MenuRepositorio {
    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Diario_Menu} en la base de datos local.
     */
    private Diario_MenuDao diarioMenuDao;

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
    public Diario_MenuRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        diarioMenuDao = database.getDiarioMenuDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene una lista con todos los registros del {@link Diario_Menu} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Diario_Menu}>> - Objeto {@link LiveData} que contiene una
     * lista de registros del {@link Diario_Menu}
     */
    public LiveData<List<Diario_Menu>> getAllDiarioMenu() {
        return diarioMenuDao.getAll();
    }

    /**
     * Obtiene una lista con los registros del {@link Diario_Menu} correspondientes al identificador del menú dado.
     *
     * @param idMenu - int - Identificador del {@link Diario_Menu}.
     * @return - LiveData<{@link List}<{@link Diario_Menu}>> - Objeto {@link LiveData} que contiene una
     * lista de registros del {@link Diario_Menu}
     */
    public LiveData<List<Diario_Menu>> getAllDiarios(int idMenu) {
        return diarioMenuDao.getAllDiarios(idMenu);
    }

    /**
     * Obtiene una lista de la clase {@link DiarioMenuJoin} con los datos del {@link Menu} y {@link Diario}
     * correspondientes a los registros de la entidad {@link Diario_Menu} seleccionados por el identificador
     * del diario dado.
     *
     * @param idDiario - int - Identificador del diario en la entidad {@link Diario_Menu}.
     * @return - LiveData<{@link List}<{@link DiarioMenuJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros del {@link DiarioMenuJoin}
     */
    public LiveData<List<DiarioMenuJoin>> getMenusDiario(int idDiario) {
        return diarioMenuDao.getMenusDiario(idDiario);
    }

    /**
     * Obtiene una lista de la clase {@link DiarioMenuJoin} con los datos del {@link Menu} y {@link Diario}
     * correspondientes a los registros de la entidad {@link Diario_Menu} seleccionados por el identificador
     * del plan de la entidad {@link Diario}
     *
     * @param idPlan - int - Identificador del plan en la entidad {@link Diario}.
     * @return - LiveData<{@link List}<{@link DiarioMenuJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros del {@link DiarioMenuJoin}
     */
    public LiveData<List<DiarioMenuJoin>> getMenusByPlan(int idPlan) {
        return diarioMenuDao.getMenusByPlan(idPlan);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a insertar.
     */
    public void insert(Diario_Menu diarioMenu) {
        executor.execute(() -> diarioMenuDao.insert(diarioMenu));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a actualizar.
     */
    public void update(Diario_Menu diarioMenu) {
        executor.execute(() -> diarioMenuDao.update(diarioMenu));
    }

    /**
     * Elimina un registro de la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a eliminar.
     */
    public void delete(Diario_Menu diarioMenu) {
        executor.execute(() -> diarioMenuDao.delete(diarioMenu));
    }

    /**
     * Borra los registros de la entidad {@link Diario_Menu} correspondientes al identificador del plan
     * de la entidad {@link Diario} dado en la base de datos.
     *
     * @param idPlan - int - Identificador del plan en la entidad {@link Diario}.
     */
    public void deleteDiarioMenusByPlan(int idPlan) {
        executor.execute(() -> diarioMenuDao.deleteDiarioMenusByPlan(idPlan));
    }



}
