package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de un usuario
 *  <p>
 *  Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 *  transportar la información del usuario entre diferentes capas de la aplicación
 *  o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 *  </p>
 *
 *  @author Gabriela Valle Puente
 *  @version 1.2
 *  @since 1.0
 */
public class DataUsuario {
    /** Identificador único del usuario en la base de datos. */
    private String idUsuario;
    /** Identificador del rol del usuario. */
    private int idRol;
    /** Rol del usuario (por ejemplo, "Administrador", "Usuario"). */
    private String rol;
    /** Nombre del usuario. */
    private String nombreUsuario;
    /** Contraseña del usuario. */
    private String password;
    /** Fecha de alta del usuario. */
    private String fechaAlta;
    /** Fecha de finalización de actividad del usuario. */
    private String fechaFin;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del usuario.
     *
     * @param idUsuario     - String - Identificador del usuario.
     * @param idRol         - int    - Identificador del rol del usuario.
     * @param rol           - String - Rol del usuario.
     * @param nombreUsuario - String - Nombre del usuario.
     * @param password      - String - Contraseña del usuario.
     * @param fechaAlta     - String - Fecha de alta del usuario.
     * @param fechaFin      - String - Fecha de fin del usuario.
     */
    public DataUsuario(String idUsuario, int idRol, String rol, String nombreUsuario, String password, String fechaAlta, String fechaFin) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.rol = rol;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.fechaAlta = fechaAlta;
        this.fechaFin = fechaFin;
    }

    /**
     * Getter  - Método que devuelve el identificador único del usuario.
     * @return - String - Devuelve el identificador único del usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Getter  - Método que devuelve el identificador del rol del usuario.
     * @return - int - Devuelve el identificador del rol del usuario.
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * Getter  - Método que devuelve el rol del usuario.
     * @return - String - Devuelve el rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Getter  - Método que devuelve el nombre del usuario.
     * @return - String - Devuelve el nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Getter  - Método que devuelve la contraseña del usuario.
     * @return - String - Devuelve la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter  - Método que devuelve la fecha de registro del usuario.
     * @return - String - Devuelve la fecha de registro del usuario.
     */
    public String getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Getter  - Método que devuelve la fecha de finalización de actividad del usuario.
     * @return - String - Devuelve la fecha de finalización de actividad del usuario.
     */
    public String getFechaFin() {
        return fechaFin;
    }
}
