package com.iesaguadulce.mirecetariodecocina.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interfaz de acceso a datos (DAO) para la entidad {@link Rol} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface RolDao {

    /**
     * Obtiene todos los registros de la entidad {@link Rol} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Rol}>> - Objeto {@link LiveData} que contiene una lista de registros de rol.
     */
    @Query("SELECT * FROM rol")
    LiveData<List<Rol>> getAll();

    /**
     * Obtiene un registro específico de la entidad {@link Rol} por su identificador.
     *
     * @param id - int - Identificador del registro a obtener.
     * @return - LiveData<{@link Rol}> - Objeto {@link LiveData} que contiene el registro de rol.
     */
    @Query("SELECT * FROM rol WHERE idRol = :id")
    LiveData<Rol> getRol(int id);

    /**
     * Obtiene un registro específico de la entidad {@link Rol} por su nombre.
     *
     * @param rol - String - Nombre del registro a obtener.
     * @return - LiveData<{@link Rol}> - Objeto {@link LiveData} que contiene el registro de rol.
     */
    @Query("SELECT * FROM rol WHERE rol = :rol")
    LiveData<Rol> getRolbyRol(String rol);

    /**
     * Obtiene el último registro insertado en la entidad {@link Rol}.
     *
     * @return - Rol - Registro de rol.
     */
    @Query ("SELECT * FROM rol ORDER BY idRol DESC LIMIT 1")
    Rol getLastInsertedRol();

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro específico de la entidad {@link Rol} por su nombre.
     *
     * @param rol - String - Nombre del registro a obtener.
     * @return - Rol - Registro de rol.
     */
    @Query("SELECT * FROM rol WHERE rol = :rol")
    Rol getRolSync(String rol);

    /**
     * Inserta un nuevo registro en la entidad {@link Rol}.
     *
     * @param rol - {@link Rol} - El registro de rol a insertar.
     * @return - long - Identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Rol rol);
}
