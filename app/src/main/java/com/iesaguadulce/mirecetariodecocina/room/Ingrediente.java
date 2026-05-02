package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un ingrediente en la base de datos.
 * <p>
 * Esta clase define la estructura de la tabla "ingredientes" en la base de datos local.
 * Almacena el nombre, la descripción, la imagen, la familia y la etiqueta del ingrediente.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "ingredientes")
public class Ingrediente {

    /** Identificador único del ingrediente (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idIngrediente;

    /** Nombre del ingrediente. No puede estar vacío. */
    @NonNull
    public String nombre;

    /** Descripción del ingrediente. */
    public String descripcion;

    /** Imagen del ingrediente. */
    public String imagen;

    /** Familia o categoría del ingrediente. */
    public String familia;

    /** Etiqueta personalizada del ingrediente. */
    public String etiqueta;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param nombre      - String - Nombre del ingrediente.
     * @param descripcion - String - Descripción del ingrediente.
     * @param imagen      - String - Imagen del ingrediente.
     * @param familia     - String - Familia del ingrediente.
     * @param etiqueta    - String - Etiqueta del ingrediente.
     */
    public Ingrediente(@NonNull String nombre, String descripcion, String imagen, String familia, String etiqueta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.familia = familia;
        this.etiqueta = etiqueta;
    }

    /**
     * Getter  - Obtiene el identificador único del ingrediente.
     * @return - int - Identificador del ingrediente.
     */
    public int getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Getter  - Obtiene el nombre del ingrediente.
     * @return - String - Nombre del ingrediente.
     */
    @NonNull
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la descripción del ingrediente.
     * @return - String - Descripción del ingrediente.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Obtiene la imagen del ingrediente.
     * @return - String - Imagen del ingrediente.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Getter  - Obtiene la familia del ingrediente.
     * @return - String - Familia del ingrediente.
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * Getter  - Obtiene la etiqueta del ingrediente.
     * @return - String - Etiqueta del ingrediente.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Setter - Establece el identificador único del ingrediente.
     * @param idIngrediente - int - Identificador del ingrediente.
     */
    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * Setter - Establece la imagen del ingrediente.
     * @param imagen - String - Imagen del ingrediente.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
