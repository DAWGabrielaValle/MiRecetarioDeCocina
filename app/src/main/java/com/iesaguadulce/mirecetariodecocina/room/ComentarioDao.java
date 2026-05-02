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
 * Interfaz de acceso a datos (DAO) para la entidad {@link Comentario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Dao
public interface ComentarioDao {
    /**
     * Obtiene todos los comentarios de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Comentario}>> - Objeto {@link LiveData} que contiene una lista de comentarios.
     */
    @Query("SELECT * FROM comentarios")
    LiveData<List<Comentario>> getAll();

    /**
     * Obtiene todos los comentarios de una {@link Receta} específica.
     *
     * @param idReceta - int - Identificador de la receta.
     * @return - LiveData<{@link List}<{@link Comentario}>> - Objeto {@link LiveData} que contiene una lista de comentarios.
     */
    @Query("SELECT * FROM comentarios WHERE idReceta = :idReceta")
    LiveData<List<Comentario>> getComentariosReceta(int idReceta);

    /**
     * Obtiene un comentario específico por su identificador.
     *
     * @param id - int - Identificador del comentario.
     * @return - LiveData<{@link Comentario}> - Objeto {@link LiveData} que contiene el comentario.
     */
    @Query("SELECT * FROM comentarios WHERE idComentario = :id")
    LiveData<Comentario> getComentario(int id);

    /**
     * Obtiene el último comentario insertado en la base de datos.
     *
     * @return - {@link Comentario} - El último comentario insertado.
     */
    @Query ("SELECT * FROM comentarios ORDER BY idComentario DESC LIMIT 1")
    Comentario getLastInsertedComentario();

    /**
     * Inserta un nuevo comentario en la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comentario comentario);

    /**
     * Actualiza un comentario existente en la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a actualizar.
     */
    @Update
    void update(Comentario comentario);

    /**
     * Elimina un comentario de la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a eliminar.
     */
    @Delete
    void delete(Comentario comentario);
}
