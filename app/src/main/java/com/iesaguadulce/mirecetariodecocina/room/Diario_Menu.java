package com.iesaguadulce.mirecetariodecocina.room;

import androidx.room.Entity;

/**
 * Entidad que representa el registro de un menú en un diario.
 * <p>
 * Esta clase define la estructura de la tabla "diario_menu" en la base de datos local.
 * Almacena el identificador del {@link Diario} y el identificador del {@link Menu}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "diario_menu",
        primaryKeys = {"idDiario", "idMenu"})
public class Diario_Menu {
    /** Identificador del {@link Diario}. */
    public int idDiario;

    /** Identificador del {@link Menu}. */
    public int idMenu;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param idDiario - int - Identificador del {@link Diario}.
     * @param idMenu   - int - Identificador del {@link Menu}.
     */
    public Diario_Menu(int idDiario, int idMenu) {
        this.idDiario = idDiario;
        this.idMenu = idMenu;
    }
}
