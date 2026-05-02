package com.iesaguadulce.mirecetariodecocina.model;

import java.util.Objects;

/**
 * Clase que guarda de forma temporal los datos de un menú que se añadirá
 * a un plan
 * <p>
 * Esta clase se utiliza como un objeto de transferencia de datos (DTO) para
 * transportar la información del menú entre diferentes capas de la aplicación
 * o fragmentos sin necesidad de realizar consultas constantes a la base de datos.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class PlanMenu {
    /** Identificador único del menú en la base de datos. */
    private int idMenu;
    /** Nombre descriptivo del menú. */
    private String nombre;
    /** Descripción detallada del menú. */
    private String descripcion;
    /** Orden del diario en el plan. */
    private int orden;

    /**
     * Constructor
     * Crea una instancia de la clase e inicializa todos los datos del menú.
     *
     * @param idMenu      - int    - Identificador del menú.
     * @param nombre      - String - Nombre del menú.
     * @param descripcion - String - Descripción del menú.
     * @param orden       - int    - Orden del diario en el plan.
     */
    public PlanMenu(int idMenu, String nombre, String descripcion, int orden) {
        this.idMenu = idMenu;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.orden = orden;
    }

    /**
     * Getter  - Método que devuelve el identificador del menú.
     * @return - int - Devuelve el identificador del menú.
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
     * Getter  - Método que devuelve el orden del menú.
     * @return - int - Devuelve el orden del menú.
     */
    public int getOrden() {
        return orden;
    }

    // Métodos añadidos para encontrar el elemento correcto para borrar, ya que es una lista filtrada por día.
    /**
     * Método que compara dos objetos de tipo PlanMenu para ver si son iguales.
     * @param o - Object  - Objeto a comparar.
     * @return  - boolean - Devuelve true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanMenu planMenu = (PlanMenu) o;
        return idMenu == planMenu.idMenu && orden == planMenu.orden && Objects.equals(nombre, planMenu.nombre) && Objects.equals(descripcion, planMenu.descripcion);
    }

    /**
     * Método que devuelve el código hash del objeto.
     * @return - int - Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMenu, nombre, descripcion, orden);
    }
}
