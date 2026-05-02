package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;

import androidx.recyclerview.widget.RecyclerView;
import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaRecetasBinding;
import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;

/**
 * ViewHolder para la representación visual de una receta individual de un menú {@link MenuReceta} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaRecetasAdapter
 */
public class ListaRecetasViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista de la receta. */
    ViewholderListaRecetasBinding binding;

    /** Referencia al objeto de datos del {@link MenuReceta} que se está mostrando actualmente. */
    private MenuReceta recetaActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderListaRecetasBinding} con las vistas del ítem.
     */
    public ListaRecetasViewHolder(ViewholderListaRecetasBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de una receta de un menú {@link MenuReceta} con la vista y configura los eventos de interacción para el
     * borrado de la receta.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param receta   - El objeto {@link MenuReceta} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de borrado de la receta.
     */
    public void bind(MenuReceta receta, ListaRecetasAdapter.OnRecetaDeleteListener listener) {
        this.recetaActual = receta;

        binding.btnEliminate.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                .setTitle("Borrar receta")
                .setMessage("¿Desea eliminar la receta " + receta.getNombre() + "?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (listener != null) {
                        listener.onRecetaDeleted(receta);
                    }
                })
                .show();
        });

    }



}
