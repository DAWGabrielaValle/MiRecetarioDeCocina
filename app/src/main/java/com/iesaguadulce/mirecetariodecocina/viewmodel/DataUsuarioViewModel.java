package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.DataUsuario;

/**
 * ViewModel para el manejo temporal de datos de un usuario ({@link DataUsuario}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de un usuario entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataUsuario
 */
public class DataUsuarioViewModel extends ViewModel {

    /** LiveData encapsulado que contiene los datos temporales del usuario. */
    private MutableLiveData<DataUsuario> dataUsuario = new MutableLiveData<>();

    /**
     * Proporciona acceso de solo lectura a los datos del usuario ({@link DataUsuario}).
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento,
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     *
     * @return - Devuelve un {@link LiveData} que contiene el objeto {@link DataUsuario} actual.
     */
    public LiveData<DataUsuario> getDataUsuario() {
        return dataUsuario;
    }

    /**
     * Actualiza el usuario ({@link DataUsuario}) almacenado en el ViewModel y notifica a los observadores.
     *
     * @param usuario - El objeto {@link DataUsuario} con la información a compartir.
     */
    public void setDataUsuario(DataUsuario usuario) {
        dataUsuario.setValue(usuario);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones.
     * </p>
     */
    public void clear() {
        dataUsuario.setValue(null);
    }

}
