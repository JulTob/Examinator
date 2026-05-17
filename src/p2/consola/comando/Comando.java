package p2.consola.comando;

/**
 * Una opción del menú principal de la aplicación de consola.
 *
 * @return {@code true} si el programa debe terminar después de ejecutarla;
 *         {@code false} si debe seguir mostrando el menú principal.
 */
public interface Comando {

    boolean ejecutar();
}
