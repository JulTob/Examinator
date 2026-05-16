# Práctica 2: Generador de Exámenes Aleatorio

**Asignatura:** Programación orientada a objetos  
**Titulación:** Grado en Ciencia de Datos e Inteligencia Artificial  
**Curso:** 2025-2026

## 1. Objetivos

El objetivo es que el alumno, una vez asimilados los conocimientos básicos durante la realización de la práctica 1, aplique conocimientos avanzados de POO vistos en las clases de teoría.

Mediante esta práctica se desea terminar de afianzar los conocimientos básicos de POO e introducir nuevos de carácter avanzado, especialmente:

- Herencia.
- Polimorfismo.
- Razonamiento sobre qué técnicas avanzadas de POO utilizar para conseguir código eficiente y mantenible.

## 2. Parte I: 8 puntos

Se trata de realizar un programa que permita crear, almacenar e imprimir exámenes de convocatorias de asignaturas.

La funcionalidad básica será:

- Dar de alta preguntas.
- Dar de alta exámenes.
- Ver exámenes guardados.
- Imprimir exámenes:
  - Sin respuestas.
  - Con respuestas.

### Menú inicial

Lo primero que realizará el programa será solicitar qué se desea hacer:

1. Crear pregunta.
2. Crear examen.
3. Ver exámenes guardados.
4. Salir del programa.

### Asignaturas por defecto

La práctica deberá estar preparada para tener varias asignaturas, pero se obvia el alta de las mismas. Por tanto, existirán dos asignaturas por defecto:

| Código | Título |
| --- | --- |
| `POO` | Programación Orientada a Objetos |
| `DADM` | Desarrollo de aplicaciones para dispositivos móviles |

## 2.1. Crear Pregunta

En el alta de pregunta se almacenarán los siguientes datos generales:

- Tipo de pregunta.
- Texto de la pregunta.
- Nota de la pregunta.

Tipos de pregunta:

- Teórica.
- Test.
- Desarrollo de código.
- Rellenar.

Primero se preguntará el tipo de pregunta y, una vez seleccionado, se solicitarán el resto de datos genéricos. Además de estos datos, habrá que recuperar también los datos específicos de cada tipo de pregunta, definidos en la sección de definiciones.

## 2.2. Crear Examen

En el alta de examen, el programa debe solicitar:

1. Nombre de la persona que va a generar el examen.
2. Asignatura, seleccionada desde el listado de asignaturas guardadas.
3. Convocatoria:
   - Junio.
   - Septiembre.
   - Diciembre.
4. Curso, por ejemplo `2024-2025`.

La fecha de realización se establecerá directamente con el día actual.

Después de introducir esos datos, se solicitará el tipo de examen:

- Teórico.
- Test.
- Práctico.
- Mixto.

### Reglas por Tipo de Examen

| Tipo de examen | Preguntas permitidas |
| --- | --- |
| Teórico | Solo preguntas teóricas de la asignatura |
| Test | Preguntas tipo test y de rellenar |
| Práctico | Preguntas de desarrollo/práctico y escribir resultado de código |
| Mixto | Todo tipo de preguntas |

La selección de preguntas será aleatoria entre la totalidad de preguntas que cumplan el criterio.

Sea cual sea el tipo de examen elegido, se solicitará el número de preguntas que tendrá el examen.

### Reglas de Puntuación

- Si el examen es de tipo mixto, existirá una pregunta de tipo desarrollo/práctico con valor `4` sobre `10`.
- En el examen mixto, el resto de preguntas podrán ser de cualquier tipo y se repartirán los `6` puntos restantes por igual.
- Para cualquier otro tipo de examen, el valor de cada pregunta se repartirá por igual entre todas las preguntas.

## 2.3. Ver Exámenes Guardados

Desde esta opción se listarán los exámenes guardados en la aplicación.

Se mostrarán los siguientes campos:

| Campo |
| --- |
| Fecha |
| RealizadoPor |
| Asignatura |
| Convocatoria |
| Curso |
| Nº Preguntas |

Una vez listados, se preguntará al usuario si desea seleccionar alguno de ellos para imprimirlo por pantalla.

