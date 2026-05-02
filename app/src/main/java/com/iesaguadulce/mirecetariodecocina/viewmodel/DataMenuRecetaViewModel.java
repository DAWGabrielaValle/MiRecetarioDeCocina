package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;
import com.iesaguadulce.mirecetariodecocina.model.MenuRecetaRepositorio;

import java.util.List;

/**
 * ViewModel para el manejo temporal de recetas en un menú ({@link MenuReceta}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de una lista de recetas en un menú entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenuReceta
 */
public class DataMenuRecetaViewModel extends ViewModel {

    /** LiveData encapsulado que contiene la lista de recetas del menú. */
    private MutableLiveData<List<MenuReceta>> menuRecetas;

    /** Repositorio para la gestión de las operaciones de datos de recetas. */
    private MenuRecetaRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de recetas.
     */
    public DataMenuRecetaViewModel() {
        repositorio = new MenuRecetaRepositorio();
        menuRecetas = repositorio.getRecetas();
    }

    /**
     * Proporciona acceso de solo lectura a la lista de recetas del menú.
     * Obtiene la lista de recetas del menú ({@link MenuReceta}) desde el repositorio.
     *
     * @return - Devuelve un {@link MutableLiveData} que contiene la lista de recetas del menú ({@link MenuReceta}).
     */
    public MutableLiveData<List<MenuReceta>> getAllRecetas() {
        return menuRecetas;
    }

    /**
     * Actualiza la lista de recetas del menú ({@link MenuReceta}) y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información de la lista de recetas
     * en un menú entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param listaRecetas - {@link List}<{@link MenuReceta}> - La nueva lista de recetas a compartir.
     */
    public void setListaRecetas(List<MenuReceta> listaRecetas) {
        repositorio.setListaRecetas(listaRecetas);
    }

    /**
     * Añade una receta al menú ({@link MenuReceta}).
     * <p>
     * Este método se utiliza para compartir la información de una receta
     * en un menú entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param receta - {@link MenuReceta} - La receta a añadir al menú.
     */
    public void addReceta(MenuReceta receta) {
        repositorio.addReceta(receta);
    }

    /**
     * Elimina una receta del menú ({@link MenuReceta}).
     * <p>
     * Este método se utiliza para compartir la información de una receta
     * en un menú entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param receta - {@link MenuReceta} - La receta a eliminar del menú.
     */
    public void removeReceta(MenuReceta receta) {
        repositorio.removeReceta(receta);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clear() {
        repositorio.clear();
    }


}
