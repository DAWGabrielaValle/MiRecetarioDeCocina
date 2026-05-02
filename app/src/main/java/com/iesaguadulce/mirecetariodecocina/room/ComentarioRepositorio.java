package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositorio para la entidad {@link Comentario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class ComentarioRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Comentario} en la base de datos local.
     */
    private ComentarioDao comentarioDao;

    /**
     * Ejecutor para operaciones de base de datos en un hilo separado.
     */
    private Executor executor;

    /**
     * Constructor
     * Crea una instancia del repositorio con el DAO y el executor necesarios.
     *
     * @param application - {@link Application} - La aplicación que utiliza el repositorio.
     */
    public ComentarioRepositorio(Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        comentarioDao = database.getComentarioDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los comentarios de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Comentario}>> - Objeto {@link LiveData} que contiene una lista de comentarios.
     */
    public LiveData<List<Comentario>> getAllComentarios() {
        return comentarioDao.getAll();
    }

    /**
     * Obtiene todos los comentarios de una {@link Receta} específica.
     *
     * @param idReceta - int - Identificador de la receta.
     * @return - LiveData<{@link List}<{@link Comentario}>> - Objeto {@link LiveData} que contiene una lista de comentarios.
     */
    public LiveData<List<Comentario>> getComentariosReceta(int idReceta) {
        return comentarioDao.getComentariosReceta(idReceta);
    }

    /**
     * Obtiene un comentario específico por su identificador.
     *
     * @param id - int - Identificador del comentario.
     * @return - LiveData<{@link Comentario}> - Objeto {@link LiveData} que contiene el comentario.
     */
    public LiveData<Comentario> getComentario(int id) {
        return comentarioDao.getComentario(id);
    }

    /**
     * Inserta un nuevo comentario en la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a insertar.
     */
    public void insert(Comentario comentario) {
        executor.execute(() -> comentarioDao.insert(comentario));
    }

    /**
     * Actualiza un comentario existente en la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a actualizar.
     */
    public void update(Comentario comentario) {
        executor.execute(() -> comentarioDao.update(comentario));
    }

    /**
     * Elimina un comentario de la base de datos.
     *
     * @param comentario - {@link Comentario} - El comentario a eliminar.
     */
    public void delete(Comentario comentario) {
        executor.execute(() -> comentarioDao.delete(comentario));
    }

}
