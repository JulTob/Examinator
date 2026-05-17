package p2.consola;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import p2.consola.comando.Comando;
import p2.consola.comando.ComandoSalir;
import p2.consola.comando.ComandoSubmenu;
import p2.dificultad.SistemaDificultad;
import p2.dificultad.TestTester;
import p2.dominio.asignaturas.Asignatura;
import p2.generacion.GeneradorExamen;
import p2.persistencia.RepositorioExamenes;
import p2.persistencia.RepositorioPreguntas;
import p2.persistencia.ServicioPersistencia;
import p2.persistencia.markdown.ArchivoPreguntasMarkdown;

/**
 * Punto de arranque de la aplicacion por consola.
 *
 * El menu principal agrupa acciones en submenus (Preguntas, Examenes,
 * Tests Dificultad) en lugar de un menu plano con todas las opciones
 * mezcladas. 
 * El enunciado exige crear preguntas, crear examenes, 
 * ver examenes guardados y salir. Esas operaciones siguen disponibles
 * dentro de cada area. Los submenus reducen ruido en pantalla,
 * asignan una vista por responsabilidad y mantienen la ampliacion
 * de dificultad separada del flujo obligatorio.
 */
public class AplicacionConsola extends ConsolaBase {

    private final Map<String, Asignatura> asignaturas;
    private final VistaAsignaturas vistaAsignaturas;
    private final VistaPreguntas vistaPreguntas;
    private final VistaExamenes vistaExamenes;
    private final VistaDificultad vistaDificultad;

    public AplicacionConsola() {
        super(new Scanner(System.in));

        DependenciasConsola dependencias =
            inicializarDependencias(
                scanner
            );
        RepositorioExamenes repositorioExamenes =
            new RepositorioExamenes(
                archivoExamenes
            );
        GeneradorExamen generadorExamen = new GeneradorExamen();
        TestTester testTester = new TestTester(new SistemaDificultad());

        this.asignaturas = dependencias.asignaturas;
        this.repositorioExamenes = dependencias.repositorioExamenes;
        this.vistaAsignaturas = dependencias.vistaAsignaturas;
        this.vistaPreguntas = dependencias.vistaPreguntas;
        this.vistaExamenes = dependencias.vistaExamenes;
        this.vistaDificultad = dependencias.vistaDificultad;

        registrarAsignaturasPorDefecto();
        vistaAsignaturas.detectarAsignaturas();
        vistaAsignaturas.cargarPreguntas();
    }

    public void ejecutar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Selecciona una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        vistaPreguntas.ejecutar();
                        break;
                    case 2:
                        vistaExamenes.ejecutar();
                        break;
                    case 3:
                        vistaDificultad.ejecutar();
                        break;
                    case 4:
                        salir();
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException excepcion) {
                System.out.println("Error: " + excepcion.getMessage());
                }
        }

        System.out.println("Programa finalizado.");
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("===== Generador de Examenes =====");
        System.out.println("1. Preguntas");
        System.out.println("2. Examenes");
        System.out.println("3. Tests Dificultad");
        System.out.println("4. Salir");
    }

    private Map<Integer, Comando> crearComandosMenu(
            DependenciasConsola dependencias
            ) {

        Map<Integer, Comando> comandos = new LinkedHashMap<>();

        comandos.put(
            1,
            new ComandoSubmenu(
                dependencias.vistaPreguntas()::ejecutar
            )
        );
        comandos.put(
            2,
            new ComandoSubmenu(
                dependencias.vistaExamenes()::ejecutar
            )
        );
        comandos.put(
            3,
            new ComandoSubmenu(
                dependencias.vistaDificultad()::ejecutar
            )
        );
        comandos.put(
            4,
            new ComandoSalir(
                dependencias.servicioPersistencia(),
                this::cerrar
            )
        );

        return comandos;
    }

    private DependenciasConsola inicializarDependencias(
            Scanner scannerEntrada
            ) {

        Map<String, Asignatura> mapaAsignaturas =
            new LinkedHashMap<>();

        Path carpetaPreguntas =
            Paths.get(
                "files",
                "preguntas"
            );
        Path archivoExamenes =
            Paths.get(
                "files",
                "examenes.dat"
            );

        RepositorioPreguntas repositorioPreguntas =
            new RepositorioPreguntas(
                carpetaPreguntas,
                new ArchivoPreguntasMarkdown()
                );
        RepositorioExamenes repositorioExamenesLocal =
            new RepositorioExamenes(
                archivoExamenes
                );
        GeneradorExamen generadorExamen =
            new GeneradorExamen();
        TestTester testTester =
            new TestTester(
                new SistemaDificultad()
                );

        VistaAsignaturas vistaAsignaturasLocal =
            new VistaAsignaturas(
                scannerEntrada,
                mapaAsignaturas,
                repositorioPreguntas
                );
        VistaPreguntas vistaPreguntasLocal =
            new VistaPreguntas(
                scannerEntrada,
                vistaAsignaturasLocal,
                repositorioPreguntas
                );
        VistaExamenes vistaExamenesLocal =
            new VistaExamenes(
                scannerEntrada,
                vistaAsignaturasLocal,
                repositorioExamenesLocal,
                generadorExamen
                );
        VistaDificultad vistaDificultadLocal =
            new VistaDificultad(
                scannerEntrada,
                vistaAsignaturasLocal,
                repositorioPreguntas,
                testTester
                );

        return new DependenciasConsola(
            mapaAsignaturas,
            repositorioExamenesLocal,
            vistaAsignaturasLocal,
            vistaPreguntasLocal,
            vistaExamenesLocal,
            vistaDificultadLocal
            );
        }

    private void registrarAsignaturasPorDefecto() {
        asignaturas.put(
            "POO",
            new Asignatura(
                "POO",
                "Programacion Orientada a Objetos"
            )
        );
        asignaturas.put(
            "DADM",
            new Asignatura(
                "DADM",
                "Desarrollo de aplicaciones para dispositivos moviles"
            )
        );
    }

    private record DependenciasConsola(
            Map<String, Asignatura> asignaturas,
            RepositorioExamenes repositorioExamenes,
            VistaAsignaturas vistaAsignaturas,
            VistaPreguntas vistaPreguntas,
            VistaExamenes vistaExamenes,
            VistaDificultad vistaDificultad
            ) {
        }
    }
