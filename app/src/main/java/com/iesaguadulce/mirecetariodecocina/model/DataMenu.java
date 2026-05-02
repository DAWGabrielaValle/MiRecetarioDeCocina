package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de un menú
 * <p>
 * Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 * transportar la información del menú entre diferentes capas de la aplicación
 * o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DataMenu {
    /** Identificador único del menú en la base de datos. */
    private int idMenu;
    /** Nombre descriptivo del menú. */
    private String nombre;
    /** Descripción detallada del menú. */
    private String descripcion;
    /** Tipo de menú (por ejemplo, "Desayuno", "Almuerzo", "Cena"). */
    private String tipo;
    /** Etiqueta personalizada para filtrado o agrupación. */
    private String etiqueta;
    /** Identificador del usuario que crea el menú. */
    private String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del menú.
     *
     * @param idMenu        - int    - Identificador único del menú.
     * @param nombre        - String - Nombre del menú.
     * @param descripcion   - String - Descripción del menú.
     * @param tipo          - String - Tipo de menú.
     * @param etiqueta      - String - Etiqueta para agrupar el menú.
     * @param idUsuario     - String - Identificador del usuario que crea el menú.
     */
    public DataMenu(int idMenu, String nombre, String descripcion, String tipo, String etiqueta, String idUsuario) {
        this.idMenu = idMenu;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.idUsuario = idUsuario;
    }

    /**
     * Getter  - Método que devuelve el identificador único del menú.
     * @return - int - Devuelve el identificador único del menú.
     */
    public int getIdMenu() {
        return idMenu;
    }

    /**
     * Getter  - Método que devuelve el nombre del menú.
     * @return - String - Devuelve el nombre del menú.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Método que devuelve la descripción del menú.
     * @return - String - Devuelve la descripción del menú.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Método que devuelve el tipo del menú.
     * @return - String - Devuelve el tipo del menú.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Getter  - Método que devuelve la etiqueta del menú.
     * @return - String - Devuelve la etiqueta del menú.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Método que devuelve el identificador del usuario que crea el menú.
     * @return - String - Devuelve el identificador del usuario que crea el menú.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setter - Método que cambia el identificador único del menú.
     * @param idMenu - int - Identificador único del menú.
     */
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * Setter - Método que cambia el nombre del menú.
     * @param nombre - String - Nombre del menú.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Setter - Método que cambia la descripción del menú.
     * @param descripcion - String - Descripción del menú.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Setter - Método que cambia el tipo del menú.
     * @param tipo - String - Tipo de menú.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Setter - Método que cambia la etiqueta del menú.
     * @param etiqueta - String - Etiqueta para agrupar el menú.
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Setter - Método que cambia el identificador del usuario que crea el menú.
     * @param idUsuario - String - Identificador del usuario que crea el menú.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}