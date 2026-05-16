package p2.dominio.preguntas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pregunta basada en una frase con huecos representados por signos `?`.
 */
public class PreguntaRellenar extends Pregunta {

    private final String fraseConHuecos;
    private final List<String> palabrasCorrectas;

    public PreguntaRellenar(
            String texto,
            String textoAclaratorio,
            double nota,
            String fraseConHuecos,
            List<String> palabrasCorrectas
            ) {

        super( texto,
                textoAclaratorio,
                nota
                );

        this.fraseConHuecos =
            validarFrase( fraseConHuecos );

        this.palabrasCorrectas =
            copiarPalabrasCorrectas(
                    palabrasCorrectas
                );

        validarNumeroDeHuecos();
        }

    public PreguntaRellenar(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            String fraseConHuecos,
            List<String> palabrasCorrectas
            ) {

        super(
            texto,
            textoAclaratorio,
            nota,
            dificultad
            );

        this.fraseConHuecos =
            validarFrase(
                    fraseConHuecos
                );

        this.palabrasCorrectas =
            copiarPalabrasCorrectas(
                    palabrasCorrectas
                );

        validarNumeroDeHuecos();
        }

    public String getFraseConHuecos() {
        return fraseConHuecos;
        }

    public List<String> getPalabrasCorrectas() {
        return Collections.unmodifiableList(
                palabrasCorrectas
            );
        }

    @Override
    public TipoPregunta getTipoPregunta() {
        return TipoPregunta.RELLENAR;
        }

    @Override
    public String imprimirSimple() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());
        builder.append(System.lineSeparator());
        builder.append(fraseConHuecos);

        return builder.toString();
        }

    @Override
    public String imprimirCompleto() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirSimple());
        builder.append(System.lineSeparator());
        builder.append("Palabras correctas:");

        for (String palabra : palabrasCorrectas) {
            builder.append(System.lineSeparator());
            builder.append("* ");
            builder.append(palabra);
            }

        return builder.toString();
        }

    private static String validarFrase(String fraseConHuecos) {
        if (fraseConHuecos == null || fraseConHuecos.isBlank()) {
            throw new IllegalArgumentException(
                    "La frase con huecos es obligatoria."
                );
            }

        return fraseConHuecos.trim();
        }

    private static List<String> copiarPalabrasCorrectas(
            List<String> palabrasCorrectas
            ) {

        if (palabrasCorrectas == null || palabrasCorrectas.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe haber al menos una palabra correcta."
                );
            }

        List<String> copia = new ArrayList<>();

        for (String palabra : palabrasCorrectas) {
            if (palabra == null || palabra.isBlank()) {
                throw new IllegalArgumentException(
                        "Las palabras correctas no pueden estar vacias."
                    );
                }

            copia.add(
                palabra.trim()
                );
            }

        return copia;
        }

    private void validarNumeroDeHuecos() {
        int huecos = contarHuecos();

        if (huecos != palabrasCorrectas.size()) {
            throw new IllegalArgumentException(
                    "El numero de huecos debe coincidir con las palabras correctas."
                );
            }
        }

    private int contarHuecos() {
        int huecos = 0;

        for (int i = 0; i < fraseConHuecos.length(); i++) {
            if (fraseConHuecos.charAt(i) == '?') {
                huecos++;
                }
            }

        return huecos;
        }
    }
