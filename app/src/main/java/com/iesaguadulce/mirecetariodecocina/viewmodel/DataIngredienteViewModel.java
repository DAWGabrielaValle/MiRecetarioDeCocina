package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.DataIngrediente;

/**
 * ViewModel para el manejo temporal de datos de ingredientes ({@link DataIngrediente}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información 
 * de un ingrediente específico entre diferentes fragmentos o actividades sin necesidad 
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven 
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataIngrediente
 */
public class DataIngredienteViewModel extends ViewModel {

    /** LiveData encapsulado que contiene los datos temporales del ingrediente seleccionado.  */
    private MutableLiveData<DataIngrediente> dataIngrediente = new MutableLiveData<>();

    /**
     * Proporciona acceso de solo lectura a los datos del ingrediente ({@link DataIngrediente}).
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento, 
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     * 
     * @return - Devuelve un {@link LiveData} que contiene el objeto {@link DataIngrediente} actual.
     */
    public LiveData<DataIngrediente> getDataIngrediente() {
        return dataIngrediente;
    }

    /**
     * Actualiza el ingrediente ({@link DataIngrediente}) almacenado en el ViewModel y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información del ingrediente que se desea 
     * mostrar o editar en la siguiente pantalla del flujo de navegación.
     * </p>
     * 
     * @param ingrediente - El objeto {@link DataIngrediente} con la información a compartir.
     */
    public void setDataIngrediente(DataIngrediente ingrediente) {
        dataIngrediente.setValue(ingrediente);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o 
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clear() {
        dataIngrediente.setValue(null);
    }
}
