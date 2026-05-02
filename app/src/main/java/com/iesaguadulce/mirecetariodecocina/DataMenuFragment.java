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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataMenuBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataMenu;
import com.iesaguadulce.mirecetariodecocina.model.MenuReceta;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.Menu;
import com.iesaguadulce.mirecetariodecocina.room.MenuRecetasJoin;
import com.iesaguadulce.mirecetariodecocina.room.RecetaIngredientesJoin;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataMenuViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataMenuRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaIngredienteViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Menu_RecViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.MenusViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RecetasViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para gestionar el registro o edición de un menú.
 * <p>
 * Este fragmento se encarga de recoger los datos de un menú y guardarlo en la base de datos.
 * Al guardar el menú se actualiza la lista de menus del fragmento de gestión de menus
 * que observa el ViewModel {@link MenusViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see MenusViewModel
 */
public class DataMenuFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_menu.xml. */
    private FragmentDataMenuBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para compartir el identificador del usuario logueado. */
    private UserSharedViewModel userViewModel;

    /** Identificador del usuario logueado. */
    private String idUsuario = "";

    // ViewModel de base de datos
    /** ViewModel para la gestión de menus. */
    private MenusViewModel menuViewModel;

    /** ViewModel para la gestión de las recetas del menú. */
    private Menu_RecViewModel menuRecViewModel;

    /** ViewModel para la consulta de recetas. */
    private RecetasViewModel recetaViewModel;

    // ViewModel de datos temporales que se guardan en memoria
    /** ViewModel para la gestión de datos del menú. */
    private DataMenuViewModel dataMenuViewModel;

    /** ViewModel para la gestión de datos de las recetas del menú. */
    private DataMenuRecetaViewModel dataMenuRecetaViewModel;

    /** ViewModel para la gestión de datos de los ingredientes de la receta. */
    private DataRecetaIngredienteViewModel dataRecetaIngredienteViewModel;

    /** Objeto que contiene los datos del menú actual. */
    private DataMenu dataMenu = null;

    /** Lista de recetas seleccionadas para el menú. */
    private List<MenuReceta> listaRecetasMenu;

    /** Lista de ingredientes seleccionados para las recetas. */
    private List<RecetaIngrediente> listaIngredientesReceta;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataMenuFragment() {
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
     * @param inflater  - El objeto LayoutInflater para inflar vistas.
     * @param container - El contenedor del fragmento.
     * @param savedInstanceState - Estado previo del fragmento, si existe.
     *
     * @return - La vista raíz del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar el ViewModel
        menuViewModel = new ViewModelProvider(this).get(MenusViewModel.class);
        menuRecViewModel = new ViewModelProvider(this).get(Menu_RecViewModel.class);
        recetaViewModel = new ViewModelProvider(this).get(RecetasViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        dataMenuViewModel = new ViewModelProvider(requireActivity()).get(DataMenuViewModel.class);
        dataMenuRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataMenuRecetaViewModel.class);
        dataRecetaIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaIngredienteViewModel.class);

        // Inflate the layout for this fragment
        return (binding = FragmentDataMenuBinding.inflate(inflater, container, false)).getRoot();
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
     * @param savedInstanceState - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el identificador del usuario
        idUsuario = userViewModel.getIdUser();

        // Si ya existen los datos en el ViewModel, los mostramos
        dataMenuViewModel.getDataMenu().observe(getViewLifecycleOwner(), menu -> {
            if (menu != null) {
                this.dataMenu = menu;
                binding.nameMenuValue.setText(menu.getNombre());
                if (menu.getIdMenu() > 0) {
                    binding.nameMenuTextInput.setEnabled(false);
                }
                binding.descriptionMenuValue.setText(menu.getDescripcion());
                binding.typeMenuValue.setText(menu.getTipo());
                binding.labelMenuValue.setText(menu.getEtiqueta());

                if (this.dataMenu.getIdMenu() != 0) {
                    // IMPORTANTE: Solo cargamos de la BD si el ViewModel de recetas está vacío
                    // para evitar sobrescribir los cambios realizados en memoria al volver de otras pantallas.
                    if (dataMenuRecetaViewModel.getAllRecetas().getValue() == null ||
                        dataMenuRecetaViewModel.getAllRecetas().getValue().isEmpty()) {
                        // Dentro del observador de la receta o al iniciar la edición
                        menuViewModel.getRecetasMenu(this.dataMenu.getIdMenu()).observe(getViewLifecycleOwner(), listaJoin -> {
                            if (listaJoin != null && !listaJoin.isEmpty()) {
                                List<MenuReceta> listaParaViewModel = new ArrayList<>();
                                for (MenuRecetasJoin join : listaJoin) {
                                    // Convertimos el objeto de BD al objeto de UI
                                    listaParaViewModel.add(new MenuReceta(
                                            join.idReceta,
                                            join.nombre,
                                            join.descripcion
                                    ));
                                }
                                // Pasamos la lista completa al ViewModel que gestiona la pantalla de edición
                                dataMenuRecetaViewModel.setListaRecetas(listaParaViewModel);
                            }
                        });
                    }

                    if (dataRecetaIngredienteViewModel.getAllIngredientes().getValue() == null ||
                            dataRecetaIngredienteViewModel.getAllIngredientes().getValue().isEmpty()) {
                        // Dentro del observador del menú o al iniciar la edición
                        recetaViewModel.getIngredientesMenu(this.dataMenu.getIdMenu()).observe(getViewLifecycleOwner(), listaJoin -> {
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
        binding.nameMenuValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.nameMenuTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.descriptionMenuValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });
        binding.typeMenuValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });
        binding.labelMenuValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ocultarTeclado(v);
            }
        });

        // Borrar el contenido del campo al pulsar el icono de borrar
        binding.nameMenuTextInput.setEndIconOnClickListener(v -> {
            binding.nameMenuValue.setText("");
        });
        binding.descriptionMenuTextInput.setEndIconOnClickListener(v -> {
            binding.descriptionMenuValue.setText("");
        });
        binding.typeMenuTextInput.setEndIconOnClickListener(v -> {
            binding.typeMenuValue.setText("");
        });
        binding.labelMenuTextInput.setEndIconOnClickListener(v -> {
            binding.labelMenuValue.setText("");
        });

        // Configuramos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario y volver al listado de menús
        binding.btnMenuCancel.setOnClickListener(v -> {
            // Inicializamos los datos temporales antes de volver
            dataMenuViewModel.clear();
            dataMenuRecetaViewModel.clear();

            // Volvemos al listado de menús
            navController.navigate(
                    R.id.action_dataMenuFragment_to_menuChefFragment,
                    null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.dataMenuFragment, true) // Borra el fragmento de la pila
                            .build()
            );
        });

        // Botón para guardar el menú
        binding.btnMenuSave.setOnClickListener(v -> {
            String nombre = binding.nameMenuValue.getText().toString().trim();
            String descripcion = binding.descriptionMenuValue.getText().toString().trim();
            String tipo = binding.typeMenuValue.getText().toString().trim();
            String etiqueta = binding.labelMenuValue.getText().toString().trim();

            // Obtenemos el listado de recetas elegido
            listaRecetasMenu = dataMenuRecetaViewModel.getAllRecetas().getValue();
            if (listaRecetasMenu == null) {
                listaRecetasMenu = new ArrayList<>();
            }

            // Validamos los datos
            Boolean valid = true;
            if (nombre.isEmpty()) {
                binding.nameMenuTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (valid) {
                // Generamos el menú
                Menu menu = new Menu(nombre, descripcion, tipo, etiqueta, idUsuario);

                // Si el menú tiene un identificador en base de datos, lo actualizamos.
                if (dataMenu.getIdMenu() > 0) {
                    menu.setIdMenu(dataMenu.getIdMenu());

                    // Llamada única al viewmodel para actualizar el menú
                    menuViewModel.actualizarMenuConRecetas(
                            menu,
                            listaRecetasMenu
                            );
                    Toast.makeText(getContext(), "Menú actualizado correctamente", Toast.LENGTH_SHORT).show();
                    // Inicializamos los datos temporales y volvemos al listado de menús
                    inicializamosViewModelyVolvemos();

                // Si el menú no tiene un identificador en base de datos, lo insertamos.
                } else {
                    // Observamos el menú por su NOMBRE para verificar si ya existe
                    menuViewModel.getMenuByNombre(nombre).observe(getViewLifecycleOwner(), menuBD -> {
                        if (menuBD != null) {
                            // El menú ya existe en la base de datos
                            binding.nameMenuTextInput.setError("El nombre del menú ya existe");
                        } else {
                            // El menú no existe, procedemos a guardar los datos
                            menuViewModel.insertarMenuConRecetas(
                                    menu,
                                    listaRecetasMenu
                            );

                            Toast.makeText(getContext(), "Menú guardado correctamente", Toast.LENGTH_SHORT).show();
                            // Inicializamos los datos temporales y volvemos al listado de menús
                            inicializamosViewModelyVolvemos();
                        }
                    });
                }
            }
        });

        // Botón para ir a la lista de recetas
        binding.btnRecipesMenu.setOnClickListener(v -> {
            cargarDatosMenuEnViewModel();
            navController.navigate(R.id.action_dataMenuFragment_to_listaRecetasMenuFragment);
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
            public void onCreateMenu(@NonNull android.view.Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.data_menu_menu, menu);
            }

            /**
             * Maneja la selección de un elemento del menú.
             * Sólo se gestiona la opción de ver la lista de compras.
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú seleccionado.
             * @return - boolean - {@code true} si el elemento ha sido seleccionado, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                // Ir a la lista de compras
                if (id == R.id.action_lista_compras) {
                    if (dataMenu != null && dataMenu.getIdMenu() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("tipoLista", "Menu");
                        navController.navigate(R.id.action_dataMenuFragment_to_listaCompraFragment, bundle);
                    } else {
                        Toast.makeText(getContext(), "No se puede ver la lista de la compra para este menú porque aún no se ha guardado", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

    }

    /**
     * Inicializamos los datos temporales y volvemos al listado de menús
     */
    private void inicializamosViewModelyVolvemos () {
        dataMenuViewModel.clear();
        dataMenuRecetaViewModel.clear();

        navController.navigate(
                R.id.action_dataMenuFragment_to_menuChefFragment,
                null,
                new NavOptions.Builder()
                        .setPopUpTo(R.id.dataMenuFragment, true) // Borra el fragmento de la pila
                        .build()
        );
    }

    /**
     * Cargamos los datos del menú en el ViewModel compartido
     */
    private void cargarDatosMenuEnViewModel() {
        int idMenu = 0;
        if (this.dataMenu != null) {
            if (this.dataMenu.getIdMenu() > 0) {
                idMenu = this.dataMenu.getIdMenu();
            }
        }
        dataMenuViewModel.setDataMenu(new DataMenu(idMenu,
                binding.nameMenuValue.getText().toString(),
                binding.descriptionMenuValue.getText().toString(),
                binding.typeMenuValue.getText().toString(),
                binding.labelMenuValue.getText().toString(),
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