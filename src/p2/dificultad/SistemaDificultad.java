package p2.dificultad;

import p2.dominio.preguntas.Pregunta;

/**
 * Lógica de actualización de dificultad histórica por evidencias.
 */
public class SistemaDificultad {

    public void registrarResultado(ResultadoPrueba resultado) {
        if (resultado.isTieneRespuestaObjetiva()) {
            if (resultado.isAcertada()) {
                resultado.getSujetoTester().registrarAcierto();
            } else {
                resultado.getSujetoTester().registrarFallo();
            }
        }

        actualizarDificultad(
            resultado.getPregunta(),
            resultado
        );
    }

    public double calcularUtilidadTester(SujetoTester sujetoTester) {
        double maestria = sujetoTester.getMaestria();
        return 4.0 * maestria * (1.0 - maestria);
    }

    public void actualizarDificultad(
            Pregunta pregunta,
            ResultadoPrueba resultado
            ) {

        double dificultadActual = pregunta.getDificultad();
        double utilidad =
            calcularUtilidadTester(
                resultado.getSujetoTester()
            );
        double evidencia = construirEvidencia(resultado);

        double nuevaDificultad =
            ((10.0 - utilidad) * dificultadActual + utilidad * evidencia) / 10.0;

        nuevaDificultad = Math.max(0.0, nuevaDificultad);
        nuevaDificultad = Math.min(1.0, nuevaDificultad);

        pregunta.setDificultad(nuevaDificultad);
    }

    private double construirEvidencia(ResultadoPrueba resultado) {
        if (!resultado.isTieneRespuestaObjetiva()) {
            return (resultado.getValoracionSubjetiva() - 1.0) / 9.0;
        }

        if (resultado.isAcertada()) {
            return 0.0;
        }

        return 1.0;
    }
}
