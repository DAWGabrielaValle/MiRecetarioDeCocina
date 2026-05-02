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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Diario_Menu} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface Diario_MenuDao {
    /**
     * Obtiene todos los registros de la entidad {@link Diario_Menu} de la base de datos
     * @return - LiveData<{@link List}<{@link Diario_Menu}>> - Objeto {@link LiveData} que contiene una lista de registros del diario_menu
     */
    @Query("SELECT * FROM diario_menu")
    LiveData<List<Diario_Menu>> getAll();

    /**
     * Obtiene una lista de la clase {@link DiarioMenuJoin} con los datos del {@link Menu} y {@link Diario}
     * correspondientes a los registros de la entidad {@link Diario_Menu} seleccionados por el identificador
     * del diario.
     *
     * @param idDiario - int - Identificador del diario en la entidad {@link Diario_Menu}.
     * @return - LiveData<{@link List}<{@link DiarioMenuJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros del {@link DiarioMenuJoin}
     */
    @Query("SELECT diario_menu.*, menu.nombre, menu.descripcion, diario.orden FROM diario_menu "+
            "INNER JOIN menu ON diario_menu.idMenu = menu.idMenu "+
            "INNER JOIN diario ON diario_menu.idDiario = diario.idDiario "+
            "WHERE diario_menu.idDiario = :idDiario ORDER BY menu.nombre ASC")
    LiveData<List<DiarioMenuJoin>> getMenusDiario(int idDiario);

    /**
     * Obtiene una lista de la clase {@link DiarioMenuJoin} con los datos del {@link Menu} y {@link Diario}
     * correspondientes a los registros de la entidad {@link Diario_Menu} seleccionados por el identificador
     * del plan de la entidad {@link Diario}
     *
     * @param idPlan - int - Identificador del plan en la entidad {@link Diario}.
     * @return - LiveData<{@link List}<{@link DiarioMenuJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros del {@link DiarioMenuJoin}
     */
    @Query("SELECT diario_menu.*, menu.nombre, menu.descripcion, diario.orden FROM diario_menu "+
            "INNER JOIN menu ON diario_menu.idMenu = menu.idMenu "+
            "INNER JOIN diario ON diario_menu.idDiario = diario.idDiario "+
            "WHERE diario.idPlan = :idPlan")
    LiveData<List<DiarioMenuJoin>> getMenusByPlan(int idPlan);

    /**
     * Obtiene los registros de la entidad {@link Diario_Menu} correspondientes a los diarios asociados al
     * identificador del menú dado.
     *
     * @param idMenu - int - Identificador del menú en la entidad {@link Diario_Menu}.
     * @return - LiveData<{@link List}<{@link Diario_Menu}>> - Objeto {@link LiveData} que contiene una lista de registros del {@link Diario_Menu}
     */
    @Query ("SELECT * FROM diario_menu WHERE idMenu = :idMenu")
    LiveData<List<Diario_Menu>> getAllDiarios(int idMenu);

    /**
     * Borra los registros de la entidad {@link Diario_Menu} correspondientes al identificador del plan
     * de la entidad {@link Diario} dado.
     *
     * @param idPlan - int - Identificador del plan en la entidad {@link Diario}.
     */
    @Query("DELETE FROM diario_menu WHERE idDiario in (SELECT idDiario FROM diario WHERE idPlan = :idPlan)")
    void deleteDiarioMenusByPlan(int idPlan);

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Diario_Menu} por su identificador de diario y menú.
     *
     * @param idDiario - int - Identificador del diario en la entidad {@link Diario_Menu}.
     * @param idMenu   - int - Identificador del menú en la entidad {@link Diario_Menu}.
     * @return - {@link Diario_Menu} - El registro del {@link Diario_Menu}.
     */
    @Query("SELECT * FROM diario_menu WHERE idDiario = :idDiario and idMenu = :idMenu")
    Diario_Menu getDiarioMenuByIdSync(int idDiario, int idMenu);

    /**
     * Inserta un nuevo registro en la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Diario_Menu diarioMenu);

    /**
     * Actualiza un registro existente en la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a actualizar.
     */
    @Update
    void update(Diario_Menu diarioMenu);

    /**
     * Elimina un registro de la entidad {@link Diario_Menu}.
     *
     * @param diarioMenu - {@link Diario_Menu} - El registro del {@link Diario_Menu} a eliminar.
     */
    @Delete
    void delete(Diario_Menu diarioMenu);
}