Si selecciona un examen, se le solicitará el modo de impresión:

- Con respuestas.
- Sin respuestas, en modo examen real.

En el primer caso se mostrarán las respuestas. En el segundo caso se mostrarán solo las preguntas, como si fuera un examen real.

## 2.4. Definiciones

A continuación se detallan los actores principales que aparecen en el programa.

### Asignatura

Almacenará los datos de cada asignatura de la cual se puede generar un examen.

Datos a almacenar:

- Código.
- Título.
- Listado de preguntas para esa asignatura.

### Examen

Almacenará los datos de cada examen generado.

Datos a almacenar:

- Fecha de realización: `xxxx`.
- Realizado por: `xxxxx`.
- Asignatura: al imprimir se debe mostrar código y título de la asignatura.
- Convocatoria: junio, septiembre o diciembre.
- Curso: por ejemplo `2024-2025`.
- Tipo examen: teórico, test, práctico o mixto.
- Preguntas: listado de preguntas del examen.

### Pregunta

Almacenará los datos de cada pregunta dada de alta.

Datos a almacenar:

- Texto de la pregunta.
- Texto aclaratorio.
- Nota numérica de la pregunta.

### Pregunta Teórica

Pregunta con respuesta.

Datos a almacenar:

- Respuesta correcta.

La respuesta correcta no se imprimirá al imprimir el examen sin respuestas.

### Pregunta Tipo Test

Pregunta con varias opciones de contestación.

Datos a almacenar:

- Opciones de contestación.
- Indicación de si la pregunta fallada resta.

Tipos de preguntas de tipo test:

- Pregunta de verdadero/falso:
  - Resultado: verdadero/falso.
- Pregunta de listado de opciones, con una o varias correctas:
  - Número de respuestas.
  - Listado de respuestas posibles.
  - Texto de la respuesta.
  - Si la opción es correcta o no.

La información sobre qué opción es correcta no se imprimirá al imprimir el examen sin respuestas.

### Pregunta de Rellenar

Pregunta basada en texto con `x` palabras que rellenar.

Datos a almacenar:

- Frase de la respuesta, que contendrá `x` caracteres `?`.
- Listado de palabras correctas por orden de aparición en la frase de respuesta.

Cada carácter `?` deberá ser completado al responder el examen.

Las palabras correctas no se imprimirán al imprimir el examen sin respuestas.

### Pregunta de Desarrollo / Práctico

Pregunta de desarrollo de código.

Tiene varios apartados evaluados parcialmente respecto a la nota final de la pregunta.

Datos a almacenar:

- Listado de apartados del código a desarrollar.
- Texto de cada apartado.
- Porcentaje de cada apartado sobre la nota de la pregunta.

## 3. Consideraciones a Tener en Cuenta

### Interfaz de Impresión

Para estandarizar la impresión de los datos de cualquier clase se requiere la utilización de una interfaz llamada `IImprimible`.

La interfaz tendrá dos métodos:

- `imprimirSimple`.
- `imprimirCompleto`.

Estos métodos realizarán la impresión de dichas clases en un formato amigable:

- Formato simple: datos reducidos.
- Formato completo: todos los datos.

En el caso de las preguntas:

- El formato de impresión simple no tendrá las respuestas.
- El formato de impresión completo sí tendrá las respuestas.

### Persistencia

Los datos almacenados en la práctica deben persistirse en fichero. Es decir, no se pueden perder los datos entre una ejecución y otra de la práctica.

En clase se han visto métodos para:

- Serializar y deserializar objetos mediante GSON.
- Leer y escribir en fichero.
- Utilizar la interfaz `Serializable` para guardar datos como array de bytes.

En cualquier caso, los ficheros se guardarán en el directorio `files` de la raíz del proyecto.

Para dicha persistencia se sugiere utilizar:

- Una clase por cada clase a persistir.
- O bien una clase con todo lo necesario para ser persistido.

En función de la opción elegida tendremos `1` fichero o `2` ficheros de persistencia.

En la entrega se deberá incluir el fichero de persistencia para facilitar la corrección.

