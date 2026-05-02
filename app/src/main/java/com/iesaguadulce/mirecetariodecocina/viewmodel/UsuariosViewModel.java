package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRepositorio;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRolJoin;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los usuarios ({@link Usuario}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link UsuarioRepositorio},
 * garantizando que los datos de los usuarios sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuarioRepositorio
 */
public class UsuariosViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los usuarios. */
    private UsuarioRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de usuarios.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public UsuariosViewModel(@NonNull Application application) {
        super(application);
        repositorio = new UsuarioRepositorio(application);
    }

    /**
     * Recupera todos los usuarios almacenados en la base de datos con su rol correspondiente utilizando
     * la clase {@link UsuarioRolJoin} que combina los datos de {@link Usuario} y {@link com.iesaguadulce.mirecetariodecocina.room.Rol}.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link UsuarioRolJoin}.
     */
    public LiveData<List<UsuarioRolJoin>> getAllUsuariosRol() {
        return repositorio.getAllUsuariosRol();
    }

    /**
     * Recupera todos los usuarios ({@link Usuario}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Usuario}.
     */
    public LiveData<List<Usuario>> getAllUsuarios() {
        return repositorio.getAllUsuarios();
    }

    /**
     * Obtiene un {@link Usuario} específico por su identificador.
     *
     * @param idUsuario - String - Identificador único del usuario.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Usuario} solicitado.
     */
    public LiveData<Usuario> getUsuario(String idUsuario) {
        return repositorio.getUsuario(idUsuario);
    }

    /**
     * Inserta un nuevo {@link Usuario} en la base de datos de forma asíncrona.
     *
     * @param usuario - El objeto {@link Usuario} a insertar.
     */
    public void insert(Usuario usuario) {
        repositorio.insert(usuario);
    }

    /**
     * Actualiza un {@link Usuario} existente en la base de datos de forma asíncrona.
     *
     * @param usuario - El objeto {@link Usuario} con los datos actualizados.
     */
    public void update(Usuario usuario) {
        repositorio.update(usuario);
    }

    /**
     * Elimina un {@link Usuario} de la base de datos de forma asíncrona.
     *
     * @param usuario - El objeto {@link Usuario} que se desea eliminar.
     */
    public void delete(Usuario usuario) {
        repositorio.delete(usuario);
    }
}
