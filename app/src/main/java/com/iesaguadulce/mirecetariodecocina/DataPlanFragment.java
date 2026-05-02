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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataPlanBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataPlan;
import com.iesaguadulce.mirecetariodecocina.model.PlanDia;
import com.iesaguadulce.mirecetariodecocina.model.PlanMenu;
import com.iesaguadulce.mirecetariodecocina.model.RecetaIngrediente;
import com.iesaguadulce.mirecetariodecocina.room.DiarioMenuJoin;
import com.iesaguadulce.mirecetariodecocina.room.Plan;
import com.iesaguadulce.mirecetariodecocina.room.RecetaIngredientesJoin;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaIngredienteViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DiarioViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.Diario_MenuViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanDiaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanMenuViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.PlanesViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RecetasViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para gestionar el registro o edición de un plan.
 * <p>
 * Este fragmento se encarga de recoger los datos de un plan y guardarlo en la base de datos.
 * Al guardar el plan se actualiza la lista de planes del fragmento de gestión de planes
 * que observa el ViewModel {@link PlanesViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanesViewModel
 */
public class DataPlanFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_plan.xml. */
    private FragmentDataPlanBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para compartir el identificador del usuario logueado. */
    private UserSharedViewModel userViewModel;

    /** Identificador del usuario logueado. */
    private String idUsuario = "";

    // ViewModel de base de datos
    /** ViewModel para la gestión de planes. */
    private PlanesViewModel planesViewModel;

    /** ViewModel para la gestión de diarios. */
    private DiarioViewModel diarioViewModel;

    /** ViewModel para la gestión de menus del diario. */
    private Diario_MenuViewModel diarioMenuViewModel;

    /** ViewModel para la consulta de recetas. */
    private RecetasViewModel recetaViewModel;

    // ViewModel de datos temporales que se guardan en memoria
    /** ViewModel para la gestión de datos del plan. */
    private DataPlanViewModel dataPlanViewModel;

    /** ViewModel para la gestión de datos de los días del plan. */
    private DataPlanDiaViewModel dataPlanDiaViewModel;

    /** ViewModel para la gestión de datos de los menus por día del plan. */
    private DataPlanMenuViewModel dataPlanMenuViewModel;

    /** ViewModel para la gestión de datos de los ingredientes de la receta. */
    private DataRecetaIngredienteViewModel dataRecetaIngredienteViewModel;

    /** Objeto que contiene los datos del plan actual. */
    private DataPlan dataPlan = null;

    /** Indica si ya existen datos en el DataPlanViewModel. */
    private boolean existeDataPlan = false;

    /** Lista de menus seleccionados para el plan. */
    private List<PlanMenu> listaMenusPlan;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataPlanFragment() {
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
        planesViewModel = new ViewModelProvider(this).get(PlanesViewModel.class);
        diarioViewModel = new ViewModelProvider(this).get(DiarioViewModel.class);
        diarioMenuViewModel = new ViewModelProvider(this).get(Diario_MenuViewModel.class);
        recetaViewModel = new ViewModelProvider(this).get(RecetasViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        dataPlanViewModel = new ViewModelProvider(requireActivity()).get(DataPlanViewModel.class);
        dataPlanDiaViewModel = new ViewModelProvider(requireActivity()).get(DataPlanDiaViewModel.class);
        dataPlanMenuViewModel = new ViewModelProvider(requireActivity()).get(DataPlanMenuViewModel.class);
        dataRecetaIngredienteViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaIngredienteViewModel.class);

        // Inflate the layout for this fragment
        return (binding = FragmentDataPlanBinding.inflate(inflater, container, false)).getRoot();
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

        // Obtenemos el identificador del usuario
        idUsuario = userViewModel.getIdUser();

        // Si ya existen los datos en el ViewModel, los mostramos
        dataPlanViewModel.getDataPlan().observe(getViewLifecycleOwner(), plan -> {
            if (plan != null) {
                this.dataPlan = plan;
                binding.namePlanValue.setText(plan.getNombre());
                if (plan.getIdPlan() > 0) {
                    binding.namePlanTextInput.setEnabled(false);
                }
                binding.descriptionPlanValue.setText(plan.getDescripcion());
                binding.typePlanValue.setText(plan.getTipo());
                binding.labelPlanValue.setText(plan.getEtiqueta());
                binding.durationPlanValue.setText(String.valueOf(plan.getDiasPlan()));
                binding.durationPlanTextInput.setEnabled(false);

                existeDataPlan = true;

                if (this.dataPlan.getIdPlan() != 0) {
                    // Rellenamos el ViewModel con los días del plan solo si está vacío
                    if (dataPlanDiaViewModel.getAllDias().getValue() == null || dataPlanDiaViewModel.getAllDias().getValue().isEmpty()) {
                        for (int i = 1; i <= plan.getDiasPlan(); i++) {
                            dataPlanDiaViewModel.addDia(new PlanDia("Día " + i, i));
                        }
                    }
                    // IMPORTANTE: Solo cargamos de la BD si el ViewModel de menus está vacío
                    // para evitar sobrescribir los cambios realizados en memoria al volver de otras pantallas.
                    if (dataPlanMenuViewModel.getAllPlanMenus().getValue() == null ||
                        dataPlanMenuViewModel.getAllPlanMenus().getValue().isEmpty()) {
                        // USAR getMenusByPlan en lugar de getMenusDiario, pasando el idPlan
                        diarioMenuViewModel.getMenusByPlan(this.dataPlan.getIdPlan()).observe(getViewLifecycleOwner(), listaJoin -> {
                            if (listaJoin != null && !listaJoin.isEmpty()) {
                                List<PlanMenu> listaParaViewModel = new ArrayList<>();
                                // Convertimos el objeto de BD al objeto de UI
                                for (DiarioMenuJoin join : listaJoin) {
                                    listaParaViewModel.add(new PlanMenu(
                                            join.idMenu,
                                            join.nombre,
                                            join.descripcion,
                                            join.orden
                                    ));
                                }

                                // Pasamos la lista completa al ViewModel que gestiona la pantalla de edición
                                dataPlanMenuViewModel.setListaPlanMenus(listaParaViewModel);

                            }
                        });
                    }

                    if (dataRecetaIngredienteViewModel.getAllIngredientes().getValue() == null ||
                            dataRecetaIngredienteViewModel.getAllIngredientes().getValue().isEmpty()) {
                        recetaViewModel.getIngredientesPlan(this.dataPlan.getIdPlan()).observe(getViewLifecycleOwner(), listaJoin -> {
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
        binding.namePlanValue.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        binding.namePlanTextInput.setError(null);
                        ocultarTeclado(v);
                    }
                });
        binding.descriptionPlanValue.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        ocultarTeclado(v);
                    }
                });
        binding.typePlanValue.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        ocultarTeclado(v);
                    }
                });
        binding.labelPlanValue.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        ocultarTeclado(v);
                    }
                });
        binding.durationPlanValue.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        binding.durationPlanTextInput.setError(null);
                        ocultarTeclado(v);
                    }
                });

        // Borrar el contenido del campo al pulsar el icono de borrar
        binding.namePlanTextInput.setEndIconOnClickListener(v -> {
            binding.namePlanValue.setText("");
        });
        binding.descriptionPlanTextInput.setEndIconOnClickListener(v -> {
            binding.descriptionPlanValue.setText("");
        });
        binding.typePlanTextInput.setEndIconOnClickListener(v -> {
            binding.typePlanValue.setText("");
        });
        binding.labelPlanTextInput.setEndIconOnClickListener(v -> {
            binding.labelPlanValue.setText("");
        });
        binding.durationPlanTextInput.setEndIconOnClickListener(v -> {
            binding.durationPlanValue.setText("");
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario
        binding.btnPlanCancel.setOnClickListener(v -> {
            // Reiniciamos todos los datos temporales antes de volver
            dataPlanViewModel.clear();
            dataPlanMenuViewModel.clear();
            dataPlanDiaViewModel.clear();
            // Volvemos al listado de planes
            navController.navigate(R.id.action_dataPlanFragment_to_planChefFragment);
        });

        // Botón para guardar el plan
        binding.btnPlanSave.setOnClickListener(v -> {
            String nombre = binding.namePlanValue.getText().toString().trim();
            String descripcion = binding.descriptionPlanValue.getText().toString().trim();
            String tipo = binding.typePlanValue.getText().toString().trim();
            String etiqueta = binding.labelPlanValue.getText().toString().trim();
            String diasStr = binding.durationPlanValue.getText().toString().trim();

            // Obtenemos el listado menus por días elegidos
            listaMenusPlan = dataPlanMenuViewModel.getAllPlanMenus().getValue();
            if (listaMenusPlan == null) {
                listaMenusPlan = new ArrayList<>();
            }

            // Validamos los datos
            Boolean valid = true;
            if (nombre.isEmpty()) {
                binding.namePlanTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (diasStr.isEmpty()) {
                binding.durationPlanTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (valid) {
                // Generamos el plan
                Plan plan = new Plan(nombre, descripcion, tipo, etiqueta, Integer.parseInt(diasStr), idUsuario);

                // Si el plan tiene un identificador en base de datos, lo actualizamos.
                if (dataPlan != null && dataPlan.getIdPlan() > 0) {
                    plan.setIdPlan(dataPlan.getIdPlan());

                    // Llamada única al viewmodel para actualizar el plan
                    planesViewModel.actualizarPlanConMenus(
                            plan,
                            listaMenusPlan
                    );
                    Toast.makeText(getContext(), "Plan actualizado correctamente", Toast.LENGTH_SHORT).show();

                    // Reiniciamos todos los datos temporales y volvemos al listado de planes
                    inicializamosViewModelyVolvemos();

                // Si el plan no tiene un identificador en base de datos, lo insertamos.
                } else {
                    // Observamos el plan por su NOMBRE para verificar si ya existe
                    planesViewModel.getPlanByNombre(nombre).observe(getViewLifecycleOwner(), planBD -> {
                        if (planBD != null) {
                            // El plan ya existe en la base de datos
                            binding.namePlanTextInput.setError("El nombre del menú ya existe");
                        } else {
                            // El plan no existe, procedemos a guardar el plan
                            planesViewModel.insertarPlanConMenus(
                                    plan,
                                    listaMenusPlan
                            );

                            Toast.makeText(getContext(), "Plan guardado correctamente", Toast.LENGTH_SHORT).show();
                            // Reiniciamos todos los datos temporales y volvemos al listado de planes
                            inicializamosViewModelyVolvemos();
                        }
                    });
                }
            }
        });

        // Botón que genera los días (diario) de la planificación
        binding.btnPlanDays.setOnClickListener(v -> {
            String diasStr = binding.durationPlanValue.getText().toString().trim();

            // Validamos los datos
            Boolean valid = true;
            if (diasStr.isEmpty()) {
                binding.durationPlanTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (valid) {
                binding.durationPlanTextInput.setEnabled(false);

                int dias = Integer.parseInt(diasStr);
                if (!existeDataPlan) {
                    for (int i = 1; i <= dias; i++) {
                        dataPlanDiaViewModel.addDia(new PlanDia("Día " + i, i));
                    }
                }

                // Guardamos los datos del plan, manteniendo el ID si ya existe
                int currentId = 0;
                if (dataPlan != null) {
                    currentId = dataPlan.getIdPlan();
                }

                String nombre = binding.namePlanValue.getText().toString().trim();
                String descripcion = binding.descriptionPlanValue.getText().toString().trim();
                String tipo = binding.typePlanValue.getText().toString().trim();
                String etiqueta = binding.labelPlanValue.getText().toString().trim();
                dataPlanViewModel.setDataPlan(new DataPlan(currentId, nombre, descripcion, tipo, etiqueta, dias));

                // Pasamos el identificador del plan a la siguiente pantalla
                Bundle args = new Bundle();
                args.putString("dias", diasStr);
                navController.navigate(R.id.action_dataPlanFragment_to_planificacionDiariaFragment, args);
            }
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
                menuInflater.inflate(R.menu.data_plan_menu, menu);
            }

            /**
             * Maneja la selección de un elemento del menú.
             * <p>
             * Sólo se gestiona la opción de ver la lista de compras.
             * </p>
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú seleccionado.
             * @return - boolean - {@code true} si el elemento ha sido seleccionado, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_lista_compras) {
                    if (dataPlan != null && dataPlan.getIdPlan() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("tipoLista", "Plan");
                        navController.navigate(R.id.action_dataPlanFragment_to_listaCompraFragment, bundle);
                    } else {
                        Toast.makeText(getContext(), "No se puede ver la lista de la compra para este plan porque aún no se ha guardado", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

    }

    /**
     * Reiniciamos todos los datos temporales y volvemos al fragmento indicado.
     */
    private void inicializamosViewModelyVolvemos () {
            // Reiniciamos todos los datos temporales antes de volver
            dataPlanViewModel.clear();
            dataPlanMenuViewModel.clear();
            dataPlanDiaViewModel.clear();

            navController.navigate(
                    R.id.action_dataPlanFragment_to_planChefFragment,
                    null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.dataPlanFragment, true) // Borra el fragmento de la pila
                            .build()
            );
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
