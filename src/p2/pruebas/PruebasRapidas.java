package p2.pruebas;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.examenes.Convocatoria;
import p2.dominio.examenes.Examen;
import p2.dominio.examenes.TipoExamen;
import p2.generacion.GeneradorExamen;
import p2.persistencia.RepositorioPreguntas;
import p2.persistencia.markdown.ArchivoPreguntasMarkdown;

/**
 * Pruebas rápidas de humo para validar flujo básico.
 */
public final class PruebasRapidas {

    private PruebasRapidas() {
    }

    public static void main(String[] args) {
        Path carpetaPreguntas = Paths.get("files", "preguntas");

        RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas(
            carpetaPreguntas,
            new ArchivoPreguntasMarkdown()
        );

        Asignatura asignatura = new Asignatura("POO", "Programacion Orientada a Objetos");
        asignatura.reemplazarPreguntas(
            repositorioPreguntas.cargar(asignatura)
        );

        if (asignatura.getPreguntas().isEmpty()) {
            throw new IllegalStateException(
                "No hay preguntas cargadas para POO."
            );
        }

        GeneradorExamen generadorExamen = new GeneradorExamen();
        Examen examen = generadorExamen.generarExamen(
            "tester",
            asignatura,
            Convocatoria.JUNIO,
            "2025-2026",
            TipoExamen.MIXTO,
            3
        );

        List<?> preguntas = examen.getPreguntas();
        if (preguntas.size() != 3) {
            throw new IllegalStateException(
                "El examen no tiene el numero esperado de preguntas."
            );
        }

        System.out.println("Pruebas rapidas OK.");
        System.out.println(examen.resumen());
    }
}
