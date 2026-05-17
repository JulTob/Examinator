package p2.consola.comando;

import p2.persistencia.GuardadoAlSalir;

/**
 * Persiste datos y cierra la entrada de consola antes de salir del programa.
 */
public class ComandoSalir implements Comando {

    private final GuardadoAlSalir guardadoAlSalir;
    private final Runnable cerrarEntrada;

    public ComandoSalir(
            GuardadoAlSalir guardadoAlSalir,
            Runnable cerrarEntrada
            ) {

        if (guardadoAlSalir == null) {
            throw new IllegalArgumentException(
                "El guardado al salir es obligatorio."
                );
            }
        if (cerrarEntrada == null) {
            throw new IllegalArgumentException(
                "El cierre de entrada es obligatorio."
                );
            }

        this.guardadoAlSalir = guardadoAlSalir;
        this.cerrarEntrada = cerrarEntrada;
        }

    @Override
    public boolean ejecutar() {
        guardadoAlSalir.guardarTodo();
        cerrarEntrada.run();
        return true;
        }
    }
