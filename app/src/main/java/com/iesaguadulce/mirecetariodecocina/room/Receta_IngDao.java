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
 * Interfaz de acceso a datos (DAO) para la relación entre {@link Receta} y {@link Ingrediente} en la base de datos local
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface Receta_IngDao {

    /**
     * Obtiene todos los registros de la entidad {@link Receta_Ing} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Receta_Ing}>> - Objeto {@link LiveData} que contiene una lista de registros de
     * {@link Receta_Ing}.
     */
    @Query("SELECT * FROM receta_ing")
    LiveData<List<Receta_Ing>> getAll();

    /**
     * Obtiene una lista de registros de la entidad {@link Receta_Ing} correspondientes las recetas asociadas al identificado del ingrediente dado
     *
     * @param idIngrediente - int - Identificador del ingrediente {@link Receta_Ing}
     * @return - LiveData<{@link List}<{@link Receta_Ing}>> - Objeto {@link LiveData} que contiene una lista de registros de {@link Receta_Ing}
     */
    @Query("SELECT * FROM receta_ing WHERE idIngrediente = :idIngrediente")
    LiveData<List<Receta_Ing>> getRecetas(int idIngrediente);

    /**
     * Obtiene una lista de la clase {@link RecetaIngredientesJoin} con los datos de la {@link Receta} y {@link Ingrediente}
     * correspondientes a los registros de la entidad {@link Receta_Ing} seleccionados por el identificador de la receta dado.
     *
     * @param idReceta - int - Identificador de la receta en la entidad {@link Receta_Ing}.
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene una lista de registros
     * del {@link RecetaIngredientesJoin}
     */
    @Query("SELECT receta_ing.*, ingredientes.nombre FROM receta_ing "+
            "INNER JOIN ingredientes ON receta_ing.idIngrediente = ingredientes.idIngrediente "+
            "WHERE receta_ing.idReceta = :idReceta ORDER BY ingredientes.nombre ASC")
    LiveData<List<RecetaIngredientesJoin>> getIngredientesReceta(int idReceta);

    /**
     * Obtiene una lista de la clase {@link RecetaIngredientesJoin} con los datos de la {@link Receta} y {@link Ingrediente}
     * correspondientes a los registros de la entidad {@link Receta_Ing} seleccionados por el identificador del menú dado.
     *
     * @param idMenu - int - Identificador del menú en la entidad {@link Menu_Rec}.
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene una lista de registros del
     * {@link RecetaIngredientesJoin}
     */
    @Query("SELECT receta_ing.*, ingredientes.nombre FROM receta_ing "+
            "INNER JOIN ingredientes ON receta_ing.idIngrediente = ingredientes.idIngrediente "+
            "WHERE receta_ing.idReceta in (SELECT idReceta FROM menu_rec WHERE idMenu = :idMenu) " +
            "ORDER BY ingredientes.nombre ASC")
    LiveData<List<RecetaIngredientesJoin>> getIngredientesMenu(int idMenu);

    /**
     * Obtiene una lista de la clase {@link RecetaIngredientesJoin} con los datos de la {@link Receta} y {@link Ingrediente}
     * correspondientes a los registros de la entidad {@link Receta_Ing} seleccionados por el identificador del plan dado.
     *
     * @param idPlan - int - Identificador del plan en la entidad {@link Diario}.
     * @return - LiveData<{@link List}<{@link RecetaIngredientesJoin}>> - Objeto {@link LiveData} que contiene una lista de registros del
     * {@link RecetaIngredientesJoin}
     */
    @Query("SELECT receta_ing.*, ingredientes.nombre FROM receta_ing "+
            "INNER JOIN ingredientes ON receta_ing.idIngrediente = ingredientes.idIngrediente "+
            "WHERE receta_ing.idReceta in ("+
                "SELECT mr.idReceta FROM diario d "+
                "INNER JOIN diario_menu dm ON d.idDiario = dm.idDiario "+
                "INNER JOIN menu_rec mr ON dm.idMenu = mr.idMenu "+
                "WHERE d.idPlan = :idPlan) " +
            "ORDER BY ingredientes.nombre ASC")
    LiveData<List<RecetaIngredientesJoin>> getIngredientesPlan(int idPlan);

    /**
     * Borra los registros de la entidad {@link Receta_Ing} correspondientes al identificador de la receta dado.
     *
     * @param idReceta - int - Identificador de la receta en la entidad {@link Receta_Ing}.
     */
    @Query("DELETE FROM receta_ing WHERE idReceta = :idReceta")
    void deleteIngredientesByReceta(int idReceta);

    /**
     * Método síncrono para pruebas unitarias.
     * Obtiene un registro de la entidad {@link Receta_Ing} por su identificador de receta y ingrediente.
     *
     * @param idReceta      - int - Identificador de la receta en la entidad {@link Receta_Ing}.
     * @param idIngrediente - int - Identificador del ingrediente en la entidad {@link Receta_Ing}.
     * @return - {@link Receta_Ing} - El registro del {@link Receta_Ing}.
     */
    @Query("SELECT * FROM receta_ing WHERE idReceta = :idReceta and idIngrediente = :idIngrediente")
    Receta_Ing getRecetaIngByIdSync(int idReceta, int idIngrediente);

    /**
     * Inserta un nuevo registro en la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro del {@link Receta_Ing} a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Receta_Ing recetaIng);

    /**
     * Actualiza un registro existente en la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro del {@link Receta_Ing} a actualizar.
     */
    @Update
    void update(Receta_Ing recetaIng);

    /**
     * Elimina un registro de la entidad {@link Receta_Ing}.
     *
     * @param recetaIng - {@link Receta_Ing} - El registro del {@link Receta_Ing} a eliminar.
     */
    @Delete
    void delete(Receta_Ing recetaIng);
}
