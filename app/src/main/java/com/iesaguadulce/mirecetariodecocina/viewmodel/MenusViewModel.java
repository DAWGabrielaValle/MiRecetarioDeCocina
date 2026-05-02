package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;
import com.iesaguadulce.mirecetariodecocina.room.Menu;
import com.iesaguadulce.mirecetariodecocina.room.MenuRecetasJoin;
import com.iesaguadulce.mirecetariodecocina.room.MenuRepositorio;
import com.iesaguadulce.mirecetariodecocina.room.Menu_RecRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los menús ({@link Menu}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link MenuRepositorio},
 * garantizando que los datos de los menús sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenuRepositorio
 * @see Menu_RecRepositorio
 */
public class MenusViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los menús. */
    private MenuRepositorio repositorio;

    /** Repositorio para la gestión de las operaciones de datos de las recetas asociadas a los menús. */
    private Menu_RecRepositorio menuRecRepositorio;

    /**
     * Constructor
     * Inicializa el repositorio de menús y de las recetas asociadas a los menús.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public MenusViewModel(@NonNull Application application) {
        super(application);
        repositorio = new MenuRepositorio(application);
        menuRecRepositorio = new Menu_RecRepositorio(application);
    }

    /**
     * Recupera todos los menús ({@link Menu}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Menu}.
     */
    public LiveData<List<Menu>> getAllMenus() {
        return repositorio.getAllMenus();
    }

    /**
     * Obtiene un {@link Menu} específico por su identificador.
     *
     * @param id - int - Identificador único del menú.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Menu} solicitado.
     */
    public LiveData<Menu> getMenu(int id) {
        return repositorio.getMenu(id);
    }

    /**
     * Obtiene un {@link Menu} específico por su nombre.
     *
     * @param nombre - String - Nombre del menú.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Menu} solicitado.
     */
    public LiveData<Menu> getMenuByNombre(String nombre) {
        return repositorio.getMenuByNombre(nombre);
    }

    /**
     * Recupera todas las recetas asociadas a un menú almacenadas en la base de datos utilizando la clase
     * {@link MenuRecetasJoin} que combina los datos de {@link Menu} y {@link MenuReceta}.
     *
     * @param idMenu - int - Identificador único del menú.
     * @return - Devuelve un {@link LiveData} con la lista de recetas asociadas al menú de {@link MenuRecetasJoin}.
     */
    public LiveData<List<MenuRecetasJoin>> getRecetasMenu(int idMenu) {
        return menuRecRepositorio.getAllRecetasMenu(idMenu);
    }

    /**
     * Inserta un nuevo {@link Menu} en la base de datos de forma asíncrona.
     *
     * @param menu - El objeto {@link Menu} a insertar.
     */
    public void insert(Menu menu) {
        repositorio.insert(menu);
    }

    /**
     * Actualiza un {@link Menu} existente en la base de datos de forma asíncrona.
     *
     * @param menu - El objeto {@link Menu} con los datos actualizados.
     */
    public void update(Menu menu) {
        repositorio.update(menu);
    }

    /**
     * Elimina un {@link Menu} de la base de datos de forma asíncrona.
     *
     * @param menu - El objeto {@link Menu} que se desea eliminar.
     */
    public void delete(Menu menu) {
        repositorio.delete(menu);
    }

    /**
     * Inserta un nuevo {@link Menu} y la lista de recetas ({@link MenuReceta}) en la base de datos.
     *
     * @param menu    - El objeto {@link Menu} a insertar.
     * @param recetas - {@link List}<{@link MenuReceta}> - La lista de recetas a asociar al menú.
     */
    public void insertarMenuConRecetas(Menu menu, List<MenuReceta> recetas) {
        repositorio.insertarMenuConRecetas(menu, recetas);
    }

    /**
     * Actualiza un {@link Menu} y la lista de recetas ({@link MenuReceta}) en la base de datos.
     *
     * @param menu    - El objeto {@link Menu} con los datos actualizados.
     * @param recetas - {@link List}<{@link MenuReceta}> - La lista de recetas a asociar al menú.
     */
    public void actualizarMenuConRecetas(Menu menu, List<MenuReceta> recetas) {
        repositorio.actualizarMenuConRecetas(menu, recetas);
    }

    /**
     * Borra un {@link Menu} y la lista de recetas asociadas al menú de la base de datos.
     *
     * @param menu - El objeto {@link Menu} a borrar.
     */
    public void borrarMenu(Menu menu) {
        repositorio.borrarMenu(menu);
    }

}
