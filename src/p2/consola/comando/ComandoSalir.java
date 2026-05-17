package p2.consola.comando;

import p2.persistencia.ServicioPersistencia;

/**
 * Persiste datos y cierra la entrada de consola antes de salir del programa.
 */
public class ComandoSalir implements Comando {

    private final ServicioPersistencia servicioPersistencia;
    private final Runnable cerrarEntrada;

    public ComandoSalir(
            ServicioPersistencia servicioPersistencia,
            Runnable cerrarEntrada
    ) {

        if (servicioPersistencia == null) {
            throw new IllegalArgumentException(
                "El servicio de persistencia es obligatorio."
            );
        }
        if (cerrarEntrada == null) {
            throw new IllegalArgumentException(
                "El cierre de entrada es obligatorio."
            );
        }

        this.servicioPersistencia = servicioPersistencia;
        this.cerrarEntrada = cerrarEntrada;
    }

    @Override
    public boolean ejecutar() {
        servicioPersistencia.guardarTodo();
        cerrarEntrada.run();
        return true;
    }
}
