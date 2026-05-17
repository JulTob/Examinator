# Memoria De La Practica 2

## Resumen

La practica se ha implementado como una aplicacion de consola en Java.
El objetivo principal es crear preguntas,
generar examenes aleatorios,
guardar los datos en fichero
e imprimir examenes con o sin respuestas.

La solucion usa paquetes para separar responsabilidades:

- `p2.dominio`: clases del dominio.
- `p2.dominio.impresion`: interfaz de impresion.
- `p2.generacion`: generacion de examenes.
- `p2.persistencia`: guardado y carga de datos.
- `p2.consola`: menus de consola.
- `p2.dificultad`: ampliacion opcional.

## Clases Principales

`IImprimible` define la impresion simple y completa.
La impresion simple evita respuestas correctas.
La completa se usa para revisar.

`Pregunta` es la clase abstracta comun.
De ella heredan las preguntas teoricas,
test,
rellenar
y desarrollo.

`Asignatura` agrupa preguntas por codigo y titulo.
La aplicacion crea por defecto `POO` y `DADM`.
Tambien permite crear nuevas asignaturas desde el alta de preguntas.

`Examen` guarda la cabecera del examen
y las preguntas ya seleccionadas y puntuadas.

`GeneradorExamen` aplica las reglas del enunciado:
seleccion aleatoria,
tipos compatibles
y reparto de puntos.

`RepositorioPreguntas` y `RepositorioExamenes` centralizan la persistencia.
Las preguntas se guardan por asignatura en:

```text
files/preguntas/<codigo_asignatura>/preguntas.md
```


```text
files/examenes.dat
```

`ArchivoPreguntasMarkdown` se encarga de transformar el markdown limitado en
objetos de dominio y viceversa.

## Ampliacion

La ampliacion es `SistemaDificultad`.
Cada pregunta tiene dificultad entre `0.0` y `1.0`,
inicialmente `0.5`.

`TestTester` ejecuta sesiones de prueba por asignatura
y se expone como opcion separada en el menú.
No mezcla asignaturas porque la maestria del sujeto tester
solo tiene sentido dentro de una materia concreta.

`SujetoTester` empieza con maestria `0.5`.
La utilidad del tester se calcula con:

```text
X = 4 * M * (1 - M)
```

Asi pesan mas los testers de maestria media
y pesan menos los extremos.

## Problemas Encontrados

El enunciado habla de nota de pregunta,
pero no pide corregir examenes de alumnos.
Por eso la aplicacion no calcula calificaciones finales.
Solo reparte la puntuacion del examen generado.

Para evitar librerias externas innecesarias,
la persistencia de exámenes se ha hecho con `Serializable`,
y la de preguntas con markdown legible y editable.

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

Generar JavaDoc:

```bash
make javadoc
```


# Bitácora 
> Development logs.

- El modelo se decide con los modulos principales.
- Se revisa para asegurar completitud con el PDF.
  - Ampliaciones: herencias de `Pregunta`.
  - Ampliación acordada con Desi:
    evaluar la dificultad de las preguntas.
    - Se determina un modelo sencillo paralelo a la funcionalidad general. 
    - Para evitar guardar todas las respuestas
      y llevar una contabilidad general,
      se usa actualización por pre-testing.
    - Inspirado en modelos de entropía de Shannon,
      pero simplificado.
    - Se determina por experimento:
      un sujeto contesta preguntas
      o evalúa su dificultad de manera subjetiva.
      - Determina la validez informativa del candidato
        por su maestría en la materia.
      - Un buen candidato sería un alumno
        que se espera que apruebe justo,
        sin llegar a maestría total.
      - Un candidato que no sabe nada
        tampoco sirve como buena medida.
      - Se compara el nivel de maestria a la pregunta:
        - Si alguien con más maestria falla, sube la dificultad.
        - Si alguien con menos maestria acierta, baja.
        - El salto se pondera según la validez del candidato. 
    - Después actualiza los valores 
  
### Implementación de clase Pregunta
  - Problema: Getters polimorficos con diferente return type no se resuelven por tipo de asignación. GetNota [float] se corrige. Deberá castearse fuera.
  - 




## Bibliografía de Consulta y Asistencia IA

Se ha utilizado un sistema de small-ML disponible por Google: NotebookLM. 
Este Modelo permite consultar fuentes bibliográficas específicas 
sin salir de las fuentes. 

Gracias a esto podemos controlar los parámetros de diseño 
sin introducir vulnerabilidades, y pudiendo consultar gracias a 
su sistema de referencias las fuentes originales para su coherencia. 

El modelo ha complementado la creación de 
las preguntas gracias a sus generadores de material de estudio. 

También, bajo revisión estricta de las fuentes, se ha utilizado para consultar 
la metodología y la consulta de librerías auxiliares. 

Las fuentes bibliográficas han sido las siguientes:
- Java 23 for Absolute Beginners, _Iuliana Cosmina_. ISBN-13 (pbk): 979-8-8688-1040-4 ISBN-13 (electronic): 979-8-8688-1041-1 https://doi.org/10.1007/979-8-8688-1041-1
- JAVA ESSENTIALS VOLUME 2: OBJECT-ORIENTED PROGRAMMING AND BEYOND - _Lawrence G. Decamora III_. ISBN 13: 978-1-63651-657-8
- Wicked Cool Java - _Brian D. Eubanks_ ISBN: 1-59327-061-5
- Java (Tutorials Point) - Website.
