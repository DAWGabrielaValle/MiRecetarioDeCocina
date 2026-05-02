package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Receta_Ing;
import com.iesaguadulce.mirecetariodecocina.room.Receta_IngRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los ingredientes asociados a las recetas ({@link Receta_Ing}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link Receta_IngRepositorio},
 * garantizando que los datos de los ingredientes asociados a las recetas sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see Receta_IngRepositorio
 */
public class Receta_IngViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los ingredientes asociados a las recetas. */
    private Receta_IngRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de ingredientes asociados a las recetas.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public Receta_IngViewModel(@NonNull Application application) {
        super(application);
        repositorio = new Receta_IngRepositorio(application);
    }

    /**
     * Recupera todos los ingredientes asociados a una receta específica ({@link Receta_Ing}) almacenados en la base de datos.
     *
     * @param idIngrediente - int - Identificador único del ingrediente.
     * @return - Devuelve un {@link LiveData} con la lista de ingredientes asociados a la receta de {@link Receta_Ing}.
     */
    public LiveData<List<Receta_Ing>> getAllRecetas(int idIngrediente) {
        return repositorio.getAllRecetas(idIngrediente);
    }

    /**
     * Inserta un nuevo {@link Receta_Ing} en la base de datos de forma asíncrona.
     *
     * @param recetaIng - El objeto {@link Receta_Ing} a insertar.
     */
    public void insert(Receta_Ing recetaIng) {
        repositorio.insert(recetaIng);
    }

    /**
     * Actualiza un {@link Receta_Ing} existente en la base de datos de forma asíncrona.
     *
     * @param recetaIng - El objeto {@link Receta_Ing} con los datos actualizados.
     */
    public void update(Receta_Ing recetaIng) {
        repositorio.update(recetaIng);
    }

    /**
     * Elimina un {@link Receta_Ing} de la base de datos de forma asíncrona.
     *
     * @param recetaIng - El objeto {@link Receta_Ing} que se desea eliminar.
     */
    public void delete(Receta_Ing recetaIng) {
        repositorio.delete(recetaIng);
    }

}
