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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Plan} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface PlanDao {

    /**
     * Obtiene todos los registros de la entidad {@link Plan} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Plan}>> - Objeto {@link LiveData} que contiene una lista de registros del plan.
     */
    @Query("SELECT * FROM `plan` ORDER BY nombre ASC")
    LiveData<List<Plan>> getAll();

    /**
     * Obtiene un registro de la entidad {@link Plan} por su identificador.
     *
     * @param id - int - Identificador del registro del plan.
     * @return - LiveData<{@link Plan}> - Objeto {@link LiveData} que contiene el registro del plan.
     */
    @Query("SELECT * FROM `plan` WHERE idPlan = :id")
    LiveData<Plan> getPlan(int id);

    /**
     * Obtiene un registro de la entidad {@link Plan} por su nombre.
     *
     * @param nombre - String - Nombre del registro del plan.
     * @return - LiveData<{@link Plan}> - Objeto {@link LiveData} que contiene el registro del plan.
     */
    @Query("SELECT * FROM `plan` WHERE nombre = :nombre")
    LiveData<Plan> getPlanByNombre(String nombre);

    /**
     * Obtiene el último registro insertado en la base de datos.
     *
     * @return - {@link Plan} - El último registro insertado.
     */
    @Query("SELECT * FROM `plan` ORDER BY idPlan DESC LIMIT 1")
    Plan getLastInsertedPlan();

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Plan} por su identificador.
     *
     * @param id - int - Identificador del registro del plan.
     * @return - {@link Plan} - El registro del plan.
     */
    @Query("SELECT * FROM `plan` WHERE idPlan = :id")
    Plan getPlanByIdSync(int id);

    /**
     * Inserta un nuevo registro en la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a insertar.
     * @return - long - El identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Plan plan);

    /**
     * Actualiza un registro existente en la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a actualizar.
     */
    @Update
    void update(Plan plan);

    /**
     * Elimina un registro de la entidad {@link Plan}.
     *
     * @param plan - {@link Plan} - El registro del plan a eliminar.
     */
    @Delete
    void delete(Plan plan);
}


