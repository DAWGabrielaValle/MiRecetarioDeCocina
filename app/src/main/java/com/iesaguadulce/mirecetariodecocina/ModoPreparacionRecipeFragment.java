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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentModoPreparacionRecipeBinding;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaViewModel;

/**
 * Fragmento para gestionar el modo de preparación de una receta.
 * <p>
 * Este fragmento se encarga de permitir al usuario editar el modo de preparación de una receta.
 * Se guarda el modo de preparación en el ViewModel compartido.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see DataRecetaViewModel
 */
public class ModoPreparacionRecipeFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_modo_preparacion_recipe.xml. */
    private FragmentModoPreparacionRecipeBinding binding;

    /** ViewModel para la gestión temporal de datos de la receta actual. */
    private DataRecetaViewModel dataRecetaViewModel;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public ModoPreparacionRecipeFragment() {
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
        // Usamos requireActivity() para compartir el ViewModel con otros fragmentos de la misma actividad
        dataRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentModoPreparacionRecipeBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del modo de preparación de la receta.
     * Permite al usuario editar el modo de preparación de la receta.
     * Guarda el modo de preparación en el ViewModel compartido.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Si ya hay un texto guardado en el ViewModel, lo mostramos
        dataRecetaViewModel.getToDoReceta().observe(getViewLifecycleOwner(), texto -> {
            if (texto != null) {
                binding.textHowtomakeMultiline.setText(texto);
            }
        });

        // Botón para guardar el contenido de la receta en el ViewModel y volver al fragmento anterior
        binding.btnHowtomakeSave.setOnClickListener(v -> {
            String modoHacerse = binding.textHowtomakeMultiline.getText().toString().trim();
            dataRecetaViewModel.setToDoReceta(modoHacerse);
            navController.popBackStack();
        });
    }
}
