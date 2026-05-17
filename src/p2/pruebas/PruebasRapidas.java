package p2.pruebas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.examenes.Convocatoria;
import p2.dominio.examenes.Examen;
import p2.dominio.examenes.TipoExamen;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaRellenar;
import p2.generacion.GeneradorExamen;
import p2.persistencia.RepositorioPreguntas;
import p2.persistencia.markdown.ArchivoPreguntasMarkdown;

/**
 * Pruebas rápidas de humo para validar flujo básico.
 */
public final class PruebasRapidas {

    private PruebasRapidas() {
    }

    public static void main(String[] args) throws Exception {
        Path carpetaPreguntas = Paths.get("files", "preguntas");
        ArchivoPreguntasMarkdown archivoMarkdown =
            new ArchivoPreguntasMarkdown();

        RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas(
            carpetaPreguntas,
            archivoMarkdown
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

        probarRellenarConInterrogacion();
        probarPersistenciaRellenarConFraseSeparada(
            archivoMarkdown
        );

        System.out.println("Pruebas rapidas OK.");
        System.out.println(examen.resumen());
    }

    private static void probarRellenarConInterrogacion() {
        PreguntaRellenar pregunta =
            new PreguntaRellenar(
                "Completa la frase.",
                "",
                1.0,
                "Java ? usa ?.",
                List.of(
                    "tambien",
                    "interfaces"
                )
            );

        if (pregunta.cantidadHuecos() != 2) {
            throw new IllegalStateException(
                "La pregunta de rellenar no reconoce los huecos con ?."
            );
        }
    }

    private static void probarPersistenciaRellenarConFraseSeparada(
            ArchivoPreguntasMarkdown archivoMarkdown
            ) throws Exception {

        Path rutaTemporal =
            Files.createTempFile(
                "p2-preguntas-",
                ".md"
            );

        try {
            PreguntaRellenar original =
                new PreguntaRellenar(
                    "Completa la frase.",
                    "Usa una palabra por hueco.",
                    1.0,
                    "Java ? usa ?.",
                    List.of(
                        "tambien",
                        "interfaces"
                    )
                );

            archivoMarkdown.escribir(
                rutaTemporal,
                List.of(original)
            );

            List<Pregunta> leidas =
                archivoMarkdown.leer(
                    rutaTemporal
                );

            if (leidas.size() != 1
                    || !(leidas.get(0) instanceof PreguntaRellenar)) {
                throw new IllegalStateException(
                    "No se reconstruyo la pregunta de rellenar."
                );
            }

            PreguntaRellenar reconstruida =
                (PreguntaRellenar) leidas.get(0);

            if (!original.getFraseConHuecos()
                    .equals(reconstruida.getFraseConHuecos())) {
                throw new IllegalStateException(
                    "La persistencia perdio la frase con huecos."
                );
            }
        } finally {
            Files.deleteIfExists(
                rutaTemporal
            );
        }
    }
}
