package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderPlanesBinding;
import com.iesaguadulce.mirecetariodecocina.room.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para gestionar y mostrar una lista de planes en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada plan y vincular
 * los datos de la entidad {@link Plan} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanesViewHolder
 */
public class PlanesAdapter extends RecyclerView.Adapter<PlanesViewHolder> {

    /** Lista de objetos {@link Plan} que se mostrarán en la interfaz. */
    private List<Plan> planes = new ArrayList<>();

    /** Copia de seguridad para el filtro de la lista de planes. */
    private List<Plan> planesFull = new ArrayList<>();

    /** Listener para gestionar los eventos de borrado de planes. */
    private OnPlanDeleteListener deleteListener;

    /** Listener para gestionar los eventos de contenido de planes. */
    private OnPlanViewContentListener contentListener;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnPlanDeleteListener {
        void onPlanDeleted(Plan plan);
    }

    /**
     * Interfaz para la comunicación de eventos de contenido desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnPlanViewContentListener {
        void onPlanViewContent(Plan plan);
    }

    /**
     * Establece el listener para los eventos de borrado.
     * @param listener - Implementación de {@link OnPlanDeleteListener}.
     */
    public void setOnPlanDeleteListener(OnPlanDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Establece el listener para los eventos de contenido.
     * @param listener - Implementación de {@link OnPlanViewContentListener}.
     */
    public void setOnPlanViewContentListener(OnPlanViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Recibe la lista de planes a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link PlanesViewHolder} - Devuelve el ViewHolder para el plan.
     */
    @NonNull
    @Override
    public PlanesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanesViewHolder(
                ViewholderPlanesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un plan con la vista del ViewHolder.
     *
     * @param holder   - {@link PlanesViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull PlanesViewHolder holder, int position) {
        Plan plan = planes.get(position);
        holder.bind(plan, deleteListener);
        holder.bind(plan, contentListener);


        String info = plan.getTipo() + " · " + plan.getEtiqueta();
        holder.binding.planName.setText(plan.getNombre());
        holder.binding.planDescription.setText(plan.getDescripcion());
        holder.binding.planinfo.setText(info);
        holder.binding.planContent.setTag("Contenido");
        holder.binding.btnEliminate.setTag("Eliminar");
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de planes en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return planes != null ? planes.size() : 0;
    }

    /**
     * Actualiza la lista de planes del adaptador y notifica los cambios.
     *
     * @param planes - {@link List}<{@link Plan}> - Nueva lista de planes a mostrar.
     */
    public void setPlanes(List<Plan> planes) {
        if (planes == null) {
            this.planes = new ArrayList<>();
            this.planesFull = new ArrayList<>();
        } else {
            // 1. Guardamos la copia de seguridad para el filtro
            this.planesFull = new ArrayList<>(planes);
            // 2. Actualizamos la lista que se ve
            this.planes = planes;
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de planes según el texto de búsqueda.
     *
     * @param query - String - Texto de búsqueda.
     */
    public void filter(String query) {
        if (planesFull == null) return;

        if (query == null || query.isEmpty()) {
            planes = new ArrayList<>(planesFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            planes = planesFull.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                            p.getTipo().toLowerCase().contains(lowerCaseQuery) ||
                            p.getEtiqueta().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }

}
