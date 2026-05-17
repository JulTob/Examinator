package p2.consola.comando;

/**
 * Acción invocable desde el menú principal.
 *
 * @return true si la aplicación debe terminar tras ejecutar el comando
 */
public interface Comando {

    boolean ejecutar();
    }
