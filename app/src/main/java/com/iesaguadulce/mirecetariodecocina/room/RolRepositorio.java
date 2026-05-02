package com.iesaguadulce.mirecetariodecocina.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Clase que representa un repositorio de datos para la entidad {@link Rol} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class RolRepositorio {

    /**
     * Interfaz de acceso a datos (DAO) para la entidad {@link Rol} en la base de datos local.
     */
    private RolDao rolDao;

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
    public RolRepositorio (Application application) {
        RecetarioCocinaDatabase database = RecetarioCocinaDatabase.getDatabase(application.getApplicationContext());
        rolDao = database.getRolDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Obtiene todos los registros de la entidad {@link Rol} de la base de datos.
     *
     * @return - LiveData<{@link List}<{@link Rol}>> - Objeto {@link LiveData} que contiene una lista de registros de rol.
     */
    public LiveData<List<Rol>> getRoles() {
        return rolDao.getAll();
    }

    /**
     * Obtiene un registro específico de la entidad {@link Rol} por su identificador.
     *
     * @param idRol - int - Identificador del registro a obtener.
     * @return - LiveData<{@link Rol}> - Objeto {@link LiveData} que contiene el registro de rol.
     */
    public LiveData<Rol> getRol(int idRol) {
        return rolDao.getRol(idRol);
    }

    /**
     * Obtiene un registro específico de la entidad {@link Rol} por su nombre.
     *
     * @param rol - String - Nombre del registro a obtener.
     * @return - LiveData<{@link Rol}> - Objeto {@link LiveData} que contiene el registro de rol.
     */
    public LiveData<Rol> getRolbyRol(String rol) {
        return rolDao.getRolbyRol(rol);
    }

    /**
     * Inserta un nuevo registro en la entidad {@link Rol}.
     *
     * @param rol - {@link Rol} - El registro de rol a insertar.
     */
    public void insert(Rol rol) {
        executor.execute(() -> rolDao.insert(rol));
    }
}
