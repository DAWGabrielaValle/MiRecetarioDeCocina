package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderComentariosBinding;
import com.iesaguadulce.mirecetariodecocina.room.Comentario;

/**
 * ViewHolder para la representación visual de un {@link Comentario} individual en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción, como la confirmación de borrado.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ComentariosAdapter
 */
public class ComentariosViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del comentario. */
    public ViewholderComentariosBinding binding;

    /** Referencia al objeto de datos del {@link Comentario} que se está mostrando actualmente. */
    private Comentario comentarioActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderComentariosBinding} con las vistas del ítem.
     */
    public ComentariosViewHolder(@NonNull ViewholderComentariosBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de un {@link Comentario} con la vista y configura los eventos de interacción para
     * el borrado del comentario.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param comentario - El objeto {@link Comentario} cuyos datos se van a mostrar.
     * @param listener   - El listener para manejar la acción de borrado del comentario.
     */
    public void bind(Comentario comentario, ComentariosAdapter.OnComentarioDeleteListener listener) {
        this.comentarioActual = comentario;

        binding.btnEliminate.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                .setTitle("Borrar comentario")
                .setMessage("¿Desea eliminar el comentario?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (listener != null) {
                        listener.onComentarioDeleted(comentario);
                    }
                })
                .show();
        });
    }
}
