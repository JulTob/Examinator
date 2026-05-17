package p2.app;

import p2.consola.AplicacionConsola;

/**
 * Punto de entrada del programa.
 */
public final class Main {

    private Main() {
        }

    public static void main(String[] args) {
        AplicacionConsola aplicacion = new AplicacionConsola();
        aplicacion.ejecutar();
        }


    }
