package p2.persistencia.markdown;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import p2.dominio.preguntas.ApartadoDesarrollo;
import p2.dominio.preguntas.OpcionRespuesta;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaDesarrollo;
import p2.dominio.preguntas.PreguntaOpciones;
import p2.dominio.preguntas.PreguntaRellenar;
import p2.dominio.preguntas.PreguntaTeorica;
import p2.dominio.preguntas.PreguntaVerdaderoFalso;
import p2.dominio.preguntas.TipoPregunta;
import p2.persistencia.ArchivoPreguntas;

/**
 * Implementación de persistencia en markdown limitado.
 */
public class ArchivoPreguntasMarkdown implements ArchivoPreguntas {

    @Override
    public List<Pregunta> leer(Path ruta) throws IOException {
        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        List<String> lineas =
            Files.readAllLines(
                ruta,
                StandardCharsets.UTF_8
            );

        List<Pregunta> preguntas = new ArrayList<>();
        List<String> bloque = new ArrayList<>();

        for (String linea : lineas) {
            if (linea.trim().equals("---")) {
                if (!bloque.isEmpty()) {
                    preguntas.add(
                        parsearBloque(
                            bloque
                        )
                    );
                    bloque.clear();
                }

                continue;
            }

            bloque.add(linea);
        }

        if (!bloque.isEmpty()) {
            preguntas.add(
                parsearBloque(
                    bloque
                )
            );
        }

        return preguntas;
    }

    @Override
    public void escribir(
            Path ruta,
            List<Pregunta> preguntas
            ) throws IOException {

        List<String> salida = new ArrayList<>();

        for (Pregunta pregunta : preguntas) {
            salida.add("---");
            salida.add(
                "> tipo: " + pregunta.getTipoPregunta().name()
            );
            salida.add(
                "> nota: " + String.format(
                    Locale.US,
                    "%.4f",
                    pregunta.getNota()
                )
            );
            salida.add(
                "> dificultad: " + String.format(
                    Locale.US,
                    "%.4f",
                    pregunta.getDificultad()
                )
            );

            if (pregunta instanceof p2.dominio.preguntas.PreguntaTest) {
                p2.dominio.preguntas.PreguntaTest preguntaTest =
                    (p2.dominio.preguntas.PreguntaTest) pregunta;
                salida.add(
                    "> penalizacion: " + String.format(
                        Locale.US,
                        "%.4f",
                        preguntaTest.getPenalizacion()
                    )
                );
            } else {
                salida.add("> penalizacion: 0.0");
            }

            salida.add("# " + pregunta.getTexto());

            if (!pregunta.getTextoAclaratorio().isBlank()) {
                salida.add("## " + pregunta.getTextoAclaratorio());
            }

            escribirDetallePregunta(
                pregunta,
                salida
            );
        }

        if (!salida.isEmpty()) {
            salida.add("---");
        }

        Files.write(
            ruta,
            salida,
            StandardCharsets.UTF_8
        );
    }

    private Pregunta parsearBloque(List<String> bloque) {
        Map<String, String> metadatos = new HashMap<>();
        String texto = "";
        String textoAclaratorio = "";
        List<String> lineasCorrectas = new ArrayList<>();
        List<String> lineasAlternativas = new ArrayList<>();

        for (String lineaOriginal : bloque) {
            String linea = lineaOriginal.trim();
            if (linea.isEmpty()) {
                continue;
            }

            if (linea.startsWith(">")) {
                parsearMetadato(
                    linea,
                    metadatos
                );
                continue;
            }

            if (linea.startsWith("##")) {
                textoAclaratorio =
                    linea.substring(2).trim();
                continue;
            }

            if (linea.startsWith("#")) {
                texto = linea.substring(1).trim();
                continue;
            }

            if (linea.startsWith("*")) {
                lineasCorrectas.add(
                    linea.substring(1).trim()
                );
                continue;
            }

            if (linea.startsWith("-")) {
                lineasAlternativas.add(
                    linea.substring(1).trim()
                );
            }
        }

        TipoPregunta tipoPregunta =
            TipoPregunta.valueOf(
                obtenerMetadatoObligatorio(
                    metadatos,
                    "tipo"
                ).toUpperCase()
            );

        double nota =
            parsearDouble(
                metadatos.getOrDefault("nota", "1.0")
            );
        double dificultad =
            parsearDouble(
                metadatos.getOrDefault("dificultad", "0.5")
            );
        double penalizacion =
            parsearDouble(
                metadatos.getOrDefault("penalizacion", "0.0")
            );

        return crearPregunta(
            tipoPregunta,
            texto,
            textoAclaratorio,
            nota,
            dificultad,
            penalizacion,
            lineasCorrectas,
            lineasAlternativas
        );
    }

