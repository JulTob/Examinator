package p2.persistencia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import p2.dominio.examenes.Examen;

/**
 * Repositorio de exámenes persistido en binario.
 */
public class RepositorioExamenes {

    private final Path rutaArchivo;

    public RepositorioExamenes(Path rutaArchivo) {
        if (rutaArchivo == null) {
            throw new IllegalArgumentException(
                "La ruta de exámenes es obligatoria."
            );
        }

        this.rutaArchivo = rutaArchivo;
    }

    public List<Examen> cargarTodos() {
        if (!Files.exists(rutaArchivo)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(
                Files.newInputStream(rutaArchivo)
                )) {

            Object valor = input.readObject();
            if (valor instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<Examen> examenes = (List<Examen>) valor;
                return new ArrayList<>(examenes);
            }

            throw new IllegalStateException(
                "El archivo de examenes no contiene una lista valida."
            );
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudieron cargar los examenes.",
                excepcion
            );
        } catch (ClassNotFoundException excepcion) {
            throw new IllegalStateException(
                "No se pudieron reconstruir los examenes persistidos.",
                excepcion
            );
        }
    }

    public void guardarTodos(List<Examen> examenes) {
        try {
            Files.createDirectories(
                rutaArchivo.getParent()
            );
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudo crear la carpeta de persistencia de examenes.",
                excepcion
            );
        }

        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(rutaArchivo)
                )) {
            output.writeObject(
                new ArrayList<>(examenes)
            );
        } catch (IOException excepcion) {
            throw new UncheckedIOException(
                "No se pudieron guardar los examenes.",
                excepcion
            );
        }
    }

    public void agregar(Examen examen) {
        List<Examen> examenes = cargarTodos();
        examenes.add(examen);
        guardarTodos(examenes);
    }
}
