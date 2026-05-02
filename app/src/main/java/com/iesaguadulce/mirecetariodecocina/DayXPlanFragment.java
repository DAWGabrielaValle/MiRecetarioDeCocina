package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDayXPlanBinding;
import com.iesaguadulce.mirecetariodecocina.ui.ListaMenusAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanMenuViewModel;

/**
 * Fragmento para gestionar el listado de menús por día (diario) de una planificación.
 * <p>
 * Este fragmento se encarga de mostrar la lista de menús por día (diario) de una planificación.
 * En este fragmento se pueden añadir o eliminar menús de la lista.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaMenusAdapter
 */
public class DayXPlanFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_day_x_plan.xml. */
    private FragmentDayXPlanBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión de datos de los menus por día del plan. */
    private DataPlanMenuViewModel viewModel;

    /** Identificador del día de la planificación. */
    private int dia = 0;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DayXPlanFragment() {
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
        viewModel = new ViewModelProvider(requireActivity()).get(DataPlanMenuViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentDayXPlanBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de menús por día, el botón de añadir y el botón de guardar.
     * <p>
     * Este método recupera los datos necesarios de los ViewModels compartidos y establece los
     * listeners para el botón de añadir y el botón de guardar.
     * </p>
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el identificador del día de la planificación desde los argumentos
        if (getArguments() != null) {
            dia = getArguments().getInt("orden");
            if (dia > 0 && getActivity() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Plan día " + dia);
            }
        }

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para añadir menús al listado de menús del día
        binding.btnFabMenus.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("orden", dia);
            navController.navigate(R.id.action_dayXPlanFragment_to_seleccionMenusFragment, bundle);
        });

        // Botón para guardar el listado de menús del día
        binding.btnMenuListSave.setOnClickListener(v -> {
            navController.navigate(R.id.action_dayXPlanFragment_to_planificacionDiariaFragment);
        });

        // Asignamos el adaptador al RecyclerView
        ListaMenusAdapter adapter = new ListaMenusAdapter();
        binding.rvMenuList.setAdapter(adapter);

        // Configuramos el listener para eliminar un menú del listado
        adapter.setOnMenuDeleteListener(menu -> {
            viewModel.removePlanMenu(menu);
        });

        // Configuramos el listener para mostrar el listado de menús del día
        viewModel.getPlanMenusByOrden(dia).observe(getViewLifecycleOwner(), menus -> {
            adapter.setMenus(menus);
        });

    }
}
