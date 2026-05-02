package com.iesaguadulce.mirecetariodecocina.ui;

import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderUsuariosBinding;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRolJoin;

/**
 * ViewHolder para representar un {@link UsuarioRolJoin} en un RecyclerView.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuariosAdapter
 */
public class UsuariosViewHolder extends RecyclerView.ViewHolder {

    /** Binding para acceder a los componentes de la vista del usuario. */
    ViewholderUsuariosBinding binding;

    /** Referencia al objeto de datos del {@link UsuarioRolJoin} que se está mostrando actualmente. */
    private UsuarioRolJoin usuarioActual;

    /**
     * Constructor
     * Recibe el binding del layout inflado.
     *
     * @param binding - Objeto {@link ViewholderUsuariosBinding} con las vistas del ítem.
     */
    public UsuariosViewHolder(@NonNull ViewholderUsuariosBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Vincula los datos de un {@link UsuarioRolJoin} con la vista y configura los eventos de interacción para el
     * borrado del usuario.
     * <p>
     * Al pulsar el botón de eliminar, se muestra un {@link AlertDialog} para confirmar
     * la acción antes de notificar al listener.
     * </p>
     *
     * @param usuario  - El objeto {@link UsuarioRolJoin} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de borrado del usuario.
     */
    public void bind(UsuarioRolJoin usuario, UsuariosAdapter.OnUsuarioDeleteListener listener) {
        this.usuarioActual = usuario;

        binding.btnEliminate.setOnClickListener(v -> {
            if (usuarioActual.getIdUsuario().equals("admin")) {
                Toast.makeText(v.getContext(), "No se puede eliminar el usuario admin", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(v.getContext())
                .setTitle("Borrar "+usuarioActual.getIdUsuario())
                .setMessage("¿Desea eliminar el usuario de " + usuarioActual.getNombre() + "?")
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Aceptar", (dialog, which) -> {
                if (listener != null) {
                    listener.onUsuarioDeleted(usuarioActual); // Delegamos al fragmento
                }
            })
            .show();

        });
    }

    /**
     * Vincula los datos de un {@link UsuarioRolJoin} con la vista y configura los eventos de interacción para el
     * contenido del usuario.
     * <p>
     * Controla el comportamiento al dar clic en el contenido del usuario en el fragmento {@link com.iesaguadulce.mirecetariodecocina.InicioAdminFragment}
     * mostrando la vista para la edición del usuario.
     * </p>
     * @param usuario  - El objeto {@link UsuarioRolJoin} cuyos datos se van a mostrar.
     * @param listener - El listener para manejar la acción de contenido del usuario.
     */
    public void bind(UsuarioRolJoin usuario, UsuariosAdapter.OnUsuarioViewContentListener listener) {
        this.usuarioActual = usuario;

        binding.userContent.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUsuarioViewContent(usuario); // ¡Faltaba llamar al listener aquí!
            }
        });
    }
}
