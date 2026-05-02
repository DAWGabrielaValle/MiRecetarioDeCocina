package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentAyudaBinding;

/**
 * Fragmento que muestra información de ayuda sobre la aplicación.
 * <p>
 * Este fragmento muestra la ayuda correspondiente en los diferentes botones de ayuda
 * de los fragmentos principales de la aplicación, adaptando su contenido dinámicamente
 * según el origen de la consulta mediante argumentos.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 */
public class AyudaFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_ayuda.xml. */
    private FragmentAyudaBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public AyudaFragment() {
        // Required empty public constructor
    }

    /**
     * Inicializa el fragmento.
     *
     * @param savedInstanceState - Estado previo del fragmento, si existe.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Infla el diseño del fragmento y configura el objeto de binding.
     *
     * @param inflater  - {@link LayoutInflater} - El objeto LayoutInflater para inflar vistas.
     * @param container - {@link ViewGroup} - El contenedor del fragmento.
     * @param savedInstanceState - Estado previo del fragmento, si existe.
     *
     * @return - {@link View} - La vista raíz del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentAyudaBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura el contenido de ayuda dinámico basado en los argumentos recibidos.
     * <p>
     * El método extrae el parámetro "mensaje" de los {@code getArguments()} y, mediante un
     * bloque {@code switch}, establece el título y el cuerpo del texto de ayuda para:
     * <ul>
     *     <li><b>user:</b> Administración de Usuarios.</li>
     *     <li><b>recipe:</b> Gestión de Recetas.</li>
     *     <li><b>ingredient:</b> Catálogo de Ingredientes.</li>
     *     <li><b>menu:</b> Creación de Menús.</li>
     *     <li><b>plan:</b> Planificación Diaria.</li>
     * </ul>
     * </p>
     *
     * @param view               - {@link View} - La vista devuelta por {@link #onCreateView}.
     * @param savedInstanceState - Estado previo guardado, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperamos el mensaje de los argumentos
        if (getArguments() != null) {
            String mensaje = getArguments().getString("mensaje");
            String contenido = "";

            // Realiza las acciones necesarias con el mensaje
            switch (mensaje) {
                case "user":
                    binding.textViewTitle.setText(R.string.ayuda_admin_usuarios);
                    contenido = "Desde este panel puede gestionar los accesos a la aplicación:\n\n" +
                            "• Filtro: Busque usuarios por su identificador, por nombre de usuario o rol.\n\n" +
                            "• Registro: Permite crear nuevas cuentas asignando un nombre de usuario único y una contraseña segura.\n" +
                            "• Asignación de Roles: Determine si el usuario tendrá permisos de \"Administrador\" (gestión total) o \"Chef\" (gestión de recetas, menús y planificación de menús).\n\n" +
                            "• Edición: Modifique los datos personales de los usuarios existentes o actualice sus contraseñas.\n\n" +
                            "• Borrado: Quite de la lista usuarios que no desee que tengan acceso a la aplicación. \n NOTA: El usuario \"admin\" no puede borrarse.";
                    binding.textViewContent.setText(contenido);
                    break;
                case "recipe":
                    binding.textViewTitle.setText(R.string.ayuda_gestion_recetas);
                    contenido = "Organice sus creaciones culinarias:\n\n" +
                            "• Filtro: Busque recetas por su nombre o tiempo de preparación.\n\n" +
                            "• Registro: Indique nombre, descripción, familia y etiquetas.\n" +
                            "• Preparación: Detalle el modo de elaboración paso a paso.\n" +
                            "• Ingredientes: Asocie los productos necesarios a cada receta.\n\n" +
                            "• Edición: Modifique la receta a su gusto, añada ingredientes o elimine los que no desee, modifique el modo de preparación a su gusto.\n" +
                            "• Comentarios: Añada comentarios o ideas de presentación del plato.\n" +
                            "• Compra: Obtenga la lista de productos a comprar para preparar la receta. \n\n" +
                            "• Borrado: Quite de la lista recetas que ya no desee tener en su cocina. Recuerde que la receta no debe estar asociada a algún menú para poder borrarla.";
                    binding.textViewContent.setText(contenido);
                    break;
                case "ingredient":
                    binding.textViewTitle.setText(R.string.ayuda_gestion_ingredientes);
                    contenido = "Mantenga una amplia selección de ingredientes:\n\n" +
                            "• Filtro: Busque ingredientes por su nombre, familia o etiqueta (Vegetal, Carne, Pescado, etc.).\n\n" +
                            "• Registro: Añada ingredientes con su descripción, familia y/o etiqueta.\n" +
                            "• Clasificación: Organice por familia y/o etiqueta (Vegetal, Carne, Pescado, etc.) para facilitar la creación de recetas.\n\n" +
                            "• Edición: Modifique los ingredientes a su gusto.\n\n" +
                            "• Borrado: Quite de la lista los ingredientes que ya no utilice en ninguna receta.";
                    binding.textViewContent.setText(contenido);
                    break;
                case "menu":
                    binding.textViewTitle.setText(R.string.ayuda_gestion_menus);
                    contenido = "Diseñe propuestas gastronómicas:\n\n" +
                            "• Filtro: Busque menús por su nombre, tipo o etiqueta.\n\n" +
                            "• Registro: Añada menús con su descripción y tipo.\n" +
                            "• Combinación: Agrupe varias recetas bajo un mismo nombre de menú.\n" +
                            "• Organización: Clasifique por tipo de comida (Almuerzo, Cena) y/o etiqueta.\n\n" +
                            "• Edición: Modifique los menús a su gusto, añada nuevas recetas o elimine las que no desee.\n" +
                            "• Compra: Obtenga la lista de productos a comprar para preparar el menú seleccionado. \n\n" +
                            "• Borrado: Quite de la lista menús que ya no desee preparar o por nuevas ideas de temporada. Recuerde que el menú no debe estar asociado a ningún plan para poder borrarlo.";
                    binding.textViewContent.setText(contenido);
                    break;
                case "plan":
                    binding.textViewTitle.setText(R.string.ayuda_gestion_plan);
                    contenido = "Organice su calendario de comidas:\n\n" +
                            "• Filtro: Busque planes por su nombre, tipo o etiqueta.\n\n" +
                            "• Registro: Añada planes con su descripción, tipo y/o etiqueta.\n" +
                            "• Planes: Cree planificaciones de varios días.\n" +
                            "• Asignación: Defina qué menús se servirán en cada jornada del plan.\n" +
                            "• Control: Gestione su dieta semanal de forma visual.\n\n" +
                            "• Edición: Modifique los planes a su gusto, añada nuevos o elimine los que no desee.\n" +
                            "• Compra: Obtenga la lista de productos a comprar para realizar la planificación seleccionada.\n\n" +
                            "• Borrado: Quite de la lista planificaciones que ya no desee llevar a cabo.";

                    binding.textViewContent.setText(contenido);
                    break;
                default:
                    binding.textViewTitle.setText(R.string.ayuda_ayuda);
                    binding.textViewContent.setText(R.string.ayuda_no_implementada);
                    break;
            }
        }

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Cerramos el fragmento y volvemos al anterior
        binding.btnAyudaClose.setOnClickListener(v -> {
            navController.popBackStack();
        });
    }
}
