package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Menu_Rec;
import com.iesaguadulce.mirecetariodecocina.room.Menu_RecRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de las recetas asociadas a los menús ({@link Menu_Rec}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link Menu_RecRepositorio},
 * garantizando que los datos de las recetas asociadas a los menús sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see Menu_RecRepositorio
 */
public class Menu_RecViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de las recetas asociadas a los menús. */
    private Menu_RecRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de las recetas asociadas a los menús.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public Menu_RecViewModel(@NonNull Application application) {
        super(application);
        repositorio = new Menu_RecRepositorio(application);
    }

    /**
     * Recupera todos los menús asociados a una receta específica ({@link Menu_Rec}) almacenados en la base de datos.
     *
     * @param idReceta - int - Identificador único de la receta.
     * @return - Devuelve un {@link LiveData} con la lista de los menús asociados a la receta de {@link Menu_Rec}.
     */
    public LiveData<List<Menu_Rec>> getAllMenus(int idReceta) {
        return repositorio.getAllMenus(idReceta);
    }

    /**
     * Inserta un nuevo {@link Menu_Rec} en la base de datos de forma asíncrona.
     *
     * @param menuRec - El objeto {@link Menu_Rec} a insertar.
     */
    public void insert(Menu_Rec menuRec) {
        repositorio.insert(menuRec);
    }

    /**
     * Actualiza un {@link Menu_Rec} existente en la base de datos de forma asíncrona.
     *
     * @param menuRec - El objeto {@link Menu_Rec} con los datos actualizados.
     */
    public void update(Menu_Rec menuRec) {
        repositorio.update(menuRec);
    }

    /**
     * Elimina un {@link Menu_Rec} de la base de datos de forma asíncrona.
     *
     * @param menuRec - El objeto {@link Menu_Rec} que se desea eliminar.
     */
    public void delete(Menu_Rec menuRec) {
        repositorio.delete(menuRec);
    }
}
