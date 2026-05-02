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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentInicioChefBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataReceta;
import com.iesaguadulce.mirecetariodecocina.room.Receta;
import com.iesaguadulce.mirecetariodecocina.ui.RecetasAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Menu_RecViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RecetasViewModel;

import java.util.List;

/**
 * Fragmento para gestionar el listado de recetas.
 * <p>
 * Este fragmento se encarga de mostrar la lista de recetas y permitir la gestión de las mismas.
 * En este fragmento se pueden añadir, editar y eliminar recetas.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetasAdapter
 */
public class InicioChefFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_inicio_chef.xml. */
    private FragmentInicioChefBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de recetas. */
    private RecetasViewModel viewModel;

    /** ViewModel para la gestión de recetas del menú. */
    private Menu_RecViewModel menuRecViewModel;

    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos de la receta actual. */
    private DataRecetaViewModel dataRecetaViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public InicioChefFragment() {
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
        viewModel = new ViewModelProvider(this).get(RecetasViewModel.class);
        menuRecViewModel = new ViewModelProvider(this).get(Menu_RecViewModel.class);
        dataRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaViewModel.class);
        return (binding = FragmentInicioChefBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de recetas, el botón de añadir y el botón de guardar.
     * Manejo de la opción de editar al dar clic en una receta y la opción de eliminar.
     * Configura el menú de ayuda y el buscador de recetas.
     * Permite la navegación entre ingredientes, menús y planificación utilizando el menú inferior.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botones de navegación del menú inferior
        binding.menuToggleButtonChef.btnIngredient.setOnClickListener(v -> {
            navController.navigate(R.id.action_inicioChefFragment_to_ingredientChefFragment);
        });
        binding.menuToggleButtonChef.btnMenu.setOnClickListener(v -> {
            navController.navigate(R.id.action_inicioChefFragment_to_menuChefFragment);
        });
        binding.menuToggleButtonChef.btnPlan.setOnClickListener(v -> {
            navController.navigate(R.id.action_inicioChefFragment_to_planChefFragment);
        });

        // Botón flotante para añadir una receta
        binding.btnFabRecipes.setOnClickListener(v -> {
            navController.navigate(R.id.action_inicioChefFragment_to_dataRecetaFragment);
        });

        // Asignamos el adaptador al RecyclerView
        RecetasAdapter adapter = new RecetasAdapter();
        binding.rvRecetas.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvRecetasSearch.setAdapter(adapter);

        // Configuramos el listener para eliminar una receta
        adapter.setOnRecetaDeleteListener(receta -> {
            // Comprobar que la receta NO se encuentra en algún menú
            menuRecViewModel.getAllMenus(receta.getIdReceta()).observe(getViewLifecycleOwner(), menus -> {
                if (menus.isEmpty()) {
                    viewModel.borrarReceta(receta);
                    Toast.makeText(this.getContext(), "Receta eliminada: " + receta.getNombre(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getContext(), "No se puede eliminar la receta: " + receta.getNombre() + " porque está asociada a algún menú", Toast.LENGTH_LONG).show();
                }
            });
        });

        // Configuramos el listener para editar una receta
        adapter.setOnRecetaViewContentListener(receta -> {
            dataRecetaViewModel.setDataReceta(new DataReceta(receta.getIdReceta(), receta.getNombre(), receta.getDescripcion(), receta.getImagen(), receta.getTiempoPrep(), receta.getFamilia(), receta.getEtiqueta(), receta.getModoPrep(), receta.getIdUsuario()));
            navController.navigate(R.id.action_inicioChefFragment_to_dataRecetaFragment);
        });

        // Ocultar el botón flotante cuando se desplaza hacia arriba
        binding.rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabRecipes.isShown()) {
                    binding.btnFabRecipes.hide();
                } else if (dy < 0 && !binding.btnFabRecipes.isShown()) {
                    binding.btnFabRecipes.show();
                }
            }
        });

        // Observamos la lista de recetas y actualizamos el adaptador
        viewModel.getAllRecetas().observe(getViewLifecycleOwner(), new Observer<List<Receta>>() {
            @Override
            public void onChanged(List<Receta> recetas) {
                adapter.setRecetas(recetas);
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
                    bundle.putString("mensaje", "recipe");
                    navController.navigate(R.id.action_inicioChefFragment_to_ayuda_nav_graph, bundle);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}