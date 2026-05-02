package com.iesaguadulce.mirecetariodecocina.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para la gestión temporal de recetas asociadas a un menú.
 * <p>
 * Esta clase actúa como un contenedor reactivo utilizando {@link MutableLiveData},
 * permitiendo que los componentes de la interfaz de usuario observen cambios en tiempo
 * real cuando se añaden o eliminan recetas durante el proceso de creación o edición.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class MenuRecetaRepositorio {
    /** Objeto observable que contiene la lista de recetas. */
    private MutableLiveData<List<MenuReceta>> menuRecetas;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa el objeto observable
     * {@link MutableLiveData} con una lista vacía de recetas.
     */
    public MenuRecetaRepositorio() {
        // Inicializamos el MutableLiveData con una lista vacía
        menuRecetas = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Devuelve el objeto observable que contiene la lista de recetas.
     * @return - Devuelve el objeto {@link MutableLiveData} que contiene la lista actual de {@link MenuReceta}.
     */
    public MutableLiveData<List<MenuReceta>> getRecetas() {
        return menuRecetas;
    }

    /**
     * Establece la lista de recetas en el objeto observable {@link MutableLiveData}.
     * @param listaRecetas - La nueva lista de {@link MenuReceta} a establecer.
     */
    public void setListaRecetas(List<MenuReceta> listaRecetas) {
        menuRecetas.setValue(listaRecetas);
    }

    /**
     * Añade una nueva receta a la lista actual.
     * @param receta - El objeto {@link MenuReceta} que se desea añadir.
     */
    public void addReceta(MenuReceta receta) {
        List<MenuReceta> listaRecetas = menuRecetas.getValue();
        if (listaRecetas == null) {
            listaRecetas = new ArrayList<>();
        }
        listaRecetas.add(receta);
        menuRecetas.setValue(listaRecetas);
    }

    /**
     * Elimina una receta de la lista actual.
     * @param receta - El objeto {@link MenuReceta} que se desea eliminar.
     */
    public void removeReceta(MenuReceta receta) {
        List<MenuReceta> listaRecetas = menuRecetas.getValue();
        if (listaRecetas != null) {
            listaRecetas.remove(receta);
            menuRecetas.setValue(listaRecetas);
        }
    }

    /**
     * Limpia la lista completa de recetas, dejándola vacía.
     */
    public void clear() {
        menuRecetas.setValue(new ArrayList<>());
    }

}
