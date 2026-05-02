package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un plan en la base de datos.
 * <p>
 * Esta clase define la estructura de la tabla "plan" en la base de datos local.
 * Almacena el nombre, la descripción, el tipo, la etiqueta, los días y el identificador del {@link Usuario}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "plan")
public class Plan {

    /** Identificador único del plan (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idPlan;

    /** Nombre del plan. No puede estar vacío. */
    @NonNull
    public String nombre;

    /** Descripción del plan. */
    public String descripcion;

    /** Tipo de plan. */
    public String tipo;

    /** Etiqueta personalizada del plan. */
    public String etiqueta;

    /** Número de días del plan. */
    public int dias;

    /** Identificador del {@link Usuario} que creó el plan. No puede estar vacío. */
    @NonNull
    public String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param nombre      - String - Nombre del plan.
     * @param descripcion - String - Descripción del plan.
     * @param tipo        - String - Tipo de plan.
     * @param etiqueta    - String - Etiqueta del plan.
     * @param dias        - int    - Número de días del plan.
     * @param idUsuario   - int    - Identificador del {@link Usuario} que creó el plan.
     */
    public Plan(@NonNull String nombre, String descripcion, String tipo, String etiqueta, int dias, @NonNull String idUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.dias = dias;
        this.idUsuario = idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador único del plan.
     * @return - int - Identificador del plan.
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Getter  - Obtiene el nombre del plan.
     * @return - String - Nombre del plan.
     */
    @NonNull
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la descripción del plan.
     * @return - String - Descripción del plan.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Obtiene el tipo del plan.
     * @return - String - Tipo del plan.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Getter  - Obtiene la etiqueta del plan.
     * @return - String - Etiqueta del plan.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Obtiene el número de días del plan.
     * @return - int - Número de días del plan.
     */
    public int getDias() {
        return dias;
    }

    /**
     * Getter  - Obtiene el identificador del {@link Usuario} que creó el plan.
     * @return - String - Identificador del usuario.
     */
    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setter - Establece el identificador único del plan.
     * @param idPlan - int - Identificador del plan.
     */
    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }
}
