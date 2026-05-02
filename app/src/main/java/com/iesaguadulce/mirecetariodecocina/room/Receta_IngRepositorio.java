package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Receta_Ing} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class Receta_IngRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la relación entre {@link Receta} y {@link Ingrediente} en la base de datos local
     */
    private Receta_IngDao recetaIngDao;

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
    public Receta_IngRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        recetaIngDao = database.getRecetaIngDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los ingredientes asociados a una receta específica.
     *
     * @param idReceta - int - Identificador de la receta {@link Receta_Ing}
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros de ingredientes {@link RecetaIngredientesJoin}
     */
    public LiveData<List<RecetaIngredientesJoin>> getAllIngredientesReceta(int idReceta) {
        return recetaIngDao.getIngredientesReceta(idReceta);
    }

    /**
     * Obtiene todos los ingredientes asociados a un menú específico.
     *
     * @param idMenu - int - Identificador del menú {@link Menu_Rec}
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros de ingredientes {@link RecetaIngredientesJoin}
     */
    public LiveData<List<RecetaIngredientesJoin>> getAllIngredientesMenu(int idMenu) {
        return recetaIngDao.getIngredientesMenu(idMenu);
    }

    /**
     * Obtiene todos los ingredientes asociados a un plan específico.
     *
     * @param idPlan - int - Identificador del plan {@link Diario}
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros de ingredientes {@link RecetaIngredientesJoin}
     */
    public LiveData<List<RecetaIngredientesJoin>> getAllIngredientesPlan(int idPlan) {
        return recetaIngDao.getIngredientesPlan(idPlan);
    }

    /**
     * Obtiene todas las recetas que contienen un ingrediente específico.
     *
     * @param idIngrediente - int - Identificador del ingrediente {@link Receta_Ing}
     * @return - LiveData<{@link List}<{@link Receta_Ing}>> - Objeto {@link LiveData} que contiene
     * una lista de registros de recetas {@link Receta_Ing}
     */
    public LiveData<List<Receta_Ing>> getAllRecetas(int idIngrediente) {
        return recetaIngDao.getRecetas(idIngrediente);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro de la receta a insertar.
     */
    public void insert(Receta_Ing recetaIng) {
        executor.execute(() -> recetaIngDao.insert(recetaIng));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro de la receta a actualizar.
     */
    public void update(Receta_Ing recetaIng) {
        executor.execute(() -> recetaIngDao.update(recetaIng));
    }

    /**
     * Elimina un registro de la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro de la receta a eliminar.
     */
    public void delete(Receta_Ing recetaIng) {
        executor.execute(() -> recetaIngDao.delete(recetaIng));
    }

}
