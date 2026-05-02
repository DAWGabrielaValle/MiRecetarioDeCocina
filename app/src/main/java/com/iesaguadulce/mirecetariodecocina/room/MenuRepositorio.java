package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Menu} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class MenuRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Menu} en la base de datos local.
     */
    private MenuDao menuDao;

    /**
     * Interfaz de acceso a datos (DAO) para la relación entre {@link Menu} y {@link Receta} en la base de datos local.
     */
    private Menu_RecDao menuRecetaDao;

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
    public MenuRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        menuDao = database.getMenuDao();
        menuRecetaDao = database.getMenuRecDao();
        executor = Executors.newSingleThreadExecutor();

    }

    /**
     * Obtiene todos los registros de la entidad {@link Menu} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Menu}>> - Objeto {@link LiveData} que contiene una lista de registros del menú.
     */
    public LiveData<List<Menu>> getAllMenus() {
        return menuDao.getAll();
    }

    /**
     * Obtiene un registro de la entidad {@link Menu} por su identificador.
     *
     * @param nombre - String - Nombre del registro del menú.
     * @return - LiveData<{@link Menu}> - Objeto {@link LiveData} que contiene el registro del menú.
     */
    public LiveData<Menu> getMenuByNombre(String nombre) {
        return menuDao.getMenuByNombre(nombre);
    }

    /**
     * Obtiene un registro de la entidad {@link Menu} por su identificador.
     *
     * @param id - int - Identificador del registro del menú.
     * @return - LiveData<{@link Menu}> - Objeto {@link LiveData} que contiene el registro del menú.
     */
    public LiveData<Menu> getMenu(int id) {
        return menuDao.getMenu(id);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a insertar.
     */
    public void insert(Menu menu) {
        executor.execute(() -> menuDao.insert(menu));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a actualizar.
     */
    public void update(Menu menu) {
        executor.execute(() -> menuDao.update(menu));
    }

    /**
     * Elimina un registro de la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a eliminar.
     */
    public void delete(Menu menu) {
        executor.execute(() -> menuDao.delete(menu));
    }

    /**
     * Inserta un nuevo menú con sus recetas asociadas
     *
     * @param menu    - {@link Menu} - El registro del menú a insertar.
     * @param recetas - {@link List}<{@link MenuReceta}> - Lista de recetas asociadas al menú.
     */
    public void insertarMenuConRecetas(Menu menu, List<MenuReceta> recetas) {
        executor.execute(() -> {
            // 1. Insertamos el menú y obtenemos su ID generado
            long menuId = menuDao.insert(menu);

            // 2. Insertamos cada receta usando ese ID
            for (MenuReceta receta : recetas) {
                Menu_Rec menuRec = new Menu_Rec(
                        (int) menuId,
                        receta.getIdReceta()
                );
                // Obtenemos acceso al Dao de Menu_Rec para realizar la inserción
                menuRecetaDao.insert(menuRec);
            }
        });
    }

    /**
     * Actualiza un menú con sus recetas asociadas
     *
     * @param menu    - {@link Menu} - El registro del menú a actualizar.
     * @param recetas - {@link List}<{@link MenuReceta}> - Lista de recetas asociadas al menú.
     */
    public void actualizarMenuConRecetas(Menu menu, List<MenuReceta> recetas) {
        executor.execute(() -> {
            // 1. Actualizamos el menú
            menuDao.update(menu);

            // 2. Eliminamos las recetas asociadas al menú
            menuRecetaDao.deleteRecetasByMenu(menu.getIdMenu());

            // 3. Insertamos las nuevas recetas
            for (MenuReceta receta : recetas) {
                menuRecetaDao.insert(new Menu_Rec(
                        menu.getIdMenu(),
                        receta.getIdReceta()));
            }
        });
    }

    /**
     * Borra un menú con sus recetas asociadas
     *
     * @param menu - {@link Menu} - El registro del menú a borrar.
     */
    public void borrarMenu(Menu menu) {
        executor.execute(() -> {
            // 1. Eliminamos las recetas asociadas al menú
            menuRecetaDao.deleteRecetasByMenu(menu.getIdMenu());

            // 2. Eliminamos el menú
            menuDao.delete(menu);
        });
    }


}
