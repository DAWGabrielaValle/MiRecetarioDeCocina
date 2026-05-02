package com.iesaguadulce.mirecetariodecocina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.iesaguadulce.mirecetariodecocina.room.Diario;
import com.iesaguadulce.mirecetariodecocina.room.DiarioDao;
import com.iesaguadulce.mirecetariodecocina.room.Diario_Menu;
import com.iesaguadulce.mirecetariodecocina.room.Diario_MenuDao;
import com.iesaguadulce.mirecetariodecocina.room.Menu;
import com.iesaguadulce.mirecetariodecocina.room.MenuDao;
import com.iesaguadulce.mirecetariodecocina.room.Plan;
import com.iesaguadulce.mirecetariodecocina.room.PlanDao;
import com.iesaguadulce.mirecetariodecocina.room.RecetarioCocinaDatabase;
import com.iesaguadulce.mirecetariodecocina.room.Rol;
import com.iesaguadulce.mirecetariodecocina.room.RolDao;
import com.iesaguadulce.mirecetariodecocina.room.Usuario;
import com.iesaguadulce.mirecetariodecocina.room.UsuarioDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Pruebas unitarias para el objeto de acceso a datos (DAO) de la entidad {@link Plan}.
 * <p>
 * Estas pruebas se ejecutan en una base de datos en memoria para garantizar que cada test
 * sea independiente y no afecte a los datos reales de la aplicación. Se verifican las
 * operaciones básicas de inserción, consulta, actualización y borrado (CRUD).
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see PlanDao
 */
@RunWith(AndroidJUnit4.class)
public class PlanDaoUnitTest {

    /** Instancia de la base de datos en memoria para pruebas. */
    private RecetarioCocinaDatabase db;

    /** DAO de planes para realizar las operaciones de prueba. */
    private PlanDao planDao;

    /** DAO de diarios para realizar las operaciones de prueba. */
    private DiarioDao diarioDao;

    /** DAO de menus para realizar las operaciones de prueba. */
    private MenuDao menuDao;

    /** DAO de diario_menu para realizar las operaciones de prueba. */
    private Diario_MenuDao diarioMenuDao;

    /** DAO de roles para realizar las operaciones de prueba. */
    private RolDao rolDao;

    /** DAO de usuarios para realizar las operaciones de prueba. */
    private UsuarioDao usuarioDao;

    /**
     * Configuración previa a cada prueba.
     * <p>
     * Crea una base de datos temporal en memoria y se obtienen los DAO necesarios. Se permite
     * la ejecución de consultas en el hilo principal para facilitar el testeo.
     * </p>
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RecetarioCocinaDatabase.class)
                .allowMainThreadQueries()
                .build();
        planDao = db.getPlanDao();
        diarioDao = db.getDiarioDao();
        menuDao = db.getMenuDao();
        diarioMenuDao = db.getDiarioMenuDao();
        rolDao = db.getRolDao();
        usuarioDao = db.getUsuarioDao();
    }

    /**
     * Cierra la base de datos tras la ejecución de cada prueba para liberar recursos.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Verifica que un plan con un menú en un día se inserte correctamente y se pueda recuperar por su nombre.
     * <p>
     * Se comprueba que el plan recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el diario recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el menú recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el menú vinculado al diario también se encuentre en la base de datos.
     */
    @Test
    public void obtenerPlanConMenus() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Plan
        Plan plan = new Plan("Plan de prueba", "Descripción del plan", "Tipo plan",
                "Etiqueta Plan", 1, usuarioResult.getIdUsuario());
        long idPlan = planDao.insert(plan);

        // 3. Insertamos el día en Diario
        long idDiario = diarioDao.insert(new Diario(1, (int) idPlan));

        // 4. Insertamos el menú
        long idMenu = menuDao.insert(new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida", usuarioResult.getIdUsuario()));

        // 5. Vinculamos el menú al día en Diario
        diarioMenuDao.insert(new Diario_Menu((int) idDiario, (int) idMenu));

        // 6. Verificaciones
        Plan resultPlan = planDao.getPlanByIdSync((int) idPlan);
        assertNotNull("El plan debería existir", resultPlan);
        assertEquals("Plan de prueba", resultPlan.getNombre());

        Diario resultDiario = diarioDao.getDiarioByIdSync((int) idDiario);
        assertNotNull("El diario debería existir", resultDiario);
        assertEquals(1, resultDiario.getOrden());

        Menu resultMenu = menuDao.getMenuByIdSync((int) idMenu);
        assertNotNull("El menú debería existir", resultMenu);
        assertEquals("Menu de prueba", resultMenu.getNombre());

        Diario_Menu resultDiarioMenu = diarioMenuDao.getDiarioMenuByIdSync((int) idDiario, (int) idMenu);
        assertNotNull("El menú vinculado al diario debería existir", resultDiarioMenu);
    }

