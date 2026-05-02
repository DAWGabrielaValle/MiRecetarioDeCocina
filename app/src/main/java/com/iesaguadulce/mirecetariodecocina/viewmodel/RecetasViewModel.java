package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Receta;
import com.iesaguadulce.mirecetariodecocina.room.RecetaIngredientesJoin;
import com.iesaguadulce.mirecetariodecocina.room.RecetaRepositorio;
import com.iesaguadulce.mirecetariodecocina.room.Receta_IngRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de las recetas ({@link Receta}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link RecetaRepositorio},
 * garantizando que los datos de las recetas sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetaRepositorio
 * @see Receta_IngRepositorio
 */
public class RecetasViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de las recetas. */
    private RecetaRepositorio repositorio;

    /** Repositorio para la gestión de las operaciones de datos de los ingredientes asociados a las recetas. */
    private Receta_IngRepositorio recetaIngRepositorio;

    /**
     * Constructor
     * Inicializa el repositorio de recetas y de los ingredientes asociados a las recetas.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public RecetasViewModel(@NonNull Application application) {
        super(application);
        repositorio = new RecetaRepositorio(application);
        recetaIngRepositorio = new Receta_IngRepositorio(application);
    }

    /**
     * Recupera todas las recetas ({@link Receta}) almacenadas en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Receta}.
     */
    public LiveData<List<Receta>> getAllRecetas() {
        return repositorio.getAllRecetas();
    }

    /**
     * Obtiene una {@link Receta} específica por su identificador.
     *
     * @param idReceta - int - Identificador único de la receta.
     * @return - Devuelve un {@link LiveData} que contiene la {@link Receta} solicitada.
     */
    public LiveData<Receta> getReceta(int idReceta) {
        return repositorio.getReceta(idReceta);
    }

    /**
     * Obtiene una {@link Receta} específica por su nombre.
     *
     * @param nombre - String - Nombre de la receta.
     * @return - Devuelve un {@link LiveData} que contiene la {@link Receta} solicitada.
     */
    public LiveData<Receta> getRecetaByNombre(String nombre) {
        return repositorio.getRecetaByNombre(nombre);
    }

    /**
     * Recupera todos los ingredientes asociados a una receta específica almacenados en la base de datos utilizando la clase
     * {@link RecetaIngredientesJoin} que combina los datos de {@link Receta} y {@link RecetaIngrediente}.
     *
     * @param idReceta - int - Identificador único de la receta.
     * @return - Devuelve un {@link LiveData} con la lista de ingredientes asociados a la receta de {@link RecetaIngredientesJoin}.
     */
    public LiveData<List<RecetaIngredientesJoin>> getIngredientesDeReceta(int idReceta) {
        return recetaIngRepositorio.getAllIngredientesReceta(idReceta);
    }

    /**
     * Recupera todos los ingredientes asociados a un menú almacenados en la base de datos utilizando la clase
     * {@link RecetaIngredientesJoin} que combina los datos de {@link Receta} y {@link RecetaIngrediente}.
     *
     * @param idMenu - int - Identificador único del menú.
     * @return - Devuelve un {@link LiveData} con la lista de ingredientes asociados al menú de {@link RecetaIngredientesJoin}.
     */
    public LiveData<List<RecetaIngredientesJoin>> getIngredientesMenu(int idMenu) {
        return recetaIngRepositorio.getAllIngredientesMenu(idMenu);
    }

    /**
     * Recupera todos los ingredientes asociados a un plan almacenados en la base de datos utilizando la clase
     * {@link RecetaIngredientesJoin} que combina los datos de {@link Receta} y {@link RecetaIngrediente}.
     *
     * @param idPlan - int - Identificador único del plan.
     * @return - Devuelve un {@link LiveData} con la lista de ingredientes asociados al plan de {@link RecetaIngredientesJoin}.
     */
    public LiveData<List<RecetaIngredientesJoin>> getIngredientesPlan(int idPlan) {
        return recetaIngRepositorio.getAllIngredientesPlan(idPlan);
    }

    /**
     * Inserta un nuevo {@link Receta} en la base de datos de forma asíncrona.
     *
     * @param receta - El objeto {@link Receta} a insertar.
     */
    public void insert(Receta receta) {
        repositorio.insert(receta);
    }

    /**
     * Actualiza un {@link Receta} existente en la base de datos de forma asíncrona.
     *
     * @param receta - El objeto {@link Receta} con los datos actualizados.
     */
    public void update(Receta receta) {
        repositorio.update(receta);
    }

    /**
     * Elimina un {@link Receta} de la base de datos de forma asíncrona.
     *
     * @param receta - El objeto {@link Receta} que se desea eliminar.
     */
    public void delete(Receta receta) {
        repositorio.delete(receta);
    }

    /**
     * Inserta un nuevo {@link Receta} y la lista de ingredientes ({@link RecetaIngrediente}) en la base de datos.
     *
     * @param receta       - El objeto {@link Receta} a insertar.
     * @param ingredientes - {@link List}<{@link RecetaIngrediente}> - La lista de ingredientes a asociar a la receta.
     */
    public void insertarRecetaCompleta(Receta receta, List<RecetaIngrediente> ingredientes) {
        repositorio.insertarRecetaConIngredientes(receta, ingredientes);
    }

    /**
     * Actualiza un {@link Receta} y la lista de ingredientes ({@link RecetaIngrediente}) en la base de datos.
     *
     * @param receta       - El objeto {@link Receta} con los datos actualizados.
     * @param ingredientes - {@link List}<{@link RecetaIngrediente}> - La lista de ingredientes a asociar a la receta.
     */
    public void actualizarRecetaCompleta(Receta receta, List<RecetaIngrediente> ingredientes) {
        repositorio.actualizarRecetaConIngredientes(receta, ingredientes);
    }

    /**
     * Borra una {@link Receta} y la lista de ingredientes asociadas a la receta de la base de datos.
     *
     * @param receta - El objeto {@link Receta} a borrar.
     */
    public void borrarReceta(Receta receta) {
        repositorio.borrarReceta(receta);
    }

}
