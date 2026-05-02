package com.iesaguadulce.mirecetariodecocina;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataIngredientRecipeBinding;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;
import com.iesaguadulce.mirecetariodecocina.viewmodel.IngredientesViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaIngredienteViewModel;

/**
 * Fragmento para gestionar el registro de ingredientes en una receta.
 * <p>
 * Este fragmento se encarga de recoger los datos adicionales del ingrediente y guardarlos de forma
 * temporal en un ViewModel compartido ({@link DataRecetaIngredienteViewModel}) para su posterior uso.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataRecetaIngredienteViewModel
 */
public class DataIngredientRecipeFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_ingredient_recipe.xml. */
    private FragmentDataIngredientRecipeBinding binding;

    /** ViewModel para la gestión de ingredientes. */
    private IngredientesViewModel viewModel;

    /** ViewModel para la gestión de datos de los ingredientes de la receta. */
    private DataRecetaIngredienteViewModel dataRecetaIngredienteViewModel;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** Objeto que contiene los datos del ingrediente seleccionado. */
    private Ingrediente ingredienteSeleccionado;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataIngredientRecipeFragment() {
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
        viewModel = new ViewModelProvider(this).get(IngredientesViewModel.class);
        dataRecetaIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaIngredienteViewModel.class);
        // Inflate the layout for this fragment
        return (binding =  FragmentDataIngredientRecipeBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del formulario, validaciones y el evento de guardado.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Si tenemos argumentos, obtenemos el identificador del ingrediente
        if (getArguments() != null) {
            int idIngrediente = getArguments().getInt("idIngrediente");

            // Cargamos los datos usando el viewModel
            viewModel.getIngrediente(idIngrediente).observe(getViewLifecycleOwner(), ingrediente -> {
                ingredienteSeleccionado = ingrediente;
                if (ingrediente != null) {
                    binding.nameIngredient.setText(ingrediente.getNombre());
                    binding.descriptionIngredient.setText(ingrediente.getDescripcion());
                    binding.familyLabelIngredient.setText(ingrediente.getFamilia() + " · " + ingrediente.getEtiqueta());

                    if (ingrediente.getImagen() != null && !ingrediente.getImagen().isEmpty()) {
                        // Declaramos resID como int y usamos requireContext() para mayor seguridad
                        int resID = requireContext().getResources().getIdentifier(
                                ingrediente.getImagen(),
                                "drawable",
                                requireContext().getPackageName()
                        );

                        // Si resID es 0, significa que no se encontró la imagen en drawable
                        if (resID != 0) {
                            binding.photoIngredient.setImageResource(resID);
                        } else {
                            // Opcional: poner una imagen por defecto si no existe la indicada
                            binding.photoIngredient.setImageResource(R.drawable.outline_image_24);
                        }
                    }

                }
            });
        }

        // Cerrar el teclado al perder el foco
        binding.quantityIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.quantityIngredientTextInput.setError(null);
                ocultarTeclado(v);

            }
        });
        binding.unitIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.unitIngredientTextInput.setError(null);
                ocultarTeclado(v);
            }
        });

        // Eliminar contenido del campo
        binding.quantityIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.quantityIngredientValue.setText("");
        });
        binding.unitIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.unitIngredientValue.setText("");
        });

        // Botones de navegación
        navController = Navigation.findNavController(view);

        // Guardar los datos adicionales del ingrediente
        binding.btnIngredientRecipeSave.setOnClickListener(v -> {
            String cantidad = binding.quantityIngredientValue.getText().toString().trim();
            String unidad = binding.unitIngredientValue.getText().toString().trim();

            // Validamos los datos
            Boolean valid = true;
            if (cantidad.isEmpty()) {
                binding.quantityIngredientTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (unidad.isEmpty()) {
                binding.unitIngredientTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (valid) {
                // Creamos el objeto RecetaIngrediente
                RecetaIngrediente recetaIngrediente = new RecetaIngrediente(ingredienteSeleccionado.getIdIngrediente(), ingredienteSeleccionado.getNombre(), Integer.parseInt(cantidad), unidad);

                // Guardamos el ingrediente en el ViewModel compartido para su posterior uso.
                dataRecetaIngredienteViewModel.addIngrediente(recetaIngrediente);

                navController.navigate(
                        R.id.action_dataIngredientRecipeFragment_to_listaIngredientesRecipeFragment,
                        null,
                        new NavOptions.Builder()
                                .setPopUpTo(R.id.dataIngredientRecipeFragment, true) // Borra el fragmento de la pila
                                .build()
                );
            }
        });
    }

    /**
     * Oculta el teclado si está visible
     */
    private void ocultarTeclado(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}