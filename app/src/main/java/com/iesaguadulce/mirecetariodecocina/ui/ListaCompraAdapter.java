package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaCompraBinding;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista la compra de ingredientes en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada ingrediente en la lista de la compra y vincular
 * los datos de la entidad {@link RecetaIngrediente} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaCompraViewHolder
 */
public class ListaCompraAdapter extends RecyclerView.Adapter<ListaCompraViewHolder> {

    /** Lista de objetos {@link RecetaIngrediente} que se mostrarán en la interfaz. */
    private List<RecetaIngrediente> listaIngredientes;

    /**
     * Recibe la lista de la compra de ingredientes a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link ListaCompraViewHolder} - Devuelve el ViewHolder para el ingrediente de la receta.
     */
    @NonNull
    @Override
    public ListaCompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaCompraViewHolder(
                ViewholderListaCompraBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un ingrediente de la lista de la compra con la vista del ViewHolder.
     *
     * @param holder   - {@link ListaCompraViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ListaCompraViewHolder holder, int position) {
        RecetaIngrediente recetaIngrediente = listaIngredientes.get(position);
        String info = recetaIngrediente.getCantidad() + " " + recetaIngrediente.getUnidad();
        holder.binding.ingredientName.setText(recetaIngrediente.getNombre());
        holder.binding.ingredientinfo.setText(info);
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de ingredientes en la lista de la compra, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return listaIngredientes != null ? listaIngredientes.size() : 0;
    }

    /**
     * Actualiza la lista de la compra de ingredientes del adaptador y notifica los cambios.
     *
     * @param listaIngredientes - {@link List}<{@link RecetaIngrediente}> - Nueva lista de la compra de ingredientes a mostrar.
     */
    public void setIngredientes(List<RecetaIngrediente> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
        notifyDataSetChanged();
    }

}
