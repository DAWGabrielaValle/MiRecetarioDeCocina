package com.iesaguadulce.mirecetariodecocina;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataCommentsRecipeBinding;
import com.iesaguadulce.mirecetariodecocina.room.Comentario;
import com.iesaguadulce.mirecetariodecocina.viewmodel.ComentariosViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataRecetaViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UserSharedViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Fragmento para gestionar el registro de comentarios en una receta específica.
 * <p>
 * Este fragmento se encarga de recoger los datos de un comentario y guardarlo en la base de datos,
 * junto con la fecha y hora actual, además de los identificadores del usuario y de la receta.
 * Al guardar el comentario, se actualiza la lista de comentarios del fragmento de gestión de comentarios
 * que observa el ViewModel {@link ComentariosViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see ComentariosViewModel
 */
public class DataCommentsRecipeFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_comments_recipe.xml. */
    private FragmentDataCommentsRecipeBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    /** ViewModel compartido para obtener los datos del usuario logueado. */
    private UserSharedViewModel userViewModel;

    /** ViewModel para realizar la operación de inserción del comentario en la base de datos. */
    private ComentariosViewModel comentarioViewModel;

    /** ViewModel compartido para identificar la receta a la cual se le añadirá el comentario. */
    private DataRecetaViewModel dataRecetaViewModel;

    /** Identificador del autor del comentario. */
    private String idUsuario = "";

    /** Fecha y hora del sistema capturada en el momento de la redacción. */
    private String fechaHora = "";

    /** Identificador de la receta vinculada. */
    private int idReceta = 0;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataCommentsRecipeFragment() {
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
        comentarioViewModel = new ViewModelProvider(this).get(ComentariosViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        dataRecetaViewModel = new ViewModelProvider(requireActivity()).get(DataRecetaViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentDataCommentsRecipeBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura la lógica del formulario, validaciones y el evento de guardado.
     * <p>
     * Este método inicializa la fecha actual, recupera los identificadores necesarios de los
     * ViewModels compartidos y establece los listeners para el botón de guardado
     * y el comportamiento del teclado.
     * </p>
     *
     * @param view               - {@link View} - La vista creada.
     * @param savedInstanceState - Estado previo guardado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el identificador del usuario y de la receta
        idUsuario = userViewModel.getIdUser();
        idReceta = dataRecetaViewModel.getDataReceta().getValue().getIdReceta();

        // Obtenemos la fecha actual
        LocalDateTime fechaHoy = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        fechaHora = formato.format(fechaHoy);
        binding.tvInfoCommentsRecipe.setText(fechaHora);

        // Cerrar teclado al perder el foco
        binding.textCommentValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.textCommentTextInput.setError(null);
                ocultarTeclado(v);
            }
        });

        // Botón para borrar el contenido del campo de texto
        binding.btnEliminateComment.setOnClickListener( v -> {
            binding.textCommentValue.setText("");
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario y volver al anterior
        binding.btnCommentsCancel.setOnClickListener(v -> {
            navController.popBackStack();
        });

        // Botón para guardar el comentario
        binding.btnCommentsSave.setOnClickListener(v -> {
            String comentarioTxt = binding.textCommentValue.getText().toString().trim();
                        
            // Validaciones
            boolean valid = true;
            if (comentarioTxt.isEmpty()) {
                binding.textCommentTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }

            if (valid) {
                // Generamos el comentario
                Comentario comentario = new Comentario(comentarioTxt, fechaHora, idUsuario, idReceta);

                // Llamada única al viewmodel para insertar el comentario
                comentarioViewModel.insert(comentario);
                Toast.makeText(getContext(), "Comentario guardado correctamente", Toast.LENGTH_SHORT).show();

                // Volvemos al fragmento anterior
                navController.popBackStack();
            }
        });
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