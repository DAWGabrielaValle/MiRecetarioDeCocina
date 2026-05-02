package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de un ingrediente
 * <p>
 * Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 * transportar la información del ingrediente entre diferentes capas de la aplicación
 * o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DataIngrediente {
    /** Identificador único del ingrediente en la base de datos. */
    private int idIngrediente;
    /** Nombre descriptivo del ingrediente (ej. "Arroz", "Pollo"). */
    private String nombre;
    /** Familia o categoría a la que pertenece (ej. "Carnes", "Cereales"). */
    private String familia;
    /** Etiqueta personalizada para filtrado o agrupación. */
    private String etiqueta;
    /** Descripción detallada de las propiedades del ingrediente. */
    private String descripcion;
    /** Nombre del archivo de imagen asociado en los recursos de la aplicación. */
    private String imagen;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del ingrediente.
     *
     * @param idIngrediente - int    - Identificador único del ingrediente.
     * @param nombre        - String - Nombre del ingrediente.
     * @param familia       - String - Familia a la que pertenece el ingrediente.
     * @param etiqueta      - String - Etiqueta para agrupar el ingrediente.
     * @param descripcion   - String - Descripción del ingrediente.
     * @param imagen        - String - Nombre del fichero de la imagen del ingrediente.
     */
    public DataIngrediente(int idIngrediente, String nombre, String familia, String etiqueta, String descripcion, String imagen) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.familia = familia;
        this.etiqueta = etiqueta;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    /**
     * Getter  - Método que devuelve el identificador único del ingrediente.
     * @return - int - Devuelve el identificador único del ingrediente.
     */
    public int getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Getter  - Método que devuelve el nombre del ingrediente.
     * @return - String - Devuelve el nombre del ingrediente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Método que devuelve la familia del ingrediente.
     * @return - String - Devuelve la familia del ingrediente.
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * Getter  - Método que devuelve la etiqueta del ingrediente.
     * @return - String - Devuelve la etiqueta del ingrediente.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Método que devuelve la descripción del ingrediente.
     * @return - String - Devuelve la descripción del ingrediente.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Método que devuelve el nombre del fichero de la imagen del ingrediente.
     * @return - String - Devuelve el nombre del fichero de la imagen del ingrediente.
     */
    public String getImagen() {
        return imagen;
    }
}


