package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.PlanDia;
import com.iesaguadulce.mirecetariodecocina.model.PlanDiaRepositorio;

import java.util.List;

/**
 * ViewModel para el manejo temporal de datos de los días de una planificación ({@link PlanDia}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de los días de una planificación entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanDia
 */
public class DataPlanDiaViewModel extends ViewModel {

    /** LiveData encapsulado que contiene la lista de los días de la planificación. */
    private MutableLiveData<List<PlanDia>> planDias;

    /** Repositorio para la gestión de las operaciones de datos de los días de la planificación. */
    private PlanDiaRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de los días de la planificación.
     */
    public DataPlanDiaViewModel() {
        repositorio = new PlanDiaRepositorio();
        planDias = repositorio.getAllDias();
    }

    /**
     * Proporciona acceso de solo lectura a la lista de los días de la planificación ({@link PlanDia}).
     * Obtiene la lista de los días de la planificación desde el repositorio.
     *
     * @return - Devuelve un {@link MutableLiveData} que contiene la lista de los días de la planificación ({@link PlanDia}).
     */
    public MutableLiveData<List<PlanDia>> getAllDias() {
        return planDias;
    }

    /**
     * Actualiza la lista de los días de la planificación ({@link PlanDia}) y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información de la lista de los días de la planificación
     * entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param dia - {@link PlanDia} - El nuevo día de la planificación a compartir.
     */
    public void addDia(PlanDia dia) {
        repositorio.addDia(dia);
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
