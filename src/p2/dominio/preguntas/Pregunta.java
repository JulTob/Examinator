package p2.dominio.preguntas;

import java.io.Serializable;

import p2.dominio.impresion.IImprimible;

/**
 * Clase base de cualquier pregunta del sistema.
 *
 * <p>La nota representa la puntuación máxima o ponderación de la pregunta,
 * no una calificación obtenida por un alumno.</p>
 */
public abstract class Pregunta implements IImprimible, Serializable {

    private static final long serialVersionUID = 1L;

    static final double DELTA_PUNTUACION = 0.001;

    public static final double DIFICULTAD_INICIAL = 0.5;

    private final String texto;
    private final String textoAclaratorio;
    private double nota;
    private double dificultad;

    /**
     * Crea una pregunta con dificultad inicial neutra.
     *
     * @param texto enunciado principal
     * @param textoAclaratorio aclaración pública opcional
     * @param nota puntuación máxima de la pregunta
     */
    protected Pregunta(
            String texto, 
            String textoAclaratorio, 
            double nota
            ) {

        this(
            texto,
            textoAclaratorio,
            nota,
            DIFICULTAD_INICIAL
            );
        }

    /**
     * Crea una pregunta con dificultad explícita.
     *
     * <p>Este constructor permite reconstruir preguntas desde persistencia.</p>
     *
     * @param texto enunciado principal
     * @param textoAclaratorio aclaración pública opcional
     * @param nota puntuación máxima de la pregunta
     * @param dificultad porcentaje normalizado entre {@code 0.0} y {@code 1.0}
     */
    protected Pregunta(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad
            ) {

        this.texto =
            validarTextoObligatorio(
                    texto,
                    "texto"
                );

        this.textoAclaratorio =
            normalizarTextoOpcional(
                    textoAclaratorio
                );

        setNota(nota);
        setDificultad(dificultad);
        }


    public String getTexto() {
        return texto;
        }

    public String getTextoAclaratorio() {
        return textoAclaratorio;
        }

    public double getNota() {
        return nota;
        }

    public void setNota(double nota) {
        if (esMenorQue(
                nota,
                0.0
                )) {
            throw new IllegalArgumentException(
                    "La nota no puede ser negativa."
                );
            }

        this.nota =
            normalizarCero(
                    nota
                );
        }

    public double getDificultad() {
        return dificultad;
        }

    public void setDificultad(double dificultad) {
        if (esMenorQue(
                dificultad,
                0.0
                )
            || esMayorQue(
                dificultad,
                1.0
                )) {
            throw new IllegalArgumentException(
                    "La dificultad debe estar entre 0.0 y 1.0."
                );
            }

        this.dificultad =
            normalizarRango(
                    dificultad,
                    0.0,
                    1.0
                );
        }

    public abstract TipoPregunta getTipoPregunta();

    protected String imprimirCabecera() {
        StringBuilder builder = new StringBuilder();

        builder.append("Pregunta (");
        builder.append(
            String.format(
                    "%.2f",
                    nota
                )
            );
        builder.append(" puntos)");
        builder.append(System.lineSeparator());
        builder.append(texto);

        if (!textoAclaratorio.isEmpty()) {
            builder.append(System.lineSeparator());
            builder.append("Aclaracion: ");
            builder.append(textoAclaratorio);
            }

        return builder.toString();
        }

    private static String validarTextoObligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    "El campo "
                        + campo
                        + " es obligatorio."
                );
            }

        return valor.trim();
        }

    private static String normalizarTextoOpcional(String valor) {
        if (valor == null) {
            return "";
            }

        return valor.trim();
        }

    static boolean esCero(double valor) {
        return Math.abs(valor) <= DELTA_PUNTUACION;
        }

    static boolean esMenorQue(
            double valor,
            double limite
            ) {

        return valor < limite - DELTA_PUNTUACION;
        }

    static boolean esMayorQue(
            double valor,
            double limite
            ) {

        return valor > limite + DELTA_PUNTUACION;
        }

    static boolean sonEquivalentes(
            double primerValor,
            double segundoValor
            ) {

        return Math.abs(
                primerValor
                    - segundoValor
                ) <= DELTA_PUNTUACION;
        }

    static double normalizarCero(double valor) {
        if (esCero(valor)) {
            return 0.0;
            }

        return valor;
        }

    static double normalizarRango(
            double valor,
            double minimo,
            double maximo
            ) {

        if (sonEquivalentes(
                valor,
                minimo
                )) {
            return minimo;
            }

        if (sonEquivalentes(
                valor,
                maximo
                )) {
            return maximo;
            }

        return valor;
        }
    }
