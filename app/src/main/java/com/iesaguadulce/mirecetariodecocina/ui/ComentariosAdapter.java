package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderComentariosBinding;
import com.iesaguadulce.mirecetariodecocina.room.Comentario;

import java.util.List;

/**
 * Adaptador para gestionar y mostrar una lista de comentarios en un RecyclerView.
 * <p>
 * Este adaptador se encarga de inflar el diseño de cada comentario y vincular
 * los datos de la entidad {@link Comentario} con las vistas correspondientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ComentariosViewHolder
 */
public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosViewHolder> {

    /** Lista de objetos {@link Comentario} que se mostrarán en la interfaz. */
    private List<Comentario> listaComentarios;

    /** Listener para gestionar los eventos de borrado de comentarios. */
    private OnComentarioDeleteListener deleteListener;

    /**
     * Interfaz para la comunicación de eventos de borrado desde el adaptador hacia la actividad o fragmento.
     */
    public interface OnComentarioDeleteListener {
        /**
         * Se ejecuta cuando el usuario solicita eliminar un comentario.
         * @param comentario - El objeto {@link Comentario} a eliminar.
         */
        void onComentarioDeleted(Comentario comentario);
    }

    /**
     * Establece el listener para los eventos de borrado.
     * @param listener - Implementación de {@link OnComentarioDeleteListener}.
     */
    public void setOnComentarioDeleteListener(OnComentarioDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Recibe la lista de comentarios a mostrar.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link ComentariosViewHolder} - Devuelve el ViewHolder para el comentario.
     */
    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComentariosViewHolder(
                ViewholderComentariosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un comentario con la vista del ViewHolder.
     *
     * @param holder   - {@link ComentariosViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ComentariosViewHolder holder, int position) {
        Comentario comentario = listaComentarios.get(position);
        holder.bind(comentario, deleteListener);
        holder.binding.comentarioText.setText(comentario.getComentario());
        holder.binding.comentarioDate.setText(comentario.getFechaCom());
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de comentarios en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return listaComentarios != null ? listaComentarios.size() : 0;
    }

    /**
     * Actualiza la lista de comentarios del adaptador y notifica los cambios.
     *
     * @param listaComentarios - {@link List}<{@link Comentario}> - Nueva lista de comentarios a mostrar.
     */
    public void setComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
        notifyDataSetChanged();
    }
}
