package p2.consola;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.preguntas.ApartadoDesarrollo;
import p2.dominio.preguntas.OpcionRespuesta;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaFactory;
import p2.dominio.preguntas.TipoPregunta;
import p2.persistencia.RepositorioPreguntas;

/**
 * Submenu del banco de preguntas: alta, listado y baja.
 *
 * <p>La creacion delega en {@link PreguntaFactory}; esta vista solo recoge datos,
 * invoca la factoria y persiste tras cada alta o baja para no perder trabajo si el
 * proceso termina antes de salir de la aplicacion.</p>
 */
public class VistaPreguntas extends ConsolaBase {

    private final VistaAsignaturas vistaAsignaturas;
    private final RepositorioPreguntas repositorioPreguntas;

    public VistaPreguntas(
            Scanner scanner,
            VistaAsignaturas vistaAsignaturas,
            RepositorioPreguntas repositorioPreguntas
    ) {

        super(scanner);

        this.vistaAsignaturas = vistaAsignaturas;
        this.repositorioPreguntas = repositorioPreguntas;
    }

    /**
     * Bucle del submenu Preguntas hasta que el usuario vuelve al menu principal.
     */
    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    ejecutarConManejoErrores(
                        this::crearPregunta
                    );
                    break;
                case 2:
                    ejecutarConManejoErrores(
                        this::listarPreguntas
                    );
                    break;
                case 3:
                    ejecutarConManejoErrores(
                        this::eliminarPregunta
                    );
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== Preguntas =====");
        System.out.println("1. Crear pregunta");
        System.out.println("2. Listar preguntas");
        System.out.println("3. Eliminar pregunta");
        System.out.println("4. Volver");
    }

    private void crearPregunta() {
        Asignatura asignatura =
            vistaAsignaturas.seleccionarAsignatura(
                true
            );

        TipoPregunta tipoPregunta = seleccionarTipoPregunta();
        String texto =
            leerTextoObligatorio(
                "Texto de la pregunta: "
            );
        String textoAclaratorio =
            leerTexto(
                "Texto aclaratorio (opcional): "
            );
        double nota =
            leerDouble(
                "Nota de la pregunta: "
            );

        Pregunta pregunta =
            construirPregunta(
                tipoPregunta,
                texto,
                textoAclaratorio,
                nota
            );

        asignatura.agregarPregunta(pregunta);

        //-- Guardado inmediato por si el proceso termina antes de Salir.
        repositorioPreguntas.guardar(asignatura);

        System.out.println(
            "Pregunta guardada en "
                + asignatura.imprimirSimple()
                + "."
        );
    }

    private void listarPreguntas() {
        Asignatura asignatura =
            vistaAsignaturas.seleccionarAsignatura(
                false
            );

        mostrarPreguntasDe(
            asignatura
        );
    }

    private void eliminarPregunta() {
        Asignatura asignatura =
            vistaAsignaturas.seleccionarAsignatura(
                false
            );

        if (asignatura.getPreguntas().isEmpty()) {
            System.out.println("La asignatura no tiene preguntas.");
            return;
        }

        mostrarPreguntasDe(
            asignatura
        );

        int posicion =
            leerEntero(
                "Pregunta a eliminar (0 para volver): "
            );

        if (posicion == 0) {
            return;
        }

        if (posicion < 1
                || posicion > asignatura.getPreguntas().size()) {
            throw new IllegalArgumentException(
                "Seleccion de pregunta no valida."
            );
        }

        Pregunta pregunta =
            asignatura.getPreguntas()
                .get(posicion - 1);

        boolean confirmar =
            leerBooleano(
                "Eliminar "
                    + describirPregunta(pregunta)
                    + "? (true/false): "
            );

        if (!confirmar) {
            System.out.println("Eliminacion cancelada.");
            return;
        }

        asignatura.eliminarPreguntaEn(
            posicion
        );

        //-- Mismo criterio que en crearPregunta: persistir tras cada cambio.
        repositorioPreguntas.guardar(
            asignatura
        );

        System.out.println("Pregunta eliminada.");
    }

    private void mostrarPreguntasDe(Asignatura asignatura) {
        List<Pregunta> preguntas = asignatura.getPreguntas();

        System.out.println();
        System.out.println(
            "Preguntas de "
                + asignatura.imprimirSimple()
        );

        if (preguntas.isEmpty()) {
            System.out.println("No hay preguntas.");
            return;
        }

        for (int i = 0; i < preguntas.size(); i++) {
            System.out.println(
                (i + 1)
                    + ". "
                    + describirPregunta(
                        preguntas.get(i)
                    )
            );
        }
    }

    //-- Resumen breve para listados; el examen usa imprimir() del dominio.
    private String describirPregunta(Pregunta pregunta) {
        return pregunta.getTipoPregunta()
            + " | "
            + String.format(
                "%.2f",
                pregunta.getNota()
            )
            + " puntos | "
            + pregunta.getTexto();
    }

    private Pregunta construirPregunta(
            TipoPregunta tipoPregunta,
            String texto,
            String textoAclaratorio,
            double nota
    ) {

        return switch (tipoPregunta) {
            case TEORICA ->
                PreguntaFactory.crearTeorica(
                    texto,
                    textoAclaratorio,
                    nota,
                    leerTextoObligatorio(
                        "Respuesta correcta: "
                    )
                );
            case VERDADERO_FALSO ->
                PreguntaFactory.crearVerdaderoFalso(
                    texto,
                    textoAclaratorio,
                    nota,
                    leerDouble(
                        "Penalizacion por fallo (0 si no penaliza): "
                    ),
                    leerBooleano(
                        "Respuesta correcta (true/false): "
                    )
                );
            case OPCIONES ->
                leerPreguntaOpciones(
                    texto,
                    textoAclaratorio,
                    nota
                );
            case RELLENAR ->
                leerPreguntaRellenar(
                    texto,
                    textoAclaratorio,
                    nota
                );
            case DESARROLLO ->
                leerPreguntaDesarrollo(
                    texto,
                    textoAclaratorio,
                    nota
                );
        };
    }

    private Pregunta leerPreguntaOpciones(
            String texto,
            String textoAclaratorio,
            double nota
    ) {

        double penalizacion =
            leerDouble(
                "Penalizacion por fallo (0 si no penaliza): "
            );
        int numeroOpciones =
            leerEntero(
                "Numero de opciones: "
            );

        if (numeroOpciones < 2) {
            throw new IllegalArgumentException(
                "Una pregunta de opciones necesita al menos 2 opciones."
            );
        }

        List<OpcionRespuesta> opciones = new ArrayList<>();

        for (int i = 1; i <= numeroOpciones; i++) {
            String textoOpcion =
                leerTextoObligatorio(
                    "Texto de opcion " + i + ": "
                );
            boolean correcta =
                leerBooleano(
                    "Es correcta? (true/false): "
                );
            opciones.add(
                new OpcionRespuesta(
                    textoOpcion,
                    correcta
                )
            );
        }

        return PreguntaFactory.crearOpciones(
            texto,
            textoAclaratorio,
            nota,
            penalizacion,
            opciones
        );
    }

    private Pregunta leerPreguntaRellenar(
            String texto,
            String textoAclaratorio,
            double nota
    ) {

        String fraseConHuecos =
            leerTextoObligatorio(
                "Frase con huecos usando '?': "
            );
        int numeroPalabras = contarHuecos(fraseConHuecos);

        if (numeroPalabras <= 0) {
            throw new IllegalArgumentException(
                "La frase de rellenar debe tener al menos un '?'."
            );
        }

        List<String> palabrasCorrectas = new ArrayList<>();

        for (int i = 1; i <= numeroPalabras; i++) {
            palabrasCorrectas.add(
                leerTextoObligatorio(
                    "Palabra correcta " + i + ": "
                )
            );
        }

        return PreguntaFactory.crearRellenar(
            texto,
            textoAclaratorio,
            nota,
            fraseConHuecos,
            palabrasCorrectas
        );
    }

    private Pregunta leerPreguntaDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota
    ) {

        int numeroApartados =
            leerEntero(
                "Numero de apartados: "
            );

        if (numeroApartados <= 0) {
            throw new IllegalArgumentException(
                "Debe existir al menos un apartado."
            );
        }

        List<ApartadoDesarrollo> apartados = new ArrayList<>();

        for (int i = 1; i <= numeroApartados; i++) {
            String textoApartado =
                leerTextoObligatorio(
                    "Texto del apartado " + i + ": "
                );
            double porcentaje =
                leerDouble(
                    "Porcentaje del apartado " + i + " (0-100): "
                );

            apartados.add(
                new ApartadoDesarrollo(
                    textoApartado,
                    porcentaje
                )
            );
        }

        return PreguntaFactory.crearDesarrollo(
            texto,
            textoAclaratorio,
            nota,
            apartados
        );
    }

    private TipoPregunta seleccionarTipoPregunta() {
        return seleccionarEnumerado(
            "Tipos de pregunta:",
            TipoPregunta.values(),
            "Selecciona tipo de pregunta: "
        );
    }

    private int contarHuecos(String texto) {
        int contador = 0;

        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '?') {
                contador++;
            }
        }

        return contador;
    }
}
