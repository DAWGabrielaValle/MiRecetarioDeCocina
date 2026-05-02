package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderRecetasBinding;
import com.iesaguadulce.mirecetariodecocina.room.Receta;

/**
 * ViewHolder para representar una {@link Receta} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetasAdapter
 */
public class RecetasViewHolder extends RecyclerView.ViewHolder  {

    /** Binding para acceder a los componentes de la vista de la receta. */
    ViewholderRecetasBinding binding;

    /** Referencia al objeto de datos de la {@link Receta} que se está mostrando actualmente. */
    private Receta recetaActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderRecetasBinding} con las vistas del ítem.
     */
    public RecetasViewHolder(@NonNull ViewholderRecetasBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de una {@link Receta} con la vista y configura los eventos de interacción para el
     * borrado de la receta.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param receta     - El objeto {@link Receta} cuyos datos se van a mostrar.
     * @param listener   - El listener para manejar la acción de borrado de la receta.
     * @param showDelete - boolean - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void bind(Receta receta, RecetasAdapter.OnRecetaDeleteListener listener, boolean showDelete) {
        this.recetaActual = receta;

        if (showDelete) {
            binding.btnEliminate.setVisibility(View.VISIBLE);
            binding.btnEliminate.setEnabled(true);
            binding.btnEliminate.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Borrar receta")
                        .setMessage("¿Desea eliminar la receta " + receta.getNombre() + "?")
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            if (listener != null) {
                               listener.onRecetaDeleted(recetaActual);
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
     * Vincula los datos de una {@link Receta} con la vista y configura los eventos de interacción para el
     * contenido de la receta y añadir una receta a un menú.
     * <p>
     * Controla si al dar clic en el contenido de la receta en el fragmento {@link com.iesaguadulce.mirecetariodecocina.InicioChefFragment}
     * o en el fragmento {@link com.iesaguadulce.mirecetariodecocina.SeleccionRecetasFragment} se muestra la vista para
     * la edición de la receta (contentlistener) o para añadir una nueva receta a un menú respectivamente (addlistener).
     * </p>
     *
     * @param receta          - El objeto {@link Receta} cuyos datos se van a mostrar.
     * @param contentlistener - El listener para manejar la acción de contenido de la receta.
     * @param addlistener     - El listener para manejar la acción de añadir una nueva receta a un menú.
     */
    public void bind(Receta receta,
                     RecetasAdapter.OnRecetaViewContentListener contentlistener,
                     RecetasAdapter.OnAddRecetaMenuListener addlistener) {
        this.recetaActual = receta;

        binding.recipeContent.setOnClickListener(v -> {
            //Toast.makeText(v.getContext(), "Contenido: " + receta.getNombre() + "("+receta.getIdReceta()+")", Toast.LENGTH_SHORT).show();
            if (contentlistener != null) {
                contentlistener.onRecetaViewContent(recetaActual);
            } else if (addlistener != null) {
                addlistener.onAddRecetaMenu(recetaActual);
            }
        });
    }
}
