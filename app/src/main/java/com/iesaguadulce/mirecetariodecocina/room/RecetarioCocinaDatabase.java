package com.iesaguadulce.mirecetariodecocina.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase que representa la base de datos local de la aplicación utilizando la librería Room.
 * <p>
 * Esta clase actúa como el punto de acceso principal a la persistencia de datos en SQLite.
 * Implementa el patrón Singleton para garantizar una única instancia de la base de datos
 * en toda la aplicación, evitando conflictos de acceso y optimizando el consumo de recursos.
 * </p>
 * <p>
 * La base de datos incluye entidades para la gestión integral de:
 * <ul>
 *     <li>Usuarios, Roles y autenticación.</li>
 *     <li>Ingredientes, Recetas y sus relaciones.</li>
 *     <li>Menús y Planificación diaria de menús.</li>
 *     <li>Comentarios de usuarios sobre recetas.</li>
 * </ul>
 * </p>
 *
 * @author Gabriela Valle Puente
 * @version 1.2
 * @since 1.0
 * @see RoomDatabase
 */
@Database(entities = {Rol.class, Usuario.class, Ingrediente.class, Receta.class, Receta_Ing.class, Receta_Usu.class, Comentario.class, Menu.class, Menu_Rec.class, Plan.class, Diario.class, Diario_Menu.class}, version = 1, exportSchema = false)
public abstract class RecetarioCocinaDatabase extends RoomDatabase {

    /**
     * Instancia única de la base de datos.
     */
    private static volatile RecetarioCocinaDatabase INSTANCIA;

    /**
     * Número de hilos a utilizar para operaciones de escritura en la base de datos.
     */
    private static final int NUM_HILOS = 4;

