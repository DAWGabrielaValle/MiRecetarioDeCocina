package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Menu_Rec} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class Menu_RecRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la relación entre {@link Menu} y {@link Receta} en la base de datos local.
     */
    private Menu_RecDao menuRecDao;

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
    public Menu_RecRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        menuRecDao = database.getMenuRecDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene una lista con todas las recetas asociadas a un menú dado en la base de datos {@link MenuRecetasJoin}.
     *
     * @param idMenu - int - Identificador del {@link Menu_Rec}.
     * @return - LiveData<{@link List}<{@link MenuRecetasJoin}>> - Objeto {@link LiveData} que contiene una
     * lista de registros del {@link MenuRecetasJoin}
     */
    public LiveData<List<MenuRecetasJoin>> getAllRecetasMenu(int idMenu) {
        return menuRecDao.getRecetasMenu(idMenu);
    }

    /**
     * Obtiene una lista con todos los registros del {@link Menu_Rec} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Menu_Rec}>> - Objeto {@link LiveData} que contiene una
     * lista de registros del {@link Menu_Rec}
     */
    public LiveData<List<Menu_Rec>> getAllMenuRecetas() {
        return menuRecDao.getAll();
    }

    /**
     * Obtiene una lista de los menús asociados a una receta específica.
     *
     * @param idReceta - int - Identificador de la receta {@link Menu_Rec}
     * @return - LiveData<{@link List}<{@link Menu_Rec}>> - Objeto {@link LiveData} que contiene una
     * lista de registros del {@link Menu_Rec}
     */
    public LiveData<List<Menu_Rec>> getAllMenus(int idReceta) {
        return menuRecDao.getMenus(idReceta);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a insertar.
     */
    public void insert(Menu_Rec menuRec) {
        executor.execute(() -> menuRecDao.insert(menuRec));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a actualizar.
     */
    public void update(Menu_Rec menuRec) {
        executor.execute(() -> menuRecDao.update(menuRec));
    }

    /**
     * Elimina un registro de la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a eliminar.
     */
    public void delete(Menu_Rec menuRec) {
        executor.execute(() -> menuRecDao.delete(menuRec));
    }

}
