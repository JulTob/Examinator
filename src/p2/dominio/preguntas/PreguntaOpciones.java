package p2.dominio.preguntas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pregunta tipo test con varias opciones de respuesta.
 */
public class PreguntaOpciones extends PreguntaTest {

    private final List<OpcionRespuesta> opciones;

    public PreguntaOpciones(
            String texto,
            String textoAclaratorio,
            double nota,
            double penalizacion
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                penalizacion
                );

        this.opciones = new ArrayList<>();
        }

    public PreguntaOpciones(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            double penalizacion
            ) {

        super(  texto,
                textoAclaratorio,
                nota,
                dificultad,
                penalizacion
                );

        this.opciones = new ArrayList<>();
        }

    public List<OpcionRespuesta> getOpciones() {
        return Collections.unmodifiableList( opciones );
        }

    public void agregarOpcion(OpcionRespuesta opcion) {
        if (opcion == null) {
            throw new IllegalArgumentException(
                    "La opcion no puede ser nula."
                );
            }

        opciones.add( opcion );
        }

    public boolean validarOpciones() {
        return opciones.size() >= 2
            && tieneOpcionCorrecta();
        }

    @Override
    public TipoPregunta getTipoPregunta() {
        return TipoPregunta.OPCIONES;
        }

    @Override
    public String imprimirSimple() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirEnunciado());
        builder.append(System.lineSeparator());

        for (OpcionRespuesta opcion : opciones) {
            builder.append(opcion.imprimirSimple());
            builder.append(System.lineSeparator());
            }

        return builder.toString().trim();
        }

    public String imprimirEnunciado() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());
        builder.append(System.lineSeparator());
        builder.append(imprimirPenalizacion());

        return builder.toString();
        }

    @Override
    public String imprimirCompleto() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());
        builder.append(System.lineSeparator());
        builder.append(imprimirPenalizacion());

        for (OpcionRespuesta opcion : opciones) {
            builder.append(System.lineSeparator());
            builder.append(opcion.imprimirCompleto());
            }

        return builder.toString();
        }

    private boolean tieneOpcionCorrecta() {
        for (OpcionRespuesta opcion : opciones) {
            if (opcion.correcta()) {
                return true;
                }
            }

        return false;
        }

    public int cantidadOpcionesCorrectas() {
        int cantidad = 0;

        for (OpcionRespuesta opcion : opciones) {
            if (opcion.correcta()) {
                cantidad++;
                }
            }

        return cantidad;
        }

    public boolean tieneVariasOpcionesCorrectas() {
        return cantidadOpcionesCorrectas() > 1;
        }

    /**
     * Indica si la opción elegida (numeración 1..N) es correcta.
     */
    public boolean aciertaOpcionSeleccionada(int numeroOpcion) {
        if (numeroOpcion < 1 || numeroOpcion > opciones.size()) {
            return false;
            }

        return opciones.get(numeroOpcion - 1).correcta();
        }

    public String imprimirOpcionesNumeradas() {
        StringBuilder builder = new StringBuilder();
        int numero = 1;

        for (OpcionRespuesta opcion : opciones) {
            builder.append(numero);
            builder.append(". ");
            builder.append(opcion.texto());
            builder.append(System.lineSeparator());
            numero++;
            }

        return builder.toString();
        }
    }
