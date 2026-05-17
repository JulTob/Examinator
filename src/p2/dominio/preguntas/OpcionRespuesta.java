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

    /**
     * Versión del formato de serialización: Java la compara al leer un fichero .dat.
     * Valor fijo para que exámenes guardados signifiquen "misma clase lógica" aunque
     * recompilemos; si cambiamos los campos del record en un cambio incompatible,
     * incrementar este número (o regenerar datos).
     */
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
