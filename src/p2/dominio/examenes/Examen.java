package p2.dominio.examenes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.impresion.IImprimible;
import p2.dominio.preguntas.Pregunta;

/**
 * Examen generado para una asignatura concreta.
 */
public class Examen implements IImprimible, Serializable {

    private static final long serialVersionUID = 1L;

    private static final double PUNTUACION_MAXIMA_POR_DEFECTO = 10.0;

    private final LocalDate fecha;
    private final String realizadoPor;
    private final Asignatura asignatura;
    private final Convocatoria convocatoria;
    private final String curso;
    private final TipoExamen tipo;
    private final double puntuacionMaxima;
    private final List<Pregunta> preguntas;

    public Examen(
            String realizadoPor,
            Asignatura asignatura,
            Convocatoria convocatoria,
            String curso,
            TipoExamen tipo,
            List<Pregunta> preguntas
            ) {

        this(
            realizadoPor,
            asignatura,
            convocatoria,
            curso,
            tipo,
            PUNTUACION_MAXIMA_POR_DEFECTO,
            preguntas
        );
    }

    public Examen(
            String realizadoPor,
            Asignatura asignatura,
            Convocatoria convocatoria,
            String curso,
            TipoExamen tipo,
            double puntuacionMaxima,
            List<Pregunta> preguntas
            ) {

        this.fecha = LocalDate.now();
        this.realizadoPor =
            validarTextoObligatorio(
                realizadoPor,
                "realizadoPor"
            );
        this.asignatura = validarAsignatura(asignatura);
        this.convocatoria = validarConvocatoria(convocatoria);
        this.curso = validarTextoObligatorio(curso, "curso");
        this.tipo = validarTipo(tipo);
        this.puntuacionMaxima =
            validarPuntuacionMaxima(puntuacionMaxima);
        this.preguntas = copiarPreguntas(preguntas);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public String getCurso() {
        return curso;
    }

    public TipoExamen getTipo() {
        return tipo;
    }

    public double getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public List<Pregunta> getPreguntas() {
        return Collections.unmodifiableList(
            preguntas
        );
    }

    public String resumen() {
        return String.format(
            "%s | %s | %s | %s | %s | %d preguntas",
            fecha.format(DateTimeFormatter.ISO_DATE),
            realizadoPor,
            asignatura.imprimirSimple(),
            convocatoria,
            curso,
            preguntas.size()
        );
    }

    @Override
    public String imprimirSimple() {
        return imprimir(false);
    }

    @Override
    public String imprimirCompleto() {
        return imprimir(true);
    }

    @Override
    public String imprimir(boolean incluirRespuestas) {
        StringBuilder builder = new StringBuilder();

        builder.append("Fecha: ");
        builder.append(fecha.format(DateTimeFormatter.ISO_DATE));
        builder.append(System.lineSeparator());
        builder.append("Realizado por: ");
        builder.append(realizadoPor);
        builder.append(System.lineSeparator());
        builder.append("Asignatura: ");
        builder.append(asignatura.imprimirSimple());
        builder.append(System.lineSeparator());
        builder.append("Convocatoria: ");
        builder.append(convocatoria);
        builder.append(System.lineSeparator());
        builder.append("Curso: ");
        builder.append(curso);
        builder.append(System.lineSeparator());
        builder.append("Tipo: ");
        builder.append(tipo);
        builder.append(System.lineSeparator());
        builder.append("Puntuacion maxima: ");
        builder.append(String.format("%.2f", puntuacionMaxima));
        builder.append(System.lineSeparator());
        builder.append("Numero de preguntas: ");
        builder.append(preguntas.size());

        for (int i = 0; i < preguntas.size(); i++) {
            builder.append(System.lineSeparator());
            builder.append(System.lineSeparator());
            builder.append("Pregunta ");
            builder.append(i + 1);
            builder.append(":");
            builder.append(System.lineSeparator());
            builder.append(
                preguntas.get(i).imprimir(incluirRespuestas)
            );
        }

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

    private static Asignatura validarAsignatura(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException(
                "La asignatura es obligatoria."
            );
        }

        return asignatura;
    }

    private static Convocatoria validarConvocatoria(
            Convocatoria convocatoria
            ) {
        if (convocatoria == null) {
            throw new IllegalArgumentException(
                "La convocatoria es obligatoria."
            );
        }

        return convocatoria;
    }

    private static TipoExamen validarTipo(TipoExamen tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                "El tipo de examen es obligatorio."
            );
        }

        return tipo;
    }

    private static double validarPuntuacionMaxima(double valor) {
        if (valor <= 0.0) {
            throw new IllegalArgumentException(
                "La puntuacion maxima debe ser mayor que 0."
            );
        }

        return valor;
    }

    private static List<Pregunta> copiarPreguntas(
            List<Pregunta> preguntas
            ) {
        if (preguntas == null || preguntas.isEmpty()) {
            throw new IllegalArgumentException(
                "El examen debe incluir al menos una pregunta."
            );
        }

        List<Pregunta> copia = new ArrayList<>();

        for (Pregunta pregunta : preguntas) {
            if (pregunta == null) {
                throw new IllegalArgumentException(
                    "La lista de preguntas contiene valores nulos."
                );
            }

            copia.add(pregunta);
        }

        return copia;
    }
}
