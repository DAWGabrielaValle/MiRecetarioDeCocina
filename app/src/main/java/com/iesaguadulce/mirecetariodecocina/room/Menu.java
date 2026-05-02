package com.iesaguadulce.mirecetariodecocina.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un menú en la base de datos.
 * <p>
 * Esta clase define la estructura de la tabla "menu" en la base de datos local.
 * Almacena el nombre, la descripción, el tipo, la etiqueta y el identificador del {@link Usuario}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@Entity(tableName = "menu")
public class Menu {

    /** Identificador único del menú (Autogenerado). */
    @PrimaryKey(autoGenerate = true)
    public int idMenu;

    /** Nombre del menú. No puede estar vacío. */
    @NonNull
    public String nombre;

    /** Descripción del menú. */
    public String descripcion;

    /** Tipo de menú. */
    public String tipo;

    /** Etiqueta personalizada del menú. */
    public String etiqueta;

    /** Identificador del {@link Usuario} que creó el menú. No puede estar vacío. */
    @NonNull
    public String idUsuario;

    /**
     * Constructor
     * Crea una instancia de la clase con los datos proporcionados.
     *
     * @param nombre      - String - Nombre del menú.
     * @param descripcion - String - Descripción del menú.
     * @param tipo        - String - Tipo de menú.
     * @param etiqueta    - String - Etiqueta del menú.
     * @param idUsuario   - int    - Identificador del {@link Usuario} que creó el menú.
     */
    public Menu(@NonNull String nombre, String descripcion, String tipo, String etiqueta, @NonNull String idUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.idUsuario = idUsuario;
    }

    /**
     * Getter  - Obtiene el identificador único del menú.
     * @return - int - Identificador del menú.
     */
    public int getIdMenu() {
        return idMenu;
    }

    /**
     * Getter  - Obtiene el nombre del menú.
     * @return - String - Nombre del menú.
     */
    @NonNull
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter  - Obtiene la descripción del menú.
     * @return - String - Descripción del menú.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Getter  - Obtiene el tipo del menú.
     * @return - String - Tipo del menú.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Getter  - Obtiene la etiqueta del menú.
     * @return - String - Etiqueta del menú.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Getter  - Obtiene el identificador del {@link Usuario} que creó el menú.
     * @return - String - Identificador del usuario.
     */
    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setter - Establece el identificador único del menú.
     * @param idMenu - int - Identificador del menú.
     */
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }
}
