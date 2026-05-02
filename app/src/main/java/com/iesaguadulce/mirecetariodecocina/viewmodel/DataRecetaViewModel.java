package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.DataReceta;

/**
 * ViewModel para el manejo temporal de datos de una receta ({@link DataReceta}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de una receta entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataReceta
 */
public class DataRecetaViewModel extends ViewModel {

    // Elementos de la receta temporales
    /** LiveData encapsulado que contiene el modo de preparación de la receta de forma temporal. */
    private MutableLiveData<String> toDoReceta = new MutableLiveData<>("");

    /**
     * Proporciona acceso de solo lectura al modo de preparación de la receta.
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento,
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     *
     * @return - Devuelve un {@link LiveData} que contiene el modo de preparación de la receta de forma temporal.
     */
    public LiveData<String> getToDoReceta() {
        return toDoReceta;
    }

    /**
     * Actualiza el modo de preparación de la receta de forma temporal y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir el modo de preparación de la receta que se desea
     * mostrar o editar en la siguiente pantalla del flujo de navegación.
     * </p>
     *
     * @param toDoReceta - String - El modo de preparación de la receta de forma temporal a compartir.
     */
    public void setToDoReceta(String toDoReceta) {
        this.toDoReceta.setValue(toDoReceta);
    }

    /**
     * Limpia el modo de preparación de la receta de forma temporal estableciendo el valor a {@code ""}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clearToDoReceta() {
        this.toDoReceta.setValue("");
    }
    // Fin métodos para los elementos de la receta temporales

    /** LiveData encapsulado que contiene los datos temporales de la receta. */
    private MutableLiveData<DataReceta> dataReceta = new MutableLiveData<>();

    /**
     * Proporciona acceso de solo lectura a los datos de la receta ({@link DataReceta}).
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento,
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     *
     * @return - Devuelve un {@link LiveData} que contiene el objeto {@link DataReceta} actual.
     */
    public LiveData<DataReceta> getDataReceta() {
        return dataReceta;
    }

    /**
     * Actualiza la receta ({@link DataReceta}) almacenada en el ViewModel y notifica a los observadores.
     *
     * @param receta - El objeto {@link DataReceta} con la información a compartir.
     */
    public void setDataReceta(DataReceta receta) {
        dataReceta.setValue(receta);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clear() {
        dataReceta.setValue(null);
    }

}
