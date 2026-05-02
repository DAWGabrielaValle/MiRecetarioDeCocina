package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Diario;
import com.iesaguadulce.mirecetariodecocina.room.DiarioRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los días de la planificación ({@link Diario}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link DiarioRepositorio},
 * garantizando que los datos de los días de la planificación sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DiarioRepositorio
 */
public class DiarioViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los días de la planificación. */
    private DiarioRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de los días de la planificación.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public DiarioViewModel(@NonNull Application application) {
        super(application);
        repositorio = new DiarioRepositorio(application);
    }

    /**
     * Recupera todos los días de la planificación ({@link Diario}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Diario}.
     */
    public LiveData<List<Diario>> getAllDiarios() {
        return repositorio.getAllDiarios();
    }

    /**
     * Obtiene un {@link Diario} específico por su identificador.
     *
     * @param id - int - Identificador único del diario.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Diario} solicitado.
     */
    public LiveData<Diario> getDiario(int id) {
        return repositorio.getDiario(id);
    }

    /**
     * Obtiene el último {@link Diario} insertado en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} que contiene el último {@link Diario} insertado.
     */
    public LiveData<Diario> getLastInsertedDay() {
        return repositorio.getLastInsertedDay();
    }

    /**
     * Obtiene los días de la planificación ({@link Diario}) asociados a una planificación específica.
     *
     * @param idPlan - int - Identificador único de la planificación.
     * @return - Devuelve un {@link LiveData} con la lista de días de la planificación ({@link Diario}).
     */
    public LiveData<List<Diario>> getDiarioByPlan(int idPlan) {
        return repositorio.getDiarioByPlan(idPlan);
    }

    /**
     * Inserta un nuevo {@link Diario} en la base de datos de forma asíncrona.
     *
     * @param diario - El objeto {@link Diario} a insertar.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Diario} insertado.
     */
    public LiveData<Diario> insertarDiario(Diario diario) {
        return repositorio.insertarDiario(diario);
    }

    /**
     * Actualiza un {@link Diario} existente en la base de datos de forma asíncrona.
     *
     * @param diario - El objeto {@link Diario} con los datos actualizados.
     */
    public void insert(Diario diario) {
        repositorio.insert(diario);
    }

    /**
     * Actualiza un {@link Diario} existente en la base de datos de forma asíncrona.
     *
     * @param diario - El objeto {@link Diario} con los datos actualizados.
     */
    public void update(Diario diario) {
        repositorio.update(diario);
    }

    /**
     * Elimina un {@link Diario} de la base de datos de forma asíncrona.
     *
     * @param diario - El objeto {@link Diario} que se desea eliminar.
     */
    public void delete(Diario diario) {
        repositorio.delete(diario);
    }
}
