package p2.dificultad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaOpciones;
import p2.dominio.preguntas.PreguntaRellenar;
import p2.dominio.preguntas.PreguntaVerdaderoFalso;
import p2.dominio.preguntas.TipoPregunta;

/**
 * Flujo interactivo simple para calibrar dificultad de preguntas.
 */
public class TestTester {

    private final SistemaDificultad sistemaDificultad;
    private final Random random;

    public TestTester(SistemaDificultad sistemaDificultad) {
        this(
            sistemaDificultad,
            new Random()
        );
    }

    public TestTester(
            SistemaDificultad sistemaDificultad,
            Random random
            ) {

        if (sistemaDificultad == null) {
            throw new IllegalArgumentException(
                "El sistema de dificultad es obligatorio."
            );
        }
        if (random == null) {
            throw new IllegalArgumentException(
                "El generador aleatorio es obligatorio."
            );
        }

        this.sistemaDificultad = sistemaDificultad;
        this.random = random;
    }

    public void ejecutarSesion(
            Asignatura asignatura,
            int numeroPreguntas,
            Scanner scanner
            ) {

        if (asignatura == null) {
            throw new IllegalArgumentException(
                "La asignatura es obligatoria."
            );
        }
        if (scanner == null) {
            throw new IllegalArgumentException(
                "El scanner es obligatorio."
            );
        }
        if (numeroPreguntas <= 0) {
            throw new IllegalArgumentException(
                "El numero de preguntas debe ser mayor que 0."
            );
        }
        if (asignatura.getPreguntas().size() < numeroPreguntas) {
            throw new IllegalArgumentException(
                "No hay preguntas suficientes para ejecutar la sesion."
            );
        }

        System.out.print("Nombre del sujeto tester: ");
        String nombre = scanner.nextLine();
        SujetoTester sujetoTester = new SujetoTester(nombre);

        List<Pregunta> seleccionadas =
            seleccionarPreguntas(
                asignatura.getPreguntas(),
                numeroPreguntas
            );

        for (Pregunta pregunta : seleccionadas) {
            System.out.println();
            System.out.println("----- Pregunta -----");
            mostrarPregunta(pregunta);

            ResultadoPrueba resultado =
                recogerResultado(
                    pregunta,
                    sujetoTester,
                    scanner
                );

            sistemaDificultad.registrarResultado(resultado);
            System.out.println(
                resultado.isAcertada()
                    ? "Respuesta correcta."
                    : "Respuesta incorrecta."
            );
            System.out.println(
                "Dificultad actualizada: "
                    + String.format("%.3f", pregunta.getDificultad())
            );
        }

        System.out.println();
        System.out.println(
            "Sesion completada. Maestria final del tester: "
                + String.format("%.3f", sujetoTester.getMaestria())
        );
    }

    private void mostrarPregunta(Pregunta pregunta) {
        if (pregunta instanceof PreguntaOpciones preguntaOpciones) {
            System.out.println(preguntaOpciones.imprimirEnunciado());
            System.out.print(preguntaOpciones.imprimirOpcionesNumeradas());
            return;
        }

        System.out.println(pregunta.imprimirSimple());
    }

    private List<Pregunta> seleccionarPreguntas(
            List<Pregunta> preguntas,
            int numeroPreguntas
            ) {

        List<Pregunta> copia = new ArrayList<>(preguntas);
        Collections.shuffle(copia, random);
        return copia.subList(0, numeroPreguntas);
    }

    private ResultadoPrueba recogerResultado(
            Pregunta pregunta,
            SujetoTester sujetoTester,
            Scanner scanner
            ) {

        TipoPregunta tipo = pregunta.getTipoPregunta();

        if (tipo == TipoPregunta.VERDADERO_FALSO
            || tipo == TipoPregunta.OPCIONES
            || tipo == TipoPregunta.RELLENAR) {

            boolean acierta = evaluarRespuestaObjetiva(
                pregunta,
                scanner
            );

            return new ResultadoPrueba(
                pregunta,
                sujetoTester,
                acierta,
                true,
                0
            );
        }

        int valoracion = leerEnteroEnRango(
            scanner,
            "Valoracion subjetiva de dificultad (1-10): ",
            1,
            10
        );

        return new ResultadoPrueba(
            pregunta,
            sujetoTester,
            false,
            false,
            valoracion
        );
    }

    private boolean evaluarRespuestaObjetiva(
            Pregunta pregunta,
            Scanner scanner
            ) {

        return switch (pregunta.getTipoPregunta()) {
            case VERDADERO_FALSO ->
                evaluarVerdaderoFalso(
                    (PreguntaVerdaderoFalso) pregunta,
                    scanner
                );
            case OPCIONES ->
                evaluarOpciones(
                    (PreguntaOpciones) pregunta,
                    scanner
                );
            case RELLENAR ->
                evaluarRellenar(
                    (PreguntaRellenar) pregunta,
                    scanner
                );
            default ->
                throw new IllegalArgumentException(
                    "Tipo de pregunta no evaluable de forma objetiva: "
                        + pregunta.getTipoPregunta()
                );
        };
    }

    private boolean evaluarVerdaderoFalso(
            PreguntaVerdaderoFalso pregunta,
            Scanner scanner
            ) {

        boolean respuesta = leerBooleano(
            scanner,
            "Respuesta (true/false o si/no): "
        );

        return respuesta == pregunta.isRespuestaCorrecta();
    }

    private boolean evaluarOpciones(
            PreguntaOpciones pregunta,
            Scanner scanner
            ) {

        if (pregunta.tieneVariasOpcionesCorrectas()) {
            System.out.println(
                "Varias opciones son validas. Indica el numero de una opcion correcta."
            );
        } else {
            System.out.println(
                "Indica el numero de la opcion correcta."
            );
        }

        int numeroOpcion = leerEnteroEnRango(
            scanner,
            "Numero de opcion: ",
            1,
            pregunta.getOpciones().size()
        );

        return pregunta.aciertaOpcionSeleccionada(numeroOpcion);
    }

    private boolean evaluarRellenar(
            PreguntaRellenar pregunta,
            Scanner scanner
            ) {

        List<String> respuestas = new ArrayList<>();
        int huecos = pregunta.cantidadHuecos();

        for (int i = 1; i <= huecos; i++) {
            System.out.print("Palabra para el hueco " + i + ": ");
            respuestas.add(scanner.nextLine());
        }

        return pregunta.aciertaPalabrasEnOrden(respuestas);
    }

    private boolean leerBooleano(
            Scanner scanner,
            String mensaje
            ) {

        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim().toLowerCase();

            if (valor.equals("s")
                || valor.equals("si")
                || valor.equals("true")
                || valor.equals("t")
                || valor.equals("verdadero")) {
                return true;
            }

            if (valor.equals("n")
                || valor.equals("no")
                || valor.equals("false")
                || valor.equals("f")
                || valor.equals("falso")) {
                return false;
            }

            System.out.println(
                "Entrada no valida. Usa true/false o si/no."
            );
        }
    }

    private int leerEnteroEnRango(
            Scanner scanner,
            String mensaje,
            int minimo,
            int maximo
            ) {

        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine();

            try {
                int numero = Integer.parseInt(valor.trim());

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException excepcion) {
                // Reintento.
            }

            System.out.println(
                "Entrada no valida. Debe estar entre "
                    + minimo
                    + " y "
                    + maximo
                    + "."
            );
        }
    }
}
