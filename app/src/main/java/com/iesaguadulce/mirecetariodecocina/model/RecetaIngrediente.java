package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de un ingrediente de una receta
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
public class RecetaIngrediente {
    /** Identificador único del ingrediente en la base de datos. */
    private int idIngrediente;
    /** Nombre descriptivo del ingrediente. */
    private String nombre;
    /** Cantidad del ingrediente. */
    private int cantidad;
    /** Unidad del ingrediente. */
    private String unidad;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del ingrediente.
     *
     * @param idIngrediente - int    - Identificador del ingrediente.
     * @param nombre        - String - Nombre del ingrediente.
     * @param cantidad      - int    - Cantidad del ingrediente.
     * @param unidad        - String - Unidad del ingrediente.
     */
    public RecetaIngrediente(int idIngrediente, String nombre, int cantidad, String unidad) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    /**
     * Getter  - Método que devuelve el identificador del ingrediente.
     * @return - int - Devuelve el identificador del ingrediente.
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
     * Getter  - Método que devuelve la cantidad del ingrediente.
     * @return - int - Devuelve la cantidad del ingrediente.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Getter  - Método que devuelve la unidad del ingrediente.
     * @return - String - Devuelve la unidad del ingrediente.
     */
    public String getUnidad() {
        return unidad;
    }
}
