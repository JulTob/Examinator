package p2.dificultad;

/**
 * Contrato de lectura para sesiones de TestTester sin acoplar a Scanner.
 */
public interface LectorRespuestas {

    String leerLinea(String mensaje);

    boolean leerBooleano(String mensaje);

    int leerEnteroEnRango(
            String mensaje,
            int minimo,
            int maximo
            );
    }
