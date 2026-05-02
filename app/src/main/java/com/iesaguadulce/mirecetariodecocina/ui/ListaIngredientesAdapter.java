package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaIngredientesBinding;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de ingredientes de una receta en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada ingrediente de la receta y vincular
 * los datos de la entidad {@link RecetaIngrediente} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaIngredientesViewHolder
 */
public class ListaIngredientesAdapter extends RecyclerView.Adapter<ListaIngredientesViewHolder> {

    /** Lista de objetos {@link RecetaIngrediente} que se mostrarán en la interfaz. */
    private List<RecetaIngrediente> listaIngredientes;

    /** Listener para gestionar los eventos de borrado de ingredientes de la receta. */
    private OnIngredienteDeleteListener deleteListener;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnIngredienteDeleteListener {
        void onIngredienteDeleted(RecetaIngrediente recetaIngrediente);
    }

    /**
     * Establece el listener para los eventos de borrado.
     * @param listener - Implementación de {@link OnIngredienteDeleteListener}.
     */
    public void setOnIngredienteDeleteListener(OnIngredienteDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Recibe la lista de ingredientes de la receta a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link ListaIngredientesViewHolder} - Devuelve el ViewHolder para el ingrediente de la receta.
     */
    @NonNull
    @Override
    public ListaIngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaIngredientesViewHolder(
                ViewholderListaIngredientesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un ingrediente de la receta con la vista del ViewHolder.
     *
     * @param holder   - {@link ListaIngredientesViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ListaIngredientesViewHolder holder, int position) {
        RecetaIngrediente recetaIngrediente = listaIngredientes.get(position);
        holder.bind(recetaIngrediente, deleteListener);

        String info = "Cantidad: "+ recetaIngrediente .getCantidad() + " " + recetaIngrediente.getUnidad();
        holder.binding.ingredientName.setText(recetaIngrediente.getNombre());
        holder.binding.ingredientinfo.setText(info);
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de ingredientes de la receta en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return listaIngredientes != null ? listaIngredientes.size() : 0;
    }

    /**
     * Actualiza la lista de ingredientes de la receta del adaptador y notifica los cambios.
     *
     * @param listaIngredientes - {@link List}<{@link RecetaIngrediente}> - Nueva lista de ingredientes a mostrar.
     */
    public void setIngredientes(List<RecetaIngrediente> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
        notifyDataSetChanged();
    }
}
