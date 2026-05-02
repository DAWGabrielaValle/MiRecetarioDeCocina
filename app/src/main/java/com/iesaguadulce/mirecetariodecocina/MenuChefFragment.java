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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentMenuChefBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataMenu;
import com.iesaguadulce.mirecetariodecocina.room.Menu;
import com.iesaguadulce.mirecetariodecocina.ui.MenusAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataMenuViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Diario_MenuViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.MenusViewModel;

import java.util.List;

/**
 * Fragmento para gestionar el listado de menús.
 * <p>
 * Este fragmento se encarga de mostrar la lista de menús y permitir la gestión de las mismas.
 * En este fragmento se pueden añadir, editar y eliminar menús.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenusAdapter
 */
public class MenuChefFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_menu_chef.xml. */
    private FragmentMenuChefBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de menús. */
    private MenusViewModel viewModel;

    /** ViewModel para la gestión de menús por día de la planificación. */
    private Diario_MenuViewModel diarioMenuViewModel;

    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos del menú en memoria. */
    private DataMenuViewModel dataMenuViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public MenuChefFragment() {
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
        viewModel = new ViewModelProvider(this).get(MenusViewModel.class);
        diarioMenuViewModel = new ViewModelProvider(this).get(Diario_MenuViewModel.class);
        dataMenuViewModel = new ViewModelProvider(requireActivity()).get(DataMenuViewModel.class);
        return (binding = FragmentMenuChefBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de menús, el botón de añadir y el botón de guardar.
     * Manejo de la opción de editar al dar clic en un menú y la opción de eliminar.
     * Configura el menú de ayuda y el buscador de menús.
     * Permite la navegación entre recetas, ingredientes y planificación utilizando el menú inferior.
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
            navController.navigate(R.id.action_menuChefFragment_to_inicioChefFragment);
        });
        binding.menuToggleButtonChef.btnIngredient.setOnClickListener(v -> {
            navController.navigate(R.id.action_menuChefFragment_to_ingredientChefFragment);
        });
        binding.menuToggleButtonChef.btnPlan.setOnClickListener(v -> {
            navController.navigate(R.id.action_menuChefFragment_to_planChefFragment);
        });

        // Botón flotante para añadir un menú
        binding.btnFabMenus.setOnClickListener(v -> {
            navController.navigate(R.id.action_menuChefFragment_to_dataMenuFragment);
        });

        // Asignamos el adaptador al RecyclerView
        MenusAdapter adapter = new MenusAdapter();
        binding.rvMenus.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvMenusSearch.setAdapter(adapter);

        // Configuramos el listener para eliminar un menú
        adapter.setOnMenuDeleteListener(menu -> {
            // Comprobar que el menu NO se encuentra en algún plan
            diarioMenuViewModel.getAllDiarios(menu.idMenu).observe(getViewLifecycleOwner(), diarios -> {
                if (diarios.isEmpty()) {
                    viewModel.borrarMenu(menu);
                    Toast.makeText(this.getContext(), "Menu eliminado: " + menu.nombre, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getContext(), "No se puede eliminar el menu: " + menu.nombre + " porque está asociado a algún plan", Toast.LENGTH_LONG).show();
                }
            });
        });

        // Configuramos el listener para editar un menú
        adapter.setOnMenuViewContentListener(menu -> {
            dataMenuViewModel.setDataMenu(new DataMenu(menu.idMenu, menu.nombre, menu.descripcion, menu.tipo, menu.etiqueta, menu.idUsuario));
            navController.navigate(R.id.action_menuChefFragment_to_dataMenuFragment);
        });

        // Ocultar el botón flotante cuando se desplaza hacia arriba
        binding.rvMenus.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabMenus.isShown()) {
                    binding.btnFabMenus.hide();
                } else if (dy < 0 && !binding.btnFabMenus.isShown()) {
                    binding.btnFabMenus.show();
                }
            }
        });

        // Observamos la lista de menús y actualizamos el adaptador
        viewModel.getAllMenus().observe(getViewLifecycleOwner(), new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menus) {
                adapter.setMenus(menus);
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
             * @param menu         - {@link android.view.Menu} - El menú a crear.
             * @param menuInflater - {@link MenuInflater} - El objeto para inflar el menú.
             */
            @Override
            public void onCreateMenu(@NonNull android.view.Menu menu, @NonNull MenuInflater menuInflater) {
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
                    bundle.putString("mensaje", "menu");
                    navController.navigate(R.id.action_menuChefFragment_to_ayuda_nav_graph, bundle);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}