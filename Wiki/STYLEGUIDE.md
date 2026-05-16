# Style Guide

Explicación de las decisiones de diseño estructurales, estilo, y organización del proyecto.

## Objetivo

Definir una forma de trabajo clara, breve y consistente para desarrollar la Practica 2.

## Principios

- Cada clase debe tener un propósito claro.
- Priorizar código simple, legible y justificable con tests.
- Evitar complejidad.
- Anotar decisiones. 
- Cubrir requisitos. 
- Separar responsabilidades entre modelo, persistencia, consola y contratos.
- Explicito

## Convenciones de Estilo

- Tabulación Waterfall:
  - Dividir en líneas por "One line, one step".
  - Conectores y ampliaciones en nuevas líneas
  - Ligereza horizontal, baja densidad de líneas.
  - Las llamadas con varios parámetros se parten en vertical.
  - Los parámetros llevan DOS tab extra respecto a la llamada,  
  para que se distingan claramente la función  
  y sus entradas.
  - La llave de cierre `}` no se de-tabula artificialmente.
  Debe quedar al nivel visual del bloque padre que está cerrando.
  - Después de `}` se cierra el bloque;
  la llave no debe parecer una instrucción independiente.

Ejemplo:

```java
this.texto =
    validarTextoObligatorio(
        texto,
        "texto"
        );
```

Ejemplo con bloque:

```java
if (nota < 0.0) {
    throw new IllegalArgumentException(
        "La nota no puede ser negativa."
        );
    }
```

- Comentar cuando aporte documentación, intención, decisión o contexto.
  - `//--`: Pin informativo intext
  - `//`: Comentario de documentación de desarrollo y organización
  - Incorporar Javadoc claro demostrando uso e intención, no algoritmo.
- Código limpio y legible
  - Nombres explícitos
  - Abstraer por *Bottom-Up* 
  - Encapsulación en métodos funcionales por propósito
  - Ignorar implementación concreta,
  algoritmos o tipos de datos
  fuera de las interacciones públicas del objeto. 
  - Clarificar con clases aislantes/asistentes de contrato:
    - Ejemplo:
    `ListaPreguntas` en lugar de `Array<Pregunta>`.
    - Ejemplo:
    `TablaAlumnos` en lugar de `Dataframe<Alumnos>`.
    - Incorporar chequeos de validez en setter/getters.

## Java

- Clases e interfaces en `PascalCase`.
- Métodos, variables y atributos en `camelCase`.
- Constantes en `UPPER_SNAKE_CASE`.
- Atributos privados salvo justificación.
- Usar interfaces cuando definan un contrato real.
- Usar herencia solo cuando exista una relación clara “es un/a”.

## Criterios de testing

- Verificar que cada requisito obligatorio funciona.
- Probar flujos principales desde consola.
- Revisar después de cada cambio si simplifica (o complica) el diseño.

