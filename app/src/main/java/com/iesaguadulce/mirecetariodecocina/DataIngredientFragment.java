package com.iesaguadulce.mirecetariodecocina;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataIngredientBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Ingrediente;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataIngredienteViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.IngredientesViewModel;

/**
 * Fragmento para gestionar el registro o edición de un ingrediente.
 * <p>
 * Este fragmento se encarga de recoger los datos de un ingrediente y guardarlo en la base de datos.
 * Al guardar el ingrediente se actualiza la lista de ingredientes del fragmento de gestión de ingredientes
 * que observa el ViewModel {@link IngredientesViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see IngredientesViewModel
 */
public class DataIngredientFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_ingredient.xml. */
    private FragmentDataIngredientBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de ingredientes. */
    private IngredientesViewModel ingredienteViewModel;

    // ViewModel de datos temporales
    /** ViewModel para la gestión de datos del ingrediente. */
    private DataIngredienteViewModel dataIngredienteViewModel;

    /** Objeto que contiene los datos del ingrediente actual. */
    private DataIngrediente dataIngrediente = null;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataIngredientFragment() {
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
        ingredienteViewModel = new ViewModelProvider(this).get(IngredientesViewModel.class);
        dataIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataIngredienteViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentDataIngredientBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del formulario, validaciones y el evento de guardado.
     * <p>
     * Este método inicializa y recupera los identificadores necesarios de los
     * ViewModels compartidos y establece los listeners para el botón de guardado
     * y el comportamiento del teclado.
     * </p>
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observamos el ViewModel dataIngrediente para obtener los datos del ingrediente
        dataIngredienteViewModel.getDataIngrediente().observe(getViewLifecycleOwner(), dataIngrediente -> {
            if (dataIngrediente != null) {
                this.dataIngrediente = dataIngrediente;
                binding.nameIngredientValue.setText(dataIngrediente.getNombre());
                binding.nameIngredientTextInput.setEnabled(false);
                binding.descriptionIngredientValue.setText(dataIngrediente.getDescripcion());
                binding.familyIngredientValue.setText(dataIngrediente.getFamilia());
                binding.labelIngredientValue.setText(dataIngrediente.getEtiqueta());

                // Dentro del observador de dataIngredienteViewModel
                if (dataIngrediente.getImagen() != null && !dataIngrediente.getImagen().isEmpty()) {
                    // Declaramos resID como int y usamos requireContext() para mayor seguridad
                    int resID = requireContext().getResources().getIdentifier(
                            dataIngrediente.getImagen(),
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

        // Cerrar teclado al perder el foco
        binding.nameIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.nameIngredientTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.descriptionIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.descriptionIngredientTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.familyIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.familyIngredientTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.labelIngredientValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.labelIngredientTextInput.setError(null);
                ocultarTeclado(v);
            }
        });

        // Eliminar el contenido del campo de texto
        binding.nameIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.nameIngredientValue.setText("");
        });
        binding.descriptionIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.descriptionIngredientValue.setText("");
        });
        binding.familyIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.familyIngredientValue.setText("");
        });
        binding.labelIngredientTextInput.setEndIconOnClickListener(v -> {
            binding.labelIngredientValue.setText("");
        });

        // Configuramos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario y volver al listado de ingredientes
        binding.btnIngredientCancel.setOnClickListener(v -> {
            // Borramos el ViewModel dataIngrediente temporal
            dataIngredienteViewModel.clear();
            // Volvemos al fragmento indicado en el controlador de navegación
            navController.navigate(R.id.action_dataIngredientFragment_to_ingredientChefFragment);
        });

        // Botón para guardar el ingrediente
        binding.btnIngredientSave.setOnClickListener(v -> {
            String nombre = binding.nameIngredientValue.getText().toString().trim();
            String descripcion = binding.descriptionIngredientValue.getText().toString().trim();
            String familia = binding.familyIngredientValue.getText().toString().trim();
            String etiqueta = binding.labelIngredientValue.getText().toString().trim();

            // Validaciones
            boolean valid = true;
            if (nombre.isEmpty()) {
                binding.nameIngredientTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (valid) {
                // Generamos el ingrediente
                Ingrediente ingrediente = new Ingrediente(nombre, descripcion, null, familia, etiqueta);

                // Si el ingrediente tiene un identificador en base de datos, lo actualizamos.
                if (dataIngrediente != null) {
                    ingrediente.setIdIngrediente(dataIngrediente.getIdIngrediente());
                    ingrediente.setImagen(dataIngrediente.getImagen());

                    // Actualizamos el ingrediente
                    ingredienteViewModel.update(ingrediente);
                    Toast.makeText(this.getContext(), "Ingrediente actualizado correctamente", Toast.LENGTH_SHORT).show();
                    // Borramos el ViewModel dataIngrediente temporal
                    dataIngredienteViewModel.clear();
                    // Volvemos al listado de ingredientes
                    navController.navigate(R.id.action_dataIngredientFragment_to_ingredientChefFragment);

                // Si no tiene identificador, lo insertamos.
                } else {

                    // Observamos el ingrediente por su NOMBRE para verificar si ya existe
                    ingredienteViewModel.getIngredienteByNombre(nombre).observe(getViewLifecycleOwner(), ingredienteDB -> {
                        if (ingredienteDB != null) {
                            // El ingrediente ya existe en la base de datos
                            binding.nameIngredientTextInput.setError("El nombre del ingrediente ya existe");
                        } else {
                            // El ingrediente no existe, procedemos a guardar los datos
                            ingredienteViewModel.insert(new Ingrediente(nombre, descripcion, null, familia, etiqueta));

                            Toast.makeText(this.getContext(), "Ingrediente registrado correctamente", Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_dataIngredientFragment_to_ingredientChefFragment);
                        }
                    });
                }
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