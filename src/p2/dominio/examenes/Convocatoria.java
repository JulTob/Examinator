package p2.dominio.examenes;

/**
 * Convocatorias válidas para un examen.
 */
public enum Convocatoria {
    JUNIO,
    SEPTIEMBRE,
    DICIEMBRE;

    public static Convocatoria desdeTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                "La convocatoria es obligatoria."
            );
        }

        return Convocatoria.valueOf(
            valor.trim().toUpperCase()
        );
    }
}
