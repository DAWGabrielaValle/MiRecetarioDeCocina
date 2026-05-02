package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentComentariosBinding;
import com.iesaguadulce.mirecetariodecocina.ui.ComentariosAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.ComentariosViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

/**
 * Fragmento encargado de mostrar y gestionar la lista de comentarios de una receta específica.
 * <p>
 * Este fragmento actúa como la vista principal para los comentarios. Se encarga de:
 * <ul>
 *     <li>Obtener la receta actual desde {@link DataRecetaViewModel}.</li>
 *     <li>Cargar y observar la lista de comentarios mediante {@link ComentariosViewModel}.</li>
 *     <li>Verificar la identidad del usuario a través de {@link UserSharedViewModel} para permitir el borrado.</li>
 *     <li>Gestionar la navegación hacia la creación de comentarios y el regreso a la receta.</li>
 * </ul>
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ComentariosAdapter
 * @see ComentariosViewModel
 */
public class ComentariosFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_comentarios.xml. */
    private FragmentComentariosBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel compartido para obtener los datos del usuario logueado. */
    private UserSharedViewModel userViewModel;

    /** Identificador del usuario actual, utilizado para validar permisos de borrado. */
    private String idUsuario = "";

    // ViewModel de base de datos
    /** ViewModel para la gestión de comentarios. */
    private ComentariosViewModel viewModel;

    /** ViewModel para la gestión de datos de recetas. */
    private DataRecetaViewModel dataRecetaViewModel;

    /** Identificador de la receta actual. */
    private int idReceta;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public ComentariosFragment() {
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
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(ComentariosViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        dataRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaViewModel.class);
        return (binding = FragmentComentariosBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura el RecyclerView, los observadores de LiveData y los eventos de los botones.
     * <p>
     * Implementa un sistema de observación encadenada para asegurar que solo se pidan
     * los comentarios una vez se ha obtenido correctamente el identificador de la receta.
     * </p>
     *
     * @param view - {@link View} - La vista raíz del fragmento.
     * @param savedInstanceState - Estado previo del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el identificador del usuario
        idUsuario = userViewModel.getIdUser();

        // Configuramos el RecyclerView
        ComentariosAdapter adapter = new ComentariosAdapter();
        binding.rvComentariosList.setAdapter(adapter);

        // Encadenamos las observaciones para asegurar que tenemos el idReceta antes de pedir los comentarios
        dataRecetaViewModel.getDataReceta().observe(getViewLifecycleOwner(), receta -> {
            if (receta != null) {
                this.idReceta = receta.getIdReceta();

                // Ahora que tenemos el ID real, observamos los comentarios de esa receta
                viewModel.getComentariosReceta(idReceta).observe(getViewLifecycleOwner(), comentarios -> {
                    adapter.setComentarios(comentarios);
                });
            }
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón flotante para crear comentarios
        binding.btnFabListComentarios.setOnClickListener(v -> {
            navController.navigate(R.id.action_comentariosFragment_to_dataCommentsRecipeFragment);
        });

        // Botón para cerrar el fragmento y volver a la receta
        binding.btnListComentariosClose.setOnClickListener(v -> {
            navController.navigate(R.id.action_comentariosFragment_to_dataRecetaFragment);
        });

        // Ocultamos el botón flotante cuando se desplaza hacia arriba
        binding.rvComentariosList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabListComentarios.isShown()) {
                    binding.btnFabListComentarios.hide();
                } else if (dy < 0 && !binding.btnFabListComentarios.isShown()) {
                    binding.btnFabListComentarios.show();
                }
            }
        });

        // Configuramos el listener para eliminar un comentario
        adapter.setOnComentarioDeleteListener(comentario -> {
            if (comentario != null && comentario.getIdUsuario().equals(idUsuario)) {
                viewModel.delete(comentario);
            } else {
                Toast.makeText(this.getContext(), "No tienes permiso para eliminar este comentario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
