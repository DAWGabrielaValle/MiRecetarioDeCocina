package com.iesaguadulce.mirecetariodecocina.room;

/**
 * Clase que representa un registro de un menú en un diario.
 * <p>
 * Esta clase une datos de las entidades {@link Diario} y {@link Menu} en una sola entidad,
 * permitiendo obtener información completa sobre el menú en el diario {@link Diario_Menu}
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class DiarioMenuJoin {

    /** Identificador del {@link Diario}. */
    public int idDiario;

    /** Identificador del {@link Menu}. */
    public int idMenu;

    /** Nombre del menú. */
    public String nombre;

    /** Descripción del menú. */
    public String descripcion;

    /** Orden del diario. */
    public int orden;
}
