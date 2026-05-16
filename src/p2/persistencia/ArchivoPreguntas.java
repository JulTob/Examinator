package p2.persistencia;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import p2.dominio.preguntas.Pregunta;

/**
 * Contrato para leer y escribir preguntas desde un formato concreto.
 */
public interface ArchivoPreguntas {

    List<Pregunta> leer(Path ruta) throws IOException;

    void escribir(
            Path ruta,
            List<Pregunta> preguntas
            ) throws IOException;
}
