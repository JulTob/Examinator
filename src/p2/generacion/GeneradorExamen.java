package p2.generacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import p2.dominio.asignaturas.Asignatura;
import p2.dominio.examenes.Convocatoria;
import p2.dominio.examenes.Examen;
import p2.dominio.examenes.TipoExamen;
import p2.dominio.preguntas.ApartadoDesarrollo;
import p2.dominio.preguntas.OpcionRespuesta;
import p2.dominio.preguntas.Pregunta;
import p2.dominio.preguntas.PreguntaDesarrollo;
import p2.dominio.preguntas.PreguntaOpciones;
import p2.dominio.preguntas.PreguntaRellenar;
import p2.dominio.preguntas.PreguntaTeorica;
import p2.dominio.preguntas.PreguntaVerdaderoFalso;
import p2.dominio.preguntas.TipoPregunta;

/**
 * Servicio de generación de exámenes según reglas del enunciado.
 */
public class GeneradorExamen {

    public static final double PUNTUACION_MAXIMA_POR_DEFECTO = 10.0;

    private final Random random;

    public GeneradorExamen() {
        this(new Random());
    }

    public GeneradorExamen(Random random) {
        if (random == null) {
            throw new IllegalArgumentException(
                "El generador aleatorio no puede ser nulo."
            );
        }

        this.random = random;
    }

    public Examen generarExamen(
            String realizadoPor,
            Asignatura asignatura,
            Convocatoria convocatoria,
            String curso,
            TipoExamen tipoExamen,
            int numeroPreguntas
            ) {

        return generarExamen(
            realizadoPor,
            asignatura,
            convocatoria,
            curso,
            tipoExamen,
            numeroPreguntas,
            PUNTUACION_MAXIMA_POR_DEFECTO
        );
    }

    public Examen generarExamen(
            String realizadoPor,
            Asignatura asignatura,
            Convocatoria convocatoria,
            String curso,
            TipoExamen tipoExamen,
            int numeroPreguntas,
            double puntuacionMaxima
            ) {

        validarNumeroPreguntas(numeroPreguntas);

        List<Pregunta> bancoCompatible =
            asignatura.getPreguntasPorTipo(tipoExamen);

        List<Pregunta> seleccionadas =
            seleccionarPreguntas(
                tipoExamen,
                bancoCompatible,
                numeroPreguntas
            );

        repartirPuntuacion(
            tipoExamen,
            seleccionadas,
            puntuacionMaxima
        );

        return new Examen(
            realizadoPor,
            asignatura,
            convocatoria,
            curso,
            tipoExamen,
            puntuacionMaxima,
            seleccionadas
        );
    }

    private void validarNumeroPreguntas(int numeroPreguntas) {
        if (numeroPreguntas <= 0) {
            throw new IllegalArgumentException(
                "El numero de preguntas debe ser mayor que 0."
            );
        }
    }

    private List<Pregunta> seleccionarPreguntas(
            TipoExamen tipoExamen,
            List<Pregunta> bancoCompatible,
            int numeroPreguntas
            ) {

        if (bancoCompatible.size() < numeroPreguntas) {
            throw new IllegalArgumentException(
                "No hay suficientes preguntas compatibles para generar el examen."
            );
        }

        if (tipoExamen == TipoExamen.MIXTO) {
            return seleccionarMixto(
                bancoCompatible,
                numeroPreguntas
            );
        }

        return seleccionarSinRepetidos(
            bancoCompatible,
            numeroPreguntas
        );
    }

    private List<Pregunta> seleccionarMixto(
            List<Pregunta> bancoCompatible,
            int numeroPreguntas
            ) {

        if (numeroPreguntas < 2) {
            throw new IllegalArgumentException(
                "Un examen mixto necesita al menos 2 preguntas."
            );
        }

        List<Pregunta> desarrollos = new ArrayList<>();

        for (Pregunta pregunta : bancoCompatible) {
            if (pregunta.getTipoPregunta() == TipoPregunta.DESARROLLO) {
                desarrollos.add(pregunta);
            }
        }

        if (desarrollos.isEmpty()) {
            throw new IllegalArgumentException(
                "No hay preguntas de desarrollo para cumplir la regla del examen mixto."
            );
        }

        Pregunta desarrolloBase =
            desarrollos.get(
                random.nextInt(desarrollos.size())
            );

        List<Pregunta> restantes = new ArrayList<>(bancoCompatible);
        restantes.remove(desarrolloBase);

        List<Pregunta> seleccion = new ArrayList<>();
        seleccion.add(clonarPregunta(desarrolloBase));
        seleccion.addAll(
            seleccionarSinRepetidos(
                restantes,
                numeroPreguntas - 1
            )
        );

        return seleccion;
    }

