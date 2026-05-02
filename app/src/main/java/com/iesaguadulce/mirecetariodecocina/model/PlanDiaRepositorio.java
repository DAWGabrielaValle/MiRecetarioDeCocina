package com.iesaguadulce.mirecetariodecocina.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para la gestión temporal de los días de un plan.
 * <p>
 * Esta clase actúa como un contenedor reactivo utilizando {@link MutableLiveData},
 * permitiendo que los componentes de la interfaz de usuario observen cambios en tiempo
 * real cuando se añaden o eliminan días durante el proceso de creación o edición.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class PlanDiaRepositorio {
    /** Objeto observable que contiene la lista de días. */
    private MutableLiveData<List<PlanDia>> dias;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa el objeto observable
     * {@link MutableLiveData} con una lista vacía de días.
     */
    public PlanDiaRepositorio() {
        dias = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Devuelve el objeto observable que contiene la lista de días.
     * @return - Devuelve el objeto {@link MutableLiveData} que contiene la lista actual de {@link PlanDia}.
     */
    public MutableLiveData<List<PlanDia>> getAllDias() {
        return dias;
    }

    /**
     * Añade un nuevo día a la lista de días en el objeto observable {@link MutableLiveData}.
     * @param dia - El objeto {@link PlanDia} que se desea añadir.
     */
    public void addDia(PlanDia dia) {
        List<PlanDia> listaDias = dias.getValue();
        if (listaDias == null) {
            listaDias = new ArrayList<>();
        }
        listaDias.add(dia);
        dias.setValue(listaDias);
    }

    /**
     * Limpia la lista completa de días, dejándola vacía.
     */
    public void clear() {
        dias.setValue(new ArrayList<>());
    }

}
