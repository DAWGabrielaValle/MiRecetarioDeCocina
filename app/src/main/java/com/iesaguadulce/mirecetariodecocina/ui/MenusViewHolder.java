package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderMenusBinding;
import com.iesaguadulce.mirecetariodecocina.room.Menu;

/**
 * ViewHolder para la representación visual de un {@link Menu} individual en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenusAdapter
 */
public class MenusViewHolder extends RecyclerView.ViewHolder  {

    /** Binding para acceder a los componentes de la vista del menú. */
    ViewholderMenusBinding binding;

    /** Referencia al objeto de datos del {@link Menu} que se está mostrando actualmente. */
    private Menu menuActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderMenusBinding} con las vistas del ítem.
     */
    public MenusViewHolder(@NonNull ViewholderMenusBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de un {@link Menu} con la vista y configura los eventos de interacción para el
     * borrado del menú.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param menu       - El objeto {@link Menu} cuyos datos se van a mostrar.
     * @param listener   - El listener para manejar la acción de borrado del menú.
     * @param showDelete - boolean - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void bind(Menu menu, MenusAdapter.OnMenuDeleteListener listener, boolean showDelete) {
        this.menuActual = menu;

        if (showDelete) {
            binding.btnEliminate.setVisibility(View.VISIBLE);
            binding.btnEliminate.setEnabled(true);
            binding.btnEliminate.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Borrar menú")
                        .setMessage("¿Desea eliminar el menú " + menu.getNombre() + "?")
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            if (listener != null) {
                                listener.onMenuDeleted(menuActual);
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
     * Vincula los datos de un {@link Menu} con la vista y configura los eventos de interacción para el
     * contenido del menú y añadir un menú a un plan
     * <p>
     * Controla si al dar clic en el contenido del menú en el fragmento {@link com.iesaguadulce.mirecetariodecocina.MenuChefFragment}
     * o en el fragmento {@link com.iesaguadulce.mirecetariodecocina.SeleccionMenusFragment} se muestra la vista para
     * la edición del menú (contentlistener) o para añadir un nuevo menú al diario de un plan respectivamente (addlistener).
     * </p>
     *
     * @param menu            - El objeto {@link Menu} cuyos datos se van a mostrar.
     * @param contentlistener - El listener para manejar la acción de edición del menú.
     * @param addlistener     - El listener para manejar la acción de añadir un nuevo menú a un diario de un plan.
     */
    public void bind(Menu menu,
                     MenusAdapter.OnMenuViewContentListener contentlistener,
                     MenusAdapter.OnAddMenuPlanListener addlistener) {
        this.menuActual = menu;

        binding.menuContent.setOnClickListener(v -> {
            if (contentlistener != null) {
                contentlistener.onMenuViewContent(menuActual);
            } else if (addlistener != null) {
                addlistener.onAddMenuPlan(menuActual);
            }
        });
    }

}
