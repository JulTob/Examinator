# Requisitos

## Fuentes

- `P2_GradoCienciaDatos_2526.pdf`

## 1. Objetivo Academico

Requisitos academicos explicitos:

- aplicar conocimientos avanzados de POO
- afianzar los conocimientos basicos de POO
- introducir herencia y polimorfismo
- razonar que tecnicas usar para obtener
  un codigo eficiente
  y mantenible

## 2. Requisitos Obligatorios.

### Flujo principal del programa 

El programa debe permitir:

- Dar de alta preguntas
- Dar de alta examenes
- ver examenes guardados
- imprimir examenes sin respuestas
- imprimir examenes con respuestas

El menu inicial debe ofrecer estas opciones:

- crear pregunta
- crear examen
- ver examenes guardados
- salir del programa

### Asignaturas

La practica debe estar preparada para manejar
varias asignaturas.

El alta de asignaturas no forma parte del flujo pedido.
En su lugar, deben existir dos asignaturas por defecto:

- `POO` - `Programacion Orientada a Objetos`
- `DADM` - `Desarrollo de aplicaciones para dispositivos moviles`

### Crear pregunta

Para dar de alta una pregunta,
el sistema debe recoger como minimo:

- tipo de pregunta
- texto de la pregunta
- nota de la pregunta

Tipos de pregunta exigidos:

- teorica
- test
- desarrollo de codigo / practico
- rellenar

Ademas de los datos genericos,
hay que almacenar los datos especificos
de cada tipo de pregunta.

### Crear examen

Para dar de alta un examen,
el sistema debe pedir:

- nombre de la persona que genera el examen
- asignatura
- convocatoria:
  junio,
  septiembre
  o diciembre
- curso:
  por ejemplo `2024-2025`

La fecha del examen debe fijarse
automaticamente con el dia actual.

El usuario debe elegir el tipo de examen:

- teorico
- test
- practico
- mixto

Reglas de composicion por tipo:

- examen teorico:
  solo preguntas teoricas de la asignatura
- examen test:
  solo preguntas tipo test y rellenar
- examen practico:
  solo preguntas de desarrollo / practico
  y escribir resultado de codigo
- examen mixto:
  puede contener todo tipo de preguntas

La seleccion de preguntas debe ser aleatoria
entre las que cumplan el criterio.

Tambien hay que pedir
el numero de preguntas del examen.

Reglas de reparto de nota:

- examen mixto:
  debe existir una pregunta
  de desarrollo / practico
  con valor `4 sobre 10`
- examen mixto:
  el resto de preguntas
  reparten los `6 puntos` restantes por igual
- cualquier otro tipo de examen:
  la nota se reparte por igual
  entre todas las preguntas

### Ver examenes guardados

Desde esta opcion se deben listar
los examenes guardados.

Campos a mostrar en el listado:

- fecha
- realizado por
- asignatura
- convocatoria
- curso
- numero de preguntas

Despues del listado,
el usuario debe poder seleccionar un examen
e imprimirlo por pantalla.

Modos de impresion requeridos:

- con respuestas
- sin respuestas,
  como examen real

## 3. Modelo De Datos Obligatorio

### Asignatura

Debe almacenar:

- codigo
- titulo
- listado de preguntas de esa asignatura

### Examen

Debe almacenar:

- fecha de realizacion
- realizado por
- asignatura
- convocatoria
- curso
- tipo de examen
- listado de preguntas del examen

Al imprimir la asignatura en un examen,
deben mostrarse codigo y titulo.

### Pregunta

Debe almacenar:

- texto de la pregunta
- texto aclaratorio
- nota numerica de la pregunta

### Pregunta teorica

Debe almacenar:

- respuesta correcta

La respuesta correcta no debe aparecer
en el examen sin respuestas.

### Pregunta tipo test

Debe almacenar:

- varias opciones de contestacion
- si la pregunta fallada resta

Subtipos exigidos en el PDF:

- verdadero / falso
- listado de opciones
  con una o varias correctas

Para preguntas de listado de opciones,
cada respuesta debe poder indicar:

- texto de la opcion
- si es correcta o no

La informacion sobre que opcion es correcta
no debe mostrarse
en el examen sin respuestas.

### Pregunta de rellenar

Debe almacenar:

- una frase de respuesta
  con caracteres `?`
  en las posiciones a completar
- un listado ordenado
  de palabras correctas

Las palabras correctas
no deben mostrarse
en el examen sin respuestas.

