package p2.consola;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import p2.dificultad.SistemaDificultad;
import p2.dificultad.TestTester;
import p2.dominio.asignaturas.Asignatura;
import p2.generacion.GeneradorExamen;
import p2.persistencia.RepositorioExamenes;
import p2.persistencia.RepositorioPreguntas;
import p2.persistencia.markdown.ArchivoPreguntasMarkdown;

/**
 * Aplicación de consola principal.
 */
public class AplicacionConsola extends ConsolaBase {

    private final Map<String, Asignatura> asignaturas;
    private final VistaAsignaturas vistaAsignaturas;
    private final VistaPreguntas vistaPreguntas;
    private final VistaExamenes vistaExamenes;
    private final VistaDificultad vistaDificultad;

    public AplicacionConsola() {
        super(new Scanner(System.in));

        this.asignaturas = new LinkedHashMap<>();

        Path carpetaPreguntas = Paths.get("files", "preguntas");
        Path archivoExamenes = Paths.get("files", "examenes.dat");

        RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas(
            carpetaPreguntas,
            new ArchivoPreguntasMarkdown()
            );
        RepositorioExamenes repositorioExamenes =
            new RepositorioExamenes(
                archivoExamenes
            );
        GeneradorExamen generadorExamen = new GeneradorExamen();
        TestTester testTester = new TestTester(new SistemaDificultad());

        this.vistaAsignaturas =
            new VistaAsignaturas(
                scanner,
                asignaturas,
                repositorioPreguntas
            );
        this.vistaPreguntas =
            new VistaPreguntas(
                scanner,
                vistaAsignaturas,
                repositorioPreguntas
            );
        this.vistaExamenes =
            new VistaExamenes(
                scanner,
                vistaAsignaturas,
                repositorioExamenes,
                generadorExamen
            );
        this.vistaDificultad =
            new VistaDificultad(
                scanner,
                vistaAsignaturas,
                repositorioPreguntas,
                testTester
            );

        registrarAsignaturasPorDefecto();
        vistaAsignaturas.detectarAsignaturas();
        vistaAsignaturas.cargarPreguntas();
    }

    public void ejecutar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Selecciona una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        vistaPreguntas.ejecutar();
                        break;
                    case 2:
                        vistaExamenes.ejecutar();
                        break;
                    case 3:
                        vistaDificultad.ejecutar();
                        break;
                    case 4:
                        salir();
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException excepcion) {
                System.out.println("Error: " + excepcion.getMessage());
                }
        }

        System.out.println("Programa finalizado.");
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("===== Generador de Examenes =====");
        System.out.println("1. Preguntas");
        System.out.println("2. Examenes");
        System.out.println("3. Tests Dificultad");
        System.out.println("4. Salir");
    }

    private void salir() {
        vistaAsignaturas.guardarPreguntas();
        System.out.println("Datos guardados.");
    }

    private void registrarAsignaturasPorDefecto() {
        asignaturas.put(
            "POO",
            new Asignatura(
                "POO",
                "Programacion Orientada a Objetos"
            )
        );
        asignaturas.put(
            "DADM",
            new Asignatura(
                "DADM",
                "Desarrollo de aplicaciones para dispositivos moviles"
            )
        );
    }
}
