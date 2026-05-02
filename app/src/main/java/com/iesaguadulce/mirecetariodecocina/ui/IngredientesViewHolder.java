package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderIngredientesBinding;
import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;

/**
 * ViewHolder para la representación visual de un {@link Ingrediente} individual en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredientesAdapter
 */
public class IngredientesViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del ingrediente. */
    ViewholderIngredientesBinding binding;

    /** Referencia al objeto de datos del {@link Ingrediente} que se está mostrando actualmente. */
    private Ingrediente ingredienteActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderIngredientesBinding} con las vistas del ítem.
     */
    public IngredientesViewHolder(@NonNull ViewholderIngredientesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de un {@link Ingrediente} con la vista y configura los eventos de interacción para el
     * borrado del ingrediente.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param ingrediente - El objeto {@link Ingrediente} cuyos datos se van a mostrar.
     * @param listener    - El listener para manejar la acción de borrado del ingrediente.
     * @param showDelete  - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void bind(Ingrediente ingrediente, IngredientesAdapter.OnIngredienteDeleteListener listener, boolean showDelete) {
        this.ingredienteActual = ingrediente;

        if (showDelete) {
            binding.btnEliminate.setVisibility(View.VISIBLE);
            binding.btnEliminate.setEnabled(true);
            binding.btnEliminate.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                    .setTitle("Borrar ingrediente")
                    .setMessage("¿Desea eliminar el ingrediente " + ingrediente.getNombre() + "?")
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        if (listener != null) {
                            listener.onIngredienteDeleted(ingredienteActual);
                        }
                    })
                    .show();
            });
        } else {
            binding.btnEliminate.setVisibility(View.GONE);
            binding.btnEliminate.setEnabled(false);
        }
    }

    /**
     * Vincula los datos de un {@link Ingrediente} con la vista y configura los eventos de interacción para el
     * contenido del ingrediente y la edición del ingrediente.
     * <p>
     * Controla si al dar clic en el contenido del Ingrediente en el fragmento {@link com.iesaguadulce.mirecetariodecocina.IngredientChefFragment}
     * o en el fragmento {@link com.iesaguadulce.mirecetariodecocina.SeleccionIngredientesFragment} se muestra la vista para
     * la edición del ingrediente (contentlistener) o para añadir un nuevo ingrediente a la receta respectivamente (editlistener).
     * </p>
     *
     * @param ingrediente     - El objeto {@link Ingrediente} cuyos datos se van a mostrar.
     * @param contentlistener - El listener para manejar la acción de edición del ingrediente.
     * @param editlistener    - El listener para manejar la acción de añadir un nuevo ingrediente a la receta.
     */
    public void bind(Ingrediente ingrediente,
                     IngredientesAdapter.OnIngredienteViewContentListener contentlistener,
                     IngredientesAdapter.OnRecetaIngredienteEditListener editlistener) {
        this.ingredienteActual = ingrediente;

        binding.ingredientContent.setOnClickListener(v -> {
            if (contentlistener != null) {
                contentlistener.onIngredienteViewContent(ingrediente);
            } else if (editlistener != null) {
                editlistener.onRecetaIngredienteEdit(ingrediente);
            }
        });
    }
}
