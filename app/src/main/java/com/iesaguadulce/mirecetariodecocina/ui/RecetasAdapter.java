package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.R;
import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderRecetasBinding;
import com.iesaguadulce.mirecetariodecocina.room.Receta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para gestionar y mostrar una lista de recetas en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada receta y vincular
 * los datos de la entidad {@link Receta} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetasViewHolder
 */
public class RecetasAdapter extends RecyclerView.Adapter<RecetasViewHolder> {

    /** Lista de objetos {@link Receta} que se mostrarán en la interfaz. */
    private List<Receta> recetas = new ArrayList<>();

    /** Copia de seguridad para el filtro de la lista de recetas. */
    private List<Receta> recetasFull = new ArrayList<>();

    /** Listener para gestionar los eventos de borrado de recetas. */
    private OnRecetaDeleteListener deleteListener;

    /** Listener para gestionar los eventos de contenido de recetas. */
    private OnRecetaViewContentListener contentListener;

    /** Listener para gestionar los eventos de añadir una receta a un menú. */
    private OnAddRecetaMenuListener addRecetaMenuListener;

    /** Indica si se debe mostrar el botón de eliminación o no. */
    private boolean showDeleteButton = true;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnRecetaDeleteListener {
        void onRecetaDeleted(Receta receta);
    }

    /**
     * Interfaz para la comunicación de eventos de contenido desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnRecetaViewContentListener {
        void onRecetaViewContent(Receta receta);
    }

    /**
     * Interfaz para la comunicación de eventos de añadir una receta a un menú desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnAddRecetaMenuListener {
        void onAddRecetaMenu(Receta receta);
    }

    /**
     * Establece el listener para los eventos de borrado.
     *
     * @param listener - Implementación de {@link OnRecetaDeleteListener}.
     */
    public void setOnRecetaDeleteListener(OnRecetaDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Establece el listener para los eventos de contenido.
     *
     * @param listener - Implementación de {@link OnRecetaViewContentListener}.
     */
    public void setOnRecetaViewContentListener(OnRecetaViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Establece el listener para los eventos de añadir una receta a un menú.
     *
     * @param listener - Implementación de {@link OnAddRecetaMenuListener}.
     */
    public void setOnAddRecetaMenuListener(OnAddRecetaMenuListener listener) {
        this.addRecetaMenuListener = listener;
    }

    /**
     * Establece si se debe mostrar el botón de eliminación o no.
     *
     * @param showDeleteButton - boolean - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
    }

    /**
     * Recibe la lista de recetas a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link RecetasViewHolder} - Devuelve el ViewHolder para representar un {@link Receta} en un RecyclerView.
     */
    @NonNull
    @Override
    public RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecetasViewHolder(
                ViewholderRecetasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de una receta con la vista del ViewHolder.
     *
     * @param holder   - {@link RecetasViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull RecetasViewHolder holder, int position) {
        Receta receta = recetas.get(position);
        holder.bind(receta, deleteListener, showDeleteButton);
        holder.bind(receta, contentListener, addRecetaMenuListener);

        String info = "Tiempo de preparación · " + receta.getTiempoPrep() + "min";
        holder.binding.recipeName.setText(receta.getNombre());
        holder.binding.recipeDescription.setText(receta.getDescripcion());
        holder.binding.recipeTime.setText(info);

        if (receta.getImagen() != null && !receta.getImagen().isEmpty()) {
            // Declaramos resID como int y usamos requireContext() para mayor seguridad
            int resID = holder.itemView.getContext().getResources().getIdentifier(
                    receta.getImagen(),
                    "drawable",
                    holder.itemView.getContext().getPackageName()
            );

            // Si resID es 0, significa que no se encontró la imagen en drawable
            if (resID != 0) {
                holder.binding.recipeImage.setImageResource(resID);
            } else {
                // Opcional: poner una imagen por defecto si no existe la indicada
                holder.binding.recipeImage.setImageResource(R.drawable.outline_image_24);
            }
        }

        holder.binding.recipeContent.setTag("Contenido");
        holder.binding.btnEliminate.setTag("Eliminar");
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de recetas en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return recetas != null ? recetas.size() : 0;
    }

    /**
     * Actualiza la lista de recetas del adaptador y notifica los cambios.
     *
     * @param recetas - {@link List}<{@link Receta}> - Nueva lista de recetas a mostrar.
     */
    public void setRecetas(List<Receta> recetas) {
        if (recetas == null) {
            this.recetas = new ArrayList<>();
            this.recetasFull = new ArrayList<>();
        } else {
            // 1. Guardamos la copia de seguridad para el filtro
            this.recetasFull = new ArrayList<>(recetas);
            // 2. Actualizamos la lista que se ve
            this.recetas = recetas;
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de recetas según el texto de búsqueda.
     *
     * @param query - String - Texto de búsqueda.
     */
    public void filter(String query) {
        if (recetasFull == null) return;

        if (query == null || query.isEmpty()) {
            recetas = new ArrayList<>(recetasFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            recetas = recetasFull.stream()
                    .filter(r -> r.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                            (r.getTiempoPrep()+"min").toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();

    }
}
