package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;
import com.iesaguadulce.mirecetariodecocina.model.PlanMenuRepositorio;

import java.util.List;

/**
 * ViewModel para el manejo temporal de datos de los menús por día de una planificación ({@link PlanMenu}) en memoria.
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
 * @see PlanMenu
 */
public class DataPlanMenuViewModel extends ViewModel {

    /** LiveData encapsulado que contiene la lista de los días de la planificación. */
    private MutableLiveData<List<PlanMenu>> planMenus;

    /** Repositorio para la gestión de las operaciones de datos de los días de la planificación. */
    private PlanMenuRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de los menús por día de la planificación.
     */
    public DataPlanMenuViewModel() {
        repositorio = new PlanMenuRepositorio();
        planMenus = repositorio.getPlanMenus();
    }

    /**
     * Proporciona acceso de solo lectura a la lista de los menús por día de la planificación ({@link PlanMenu}).
     * Obtiene la lista de los días de la planificación desde el repositorio.
     *
     * @return - Devuelve un {@link MutableLiveData} que contiene la lista de los días de la planificación ({@link PlanMenu}).
     */
    public MutableLiveData<List<PlanMenu>> getAllPlanMenus() {
        return planMenus;
    }

    /**
     * Obtiene los menús asociados a un día específico de la planificación ({@link PlanMenu}).
     *
     * @param orden - int - Identificador único del día de la planificación.
     * @return - Devuelve un {@link LiveData} con la lista de menús asociados al día especificado.
     */
    public LiveData<List<PlanMenu>> getPlanMenusByOrden(int orden) {
        return repositorio.getPlanMenusByOrden(orden);
    }

    /**
     * Actualiza la lista de los menús por día de la planificación ({@link PlanMenu}) y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información de la lista de los menús por día de la planificación
     * entre diferentes fragmentos o actividades.
     * </p>
     *
     * @param listaPlanMenus - {@link List}<{@link PlanMenu}> - La nueva lista de menús por día de la planificación a compartir.
     */
    public void setListaPlanMenus(List<PlanMenu> listaPlanMenus) {
        repositorio.setListaPlanMenus(listaPlanMenus);
    }

    /**
     * Añade un menú al día de la planificación ({@link PlanMenu}).
     *
     * @param planMenu - {@link PlanMenu} - El menú a añadir al día de la planificación.
     */
    public void addPlanMenu(PlanMenu planMenu) {
        repositorio.addPlanMenu(planMenu);
    }

    /**
     * Elimina un menú del día de la planificación ({@link PlanMenu}).
     *
     * @param planMenu - {@link PlanMenu} - El menú a eliminar del día de la planificación.
     */
    public void removePlanMenu(PlanMenu planMenu) {
        repositorio.removePlanMenu(planMenu);
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
