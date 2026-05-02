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

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentListaRecetasMenuBinding;
import com.iesaguadulce.mirecetariodecocina.ui.ListaRecetasAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataMenuRecetaViewModel;

/**
 * Fragmento para gestionar el listado de recetas de un menú.
 * <p>
 * Este fragmento se encarga de mostrar la lista de recetas de un menú y permitir la gestión de las mismas.
 * En este fragmento se pueden añadir y eliminar recetas.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaRecetasAdapter
 */
public class ListaRecetasMenuFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_lista_recetas_menu.xml. */
    private FragmentListaRecetasMenuBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión temporal de recetas en memoria. */
    private DataMenuRecetaViewModel viewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public ListaRecetasMenuFragment() {
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
        viewModel = new ViewModelProvider(requireActivity()).get(DataMenuRecetaViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentListaRecetasMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de recetas, el botón de añadir y el botón de guardar.
     * Manejo de la opción de eliminar una receta del listado.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para guardar los cambios y volver al formulario del menú
        binding.btnRecipeListSave.setOnClickListener(v -> {
            navController.navigate(R.id.action_listaRecetasMenuFragment_to_dataMenuFragment);
        });

        // Botón flotante para añadir una receta
        binding.btnFabRecipes.setOnClickListener(v -> {
            navController.navigate(R.id.action_listaRecetasMenuFragment_to_seleccionRecetasFragment);
        });

        // Asignamos el adaptador al RecyclerView
        ListaRecetasAdapter adapter = new ListaRecetasAdapter();
        binding.rvRecipeList.setAdapter(adapter);

        // Configuramos el listener para eliminar una receta
        adapter.setOnRecetaDeleteListener(receta -> {
            viewModel.removeReceta(receta);
        });

        // Observamos la lista de recetas y actualizamos el adaptador
        viewModel.getAllRecetas().observe(getViewLifecycleOwner(), recetas -> {
            adapter.setRecetas(recetas);
        });

    }
}