package p2.consola;

import java.util.Scanner;

import p2.dificultad.LectorRespuestas;

/**
 * Entrada y salida compartida por todas las vistas de consola.
 *
 * <p>Centraliza lectura tipada y seleccion de enumerados para no duplicar validaciones
 * en cada submenu. Implementa {@link p2.dificultad.LectorRespuestas} para que la capa de
 * dificultad dependa del contrato y no del tipo concreto {@link Scanner}.</p>
 */
public abstract class ConsolaBase implements LectorRespuestas {

    protected final Scanner scanner;

    /**
     * @param scanner entrada estandar compartida por todas las vistas de la aplicacion
     */
    protected ConsolaBase(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException(
                "El scanner de consola es obligatorio."
            );
        }

        this.scanner = scanner;
    }

    @Override
    public String leerLinea(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /** Lee una linea y elimina espacios al inicio y al final. */
    protected String leerTexto(String mensaje) {
        return leerLinea(mensaje).trim();
    }

    /** Repite la peticion hasta obtener texto no vacio. */
    protected String leerTextoObligatorio(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);

            if (!valor.isBlank()) {
                return valor;
            }

            System.out.println("El valor no puede estar vacio.");
        }
    }

    /** Solicita un entero hasta que la entrada sea numerica. */
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

    /** Solicita un numero decimal hasta que la entrada sea valida. */
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

    @Override
    public boolean leerBooleano(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje).toLowerCase();

            if (valor.equals("true")
                    || valor.equals("t")
                    || valor.equals("verdadero")
                    || valor.equals("v")
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

    @Override
    public int leerEnteroEnRango(
            String mensaje,
            int minimo,
            int maximo
    ) {

        while (true) {
            String valor = leerTexto(mensaje);

            try {
                int numero = Integer.parseInt(valor);

                if (numero >= minimo
                        && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException excepcion) {
                // Reintento.
            }

            System.out.println(
                "Entrada no valida. Debe estar entre "
                    + minimo
                    + " y "
                    + maximo
                    + "."
            );
        }
    }

    /**
     * Lista valores de un enumerado numerados y devuelve el elegido.
     *
     * @param titulo encabezado mostrado antes de la lista
     * @param valores conjunto a ofrecer (tipicamente {@code EnumType.values()})
     * @param mensajeSeleccion prompt tras el cual el usuario introduce el indice
     */
    protected <E extends Enum<E>> E seleccionarEnumerado(
            String titulo,
            E[] valores,
            String mensajeSeleccion
    ) {

        System.out.println();
        System.out.println(titulo);

        for (int i = 0; i < valores.length; i++) {
            System.out.println(
                (i + 1)
                    + ". "
                    + valores[i]
            );
        }

        int opcion = leerEntero(mensajeSeleccion);

        if (opcion < 1
                || opcion > valores.length) {
            throw new IllegalArgumentException(
                "Opcion no valida."
            );
        }

        return valores[opcion - 1];
    }

    /**
     * Ejecuta una accion de menu y muestra el mensaje de error
     * sin cerrar el submenu actual.
     */
    protected void ejecutarConManejoErrores(Runnable accion) {
        try {
            accion.run();
        } catch (RuntimeException excepcion) {
            System.out.println(
                "Error: "
                    + excepcion.getMessage()
            );
        }
    }

    //-- Liberar el scanner solo al cierre definitivo de la app;
    //   comparte System.in y todas las vistas usan la misma instancia.
    protected void cerrar() {
        scanner.close();
    }
}
