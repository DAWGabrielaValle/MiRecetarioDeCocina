package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaMenuBinding;
import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;

/**
 * ViewHolder para la representación visual de un menú individual del diario de un plan {@link PlanMenu} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaMenusAdapter
 */
public class ListaMenusViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del menú. */
    ViewholderListaMenuBinding binding;

    /** Referencia al objeto de datos del {@link PlanMenu} que se está mostrando actualmente. */
    private PlanMenu menuActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderListaMenuBinding} con las vistas del ítem.
     */
    public ListaMenusViewHolder(@NonNull ViewholderListaMenuBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de un menú de un diario de un plan {@link PlanMenu} con la vista y configura los eventos de interacción para el
     * borrado del menú.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param menu     - El objeto {@link PlanMenu} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de borrado del menú.
     */
    public void bind(PlanMenu menu, ListaMenusAdapter.OnMenuDeleteListener listener) {
        this.menuActual = menu;

        binding.btnEliminate.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                .setTitle("Borrar menú")
                .setMessage("¿Desea eliminar el menú " + menu.getNombre() + "?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (listener != null) {
                        listener.onMenuDeleted(menu);
                    }
                })
                .show();
        });
    }
}
