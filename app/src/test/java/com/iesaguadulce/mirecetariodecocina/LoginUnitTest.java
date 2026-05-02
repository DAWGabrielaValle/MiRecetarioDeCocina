package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.security.NoSuchAlgorithmException;

/**
 * Pruebas unitarias para el algoritmo de cifrado SHA-512.
 * <p>
 * Esta prueba comprueba los siguientes aspectos:
 * 1. Consistencia: La contraseña siempre debe generar el mismo hash.
 * 2. Unicidad: Verificamos que el algoritmo distingue correctamente entre diferentes entradas.
 * 3. Integridad: Comprobamos que el algoritmo SHA-512 devuelve una cadena con la longitud estándar
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
@RunWith(RobolectricTestRunner.class)
public class LoginUnitTest {

    /**
     * Consistencia. La contraseña siempre debe generar el mismo hash. Si por algún
     * motivo el hash cambiara cada vez, el usuario nunca podría volver a entrar después de
     * registrarte, por lo tanto, la prueba fallaría.
     *
     * @throws NoSuchAlgorithmException - Excepción lanzada si el algoritmo no se encuentra.
     */
    @Test
    public void testHashPasswordConsistency() throws NoSuchAlgorithmException {
        // La contraseña "admin" siempre debe generar el mismo hash
        String pass = "admin";
        String hash1 = MainActivity.obtenerPassword(pass);
        String hash2 = MainActivity.obtenerPassword(pass);

        assertNotNull("El hash no debe ser nulo", hash1);
        assertEquals("El hash debe ser consistente para la misma contraseña", hash1, hash2);
    }

    /**
     * Unicidad. Verificamos que el algoritmo distingue correctamente entre diferentes entradas.
     *
     * @throws NoSuchAlgorithmException - Excepción lanzada si el algoritmo no se encuentra.
     */
    @Test
    public void testDifferentPasswordsProduceDifferentHashes() throws NoSuchAlgorithmException {
        // Diferentes contraseñas deben generar diferentes hashes
        String hashAdmin = MainActivity.obtenerPassword("admin");
        String hashChef = MainActivity.obtenerPassword("chef");

        assertNotEquals("Contraseñas diferentes deben generar hashes diferentes", hashAdmin, hashChef);
    }

    /**
     * Integridad. Comprobamos que el algoritmo SHA-512 devuelve una cadena con la longitud estándar
     * (128 carateres hexadecimal), lo que significa que el cifrado se está aplicando correctamente
     *
     * @throws NoSuchAlgorithmException - Excepción lanzada si el algoritmo no se encuentra.
     */
    @Test
    public void testHashIsNotEmpty() throws NoSuchAlgorithmException {
        String hash = MainActivity.obtenerPassword("1234");
        assertNotNull(hash);
        assertNotEquals("El hash no debe estar vacío", "", hash);
        // SHA-512 en hexadecimal siempre tiene 128 caracteres
        assertEquals("El hash SHA-512 debe tener 128 caracteres", 128, hash.length());
    }
}