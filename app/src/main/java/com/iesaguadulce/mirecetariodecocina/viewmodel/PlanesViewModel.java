package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;
import com.iesaguadulce.mirecetariodecocina.room.Plan;
import com.iesaguadulce.mirecetariodecocina.room.PlanRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los planes ({@link Plan}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link PlanRepositorio},
 * garantizando que los datos de los planes sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanRepositorio
 */
public class PlanesViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los planes. */
    private PlanRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de planes.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public PlanesViewModel(@NonNull Application application) {
        super(application);
        repositorio = new PlanRepositorio(application);
    }

    /**
     * Recupera todos los planes ({@link Plan}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Plan}.
     */
    public LiveData<List<Plan>> getAllPlanes() {
        return repositorio.getAllPlanes();
    }

    /**
     * Obtiene un {@link Plan} específico por su identificador.
     *
     * @param id - int - Identificador único del plan.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Plan} solicitado.
     */
    public LiveData<Plan> getPlan(int id) {
        return repositorio.getPlan(id);
    }

    /**
     * Obtiene un {@link Plan} específico por su nombre.
     *
     * @param nombre - String - Nombre del plan.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Plan} solicitado.
     */
    public LiveData<Plan> getPlanByNombre(String nombre) {
        return repositorio.getPlanByNombre(nombre);
    }

    /**
     * Inserta un nuevo {@link Plan} en la base de datos de forma asíncrona.
     *
     * @param plan - El objeto {@link Plan} a insertar.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Plan} insertado.
     */
    public LiveData<Plan> insertarPlan(Plan plan) {
        return repositorio.insertarPlan(plan);
    }

    /**
     * Inserta un nuevo {@link Plan} en la base de datos de forma asíncrona.
     *
     * @param plan - El objeto {@link Plan} a insertar.
     */
    public void insert(Plan plan) {
        repositorio.insert(plan);
    }

    /**
     * Actualiza un {@link Plan} existente en la base de datos de forma asíncrona.
     *
     * @param plan - El objeto {@link Plan} con los datos actualizados.
     */
    public void update(Plan plan) {
        repositorio.update(plan);
    }

    /**
     * Elimina un {@link Plan} de la base de datos de forma asíncrona.
     *
     * @param plan - El objeto {@link Plan} que se desea eliminar.
     */
    public void delete(Plan plan) {
        repositorio.delete(plan);
    }

    /**
     * Inserta un nuevo {@link Plan}, y la lista de menús por día ({@link PlanMenu}) en la base de datos.
     *
     * @param plan  - El objeto {@link Plan} a insertar.
     * @param menus - {@link List}<{@link PlanMenu}> - La lista de menús por día a asociar al plan.
     */
    public void insertarPlanConMenus(Plan plan, List<PlanMenu> menus) {
        repositorio.insertarPlanConMenus(plan, menus);
    }

    /**
     * Actualiza un {@link Plan}, y la lista de menús por día ({@link PlanMenu}) en la base de datos.
     *
     * @param plan  - El objeto {@link Plan} con los datos actualizados.
     * @param menus - {@link List}<{@link PlanMenu}> - La lista de menús por día a asociar al plan.
     */
    public void actualizarPlanConMenus(Plan plan, List<PlanMenu> menus) {
        repositorio.actualizarPlanConMenus(plan, menus);
    }

    /**
     * Borra un {@link Plan} y la lista de menús por día asociadas al plan de la base de datos.
     *
     * @param plan - El objeto {@link Plan} a borrar.
     */
    public void borrarPlan(Plan plan) {
        repositorio.borrarPlan(plan);
    }

}
