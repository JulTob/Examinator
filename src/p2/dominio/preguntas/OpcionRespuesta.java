package p2.dominio.preguntas;

import java.io.Serializable;

import p2.dominio.impresion.IImprimible;

/**
 * Opción de respuesta para una pregunta de opciones.
 */
public class OpcionRespuesta implements IImprimible, Serializable {

    private static final long serialVersionUID = 1L;

    private final String texto;
    private final boolean correcta;

    public OpcionRespuesta(
            String texto,
            boolean correcta
            ) {

        this.texto = validarTexto( texto );

        this.correcta = correcta;
        }

    public String getTexto() {
        return texto;
        }

    public boolean isCorrecta() {
        return correcta;
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
