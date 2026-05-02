package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentSeleccionIngredientesBinding;
import com.iesaguadulce.mirecetariodecocina.ui.IngredientesAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.IngredientesViewModel;

/**
 * Fragmento para gestionar la selección de ingredientes.
 * <p>
 * Este fragmento se encarga de mostrar la lista de ingredientes y permitir la selección de ingredientes
 * para una receta.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredientesAdapter
 */
public class SeleccionIngredientesFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_seleccion_ingredientes.xml. */
    private FragmentSeleccionIngredientesBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión de ingredientes. */
    private IngredientesViewModel viewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public SeleccionIngredientesFragment() {
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
        viewModel = new ViewModelProvider(this).get(IngredientesViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentSeleccionIngredientesBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de ingredientes, permitiendo seleccionar el ingrediente deseado
     * para una receta al dar clic en el contenido de cada ingrediente.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cerrar el fragmento y volver al listado de ingredientes de una receta
        binding.btnIngredientsSelectClose.setOnClickListener(v -> {
            navController.popBackStack();
        });

        // Asignamos el adaptador al RecyclerView
        IngredientesAdapter adapter = new IngredientesAdapter();
        binding.rvIngredients.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvIngredientsSearch.setAdapter(adapter);

        // Ocultamos el botón borrar para este listado
        adapter.setShowDeleteButton(false);

        // Configuramos el listener para editar un ingrediente de una receta, pasando el identificador del ingrediente
        // al fragmento de edición de ingrediente de la receta
        adapter.setOnRecetaIngredienteEditListener(ingrediente -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idIngrediente", ingrediente.getIdIngrediente());

            navController.navigate(
                    R.id.action_seleccionIngredientesFragment_to_dataIngredientRecipeFragment,
                    bundle,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.seleccionIngredientesFragment, true) // Borra el fragmento de la pila
                            .build()
            );
        });

        // Observamos la lista de ingredientes y actualizamos el adaptador
        viewModel.getAllIngredientes().observe(getViewLifecycleOwner(), ingredientes -> {
            adapter.setIngredientes(ingredientes);
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

    }
}
