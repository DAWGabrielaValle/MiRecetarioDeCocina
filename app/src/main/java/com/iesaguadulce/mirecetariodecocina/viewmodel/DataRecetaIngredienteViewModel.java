package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngredienteRepositorio;

import java.util.List;

/**
 * ViewModel para el manejo temporal de los ingredientes de una receta ({@link RecetaIngrediente}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de una lista de ingredientes de una receta entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetaIngrediente
 */
public class DataRecetaIngredienteViewModel extends ViewModel {

    /** LiveData encapsulado que contiene la lista de ingredientes de la receta. */
    private MutableLiveData<List<RecetaIngrediente>> recetaIngredientes;

    /** Repositorio para la gestión de las operaciones de datos de ingredientes de la receta. */
    private RecetaIngredienteRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de ingredientes de la receta.
     */
    public DataRecetaIngredienteViewModel() {
        repositorio = new RecetaIngredienteRepositorio();
        recetaIngredientes = repositorio.getIngredientes();
    }

    /**
     * Proporciona acceso de solo lectura a la lista de ingredientes de la receta ({@link RecetaIngrediente}).
     * Obtiene la lista de ingredientes de la receta desde el repositorio.
     *
     * @return - Devuelve un {@link MutableLiveData} que contiene la lista de ingredientes de la receta ({@link RecetaIngrediente}).
     */
    public MutableLiveData<List<RecetaIngrediente>> getAllIngredientes() {
        return recetaIngredientes;
    }

    /**
     * Actualiza la lista de ingredientes de la receta ({@link RecetaIngrediente}) y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información de la lista de ingredientes
     * de una receta entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param listaIngredientes - {@link List}<{@link RecetaIngrediente}> - La nueva lista de ingredientes a compartir.
     */
    public void setListaIngredientes(List<RecetaIngrediente> listaIngredientes) {
        repositorio.setListaIngredientes(listaIngredientes);
    }

    /**
     * Añade un ingrediente a la lista de ingredientes de la receta ({@link RecetaIngrediente}).
     *
     * @param ingrediente - {@link RecetaIngrediente} - El ingrediente a añadir a la lista.
     */
    public void addIngrediente(RecetaIngrediente ingrediente) {
        repositorio.addIngrediente(ingrediente);
    }

    /**
     * Elimina un ingrediente de la lista de ingredientes de la receta ({@link RecetaIngrediente}).
     *
     * @param ingrediente - {@link RecetaIngrediente} - El ingrediente a eliminar de la lista.
     */
    public void removeIngrediente(RecetaIngrediente ingrediente) {
        repositorio.removeIngrediente(ingrediente);
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
