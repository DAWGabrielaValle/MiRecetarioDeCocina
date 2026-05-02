package com.iesaguadulce.mirecetariodecocina.viewmodel;

import androidx.lifecycle.ViewModel;

/**
 * ViewModel compartido para la gestión del identificador del usuario en la aplicación.
 * <p>
 * Esta clase actúa como intermediario entre la vista y el resto de la aplicación,
 * permitiendo compartir datos entre diferentes componentes de la aplicación.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.0
 * @since 1.0
 */
public class UserSharedViewModel extends ViewModel {

    /** Identificador único del usuario. */
    private String idUser;

    /**
     * Obtiene el identificador del usuario.
     * @return - Devuelve el identificador del usuario.
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Establece el identificador del usuario.
     * @param idUser - El nuevo identificador del usuario.
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
