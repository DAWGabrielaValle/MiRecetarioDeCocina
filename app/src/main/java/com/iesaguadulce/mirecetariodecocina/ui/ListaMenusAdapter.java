package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderListaMenuBinding;
import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de menús en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada menú y vincular
 * los datos de la entidad {@link PlanMenu} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaMenusViewHolder
 */
public class ListaMenusAdapter extends RecyclerView.Adapter<ListaMenusViewHolder>{

    /** Lista de objetos {@link PlanMenu} que se mostrarán en la interfaz. */
    private List<PlanMenu> listaMenus;

    /** Listener para gestionar los eventos de borrado de menús. */
    private OnMenuDeleteListener deleteListener;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnMenuDeleteListener {
        void onMenuDeleted(PlanMenu planMenu);
    }

    /**
     * Establece el listener para los eventos de borrado.
     *
     * @param listener - Implementación de {@link OnMenuDeleteListener}.
     */
    public void setOnMenuDeleteListener(OnMenuDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Recibe la lista de menús a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link ListaMenusViewHolder} - Devuelve el ViewHolder para el menú.
     */
    @NonNull
    @Override
    public ListaMenusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaMenusViewHolder(
                ViewholderListaMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un menú con la vista del ViewHolder.
     *
     * @param holder   - {@link ListaMenusViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ListaMenusViewHolder holder, int position) {
        PlanMenu planMenu = listaMenus.get(position);
        holder.bind(planMenu, deleteListener);

        holder.binding.menuName.setText(planMenu.getNombre());
        holder.binding.menuDescription.setText(planMenu.getDescripcion());
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de menús en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return listaMenus != null ? listaMenus.size() : 0;
    }

    /**
     * Actualiza la lista de menús del adaptador y notifica los cambios.
     *
     * @param listaMenus - {@link List}<{@link PlanMenu}> - Nueva lista de menús a mostrar.
     */
    public void setMenus(List<PlanMenu> listaMenus) {
        this.listaMenus = listaMenus;
        notifyDataSetChanged();
    }

}
