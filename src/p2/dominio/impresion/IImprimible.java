package p2.dominio.impresion;

/**
 * Contrato común para objetos del dominio que pueden representarse por texto.
 *
 * <p>El modo simple muestra la información pública. En preguntas y exámenes,
 * eso significa no mostrar respuestas correctas. El modo completo muestra la
 * información necesaria para revisar el contenido.</p>
 */
public interface IImprimible {

    /**
     * Devuelve una representación pública y reducida del objeto.
     *
     * @return texto sin información privada ni respuestas correctas
     */
    String imprimirSimple();

    /**
     * Devuelve una representación completa del objeto.
     *
     * @return texto con toda la información relevante del objeto
     */
    String imprimirCompleto();

    /**
     * Atajo para el modo seguro por defecto.
     *
     * @return resultado de {@link #imprimirSimple()}
     */
    default String imprimir() {
        return imprimirSimple();
        }

    /**
     * Atajo para elegir el modo de impresión desde la consola.
     *
     * @param incluirRespuestas si es {@code true}, usa el modo completo
     * @return representación simple o completa según el parámetro
     */
    default String imprimir(boolean incluirRespuestas) {
        if (incluirRespuestas) {
            return imprimirCompleto();
            }

        return imprimirSimple();
        }
    }
