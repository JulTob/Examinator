package p2.dominio.preguntas;

import java.io.Serializable;

import p2.dominio.impresion.IImprimible;

/**
 * Opción de respuesta para una pregunta de opciones.
 */
public record OpcionRespuesta(
        String texto,
        boolean correcta
        ) implements IImprimible, Serializable {

    //-- Intención: al persistir grafos de examen (.dat),
    //   reabrir la misma pieza del modelo (opción de respuesta) tras recompilar.
    private static final long serialVersionUID = 1L;

    public OpcionRespuesta {
        texto = validarTexto( texto );
        }

    @Override
    public String imprimirSimple() {
        return "- " + texto;
        }

    @Override
    public String imprimirCompleto() {
        if (correcta) {
            return "* "
                + texto
                + " [correcta]";
                }

        return "- " + texto;
        }

    private static String validarTexto(String texto) {
        if (texto == null
                || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "El texto de la opcion es obligatorio."
                    );
            }

        return texto.trim();
        }
    }
