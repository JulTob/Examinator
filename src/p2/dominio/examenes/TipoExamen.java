package p2.dominio.examenes;

import p2.dominio.preguntas.TipoPregunta;

/**
 * Tipos de examen y reglas de compatibilidad de preguntas.
 */
public enum TipoExamen {
    TEORICO,
    TEST,
    PRACTICO,
    MIXTO;

    public boolean admite(TipoPregunta tipoPregunta) {
        if (tipoPregunta == null) {
            return false;
        }

        switch (this) {
            case TEORICO:
                return tipoPregunta == TipoPregunta.TEORICA;
            case TEST:
                return tipoPregunta == TipoPregunta.VERDADERO_FALSO
                    || tipoPregunta == TipoPregunta.OPCIONES
                    || tipoPregunta == TipoPregunta.RELLENAR;
            case PRACTICO:
                return tipoPregunta == TipoPregunta.DESARROLLO;
            case MIXTO:
                return true;
            default:
                return false;
        }
    }

    public static TipoExamen desdeTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                "El tipo de examen es obligatorio."
            );
        }

        return TipoExamen.valueOf(
            valor.trim().toUpperCase()
        );
    }
}
