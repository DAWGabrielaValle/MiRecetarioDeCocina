package com.iesaguadulce.mirecetariodecocina.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.mirecetariodecocina.databinding.ViewholderUsuariosBinding;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRolJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter para representar una lista de {@link UsuarioRolJoin} en un RecyclerView. Esta lista representa
 * la relación entre usuarios y roles en la aplicación.
 * <p>
 * Esta clase mantiene las referencias a las vistas definidas en el layout a través de View Binding
 * y se encarga de gestionar la lógica de interacción con el usuario.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuariosViewHolder
 */
public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosViewHolder> {

    /** Copia de seguridad la lista de {@link UsuarioRolJoin} para el filtro de búsqueda. */
    private List<UsuarioRolJoin> usuariosFull = new ArrayList<>();

    /** Lista de {@link UsuarioRolJoin} que se van a mostrar en el RecyclerView. */
    private List<UsuarioRolJoin> usuarios = new ArrayList<>();

    /** Listener para manejar la acción de borrado de un {@link UsuarioRolJoin}. */
    private OnUsuarioDeleteListener deleteListener;

    /** Listener para manejar la acción de contenido de un {@link UsuarioRolJoin}. */
    private OnUsuarioViewContentListener contentListener;

    /**
     * Interfaz para manejar la acción de borrado de un {@link UsuarioRolJoin}.
     */
    public interface OnUsuarioDeleteListener {
        void onUsuarioDeleted(UsuarioRolJoin usuario);
    }

    /**
     * Interfaz para manejar la acción de contenido de un {@link UsuarioRolJoin}.
     */
    public interface OnUsuarioViewContentListener {
        void onUsuarioViewContent(UsuarioRolJoin usuario);
    }

    /**
     * Establece el listener para manejar la acción de borrado de un {@link UsuarioRolJoin}.
     *
     * @param listener - Implementación de {@link OnUsuarioDeleteListener}.
     */
    public void setOnUsuarioDeleteListener(OnUsuarioDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * Establece el listener para manejar la acción de contenido de un {@link UsuarioRolJoin}.
     *
     * @param listener
     */
    public void setOnUsuarioViewContentListener(OnUsuarioViewContentListener listener) {
        this.contentListener = listener;
    }

    /**
     * Crea y devuelve un nuevo {@link UsuariosViewHolder} para representar un {@link UsuarioRolJoin} en el RecyclerView.
     *
     * @param parent   - {@link ViewGroup} en el que se añadirá la nueva View después de estar vinculada a una posición del adaptador.
     * @param viewType - int - El tipo de vista de la nueva View.
     *
     * @return - {@link UsuariosViewHolder} - Devuelve el ViewHolder para representar un {@link UsuarioRolJoin} en un RecyclerView.
     */
    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsuariosViewHolder(
                ViewholderUsuariosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    /**
     * Vincula los datos de un {@link UsuarioRolJoin} con la vista del ViewHolder.
     *
     * @param holder   - {@link UsuariosViewHolder} - El ViewHolder debe actualizarse para representar el contenido del elemento en la posición indicada del conjunto de datos.
     * @param position - int - La posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder holder, int position) {
        UsuarioRolJoin usuario = usuarios.get(position);
        holder.bind(usuario, deleteListener);
        holder.bind(usuario, contentListener);

        String fecha = usuario.getFechaAlta();
        String info = fecha + " · " + usuario.getRol();
        holder.binding.userid.setText(usuario.getIdUsuario());
        holder.binding.username.setText(usuario.getNombre());
        holder.binding.userinfo.setText(info);
        holder.binding.userContent.setTag("Contenido-"+usuario.getIdUsuario());
        holder.binding.btnEliminate.setTag("Eliminar-"+usuario.getIdUsuario());
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return - int - Devuelve la cantidad de {@link UsuarioRolJoin} en la lista, o 0 si la lista es nula.
     */
    @Override
    public int getItemCount() {
        return usuarios != null ? usuarios.size() : 0;
    }

    /**
     * Actualiza la lista de {@link UsuarioRolJoin} del adaptador y notifica los cambios.
     *
     * @param usuarios - {@link List}<{@link UsuarioRolJoin}> - Nueva lista de {@link UsuarioRolJoin} a mostrar.
     */
    public void setUsuarios(List<UsuarioRolJoin> usuarios) {
        if (usuarios == null) {
            this.usuarios = new ArrayList<>();
            this.usuariosFull = new ArrayList<>();
        } else {
            this.usuariosFull = new ArrayList<>(usuarios);
            this.usuarios = usuarios;
        }
        notifyDataSetChanged();
    }

    /**
     * Filtra la lista de {@link UsuarioRolJoin} según el texto de búsqueda.
     *
     * @param query - String - Texto de búsqueda.
     */
    public void filter(String query) {
        if (usuariosFull == null) return;

        if (query == null || query.isEmpty()) {
            usuarios = new ArrayList<>(usuariosFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            usuarios = usuariosFull.stream()
                    .filter(u -> u.getIdUsuario().toLowerCase().contains(lowerCaseQuery) || 
                                u.getNombre().toLowerCase().contains(lowerCaseQuery) || u.getRol().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }
}
