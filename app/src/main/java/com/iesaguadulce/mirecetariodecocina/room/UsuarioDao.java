package com.iesaguadulce.mirecetariodecocina.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Interfaz de acceso a datos (DAO) para la entidad {@link Usuario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface UsuarioDao {

    /**
     * Obtiene todos los registros de la entidad {@link Usuario} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Usuario}>> - Objeto {@link LiveData} que contiene una lista de registros de usuario.
     */
    @Query("SELECT * FROM usuario ORDER BY nombre ASC")
    LiveData<List<Usuario>> getAll();

    /**
     * Obtiene un registro específico de la entidad {@link Usuario} por su identificador de usuario.
     *
     * @param id - int - Identificador del usuario del registro a obtener.
     * @return - LiveData<{@link Usuario}> - Objeto {@link LiveData} que contiene el registro de usuario.
     */
    @Query("SELECT * FROM usuario WHERE idUsuario = :id")
    LiveData<Usuario> getUsuario(String id);

    /**
     * Obtiene un registro específico de la entidad {@link Usuario} por su identificador de usuario.
     *
     * @param id - int - Identificador del usuario del registro a obtener.
     * @return - {@link Usuario} - Registro de usuario.
     */
    @Query("SELECT * FROM usuario WHERE idUsuario = :id")
    Usuario getUsuarioSync(String id);

    /**
     * Obtiene todos los roles de los usuarios de la base de datos, utilizando la relación entre
     * {@link Usuario} y {@link Rol} mediante la entidad {@link UsuarioRolJoin}.
     *
     * @return - LiveData<{@link List}<{@link UsuarioRolJoin}>> - Objeto {@link LiveData} que contiene una lista de registros de {@link UsuarioRolJoin}.
     */
    @Query("SELECT usuario.idUsuario, usuario.idRol, rol.rol, usuario.nombre, usuario.pswd, usuario.fechaAlta, usuario.fechaFin FROM usuario " +
            "INNER JOIN rol ON usuario.idRol = rol.idRol ORDER BY usuario.idUsuario ASC")
    LiveData<List<UsuarioRolJoin>> getAllUsuariosRol();

    /**
     * Obtiene el rol de un usuario de la base de datos, utilizando la relación entre
     * {@link Usuario} y {@link Rol} mediante la entidad {@link UsuarioRolJoin}.
     *
     * @param id - int - Identificador del usuario del registro a obtener.
     * @return - LiveData<{@link UsuarioRolJoin}> - Objeto {@link LiveData} que contiene el registro de {@link UsuarioRolJoin}.
     */
    @Query("SELECT usuario.idUsuario, usuario.idRol, rol.rol, usuario.nombre, usuario.pswd, usuario.fechaAlta, usuario.fechaFin FROM usuario " +
            "INNER JOIN rol ON usuario.idRol = rol.idRol "+
            "WHERE usuario.idUsuario = :id LIMIT 1")
    LiveData<UsuarioRolJoin> getUsuarioRolData(String id);

    /**
     * Inserta un nuevo registro en la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    /**
     * Actualiza un registro existente en la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a actualizar.
     */
    @Update
    void update(Usuario usuario);

    /**
     * Elimina un registro de la entidad {@link Usuario}.
     *
     * @param usuario - {@link Usuario} - El registro de usuario a eliminar.
     */
    @Delete
    void delete(Usuario usuario);
}
