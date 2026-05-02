package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.DataPlan;

/**
 * ViewModel para el manejo temporal de datos de una planificación ({@link DataPlan}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de una planificación entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataPlan
 */
public class DataPlanViewModel extends ViewModel {

    /** LiveData encapsulado que contiene los datos temporales de la planificación. */
    private MutableLiveData<DataPlan> dataPlan = new MutableLiveData<>();

    /**
     * Proporciona acceso de solo lectura a los datos de la planificación ({@link DataPlan}).
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento,
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     *
     * @return - Devuelve un {@link LiveData} que contiene el objeto {@link DataPlan} actual.
     */
    public LiveData<DataPlan> getDataPlan() {
        return dataPlan;
    }

    /**
     * Actualiza la planificación ({@link DataPlan}) almacenada en el ViewModel y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información de la planificación que se desea
     * mostrar o editar en la siguiente pantalla del flujo de navegación.
     * </p>
     *
     * @param plan - El objeto {@link DataPlan} con la información a compartir.
     */
    public void setDataPlan(DataPlan plan) {
        dataPlan.setValue(plan);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clear() {
        dataPlan.setValue(null);
    }

}
