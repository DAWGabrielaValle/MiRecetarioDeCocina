package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los datos de un plan
 * <p>
 * Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 * transportar la información del plan entre diferentes capas de la aplicación
 * o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DataPlan {
    /** Identificador único del plan en la base de datos. */
    private int idPlan;
    /** Nombre descriptivo del plan. */
    private String nombre;
    /** Descripción detallada del plan. */
    private String descripcion;
    /** Tipo de plan (por ejemplo, "Desayuno", "Almuerzo", "Cena"). */
    private String tipo;
    /** Etiqueta personalizada para filtrado o agrupación. */
    private String etiqueta;
    /** Número de días del plan. */
    private int diasPlan;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del plan.
     *
     * @param idPlan        - int    - Identificador único del plan.
     * @param nombre        - String - Nombre del plan.
     * @param descripcion   - String - Descripción del plan.
     * @param tipo          - String - Tipo de plan.
     * @param etiqueta      - String - Etiqueta para agrupar el plan.
     * @param diasPlan      - int    - Número de días del plan.
     */
    public DataPlan(int idPlan, String nombre, String descripcion, String tipo, String etiqueta, int diasPlan) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.diasPlan = diasPlan;
    }

    /**
     * Getter  - Método que devuelve el identificador único del plan.
     * @return - int - Devuelve el identificador único del plan.
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Getter  - Método que devuelve el nombre del plan.
     * @return - String - Devuelve el nombre del plan.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Método que devuelve la descripción del plan.
     * @return - String - Devuelve la descripción del plan.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Método que devuelve el tipo del plan.
     * @return - String - Devuelve el tipo del plan.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Getter  - Método que devuelve la etiqueta del plan.
     * @return - String - Devuelve la etiqueta del plan.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Método que devuelve el número de días del plan.
     * @return - int - Devuelve el número de días del plan.
     */
    public int getDiasPlan() {
        return diasPlan;
    }

    /**
     * Setter - Método que cambia el identificador único del plan.
     * @param idPlan - int - Identificador único del plan.
     */
    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    /**
     * Setter - Método que cambia el nombre del plan.
     * @param nombre - String - Nombre del plan.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Setter - Método que cambia la descripción del plan.
     * @param descripcion - String - Descripción del plan.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Setter - Método que cambia el tipo del plan.
     * @param tipo - String - Tipo de plan.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Setter - Método que cambia la etiqueta del plan.
     * @param etiqueta - String - Etiqueta para agrupar el plan.
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Setter - Método que cambia el número de días del plan.
     * @param diasPlan - int - Número de días del plan.
     */
    public void setDiasPlan(int diasPlan) {
        this.diasPlan = diasPlan;
    }
}
