package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentSeleccionMenusBinding;
import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;
import com.iesaguadulce.mirecetariodecocina.ui.MenusAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.MenusViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanMenuViewModel;

/**
 * Fragmento para gestionar la selección de menús.
 * <p>
 * Este fragmento se encarga de mostrar la lista de menús y permitir la selección de menús para los
 * días de la planificación.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenusAdapter
 */
public class SeleccionMenusFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_seleccion_menus.xml. */
    private FragmentSeleccionMenusBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión de menús. */
    private MenusViewModel viewModel;

    /** ViewModel para la gestión temporal de datos del menú por día de la planificación en memoria. */
    private DataPlanMenuViewModel planMenuViewModel;

    /** Orden del díario en la planificación. */
    private int orden = 0;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public SeleccionMenusFragment() {
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
        viewModel = new ViewModelProvider(this).get(MenusViewModel.class);
        planMenuViewModel = new ViewModelProvider(requireActivity()).get(DataPlanMenuViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentSeleccionMenusBinding.inflate(inflater,container,false)).getRoot();
    }

    /**
     * Configura la lógica del listado de menús, permitiendo seleccionar el menú deseado
     * para el día de una planificación al dar clic en el contenido de cada menú.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el orden del día de la planificación (diario)
        if (getArguments() != null) {
            orden = getArguments().getInt("orden");
        }

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cerrar el fragmento y volver al listado de menús del día de la planificación
        binding.btnMenusSelectClose.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("orden", orden);
            navController.navigate(R.id.action_seleccionMenusFragment_to_dayXPlanFragment, bundle);
        });

        // Asignamos el adaptador al RecyclerView
        MenusAdapter adapter = new MenusAdapter();
        // Ocultamos el botón borrar para este listado
        adapter.setShowDeleteButton(false);

        // Configuramos el listener para seleccionar un menú de la lista, pasando el
        // orden del día de la planificación al listado de días de la planificación
        adapter.setOnAddMenuPlanListener(menu -> {
            if (orden > 0) {
               planMenuViewModel.addPlanMenu(new PlanMenu(menu.getIdMenu(), menu.getNombre(), menu.getDescripcion(), orden));
            }
            Bundle bundle = new Bundle();
            bundle.putInt("orden", orden);
            navController.navigate(R.id.action_seleccionMenusFragment_to_dayXPlanFragment, bundle);
        });

        // Observamos la lista de menús y actualizamos el adaptador
        binding.rvMenus.setAdapter(adapter);
        viewModel.getAllMenus().observe(getViewLifecycleOwner(), menus -> {
            adapter.setMenus(menus);
        });

        // Configuramos el buscador
        binding.menuSearchView.setupWithSearchBar(binding.menuSearchBar);
        // Implementación del buscador
        binding.menuSearchView.getEditText().addTextChangedListener(new TextWatcher() {

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
    }
}