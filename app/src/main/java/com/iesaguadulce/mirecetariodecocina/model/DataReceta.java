package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de una receta
 * <p>
 * Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 * transportar la información de la receta entre diferentes capas de la aplicación
 * o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DataReceta {
    /** Identificador único de la receta en la base de datos. */
    private int idReceta;
    /** Nombre descriptivo de la receta. */
    private String nombre;
    /** Descripción detallada de la receta. */
    private String descripcion;
    /** Nombre del archivo de imagen asociado en los recursos de la aplicación. */
    private String imagen;
    /** Tiempo de preparación de la receta en minutos. */
    private int tiempoPrep;
    /** Familia o categoría a la que pertenece la receta. */
    private String familia;
    /** Etiqueta personalizada para filtrado o agrupación. */
    private String etiqueta;
    /** Modo de preparación de la receta. */
    private String modoPrep;
    /** Identificador del usuario que crea la receta. */
    private String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos de la receta.
     *
     * @param idReceta      - int    - Identificador único de la receta.
     * @param nombre        - String - Nombre de la receta.
     * @param descripcion   - String - Breve descripción de la receta.
     * @param imagen        - String - Nombre del fichero de la imagen de la receta.
     * @param tiempoPrep    - int    - Tiempo de preparación de la receta.
     * @param familia       - String - Familia a la que pertenece la receta.
     * @param etiqueta      - String - Etiqueta para agrupar la receta.
     * @param modoPrep      - String - Modo de preparación de la receta.
     * @param idUsuario     - String - Identificador del usuario que crea la receta.
     */
    public DataReceta(int idReceta, String nombre, String descripcion, String imagen, int tiempoPrep, String familia, String etiqueta, String modoPrep, String idUsuario) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tiempoPrep = tiempoPrep;
        this.familia = familia;
        this.etiqueta = etiqueta;
        this.modoPrep = modoPrep;
        this.idUsuario = idUsuario;
    }

    /**
     * Getter  - Método que devuelve el identificador único de la receta.
     * @return - int - Devuelve el identificador único de la receta.
     */
    public int getIdReceta() {
        return idReceta;
    }

    /**
     * Getter  - Método que devuelve el nombre de la receta.
     * @return - String - Devuelve el nombre de la receta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Método que devuelve la descripción de la receta.
     * @return - String - Devuelve la descripción de la receta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Método que devuelve el nombre del fichero de la imagen de la receta.
     * @return - String - Devuelve el nombre del fichero de la imagen de la receta.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Getter  - Método que devuelve el tiempo de preparación de la receta.
     * @return - int - Devuelve el tiempo de preparación de la receta.
     */
    public int getTiempoPrep() {
        return tiempoPrep;
    }

    /**
     * Getter  - Método que devuelve la familia a la que pertenece la receta.
     * @return - String - Devuelve la familia a la que pertenece la receta.
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * Getter  - Método que devuelve la etiqueta para agrupar la receta.
     * @return - String - Devuelve la etiqueta para agrupar la receta.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Método que devuelve el modo de preparación de la receta.
     * @return - String - Devuelve el modo de preparación de la receta.
     */
    public String getModoPrep() {
        return modoPrep;
    }

    /**
     * Getter  - Método que devuelve el identificador del usuario que crea la receta.
     * @return - String - Devuelve el identificador del usuario que crea la receta.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setter - Método que cambia el identificador único de la receta.
     * @param idReceta - int - Identificador único de la receta.
     */
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * Setter - Método que cambia el nombre de la receta.
     * @param nombre - String - Nombre de la receta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Setter - Método que cambia la descripción de la receta.
     * @param descripcion - String - Descripción de la receta.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Setter - Método que cambia el nombre del fichero de la imagen de la receta.
     * @param imagen - String - Nombre del fichero de la imagen de la receta.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Setter - Método que cambia el tiempo de preparación de la receta.
     * @param tiempoPrep - int - Tiempo de preparación de la receta.
     */
    public void setTiempoPrep(int tiempoPrep) {
        this.tiempoPrep = tiempoPrep;
    }

    /**
     * Setter - Método que cambia la familia a la que pertenece la receta.
     * @param familia - String - Familia a la que pertenece la receta.
     */
    public void setFamilia(String familia) {
        this.familia = familia;
    }

    /**
     * Setter - Método que cambia la etiqueta para agrupar la receta.
     * @param etiqueta - String - Etiqueta para agrupar la receta.
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Setter - Método que cambia el modo de preparación de la receta.
     * @param modoPrep - String - Modo de preparación de la receta.
     */
    public void setModoPrep(String modoPrep) {
        this.modoPrep = modoPrep;
    }

    /**
     * Setter - Método que cambia el identificador del usuario que crea la receta.
     * @param idUsuario - String - Identificador del usuario que crea la receta.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}

