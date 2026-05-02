package com.iesaguadulce.mirecetariodecocina.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para la gestión temporal de los menus de un plan.
 * <p>
 * Esta clase actúa como un contenedor reactivo utilizando {@link MutableLiveData},
 * permitiendo que los componentes de la interfaz de usuario observen cambios en tiempo
 * real cuando se añaden o eliminan menus durante el proceso de creación o edición.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class PlanMenuRepositorio {
    /** Objeto observable que contiene la lista de menus. */
    private final MutableLiveData<List<PlanMenu>> planMenus;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa el objeto observable
     * {@link MutableLiveData} con una lista vacía de menus.
     */
    public PlanMenuRepositorio() {
        planMenus = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Devuelve el objeto observable que contiene la lista de menus.
     * @return - Devuelve el objeto {@link MutableLiveData} que contiene la lista actual de {@link PlanMenu}.
     */
    public MutableLiveData<List<PlanMenu>> getPlanMenus() {
        return planMenus;
    }

    /**
     * Devuelve un LiveData filtrado por orden.
     * @param orden - int - orden del menú.
     * @return - Devuelve un objeto {@link LiveData} que contiene la lista filtrada de {@link PlanMenu}.
     */
    public LiveData<List<PlanMenu>> getPlanMenusByOrden(int orden) {
        return Transformations.map(planMenus, listaCompleta -> {
            List<PlanMenu> filtrada = new ArrayList<>();
            if (listaCompleta != null) {
                for (PlanMenu pm : listaCompleta) {
                    if (pm.getOrden() == orden) {
                        filtrada.add(pm);
                    }
                }
            }
            return filtrada;
        });
    }

    /**
     * Establece la lista de menus en el objeto observable {@link MutableLiveData}.
     * @param listaPlanMenus - La nueva lista de {@link PlanMenu} a establecer.
     */
    public void setListaPlanMenus(List<PlanMenu> listaPlanMenus) {
        // Aseguramos que pasamos una copia para que LiveData detecte el cambio de estado
        planMenus.setValue(new ArrayList<>(listaPlanMenus));
    }

    /**
     * Añade un nuevo menu a la lista actual.
     * @param planMenu - El objeto {@link PlanMenu} que se desea añadir.
     */
    public void addPlanMenu(PlanMenu planMenu) {
        List<PlanMenu> listaActual = planMenus.getValue();
        List<PlanMenu> nuevaLista = (listaActual != null) ? new ArrayList<>(listaActual) : new ArrayList<>();
        nuevaLista.add(planMenu);
        planMenus.setValue(nuevaLista);
    }

    /**
     * Elimina un menu de la lista actual.
     * @param planMenu - El objeto {@link PlanMenu} que se desea eliminar.
     */
    public void removePlanMenu(PlanMenu planMenu) {
        List<PlanMenu> listaActual = planMenus.getValue();
        if (listaActual != null) {
            List<PlanMenu> nuevaLista = new ArrayList<>(listaActual);
            if (nuevaLista.remove(planMenu)) {
                planMenus.setValue(nuevaLista);
            }
        }
    }

    /**
     * Limpia la lista completa de menus, dejándola vacía.
     */
    public void clear() {
        planMenus.setValue(new ArrayList<>());
    }
}
