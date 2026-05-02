package com.iesaguadulce.mirecetariodecocina.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iesaguadulce.mirecetariodecocina.room.Rol;
import com.iesaguadulce.mirecetariodecocina.room.RolRepositorio;

import java.util.List;

/**
 * ViewModel encargado de gestionar los datos de los roles ({@link Rol}) y servirlos a la interfaz de usuario.
 * <p>
 * Esta clase actúa como intermediario entre la vista y {@link RolRepositorio},
 * garantizando que los datos de los roles sobrevivan a los cambios de configuración
 * del dispositivo (como la rotación de pantalla).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RolRepositorio
 */
public class RolViewModel extends AndroidViewModel {

    /** Repositorio para la gestión de las operaciones de datos de los roles. */
    private RolRepositorio repositorio;

    /**
     * Constructor
     * Inicializa el repositorio de roles.
     *
     * @param application - {@link Application} - El contexto de la aplicación, necesario para {@link AndroidViewModel}.
     */
    public RolViewModel(@NonNull Application application) {
        super(application);
        repositorio = new RolRepositorio(application);
    }

    /**
     * Recupera todos los roles ({@link Rol}) almacenados en la base de datos.
     *
     * @return - Devuelve un {@link LiveData} con la lista completa de {@link Rol}.
     */
    public LiveData<List<Rol>> getRoles() {
        return repositorio.getRoles();
    }

    /**
     * Obtiene un {@link Rol} específico por su identificador.
     *
     * @param idRol - int - Identificador único del rol.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Rol} solicitado.
     */
    public LiveData<Rol> getRol(int idRol) {
        return repositorio.getRol(idRol);
    }

    /**
     * Obtiene un {@link Rol} específico por su nombre.
     *
     * @param rol - String - Nombre del rol.
     * @return - Devuelve un {@link LiveData} que contiene el {@link Rol} solicitado.
     */
    public LiveData<Rol> getRolbyRol(String rol) {
        return repositorio.getRolbyRol(rol);
    }

    /**
     * Inserta un nuevo {@link Rol} en la base de datos de forma asíncrona.
     *
     * @param rol - El objeto {@link Rol} a insertar.
     */
    public void insert(Rol rol) {
        repositorio.insert(rol);
    }

}
