package p2.dominio.preguntas;

/**
 * Pregunta teórica con una respuesta ejemplar.
 */
public class PreguntaTeorica extends Pregunta {

    private final String respuestaCorrecta;
    //-- Inmutable una vez creada.
    //-- Safeguard

    public PreguntaTeorica(
            String texto,
            String textoAclaratorio,
            double nota,
            String respuestaCorrecta
            ) {

        super(  texto,
                textoAclaratorio,
                nota
                );

        this.respuestaCorrecta =
            validarRespuestaCorrecta( respuestaCorrecta );
        }

    public PreguntaTeorica(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            String respuestaCorrecta
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                dificultad
                );

        this.respuestaCorrecta =
            validarRespuestaCorrecta( respuestaCorrecta );
        }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
        }

    @Override
    public TipoPregunta getTipoPregunta() {
        return TipoPregunta.TEORICA;
        }


    @Override
    public String imprimirSimple() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());
        builder.append(System.lineSeparator());
        builder.append("Respuesta: ______________________________");

        return builder.toString();
        }

    @Override
    public String imprimirCompleto() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirSimple());
        builder.append(System.lineSeparator());
        builder.append("Respuesta correcta de ejemplo: ");
        builder.append(respuestaCorrecta);

        return builder.toString();
        }

    private static String validarRespuestaCorrecta(String respuestaCorrecta) {
        if (respuestaCorrecta == null 
            || respuestaCorrecta.isBlank()) {
            throw new IllegalArgumentException(
                    "La respuesta correcta es obligatoria."
                    );
            }

        return respuestaCorrecta.trim();
        }
    }