    private void parsearMetadato(
            String linea,
            Map<String, String> metadatos
            ) {

        String sinPrefijo = linea.substring(1).trim();
        int separador = sinPrefijo.indexOf(':');
        if (separador < 0) {
            return;
        }

        String clave = sinPrefijo.substring(0, separador).trim().toLowerCase();
        String valor = sinPrefijo.substring(separador + 1).trim();
        metadatos.put(clave, valor);
    }

    private String obtenerMetadatoObligatorio(
            Map<String, String> metadatos,
            String nombre
            ) {
        String valor = metadatos.get(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                "Falta metadato obligatorio: " + nombre
            );
        }
        return valor;
    }

    private double parsearDouble(String valor) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                "No se pudo parsear el valor numerico: " + valor,
                excepcion
            );
        }
    }

    private Pregunta crearPregunta(
            TipoPregunta tipoPregunta,
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            double penalizacion,
            List<String> lineasCorrectas,
            List<String> lineasAlternativas
            ) {

        switch (tipoPregunta) {
            case TEORICA:
                return new PreguntaTeorica(
                    texto,
                    textoAclaratorio,
                    nota,
                    dificultad,
                    primeraLineaObligatoria(
                        lineasCorrectas,
                        "respuesta correcta de teoria"
                    )
                );
            case VERDADERO_FALSO:
                return new PreguntaVerdaderoFalso(
                    texto,
                    textoAclaratorio,
                    nota,
                    dificultad,
                    penalizacion,
                    parsearVerdaderoFalso(
                        primeraLineaObligatoria(
                            lineasCorrectas,
                            "respuesta verdadero/falso"
                        )
                    )
                );
            case OPCIONES:
                return crearPreguntaOpciones(
                    texto,
                    textoAclaratorio,
                    nota,
                    dificultad,
                    penalizacion,
                    lineasCorrectas,
                    lineasAlternativas
                );
            case RELLENAR:
                return crearPreguntaRellenar(
                    texto,
                    textoAclaratorio,
                    nota,
                    dificultad,
                    lineasCorrectas
                );
            case DESARROLLO:
                return crearPreguntaDesarrollo(
                    texto,
                    textoAclaratorio,
                    nota,
                    dificultad,
                    lineasCorrectas,
                    lineasAlternativas
                );
            default:
                throw new IllegalArgumentException(
                    "Tipo de pregunta no soportado: " + tipoPregunta
                );
        }
    }

    private Pregunta crearPreguntaOpciones(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            double penalizacion,
            List<String> lineasCorrectas,
            List<String> lineasAlternativas
            ) {

        PreguntaOpciones pregunta = new PreguntaOpciones(
            texto,
            textoAclaratorio,
            nota,
            dificultad,
            penalizacion
        );

        for (String opcionIncorrecta : lineasAlternativas) {
            pregunta.agregarOpcion(
                new OpcionRespuesta(
                    opcionIncorrecta,
                    false
                )
            );
        }

        for (String opcionCorrecta : lineasCorrectas) {
            pregunta.agregarOpcion(
                new OpcionRespuesta(
                    opcionCorrecta,
                    true
                )
            );
        }

        if (!pregunta.validarOpciones()) {
            throw new IllegalArgumentException(
                "Pregunta de opciones invalida: se necesitan al menos 2 opciones y una correcta."
            );
        }

        return pregunta;
    }

    private Pregunta crearPreguntaRellenar(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            List<String> lineasCorrectas
            ) {

        String fraseConHuecos =
            resolverFraseConHuecos(
                texto,
                textoAclaratorio
            );

        return new PreguntaRellenar(
            texto,
            textoAclaratorio,
            nota,
            dificultad,
            fraseConHuecos,
            lineasCorrectas
        );
    }

    private Pregunta crearPreguntaDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad,
            List<String> lineasCorrectas,
            List<String> lineasAlternativas
            ) {

        PreguntaDesarrollo pregunta = new PreguntaDesarrollo(
            texto,
            textoAclaratorio,
            nota,
            dificultad
        );

        List<String> apartados = new ArrayList<>();
        apartados.addAll(lineasAlternativas);
        apartados.addAll(lineasCorrectas);

        if (apartados.isEmpty()) {
            apartados.add("Desarrollo general.");
        }

        double porcentaje = 100.0 / apartados.size();

        for (String textoApartado : apartados) {
            pregunta.agregarApartado(
                new ApartadoDesarrollo(
                    limpiarTextoApartado(textoApartado),
                    extraerPorcentajeO(
                        textoApartado,
                        porcentaje
                    )
                )
            );
        }

        return pregunta;
    }

    private String resolverFraseConHuecos(
            String texto,
            String textoAclaratorio
            ) {

        if (texto.contains("[?]")) {
            return texto;
        }

        if (textoAclaratorio.contains("[?]")) {
            return textoAclaratorio;
        }

        throw new IllegalArgumentException(
            "La pregunta de rellenar necesita huecos marcados con [?]."
        );
    }

    private String primeraLineaObligatoria(
            List<String> lineas,
            String etiqueta
            ) {
        if (lineas.isEmpty()) {
            throw new IllegalArgumentException(
                "Falta " + etiqueta + "."
            );
        }
        return lineas.get(0);
    }

    private boolean parsearVerdaderoFalso(String valor) {
        String normalizado = valor.trim().toLowerCase();
        if (normalizado.equals("true") || normalizado.equals("verdadero")) {
            return true;
        }
        if (normalizado.equals("false") || normalizado.equals("falso")) {
            return false;
        }

        throw new IllegalArgumentException(
            "Respuesta verdadero/falso invalida: " + valor
        );
    }

    private double extraerPorcentajeO(
            String textoApartado,
            double porcentajePorDefecto
            ) {

        int indiceInicio = textoApartado.lastIndexOf('(');
        int indiceFin = textoApartado.lastIndexOf("%)");

        if (indiceInicio >= 0 && indiceFin > indiceInicio) {
            String valor = textoApartado.substring(
                indiceInicio + 1,
                indiceFin
            ).trim();

            return parsearDouble(valor);
        }

        return porcentajePorDefecto;
    }

    private String limpiarTextoApartado(String textoApartado) {
        int indiceInicio = textoApartado.lastIndexOf('(');
        int indiceFin = textoApartado.lastIndexOf("%)");

        if (indiceInicio >= 0 && indiceFin > indiceInicio) {
            return textoApartado.substring(0, indiceInicio).trim();
        }

        return textoApartado.trim();
    }

    private void escribirDetallePregunta(
            Pregunta pregunta,
            List<String> salida
            ) {

        if (pregunta instanceof PreguntaTeorica) {
            PreguntaTeorica preguntaTeorica = (PreguntaTeorica) pregunta;
            salida.add("* " + preguntaTeorica.getRespuestaCorrecta());
            return;
        }

        if (pregunta instanceof PreguntaVerdaderoFalso) {
            PreguntaVerdaderoFalso preguntaVf = (PreguntaVerdaderoFalso) pregunta;
            salida.add("* " + preguntaVf.isRespuestaCorrecta());
            return;
        }

        if (pregunta instanceof PreguntaOpciones) {
            PreguntaOpciones preguntaOpciones = (PreguntaOpciones) pregunta;
            for (OpcionRespuesta opcion : preguntaOpciones.getOpciones()) {
                if (opcion.correcta()) {
                    salida.add("* " + opcion.texto());
                } else {
                    salida.add("- " + opcion.texto());
                }
            }
            return;
        }

        if (pregunta instanceof PreguntaRellenar) {
            PreguntaRellenar preguntaRellenar = (PreguntaRellenar) pregunta;
            for (String palabra : preguntaRellenar.getPalabrasCorrectas()) {
                salida.add("* " + palabra);
            }
            return;
        }

        if (pregunta instanceof PreguntaDesarrollo) {
            PreguntaDesarrollo preguntaDesarrollo = (PreguntaDesarrollo) pregunta;
            for (ApartadoDesarrollo apartado : preguntaDesarrollo.getApartados()) {
                salida.add(
                    "- "
                        + apartado.getTexto()
                        + " ("
                        + String.format(Locale.US, "%.2f", apartado.getPorcentaje())
                        + "%)"
                );
            }
        }
    }
}
