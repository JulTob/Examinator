package p2.persistencia;

import p2.dominio.asignaturas.Asignatura;

/**
 * Coordina el guardado al cierre de todos los datos del sistema.
 */
public class ServicioPersistencia {

    private final RepositorioPreguntas repoPreguntas;
    private final RepositorioExamenes repoExamenes;
    private final Iterable<Asignatura> asignaturas;

    public ServicioPersistencia(
            RepositorioPreguntas repoPreguntas,
            RepositorioExamenes repoExamenes,
            Iterable<Asignatura> asignaturas
            ) {

        if (repoPreguntas == null) {
            throw new IllegalArgumentException(
                "El repositorio de preguntas es obligatorio."
            );
        }
        if (repoExamenes == null) {
            throw new IllegalArgumentException(
                "El repositorio de examenes es obligatorio."
            );
        }
        if (asignaturas == null) {
            throw new IllegalArgumentException(
                "Las asignaturas en memoria son obligatorias."
            );
        }

        this.repoPreguntas = repoPreguntas;
        this.repoExamenes = repoExamenes;
        this.asignaturas = asignaturas;
    }

    //-- Intención: volcar al salir preguntas (markdown) y examenes (.dat) coherentes en disco.
    public void guardarTodo() {
        repoPreguntas.guardarTodas(asignaturas);
        repoExamenes.guardarTodos(
            repoExamenes.cargarTodos()
            );
        System.out.println(
            "Preguntas (markdown) y examenes (.dat) guardados."
            );
    }
    }
