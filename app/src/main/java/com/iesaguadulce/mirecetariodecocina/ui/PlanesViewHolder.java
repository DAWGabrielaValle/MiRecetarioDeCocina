package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderPlanesBinding;
import com.iesaguadulce.mirecetariodecocina.room.Plan;

/**
 * ViewHolder para representar un {@link Plan} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanesAdapter
 */
public class PlanesViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del plan. */
    ViewholderPlanesBinding binding;

    /** Referencia al objeto de datos del {@link Plan} que se está mostrando actualmente. */
    private Plan planActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderPlanesBinding} con las vistas del ítem.
     */
    public PlanesViewHolder(@NonNull ViewholderPlanesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    /**
     * Vincula los datos de un {@link Plan} con la vista y configura los eventos de interacción para el
     * borrado del plan.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param plan     - El objeto {@link Plan} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de borrado del plan.
     */
    public void bind(Plan plan, PlanesAdapter.OnPlanDeleteListener listener) {
        this.planActual = plan;

        binding.btnEliminate.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Borrar plan")
                    .setMessage("¿Desea eliminar el plan " + plan.getNombre() + "?")
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        if (listener != null) {
                            listener.onPlanDeleted(planActual);
                        }
                    })
                    .show();
        });
    }

    /**
     * Vincula los datos de un {@link Plan} con la vista y configura los eventos de interacción para el
     * contenido del plan.
     * <p>
     * Controla el comportamiento al dar clic en el contenido del plan en el fragmento {@link com.iesaguadulce.mirecetariodecocina.PlanChefFragment}
     * mostrando la vista para la edición del plan.
     * </p>
     *
     * @param plan     - El objeto {@link Plan} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de contenido del plan.
     */
    public void bind(Plan plan, PlanesAdapter.OnPlanViewContentListener listener) {
        this.planActual = plan;

        binding.planContent.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPlanViewContent(planActual);
            }
        });
    }

}
