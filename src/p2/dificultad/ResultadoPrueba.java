package p2.dificultad;

import p2.dominio.preguntas.Pregunta;

/**
 * Resultado de una pregunta durante sesión de calibración.
 */
public class ResultadoPrueba {

    private final Pregunta pregunta;
    private final SujetoTester sujetoTester;
    private final boolean acertada;
    private final boolean tieneRespuestaObjetiva;
    private final int valoracionSubjetiva;

    public ResultadoPrueba(
            Pregunta pregunta,
            SujetoTester sujetoTester,
            boolean acertada,
            boolean tieneRespuestaObjetiva,
            int valoracionSubjetiva
            ) {

        if (pregunta == null) {
            throw new IllegalArgumentException(
                "La pregunta del resultado es obligatoria."
            );
        }

        if (sujetoTester == null) {
            throw new IllegalArgumentException(
                "El sujeto tester del resultado es obligatorio."
            );
        }

        if (!tieneRespuestaObjetiva
            && (valoracionSubjetiva < 1 || valoracionSubjetiva > 10)) {
            throw new IllegalArgumentException(
                "La valoracion subjetiva debe estar entre 1 y 10."
            );
        }

        this.pregunta = pregunta;
        this.sujetoTester = sujetoTester;
        this.acertada = acertada;
        this.tieneRespuestaObjetiva = tieneRespuestaObjetiva;
        this.valoracionSubjetiva = valoracionSubjetiva;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public SujetoTester getSujetoTester() {
        return sujetoTester;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public boolean isTieneRespuestaObjetiva() {
        return tieneRespuestaObjetiva;
    }

    public int getValoracionSubjetiva() {
        return valoracionSubjetiva;
    }
}
