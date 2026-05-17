package p2.consola;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import p2.dominio.asignaturas.Asignatura;
import p2.persistencia.RepositorioPreguntas;

/**
 * Vista compartida para seleccionar y crear asignaturas.
 */
public class VistaAsignaturas extends ConsolaBase {

    private final Map<String, Asignatura> asignaturas;
    private final RepositorioPreguntas repositorioPreguntas;

    public VistaAsignaturas(
            Scanner scanner,
            Map<String, Asignatura> asignaturas,
            RepositorioPreguntas repositorioPreguntas
            ) {

        super(scanner);

        this.asignaturas = asignaturas;
        this.repositorioPreguntas = repositorioPreguntas;
    }

    public Asignatura seleccionarAsignatura(boolean permitirNueva) {
        List<Asignatura> listado = new ArrayList<>(asignaturas.values());

        System.out.println();
        System.out.println("Asignaturas:");

        for (int i = 0; i < listado.size(); i++) {
            Asignatura asignatura = listado.get(i);
            System.out.println((i + 1) + ". " + asignatura.imprimirSimple());
        }

        if (permitirNueva) {
            System.out.println("0. Crear nueva asignatura");
        }

        int opcion = leerEntero("Selecciona asignatura: ");

        if (permitirNueva && opcion == 0) {
            return crearAsignatura();
        }

        if (opcion < 1 || opcion > listado.size()) {
            throw new IllegalArgumentException(
                "Asignatura no valida."
            );
        }

        return listado.get(opcion - 1);
    }

    public void guardarPreguntas() {
        for (Asignatura asignatura : asignaturas.values()) {
            repositorioPreguntas.guardar(
                asignatura
            );
        }
    }

    public void cargarPreguntas() {
        for (Asignatura asignatura : asignaturas.values()) {
            try {
                asignatura.reemplazarPreguntas(
                    repositorioPreguntas.cargar(asignatura)
                );
            } catch (UncheckedIOException | IllegalArgumentException excepcion) {
                System.out.println(
                    "Advertencia: preguntas de "
                        + asignatura.getCodigo()
                        + " no cargadas — "
                        + excepcion.getMessage()
                );
            }
        }
    }

    public void detectarAsignaturas() {
        List<Asignatura> detectadas =
            repositorioPreguntas.detectarAsignaturasDesdeCarpetas();

        for (Asignatura detectada : detectadas) {
            asignaturas.putIfAbsent(
                detectada.getCodigo(),
                detectada
            );
        }
    }

    private Asignatura crearAsignatura() {
        String codigo =
            leerTextoObligatorio(
                "Codigo asignatura: "
            ).toUpperCase();

        if (asignaturas.containsKey(codigo)) {
            throw new IllegalArgumentException(
                "Ya existe una asignatura con ese codigo."
            );
        }

        String titulo = leerTextoObligatorio("Titulo asignatura: ");
        Asignatura asignatura =
            new Asignatura(
                codigo,
                titulo
            );

        asignaturas.put(codigo, asignatura);
        repositorioPreguntas.guardar(asignatura);

        return asignatura;
    }
}
