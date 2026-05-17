package p2.consola;

import java.util.List;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.examenes.Convocatoria;
import p2.dominio.examenes.Examen;
import p2.dominio.examenes.TipoExamen;
import p2.generacion.GeneradorExamen;
import p2.persistencia.RepositorioExamenes;

/**
 * Submenú de creación y consulta de exámenes.
 */
public class VistaExamenes extends ConsolaBase {

    private final VistaAsignaturas vistaAsignaturas;
    private final RepositorioExamenes repositorioExamenes;
    private final GeneradorExamen generadorExamen;

    public VistaExamenes(
            Scanner scanner,
            VistaAsignaturas vistaAsignaturas,
            RepositorioExamenes repositorioExamenes,
            GeneradorExamen generadorExamen
            ) {

        super(scanner);

        this.vistaAsignaturas = vistaAsignaturas;
        this.repositorioExamenes = repositorioExamenes;
        this.generadorExamen = generadorExamen;
    }

    public void ejecutar() {
        boolean volver = false;

        while (!volver) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    crearExamen();
                    break;
                case 2:
                    verExamenesGuardados();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== Examenes =====");
        System.out.println("1. Crear examen");
        System.out.println("2. Ver examenes guardados");
        System.out.println("3. Volver");
    }

    private void crearExamen() {
        String realizadoPor = leerTextoObligatorio("Realizado por: ");
        Asignatura asignatura =
            vistaAsignaturas.seleccionarAsignatura(
                false
            );
        Convocatoria convocatoria = seleccionarConvocatoria();
        String curso = leerTextoObligatorio("Curso (ej: 2025-2026): ");
        TipoExamen tipoExamen = seleccionarTipoExamen();
        int numeroPreguntas = leerEntero("Numero de preguntas: ");

        Examen examen = generadorExamen.generarExamen(
            realizadoPor,
            asignatura,
            convocatoria,
            curso,
            tipoExamen,
            numeroPreguntas
        );

        repositorioExamenes.agregar(examen);

        System.out.println();
        System.out.println("Examen creado y guardado.");
        System.out.println(examen.imprimirSimple());
    }

    private void verExamenesGuardados() {
        List<Examen> examenes = repositorioExamenes.cargarTodos();

        if (examenes.isEmpty()) {
            System.out.println("No hay examenes guardados.");
            return;
        }

        System.out.println();
        System.out.println("=== Examenes Guardados ===");

        for (int i = 0; i < examenes.size(); i++) {
            System.out.println((i + 1) + ". " + examenes.get(i).resumen());
        }

        int opcion =
            leerEntero(
                "Selecciona examen para imprimir (0 para volver): "
            );

        if (opcion == 0) {
            return;
        }

        if (opcion < 1 || opcion > examenes.size()) {
            throw new IllegalArgumentException(
                "Seleccion de examen no valida."
            );
        }

        Examen examen = examenes.get(opcion - 1);
        boolean conRespuestas =
            leerBooleano(
                "Imprimir con respuestas? (true/false): "
            );

        System.out.println();
        System.out.println(examen.imprimir(conRespuestas));
    }

    private TipoExamen seleccionarTipoExamen() {
        TipoExamen[] valores = TipoExamen.values();

        System.out.println();
        System.out.println("Tipos de examen:");

        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }

        int opcion = leerEntero("Selecciona tipo de examen: ");

        if (opcion < 1 || opcion > valores.length) {
            throw new IllegalArgumentException(
                "Tipo de examen no valido."
            );
        }

        return valores[opcion - 1];
    }

    private Convocatoria seleccionarConvocatoria() {
        Convocatoria[] valores = Convocatoria.values();

        System.out.println();
        System.out.println("Convocatorias:");

        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }

        int opcion = leerEntero("Selecciona convocatoria: ");

        if (opcion < 1 || opcion > valores.length) {
            throw new IllegalArgumentException(
                "Convocatoria no valida."
            );
        }

        return valores[opcion - 1];
    }
}
