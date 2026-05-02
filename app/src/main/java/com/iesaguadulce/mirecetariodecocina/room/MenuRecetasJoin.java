package com.iesaguadulce.mirecetariodecocina.room;

/**
 * Entidad que representa la relación entre {@link Menu} y {@link Receta} en la base de datos.
 * <p>
 * Esta clase une datos de las entidades {@link Menu} y {@link Receta} en una sola entidad,
 * permitiendo obtener información completa sobre las recetas de menú {@link Menu_Rec}
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class MenuRecetasJoin {

    /** Identificador del {@link Menu}. */
    public int idMenu;

    /** Identificador de la {@link Receta}. */
    public int idReceta;

    /** Nombre de la receta. */
    public String nombre;

    /** Descripción de la receta. */
    public String descripcion;
}
