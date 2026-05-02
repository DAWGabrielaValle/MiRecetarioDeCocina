package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un comentario realizado por un usuario sobre una receta.
 * <p>
 * Esta clase define la estructura de la tabla "comentarios" en la base de datos local.
 * Almacena el texto del comentario, la fecha y las referencias al {@link Usuario} y la {@link Receta}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "comentarios")
public class Comentario {

    /** Identificador único del comentario (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idComentario;

    /** Contenido textual del comentario. No puede estar vacío. */
    @NonNull
    public String comentario;

    /** Fecha en la que se realizó el comentario. */
    public String fechaCom;

    /** Identificador del {@link Usuario} que realizó el comentario. No puede estar vacío. */
    @NonNull
    public String idUsuario;

    /** Identificador de la {@link Receta} comentada. */
    public int idReceta;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param comentario - String - Texto del comentario.
     * @param fechaCom   - String - Fecha de creación.
     * @param idUsuario  - int    - Identificador del {@link Usuario} autor del comentario.
     * @param idReceta   - int    - Identificador de la {@link Receta} vinculada.
     */
    public Comentario(@NonNull String comentario, String fechaCom, @NonNull String idUsuario, int idReceta) {
        this.comentario = comentario;
        this.fechaCom = fechaCom;
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
    }

    /**
     * Getter  - Obtiene el identificador único del comentario.
     * @return - int - Identificador del comentario.
     */
    public int getIdComentario() {
        return idComentario;
    }

    /**
     * Getter  - Obtiene el texto del comentario.
     * @return - String - Contenido del comentario.
     */
    @NonNull
    public String getComentario() {
        return comentario;
    }

    /**
     * Getter  - Obtiene la fecha del comentario.
     * @return - String - Fecha en formato String.
     */
    public String getFechaCom() {
        return fechaCom;
    }

    /**
     * Getter  - Obtiene el identificador del {@link Usuario} autor.
     * @return - String - Identificador del usuario.
     */
    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador de la {@link Receta} comentada.
     * @return - int - El identificador de la receta.
     */
    public int getIdReceta() {
        return idReceta;
    }
}
