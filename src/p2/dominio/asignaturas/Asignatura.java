package p2.dominio.asignaturas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import p2.dominio.impresion.IImprimible;
import p2.dominio.examenes.TipoExamen;
import p2.dominio.preguntas.Pregunta;

/**
 * Asignatura del sistema con su banco de preguntas.
 */
public class Asignatura implements IImprimible, Serializable {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    private final String titulo;
    private final List<Pregunta> preguntas;

    public Asignatura(
            String codigo,
            String titulo
            ) {

        this.codigo =
            validarTextoObligatorio(
                codigo,
                "codigo"
            ).toUpperCase();

        this.titulo =
            validarTextoObligatorio(
                titulo,
                "titulo"
            );

        this.preguntas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Pregunta> getPreguntas() {
        return Collections.unmodifiableList(
            preguntas
        );
    }

    public void agregarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException(
                "La pregunta no puede ser nula."
            );
        }

        preguntas.add(pregunta);
    }

    public void limpiarPreguntas() {
        preguntas.clear();
    }

    public void reemplazarPreguntas(List<Pregunta> nuevasPreguntas) {
        limpiarPreguntas();

        if (nuevasPreguntas == null) {
            return;
        }

        for (Pregunta pregunta : nuevasPreguntas) {
            agregarPregunta(pregunta);
        }
    }

    public List<Pregunta> getPreguntasPorTipo(TipoExamen tipoExamen) {
        if (tipoExamen == null) {
            throw new IllegalArgumentException(
                "El tipo de examen es obligatorio."
            );
        }

        List<Pregunta> compatibles = new ArrayList<>();

        for (Pregunta pregunta : preguntas) {
            if (tipoExamen.admite(
                    pregunta.getTipoPregunta()
                    )) {
                compatibles.add(pregunta);
            }
        }

        return compatibles;
    }

    @Override
    public String imprimirSimple() {
        return codigo + " - " + titulo;
    }

    @Override
    public String imprimirCompleto() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirSimple());
        builder.append(System.lineSeparator());
        builder.append("Preguntas disponibles: ");
        builder.append(preguntas.size());

        return builder.toString();
    }

    private static String validarTextoObligatorio(
            String valor,
            String nombreCampo
            ) {

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                "El campo " + nombreCampo + " es obligatorio."
            );
        }

        return valor.trim();
    }
}