### Polimorfismo

Al utilizar polimorfismo, en algunos casos necesitaremos comprobar la clase real del objeto.

Se puede utilizar el método `getClass()`:

```java
if (caja.getClass().getName() == "Caja") {
    // ...
}
```

O también la instrucción `instanceof`:

```java
if (caja instanceof Caja) {
    // ...
}
```

## 4. Parte II: 2 puntos

Para obtener la puntuación máxima de la práctica se deberá realizar una ampliación que consiste en algunos de los siguientes puntos.

Opciones de ampliación:

- **0,5 puntos:** utilizar la librería gratuita iText para generar los exámenes en formato PDF.
  - Ejemplo de uso: <http://www.java4s.com/core-java/creating-pdf-with-java-and-itext-generating-pdf-using-java-example/>
  - Descarga de la librería: <https://sourceforge.net/projects/itext/>
- **0,5 puntos:** identificar en la práctica un ámbito donde aplicar un patrón de los vistos en clase. Por ejemplo, utilizarlo en la clase que haga la persistencia de los objetos, ya que el acceso es único y centralizado.
- **1 punto:** hacer una ampliación adicional a la práctica, acordada previamente con el profesor.

## 5. Otros Temas

- Cualquier librería externa utilizada se almacenará en una carpeta `libs` en la raíz del proyecto.
- Se deberán controlar todos los errores mediante manejadores de excepciones.
- Es necesaria la utilización de paquetes para no tener todas las clases juntas y así tener mejor organizado el código.
- Se valorará de forma muy importante la limpieza del código, así como que esté documentado y comentado correctamente.
- Es obligatoria la realización de la documentación JavaDoc.

## 6. Normas de Entrega

La práctica deberá entregarse antes del **10 de mayo de 2026 a las 23:59** mediante la opción de entrega **Práctica 2** que aparecerá en el campus de la asignatura.

Se deberá entregar una memoria explicativa en Word, PDF u otro formato equivalente.

La memoria debe incluir:

- Un diagrama de clases, la descripción de estas y la explicación de las funciones más importantes.
- Un breve resumen de cómo se ha abordado la práctica.
- Los problemas que se han ido encontrando.

Se recuerda que las prácticas son individuales. Por tanto, cualquier copia detectada entre alumnos conllevará que ambos alumnos obtendrán la puntuación de `0`.

## 7. Apéndice I: Detalle de Tipos de Preguntas

### 7.1. Ejemplo de Pregunta Teórica
```txt
**Pregunta 8 (0,5 puntos):** ¿Qué es el polimorfismo? Contesta y explica dónde lo has utilizado en la práctica de asignatura.
```

### 7.2. Ejemplo de Pregunta Test
```txt
**Pregunta 4 (0,5 puntos):** ¿Qué significa instanciar una clase? Selecciona la respuesta correcta.

a. Crear la definición de la clase.  
b. Crear un objeto a partir de una clase.  
c. Crear clases a partir de otras clases.  
d. Todas las anteriores son correctas.
```

### 7.3. Ejemplo de Pregunta Rellenar
```txt
**Pregunta 4 (0,5 puntos):** Completa la siguiente frase:

Suponiendo que la clase B hereda de la clase A:

- A la clase A se le llama clase o ______.
- A la clase B se le llama clase o ______.
````

### 7.4. Ejemplo de Pregunta Desarrollo / Práctico
```txt
**Pregunta 15 (3 puntos):**

a. Escribe el código en Java de una clase llamada `Persona` que tenga como atributos `dni`, `nombre` y `apellidos`, todos ellos de tipo `String`. Incluye los métodos de acceso de lectura y escritura para todos ellos, un constructor que asignará solo `dni` y otro que asigne todos los atributos.

b. Crea una clase `Asignatura` que contenga el código de la asignatura, el nombre de tipo `String` y la nota de aprobado de tipo `int`.

c. Crea una clase `Alumno` que herede de la clase `Persona` y que agregue un nuevo atributo del tipo array de asignaturas. Añadir un método que permita agregar asignaturas al alumno.
```
