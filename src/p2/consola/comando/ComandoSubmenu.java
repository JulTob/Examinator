package p2.consola.comando;

/**
 * Abre un submenú de consola sin cerrar la aplicación.
 */
public class ComandoSubmenu implements Comando {

    private final Runnable accion;

    public ComandoSubmenu(Runnable accion) {
        if (accion == null) {
            throw new IllegalArgumentException(
                "La accion del submenú es obligatoria."
            );
        }

        this.accion = accion;
    }

    @Override
    public boolean ejecutar() {
        accion.run();
        return false;
    }
}
