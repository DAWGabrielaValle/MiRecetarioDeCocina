package com.iesaguadulce.mirecetariodecocina;

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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentPlanificacionDiariaBinding;
import com.iesaguadulce.mirecetariodecocina.ui.PlanDiaAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataPlanDiaViewModel;

/**
 * Fragmento para gestionar la planificación diaria.
 * <p>
 * Este fragmento se encarga de mostrar la lista de días de la planificación.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanDiaAdapter
 */
public class PlanificacionDiariaFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_planificacion_diaria.xml. */
    private FragmentPlanificacionDiariaBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión temporal de datos de la planificación diaria en memoria. */
    private DataPlanDiaViewModel viewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public PlanificacionDiariaFragment() {
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
        viewModel = new ViewModelProvider(requireActivity()).get(DataPlanDiaViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentPlanificacionDiariaBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de los días de la planificación.
     * Se muestra el listado de los días de la planificación.
     * Permite editar el contenido de los días de la planificación.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos los días de la planificación
        if (getArguments() != null) {
            String dias = getArguments().getString("dias");
        }

        // Asignamos el adaptador al RecyclerView
        PlanDiaAdapter adapter = new PlanDiaAdapter();
        binding.rvDaysList.setAdapter(adapter);

        // Observamos la lista de días y actualizamos el adaptador
        viewModel.getAllDias().observe(getViewLifecycleOwner(), dias -> {
            adapter.setDias(dias);
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Configuramos el listener para editar un día de la planificación
        adapter.setOnPlanDiaViewContentListener(dia -> {
            Bundle bundle = new Bundle();
            bundle.putInt("orden", dia.getOrden());

            navController.navigate(R.id.action_planificacionDiariaFragment_to_dayXPlanFragment, bundle);
        });

        // Botón para guardar y volver al formulario de la planificación
        binding.btnDayPlanSave.setOnClickListener(v -> {
            navController.navigate(R.id.action_planificacionDiariaFragment_to_dataPlanFragment);
        });
    }
}