package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa una receta en la base de datos.
 * <p>
 * Esta clase define la estructura de la tabla "recetas" en la base de datos local.
 * Almacena el nombre, la descripción, la imagen, el tiempo de preparación, la familia, la etiqueta, el modo de preparación y el identificador del {@link Usuario}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "recetas")
public class Receta {

    /** Identificador único de la receta (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idReceta;

    /** Nombre de la receta. No puede estar vacío. */
    @NonNull
    public String nombre;

    /** Descripción de la receta. */
    public String descripcion;

    /** Imagen de la receta. */
    public String imagen;

    /** Tiempo de preparación de la receta. */
    public int tiempoPrep;

    /** Familia o categoría de la receta. */
    public String familia;

    /** Etiqueta personalizada de la receta. */
    public String etiqueta;

    /** Modo de preparación de la receta. */
    public String modoPrep;

    /** Identificador del {@link Usuario} que creó la receta. No puede estar vacío. */
    @NonNull
    public String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param nombre      - String - Nombre de la receta.
     * @param descripcion - String - Descripción de la receta.
     * @param imagen      - String - Imagen de la receta.
     * @param tiempoPrep  - int    - Tiempo de preparación de la receta.
     * @param familia     - String - Familia o categoría de la receta.
     * @param etiqueta    - String - Etiqueta personalizada de la receta.
     * @param modoPrep    - String - Modo de preparación de la receta.
     * @param idUsuario   - int    - Identificador del {@link Usuario} que creó la receta.
     */
    public Receta(@NonNull String nombre, String descripcion, String imagen, int tiempoPrep, String familia, String etiqueta, String modoPrep, @NonNull String idUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tiempoPrep = tiempoPrep;
        this.familia = familia;
        this.etiqueta = etiqueta;
        this.modoPrep = modoPrep;
        this.idUsuario = idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador único de la receta.
     * @return - int - Identificador de la receta.
     */
    public int getIdReceta() {
        return idReceta;
    }

    /**
     * Getter  - Obtiene el nombre de la receta.
     * @return - String - Nombre de la receta.
     */
    @NonNull
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la descripción de la receta.
     * @return - String - Descripción de la receta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Obtiene la imagen de la receta.
     * @return - String - Imagen de la receta.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Getter  - Obtiene el tiempo de preparación de la receta.
     * @return - int - Tiempo de preparación de la receta.
     */
    public int getTiempoPrep() {
        return tiempoPrep;
    }

    /**
     * Getter  - Obtiene la familia o categoría de la receta.
     * @return - String - Familia o categoría de la receta.
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * Getter  - Obtiene la etiqueta personalizada de la receta.
     * @return - String - Etiqueta personalizada de la receta.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Obtiene el modo de preparación de la receta.
     * @return - String - Modo de preparación de la receta.
     */
    public String getModoPrep() {
        return modoPrep;
    }

    /**
     * Getter  - Obtiene el identificador del {@link Usuario} que creó la receta.
     * @return - String - Identificador del usuario.
     */
    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setter - Establece el identificador único de la receta.
     * @param idReceta - int - Identificador de la receta.
     */
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * Setter - Establece el nombre de la receta.
     * @param imagen - String - Imagen de la receta.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
