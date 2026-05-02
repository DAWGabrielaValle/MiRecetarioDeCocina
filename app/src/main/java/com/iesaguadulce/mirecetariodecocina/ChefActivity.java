package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.iesaguadulce.mirecetariodecocina.databinding.ActivityChefBinding;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

/**
 * Actividad principal para el rol Chef.
 * <p>
 * Esta actividad sirve como contenedor para la gestión de recetas, ingredientes, menús y planificación de menús
 * utilizando un {@link NavHostFragment} y un {@link NavController} para
 * la navegación entre los diferentes fragmentos administrativos definidos
 * en el grafo de navegación.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class ChefActivity extends AppCompatActivity {

    /** Objeto de enlace para acceder a las vistas de la actividad mediante View Binding. */
    private ActivityChefBinding binding;

    /** Controlador de navegación para gestionar el flujo de fragmentos dentro de la actividad. */
    private NavController navController;

    /** Identificador del usuario chef que ha iniciado sesión. */
    String idUser;

    /** Etiqueta para el registro de logs de la actividad. */
    private static final String TAG = "ChefActivity";

    /**
     * Inicializa la actividad, configura la interfaz de usuario y establece
     * la navegación mediante {@link NavController}.
     *
     * @param savedInstanceState - Estado previo de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuramos el enlace de vistas
        binding = ActivityChefBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtenemos el identificador del usuario logueado
        idUser = getIntent().getExtras().getString("idUser");

        // Guardamos el ID del usuario en una variable global
        UserSharedViewModel userViewModel = new ViewModelProvider(this).get(UserSharedViewModel.class);
        userViewModel.setIdUser(idUser);

        // Configuramos la Toolbar
        setSupportActionBar(binding.chefTopBar.chefToolbar);

        //Configuración de la barra de navegación
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.chef_nav_host_fragment)).getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    /**
     * Gestiona la acción de retroceso en la barra de aplicaciones.
     * <p>
     * Permite que la flecha de retroceso de la Toolbar funcione correctamente
     * sincronizada con el historial del {@link NavController}.
     * </p>
     *
     * @return - boolean - {@code true} si la navegación hacia arriba fue gestionada por el NavController.
     */
    @Override
    public boolean onSupportNavigateUp() {
        // Permite que la flecha de retroceso de la Toolbar funcione con el NavController
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