    private List<Pregunta> seleccionarSinRepetidos(
            List<Pregunta> preguntas,
            int cantidad
            ) {

        List<Pregunta> copia = new ArrayList<>(preguntas);
        Collections.shuffle(copia, random);

        List<Pregunta> seleccion = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            seleccion.add(
                clonarPregunta(
                    copia.get(i)
                )
            );
        }

        return seleccion;
    }

    private void repartirPuntuacion(
            TipoExamen tipoExamen,
            List<Pregunta> preguntas,
            double puntuacionMaxima
            ) {

        if (tipoExamen == TipoExamen.MIXTO) {
            repartirPuntuacionMixto(
                preguntas,
                puntuacionMaxima
            );
            return;
        }

        double notaUniforme = puntuacionMaxima / preguntas.size();

        for (Pregunta pregunta : preguntas) {
            pregunta.setNota(notaUniforme);
        }
    }

    private void repartirPuntuacionMixto(
            List<Pregunta> preguntas,
            double puntuacionMaxima
            ) {

        Pregunta preguntaDesarrollo = buscarDesarrollo(preguntas);
        if (preguntaDesarrollo == null) {
            throw new IllegalStateException(
                "No se encontro pregunta de desarrollo en examen mixto."
            );
        }

        double notaDesarrollo = puntuacionMaxima * 0.4;
        double notaResto = (puntuacionMaxima * 0.6) / (preguntas.size() - 1);

        preguntaDesarrollo.setNota(notaDesarrollo);

        for (Pregunta pregunta : preguntas) {
            if (pregunta == preguntaDesarrollo) {
                continue;
            }

            pregunta.setNota(notaResto);
        }
    }

    private Pregunta buscarDesarrollo(List<Pregunta> preguntas) {
        for (Pregunta pregunta : preguntas) {
            if (pregunta.getTipoPregunta() == TipoPregunta.DESARROLLO) {
                return pregunta;
            }
        }

        return null;
    }

    private Pregunta clonarPregunta(Pregunta original) {
        if (original instanceof PreguntaTeorica) {
            PreguntaTeorica pregunta = (PreguntaTeorica) original;

            return new PreguntaTeorica(
                pregunta.getTexto(),
                pregunta.getTextoAclaratorio(),
                pregunta.getNota(),
                pregunta.getDificultad(),
                pregunta.getRespuestaCorrecta()
            );
        }

        if (original instanceof PreguntaVerdaderoFalso) {
            PreguntaVerdaderoFalso pregunta = (PreguntaVerdaderoFalso) original;

            return new PreguntaVerdaderoFalso(
                pregunta.getTexto(),
                pregunta.getTextoAclaratorio(),
                pregunta.getNota(),
                pregunta.getDificultad(),
                pregunta.getPenalizacion(),
                pregunta.isRespuestaCorrecta()
            );
        }

        if (original instanceof PreguntaOpciones) {
            PreguntaOpciones pregunta = (PreguntaOpciones) original;

            PreguntaOpciones copia = new PreguntaOpciones(
                pregunta.getTexto(),
                pregunta.getTextoAclaratorio(),
                pregunta.getNota(),
                pregunta.getDificultad(),
                pregunta.getPenalizacion()
            );

            for (OpcionRespuesta opcion : pregunta.getOpciones()) {
                copia.agregarOpcion(
                    new OpcionRespuesta(
                        opcion.getTexto(),
                        opcion.isCorrecta()
                    )
                );
            }

            return copia;
        }

        if (original instanceof PreguntaRellenar) {
            PreguntaRellenar pregunta = (PreguntaRellenar) original;

            return new PreguntaRellenar(
                pregunta.getTexto(),
                pregunta.getTextoAclaratorio(),
                pregunta.getNota(),
                pregunta.getDificultad(),
                pregunta.getFraseConHuecos(),
                pregunta.getPalabrasCorrectas()
            );
        }

        if (original instanceof PreguntaDesarrollo) {
            PreguntaDesarrollo pregunta = (PreguntaDesarrollo) original;

            PreguntaDesarrollo copia = new PreguntaDesarrollo(
                pregunta.getTexto(),
                pregunta.getTextoAclaratorio(),
                pregunta.getNota(),
                pregunta.getDificultad()
            );

            for (ApartadoDesarrollo apartado : pregunta.getApartados()) {
                copia.agregarApartado(
                    new ApartadoDesarrollo(
                        apartado.getTexto(),
                        apartado.getPorcentaje()
                    )
                );
            }

            return copia;
        }

        throw new IllegalArgumentException(
            "Tipo de pregunta no soportado para clonado: " + original.getClass().getName()
        );
    }
}
