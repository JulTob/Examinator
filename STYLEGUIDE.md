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


## Organización del Proyecto 
- `modelo`: entidades y lógica de dominio.
- `persistencia`: guardado y carga del estado.
- `ui`: interacción por consola.
- `contrato`: interfaces compartidas.


## Convenciones de Estilo
- Tabulación Waterfall:
  - Dividir en líneas por "One line, one step".
  - Conectores y ampliaciones en nuevas líneas
  - Ligereza horizontal, baja densidad de líneas.
- Comentar cuando aporte documentación, intención, decisión o contexto.
  - `//--`: Pin informativo intext
  - `//`: Comentario de documentación de desarrollo y organización
  - Incorporar Javadoc claro demostrando uso e intención, no algoritmo. 
- Código limpio y legible
  - Nombres explícitos
  - Abstraer por _Bottom-Up_ 
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


