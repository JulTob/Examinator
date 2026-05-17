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
import p2.persistencia.GuardadoAlSalir;
import p2.persistencia.RepositorioExamenes;
import p2.persistencia.RepositorioPreguntas;
import p2.persistencia.markdown.ArchivoPreguntasMarkdown;

/**
 * Punto de arranque de la aplicacion por consola.
 *
 * <p>El menu principal agrupa acciones en submenus (preguntas, examenes,
 * tests de dificultad). Las funciones obligatorias del enunciado siguen disponibles:
 * alta de preguntas y examenes, listado de examenes guardados e impresion con o sin
 * respuestas dentro del submenu correspondiente; salir persiste y cierra la entrada.</p>
 *
 * <p>{@link p2.persistencia.GuardadoAlSalir} concentra el volcado al abandonar la aplicacion,
 * manteniendo esta clase enfocada en el cableado de vistas y dependencias.</p>
 */
public class AplicacionConsola extends ConsolaBase {

    private final Map<String, Asignatura> asignaturas;
    private final GuardadoAlSalir guardadoAlSalir;
    private final VistaAsignaturas vistaAsignaturas;
    private final VistaPreguntas vistaPreguntas;
    private final VistaExamenes vistaExamenes;
    private final VistaDificultad vistaDificultad;

    /**
     * Construye la aplicacion: rutas de datos por defecto, repositorios, vistas y
     * registro/carga inicial de asignaturas conocidas.
     */
    public AplicacionConsola() {
        super(new Scanner(System.in));

        this.asignaturas =
            new LinkedHashMap<>();

        Path carpetaPreguntas =
            Paths.get(
                "files",
                "preguntas"
            );
        Path archivoExamenes =
            Paths.get(
                "files",
                "examenes.dat"
            );

        RepositorioPreguntas repositorioPreguntas =
            new RepositorioPreguntas(
                carpetaPreguntas,
                new ArchivoPreguntasMarkdown()
            );
        RepositorioExamenes repositorioExamenes =
            new RepositorioExamenes(
                archivoExamenes
            );

        this.guardadoAlSalir =
            new GuardadoAlSalir(
                repositorioPreguntas,
                repositorioExamenes,
                asignaturas.values()
            );
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
                new GeneradorExamen()
            );
        this.vistaDificultad =
            new VistaDificultad(
                scanner,
                vistaAsignaturas,
                repositorioPreguntas,
                new TestTester(
                    new SistemaDificultad()
                )
            );

        registrarAsignaturasPorDefecto();
        vistaAsignaturas.detectarAsignaturas();
        vistaAsignaturas.cargarPreguntas();
    }

    /**
     * Bucle del menu principal hasta que el usuario elige salir.
     */
    public void ejecutar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Selecciona una opcion: ");

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
                    guardarYCerrar();
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
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

    private void guardarYCerrar() {
        guardadoAlSalir.guardarTodo();
        cerrar();
    }
}
