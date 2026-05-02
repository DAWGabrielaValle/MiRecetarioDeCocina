package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;
import com.iesaguadulce.mirecetariodecocina.room.IngredienteRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los ingredientes ({@link Ingrediente}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link IngredienteRepositorio},
 * garantizando que los datos de los ingredientes sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredienteRepositorio
 */
public class IngredientesViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de ingredientes. */
    private IngredienteRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de ingredientes.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public IngredientesViewModel(@NonNull Application application) {
        super(application);
        repositorio = new IngredienteRepositorio(application);
    }

    /**
     * Recupera todos los ingredientes ({@link Ingrediente}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Ingrediente}.
     */
    public LiveData<List<Ingrediente>> getAllIngredientes() {
        return repositorio.getAllIngredientes();
    }

    /**
     * Obtiene un {@link Ingrediente} específico por su identificador.
     *
     * @param idIngrediente - int - Identificador único del ingrediente.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Ingrediente} solicitado.
     */
    public LiveData<Ingrediente> getIngrediente(int idIngrediente) {
        return repositorio.getIngrediente(idIngrediente);
    }

    /**
     * Obtiene un {@link Ingrediente} específico por su nombre.
     *
     * @param nombre - String - Nombre del ingrediente.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Ingrediente} solicitado.
     */
    public LiveData<Ingrediente> getIngredienteByNombre(String nombre) {
        return repositorio.getIngredienteByNombre(nombre);
    }

    /**
     * Inserta un nuevo {@link Ingrediente} en la base de datos de forma asíncrona.
     *
     * @param ingrediente - El objeto {@link Ingrediente} a insertar.
     */
    public void insert(Ingrediente ingrediente) {
        repositorio.insert(ingrediente);
    }

    /**
     * Actualiza un {@link Ingrediente} existente en la base de datos de forma asíncrona.
     *
     * @param ingrediente - El objeto {@link Ingrediente} con los datos actualizados.
     */
    public void update(Ingrediente ingrediente) {
        repositorio.update(ingrediente);
    }

    /**
     * Elimina un {@link Ingrediente} de la base de datos de forma asíncrona.
     *
     * @param ingrediente - El objeto {@link Ingrediente} que se desea eliminar.
     */
    public void delete(Ingrediente ingrediente) {
        repositorio.delete(ingrediente);
    }

}
