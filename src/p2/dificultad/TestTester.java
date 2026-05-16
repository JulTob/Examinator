package p2.dificultad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.preguntas.Pregunta;
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
            System.out.println(pregunta.imprimirSimple());

            ResultadoPrueba resultado =
                recogerResultado(
                    pregunta,
                    sujetoTester,
                    scanner
                );

            sistemaDificultad.registrarResultado(resultado);
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

        boolean objetiva =
            esPreguntaObjetiva(
                pregunta.getTipoPregunta()
            );

        if (objetiva) {
            boolean acierta = leerBooleano(
                scanner,
                "¿El sujeto acerto la pregunta? (s/n): "
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

    private boolean esPreguntaObjetiva(TipoPregunta tipoPregunta) {
        return tipoPregunta == TipoPregunta.VERDADERO_FALSO
            || tipoPregunta == TipoPregunta.OPCIONES
            || tipoPregunta == TipoPregunta.RELLENAR;
    }

    private boolean leerBooleano(
            Scanner scanner,
            String mensaje
            ) {

        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim().toLowerCase();
            if (valor.equals("s") || valor.equals("si")) {
                return true;
            }
            if (valor.equals("n") || valor.equals("no")) {
                return false;
            }
            System.out.println("Entrada no valida. Usa s/n.");
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
