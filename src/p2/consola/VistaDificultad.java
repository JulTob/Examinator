package p2.consola;

import java.util.Scanner;

import p2.dificultad.TestTester;
import p2.dominio.asignaturas.Asignatura;
import p2.persistencia.RepositorioPreguntas;

/**
 * Submenú de la ampliación de dificultad.
 */
public class VistaDificultad extends ConsolaBase {

    private final VistaAsignaturas vistaAsignaturas;
    private final RepositorioPreguntas repositorioPreguntas;
    private final TestTester testTester;

    public VistaDificultad(
            Scanner scanner,
            VistaAsignaturas vistaAsignaturas,
            RepositorioPreguntas repositorioPreguntas,
            TestTester testTester
            ) {

        super(scanner);

        this.vistaAsignaturas = vistaAsignaturas;
        this.repositorioPreguntas = repositorioPreguntas;
        this.testTester = testTester;
    }

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    ejecutarSesionDificultad();
                    break;
                case 2:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== Tests Dificultad =====");
        System.out.println("1. Ejecutar sesion de dificultad");
        System.out.println("2. Volver");
    }

    private void ejecutarSesionDificultad() {
        Asignatura asignatura = vistaAsignaturas.seleccionarAsignatura(false);
        int numeroPreguntas = leerEntero("Numero de preguntas de la sesion: ");

        testTester.ejecutarSesion(
            asignatura,
            numeroPreguntas,
            scanner
        );

        repositorioPreguntas.guardar(asignatura);
        System.out.println("Dificultades guardadas.");
    }
}