### Pregunta de desarrollo / practico

Debe almacenar:

- un listado de apartados

Cada apartado debe almacenar:

- texto del apartado
- porcentaje sobre la nota de la pregunta

## 4. Restricciones Tecnicas Obligatorias

### Interfaz de impresion

El proyecto usara una interfaz llamada `IImprimible`
con dos operaciones principales:

- `imprimirSimple`
- `imprimirCompleto`

Objetivo:

- formato por defecto con datos reducidos
- formato con respuestas cuando se solicita explicitamente

Regla explicita para preguntas:

- `imprimirSimple`:
  sin respuestas
- `imprimirCompleto`:
  con respuestas

La implementacion puede anadir atajos como `imprimir()`
o `imprimir(true)`,
siempre que los dos modos principales existan.

### Persistencia

Los datos deben persistirse en fichero.
No se pueden perder
entre una ejecucion y otra.

Opciones mencionadas por el PDF:

- serializacion con `GSON`
- lectura y escritura en fichero
- `Serializable`

Ubicacion exigida:

- directorio `files`
  en la raiz del proyecto

El PDF acepta:

- una clase de persistencia por clase persistida
- o una clase con todo lo necesario para persistir

Segun la opcion elegida,
habra `1` o `2` ficheros de persistencia.

En la entrega se debe incluir
el fichero de persistencia.

### Organizacion y calidad

Requisitos explicitos:

- controlar errores con manejadores de excepciones
- usar paquetes
- mantener el codigo limpio
- documentar y comentar correctamente
- generar documentacion `JavaDoc`

### Librerias externas

Si se usa alguna libreria externa,
debe almacenarse en:

- `libs/`

## 5. Parte II. Ampliaciones

La Parte II figura como `2 puntos`.

Opciones mencionadas:

- `0,5 puntos`:
  generar examenes en PDF con `iText`
- `0,5 puntos`:
  aplicar un patron visto en clase
- `1 punto`:
  ampliacion adicional
  acordada previamente con el profesor

## 6. Entrega

Requisitos de entrega extraidos del PDF:

- fecha limite:
  `10 de mayo de 2026`
  a las `23:59`
- entrega mediante la opcion
  `Practica 2`
  del campus

Ademas,
hay que entregar
una memoria explicativa
en Word,
PDF
u otro formato equivalente.

La memoria debe incluir:

- diagrama de clases
- descripcion de las clases
- explicacion de las funciones mas importantes
- breve resumen de como se ha abordado la practica
- problemas encontrados

El PDF recuerda expresamente
que la practica es individual.

## 7. Ambiguedades Y Decisiones Abiertas

Estos puntos aparecen poco definidos
o generan tension entre requisitos.

- el flujo exacto para asociar una pregunta
  a una asignatura
  no se detalla de forma explicita,
  aunque esa relacion es necesaria
- la `nota de la pregunta`
  aparece como dato propio de la pregunta,
  pero al crear examen
  el PDF vuelve a repartir la nota
  entre las preguntas seleccionadas
- no se especifica que hacer
  si el usuario pide mas preguntas
  de las disponibles
- no se define la formula exacta
  de penalizacion
  cuando una pregunta test fallada resta
- no queda claro
  si el `texto aclaratorio`
  es siempre obligatorio
  o solo se usa cuando haga falta
- la frase
  `escribir el resultado de codigo`
  en el examen practico
  no queda bien integrada
  con la lista formal de tipos de pregunta
- en la Parte II
  el texto habla de `2 puntos`,
  pero luego dice
  `hasta sumar el punto`,
  lo que parece una inconsistencia del enunciado

## 8. Lo Que El PDF No Exige De Forma Explicita

Para no sobredisenar,
conviene dejar anotado
lo que no aparece
como requisito explicito:

- interfaz grafica
- base de datos
- aplicacion web
- login o gestion de usuarios
- alta manual de asignaturas
- exportaciones distintas de pantalla
  o PDF opcional
- metricas avanzadas
- patrones de diseno obligatorios
  en la Parte I

## 9. Lectura Practica

Si queremos ser sencillos,
efectivos
y completos,
la lectura mas segura del PDF es esta:

- cumplir toda la Parte I
- dejar la persistencia funcionando
- implementar bien `IImprimible`
- documentar el diseno y las decisiones
- tratar la exportacion a PDF
  y los patrones
  como ampliaciones,
  no como base obligatoria