    /**
     * Executor dedicado a realizar operaciones de escritura en la base de datos
     * de forma asíncrona fuera del hilo principal.
     */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_HILOS);

    /**
     * Obtiene la instancia única de la base de datos.
     * <p>
     * Utiliza un bloqueo sincronizado para asegurar que solo se cree una instancia
     * (Thread-safe) y configura la base de datos para pre-poblar datos iniciales
     * mediante un callback en la creación.
     * </p>
     *
     * @param context El contexto de la aplicación.
     * @return La instancia única de {@link RecetarioCocinaDatabase}.
     */
    public static RecetarioCocinaDatabase getDatabase(Context context) {
        if (INSTANCIA == null) {
            synchronized (RecetarioCocinaDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RecetarioCocinaDatabase.class,
                            "recetario_cocina.db")
                    .addCallback(roomCallback) // Para pre-poblar la base de datos
                    .build();
                }
            }
        }
        return INSTANCIA;
    }

    /** @return Objeto de acceso a datos para la entidad {@link Rol}. */
    public abstract RolDao getRolDao();

    /** @return Objeto de acceso a datos para la entidad {@link Usuario}. */
    public abstract UsuarioDao getUsuarioDao();

    /** @return Objeto de acceso a datos para la entidad {@link Ingrediente}. */
    public abstract IngredienteDao getIngredienteDao();

    /** @return Objeto de acceso a datos para la entidad {@link Receta}. */
    public abstract RecetaDao getRecetaDao();

    /** @return Objeto de acceso a datos para la entidad {@link Receta_Ing}. */
    public abstract Receta_IngDao getRecetaIngDao();

    /** @return Objeto de acceso a datos para la entidad {@link Receta_Usu}. */
    public abstract Receta_UsuDao getRecetaUsuDao();

    /** @return Objeto de acceso a datos para la entidad {@link Comentario}. */
    public abstract ComentarioDao getComentarioDao();

    /** @return Objeto de acceso a datos para la entidad {@link Menu}. */
    public abstract MenuDao getMenuDao();

    /** @return Objeto de acceso a datos para la entidad {@link Menu_Rec}. */
    public abstract Menu_RecDao getMenuRecDao();

    /** @return Objeto de acceso a datos para la entidad {@link Plan}. */
    public abstract PlanDao getPlanDao();

    /** @return Objeto de acceso a datos para la entidad {@link Diario}. */
    public abstract DiarioDao getDiarioDao();

    /** @return Objeto de acceso a datos para la entidad {@link Diario_Menu}. */
    public abstract Diario_MenuDao getDiarioMenuDao();

    /**
     * Callback que se ejecuta al crear la base de datos por primera vez.
     * <p>
     * Inserta los roles por defecto (Administrador, Chef), usuarios iniciales
     * y el catálogo base de ingredientes y recetas.
     * </p>
     */
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Obtenemos la instancia de nuevo para estar seguros
                RecetarioCocinaDatabase database = INSTANCIA;
                if (database != null) {
                    RolDao rolDao = database.getRolDao();
                    rolDao.insert(new Rol("Administrador"));

                    Rol usuRol = rolDao.getLastInsertedRol();

                    UsuarioDao usuarioDao = database.getUsuarioDao();
                    usuarioDao.insert(new Usuario("admin", usuRol.getIdRol(), "Gabriela Valle", "9ca890a1bebd001bbfd410098728d3529e810a41cdb2ab3c4d9c12e5deb62463b3767813ab131cc659c4b54a53e2e447b0be1e13daef89046b9f2ac07df6d0ed", "21/03/2026", null));

                    rolDao.insert(new Rol("Chef"));
                    usuRol = rolDao.getLastInsertedRol();

                    usuarioDao.insert(new Usuario("chef", usuRol.getIdRol(), "Gabriela Valle", "8256a9b1079731081753bf18aff5967d30b8f7ba13e619cfa88a2b49a25358355fd2ab3d40c665501156161215b1f043743394e0ffd4c9c0a495e34484518872", "21/03/2026", null));

                    IngredienteDao ingredienteDao = database.getIngredienteDao();
                    ingredienteDao.insert(new Ingrediente("Patata", "La patata es un tubérculo comestible, originario de Sudamérica, muy cultivado globalmente como alimento básico, rico en hidratos de carbono (energía) y potasio", "patata", "Tubérculo", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Zanahoria", "La zanahoria es una verdura, específicamente una hortaliza de raíz y no una fruta", "zanahoria", "Hortaliza", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Tomate", "Los tomates son nutritivos y muy poco calóricos. Contienen grandes cantidades de vitamina C y ácido fólico", "tomate","Fruta", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Lechuga", "La lechuga es una hortaliza formada por grandes hojas que se disponen unas sobre otras formando, en algunos casos, un repollo", "lechuga","Ensalada", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Cebolla", "La cebolla es un superalimento que fortalece el sistema inmune, protege el corazón y regula el azúcar en sangre, mejora la digestión y la salud intestinal, ayuda a huesos fuertes y alivia problemas respiratorios", "cebolla", "Hortaliza", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Ajo", "El ajo es una hortaliza, valorada por su fuerte sabor como condimento y sus múltiples propiedades medicinales", "ajo","Hortaliza", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Pimiento verde", "El pimiento verde es un superalimento bajo en calorías, rico en Vitamina C, antioxidantes, fibra, vitaminas A, E y minerales", "pimiento_verde", "Hortaliza" ,"Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Perejil", "El perejil es una planta aromática mediterránea fundamental en la cocina global", "perejil", "Aromatizante", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Guisantes cocidos", "Los guisantes son legumbres, no verduras, aunque a menudo se confundan por su color verde", "guisantes_cocidos", "Conserva", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Pimiento morrón asado", "El morrón es una variedad de pimiento grande, carnoso y dulce, sin picante, que se consume como verdura", "pimiento_asado", "Conserva", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Aceituna partida", "La aceituna es una fruta desde el punto de vista botánico, ya que es el fruto del olivo que se desarrolla a partir de la flor", "aceituna_partida", "Aliñada", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Bonito en aceite de oliva", "Pez azul delicioso, similar al atún pero más pequeño, con carne blanca y suave", "bonito_aove", "Conserva", "Pescado"));
                    ingredienteDao.insert(new Ingrediente("Lenteja", "Las lentejas son ricas en fibra, en vitaminas del grupo B, entre las cuales destaca el ácido fólico, y en minerales como el potasio, el calcio, el fósforo, magnesio, zinc y el hierro", "lenteja", "Legumbre", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Laurel","El laurel contiene pequeñas cantidades de vitaminas y minerales, como vitamina A, vitamina C, calcio, hierro y magnesio","laurel", "Hojas","Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Pimentón", "El pimentón es un condimento en polvo, de color rojo y sabor característico, que se obtiene al secar y moler pimientos rojos", "pimenton", "Condimento", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Guindilla", "La guindilla es un pimiento picante, valorado en la cocina por su sabor ardiente", "guindilla", "Condimento", "Picante"));
                    ingredienteDao.insert(new Ingrediente("Redondo de ternera", "El redondo de ternera es un corte de carne cilíndrico de la parte trasera de la pata de la ternera, situado entre la tapa y la contra", "redondo_ternera", "Ternera", "Carne"));
                    ingredienteDao.insert(new Ingrediente("Bacon", "El bacon (o beicon) es panceta de cerdo curada y ahumada, obtenida de la zona del vientre", "bacon", "Cerdo", "Carne"));
                    ingredienteDao.insert(new Ingrediente("Dorada", "La dorada es un pescado blanco muy popular en España, su carne es jugosa y tiene un sabor delicado", "dorada", "Pescado blanco", "Pescado"));
                    ingredienteDao.insert(new Ingrediente("Huevo", "El huevo es un alimento proteico, al igual que la carne y el pescado", "huevo", "Huevo", "Ave"));
                    ingredienteDao.insert(new Ingrediente("Mahonesa", "Salsa emulsionada de huevo y aceite, cuyo nombre proviene de Mahón, la capital de Menorca, España", "mahonesa", "Mahonesa", "Salsa"));
                    ingredienteDao.insert(new Ingrediente("Tabasco", "La salsa Tabasco es una salsa picante utilizada como condimento. Se prepara con chile tabasco rojo, vinagre, agua y sal macerados en barriles de roble", "tabasco","Picante", "Salsa"));
                    ingredienteDao.insert(new Ingrediente("Vino blanco", "Para cocinar, es recomendable utilizar un vino blanco de buena calidad, pero no es necesario que sea un vino de alta gama. Buscar vinos secos y de acidez moderada", "vino_blanco", "Blanco", "Vino"));
                    ingredienteDao.insert(new Ingrediente("Agua", "Es el principal componente del cuerpo humano y el consumo de ella brinda grandes beneficios", "agua", "Agua", "Agua"));
                    ingredienteDao.insert(new Ingrediente("Leche evaporada", "La leche evaporada es leche de vaca a la que se le ha eliminado aproximadamente el 60% de su agua, resultando en un producto más espeso, cremoso y concentrado", "leche_evaporada", "Leche", "Vaca"));
                    ingredienteDao.insert(new Ingrediente("Vainilla", "La vainilla es una especia aromática extraída de las vainas de ciertas orquídeas", "vainilla", "Extracto", "Aromatizante"));
                    ingredienteDao.insert(new Ingrediente("AOVE", "AOVE es el acrónimo de Aceite de Oliva Virgen Extra, la categoría de aceite de oliva de la más alta calidad, extraído directamente de aceitunas frescas mediante procesos mecánicos", "aove", "Aceite", "Vegetal"));
                    ingredienteDao.insert(new Ingrediente("Sal", "La sal es esencial para la vida, se usa como condimento y conservante, y regula fluidos y funciones nerviosas en el cuerpo", "sal", "Condimento", "Conservante"));
                    ingredienteDao.insert(new Ingrediente("Sal gruesa", "La sal gruesa se refiere a que los granos de esta sal se dejan con una textura más irregular y de mayor tamaño", "sal_gruesa", "Condimento", "Conservante"));
                    ingredienteDao.insert(new Ingrediente("Azúcar", "El azúcar es un carbohidrato dulce, principalmente sacarosa, que se obtiene de la caña de azúcar o remolacha", "azucar", "Blanco", "Endulzante"));

                    RecetaDao recetaDao = database.getRecetaDao();
                    recetaDao.insert(new Receta("Patatas Bravas", "Aunque es una tapa típica de todas las regiones de España, los expertos gastronómicos coinciden en que se inventaron en Madrid. Concretamente en dos establecimientos ya desaparecidos que, hoy en día, se disputan la invención: Casa Pellico y La Casona.", "patatas_bravas", 30, "Vegetales", "Tapa", "Lavar las patatas y ponerlas a cocer con piel, en abundante agua con sal. Una vez cocidas, escurrirlas, pelarlas y trocearlas en cuadraditos, de similar tamaño. En una sartén, con aceite muy caliente, freír las patatas cocidas hasta que empiecen a tomar un color dorado, momento en que se sacarán a una fuente. Mientras tanto freír los tomates, troceados, en una sartén pequeña con dos cucharadas de aceite. Pasar esta salsa de tomate por el pasapurés y mezclarla con una cucharadita de tabasco (la cantidad de tabasco dependerá de lo picantes que se deseen tomar). Cubrir las patatas con esta salsa de tomate. Se pueden servir calientes o frías.", "chef"));

                    Receta receta = recetaDao.getLastInsertedReceta();
                    Ingrediente ingrediente = ingredienteDao.getIngredienteByNombre("Patata");

                    Receta_IngDao recetaIngDao = database.getRecetaIngDao();
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 500, "Gramos"));

                    ingrediente = ingredienteDao.getIngredienteByNombre("Tomate");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 3, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Tabasco");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Cucharadita"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("AOVE");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Cucharada"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Sal");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pizca"));

                    ComentarioDao comentarioDao = database.getComentarioDao();

                    comentarioDao.insert(new Comentario("Receta con pocos ingredientes y fácil de seguir", "2025/12/14", "chef", receta.getIdReceta()));
                    comentarioDao.insert(new Comentario("Si quieres que las patatas se cuezan más rápidamente, pincharlas con una aguja gordita antes de meterlas en el agua, de esta forma tardarán la mitad de tiempo", "2025/12/14", "chef", receta.getIdReceta()));

                    recetaDao.insert(new Receta("Lentejas guisadas", "Es un plato ancestral que ha viajado por el mundo, transformándose con ingredientes locales, pero siempre manteniendo su esencia de comida nutritiva y reconfortante.", "lentejas_guisadas", 120, "Mediterráneo", "Guiso", "Después de pasar dos horas a remojo, las lentejas se escurren y se echan en una cazuela con una cebolla, el ajo, el pimiento y el tomate, todo ello picado; se añade la hoja de laurel, el pimentón y casi todo el aceite de oliva, se cubre de agua, se tapa y se pone a hervir lentamente por espacio de una o más horas, hasta que las lentejas estén tiernas (dependiendo de la calidad de las lentejas) añadirle la sal un poco antes de apartarlas del fuego. En una sarten se pone un poco de aceite de oliva y la otra cebolla picada, freís a fuego suave hasta que la cebolla se dore muy ligeramente, a continuación a añadir la panceta troceada y rehogar dos o tres minutos más. Por último incorporar este sofrito a las lentejas, rectificar de sal, poner un chorreoncito de aceite de oliva crudo y cocer tapado otros cinco minutos.", "chef"));

                    receta = recetaDao.getLastInsertedReceta();

                    ingrediente = ingredienteDao.getIngredienteByNombre("Lenteja");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 500, "Gramos"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Cebolla");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Ajo");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Dientes"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Laurel");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Hoja"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Pimiento verde");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pizca"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Tomate");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pieza"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Bacon");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Loncha"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Pimentón");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Cucharadita"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("AOVE");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Vasito"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Sal");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Cucharadita"));

                    recetaDao.insert(new Receta("Ternera asada", "La ternera asada en su jugo emula las añejas elaboraciones de las provincias de Segovia o Ávila.", "ternera_asada", 120, "Carne roja", "Carne en su jugo", "Adobar la pieza de carne con dos dientes de ajo, machacados en el mortero, dejándola reposar durante una hora. Pasado este tiempo sazonar con sal. En una tartera, calentar abundate aceite, cuando comience a echar humo, rehogar la carne hasta que quede dorada por todos lados. Añadir las cebollas, cortadas en trozos grandes, y dos dientes de ajo partidos por la mitad. Dejar freír durante cuatro minutos y regar con un vaso de vino blanco. Cocer lentamente durante una hora y media. La carne estará a punto cuando sea fácil el traspasarla con una aguja. Sacar la carne y dejar enfriar. Separar la salsa, pasala por el pasapurés y reservar. La pieza de carne puede mantenerse varios días en el frigorífico. Se puede servir caliente, cortada en lonchas finas y calentadas con su propia salsa, hasta que ésta dé el primer hervor. Acompañar con pimientos rojos y puré de patatas. También se puede servir fría, cortada en lonchas finas, sin salsa, acompañada de mayonesa, u otra salsa al gusto", "chef"));

                    receta = recetaDao.getLastInsertedReceta();

                    ingrediente = ingredienteDao.getIngredienteByNombre("Redondo de ternera");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Kilogramo"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Cebolla");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Ajo");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 4, "Dientes"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Vino blanco");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Vaso"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("AOVE");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Vasito"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Sal");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Cucharadita"));

                    recetaDao.insert(new Receta("Flan", "Dulce que se hace con yemas de huevo, leche y azúcar, y se cuaja en el baño de María, dentro de un molde generalmente bañado de azúcar tostada", "flan", 60, "Dulce", "Postre", "En un molde para flan vacíe la mita de la taza de azúcar, el resto reservar; colóquelo sobre el fuego y deje que se derrita la azúcar. Ladee el molde para que se cubra bien el fondo y las paredes. Aparte licúe los huevos, el resto de la azúcar, el agua, la leche evaporada y la vainilla. Vacíe sobre el caramelo. Tape el molde con un trozo de papel aluminio y hornee a baño maría durante 35 ó 40 minutos. Refrigere o desmolde. NOTA: Si desea cocínelo en olla express a baño maría durante veinte minutos", "Chef"));

                    receta = recetaDao.getLastInsertedReceta();

                    ingrediente = ingredienteDao.getIngredienteByNombre("Azúcar");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Taza"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Huevo");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 4, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Agua");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Taza"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Leche evaporada");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 420, "Mililitros"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Vainilla");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Cucharaditas"));

                    recetaDao.insert(new Receta("Dorada al horno", "La dorada es muy saludable si se incluye en nuestra dieta diaria por su alto valor nutricional", "dorada_horno", 40, "Pescado blanco", "Pescado", "Limpia las doradas y saca los lomos. No retires la piel. Cubre la placa del horno con papel aluminio. Vierte un poquito de aceite y coloca encima los cuatro lomos de dorada dejando la piel hacia arriba. Sobre cada trozo pon un puñadito de sal gruesa y extiéndela. Introduce en el horno a 220ºC durante diez minutos. Cuando estén hechos retírales la piel con cuidado y colócalos en una fuente amplia. Limpia los tomates y córtalos en rodajas gruesas y fríelas en una sarten con un poco de aceite. Colócalas en la misma fuente que el pescado. Pon unas ocho cucharadas de aceite en otra sartén, caliente y añade los dientes de ajo cortados en láminas. Cuando se doren un poco añade unas tiras de guindilla. Vierte el aceite sobre el pescado . Espolvorea con perejil picado.", "Chef"));

                    receta = recetaDao.getLastInsertedReceta();

                    ingrediente = ingredienteDao.getIngredienteByNombre("Dorada");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Tomate");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Ajo");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 5, "Dientes"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Guindilla");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pieza"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("AOVE");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 12, "Cucharadas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Sal gruesa");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 4, "Puñaditos"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Perejil");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 4, "Ramitas"));

                    recetaDao.insert(new Receta("Ensaladilla rusa", "La ensaladilla rusa es un plato con origen en Rusia, creado por el chef franco-belga Lucien Olivier", "ensaladilla_rusa", 60, "Ensalada", "Vegetal", "Pon a remojo la lechuga y después pasa cada hoja bajo el grifo. Pon a cocer las patatas con piel, los huevos enteros y las zanahorias peladas en una cazuela con abundante agua. Deja que se enfríen, pela las patatas y los huevos, reservando un huevo para decorar. Trocea todo en cuadraditos pequeños y colócalos en un bol grande. Sazona, Agrega el bonito desmigado y los guisantes cocidos. Mezcla todos los ingredientes, añade la mahonesa y sirve en una fuente amplia. Decórala a tu gusto con unas tiras de pimiento morrón, aceitunas, el huevo y las hojas de lechuga cortada en juliana fina", "chef"));

                    receta = recetaDao.getLastInsertedReceta();

                    ingrediente = ingredienteDao.getIngredienteByNombre("Patata");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 4, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Zanahoria");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 2, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Huevo");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 3, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Guisantes cocidos");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 150, "Gramos"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Bonito en aceite de oliva");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 200, "Gramos"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Mahonesa");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 500, "Mililitros"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Sal");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Cucharada"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Lechuga");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pieza"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Aceituna partida");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 12, "Piezas"));
                    ingrediente = ingredienteDao.getIngredienteByNombre("Pimiento morrón asado");
                    recetaIngDao.insert(new Receta_Ing(receta.getIdReceta(), ingrediente.getIdIngrediente(), 1, "Pieza"));

                    comentarioDao.insert(new Comentario("Si quieres que las patatas se cuezan más rápidamente, pincharlas con una aguja gordita antes de meterlas en el agua, de esta forma tardarán la mitad de tiempo", "2025/12/14", "chef", receta.getIdReceta()));

                    PlanDao planDao = database.getPlanDao();

                    planDao.insert(new Plan("Comida fin de semana", "Selección de recetas que se ajustan al tiempo disponible", "Comida", "Carne", 2, "chef"));

                    Plan plan = planDao.getLastInsertedPlan();

                    DiarioDao diarioDao = database.getDiarioDao();

                    diarioDao.insert(new Diario(1, plan.getIdPlan()));

                    Diario diario = diarioDao.getLastInsertedDiario();

                    MenuDao menuDao = database.getMenuDao();

                    menuDao.insert(new Menu("Especialidad de carne", "Denominamos carne roja a aquellos alimentos que contienen carne de ternera, de cerdo, de toro, de buey, de pato y ganso, de cabra o de cordero, entre otros", "Comida", "Carne", "chef"));

                    Menu menu = menuDao.getLastInsertedMenu();

                    Menu_RecDao menuRecDao = database.getMenuRecDao();

                    receta = recetaDao.getRecetaByNombre("Patatas Bravas");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));
                    receta = recetaDao.getRecetaByNombre("Ternera asada");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));
                    receta = recetaDao.getRecetaByNombre("Flan");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));

                    Diario_MenuDao diarioMenuDao = database.getDiarioMenuDao();

                    diarioMenuDao.insert(new Diario_Menu(diario.getIdDiario(), menu.getIdMenu()));

                    diarioDao.insert(new Diario(2, plan.getIdPlan()));

                    diario = diarioDao.getLastInsertedDiario();

                    menuDao.insert(new Menu("Especialidad de pescado", "Una comida de pescado equilibrada debe tener una salsa suntuosa y una guarnición deliciosa que la complemente a la perfección", "Comida", "Pescado", "chef"));

                    menu = menuDao.getLastInsertedMenu();

                    receta = recetaDao.getRecetaByNombre("Ensaladilla rusa");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));
                    receta = recetaDao.getRecetaByNombre("Dorada al horno");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));
                    receta = recetaDao.getRecetaByNombre("Flan");
                    menuRecDao.insert(new Menu_Rec(menu.getIdMenu(), receta.getIdReceta()));

                    diarioMenuDao.insert(new Diario_Menu(diario.getIdDiario(), menu.getIdMenu()));

                    Log.d("DB", "Datos iniciales insertados con éxito");
                }
            });
        }
    };
}
