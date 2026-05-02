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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Receta} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface RecetaDao {
    /**
     * Obtiene todos los registros de la entidad {@link Receta} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Receta}>> - Objeto {@link LiveData} que contiene una lista de registros de recetas.
     */
    @Query("SELECT * FROM recetas ORDER BY nombre ASC")
    LiveData<List<Receta>> getAll();

    /**
     * Obtiene un registro de la entidad {@link Receta} por su identificador.
     *
     * @param id - int - Identificador del registro de la receta.
     * @return - LiveData<{@link Receta}> - Objeto {@link LiveData} que contiene el registro de la receta.
     */
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<Receta> getReceta(int id);

    /**
     * Obtiene un registro de la entidad {@link Receta} por su nombre.
     *
     * @param nombre - String - Nombre del registro de la receta.
     * @return - LiveData<{@link Receta}> - Objeto {@link LiveData} que contiene el registro de la receta.
     */
    @Query("SELECT * FROM recetas WHERE nombre = :nombre LIMIT 1")
    LiveData<Receta> getRecetaByName(String nombre);

    // Utilizado para pre-poblar la base de datos
    /**
     * Obtiene el último registro insertado en la base de datos.
     *
     * @return - {@link Receta} - El último registro insertado.
     */
    @Query("SELECT * FROM recetas ORDER BY idReceta DESC LIMIT 1")
    Receta getLastInsertedReceta();

    // Utilizado para pre-poblar la base de datos
    /**
     * Obtiene un registro de la entidad {@link Receta} por su nombre.
     *
     * @param nombre - String - Nombre del registro de la receta.
     * @return - {@link Receta} - El registro de la receta.
     */
    @Query("SELECT * FROM recetas WHERE nombre = :nombre LIMIT 1")
    Receta getRecetaByNombre(String nombre);

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Receta} por su identificador.
     *
     * @param id - int - Identificador del registro de la receta.
     * @return - {@link Receta} - El registro de la receta.
     */
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    Receta getRecetaByIdSync(int id);

    /**
     * Inserta un nuevo registro en la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a insertar.
     * @return - long - El identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Receta receta); // Ahora devuelve el ID autogenerado

    /**
     * Actualiza un registro existente en la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a actualizar.
     */
    @Update
    void update(Receta receta);

    /**
     * Elimina un registro de la entidad {@link Receta}.
     *
     * @param receta - {@link Receta} - El registro de la receta a eliminar.
     */
    @Delete
    void delete(Receta receta);
}
