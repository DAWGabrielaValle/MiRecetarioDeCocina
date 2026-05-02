package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Entidad que representa la relación entre {@link Receta} y {@link Usuario} en la base de datos local.
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "receta_usu",
        primaryKeys = {"idReceta", "idUsuario"})
public class Receta_Usu {

    /** Identificador de la receta. */
    public int idReceta;

    /** Identificador del usuario. */
    @NonNull
    public String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param idReceta  - int    - Identificador de la receta.
     * @param idUsuario - String - Identificador del usuario.
     */
    public Receta_Usu(int idReceta, @NonNull String idUsuario) {
        this.idReceta = idReceta;
        this.idUsuario = idUsuario;
    }
}
