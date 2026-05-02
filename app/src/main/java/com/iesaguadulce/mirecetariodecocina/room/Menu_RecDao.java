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
 * Interfaz de acceso a datos (DAO) para la relación entre {@link Menu} y {@link Receta} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface Menu_RecDao {

    /**
     * Obtiene todos los registros de la entidad {@link Menu_Rec} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Menu_Rec}>> - Objeto {@link LiveData} que contiene una lista de registros de menu_rec.
     */
    @Query("SELECT * FROM menu_rec")
    LiveData<List<Menu_Rec>> getAll();

    /**
     * Obtiene una lista registros de la entidad {@link Menu_Rec} correspondiente a los menús asociados a una receta específica.
     *
     * @param idReceta - int - Identificador de la receta {@link Menu_Rec}
     * @return - LiveData<{@link List}<{@link Menu_Rec}>> - Objeto {@link LiveData} que contiene una lista de registros de menu_rec.
     */
    @Query("SELECT * FROM menu_rec WHERE idReceta = :idReceta")
    LiveData<List<Menu_Rec>> getMenus(int idReceta);

    /**
     * Obtiene una lista de la clase {@link MenuRecetasJoin} con los datos del {@link Menu} y {@link Receta}
     * correspondientes a los registros de la entidad {@link Menu_Rec} seleccionados por el identificador
     * del menú dado.
     *
     * @param idMenu - int - Identificador del menú en la entidad {@link Menu_Rec}.
     * @return - LiveData<{@link List}<{@link MenuRecetasJoin}>> - Objeto {@link LiveData} que contiene
     * una lista de registros del {@link MenuRecetasJoin}
     */
    @Query("SELECT menu_rec.*, recetas.nombre, recetas.descripcion FROM menu_rec "+
            "INNER JOIN recetas ON menu_rec.idReceta = recetas.idReceta "+
            "WHERE menu_rec.idMenu = :idMenu ORDER BY recetas.nombre ASC")
    LiveData<List<MenuRecetasJoin>> getRecetasMenu(int idMenu);

    /**
     * Borra los registros de la entidad {@link Menu_Rec} correspondientes al identificador del menú dado.
     *
     * @param idMenu - int - Identificador del menú en la entidad {@link Menu_Rec}.
     */
    @Query("DELETE FROM menu_rec WHERE idMenu = :idMenu")
    void deleteRecetasByMenu(int idMenu);

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Menu_Rec} por su identificador de menú y receta.
     *
     * @param idMenu   - int - Identificador del menú en la entidad {@link Menu_Rec}.
     * @param idReceta - int - Identificador de la receta en la entidad {@link Menu_Rec}.
     * @return - {@link Menu_Rec} - El registro del {@link Menu_Rec}.
     */
    @Query("SELECT * FROM menu_rec WHERE idMenu = :idMenu and idReceta = :idReceta")
    Menu_Rec getMenuRecByIdSync(int idMenu, int idReceta);

    /**
     * Inserta un nuevo registro en la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Menu_Rec menuRec);

    /**
     * Actualiza un registro existente en la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a actualizar.
     */
    @Update
    void update(Menu_Rec menuRec);

    /**
     * Elimina un registro de la entidad {@link Menu_Rec}.
     *
     * @param menuRec - {@link Menu_Rec} - El registro del {@link Menu_Rec} a eliminar.
     */
    @Delete
    void delete(Menu_Rec menuRec);
}
