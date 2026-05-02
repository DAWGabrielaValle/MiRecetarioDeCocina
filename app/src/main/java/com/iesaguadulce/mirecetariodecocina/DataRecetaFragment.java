package com.iesaguadulce.mirecetariodecocina;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataRecetaBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataReceta;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Receta;
import com.iesaguadulce.mirecetariodecocina.room.RecetaIngredientesJoin;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaIngredienteViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Receta_IngViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RecetasViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para gestionar el registro o edición de una receta.
 * <p>
 * Este fragmento se encarga de recoger los datos de una receta y guardarlo en la base de datos.
 * Al guardar la receta se actualiza la lista de recetas del fragmento de gestión de recetas
 * que observa el ViewModel {@link RecetasViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RecetasViewModel
 */
public class DataRecetaFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_receta.xml. */
    private FragmentDataRecetaBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para compartir el identificador del usuario logueado. */
    private UserSharedViewModel userViewModel;

    /** Identificador del usuario logueado. */
    private String idUsuario = "";

    // ViewModel de base de datos
    /** ViewModel para la gestión de recetas. */
    private RecetasViewModel recetaViewModel;

    /** ViewModel para la gestión de ingredientes de la receta. */
    private Receta_IngViewModel recetaIngViewModel;

    // ViewModel de lista de ingredientes en memoria
    /** ViewModel para la gestión de datos de la receta. */
    private DataRecetaViewModel dataRecetaViewModel;

    /** ViewModel para la gestión de datos de los ingredientes de la receta. */
    private DataRecetaIngredienteViewModel dataRecetaIngredienteViewModel;

    /** Objeto que contiene los datos de la receta actual. */
    private DataReceta dataReceta = null;

    /** Lista de ingredientes de la receta. */
    private List<RecetaIngrediente> listaIngredientesReceta;

    /** Modo de hacer la receta. */
    private String modoHacerse = "";

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataRecetaFragment() {
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
        // requireActivity Asegura que ambos compartan la misma instancia ligada a la actividad
        recetaViewModel = new ViewModelProvider(this).get(RecetasViewModel.class);
        recetaIngViewModel = new ViewModelProvider(this).get(Receta_IngViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        dataRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaViewModel.class);
        dataRecetaIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaIngredienteViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentDataRecetaBinding.inflate(inflater, container, false)).getRoot();
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

        // Obtenemos el identificador del usuario logueado
        idUsuario = userViewModel.getIdUser();

        // Si existen datos en el ViewModel, los mostramos
        dataRecetaViewModel.getDataReceta().observe(getViewLifecycleOwner(), receta -> {
            if (receta != null) {
                this.dataReceta = receta;
                binding.nameRecipeValue.setText(receta.getNombre());
                if (receta.getIdReceta() > 0) {
                    binding.nameRecipeTextInput.setEnabled(false);
                    if (modoHacerse.isEmpty()) {
                        dataRecetaViewModel.setToDoReceta(receta.getModoPrep());
                        this.modoHacerse = receta.getModoPrep();
                    }
                }
                binding.descriptionRecipeValue.setText(receta.getDescripcion());
                binding.prepTimeRecipeValue.setText(String.valueOf(receta.getTiempoPrep()));
                binding.familyRecipeValue.setText(receta.getFamilia());
                binding.labelRecipeValue.setText(receta.getEtiqueta());

                if (receta.getImagen() != null && !receta.getImagen().isEmpty()) {
                    // Declaramos resID como int y usamos requireContext() para mayor seguridad
                    int resID = requireContext().getResources().getIdentifier(
                            receta.getImagen(),
                            "drawable",
                            requireContext().getPackageName()
                    );

                    // Si resID es 0, significa que no se encontró la imagen en drawable
                    if (resID != 0) {
                        binding.photoRecipe.setImageResource(resID);
                    } else {
                        // Opcional: poner una imagen por defecto si no existe la indicada
                        binding.photoRecipe.setImageResource(R.drawable.outline_image_24);
                    }
                }

                if (this.dataReceta.getIdReceta() != 0) {
                    // IMPORTANTE: Solo cargamos de la BD si el ViewModel de ingredientes está vacío
                    // para evitar sobrescribir los cambios realizados en memoria al volver de otras pantallas.
                    if (dataRecetaIngredienteViewModel.getAllIngredientes().getValue() == null ||
                            dataRecetaIngredienteViewModel.getAllIngredientes().getValue().isEmpty()) {
                        // Dentro del observador de la receta o al iniciar la edición
                        recetaViewModel.getIngredientesDeReceta(this.dataReceta.getIdReceta()).observe(getViewLifecycleOwner(), listaJoin -> {
                            if (listaJoin != null && !listaJoin.isEmpty()) {
                                List<RecetaIngrediente> listaParaViewModel = new ArrayList<>();

                                for (RecetaIngredientesJoin join : listaJoin) {
                                    // Convertimos el objeto de BD al objeto de UI
                                    listaParaViewModel.add(new RecetaIngrediente(
                                            join.idIngrediente,
                                            join.nombre,
                                            join.cantidad,
                                            join.unidad
                                    ));
                                }
                                // Pasamos la lista completa al ViewModel que gestiona la pantalla de edición
                                dataRecetaIngredienteViewModel.setListaIngredientes(listaParaViewModel);
                            }
                        });
                    }
                }
            }
        });


        // Cerrar teclado al perder el foco
        binding.nameRecipeValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.nameRecipeTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.descriptionRecipeValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });
        binding.prepTimeRecipeValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.prepTimeRecipeTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.familyRecipeValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });
        binding.labelRecipeValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });

        // Borrar el contenido del campo al pulsar el icono de borrar
        binding.nameRecipeTextInput.setEndIconOnClickListener(v -> {
            binding.nameRecipeValue.setText("");
        });
        binding.descriptionRecipeTextInput.setEndIconOnClickListener(v -> {
            binding.descriptionRecipeValue.setText("");
        });
        binding.prepTimeRecipeTextInput.setEndIconOnClickListener(v -> {
            binding.prepTimeRecipeValue.setText("");
        });
        binding.familyRecipeTextInput.setEndIconOnClickListener(v -> {
            binding.familyRecipeValue.setText("");
        });
        binding.labelRecipeTextInput.setEndIconOnClickListener(v -> {
            binding.labelRecipeValue.setText("");
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario
        binding.btnRecipeCancel.setOnClickListener(v -> {
            // Inicializamos los datos temporales antes de volver
            dataRecetaViewModel.clear();
            dataRecetaViewModel.clearToDoReceta();
            dataRecetaIngredienteViewModel.clear();

            // Volvemos al listado de recetas
            navController.navigate(
                    R.id.action_dataRecetaFragment_to_inicioChefFragment,
                    null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.dataRecetaFragment, true) // Borra DataRecetaFragment de la pila
                            .build()
            );

        });

        // Botón para guardar la receta
        binding.btnRecipeSave.setOnClickListener(v -> {
            String nombre = binding.nameRecipeValue.getText().toString().trim();
            String descripcion = binding.descriptionRecipeValue.getText().toString().trim();
            String tiempo = binding.prepTimeRecipeValue.getText().toString().trim();
            String familia = binding.familyRecipeValue.getText().toString().trim();
            String etiqueta = binding.labelRecipeValue.getText().toString().trim();

            // Obtenemos el modo de hacer la receta
            modoHacerse = dataRecetaViewModel.getToDoReceta().getValue();
            if (modoHacerse == null) {
                modoHacerse = "";
            }

            // Obtenemos el listado de ingredientes elegido
            listaIngredientesReceta = dataRecetaIngredienteViewModel.getAllIngredientes().getValue();
            if (listaIngredientesReceta == null) {
                listaIngredientesReceta = new ArrayList<>();
            }

            // Validamos los datos
            boolean valid = true;
            if (nombre.isEmpty()) {
                binding.nameRecipeTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (tiempo.isEmpty()) {
                binding.prepTimeRecipeTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }

            if (valid) {
                // Generamos la receta
                Receta receta = new Receta(nombre, descripcion, null, Integer.parseInt(tiempo), familia, etiqueta, modoHacerse, idUsuario);

                // Si la receta tiene un identificador en base de datos, la actualizamos.
                if (dataReceta.getIdReceta() > 0) {
                    receta.setIdReceta(dataReceta.getIdReceta());

                    // Llamada única al viewmodel para actualizar la receta
                    recetaViewModel.actualizarRecetaCompleta(
                            receta,
                            listaIngredientesReceta
                    );

                    Toast.makeText(getContext(), "Receta actualizada correctamente", Toast.LENGTH_SHORT).show();
                    // Inicializamos los datos temporales y volvemos al listado de recetas
                    inicializamosViewModelyVolvemos();

                // Si la receta no tiene un identificador en base de datos, la insertamos.
                } else {
                    // Observamos la receta por su NOMBRE para verificar si ya existe
                    recetaViewModel.getRecetaByNombre(nombre).observe(getViewLifecycleOwner(), recetaBD -> {
                        if (recetaBD != null) {
                            // La receta ya existe en la base de datos
                            binding.nameRecipeTextInput.setError("El nombre de la receta ya existe");
                        } else {

                            // Llamada única al viewmodel para insertar la receta
                            recetaViewModel.insertarRecetaCompleta(
                                    receta,
                                    listaIngredientesReceta
                            );

                            Toast.makeText(getContext(), "Receta guardada correctamente", Toast.LENGTH_SHORT).show();

                            // Inicializamos los datos temporales y volvemos al listado de recetas
                            inicializamosViewModelyVolvemos();
                        }
                    });
                }
            }
        });

        // Botón para abrir el fragmento de ingredientes
        binding.btnIngredients.setOnClickListener(v -> {
            cargarDatosRecetaEnViewModel();
            navController.navigate(R.id.action_dataRecetaFragment_to_listaIngredientesRecipeFragment);
        });

        // Botón para abrir el fragmento de modo de hacer la receta
        binding.btnHowtomake.setOnClickListener(v -> {
            cargarDatosRecetaEnViewModel();
            navController.navigate(R.id.action_dataRecetaFragment_to_modoPreparacionRecipeFragment);
        });

        // Añadimos el menú
        requireActivity().addMenuProvider(new MenuProvider() {

            /**
             * Crea el menú de opciones.
             *
             * @param menu         - {@link android.view.Menu} - El menú a crear.
             * @param menuInflater - {@link MenuInflater} - El objeto para inflar el menú.
             */
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.data_receta_menu, menu);
            }

            /**
             * Maneja la selección de un elemento del menú.
             * <p>
             * Las opciones del menú son:
             * <ul>
             *     <li>Comentarios</li>
             *     <li>Lista de la compra</li>
             *     <li>Compartir (no implementado)</li>
             * </li>
             * </p>
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú seleccionado.
             * @return - boolean - {@code true} si el elemento ha sido seleccionado, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                // Opción comentarios
                if (id == R.id.action_comentarios) {
                    if (dataReceta != null && dataReceta.getIdReceta() > 0) {
                        navController.navigate(R.id.action_dataRecetaFragment_to_comentariosFragment);
                    } else {
                        Toast.makeText(getContext(), "Debe guardar la receta para poder ver los comentarios", Toast.LENGTH_LONG).show();
                    }
                    return true;
                // Opción lista de la compra
                } else if (id == R.id.action_lista_compras) {
                    if (dataReceta != null && dataReceta.getIdReceta() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("tipoLista", "Receta");
                        navController.navigate(R.id.action_dataRecetaFragment_to_listaCompraFragment, bundle);
                    } else {
                        Toast.makeText(getContext(), "Debe guardar la receta para poder ver la lista de la compra", Toast.LENGTH_LONG).show();
                    }
                    return true;
                // Opción compartir
                } else if (id == R.id.action_compartir) {
                    Toast.makeText(getContext(), "Compartir", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    // Método para inicializar los datos temporales y volver al listado de recetas
    private void inicializamosViewModelyVolvemos () {
        dataRecetaViewModel.clear();
        dataRecetaViewModel.clearToDoReceta();
        dataRecetaIngredienteViewModel.clear();

        navController.navigate(
                R.id.action_dataRecetaFragment_to_inicioChefFragment,
                null,
                new NavOptions.Builder()
                        .setPopUpTo(R.id.dataRecetaFragment, true) // Borra DataRecetaFragment de la pila
                        .build()
        );

    }

    // Método para cargar los datos de la receta en el ViewModel
    private void cargarDatosRecetaEnViewModel() {
        int idReceta = 0;
        if (this.dataReceta != null) {
            if (this.dataReceta.getIdReceta() > 0) {
                idReceta = this.dataReceta.getIdReceta();
            }
        }

        String tiempoStr = binding.prepTimeRecipeValue.getText().toString().trim();
        int tiempo = 0;
        if (!tiempoStr.isEmpty()) {
            try {
                tiempo = Integer.parseInt(tiempoStr);
            } catch (NumberFormatException e) {
                tiempo = 0;
            }
        }

        dataRecetaViewModel.setDataReceta(new DataReceta(idReceta,
                binding.nameRecipeValue.getText().toString(),
                binding.descriptionRecipeValue.getText().toString(),
                "",
                tiempo,
                binding.familyRecipeValue.getText().toString(),
                binding.labelRecipeValue.getText().toString(),
                dataRecetaViewModel.getToDoReceta().getValue(),
                this.idUsuario));

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