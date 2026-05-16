package p2.dominio.preguntas;

/**
 * Base común para preguntas tipo test.
 *
 * <p>La penalización indica cuántos puntos se restan si la respuesta es
 * incorrecta. Un valor {@code 0.0} significa que no penaliza.</p>
 */
public abstract class PreguntaTest extends Pregunta {

    private double penalizacion;

    protected PreguntaTest(
            String texto,
            String textoAclaratorio,
            double nota,
            double penalizacion
            ) {

        super(  texto,
                textoAclaratorio,
                nota
                );

        setPenalizacion(penalizacion);
        }

    protected PreguntaTest(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            double penalizacion
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                dificultad
                );

        setPenalizacion(penalizacion);
        }

    public double getPenalizacion() {
        return penalizacion;
        }

    public void setPenalizacion(double penalizacion) {
        if (esMenorQue(
                penalizacion,
                0.0
                )) {
            throw new IllegalArgumentException(
                    "La penalizacion no puede ser negativa."
                );
            }

        this.penalizacion =
            normalizarCero(
                    penalizacion
                );
        }

    protected String imprimirPenalizacion() {
        if (esCero(
                penalizacion
                )) {
            return "Penalizacion: no penaliza";
            }

        return "Penalizacion: "
            + String.format(
                    "%.2f",
                    penalizacion
                    )
            + " puntos";
        }
    }
