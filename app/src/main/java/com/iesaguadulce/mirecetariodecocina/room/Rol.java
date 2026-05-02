package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa la entidad {@link Rol} en la base de datos local.
 * <p>
 * Esta clase define la estructura de la tabla "roles" en la base de datos local.
 * Almacena el nombre del rol.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "rol")
public class Rol {

    /** Identificador único del rol (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idRol;

    /** Nombre del rol. No puede estar vacío. */
    @NonNull
    public String rol;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param rol - String - Nombre del rol.
     */
    public Rol(@NonNull String rol) {
        this.rol = rol;
    }

    /**
     * Getter  - Obtiene el identificador único del rol.
     * @return - int - Identificador del rol.
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Getter  - Obtiene el nombre del rol.
     * @return - String - Nombre del rol.
     */
    @NonNull
    public String getRol() {
        return rol;
    }
}
