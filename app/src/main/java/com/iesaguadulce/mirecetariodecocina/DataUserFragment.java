package com.iesaguadulce.mirecetariodecocina;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentDataUserBinding;
import com.iesaguadulce.mirecetariodecocina.model.DataUsuario;
import com.iesaguadulce.mirecetariodecocina.room.Rol;
import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.viewmodel.DataUsuarioViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.RolViewModel;
import com.iesaguadulce.mirecetariodecocina.viewmodel.UsuariosViewModel;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Fragmento para gestionar el registro o edición de un usuario.
 * <p>
 * Este fragmento se encarga de recoger los datos de un usuario y guardarlo en la base de datos.
 * Al guardar el usuario se actualiza la lista de usuarios del fragmento de gestión de usuarios
 * que observa el ViewModel {@link UsuariosViewModel}.
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see UsuariosViewModel
 */
public class DataUserFragment extends Fragment {

    /** Enlace a las vistas del layout fragment_data_user.xml. */
    private FragmentDataUserBinding binding;

    /** Controlador de navegación para gestionar la navegación entre fragmentos. */
    private NavController navController;

    // ViewModel de base de datos
    /** ViewModel para la gestión de usuarios. */
    private UsuariosViewModel viewModelUser;

    /** ViewModel para la gestión de roles. */
    private RolViewModel viewModelRol;

    // ViewModel de datos temporales
    /** ViewModel para la gestión temporal de datos del usuario actual. */
    private DataUsuarioViewModel dataUsuarioViewModel;

    /** Objeto que contiene los datos del usuario actual. */
    private DataUsuario dataUsuario = null;

    /** Lista de roles. */
    private List<Rol> listaRoles;

