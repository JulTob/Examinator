package p2.dominio.preguntas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pregunta práctica o de desarrollo formada por apartados evaluables.
 */
public class PreguntaDesarrollo extends Pregunta {

    private static final double PORCENTAJE_TOTAL = 100.0;

    private final List<ApartadoDesarrollo> apartados;

    public PreguntaDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota
            ) {

        super(
            texto,
            textoAclaratorio,
            nota
            );

        this.apartados = new ArrayList<>();
        }

    public PreguntaDesarrollo(
            String texto,
            String textoAclaratorio,
            double nota,
            double dificultad
            ) {

        super(
            texto,
            textoAclaratorio,
            nota,
            dificultad
            );

        this.apartados = new ArrayList<>();
        }

    public List<ApartadoDesarrollo> getApartados() {
        return Collections.unmodifiableList(
                apartados
            );
        }

    public void agregarApartado(ApartadoDesarrollo apartado) {
        if (apartado == null) {
            throw new IllegalArgumentException(
                    "El apartado no puede ser nulo."
                );
            }

        apartados.add(apartado);
        }

    public boolean validarPorcentajes() {
        return !apartados.isEmpty()
            && sonEquivalentes(
                    sumaPorcentajes(),
                    PORCENTAJE_TOTAL
                );
        }

    @Override
    public TipoPregunta getTipoPregunta() {
        return TipoPregunta.DESARROLLO;
        }

    @Override
    public String imprimirSimple() {
        StringBuilder builder = new StringBuilder();

        builder.append(imprimirCabecera());

        for (ApartadoDesarrollo apartado : apartados) {
            builder.append(System.lineSeparator());
            builder.append(apartado.imprimirSimple());
            }

        return builder.toString();
        }

    @Override
    public String imprimirCompleto() {
        return imprimirSimple();
        }

    private double sumaPorcentajes() {
        double suma = 0.0;

        for (ApartadoDesarrollo apartado : apartados) {
            suma += apartado.getPorcentaje();
            }

        return suma;
        }
    }
