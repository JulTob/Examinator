package p2.dominio.preguntas;

/**
 * Pregunta tipo test con respuesta verdadera o falsa.
 */
public class PreguntaVerdaderoFalso extends PreguntaTest {

    private final boolean respuestaCorrecta;

    public PreguntaVerdaderoFalso(
            String texto,
            String textoAclaratorio,
            double nota,
            double penalizacion,
            boolean respuestaCorrecta
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                penalizacion
                );

        this.respuestaCorrecta = respuestaCorrecta;
        }

    public PreguntaVerdaderoFalso(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            double penalizacion,
            boolean respuestaCorrecta
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                dificultad,
                penalizacion
                );

        this.respuestaCorrecta = respuestaCorrecta;
        }

    public boolean isRespuestaCorrecta() {
        return respuestaCorrecta;
        }

    @Override
    public TipoPregunta getTipoPregunta() {
        return TipoPregunta.VERDADERO_FALSO;
        }

    @Override
    public String imprimirSimple() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());
        builder.append(System.lineSeparator());
        builder.append("Opciones: verdadero / falso");
        builder.append(System.lineSeparator());
        builder.append(imprimirPenalizacion());

        return builder.toString();
        }

    @Override
    public String imprimirCompleto() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirSimple());
        builder.append(System.lineSeparator());
        builder.append("Respuesta correcta: ");
        builder.append(respuestaCorrecta);

        return builder.toString();
        }
    }