    /**
     * Verifica que los datos de un plan existente se actualicen correctamente.
     * <p>
     * Se inserta un plan con un menú en un día inicial, se modifica su nombre manteniendo el mismo identificador
     * y se comprueba que la base de datos refleja el cambio tras la actualización.
     * Se comprueba que el diario recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el menú recuperado no sea nulo y que sus atributos coincidan
     * con los valores proporcionados durante la inserción.
     * Se comprueba que el menú vinculado al diario también se encuentre en la base de datos.
     * </p>
     */
    @Test
    public void modificarPlan() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Plan
        Plan plan = new Plan("Plan de prueba", "Descripción del plan", "Tipo plan",
                "Etiqueta Plan", 1, usuarioResult.getIdUsuario());
        long idPlan = planDao.insert(plan);

        // 3. Insertamos el día en Diario
        long idDiario = diarioDao.insert(new Diario(1, (int) idPlan));

        // 4. Insertamos el menú
        long idMenu = menuDao.insert(new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida", usuarioResult.getIdUsuario()));

        // 5. Vinculamos el menú al día en Diario
        diarioMenuDao.insert(new Diario_Menu((int) idDiario, (int) idMenu));

        // 6. Modificamos el plan
        Plan planAModificar = planDao.getPlanByIdSync((int) idPlan);
        Plan planModificado = new Plan(
                "Plan de prueba Modificado",
                planAModificar.getDescripcion(),
                planAModificar.getTipo(),
                planAModificar.getEtiqueta(),
                planAModificar.getDias(),
                planAModificar.getIdUsuario()
        );
        planModificado.setIdPlan((int) idPlan);
        planDao.update(planModificado);

        // 7. Verificaciones
        Plan resultPlan = planDao.getPlanByIdSync((int) idPlan);
        assertEquals("Plan de prueba Modificado", resultPlan.getNombre());

        Diario resultDiario = diarioDao.getDiarioByIdSync((int) idDiario);
        assertNotNull("El diario debería existir", resultDiario);
        assertEquals(1, resultDiario.getOrden());

        Menu resultMenu = menuDao.getMenuByIdSync((int) idMenu);
        assertNotNull("El menú debería existir", resultMenu);
        assertEquals("Menu de prueba", resultMenu.getNombre());

        Diario_Menu resultDiarioMenu = diarioMenuDao.getDiarioMenuByIdSync((int) idDiario, (int) idMenu);
        assertNotNull("El menú vinculado al diario debería existir", resultDiarioMenu);
    }

    /**
     * Verifica que un plan con un menú en un día se elimine correctamente de la base de datos.
     * <p>
     * Se inserta un plan con un menú en un día, se procede a su eliminación y se confirma que,
     * al intentar recuperarlo de nuevo, el resultado obtenido es {@code null}.
     * Se comprueba que el diario se haya borrado.
     * Se comprueba que el menú vinculado al diario también se haya borrado.
     * </p>
     */
    @Test
    public void borrarPlan() {
        // 1. Preparamos el entorno (Rol y Usuario) para cumplir con la FK de Receta
        long idRol = rolDao.insert(new Rol("Chef"));
        Usuario usuario = new Usuario("user_chef", (int) idRol, "Nombre Chef", "pass", "2026-01-01", null);
        usuarioDao.insert(usuario);
        Usuario usuarioResult = usuarioDao.getUsuarioSync("user_chef");

        // 2. Insertamos el Plan
        Plan plan = new Plan("Plan de prueba", "Descripción del plan", "Tipo plan",
                "Etiqueta Plan", 1, usuarioResult.getIdUsuario());
        long idPlan = planDao.insert(plan);

        // 3. Insertamos el día en Diario
        long idDiario = diarioDao.insert(new Diario(1, (int) idPlan));

        // 4. Insertamos el menú
        long idMenu = menuDao.insert(new Menu("Menu de prueba", "Descripción del menú",
                "Comida", "Comida", usuarioResult.getIdUsuario()));

        // 5. Vinculamos el menú al día en Diario
        diarioMenuDao.insert(new Diario_Menu((int) idDiario, (int) idMenu));

        // 6. Comprobamos que existe antes de borrar
        assertNotNull(planDao.getPlanByIdSync((int) idPlan));
        assertNotNull(diarioDao.getDiarioByIdSync((int) idDiario));
        assertNotNull(menuDao.getMenuByIdSync((int) idMenu));
        assertNotNull(diarioMenuDao.getDiarioMenuByIdSync((int) idDiario, (int) idMenu));

        // 7. Borramos
        diarioMenuDao.delete(diarioMenuDao.getDiarioMenuByIdSync((int) idDiario, (int) idMenu));
        diarioDao.delete(diarioDao.getDiarioByIdSync((int) idDiario));
        planDao.delete(planDao.getPlanByIdSync((int) idPlan));

        // 8. Verificamos que ya no existe
        Plan resultPlan = planDao.getPlanByIdSync((int) idPlan);
        assertNull("El plan debería haber sido borrado", resultPlan);

        Diario resultDiario = diarioDao.getDiarioByIdSync((int) idDiario);
        assertNull("El diario debería haber sido borrado", resultDiario);

        Diario_Menu resultDiarioMenu = diarioMenuDao.getDiarioMenuByIdSync((int) idDiario, (int) idMenu);
        assertNull("El menú vinculado al diario debería haber sido borrado", resultDiarioMenu);

    }
}
