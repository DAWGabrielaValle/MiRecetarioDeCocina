package com.iesaguadulce.mirecetariodecocina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import com.iesaguadulce.mirecetariodecocina.databinding.ActivityMainBinding;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRepositorio;

import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.text.util.Linkify;

/**
 * Actividad principal de la aplicación.
 * <p>
 * Esta actividad proporciona una interfaz de inicio de sesión para los usuarios.
 * El usuario puede iniciar sesión con un nombre de usuario y una contraseña.
 * Si las credenciales son correctas, se redirige a la actividad correspondiente dependiendo de su rol (Chef o Administrador).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** Objeto de enlace para acceder a las vistas de la actividad mediante View Binding. */
    private ActivityMainBinding binding;

    /** Etiqueta para el registro de logs de la actividad. */
    private static final String TAG = "MainActivityLogin";

    /**
     * Inicializa la actividad y configura la interfaz de usuario.
     * Se configura la Toolbar y el botón de inicio de sesión.
     *
     * @param savedInstanceState - Estado previo de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la Toolbar
        setSupportActionBar(binding.topAppBar.toolbar);

        // Ocultamos el foco cuando se pierde el foco del campo de texto de la contraseña del usuario
        binding.passwordValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.passwordLabel.setError(null);
                ocultarTeclado(v);
            }
        });

        // Botón de inicio de sesión
        binding.btnInicioSesion.setOnClickListener(v-> {

            // Obtenemos los datos introducidos por el usuario
            String usuario = String.valueOf(binding.userValue.getText());
            String password = String.valueOf(binding.passwordValue.getText());

            UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(getApplication());
            usuarioRepositorio.getUsuarioRol(usuario).observe(this, usuarioRolJoin -> {
                // Si el usuario existe en la base de datos y la contraseña es correcta, iniciamos la actividad correspondiente
                if (usuarioRolJoin != null) {
                    try {
                        String passwordHASH = obtenerPassword(password);
                        // Si el usuario es administrador, iniciamos la actividad de administrador
                        if (usuarioRolJoin.getRol().equals("Administrador") && passwordHASH.equals(usuarioRolJoin.getPswd())) {
                            Intent i = new Intent(this, AdminActivity.class);
                            i.putExtra("idUser", usuario);
                            startActivity(i);
                            inicializaFormularioLogin();
                        // Si el usuario es chef, iniciamos la actividad de chef
                        } else if (usuarioRolJoin.getRol().equals("Chef") && passwordHASH.equals(usuarioRolJoin.getPswd())) {
                            Intent i = new Intent(this, ChefActivity.class);
                            i.putExtra("idUser", usuario);
                            startActivity(i);
                            inicializaFormularioLogin();
                        } else {
                            // Si las credenciales son incorrectas, mostramos un mensaje de error
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    // Si el usuario no existe en la base de datos, mostramos un mensaje de error
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Añadir el menú
        addMenuProvider(new MenuProvider() {

            /**
             * Crea el menú de opciones.
             *
             * @param menu         - {@link Menu} - El menú de opciones a crear.
             * @param menuInflater - El objeto {@link MenuInflater} para inflar el menú.
             */
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.help_menu, menu);
            }

            /**
             * Gestiona la acción a realizar cuando se selecciona un elemento del menú.
             * <p>
             * En esta implementación, se captura la selección del botón de ayuda
             * para mostrar un {@link AlertDialog} con información sobre el uso de la app
             * y enlaces clicables a las políticas de privacidad y borrado de datos.
             * </p>
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú que ha sido seleccionado.
             * @return -boolean - {@code true} si la acción ha sido gestionada, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.action_ayuda) {
                    String contenido = "Esta aplicación le permitirá organizar su cocina de manera profesional. Utilice la gestión de usuarios para crear cuentas y navegue entre la gestión de recetas, menús y su planificación de menús.\n\n" +
                            "Para iniciar sesión por primera vez puede ingresar utilizando el usuario admin, que le llevará a la gestión de cuentas, o chef, que le llevará a la gestión de recetas, menús y planificación de menús. Las contraseñas son admin y chef, pero se recomienda que las cambie para proteger su cuenta.\n\n" +
                            "Política de Privacidad:\nhttps://gist.githubusercontent.com/DAWGabrielaValle/652fb4f4a9dbc50a78c1c02df9b7296c/raw/9ebfc4395c93b938889f89161725b33a8799b24b/gistfile1.txt\n\n" +
                            "Borrado de Datos de Usuario:\nhttps://gist.githubusercontent.com/DAWGabrielaValle/8dcdc89c5f3af2c3fa771184faf2d62d/raw/b50f6a0c25785afc4a9a37ca4c410ef466715ac8/gistfile1.txt";


                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Bienvenidos a MiRecetarioDeCocina")
                            .setMessage(contenido)
                            .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                    // --- PASO CLAVE: Hacer los enlaces clicables ---
                    // Obtenemos el TextView del mensaje por su ID interno de Android
                    TextView messageView = dialog.findViewById(android.R.id.message);
                    if (messageView != null) {
                        // Esto permite que se pueda hacer scroll y clic en los enlaces
                        messageView.setMovementMethod(LinkMovementMethod.getInstance());
                        // Esto busca las URL en el texto y las convierte en enlaces azules
                        Linkify.addLinks(messageView, Linkify.WEB_URLS);
                    }

                    return true;
                }
                return false;
            }
        }, this, Lifecycle.State.RESUMED);

    }

    /**
     * Inicializa el formulario de inicio de sesión.
     */
    private void inicializaFormularioLogin() {
        binding.userValue.setText("");
        binding.passwordValue.setText("");
    }

    /**
     * Realiza el cifrado de la contraseña usando SHA-512.
     *
     * @param password - String - La contraseña en texto plano.
     *
     * @return - String - La contraseña cifrada en formato hexadecimal.
     * @throws NoSuchAlgorithmException - Si el algoritmo de cifrado no existe.
     */
    public static String obtenerPassword(String password) throws NoSuchAlgorithmException {
        String salt = "MiRecetarioDeCocina"; //getSalt();

        String securePassword = get_SHA_512_SecurePassword(password, salt);
        Log.i(TAG, securePassword);

        return securePassword;
    }

    /**
     * Realiza el cifrado de la contraseña usando SHA-512.
     *
     * @param passwordToHash - String - La contraseña en texto plano.
     * @param salt - String - El salt.
     *
     * @return - String - La contraseña cifrada en formato hexadecimal.
     */
    private static String get_SHA_512_SecurePassword(String passwordToHash,
                                                     String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error al cifrar la contraseña", e);
        }
        return generatedPassword;
    }

    /**
     * Genera un salt aleatorio.
     *
     * @return - String - El salt generado.
     * @throws NoSuchAlgorithmException - Si el algoritmo de cifrado no existe.
     */
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }

    /**
     * Oculta el teclado si está visible
     */
    private void ocultarTeclado(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
