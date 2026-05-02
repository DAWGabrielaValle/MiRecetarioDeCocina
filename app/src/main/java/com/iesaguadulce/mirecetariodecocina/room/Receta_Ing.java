package com.iesaguadulce.mirecetariodecocina.room;

import androidx.room.Entity;

/**
 * Entidad que representa la relación entre {@link Receta} y {@link Ingrediente} en la base de datos local.
 * <p>
 * Esta clase define la estructura de la tabla "receta_ing" en la base de datos local.
 * Almacena el identificador de la receta, el identificador del ingrediente, la cantidad y la unidad.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "receta_ing",
        primaryKeys = {"idReceta", "idIngrediente"})
public class Receta_Ing {

    /** Identificador de la receta. */
    public int idReceta;

    /** Identificador del ingrediente. */
    public int idIngrediente;

    /** Cantidad del ingrediente. */
    public int cantidad;

    /** Unidad del ingrediente. */
    public String unidad;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param idReceta      - int    - Identificador de la receta.
     * @param idIngrediente - int    - Identificador del ingrediente.
     * @param cantidad      - int    - Cantidad del ingrediente.
     * @param unidad        - String - Unidad del ingrediente.
     */
    public Receta_Ing(int idReceta, int idIngrediente, int cantidad, String unidad) {
        this.idReceta = idReceta;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    /**
     * Getter  - Obtiene el identificador de la receta.
     * @return - int - Identificador de la receta.
     */
    public int getIdReceta() {
        return idReceta;
    }

    /**
     * Getter  - Obtiene el identificador del ingrediente.
     * @return - int - Identificador del ingrediente.
     */
    public int getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Getter  - Obtiene la cantidad del ingrediente.
     * @return - int - Cantidad del ingrediente.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Getter  - Obtiene la unidad del ingrediente.
     * @return - String - Unidad del ingrediente.
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Setter - Establece el identificador de la receta.
     * @param idReceta - int - Identificador de la receta.
     */
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }
}
