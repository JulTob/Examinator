package p2.persistencia;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.preguntas.Pregunta;

/**
 * Repositorio de preguntas por asignatura en carpeta separada.
 */
public class RepositorioPreguntas {

    private final Path carpetaBasePreguntas;
    private final ArchivoPreguntas archivoPreguntas;

    public RepositorioPreguntas(
            Path carpetaBasePreguntas,
            ArchivoPreguntas archivoPreguntas
            ) {

        if (carpetaBasePreguntas == null) {
            throw new IllegalArgumentException(
                "La carpeta base de preguntas es obligatoria."
            );
        }

        if (archivoPreguntas == null) {
            throw new IllegalArgumentException(
                "El adaptador de archivo de preguntas es obligatorio."
            );
        }

        this.carpetaBasePreguntas = carpetaBasePreguntas;
        this.archivoPreguntas = archivoPreguntas;
    }

    public List<Pregunta> cargar(Asignatura asignatura) {
        Path rutaArchivo = construirRutaArchivoPreguntas(asignatura);

        if (!Files.exists(rutaArchivo)) {
            return new ArrayList<>();
        }

        try {
            return archivoPreguntas.leer(rutaArchivo);
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudieron cargar las preguntas de " + asignatura.getCodigo(),
                excepcion
            );
        }
    }

    public void guardar(Asignatura asignatura) {
        guardar(
            asignatura,
            asignatura.getPreguntas()
        );
    }

    public void guardarTodas(Iterable<Asignatura> asignaturas) {
        if (asignaturas == null) {
            throw new IllegalArgumentException(
                "Las asignaturas son obligatorias."
            );
        }

        for (Asignatura asignatura : asignaturas) {
            guardar(asignatura);
        }
    }

    public void guardar(
            Asignatura asignatura,
            List<Pregunta> preguntas
            ) {

        Path rutaArchivo = construirRutaArchivoPreguntas(asignatura);

        try {
            Files.createDirectories(
                rutaArchivo.getParent()
            );
            archivoPreguntas.escribir(
                rutaArchivo,
                preguntas
            );
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudieron guardar las preguntas de " + asignatura.getCodigo(),
                excepcion
            );
        }
    }

    public List<Asignatura> detectarAsignaturasDesdeCarpetas() {
        List<Asignatura> asignaturas = new ArrayList<>();

        if (!Files.exists(carpetaBasePreguntas)) {
            return asignaturas;
        }

        try (var stream = Files.list(carpetaBasePreguntas)) {
            stream
                .filter(Files::isDirectory)
                .forEach(rutaAsignatura -> {
                    String codigo = rutaAsignatura.getFileName().toString();
                    asignaturas.add(
                        new Asignatura(
                            codigo,
                            codigo
                        )
                    );
                });
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudieron detectar las asignaturas desde carpetas.",
                excepcion
            );
        }

        return asignaturas;
    }

    private Path construirRutaArchivoPreguntas(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException(
                "La asignatura es obligatoria."
            );
        }

        return carpetaBasePreguntas
            .resolve(asignatura.getCodigo())
            .resolve("preguntas.md");
    }
}
