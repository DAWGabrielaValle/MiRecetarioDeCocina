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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Ingrediente} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface IngredienteDao {
    /**
     * Obtiene todos los registros de la entidad {@link Ingrediente} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Ingrediente}>> - Objeto {@link LiveData} que contiene una lista de registros del ingrediente.
     */
    @Query("SELECT * FROM ingredientes ORDER BY nombre ASC")
    LiveData<List<Ingrediente>> getAll();

    /**
     * Obtiene un registro de la entidad {@link Ingrediente} por su identificador.
     *
     * @param id - int - Identificador del registro del ingrediente.
     * @return - LiveData<{@link Ingrediente}> - Objeto {@link LiveData} que contiene el registro del ingrediente.
     */
    @Query("SELECT * FROM ingredientes WHERE idIngrediente = :id")
    LiveData<Ingrediente> getIngrediente(int id);

    /**
     * Obtiene un registro de la entidad {@link Ingrediente} por su nombre.
     *
     * @param nombre - String - Nombre del registro del ingrediente.
     * @return - LiveData<{@link Ingrediente}> - Objeto {@link LiveData} que contiene el registro del ingrediente.
     */
    @Query("SELECT * FROM ingredientes WHERE nombre = :nombre LIMIT 1")
    LiveData<Ingrediente> getIngredientByName(String nombre);

    // Utilizado para pre-poblar la base de datos
    /**
     * Obtiene un registro de la entidad {@link Ingrediente} por su nombre.
     *
     * @param nombre - String - Nombre del registro del ingrediente.
     * @return - {@link Ingrediente} - El registro del ingrediente.
     */
    @Query("SELECT * FROM ingredientes WHERE nombre = :nombre LIMIT 1")
    Ingrediente getIngredienteByNombre(String nombre);

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Ingrediente} por su identificador.
     *
     * @param id - int - Identificador del registro del ingrediente.
     * @return - {@link Ingrediente} - El registro del ingrediente.
     */
    @Query("SELECT * FROM ingredientes WHERE idIngrediente = :id LIMIT 1")
    Ingrediente getIngredienteByIdSync(int id);

    /**
     * Inserta un nuevo registro en la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a insertar.
     * @return - long - El identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Ingrediente ingrediente);

    /**
     * Actualiza un registro existente en la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a actualizar.
     */
    @Update
    void update(Ingrediente ingrediente);

    /**
     * Elimina un registro de la entidad {@link Ingrediente}.
     *
     * @param ingrediente - {@link Ingrediente} - El registro del ingrediente a eliminar.
     */
    @Delete
    void delete(Ingrediente ingrediente);
}
