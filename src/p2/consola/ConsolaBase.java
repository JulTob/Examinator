package p2.consola;

import java.util.Scanner;

/**
 * Funciones comunes para las vistas de consola.
 */
public abstract class ConsolaBase {

    protected final Scanner scanner;

    protected ConsolaBase(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException(
                "El scanner de consola es obligatorio."
            );
        }

        this.scanner = scanner;
    }

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    protected String leerTextoObligatorio(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            if (!valor.isBlank()) {
                return valor;
            }

            System.out.println("El valor no puede estar vacio.");
        }
    }

    protected int leerEntero(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException excepcion) {
                System.out.println("Debes introducir un numero entero.");
            }
        }
    }

    protected double leerDouble(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException excepcion) {
                System.out.println("Debes introducir un numero valido.");
            }
        }
    }

    protected boolean leerBooleano(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje).toLowerCase();

            if (valor.equals("true")
                || valor.equals("t")
                || valor.equals("verdadero")
                || valor.equals("si")
                || valor.equals("s")) {
                return true;
            }

            if (valor.equals("false")
                || valor.equals("f")
                || valor.equals("no")
                || valor.equals("n")
                || valor.equals("falso")) {
                return false;
            }

            System.out.println("Debes introducir true/false (o si/no).");
        }
    }
}
