package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.R;
import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderIngredientesBinding;
import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para gestionar y mostrar una lista de ingredientes en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada ingrediente y vincular
 * los datos de la entidad {@link Ingrediente} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredientesViewHolder
 */
public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesViewHolder> {

    /** Copia de seguridad para el filtro de la lista de objetos {@link Ingrediente} que se mostrarán en la interfaz. */
    private List<Ingrediente> ingredientesFull = new ArrayList<>();

    /** Lista de objetos {@link Ingrediente} que se mostrarán en la interfaz. */
    private List<Ingrediente> ingredientes = new ArrayList<>();

    /** Listener para gestionar los eventos de borrado de ingredientes. */
    private OnIngredienteDeleteListener deleteListener;

    /** Listener para gestionar los eventos de contenido de ingredientes. */
    private OnIngredienteViewContentListener contentListener;

    /** Listener para gestionar los eventos de edición de ingredientes. */
    private OnRecetaIngredienteEditListener recetaIngredienteListener;

    /** Indica si se debe mostrar el botón de eliminación o no. */
    private boolean showDeleteButton = true;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnIngredienteDeleteListener {
        void onIngredienteDeleted(Ingrediente ingrediente);
    }

    /**
     * Interfaz para la comunicación de eventos de contenido desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnIngredienteViewContentListener {
        void onIngredienteViewContent(Ingrediente ingrediente);
    }

    /**
     * Interfaz para la comunicación de eventos de edición desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnRecetaIngredienteEditListener {
        void onRecetaIngredienteEdit(Ingrediente ingrediente);
    }

    /**
     * Establece el listener para los eventos de borrado.
     * @param listener - Implementación de {@link OnIngredienteDeleteListener}.
     */
    public void setOnIngredienteDeleteListener(OnIngredienteDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Establece el listener para los eventos de contenido.
     * @param listener - Implementación de {@link OnIngredienteViewContentListener}.
     */
    public void setOnIngredienteViewContentListener(OnIngredienteViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Establece el listener para los eventos de edición.
     * @param listener - Implementación de {@link OnRecetaIngredienteEditListener}.
     */
    public void setOnRecetaIngredienteEditListener(OnRecetaIngredienteEditListener listener) {
        this.recetaIngredienteListener = listener;
    }

    /**
     * Establece si se debe mostrar el botón de eliminación o no.
     * @param showDeleteButton - boolean - Indica si se debe mostrar el botón de eliminación o no.
     */
    public void setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
    }

    /**
     * Recibe la lista de ingredientes a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link IngredientesViewHolder} - Devuelve el ViewHolder para el ingrediente.
     */
    @NonNull
    @Override
    public IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientesViewHolder(
                ViewholderIngredientesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un ingrediente con la vista del ViewHolder.
     *
     * @param holder   - {@link IngredientesViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientesViewHolder holder, int position) {
        Ingrediente ingrediente = ingredientes.get(position);
        holder.bind(ingrediente, deleteListener, showDeleteButton);
        holder.bind(ingrediente, contentListener, recetaIngredienteListener);

        String info = ingrediente.getFamilia() + " · " + ingrediente.getEtiqueta();
        holder.binding.ingredientName.setText(ingrediente.getNombre());
        holder.binding.ingredientDescription.setText(ingrediente.getDescripcion());
        holder.binding.ingredientinfo.setText(info);

        if (ingrediente.getImagen() != null && !ingrediente.getImagen().isEmpty()) {
            // Declaramos resID como int y usamos requireContext() para mayor seguridad
            int resID = holder.itemView.getContext().getResources().getIdentifier(
                    ingrediente.getImagen(),
                    "drawable",
                    holder.itemView.getContext().getPackageName()
            );

            // Si resID es 0, significa que no se encontró la imagen en drawable
            if (resID != 0) {
                holder.binding.ingredientImage.setImageResource(resID);
            } else {
                // Opcional: poner una imagen por defecto si no existe la indicada
                holder.binding.ingredientImage.setImageResource(R.drawable.outline_image_24);
            }
        }

        holder.binding.ingredientContent.setTag("Contenido");
        holder.binding.btnEliminate.setTag("Eliminar");
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de ingredientes en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return ingredientes != null ? ingredientes.size() : 0;
    }

    /**
     * Actualiza la lista de ingredientes del adaptador y notifica los cambios.
     *
     * @param ingredientes - {@link List}<{@link Ingrediente}> - Nueva lista de ingredientes a mostrar.
     */
    public void setIngredientes(List<Ingrediente> ingredientes) {
        if (ingredientes == null) {
            this.ingredientes = new ArrayList<>();
            this.ingredientesFull = new ArrayList<>();
        } else {
            // 1. Guardamos la copia de seguridad para el filtro
            this.ingredientesFull = new ArrayList<>(ingredientes);
            // 2. Actualizamos la lista que se ve
            this.ingredientes = ingredientes;
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de ingredientes según el texto de búsqueda.
     *
     * @param query - String - Texto de búsqueda.
     */
    public void filter(String query) {
        if (ingredientesFull == null) return;

        if (query == null || query.isEmpty()) {
            ingredientes = new ArrayList<>(ingredientesFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            ingredientes = ingredientesFull.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                                i.getFamilia().toLowerCase().contains(lowerCaseQuery) || i.getEtiqueta().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }
}
