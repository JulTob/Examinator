package p2.dificultad;

/**
 * Persona que participa en una sesión de calibración de dificultad.
 */
public class SujetoTester {

    private final String nombre;
    private double maestria;
    private int preguntasRespondidas;
    private int preguntasAcertadas;

    public SujetoTester(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del sujeto tester es obligatorio."
            );
        }

        this.nombre = nombre.trim();
        this.maestria = 0.5;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMaestria() {
        return maestria;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public int getPreguntasAcertadas() {
        return preguntasAcertadas;
    }

    public void registrarAcierto() {
        preguntasRespondidas++;
        preguntasAcertadas++;
        recalcularMaestria();
    }

    public void registrarFallo() {
        preguntasRespondidas++;
        recalcularMaestria();
    }

    private void recalcularMaestria() {
        if (preguntasRespondidas == 0) {
            maestria = 0.5;
            return;
        }

        maestria = (double) preguntasAcertadas / preguntasRespondidas;
    }
}
