package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaRecetasBinding;
import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de recetas en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada receta y vincular
 * los datos de la entidad {@link MenuReceta} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaRecetasViewHolder
 */
public class ListaRecetasAdapter extends RecyclerView.Adapter<ListaRecetasViewHolder>{

    /** Lista de objetos {@link MenuReceta} que se mostrarán en la interfaz. */
    private List<MenuReceta> listaRecetas;

    /** Listener para gestionar los eventos de borrado de recetas. */
    private OnRecetaDeleteListener deleteListener;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnRecetaDeleteListener {
        void onRecetaDeleted(MenuReceta receta);
    }

    /**
     * Establece el listener para los eventos de borrado.
     * @param listener - Implementación de {@link OnRecetaDeleteListener}.
     */
    public void setOnRecetaDeleteListener(OnRecetaDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Recibe la lista de recetas a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link ListaRecetasViewHolder} - Devuelve el ViewHolder para la receta.
     */
    @NonNull
    @Override
    public ListaRecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaRecetasViewHolder(
                ViewholderListaRecetasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );

    }

    /**
     * Vincula los datos de una receta con la vista del ViewHolder.
     *
     * @param holder   - {@link ListaRecetasViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ListaRecetasViewHolder holder, int position) {
        MenuReceta menuReceta = listaRecetas.get(position);
        holder.bind(menuReceta, deleteListener);

        holder.binding.recipeName.setText(menuReceta.getNombre());
        holder.binding.recipeDescription.setText(menuReceta.getDescripcion());
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de recetas en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return listaRecetas != null ? listaRecetas.size() : 0;
    }

    /**
     * Actualiza la lista de recetas del adaptador y notifica los cambios.
     *
     * @param listaRecetas - {@link List}<{@link MenuReceta}> - Nueva lista de recetas a mostrar.
     */
    public void setRecetas(List<MenuReceta> listaRecetas) {
        this.listaRecetas = listaRecetas;
        notifyDataSetChanged();
    }

}
