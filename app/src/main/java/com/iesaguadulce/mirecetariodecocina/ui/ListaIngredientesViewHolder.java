package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaIngredientesBinding;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;

/**
 * ViewHolder para la representación visual de un ingrediente individual de la receta {@link RecetaIngrediente} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaIngredientesAdapter
 */
public class ListaIngredientesViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del ingrediente de la receta. */
    ViewholderListaIngredientesBinding binding;

    /** Referencia al objeto de datos del {@link RecetaIngrediente} que se está mostrando actualmente. */
    private RecetaIngrediente ingredienteActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderListaIngredientesBinding} con las vistas del ítem.
     */
    public ListaIngredientesViewHolder(@NonNull ViewholderListaIngredientesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    /**
     * Vincula los datos de un ingrediente de la receta {@link RecetaIngrediente} con la vista y configura los eventos de interacción para el
     * borrado del ingrediente de la receta.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param recetaIngrediente - El objeto {@link RecetaIngrediente} cuyos datos se van a mostrar.
     * @param listener          - El listener para manejar la acción de borrado del ingrediente.
     */
    public void bind(RecetaIngrediente recetaIngrediente, ListaIngredientesAdapter.OnIngredienteDeleteListener listener) {
        this.ingredienteActual = recetaIngrediente;

        binding.btnEliminate.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                .setTitle("Borrar ingrediente")
                .setMessage("¿Desea eliminar el ingrediente " + recetaIngrediente.getNombre() + "?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (listener != null) {
                        listener.onIngredienteDeleted(recetaIngrediente);
                    }
                })
                .show();
        });
    }

}
