package com.iesaguadulce.mirecetariodecocina.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaCompraBinding;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;

/**
 * ViewHolder para la representación visual de un ingrediente individual de la lista de compra {@link RecetaIngrediente} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaCompraAdapter
 */
public class ListaCompraViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del ingrediente de la lista de compra. */
    ViewholderListaCompraBinding binding;

    /** Referencia al objeto de datos del {@link RecetaIngrediente} que se está mostrando actualmente. */
    private RecetaIngrediente ingredienteActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderListaCompraBinding} con las vistas del ítem.
     */
    public ListaCompraViewHolder(@NonNull ViewholderListaCompraBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
