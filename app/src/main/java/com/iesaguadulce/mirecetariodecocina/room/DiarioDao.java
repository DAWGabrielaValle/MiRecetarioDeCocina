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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Diario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface DiarioDao {
    /**
     * Obtiene todos los registros del diario de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Diario}>> - Objeto {@link LiveData} que contiene una lista de registros del diario.
     */
    @Query("SELECT * from diario ORDER BY orden ASC")
    LiveData<List<Diario>> getAll();

    /**
     * Obtiene todos los registros del diario de un {@link Plan} específico.
     *
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el diario.
     * @return - LiveData<{@link List}<{@link Diario}>> - Objeto {@link LiveData} que contiene una lista de registros del diario.
     */
    @Query("SELECT * from diario WHERE idPlan = :idPlan")
    LiveData<List<Diario>> getDiarioByPlan(int idPlan);

    /**
     * Obtiene todos los registros del diario de un {@link Plan} específico.
     *
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el diario.
     * @return - List<{@link Diario}> - Lista de registros del diario.
     */
    @Query("SELECT * from diario WHERE idPlan = :idPlan")
    List<Diario> getListDiariosByPlan(int idPlan); // Nuevo método síncrono

    /**
     * Obtiene un registro del diario por su identificador.
     *
     * @param id - int - Identificador del registro del diario.
     * @return - LiveData<{@link Diario}> - Objeto {@link LiveData} que contiene el registro del diario.
     */
    @Query("SELECT * FROM diario WHERE idDiario = :id")
    LiveData<Diario> getDiario(int id);

    /**
     * Obtiene el último registro del diario insertado en la base de datos.
     *
     * @return - LiveData<{@link Diario}> - Objeto {@link LiveData} que contiene el último registro del diario.
     */
    @Query("SELECT * FROM diario ORDER BY idDiario DESC LIMIT 1")
    LiveData<Diario> getLastInsertedDay();

    /**
     * Obtiene el último registro del diario insertado en la base de datos.
     *
     * @return - {@link Diario} - El último registro del diario.
     */
    @Query("SELECT * FROM diario ORDER BY idDiario DESC LIMIT 1")
    Diario getLastInsertedDiario();

    /**
     * Borra todos los registros del diario de un {@link Plan} específico.
     *
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el diario.
     */
    @Query("DELETE FROM diario WHERE idPlan = :idPlan")
    void deleteDiariosByPlan(int idPlan);


    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro del diario por su identificador.
     *
     * @param id - int - Identificador del registro del diario.
     * @return - {@link Diario} - El registro del diario.
     */
    @Query("SELECT * FROM diario WHERE idDiario = :id")
    Diario getDiarioByIdSync(int id);

    /**
     * Inserta un nuevo registro en el diario.
     *
     * @param diario - {@link Diario} - El registro del diario a insertar.
     * @return - long - El identificador del registro insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Diario diario);

    /**
     * Actualiza un registro existente en el diario.
     *
     * @param diario - {@link Diario} - El registro del diario a actualizar.
     */
    @Update
    void update(Diario diario);

    /**
     * Elimina un registro del diario.
     *
     * @param diario - {@link Diario} - El registro del diario a eliminar.
     */
    @Delete
    void delete(Diario diario);
}
