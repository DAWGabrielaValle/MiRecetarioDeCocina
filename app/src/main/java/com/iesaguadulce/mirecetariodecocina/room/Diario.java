package com.iesaguadulce.mirecetariodecocina.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un registro en el diario de planificación de menús.
 * <p>
 * Esta clase define la estructura de la tabla "diario" en la base de datos local.
 * Almacena el orden del registro en el diario y el identificador del {@link Plan} al que pertenece.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "diario")
public class Diario {

    /** Identificador único del registro en el diario (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idDiario;

    /** Orden del registro en el diario. */
    public int orden;

    /** Identificador del {@link Plan} al que pertenece el registro. */
    public int idPlan;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param orden  - int - Orden del registro en el diario.
     * @param idPlan - int - Identificador del {@link Plan} al que pertenece el registro.
     */
    public Diario(int orden, int idPlan) {
        this.orden = orden;
        this.idPlan = idPlan;
    }

    /**
     * Getter  - Obtiene el identificador único del registro en el diario.
     * @return - int - Identificador del registro en el diario.
     */
    public int getIdDiario() {
        return idDiario;
    }

    /**
     * Getter  - Obtiene el orden del registro en el diario.
     * @return - int - Orden del registro en el diario.
     */
    public int getOrden() {
        return orden;
    }

    /**
     * Getter  - Obtiene el identificador del {@link Plan} al que pertenece el registro.
     * @return - int - Identificador del plan.
     */
    public int getIdPlan() {
        return idPlan;
    }
}
