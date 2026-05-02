package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa la entidad {@link Usuario} en la base de datos local.
 * <p>
 * Esta clase define la estructura de la tabla "usuarios" en la base de datos local.
 * Almacena el nombre, el rol, la contraseña, la fecha de alta y la fecha de fin.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "usuario")
public class Usuario {

    /** Identificador único del usuario (Autogenerado). */
    @PrimaryKey @NonNull
    public String idUsuario;

    /** Identificador del rol del usuario. */
    public int idRol;

    /** Nombre del usuario. No puede estar vacío. */
    @NonNull
    public String nombre;

    /** Contraseña del usuario. No puede estar vacío. */
    @NonNull
    public String pswd;

    /** Fecha de alta del usuario. */
    public String fechaAlta;

    /** Fecha de fin del usuario. */
    public String fechaFin;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param idUsuario - String - Identificador del usuario.
     * @param idRol     - int    - Identificador del rol del usuario.
     * @param nombre    - String - Nombre del usuario.
     * @param pswd      - String - Contraseña del usuario.
     * @param fechaAlta - String - Fecha de alta del usuario.
     * @param fechaFin  - String - Fecha de fin del usuario.
     */
    public Usuario(@NonNull String idUsuario, int idRol, @NonNull String nombre, @NonNull String pswd, String fechaAlta, String fechaFin) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombre = nombre;
        this.pswd = pswd;
        this.fechaAlta = fechaAlta;
        this.fechaFin = fechaFin;
    }

    /**
     * Getter  - Obtiene el identificador único del usuario.
     * @return - String - Identificador del usuario.
     */
    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador del rol del usuario.
     * @return - int - Identificador del rol del usuario.
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Getter  - Obtiene el nombre del usuario.
     * @return - String - Nombre del usuario.
     */
    @NonNull
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la contraseña del usuario.
     * @return - String - Contraseña del usuario.
     */
    @NonNull
    public String getPswd() {
        return pswd;
    }

    /**
     * Getter  - Obtiene la fecha de alta del usuario.
     * @return - String - Fecha de alta del usuario.
     */
    public String getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Getter  - Obtiene la fecha de fin del usuario.
     * @return - String - Fecha de fin del usuario.
     */
    public String getFechaFin() {
        return fechaFin;
    }
}
