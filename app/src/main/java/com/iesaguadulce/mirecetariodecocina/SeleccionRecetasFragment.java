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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentSeleccionRecetasBinding;
import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;
import com.iesaguadulce.mirecetariodecocina.ui.RecetasAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataMenuRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RecetasViewModel;

/**
 * Fragmento para gestionar la selección de recetas.
 * <p>
 * Este fragmento se encarga de mostrar la lista de recetas y permitir la selección de recetas para un menú
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetasAdapter
 */
public class SeleccionRecetasFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_seleccion_recetas.xml. */
    private FragmentSeleccionRecetasBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión de recetas. */
    private RecetasViewModel viewModel;

    /** ViewModel para la gestión temporal de datos de las recetas de un menú en memoria. */
    private DataMenuRecetaViewModel menuRecetaViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public SeleccionRecetasFragment() {
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
        viewModel = new ViewModelProvider(this).get(RecetasViewModel.class);
        menuRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataMenuRecetaViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentSeleccionRecetasBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de recetas, permitiendo seleccionar la receta deseada
     * para un menú al dar clic en el contenido de cada receta.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cerrar el fragmento y volver al listado de recetas del menú
        binding.btnRecipesSelectClose.setOnClickListener(v -> {
            navController.navigate(R.id.action_seleccionRecetasFragment_to_listaRecetasMenuFragment);
        });

        // Asignamos el adaptador al RecyclerView
        RecetasAdapter adapter = new RecetasAdapter();
        // Ocultamos el botón borrar para este listado
        adapter.setShowDeleteButton(false);

        // Configuramos el listener para seleccionar una receta para un menú
        adapter.setOnAddRecetaMenuListener(receta -> {
            menuRecetaViewModel.addReceta(new MenuReceta(receta.getIdReceta(), receta.getNombre(), receta.getDescripcion()));
            navController.navigate(R.id.action_seleccionRecetasFragment_to_listaRecetasMenuFragment);
        });

        // Observamos la lista de recetas y actualizamos el adaptador
        binding.rvRecipes.setAdapter(adapter);
        viewModel.getAllRecetas().observe(getViewLifecycleOwner(), recetas -> {
            adapter.setRecetas(recetas);
        });

        // Configuramos el buscador
        binding.chefSearchView.setupWithSearchBar(binding.chefSearchBar);
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

    }
}