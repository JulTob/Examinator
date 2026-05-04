# Modelo De Dominio

Documento de diseño del modelo de la Practica 2.

El modelo se mantiene dividido en piezas pequeñas,
legibles y fáciles de revisar.

## Criterios De Diseño
- Las relaciones entre clases se documentan en diagramas pequenos,
  orientados a comportamiento.

## Vision General Del Modelo

```mermaid
flowchart TB
    

    Pregunta(["Pregunta"])
    Asignatura["Asignatura"]
    Examen["Examen"]


    PreguntaTeorica["Teorica"]
    PreguntaTest(["Test"])
    PreguntaRellenar["Rellenar"]
    PreguntaDesarrollo["Desarrollo"]

    PreguntaVerdaderoFalso["VerdaderoFalso"]
    PreguntaOpciones["Opciones"]


    OpcionRespuesta["OpcionRespuesta"]
    ApartadoDesarrollo["ApartadoDesarrollo"]
    Convocatoria["Convocatoria"]
    IImprimible("IImprimible")

    IImprimible -.-> Examen
    IImprimible -.-> Pregunta

    Asignatura --- Pregunta
    Examen --- Asignatura
    Examen --- Pregunta
    Examen --- Convocatoria

    Pregunta -.-> PreguntaTeorica
    Pregunta -.-> PreguntaTest
    Pregunta -.-> PreguntaRellenar
    Pregunta -.-> PreguntaDesarrollo

    PreguntaTest -.-> PreguntaVerdaderoFalso
    PreguntaTest -.-> PreguntaOpciones

    PreguntaOpciones --- OpcionRespuesta
    PreguntaDesarrollo --- ApartadoDesarrollo

    classDef contract fill:#f0fdfa,stroke:#2dd4bf,color:#1e1b4b
    classDef entity fill:#eef2ff,stroke:#818cf8,color:#1e1b4b
    classDef abstract fill:#f5f3ff,stroke:#a78bfa,color:#1e1b4b
    classDef subtype fill:#fff7ed,stroke:#fb923c,color:#1e1b4b
    classDef metadata fill:#f0f9ff,stroke:#38bdf8,color:#1e1b4b

    class IImprimible contract
    class Asignatura,Examen entity
    class Pregunta,PreguntaTest abstract
    class PreguntaTeorica,PreguntaRellenar,PreguntaDesarrollo,PreguntaVerdaderoFalso,PreguntaOpciones subtype
    class OpcionRespuesta,ApartadoDesarrollo,Convocatoria,TipoPregunta metadata
```

## Interfaz De Impresión

El contrato `IImprimible` define la forma publica de imprimir objetos
del dominio.

`imprimirSimple()` es el caso seguro por defecto:
no muestra respuestas.
`imprimirCompleto()` se usa cuando se quieren revisar
las respuestas correctas.

Ademas,
la interfaz incluye atajos `imprimir()`
e `imprimir(true)`
para poder elegir el modo con un booleano.

```
String imprimirSimple()
String imprimirCompleto()
String imprimir()
String imprimir(boolean incluirRespuestas)
```

El booleano `incluirRespuestas`
se usa para mostrar informacion
solo cuando se pide el modo completo.
En preguntas equivale a imprimir con respuestas.

```mermaid
classDiagram
    class IImprimible {
        <<interface>>
        +imprimirSimple() String
        +imprimirCompleto() String
        +imprimir() String
        +imprimir(boolean) String
        }
```

## Clase Asignatura

Modela una asignatura para asociar a las preguntas
y examenes.

Almacena el codigo,
el titulo
y el conjunto de preguntas asociadas.

```mermaid
classDiagram
    class Asignatura {
        -codigo String
        -titulo String
        -preguntas List~Pregunta~
        +Asignatura(codigo, titulo)
        +getCodigo() String
        +getTitulo() String
        +getPreguntas() List~Pregunta~
        +agregarPregunta(pregunta) void
        +getPreguntasPorTipo(tipoExamen) List~Pregunta~
        +imprimir() String
        +imprimir(boolean) String
        }
```






## Clase Examen

Representa un examen generado para el usuario.

Guarda los datos de cabecera,
la asignatura,
la convocatoria,
el curso,
el tipo de examen
y las preguntas seleccionadas.

