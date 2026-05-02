package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Plan} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class PlanRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Plan} en la base de datos local.
     */
    private PlanDao planDao;

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Diario} en la base de datos local.
     */
    private DiarioDao diarioDao;

    /**
     * Interfaz de acceso a datos (DAO) para la relación entre {@link Diario} y {@link Menu} en la base de datos local.
     */
    private Diario_MenuDao diarioMenuDao;

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
    public PlanRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        planDao = database.getPlanDao();
        diarioDao = database.getDiarioDao();
        diarioMenuDao = database.getDiarioMenuDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros de la entidad {@link Plan} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Plan}>> - Objeto {@link LiveData} que contiene una lista de registros del plan.
     */
    public LiveData<List<Plan>> getAllPlanes() {
        return planDao.getAll();
    }

    /**
     * Obtiene un registro de la entidad {@link Plan} por su identificador.
     *
     * @param id - int - Identificador del registro del plan.
     * @return - LiveData<{@link Plan}> - Objeto {@link LiveData} que contiene el registro del plan.
     */
    public LiveData<Plan> getPlan(int id) {
        return planDao.getPlan(id);
    }

    /**
     * Obtiene un registro de la entidad {@link Plan} por su nombre.
     *
     * @param nombre - String - Nombre del registro del plan.
     * @return - LiveData<{@link Plan}> - Objeto {@link LiveData} que contiene el registro del plan.
     */
    public LiveData<Plan> getPlanByNombre(String nombre) {
        return planDao.getPlanByNombre(nombre);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a insertar.
     * @return - LiveData<{@link Plan}> - Objeto {@link LiveData} que contiene el registro del plan.
     */
    public LiveData<Plan> insertarPlan(Plan plan) {
        executor.execute(() -> planDao.insert(plan));
        return planDao.getPlanByNombre(plan.getNombre());
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a insertar.
     */
    public void insert(Plan plan) {
        executor.execute(() -> planDao.insert(plan));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a actualizar.
     */
    public void update(Plan plan) {
        executor.execute(() -> planDao.update(plan));
    }

    /**
     * Elimina un registro de la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a eliminar.
     */
    public void delete(Plan plan) {
        executor.execute(() -> planDao.delete(plan));
    }

    /**
     * Inserta un nuevo plan con sus respectivos diarios y menús en la base de datos.
     *
     * @param plan  - {@link Plan} - El registro del plan a insertar.
     * @param menus - {@link List}<{@link PlanMenu}> - La lista de menús asociados al plan.
     */
    public void insertarPlanConMenus(Plan plan, List<PlanMenu> menus) {
        executor.execute(() -> {
            // 1. Insertamos el plan y obtenemos su ID generado
            long planId = planDao.insert(plan);

            // 2. Insertamos cada diario usando ese ID
            for (int i = 1; i <= plan.getDias(); i++) {
                Diario nuevoDiario = new Diario(
                        i,
                        (int) planId
                );
                // Obtenemos acceso al Dao de Diario para realizar la inserción
                long idDiario = diarioDao.insert(nuevoDiario);

                // 3. Insertamos cada menú usando ese ID de diario
                for (PlanMenu menu : menus) {
                    if (menu.getOrden() == i) {
                        Diario_Menu planMenu = new Diario_Menu(
                                (int) idDiario,
                                menu.getIdMenu()
                        );
                        // Obtenemos acceso al Dao de Diario_Menu para realizar la inserción
                        diarioMenuDao.insert(planMenu);
                    }
                }
            }
        });
    }

    /**
     * Actualiza un plan con sus respectivos diarios y menús en la base de datos.
     *
     * @param plan  - {@link Plan} - El registro del plan a actualizar.
     * @param menus - {@link List}<{@link PlanMenu}> - La lista de menús asociados al plan.
     */
    public void actualizarPlanConMenus(Plan plan, List<PlanMenu> menus) {
        executor.execute(() -> {
            // 1. Actualizamos el plan
            planDao.update(plan);

            // 2. Eliminamos los menús asociados al plan
            diarioMenuDao.deleteDiarioMenusByPlan(plan.getIdPlan());

            // 3. Obtenemos los diarios asociados al plan de forma SÍNCRONA
            List<Diario> diarios = diarioDao.getListDiariosByPlan(plan.getIdPlan());
            if (diarios != null) {
                for (Diario diario : diarios) {
                    // 4. Insertamos cada menú usando ese ID de diario
                    for (PlanMenu menu : menus) {
                        if (menu.getOrden() == diario.getOrden()) {
                            Diario_Menu planMenu = new Diario_Menu(
                                    diario.getIdDiario(),
                                    menu.getIdMenu()
                            );
                            // Obtenemos acceso al Dao de Diario_Menu para realizar la inserción
                            diarioMenuDao.insert(planMenu);
                        }
                    }
                }
            }
        });
    }

    /**
     * Borra un plan con sus respectivos diarios y menús en la base de datos.
     *
     * @param plan - {@link Plan} - El registro del plan a borrar.
     */
    public void borrarPlan(Plan plan) {
        executor.execute(() -> {
            // 1. Eliminamos los menús asociados al plan
            diarioMenuDao.deleteDiarioMenusByPlan(plan.getIdPlan());
            // 2. Eliminamos los diarios asociados al plan
            diarioDao.deleteDiariosByPlan(plan.getIdPlan());
            // 3. Eliminamos el plan
            planDao.delete(plan);
        });
    }
}
