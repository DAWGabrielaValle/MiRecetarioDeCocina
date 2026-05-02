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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentPlanChefBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataPlan;
import com.iesaguadulce.mirecetariodecocina.room.Plan;
import com.iesaguadulce.mirecetariodecocina.ui.PlanesAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.PlanesViewModel;

import java.util.List;

/**
 * Fragmento para gestionar el listado de planes.
 * <p>
 * Este fragmento se encarga de mostrar la lista de planes y permitir la gestión de los mismos.
 * En este fragmento se pueden añadir, editar y eliminar planes.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanesAdapter
 */
public class PlanChefFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_plan_chef.xml. */
    private FragmentPlanChefBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de planes. */
    private PlanesViewModel viewModel;

    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos del plan en memoria. */
    private DataPlanViewModel dataPlanViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public PlanChefFragment() {
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
        viewModel = new ViewModelProvider(this).get(PlanesViewModel.class);
        dataPlanViewModel = new ViewModelProvider(requireActivity()).get(DataPlanViewModel.class);
        return (binding = FragmentPlanChefBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de planificaciones, el botón de añadir y el botón de guardar.
     * Manejo de la opción de editar al dar clic en una planificación y la opción de eliminar.
     * Configura el menú de ayuda y el buscador de planificaciones.
     * Permite la navegación entre recetas, ingredientes y menús utilizando el menú inferior.
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
            navController.navigate(R.id.action_planChefFragment_to_inicioChefFragment);
        });
        binding.menuToggleButtonChef.btnIngredient.setOnClickListener(v -> {
            navController.navigate(R.id.action_planChefFragment_to_ingredientChefFragment);
        });
        binding.menuToggleButtonChef.btnMenu.setOnClickListener(v -> {
            navController.navigate(R.id.action_planChefFragment_to_menuChefFragment);
        });

        // Botón flotante para añadir una nueva planificación
        binding.btnFabPlan.setOnClickListener(v -> {
            navController.navigate(R.id.action_planChefFragment_to_dataPlanFragment);
        });

        // Asignamos el adaptador al RecyclerView
        PlanesAdapter adapter = new PlanesAdapter();
        binding.rvPlans.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvPlansSearch.setAdapter(adapter);

        // Configuramos el listener para eliminar una planificación
        adapter.setOnPlanDeleteListener(plan -> {
            viewModel.borrarPlan(plan);
        });

        // Configuramos el listener para editar una planificación
        adapter.setOnPlanViewContentListener(plan -> {
            dataPlanViewModel.setDataPlan(new DataPlan(plan.getIdPlan(), plan.getNombre(), plan.getDescripcion(), plan.getTipo(), plan.getEtiqueta(), plan.getDias()));
            navController.navigate(R.id.action_planChefFragment_to_dataPlanFragment);
        });

        // Ocultar el botón flotante cuando se desplaza hacia arriba
        binding.rvPlans.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabPlan.isShown()) {
                    binding.btnFabPlan.hide();
                } else if (dy < 0 && !binding.btnFabPlan.isShown()) {
                    binding.btnFabPlan.show();
                }
            }
        });

        // Observamos la lista de planes y actualizamos el adaptador
        viewModel.getAllPlanes().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> planes) {
                adapter.setPlanes(planes);
            }
        });

        // Configuramos el buscador
        binding.chefSearchView.setupWithSearchBar(binding.chefSearchBar);
        // Implementación del buscador
        binding.chefSearchView.getEditText().addTextChangedListener(new TextWatcher() {

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param charSequence - CharSequence - El texto actual del EditText.
             * @param i            - int - La posición de inicio del texto.
             * @param i1           - int - El número de caracteres agregados.
             * @param i2           - int - El número de caracteres eliminados.
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            /**
             * Se llama cuando el texto del EditText cambia. Se obtiene el texto actual y se filtra el adaptador.
             *
             * @param charSequence - CharSequence - El texto actual del EditText.
             * @param i            - int - La posición de inicio del texto.
             * @param i1           - int - El número de caracteres agregados.
             * @param i2           - int - El número de caracteres eliminados.
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(charSequence.toString());
            }

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param editable - Editable - El texto actual del EditText.
             */
            @Override
            public void afterTextChanged(Editable editable) {}
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
                    bundle.putString("mensaje", "plan");
                    navController.navigate(R.id.action_planChefFragment_to_ayuda_nav_graph, bundle);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}