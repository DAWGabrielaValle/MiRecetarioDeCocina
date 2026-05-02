package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderDiasBinding;
import com.iesaguadulce.mirecetariodecocina.model.PlanDia;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de días (diario) en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada día y vincular
 * los datos de la entidad {@link PlanDia} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanDiaViewHolder
 */
public class PlanDiaAdapter extends RecyclerView.Adapter<PlanDiaViewHolder>{

    /** Lista de objetos {@link PlanDia} que se mostrarán en la interfaz. */
    private List<PlanDia> dias;

    /** Listener para gestionar los eventos de contenido desde el adaptador hacia la actividad o fragmento. */
    private OnPlanDiaViewContentListener contentListener;

    /**
     * Interfaz para la comunicación de eventos de contenido desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnPlanDiaViewContentListener {
        void onPlanDiaViewContent(PlanDia dia);
    }

    /**
     * Establece el listener para los eventos de contenido.
     *
     * @param listener - Implementación de {@link OnPlanDiaViewContentListener}.
     */
    public void setOnPlanDiaViewContentListener(OnPlanDiaViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Recibe la lista de días a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link PlanDiaViewHolder} - Devuelve el ViewHolder para el día.
     */
    @NonNull
    @Override
    public PlanDiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanDiaViewHolder(
                ViewholderDiasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un día con la vista del ViewHolder.
     *
     * @param holder   - {@link PlanDiaViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull PlanDiaViewHolder holder, int position) {
        PlanDia dia = dias.get(position);
        holder.bind(dia, contentListener);

        holder.binding.diaNumber.setText(dia.getDia());
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de días en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return dias != null ? dias.size() : 0;
    }

    /**
     * Actualiza la lista de días del adaptador y notifica los cambios.
     *
     * @param dias - {@link List}<{@link PlanDia}> - Nueva lista de días a mostrar.
     */
    public void setDias(List<PlanDia> dias) {
        this.dias = dias;
        notifyDataSetChanged();
    }
}
