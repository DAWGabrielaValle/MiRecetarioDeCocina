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
 * Interfaz de acceso a datos (DAO) para la relación entre {@link Receta} y {@link Usuario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface Receta_UsuDao {

    /**
     * Obtiene todos los registros de la entidad {@link Receta_Usu} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Receta_Usu}>> - Objeto {@link LiveData} que contiene una lista de registros de receta_usu.
     */
    @Query("SELECT * FROM receta_usu")
    LiveData<List<Receta_Usu>> getAll();

    /**
     * Inserta un nuevo registro en la entidad {@link Receta_Usu}.
     *
     * @param recetaUsu - {@link Receta_Usu} - El registro de la receta a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Receta_Usu recetaUsu);

    /**
     * Actualiza un registro existente en la entidad {@link Receta_Usu}.
     *
     * @param recetaUsu - {@link Receta_Usu} - El registro de la receta a actualizar.
     */
    @Update
    void update(Receta_Usu recetaUsu);

    /**
     * Elimina un registro de la entidad {@link Receta_Usu}.
     *
     * @param recetaUsu - {@link Receta_Usu} - El registro de la receta a eliminar.
     */
    @Delete
    void delete(Receta_Usu recetaUsu);
}
