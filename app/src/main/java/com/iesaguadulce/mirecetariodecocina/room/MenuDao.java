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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Menu} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface MenuDao {
    /**
     * Obtiene todos los registros de la entidad {@link Menu} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Menu}>> - Objeto {@link LiveData} que contiene una lista de registros del menú.
     */
    @Query("SELECT * FROM menu ORDER BY nombre ASC")
    LiveData<List<Menu>> getAll();

    /**
     * Obtiene un registro de la entidad {@link Menu} por su identificador.
     *
     * @param id - int - Identificador del registro del menú.
     * @return - LiveData<{@link Menu}> - Objeto {@link LiveData} que contiene el registro del menú.
     */
    @Query("SELECT * FROM menu WHERE idMenu = :id")
    LiveData<Menu> getMenu(int id);

    /**
     * Obtiene un registro de la entidad {@link Menu} por su nombre.
     *
     * @param nombre - String - Nombre del registro del menú.
     * @return - LiveData<{@link Menu}> - Objeto {@link LiveData} que contiene el registro del menú.
     */
    @Query("SELECT * FROM menu WHERE nombre = :nombre")
    LiveData<Menu> getMenuByNombre(String nombre);

    /**
     * Obtiene el último registro insertado en la base de datos.
     *
     * @return - {@link Menu} - El último registro insertado.
     */
    @Query("SELECT * FROM menu ORDER BY idMenu DESC LIMIT 1")
    Menu getLastInsertedMenu();

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Menu} por su identificador.
     *
     * @param id - int - Identificador del registro del menú.
     * @return - {@link Menu} - El registro del menú.
     */
    @Query("SELECT * FROM menu WHERE idMenu = :id")
    Menu getMenuByIdSync(int id);

    /**
     * Inserta un nuevo registro en la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a insertar.
     * @return - long - El identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Menu menu);

    /**
     * Actualiza un registro existente en la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a actualizar.
     */
    @Update
    void update(Menu menu);

    /**
     * Elimina un registro de la entidad {@link Menu}.
     *
     * @param menu - {@link Menu} - El registro del menú a eliminar.
     */
    @Delete
    void delete(Menu menu);
}
