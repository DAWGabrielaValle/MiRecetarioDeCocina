package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iesaguadulce.mirecetariodecocina.model.DataMenu;

/**
 * ViewModel para el manejo temporal de datos de menú ({@link DataMenu}) en memoria.
 * <p>
 * Esta clase actúa como un contenedor de estado compartido que permite pasar información
 * de un menú entre diferentes fragmentos o actividades sin necesidad
 * de persistencia inmediata. Al extender de {@link ViewModel}, los datos sobreviven
 * a cambios de configuración como la rotación de la pantalla.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataMenu
 */
public class DataMenuViewModel extends ViewModel {

    /** LiveData encapsulado que contiene los datos temporales del menú. */
    private MutableLiveData<DataMenu> dataMenu = new MutableLiveData<>();

    /**
     * Proporciona acceso de solo lectura a los datos del menú ({@link DataMenu}).
     * <p>
     * Se expone como {@link LiveData} para seguir el principio de encapsulamiento,
     * asegurando que la vista solo pueda observar los datos y no modificarlos directamente.
     * </p>
     *
     * @return - Devuelve un {@link LiveData} que contiene el objeto {@link DataMenu} actual.
     */
    public LiveData<DataMenu> getDataMenu() {
        return dataMenu;
    }

    /**
     * Actualiza el menú ({@link DataMenu}) almacenado en el ViewModel y notifica a los observadores.
     * <p>
     * Este método se utiliza para compartir la información del menú que se desea
     * mostrar o editar en la siguiente pantalla del flujo de navegación.
     * </p>
     *
     * @param menu - El objeto {@link DataMenu} con la información a compartir.
     */
    public void setDataMenu(DataMenu menu) {
        dataMenu.setValue(menu);
    }

    /**
     * Limpia los datos almacenados actualmente, estableciendo el valor a {@code null}.
     * <p>
     * Es recomendable llamar a este método al finalizar el flujo de edición o
     * visualización para evitar estados inconsistentes en futuras navegaciones
     * </p>
     */
    public void clear() {
        dataMenu.setValue(null);
    }
}