    /**
     * Constructor por defecto de la clase requerido por Android.
     */
    public DataUserFragment() {
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
        viewModelUser = new ViewModelProvider(this).get(UsuariosViewModel.class);
        viewModelRol = new ViewModelProvider(this).get(RolViewModel.class);
        dataUsuarioViewModel = new ViewModelProvider(requireActivity()).get(DataUsuarioViewModel.class);
        // Inflate the layout for this fragment
        return (binding = FragmentDataUserBinding.inflate(inflater, container, false)).getRoot();
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

        // Obtenemos la fecha actual
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = formato.format(fechaHoy);
        binding.todayValue.setText(fecha);

        // Si existen datos en el ViewModel, los mostramos
        dataUsuarioViewModel.getDataUsuario().observe(getViewLifecycleOwner(), dataUsuario -> {
            if (dataUsuario != null) {
                this.dataUsuario = dataUsuario;
                binding.groupUserRadios.check(dataUsuario.getRol().equals("Administrador") ? R.id.radio_admin : R.id.radio_chef);
                binding.userValue.setText(dataUsuario.getIdUsuario());
                binding.userTextInput.setEnabled(false);
                binding.nameUserValue.setText(dataUsuario.getNombreUsuario());
                binding.todayValue.setText(dataUsuario.getFechaAlta());
                binding.expiresDateValue.setText(dataUsuario.getFechaFin());
            }
        });

        // Deshabilitamos el foco en el campo de fecha de fin de uso para mostrar el calendario
        binding.expiresDateValue.setFocusable(false);
        binding.expiresDateValue.setOnClickListener(v -> mostrarCalendario());

        // Obtenemos la lista de roles
        viewModelRol.getRoles().observe(getViewLifecycleOwner(), roles -> {
            listaRoles = roles;
        });

        // Cerrar teclado al perder el foco
        binding.userValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.userTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.nameUserValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.nameUserTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.passwordUserValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.passwordUserTextInput.setError(null);
                ocultarTeclado(v);
            }
        });
        binding.repasswordUserValue.setOnFocusChangeListener((v, hasFocus) -> {
            String password = binding.passwordUserValue.getText().toString();
            String repassword = binding.repasswordUserValue.getText().toString();

            // Comprobamos que las contraseñas coincidan
            if (!hasFocus && !password.equals(repassword)) {
                binding.repasswordUserTextInput.setError("Las contraseñas no coinciden");
            } else {
                binding.repasswordUserTextInput.setError(null);
                ocultarTeclado(v);
            }
        });

        // Borrar el contenido del campo al pulsar el icono de borrar
        binding.userTextInput.setEndIconOnClickListener(v -> {
            binding.userValue.setText("");
        });
        binding.nameUserTextInput.setEndIconOnClickListener(v -> {
            binding.nameUserValue.setText("");
        });
        binding.passwordUserTextInput.setEndIconOnClickListener(v -> {
            binding.passwordUserValue.setText("");
        });
        binding.repasswordUserTextInput.setEndIconOnClickListener(v -> {
            binding.repasswordUserValue.setText("");
        });
        binding.expiresDateTextInput.setEndIconOnClickListener(v -> {
            binding.expiresDateValue.setText("");
        });

        // Inicializamos el controlador de navegación
        navController = Navigation.findNavController(view);

        // Botón para cancelar el formulario y volver al fragmento anterior
        binding.btnUserCancel.setOnClickListener(v -> {
            // Borramos el ViewModel dataUsuario temporal
            dataUsuarioViewModel.clear();
            // Volvemos al fragmento anterior
            navController.popBackStack(R.id.inicioAdminFragment, false);
        });

        // Botón para guardar el usuario
        binding.btnUserSave.setOnClickListener(v -> {

            String rol = binding.groupUserRadios.getCheckedRadioButtonId() == R.id.radio_admin ? "Administrador" : "Chef";
            String idUsuario = binding.userValue.getText().toString().trim();
            String nombreUsuario = binding.nameUserValue.getText().toString().trim();
            String passwordUsuario = binding.passwordUserValue.getText().toString().trim();
            String repasswordUsuario = binding.repasswordUserValue.getText().toString().trim();
            String fechaRegistro = binding.todayValue.getText().toString();
            String fechaFinUso = binding.expiresDateValue.getText().toString();

            // Validaciones
            boolean valid = true;
            if (idUsuario.isEmpty()) {
                binding.userTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (nombreUsuario.isEmpty()) {
                binding.nameUserTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (passwordUsuario.isEmpty()) {
                binding.passwordUserTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }
            if (repasswordUsuario.isEmpty()) {
                binding.repasswordUserTextInput.setError("El campo no puede estar vacío");
                valid = false;
            }

            if (valid) {
                try {
                    String passUser = MainActivity.obtenerPassword(passwordUsuario);
                    int idRol = 1;

                    if (listaRoles != null) {
                        for (Rol rolActual : listaRoles) {
                            if (rolActual.getRol().equals(rol)) {
                                idRol = rolActual.getIdRol();
                            }
                        }
                    }

                    // Generamos el usuario
                    Usuario usuario = new Usuario(idUsuario, idRol, nombreUsuario, passUser, fechaRegistro, fechaFinUso);

                    // Si el usuario tiene un identificador en base de datos, lo actualizamos.
                    if (dataUsuario != null) {
                        // Actualizamos el usuario
                        viewModelUser.update(usuario);
                        Toast.makeText(this.getContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                        // Borramos el ViewModel dataUsuario temporal
                        dataUsuarioViewModel.clear();
                        // Volvemos al fragmento anterior
                        navController.popBackStack(R.id.inicioAdminFragment, false);

                    // Si el usuario no tiene un identificador en base de datos, lo insertamos.
                    } else {
                        // Observamos el usuario por su ID para verificar si ya existe
                        viewModelUser.getUsuario(idUsuario).observe(getViewLifecycleOwner(), usuarioDB -> {

                            if (usuarioDB != null) {
                                // El usuario ya existe en la base de datos
                                binding.userTextInput.setError("El nombre de usuario ya existe");
                            } else {
                                // El usuario no existe, procedemos a guardar los datos
                                viewModelUser.insert(usuario);
                                Toast.makeText(this.getContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                                // Borramos el ViewModel dataUsuario temporal
                                dataUsuarioViewModel.clear();
                                // Volvemos al fragmento anterior
                                navController.popBackStack(R.id.inicioAdminFragment, false);

                            }
                        });
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Error al encriptar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Muestra el calendario para seleccionar la fecha de fin de uso.
     */
    private void mostrarCalendario() {
        // Obtenemos la fecha actual
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    // Formatear fecha seleccionada
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    binding.expiresDateValue.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );
        // Opcional: limitar fechas
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // Solo fechas futuras
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Solo fechas pasadas

        datePickerDialog.show();
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
