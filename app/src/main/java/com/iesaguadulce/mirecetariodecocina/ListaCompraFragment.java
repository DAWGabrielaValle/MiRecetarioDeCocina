package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentListaCompraBinding;
import com.iesaguadulce.mirecetariodecocina.ui.ListaCompraAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaIngredienteViewModel;

/**
 * Fragmento para gestionar el listado de la compra.
 * <p>
 * Este fragmento se encarga solamente de mostrar la lista de ingredientes de una o más recetas.
 * Dependiendo del fragmento desde el que se llame se mostrará una lista de ingredientes u otra.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ListaCompraAdapter
 */
public class ListaCompraFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_lista_compra.xml. */
    private FragmentListaCompraBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel para la gestión de ingredientes. */
    private DataRecetaIngredienteViewModel viewModel;

    /** Tipo de lista de ingredientes. */
    private String tipoLista = "";

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public ListaCompraFragment() {
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
        viewModel = new ViewModelProvider(requireActivity()).get(DataRecetaIngredienteViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentListaCompraBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de la compra de ingredientes.
     * Manejo de la opción de cerrar la lista de la compra.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el tipo de lista de la compra de los argumentos del fragmento
        if (getArguments() != null) {
            tipoLista = getArguments().getString("tipoLista");
            if (tipoLista != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Lista de la compra: "+tipoLista);
            } else {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Lista de la compra");
            }
        }

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cerrar la lista de la compra y volver al fragmento anterior
        binding.btnCompraListCerrar.setOnClickListener(v -> {
            navController.popBackStack();
        });

        // Asignamos el adaptador al RecyclerView
        ListaCompraAdapter adapter = new ListaCompraAdapter();
        binding.rvCompraList.setAdapter(adapter);

        // Observamos la lista de ingredientes y actualizamos el adaptador
        viewModel.getAllIngredientes().observe(getViewLifecycleOwner(), listaIngredientes -> {
            adapter.setIngredientes(listaIngredientes);
        });
    }
}
