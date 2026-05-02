package com.iesaguadulce.mirecetariodecocina.model;

/**
 * Clase que guarda de forma temporal los días de un plan (diarios)
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
public class PlanDia {
    /** Nombre del día. */
    private String dia;
    /** Orden del día. */
    private int orden;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del día del plan.
     *
     * @param dia   - String - Nombre del día.
     * @param orden - int    - Orden del día.
     */
    public PlanDia(String dia, int orden) {
        this.dia = dia;
        this.orden = orden;
    }

    /**
     * Getter   - Método que devuelve el nombre del día
     * @return  - String - Devuelve el nombre del día
     */
    public String getDia() {
        return dia;
    }

    /**
     * Getter   - Método que devuelve el orden del día
     * @return  - int - Devuelve el orden del día
     */
    public int getOrden() {
        return orden;
    }

}
