package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Comentario;
import com.iesaguadulce.mirecetariodecocina.room.ComentarioRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los comentarios ({@link Comentario}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link ComentarioRepositorio},
 * garantizando que los datos de los comentarios sobrevivan a los cambios de configuración 
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ComentarioRepositorio
 * @see Comentario
 */
public class ComentariosViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de comentarios. */
    private ComentarioRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de comentarios.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public ComentariosViewModel(@NonNull Application application){
        super(application);
        repositorio = new ComentarioRepositorio(application);
    }

    /**
     * Recupera todos los comentarios ({@link Comentario}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Comentario}.
     */
    public LiveData<List<Comentario>> getAllComentarios() {
        return repositorio.getAllComentarios();
    }

    /**
     * Recupera los comentarios ({@link Comentario}) asociados a una receta específica.
     *
     * @param idReceta - int - Identificador único de la receta.
     * @return - Devuelve un {@link LiveData} con la lista de comentarios de la receta indicada.
     */
    public LiveData<List<Comentario>> getComentariosReceta(int idReceta) {
        return repositorio.getComentariosReceta(idReceta);
    }

    /**
     * Obtiene un {@link Comentario} específico por su identificador.
     *
     * @param id - int - Identificador único del comentario.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Comentario} solicitado.
     */
    public LiveData<Comentario> getComentario(int id) {
        return repositorio.getComentario(id);
    }

    /**
     * Inserta un nuevo {@link Comentario} en la base de datos de forma asíncrona.
     *
     * @param comentario - El objeto {@link Comentario} a insertar.
     */
    public void insert(Comentario comentario) {
        repositorio.insert(comentario);
    }

    /**
     * Actualiza un {@link Comentario} existente en la base de datos de forma asíncrona.
     *
     * @param comentario - El objeto {@link Comentario} con los datos actualizados.
     */
    public void update(Comentario comentario) {
        repositorio.update(comentario);
    }

    /**
     * Elimina un {@link Comentario} de la base de datos de forma asíncrona.
     *
     * @param comentario - El objeto {@link Comentario} que se desea eliminar.
     */
    public void delete(Comentario comentario) {
        repositorio.delete(comentario);
    }
}
