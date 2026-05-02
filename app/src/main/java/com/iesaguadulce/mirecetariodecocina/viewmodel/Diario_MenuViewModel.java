package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.DiarioMenuJoin;
import com.iesaguadulce.mirecetariodecocina.room.Diario_Menu;
import com.iesaguadulce.mirecetariodecocina.room.Diario_MenuRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los menús asociados a los días de la planificación ({@link Diario_Menu}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link Diario_MenuRepositorio},
 * garantizando que los datos de los menús asociados a los días de la planificación sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see Diario_MenuRepositorio
 */
public class Diario_MenuViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los menús asociados a los días de la planificación. */
    private Diario_MenuRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de los menús asociados a los días de la planificación.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public Diario_MenuViewModel(@NonNull Application application){
        super(application);
        repositorio = new Diario_MenuRepositorio(application);
    }

    /**
     * Recupera todos los menús asociados a los días de la planificación ({@link Diario_Menu}).
     *
     * @param idMenu - int - Identificador único del menú.
     * @return - Devuelve un {@link LiveData} con la lista de menús asociados al día de la planificación ({@link Diario_Menu}).
     */
    public LiveData<List<Diario_Menu>> getAllDiarios(int idMenu){
        return repositorio.getAllDiarios(idMenu);
    }

    /**
     * Recupera todos los menús asociados a un día dado de una planificación ({@link Diario_Menu}).
     *
     * @param idDiario - int - Identificador único del diario.
     * @return - Devuelve un {@link LiveData} con la lista de menús asociados al día de la planificación ({@link Diario_Menu}).
     */
    public LiveData<List<DiarioMenuJoin>> getMenusDiario(int idDiario){
        return repositorio.getMenusDiario(idDiario);
    }

    /**
     * Recupera todos los menús asociados a una planificación ({@link Diario_Menu}).
     *
     * @param idPlan - int - Identificador único de la planificación.
     * @return - Devuelve un {@link LiveData} con la lista de menús asociados a la planificación ({@link Diario_Menu}).
     */
    public LiveData<List<DiarioMenuJoin>> getMenusByPlan(int idPlan) {
        return repositorio.getMenusByPlan(idPlan);
    }

    /**
     * Inserta un nuevo {@link Diario_Menu} en la base de datos de forma asíncrona.
     *
     * @param diarioMenu - El objeto {@link Diario_Menu} a insertar.
     */
    public void insert(Diario_Menu diarioMenu){
        repositorio.insert(diarioMenu);
    }

}
