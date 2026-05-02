package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderMenusBinding;
import com.iesaguadulce.mirecetariodecocina.room.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para gestionar y mostrar una lista de menús en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada menú y vincular
 * los datos de la entidad {@link Menu} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenusViewHolder
 */
public class MenusAdapter extends RecyclerView.Adapter<MenusViewHolder> {

    /** Lista de objetos {@link Menu} que se mostrarán en la interfaz. */
    private List<Menu> menus = new ArrayList<>();

    /** Copia de seguridad para el filtro de la lista de menús. */
    private List<Menu> menusFull = new ArrayList<>();

    /** Listener para gestionar los eventos de borrado de menús. */
    private OnMenuDeleteListener deleteListener;

    /** Listener para gestionar los eventos de contenido de menús. */
    private OnMenuViewContentListener contentListener;

    /** Listener para gestionar los eventos de añadir un menú a un plan. */
    private OnAddMenuPlanListener addMenuPlanListener;

    /** Indica si se debe mostrar el botón de eliminación o no. */
    private boolean showDeleteButton = true;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnMenuDeleteListener {
        void onMenuDeleted(Menu menu);
    }

    /**
     * Interfaz para la comunicación de eventos de contenido desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnMenuViewContentListener {
        void onMenuViewContent(Menu menu);
    }

    /**
     * Interfaz para la comunicación de eventos de añadir un menú a un plan desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnAddMenuPlanListener {
        void onAddMenuPlan(Menu menu);
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
     * Establece el listener para los eventos de contenido.
     *
     * @param listener - Implementación de {@link OnMenuViewContentListener}.
     */
    public void setOnMenuViewContentListener(OnMenuViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Establece el listener para los eventos de añadir un menú a un plan.
     *
     * @param listener - Implementación de {@link OnAddMenuPlanListener}.
     */
    public void setOnAddMenuPlanListener(OnAddMenuPlanListener listener) {
        this.addMenuPlanListener = listener;
    }

    /**
     * Establece la visibilidad del botón de eliminación.
     *
     * @param showDeleteButton - boolean - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
    }

    /**
     * Recibe la lista de menús a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link MenusViewHolder} - Devuelve el ViewHolder para el menú.
     */
    @NonNull
    @Override
    public MenusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenusViewHolder(
                ViewholderMenusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un menú con la vista del ViewHolder.
     *
     * @param holder   - {@link MenusViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull MenusViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.bind(menu, deleteListener, showDeleteButton);
        holder.bind(menu, contentListener, addMenuPlanListener);

        String info = menu.getTipo() + " . " + menu.getEtiqueta();
        holder.binding.menuName.setText(menu.getNombre());
        holder.binding.menuDescription.setText(menu.getDescripcion());
        holder.binding.menuinfo.setText(info);
        holder.binding.menuContent.setTag("Contenido");
        holder.binding.btnEliminate.setTag("Eliminar");
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de menús en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return menus != null ? menus.size() : 0;
    }

    /**
     * Actualiza la lista de menús del adaptador y notifica los cambios.
     *
     * @param menus - {@link List}<{@link Menu}> - Nueva lista de menús a mostrar.
     */
    public void setMenus(List<Menu> menus) {
        if (menus == null) {
            this.menus = new ArrayList<>();
            this.menusFull = new ArrayList<>();
        } else {
            // 1. Guardamos la copia de seguridad para el filtro
            this.menusFull = new ArrayList<>(menus);
            // 2. Actualizamos la lista que se ve
            this.menus = menus;
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de menús según el texto de búsqueda.
     *
     * @param query - String - Texto de búsqueda.
     */
    public void filter(String query) {
        if (menusFull == null) return;

        if (query == null || query.isEmpty()) {
            menus = new ArrayList<>(menusFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            menus = menusFull.stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                            m.getTipo().toLowerCase().contains(lowerCaseQuery) ||
                            m.getEtiqueta().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }

}
