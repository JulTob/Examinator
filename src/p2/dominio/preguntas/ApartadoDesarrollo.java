package p2.dominio.preguntas;

import p2.dominio.impresion.IImprimible;

/**
 * Apartado evaluable de una pregunta de desarrollo.
 */
public class ApartadoDesarrollo implements IImprimible {

    private final String texto;
    private final double porcentaje;

    public ApartadoDesarrollo(
            String texto,
            double porcentaje
            ) {

        this.texto =
            validarTexto( texto );

        this.porcentaje =
            validarPorcentaje( porcentaje );
        }

    public String getTexto() {
        return texto;
        }

    public double getPorcentaje() {
        return porcentaje;
        }

    @Override
    public String imprimirSimple() {
        return "- "
            + texto
            + " ("
            + String.format( "%.2f",
                porcentaje
                )
            + "%)";
        }

    @Override
    public String imprimirCompleto() {
        return imprimirSimple();
        }

    private static String validarTexto(String texto) {
        if (texto == null
            || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "El texto del apartado es obligatorio."
                    );
            }

        return texto.trim();
        }

    private static double validarPorcentaje(double porcentaje) {
        if (Pregunta.esMenorQue(
                porcentaje,
                0.0
                )
            || Pregunta.esMayorQue(
                porcentaje,
                100.0
                )) {
            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 0.0 y 100.0."
                    );
            }

        return Pregunta.normalizarRango(
                porcentaje,
                0.0,
                100.0
                );
        }
    }
