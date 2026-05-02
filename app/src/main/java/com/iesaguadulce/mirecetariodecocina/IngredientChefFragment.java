package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentIngredientChefBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;
import com.iesaguadulce.mirecetariodecocina.ui.IngredientesAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataIngredienteViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.IngredientesViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Receta_IngViewModel;

import java.util.List;

/**
 * Fragmento para gestionar el listado de ingredientes.
 * <p>
 * Este fragmento se encarga de mostrar la lista de ingredientes y permitir la gestión de los mismos.
 * En este fragmento se pueden añadir, editar y eliminar ingredientes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredientesAdapter
 */
public class IngredientChefFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_ingredient_chef.xml. */
    private FragmentIngredientChefBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de ingredientes. */
    private IngredientesViewModel viewModel;

    /** ViewModel para la gestión de ingredientes de las recetas. */
    private Receta_IngViewModel recetaIngViewModel;


    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos del ingrediente actual. */
    private DataIngredienteViewModel dataIngredienteViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public IngredientChefFragment() {
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
     * Inicializa los ViewModels necesarios para el fragmento.
     * <p>
     * Se utilizan proveedores de ViewModel con ámbito de actividad (requireActivity)
     * para los datos compartidos temporalmente en memoria entre fragmentos.
     * </p>
     *
     * @param inflater  - {@link LayoutInflater} - El objeto LayoutInflater para inflar vistas.
     * @param container - {@link ViewGroup} - El contenedor del fragmento.
     * @param savedInstanceState - Estado previo del fragmento, si existe.
     *
     * @return - {@link View} - La vista raíz del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(IngredientesViewModel.class);
        recetaIngViewModel = new ViewModelProvider(this).get(Receta_IngViewModel.class);
        dataIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataIngredienteViewModel.class);
        return (binding = FragmentIngredientChefBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de ingredientes, el botón de añadir y el botón de guardar.
     * Manejo de la opción de editar al dar clic en un ingrediente y la opción de eliminar.
     * Configura el menú de ayuda y el buscador de ingredientes.
     * Permite la navegación entre recetas, menús y planificación utilizando el menú inferior.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botones de navegación del menú inferior
        binding.menuToggleButtonChef.btnRecipe.setOnClickListener(v -> {
            navController.navigate(R.id.action_ingredientChefFragment_to_inicioChefFragment);
        });
        binding.menuToggleButtonChef.btnMenu.setOnClickListener(v -> {
            navController.navigate(R.id.action_ingredientChefFragment_to_menuChefFragment);
        });
        binding.menuToggleButtonChef.btnPlan.setOnClickListener(v -> {
            navController.navigate(R.id.action_ingredientChefFragment_to_planChefFragment);
        });

        // Botón flotante para añadir un ingrediente
        binding.btnFabIngredient.setOnClickListener(v -> {
            navController.navigate(R.id.action_ingredientChefFragment_to_dataIngredientFragment);
        });

        // Asignamos el adaptador al RecyclerView
        IngredientesAdapter adapter = new IngredientesAdapter();
        binding.rvIngredients.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvIngredientsSearch.setAdapter(adapter);

        // Configuramos el listener para eliminar un ingrediente
        adapter.setOnIngredienteDeleteListener(ingrediente -> {
            // Comprobar que el ingrediente NO se encuentra en alguna receta
            recetaIngViewModel.getAllRecetas(ingrediente.getIdIngrediente()).observe(getViewLifecycleOwner(), recetas -> {
                if (recetas.isEmpty()) {
                    viewModel.delete(ingrediente);
                    Toast.makeText(this.getContext(), "Ingrediente eliminado: " + ingrediente.getNombre(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getContext(), "No se puede eliminar el ingrediente: " + ingrediente.getNombre() + " porque tiene recetas asociadas", Toast.LENGTH_LONG).show();
                }
            });
        });

        // Configuramos el listener para editar un ingrediente
        adapter.setOnIngredienteViewContentListener(ingrediente -> {
            dataIngredienteViewModel.setDataIngrediente(new DataIngrediente(ingrediente.getIdIngrediente(), ingrediente.getNombre(), ingrediente.getFamilia(), ingrediente.getEtiqueta(), ingrediente.getDescripcion(), ingrediente.getImagen()));
            navController.navigate(R.id.action_ingredientChefFragment_to_dataIngredientFragment);
        });

        // Ocultar el botón flotante cuando se desplaza hacia arriba
        binding.rvIngredients.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabIngredient.isShown()) {
                    binding.btnFabIngredient.hide();
                } else if (dy < 0 && !binding.btnFabIngredient.isShown()) {
                    binding.btnFabIngredient.show();
                }
            }
        });

        // Observamos la lista de ingredientes y actualizamos el adaptador
        viewModel.getAllIngredientes().observe(getViewLifecycleOwner(), new Observer<List<Ingrediente>>() {
            @Override
            public void onChanged(List<Ingrediente> ingredientes) {
                adapter.setIngredientes(ingredientes);
            }
        });

        // Configuramos el buscador
        binding.chefSearchView.setupWithSearchBar(binding.chefSearchBar);
        // Implementación del buscador
        binding.chefSearchView.getEditText().addTextChangedListener(new TextWatcher() {

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param s     - CharSequence - El texto actual del EditText.
             * @param start - int - La posición de inicio del texto.
             * @param count - int - El número de caracteres agregados.
             * @param after - int - El número de caracteres eliminados.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            /**
             * Se llama cuando el texto del EditText cambia. Se obtiene el texto actual y se filtra el adaptador.
             *
             * @param s      - CharSequence - El texto actual del EditText.
             * @param start  - int - La posición de inicio del texto.
             * @param before - int - El número de caracteres eliminados.
             * @param count  - int - El número de caracteres agregados.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param s - Editable - El texto actual del EditText.
             */
            @Override
            public void afterTextChanged(Editable s) {}

        });

        // Añadir el menú
        requireActivity().addMenuProvider(new MenuProvider() {

            /**
             * Crea el menú de opciones.
             *
             * @param menu         - {@link Menu} - El menú a crear.
             * @param menuInflater - {@link MenuInflater} - El objeto para inflar el menú.
             */
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.help_menu, menu);
            }

            /**
             * Maneja la selección de un elemento del menú.
             * <p>
             * La opción de ayuda es la única del menú.
             * </p>
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú seleccionado.
             * @return - boolean - {@code true} si el elemento ha sido seleccionado, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                // Opción ayuda
                if (id == R.id.action_ayuda) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mensaje", "ingredient");
                    navController.navigate(R.id.action_ingredientChefFragment_to_ayuda_nav_graph, bundle);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}