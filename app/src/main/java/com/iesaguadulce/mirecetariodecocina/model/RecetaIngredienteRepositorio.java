package com.iesaguadulce.mirecetariodecocina.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para la gestión temporal de ingredientes de una receta.
 * <p>
 * Esta clase actúa como un contenedor reactivo utilizando {@link MutableLiveData},
 * permitiendo que los componentes de la interfaz de usuario observen cambios en tiempo
 * real cuando se añaden o eliminan ingredientes durante el proceso de creación o edición.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class RecetaIngredienteRepositorio {
    /** Objeto observable que contiene la lista de ingredientes. */
    private MutableLiveData<List<RecetaIngrediente>> recetaIngredientes;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa el objeto observable
     * {@link MutableLiveData} con una lista vacía de ingredientes.
     */
    public RecetaIngredienteRepositorio() {
        // Inicializamos el MutableLiveData con una lista vacía
        recetaIngredientes = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Devuelve el objeto observable que contiene la lista de ingredientes.
     * @return -Devuelve el objeto {@link MutableLiveData} que contiene la lista actual de {@link RecetaIngrediente}.
     */
    public MutableLiveData<List<RecetaIngrediente>> getIngredientes() {
        return recetaIngredientes;
    }

    /**
     * Establece la lista de ingredientes en el objeto observable {@link MutableLiveData}.
     * @param listaIngredientes - La nueva lista de {@link RecetaIngrediente} a establecer.
     */
    public void setListaIngredientes(List<RecetaIngrediente> listaIngredientes) {
        recetaIngredientes.setValue(listaIngredientes);
    }

    /**
     * Añade un nuevo ingrediente a la lista actual.
     * @param ingrediente - El objeto {@link RecetaIngrediente} que se desea añadir.
     */
    public void addIngrediente(RecetaIngrediente ingrediente) {
        List<RecetaIngrediente> listaIngredientes = recetaIngredientes.getValue();
        if (listaIngredientes == null) {
            listaIngredientes = new ArrayList<>();
        }
        listaIngredientes.add(ingrediente);
        recetaIngredientes.setValue(listaIngredientes);
    }

    /**
     * Elimina un ingrediente de la lista actual.
     * @param ingrediente - El objeto {@link RecetaIngrediente} que se desea eliminar.
     */
    public void removeIngrediente(RecetaIngrediente ingrediente) {
        List<RecetaIngrediente> listaIngredientes = recetaIngredientes.getValue();
        if (listaIngredientes != null) {
            listaIngredientes.remove(ingrediente);
            recetaIngredientes.setValue(listaIngredientes);
        }
    }

    /**
     * Limpia la lista completa de ingredientes, dejándola vacía.
     */
    public void clear() {
        recetaIngredientes.setValue(new ArrayList<>());
    }

}
