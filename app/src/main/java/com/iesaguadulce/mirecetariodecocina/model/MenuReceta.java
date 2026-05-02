package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de una receta que se añadirá
 * a un menú
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
public class MenuReceta {
    /** Identificador único de la receta en la base de datos. */
    private int idReceta;
    /** Nombre descriptivo de la receta. */
    private String nombre;
    /** Descripción detallada de la receta. */
    private String descripcion;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos de la receta.
     *
     * @param idReceta      - int    - Identificador de la receta.
     * @param nombre        - String - Nombre de la receta.
     * @param descripcion   - String - Descripción de la receta.
     */
    public MenuReceta(int idReceta, String nombre, String descripcion) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
}
