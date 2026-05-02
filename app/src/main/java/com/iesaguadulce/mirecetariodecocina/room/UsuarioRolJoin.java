package com.iesaguadulce.mirecetariodecocina.room;

/**
 * Entidad que representa la relación entre {@link Usuario} y {@link Rol} en la base de datos local.
 * <p>
 * Esta clase une datos de las entidades {@link Usuario} y {@link Rol} en una sola entidad,
 * permitiendo obtener información completa sobre el rol del usuario {@link UsuarioRolJoin}
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class UsuarioRolJoin {

    /** Identificador del usuario. */
    public String idUsuario;

    /** Identificador del rol. */
    public int idRol;

    /** Nombre del rol. */
    public String rol;

    /** Nombre del usuario. */
    public String nombre;

    /** Contraseña del usuario. */
    public String pswd;

    /** Fecha de alta del usuario. */
    public String fechaAlta;

    /** Fecha de fin del usuario. */
    public String fechaFin;

    /**
     * Getter  - Obtiene el identificador del usuario.
     * @return - String - Identificador del usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador del rol.
     * @return - int - Identificador del rol.
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Getter  - Obtiene el nombre del rol.
     * @return - String - Nombre del rol.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Getter  - Obtiene el nombre del usuario.
     * @return - String - Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la contraseña del usuario.
     * @return - String - Contraseña del usuario.
     */
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
