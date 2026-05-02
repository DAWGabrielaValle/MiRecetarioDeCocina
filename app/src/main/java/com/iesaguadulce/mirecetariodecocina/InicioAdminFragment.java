package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentInicioAdminBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataUsuario;
import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioRolJoin;
import com.iesaguadulce.mirecetariodecocina.ui.UsuariosAdapter;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataUsuarioViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RolViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UsuariosViewModel;

import java.util.List;

/**
 * Fragmento para gestionar el listado de usuarios.
 * <p>
 * Este fragmento se encarga de mostrar la lista de usuarios y permitir la gestión de los mismos.
 * En este fragmento se pueden añadir, editar y eliminar usuarios.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuariosAdapter
 */
public class InicioAdminFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_inicio_admin.xml. */
    private FragmentInicioAdminBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de usuarios. */
    private UsuariosViewModel viewModel;

    /** ViewModel para la gestión de roles de usuarios. */
    private RolViewModel viewModelRol;

    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos del usuario actual. */
    private DataUsuarioViewModel dataUsuarioViewModel;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public InicioAdminFragment() {
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
        viewModel = new ViewModelProvider(this).get(UsuariosViewModel.class);
        dataUsuarioViewModel = new ViewModelProvider(requireActivity()).get(DataUsuarioViewModel.class);

        return (binding = FragmentInicioAdminBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del listado de usuarios, el botón de añadir y el botón de guardar.
     * Manejo de la opción de editar al dar clic en un usuario y la opción de eliminar.
     * Configura el menú de ayuda y el buscador de usuarios.
     *
     * @param view - {@link View} - La vista creada.
     * @param savedInstanceState  - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón flotante para añadir un usuario
        binding.btnFabUsers.setOnClickListener(v -> {
            navController.navigate(R.id.action_inicioAdminFragment_to_dataUserFragment);
        });

        // Asignamos el adaptador al RecyclerView
        UsuariosAdapter adapter = new UsuariosAdapter();
        binding.rvUsers.setAdapter(adapter);
        // De esta forma se ven los elementos filtrados en la búsqueda
        binding.rvUsersSearch.setAdapter(adapter);

        // Ocultar el botón flotante cuando se desplaza hacia arriba
        binding.rvUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && binding.btnFabUsers.isShown()) {
                    binding.btnFabUsers.hide();
                } else if (dy < 0 && !binding.btnFabUsers.isShown()) {
                    binding.btnFabUsers.show();
                }
            }
        });

        // Configuramos el listener para eliminar un usuario
        adapter.setOnUsuarioDeleteListener(usuario -> {
            Usuario usuarioToDelete = new Usuario(usuario.getIdUsuario(), usuario.getIdRol(), usuario.getNombre(), usuario.getPswd(), usuario.getFechaAlta(), usuario.getFechaFin());

            viewModel.delete(usuarioToDelete);
            Toast.makeText(this.getContext(), "Usuario eliminado: " + usuario.getIdUsuario(), Toast.LENGTH_SHORT).show();

        });

        // Configuramos el listener para editar un usuario
        adapter.setOnUsuarioViewContentListener(usuario -> {
            dataUsuarioViewModel.setDataUsuario(new DataUsuario(usuario.getIdUsuario(), usuario.getIdRol(), usuario.getRol(), usuario.getNombre(), usuario.getPswd(), usuario.getFechaAlta(), usuario.getFechaFin()));
            navController.navigate(R.id.action_inicioAdminFragment_to_dataUserFragment);
        });

        // Observamos la lista de usuarios y actualizamos el adaptador
        viewModel.getAllUsuariosRol().observe(getViewLifecycleOwner(), new Observer<List<UsuarioRolJoin>>() {
            @Override
            public void onChanged(List<UsuarioRolJoin> usuarios) {
                adapter.setUsuarios(usuarios);
            }
        });

        // Configuramos el buscador
        binding.adminSearchView.setupWithSearchBar(binding.adminSearchBar);
        // Implementación del buscador para usar el SearchView
        binding.adminSearchView.getEditText().addTextChangedListener(new TextWatcher() {

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param s     - CharSequence - El texto actual del EditText.
             * @param start - int - La posición de inicio del texto.
             * @param count - int - El número de caracteres agregados.
             * @param after - int - El número de caracteres eliminados.
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            /**
             * Se llama cuando el texto del EditText cambia. Se obtiene el texto actual y se filtra el adaptador.
             *
             * @param s      - CharSequence - El texto actual del EditText.
             * @param start  - int - La posición de inicio del texto.
             * @param before - int - El número de caracteres eliminados.
             * @param count  - int - El número de caracteres agregados.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            /**
             * Se llama cuando el texto del EditText cambia.
             *
             * @param s - CharSequence - El texto actual del EditText.
             */
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Añadir el menú
        requireActivity().addMenuProvider(new MenuProvider() {

            /**
             * Crea el menú de opciones.
             *
             * @param menu         - {@link Menu} - El menú a crear.
             * @param menuInflater - {@link MenuInflater} - El objeto para inflar el menú.
             */
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.help_menu, menu);
            }

            /**
             * Maneja la selección de un elemento del menú.
             * <p>
             * La opción de ayuda es la única del menú.
             * </p>
             *
             * @param menuItem - {@link MenuItem} - El elemento del menú seleccionado.
             * @return - boolean - {@code true} si el elemento ha sido seleccionado, {@code false} en caso contrario.
             */
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.action_ayuda) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mensaje", "user");
                    navController.navigate(R.id.action_inicioAdminFragment_to_ayuda_nav_graph, bundle);
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}
