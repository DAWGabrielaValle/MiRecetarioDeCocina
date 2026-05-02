package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Usuario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class UsuarioRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Usuario} en la base de datos local.
     */
    private UsuarioDao usuarioDao;

    /**
     * Ejecutor para operaciones de base de datos en un hilo separado.
     */
    private Executor executor;

    /**
     * Constructor
     * Crea una instancia del repositorio con el DAO y el executor necesarios.
     *
     * @param application - {@link Application} - La aplicación que utiliza el repositorio.
     */
    public UsuarioRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        usuarioDao = database.getUsuarioDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros de la entidad {@link Usuario} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Usuario}>> - Objeto {@link LiveData} que contiene una lista de registros de usuarios.
     */
    public LiveData<List<Usuario>> getAllUsuarios() {
        return usuarioDao.getAll();
    }

    /**
     * Obtiene un registro de la entidad {@link Usuario} por su identificador.
     *
     * @param idUsuario - int - Identificador de usuario.
     * @return - LiveData<{@link Usuario}> - Objeto {@link LiveData} que contiene el registro de usuario.
     */
    public LiveData<Usuario> getUsuario(String idUsuario) {
        return usuarioDao.getUsuario(idUsuario);
    }

    /**
     * Obtiene todos los roles de los usuarios de la base de datos, utilizando la relación entre
     * {@link Usuario} y {@link Rol} mediante la entidad {@link UsuarioRolJoin}.
     *
     * @return - LiveData<{@link List}<{@link UsuarioRolJoin}>> - Objeto {@link LiveData} que contiene una lista de registros de {@link UsuarioRolJoin}.
     */
    public LiveData<List<UsuarioRolJoin>> getAllUsuariosRol() { return usuarioDao.getAllUsuariosRol(); }

    /**
     * Obtiene el rol de un usuario de la base de datos, utilizando la relación entre
     * {@link Usuario} y {@link Rol} mediante la entidad {@link UsuarioRolJoin}.
     *
     * @param idUsuario - int - Identificador del usuario del registro a obtener.
     * @return - LiveData<{@link UsuarioRolJoin}> - Objeto {@link LiveData} que contiene el registro de {@link UsuarioRolJoin}.
     */
    public LiveData<UsuarioRolJoin> getUsuarioRol(String idUsuario) {
        return usuarioDao.getUsuarioRolData(idUsuario);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a insertar.
     */
    public void insert(Usuario usuario) {
        executor.execute(() -> usuarioDao.insert(usuario));
    }

    /**
     * Actualiza un registro existente en la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a actualizar.
     */
    public void update(Usuario usuario) {
        executor.execute(() -> usuarioDao.update(usuario));
    }

    /**
     * Elimina un registro de la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a eliminar.
     */
    public void delete(Usuario usuario) {
        executor.execute(() -> usuarioDao.delete(usuario));
    }
}