```mermaid
classDiagram

    class Examen {
        -fecha LocalDate
        -realizadoPor String
        -asignatura Asignatura
        -convocatoria Convocatoria
        -curso String
        -tipo TipoExamen
        -preguntas List~Pregunta~
        +Examen(realizadoPor, asignatura, convocatoria, curso, tipo, preguntas)
        +resumen() String
        +imprimir() String
        +imprimir(boolean) String
        }
```


## Clase Pregunta

Clase abstracta comun para todos los tipos de pregunta.

Datos:
- texto,
- texto aclaratorio
- nota numerica.

```mermaid
classDiagram
    class Pregunta {
        <<abstract>>
        -texto String
        -textoAclaratorio String
        -nota double
        +Pregunta(texto, textoAclaratorio, nota)
        +getTexto() String
        +getTextoAclaratorio() String
        +getNota() double
        +setNota(nota) void
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase PreguntaTeorica

Pregunta con respuesta ejemplar correcta.

La respuesta solo debe aparecer en `imprimir(true)`.

```mermaid
classDiagram
    class PreguntaTeorica {
        -respuestaCorrecta String
        +PreguntaTeorica(texto, textoAclaratorio, nota, respuestaCorrecta)
        +getRespuestaCorrecta() String
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase PreguntaTest

Clase abstracta para preguntas tipo test.

Centraliza la regla de penalizacion por fallo,
comun a verdadero/falso
y listado de opciones.

```mermaid
classDiagram
    class PreguntaTest {
        <<abstract>>
        -restaSiFalla boolean
        +PreguntaTest(texto, textoAclaratorio, nota, restaSiFalla)
        +restaSiFalla() boolean
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase PreguntaVerdaderoFalso

Pregunta test con dos posibles respuestas:
verdadero
o falso.

La respuesta correcta no aparece en el modo simple.

```mermaid
classDiagram
    class PreguntaVerdaderoFalso {
        -respuestaCorrecta boolean
        +PreguntaVerdaderoFalso(texto, textoAclaratorio, nota, restaSiFalla, respuestaCorrecta)
        +isRespuestaCorrecta() boolean
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase PreguntaOpciones

Pregunta test con varias opciones posibles.

Cada opcion conoce si es correcta,
pero esa informacion solo se imprime en modo completo.

```mermaid
classDiagram
    class PreguntaOpciones {
        -opciones List~OpcionRespuesta~
        +PreguntaOpciones(texto, textoAclaratorio, nota, restaSiFalla)
        +getOpciones() List~OpcionRespuesta~
        +agregarOpcion(opcion) void
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase OpcionRespuesta

Valor asociado a `PreguntaOpciones`.

No es una pregunta completa.
Solo representa una opcion concreta.

```mermaid
classDiagram
    class OpcionRespuesta {
        -texto String
        -correcta boolean
        +OpcionRespuesta(texto, correcta)
        +getTexto() String
        +isCorrecta() boolean
        +imprimir() String
        +imprimir(boolean) String
    }
```


## Clase PreguntaRellenar

Pregunta basada en una frase con huecos,
representados por `?`,
y una lista ordenada de palabras correctas.

Las palabras correctas solo aparecen en modo completo.

```mermaid
classDiagram
    class PreguntaRellenar {
        -fraseConHuecos String
        -palabrasCorrectas List~String~
        +PreguntaRellenar(texto, textoAclaratorio, nota, fraseConHuecos, palabrasCorrectas)
        +getFraseConHuecos() String
        +getPalabrasCorrectas() List~String~
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }
```


## Clase PreguntaDesarrollo

Pregunta practica o de desarrollo.

Se compone de apartados.
Cada apartado aporta un porcentaje sobre la nota total
de la pregunta.

```mermaid
classDiagram
    class PreguntaDesarrollo {
        -apartados List~ApartadoDesarrollo~
        +PreguntaDesarrollo(texto, textoAclaratorio, nota)
        +getApartados() List~ApartadoDesarrollo~
        +agregarApartado(apartado) void
        +validarPorcentajes() boolean
        +getTipoPregunta() TipoPregunta
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }
```


## Clase ApartadoDesarrollo

Valor asociado a `PreguntaDesarrollo`.

Representa una parte evaluable de la pregunta.

```mermaid
classDiagram
    class ApartadoDesarrollo {
        -texto String
        -porcentaje double
        +ApartadoDesarrollo(texto, porcentaje)
        +getTexto() String
        +getPorcentaje() double
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }
```


## Enumeracion Convocatoria

Valores validos de convocatoria exigidos por el enunciado.

```mermaid
classDiagram
    class Convocatoria {
        <<enumeration>>
        JUNIO
        SEPTIEMBRE
        DICIEMBRE
    }
```


## Enumeracion TipoExamen

Controla las reglas de composicion del examen.

```mermaid
classDiagram
    class TipoExamen {
        <<enumeration>>
        TEORICO
        TEST
        PRACTICO
        MIXTO
    }
```


## Enumeracion TipoPregunta

Permite clasificar preguntas sin depender de clases concretas
en el flujo de consola.

```mermaid
classDiagram
    class TipoPregunta {
        <<enumeration>>
        TEORICA
        VERDADERO_FALSO
        OPCIONES
        RELLENAR
        DESARROLLO
    }
```


## Relacion: Impresion Del Dominio

`Asignatura`,
`Examen`
y todas las preguntas son imprimibles.

El contrato evita que la consola necesite conocer detalles internos
de cada clase.

```mermaid
classDiagram
    IImprimible <|.. Asignatura
    IImprimible <|.. Examen
    IImprimible <|.. Pregunta

    class IImprimible {
        <<interface>>
        +imprimirSimple() String
        +imprimirCompleto() String
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }

    class Asignatura
    class Examen
    class Pregunta {
        <<abstract>>
    }
```


## Relacion: Asignatura Y Preguntas

Una asignatura agrupa preguntas.

La pregunta no necesita conocer directamente a su asignatura,
lo que mantiene bajo el acoplamiento del modelo.

```mermaid
classDiagram
    Asignatura "1" o-- "0..*" Pregunta : contiene

    class Asignatura {
        -codigo String
        -titulo String
        -preguntas List~Pregunta~
        +agregarPregunta(pregunta) void
        +getPreguntasPorTipo(tipoExamen) List~Pregunta~
    }

    class Pregunta {
        <<abstract>>
        -texto String
        -textoAclaratorio String
        -nota double
    }
```


## Relacion: Examen, Asignatura Y Preguntas

Un examen pertenece a una asignatura
y contiene una seleccion de preguntas de esa asignatura.

La seleccion se realiza fuera del constructor,
para poder validar disponibilidad
y repartir notas despues.

```mermaid
classDiagram
    Examen "1" --> "1" Asignatura : se genera para
    Examen "1" o-- "1..*" Pregunta : incluye

    class Examen {
        -fecha LocalDate
        -realizadoPor String
        -convocatoria Convocatoria
        -curso String
        -tipo TipoExamen
        +agregarPregunta(pregunta) void
        +repartirNotas() void
    }

    class Asignatura {
        -codigo String
        -titulo String
    }

    class Pregunta {
        <<abstract>>
        -nota double
    }
```


## Relacion: Jerarquia De Preguntas

La clase `Pregunta` define los datos comunes.

Cada subtipo solo incorpora los datos especificos
que exige el enunciado.

```mermaid
classDiagram
    Pregunta <|-- PreguntaTeorica
    Pregunta <|-- PreguntaTest
    Pregunta <|-- PreguntaRellenar
    Pregunta <|-- PreguntaDesarrollo
    PreguntaTest <|-- PreguntaVerdaderoFalso
    PreguntaTest <|-- PreguntaOpciones

    class Pregunta {
        <<abstract>>
        -texto String
        -textoAclaratorio String
        -nota double
    }

    class PreguntaTeorica {
        -respuestaCorrecta String
    }

    class PreguntaTest {
        <<abstract>>
        -restaSiFalla boolean
    }

    class PreguntaVerdaderoFalso {
        -respuestaCorrecta boolean
    }

    class PreguntaOpciones {
        -opciones List~OpcionRespuesta~
    }

    class PreguntaRellenar {
        -fraseConHuecos String
        -palabrasCorrectas List~String~
    }

    class PreguntaDesarrollo {
        -apartados List~ApartadoDesarrollo~
    }
```


## Relacion: Pregunta De Opciones

`PreguntaOpciones` se compone de varias `OpcionRespuesta`.

La opcion es un objeto separado para evitar listas paralelas
de textos
y valores booleanos.

```mermaid
classDiagram
    PreguntaOpciones "1" o-- "2..*" OpcionRespuesta : contiene

    class PreguntaOpciones {
        -opciones List~OpcionRespuesta~
        +agregarOpcion(opcion) void
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }

    class OpcionRespuesta {
        -texto String
        -correcta boolean
        +imprimir() String
        +imprimir(boolean incluirRespuestas) String
    }
```


## Relacion: Pregunta De Desarrollo

`PreguntaDesarrollo` se compone de apartados.

La suma de porcentajes debe ser `100`.

```mermaid
classDiagram
    PreguntaDesarrollo "1" o-- "1..*" ApartadoDesarrollo : contiene

    class PreguntaDesarrollo {
        -apartados List~ApartadoDesarrollo~
        +agregarApartado(apartado) void
        +validarPorcentajes() boolean
    }

    class ApartadoDesarrollo {
        -texto String
        -porcentaje double
    }
```


## Relacion: Tipos Y Reglas De Composicion

`TipoExamen` decide que preguntas son compatibles.

No es una clase con estado.
Es una enumeracion porque el PDF define un conjunto cerrado:
teorico,
test,
practico
y mixto.

La regla de seleccion
y reparto de notas
debe vivir en un componente de generacion
o en un metodo aislado de seleccion,
no dentro de `Examen`.

```mermaid
classDiagram
    Examen --> TipoExamen : registra
    Pregunta --> TipoPregunta : expone
    Asignatura --> TipoExamen : filtra preguntas para

    class TipoExamen {
        <<enumeration>>
        TEORICO
        TEST
        PRACTICO
        MIXTO
    }

    class TipoPregunta {
        <<enumeration>>
        TEORICA
        VERDADERO_FALSO
        OPCIONES
        RELLENAR
        DESARROLLO
    }
```


## Reglas Importantes Del Modelo

- `Pregunta` es abstracta porque no existe una pregunta generica
  instanciable en el enunciado.
- `PreguntaTest` tambien es abstracta porque el PDF exige subtipos
  concretos.
- `imprimir()` nunca muestra respuestas correctas.
- `imprimir(true)` muestra todos los datos necesarios para revisar
  el examen.
- `Examen` no reparte notas.
  Guarda preguntas ya seleccionadas
  y puntuadas.
- `TipoExamen` existe porque el PDF obliga a distinguir reglas de
  composicion:
  teorico,
  test,
  practico
  y mixto.
- En un examen mixto debe existir una pregunta de desarrollo con valor
  `4`.
- En examenes no mixtos la nota se reparte por igual entre preguntas.
- `PreguntaDesarrollo` debe validar que sus apartados suman `100`.
- `Asignatura` es la raiz natural para localizar preguntas disponibles.


## Resumen De Responsabilidades

| Elemento | Responsabilidad |
| --- | --- |
| `IImprimible` | Contrato comun de impresion simple y completa. |
| `Asignatura` | Agrupa preguntas por codigo y titulo. |
| `Examen` | Guarda cabecera, asignatura y preguntas seleccionadas. |
| `Pregunta` | Define datos comunes de cualquier pregunta. |
| `PreguntaTeorica` | Guarda respuesta ejemplar correcta. |
| `PreguntaTest` | Centraliza penalizacion por fallo. |
| `PreguntaVerdaderoFalso` | Modela test binario. |
| `PreguntaOpciones` | Modela test con varias opciones. |
| `OpcionRespuesta` | Guarda texto de opcion y si es correcta. |
| `PreguntaRellenar` | Guarda frase con huecos y palabras correctas. |
| `PreguntaDesarrollo` | Agrupa apartados practicos evaluables. |
| `ApartadoDesarrollo` | Guarda texto y porcentaje de un apartado. |
| `Convocatoria` | Limita convocatorias validas. |
| `TipoExamen` | Limita tipos de examen validos. |
| `TipoPregunta` | Limita tipos de pregunta validos. |


## Ampliacion Opcional: Sistema De Dificultad

`SistemaDificultad` no forma parte del modelo obligatorio del enunciado.
Se plantea como ampliacion para estimar
y actualizar la dificultad de las preguntas
a partir de experimentos.

No corrige examenes completos, ni calcula la nota final de un alumno.
Su responsabilidad es establecer una medida de dificultad
para cada pregunta
y actualizarla con sesiones de prueba controladas.

La dificultad de cada pregunta se guarda como un valor entre `0.0`
y `1.0`.
Al crear una pregunta,
su dificultad inicial es `0.5`,
porque aun no hay evidencia suficiente
para considerarla facil o dificil. 

`TestTester` es el programa auxiliar de la ampliacion.
Puede ejecutarse de forma independiente al flujo normal
de creacion de examenes.

El flujo previsto es:

- pedir el codigo de una asignatura;
- seleccionar una asignatura concreta;
- no mezclar asignaturas,
  porque la maestria del tester solo tiene sentido
  dentro de una materia;
- seleccionar `N` preguntas para probar;
- recoger respuestas objetivas en preguntas deterministas;
- pedir una valoracion subjetiva de dificultad
  en preguntas libres o de desarrollo;
- actualizar la dificultad historica de las preguntas probadas.

El usuario de esta prueba se modela como `SujetoTester`.
No tiene que ser necesariamente un alumno real:
puede ser otro profesor,
un alumno colaborador
o cualquier persona usada para validar preguntas.

El `SujetoTester` tambien tiene una maestria estimada, entre `0.0`
y `1.0`.
Empieza en `0.5`.
Durante la sesion,
su maestria se actualiza con las preguntas deterministas:

- si acierta muchas,
  se acerca a `1.0`;
- si falla muchas,
  se acerca a `0.0`;
- si queda cerca de `0.5`,
  se considera un tester más informativo.

La idea es no dar demasiado peso
a testers extremos:

- quien acierta todo puede conocer ya las respuestas;
- quien falla todo puede no servir para estimar la dificultad;
- quien queda cerca de `0.5` aporta maxima incertidumbre
  y es mas util para calibrar.

Para explicar la regla se usan estos valores:

- `M`: maestria estimada del `SujetoTester`;
- `X`: utilidad del tester;
- `D`: dificultad actual de la pregunta.

La utilidad `X` se calcula con una regla simple:

```text
X = 4 * M*(1-M)
```

Por tanto:

- si `M` esta cerca de `0.5`,
  `X` se acerca a `1.0`;
- si `M` esta cerca de `0.0` o `1.0`,
  `X` se acerca a `0.0`.

Para preguntas deterministas,
la actualizacion solo se hace cuando el resultado aporta informacion:

- si el tester falla una pregunta que parecia mas facil que su nivel,
  la dificultad sube;
- si el tester acierta una pregunta que parecia mas dificil que su nivel,
  la dificultad baja;
- si el resultado era esperable,
  la dificultad no cambia.

Para preguntas libres o de desarrollo,
el tester no introduce una respuesta automaticamente corregible.
En su lugar,
indica del `1` al `10`
como de dificil considera la pregunta.
Esa valoracion se convierte a escala `0.0` a `1.0`
y se combina con la dificultad anterior,
ponderada por `X`.

```mermaid
classDiagram
    TestTester --> SistemaDificultad : usa
    TestTester --> SujetoTester : estima maestria
    TestTester --> ResultadoPrueba : crea
    SistemaDificultad --> Pregunta : actualiza dificultad
    ResultadoPrueba --> Pregunta : corresponde a
    ResultadoPrueba --> SujetoTester : realizado por

    class SistemaDificultad {
        +registrarResultado(resultado) void
        +calcularDificultad(pregunta) double
        +calcularUtilidadTester(sujetoTester) double
    }

    class TestTester {
        +ejecutarSesion(codigoAsignatura, numeroPreguntas) void
        +seleccionarPreguntas(codigoAsignatura, numeroPreguntas) List~Pregunta~
        +recogerResultado(pregunta, sujetoTester) ResultadoPrueba
    }

    class SujetoTester {
        -nombre String
        -maestria double
        -preguntasRespondidas int
        -preguntasAcertadas int
        +registrarAcierto() void
        +registrarFallo() void
        +getMaestria() double
    }

    class ResultadoPrueba {
        -pregunta Pregunta
        -sujetoTester SujetoTester
        -acertada boolean
        -tieneRespuestaObjetiva boolean
        -valoracionSubjetiva int
    }

    class Pregunta {
        <<abstract>>
        -texto String
        -nota double
        -dificultad double
    }
```


### Responsabilidad De La Ampliacion

| Elemento | Responsabilidad |
| --- | --- |
| `SistemaDificultad` | Actualiza y calcula la dificultad historica de una pregunta. |
| `TestTester` | Ejecuta sesiones de prueba para calibrar preguntas. |
| `SujetoTester` | Representa a la persona que prueba las preguntas y su maestria estimada. |
| `ResultadoPrueba` | Guarda el resultado observado de una pregunta en una sesion de prueba. |


- Metodo de actualizacion elegido:
  media ponderada simple.
  La dificultad se mueve poco a poco
  hacia la evidencia observada,
  ponderada por la utilidad `X` del tester.
