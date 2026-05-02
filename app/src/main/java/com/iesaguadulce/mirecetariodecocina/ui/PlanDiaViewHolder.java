package com.iesaguadulce.mirecetariodecocina.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderDiasBinding;
import com.iesaguadulce.mirecetariodecocina.model.PlanDia;

/**
 * ViewHolder para la representación visual de un día individual (Diario) de un plan {@link PlanDia} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanDiaAdapter
 */
public class PlanDiaViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del día. */
    ViewholderDiasBinding binding;

    /** Referencia al objeto de datos del {@link PlanDia} que se está mostrando actualmente. */
    private PlanDia diaActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderDiasBinding} con las vistas del ítem.
     */
    public PlanDiaViewHolder(ViewholderDiasBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    /**
     * Vincula los datos de un día (diario) de un plan {@link PlanDia} con la vista y configura los eventos de interacción para el
     * contenido del día.
     *
     * @param dia      - El objeto {@link PlanDia} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de contenido del día.
     */
    public void bind(PlanDia dia, PlanDiaAdapter.OnPlanDiaViewContentListener listener) {
        this.diaActual = dia;

        binding.diasContent.setOnClickListener(v -> {
            //Toast.makeText(v.getContext(), "Contenido: " + dia.getDia() + "("+dia.getOrden()+")", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onPlanDiaViewContent(diaActual);
            }
        });
    }

}
