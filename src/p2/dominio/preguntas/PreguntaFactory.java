package p2.dominio.preguntas;

import java.util.List;

/**
 * Centraliza la construcción de preguntas a partir de datos ya validados en consola.
 */
public final class PreguntaFactory {

    private PreguntaFactory() {
        }

    public static PreguntaTeorica crearTeorica(
            String texto,
            String textoAclaratorio,
            double nota,
            String respuestaCorrecta
            ) {

        return new PreguntaTeorica(
            texto,
            textoAclaratorio,
            nota,
            respuestaCorrecta
            );
        }

    public static PreguntaVerdaderoFalso crearVerdaderoFalso(
            String texto,
            String textoAclaratorio,
            double nota,
            double penalizacion,
            boolean respuestaCorrecta
            ) {

        return new PreguntaVerdaderoFalso(
            texto,
            textoAclaratorio,
            nota,
            penalizacion,
            respuestaCorrecta
            );
        }

    public static PreguntaOpciones crearOpciones(
            String texto,
            String textoAclaratorio,
            double nota,
            double penalizacion,
            List<OpcionRespuesta> opciones
            ) {

        PreguntaOpciones pregunta = new PreguntaOpciones(
            texto,
            textoAclaratorio,
            nota,
            penalizacion
            );

        for (OpcionRespuesta opcion : opciones) {
            pregunta.agregarOpcion( opcion );
            }

        if (!pregunta.validarOpciones()) {
            throw new IllegalArgumentException(
                "La pregunta necesita al menos 2 opciones y una correcta."
                );
            }

        return pregunta;
        }

    public static PreguntaRellenar crearRellenar(
            String texto,
            String textoAclaratorio,
            double nota,
            String fraseConHuecos,
            List<String> palabrasCorrectas
            ) {

        return new PreguntaRellenar(
            texto,
            textoAclaratorio,
            nota,
            fraseConHuecos,
            palabrasCorrectas
            );
        }

    public static PreguntaDesarrollo crearDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota,
            List<ApartadoDesarrollo> apartados
            ) {

        PreguntaDesarrollo pregunta = new PreguntaDesarrollo(
            texto,
            textoAclaratorio,
            nota
            );

        for (ApartadoDesarrollo apartado : apartados) {
            pregunta.agregarApartado( apartado );
            }

        if (!pregunta.validarPorcentajes()) {
            throw new IllegalArgumentException(
                "La suma de porcentajes debe ser 100."
                );
            }

        return pregunta;
        }
    }
