package p2.consola;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.preguntas.ApartadoDesarrollo;
import p2.dominio.preguntas.OpcionRespuesta;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaDesarrollo;
import p2.dominio.preguntas.PreguntaOpciones;
import p2.dominio.preguntas.PreguntaRellenar;
import p2.dominio.preguntas.PreguntaTeorica;
import p2.dominio.preguntas.PreguntaVerdaderoFalso;
import p2.dominio.preguntas.TipoPregunta;
import p2.persistencia.RepositorioPreguntas;

/**
 * Submenú de gestión del banco de preguntas.
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

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    crearPregunta();
                    break;
                case 2:
                    listarPreguntas();
                    break;
                case 3:
                    eliminarPregunta();
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
        String texto = leerTextoObligatorio("Texto de la pregunta: ");
        String textoAclaratorio = leerTexto("Texto aclaratorio (opcional): ");
        double nota = leerDouble("Nota de la pregunta: ");

        Pregunta pregunta =
            construirPregunta(
                tipoPregunta,
                texto,
                textoAclaratorio,
                nota
            );

        asignatura.agregarPregunta(pregunta);
        repositorioPreguntas.guardar(asignatura);

        System.out.println("Pregunta guardada en " + asignatura.imprimirSimple() + ".");
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

        if (posicion < 1 || posicion > asignatura.getPreguntas().size()) {
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
        repositorioPreguntas.guardar(
            asignatura
        );

        System.out.println("Pregunta eliminada.");
    }

    private void mostrarPreguntasDe(Asignatura asignatura) {
        List<Pregunta> preguntas = asignatura.getPreguntas();

        System.out.println();
        System.out.println("Preguntas de " + asignatura.imprimirSimple());

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

        switch (tipoPregunta) {
            case TEORICA:
                return new PreguntaTeorica(
                    texto,
                    textoAclaratorio,
                    nota,
                    leerTextoObligatorio("Respuesta correcta: ")
                );
            case VERDADERO_FALSO:
                return new PreguntaVerdaderoFalso(
                    texto,
                    textoAclaratorio,
                    nota,
                    leerDouble("Penalizacion por fallo (0 si no penaliza): "),
                    leerBooleano("Respuesta correcta (true/false): ")
                );
            case OPCIONES:
                return crearPreguntaOpciones(
                    texto,
                    textoAclaratorio,
                    nota
                );
            case RELLENAR:
                return crearPreguntaRellenar(
                    texto,
                    textoAclaratorio,
                    nota
                );
            case DESARROLLO:
                return crearPreguntaDesarrollo(
                    texto,
                    textoAclaratorio,
                    nota
                );
            default:
                throw new IllegalArgumentException(
                    "Tipo de pregunta no soportado."
                );
        }
    }

    private Pregunta crearPreguntaOpciones(
            String texto,
            String textoAclaratorio,
            double nota
            ) {

        double penalizacion = leerDouble("Penalizacion por fallo (0 si no penaliza): ");
        PreguntaOpciones pregunta = new PreguntaOpciones(
            texto,
            textoAclaratorio,
            nota,
            penalizacion
        );

        int numeroOpciones = leerEntero("Numero de opciones: ");
        if (numeroOpciones < 2) {
            throw new IllegalArgumentException(
                "Una pregunta de opciones necesita al menos 2 opciones."
            );
        }

        for (int i = 1; i <= numeroOpciones; i++) {
            String textoOpcion =
                leerTextoObligatorio(
                    "Texto de opcion " + i + ": "
                );
            boolean correcta =
                leerBooleano(
                    "Es correcta? (true/false): "
                );
            pregunta.agregarOpcion(
                new OpcionRespuesta(
                    textoOpcion,
                    correcta
                )
            );
        }

        if (!pregunta.validarOpciones()) {
            throw new IllegalArgumentException(
                "La pregunta necesita al menos una opcion correcta."
            );
        }

        return pregunta;
    }

    private Pregunta crearPreguntaRellenar(
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

        return new PreguntaRellenar(
            texto,
            textoAclaratorio,
            nota,
            fraseConHuecos,
            palabrasCorrectas
        );
    }

    private Pregunta crearPreguntaDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota
            ) {

        PreguntaDesarrollo pregunta = new PreguntaDesarrollo(
            texto,
            textoAclaratorio,
            nota
        );

        int numeroApartados =
            leerEntero("Numero de apartados: ");
        if (numeroApartados <= 0) {
            throw new IllegalArgumentException(
                "Debe existir al menos un apartado."
            );
        }

        for (int i = 1; i <= numeroApartados; i++) {
            String textoApartado =
                leerTextoObligatorio(
                    "Texto del apartado " + i + ": "
                );
            double porcentaje =
                leerDouble(
                    "Porcentaje del apartado " + i + " (0-100): "
                );

            pregunta.agregarApartado(
                new ApartadoDesarrollo(
                    textoApartado,
                    porcentaje
                )
            );
        }

        if (!pregunta.validarPorcentajes()) {
            throw new IllegalArgumentException(
                "La suma de porcentajes debe ser 100."
            );
        }

        return pregunta;
    }

    private TipoPregunta seleccionarTipoPregunta() {
        TipoPregunta[] valores = TipoPregunta.values();

        System.out.println();
        System.out.println("Tipos de pregunta:");
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }

        int opcion = leerEntero("Selecciona tipo de pregunta: ");
        if (opcion < 1 || opcion > valores.length) {
            throw new IllegalArgumentException(
                "Tipo de pregunta no valido."
            );
        }

        return valores[opcion - 1];
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
