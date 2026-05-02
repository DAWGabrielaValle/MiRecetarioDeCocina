package com.iesaguadulce.mirecetariodecocina.room;

import androidx.room.Entity;

/**
 * Entidad que representa la relación entre {@link Menu} y {@link Receta} en la base de datos.
 * <p>
 * Esta clase define la estructura de la tabla "menu_rec" en la base de datos local.
 * Almacena el identificador del {@link Menu} y el identificador de la {@link Receta}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "menu_rec",
        primaryKeys = {"idMenu", "idReceta"})
public class Menu_Rec {

    /** Identificador del {@link Menu}. */
    public int idMenu;

    /** Identificador de la {@link Receta}. */
    public int idReceta;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param idMenu   - int - Identificador del {@link Menu}.
     * @param idReceta - int - Identificador de la {@link Receta}.
     */
    public Menu_Rec(int idMenu, int idReceta) {
        this.idMenu = idMenu;
        this.idReceta = idReceta;
    }
}
