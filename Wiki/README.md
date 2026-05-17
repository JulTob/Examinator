# Practica 2

Generador de examenes aleatorios para la asignatura de Programacion
Orientada a Objetos.

## Estado De Entrega

La practica incluye:

- alta de preguntas por asignatura;
- alta de examenes;
- listado de examenes guardados;
- impresion sin respuestas;
- impresion con respuestas;
- persistencia en fichero;
- paquetes Java separados por responsabilidad;
- JavaDoc generado;
- ampliacion opcional `SistemaDificultad`.

## Estructura

```text
src/p2/
  app/              Punto de entrada y coordinación general
  dominio/          Modelo puro del problema
    asignaturas/
    examenes/
    preguntas/
    impresion/
  generacion/       Reglas para crear exámenes
  persistencia/     Lectura/escritura de datos
    markdown/
  consola/          Interacción con usuario
  dificultad/       Ampliación opcional
  pruebas/          Pruebas simples
files/              Datos editables/persistidos
docs/javadoc/       JavaDoc generado
```

## Datos Incluidos

Existen dos asignaturas por defecto:

- `POO` - `Programacion Orientada a Objetos`
- `DADM` - `Desarrollo de aplicaciones para dispositivos moviles`

Agregadas dos asignaturas más de ejemplos:
- `CALC` - `Cálculo`
-`STAT`- `Estadística`

Las preguntas se guardan por carpeta de asignatura:

```text
files/preguntas/POO/preguntas.md
files/preguntas/DADM/preguntas.md
```

También se incluye persistencia de exámenes en:

```text
files/examenes.dat
```

## Ejecucion

Compilar:

```bash
make compile
```

Ejecutar:

```bash
make run
```

Pruebas rapidas:

```bash
make test
```

La opcion de ampliacion de dificultad se lanza desde el menú principal de la app.

Generar JavaDoc:

```bash
make javadoc
```

## Documentos

- `ENUNCIADO_P2_PDF(copypasted).md`: enunciado usado como fuente.
- `REQUISITOS.md`: requisitos extraidos.
- `MODELO.md`: modelo de dominio y ampliacion.
- `MEMORIA.md`: memoria breve para la entrega.
- `STYLEGUIDE.md`: decisiones de estilo.

