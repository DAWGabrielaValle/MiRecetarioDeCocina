package com.iesaguadulce.mirecetariodecocina.room;

/**
 * Entidad que representa la relación entre {@link Receta} y {@link Ingrediente} en la base de datos local.
 * <p>
 * Esta clase une datos de las entidades {@link Receta} y {@link Ingrediente} en una sola entidad,
 * permitiendo obtener información completa sobre los ingredientes de la receta {@link Receta_Ing}
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class RecetaIngredientesJoin {

    /** Identificador de la receta. */
    public int idReceta;

    /** Identificador del ingrediente. */
    public int idIngrediente;

    /** Cantidad del ingrediente. */
    public int cantidad;

    /** Unidad del ingrediente */
    public  String unidad;

    /** Nombre del ingrediente. */
    public String nombre;
}
