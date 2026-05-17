---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Explica el polimorfismo en Java y cómo se relaciona con interfaces y clases abstractas.
## Responde con definiciones claras y un ejemplo breve.
* El polimorfismo permite tratar objetos de distintas subclases mediante un tipo común (superclase o interfaz). Una interfaz fija el contrato; la clase abstracta puede compartir implementación parcial. Ejemplo: `List<String> xs = new ArrayList<>();` y llamar a `add` sin conocer la implementación concreta.
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Qué ventaja aporta la encapsulación frente a exponer campos públicos.
## Menciona invariantes y mantenimiento.
* Ocultar el estado con métodos permite validar cambios, preservar invariantes y cambiar la representación interna sin romper a los clientes. Los campos públicos acoplan el uso interno y dificultan evolucionar o depurar el modelo.
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Diferencia entre sobrecarga y sobrescritura de métodos.
## Incluye reglas de ligadura relevantes.
* Sobrecarga: mismo nombre, distinta firma en la misma clase o herencia; resolución en tiempo de compilación. Sobrescritura: misma firma en subclase; ligadura dinámica con instancias; requiere contrato compatible (`@Override`).
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Describe el patrón delegación frente a herencia profunda.
## Cuándo preferirías composición.
* La delegación reenvía responsabilidad a un colaborador en lugar de especializar muchas capas. Preferible cuando la relación no es estrictamente “es-un” o cuando quieres intercambiar comportamientos en tiempo de ejecución sin jerarquías frágiles.
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# En Java, una clase puede heredar de varias clases al mismo tiempo.
* false
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Un método declarado `final` en una superclase puede ser sobrescrito en una subclase.
* false
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Una interfaz puede declarar métodos `default` con implementación.
* true
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# El operador `instanceof` puede usarse con patrones de coincidencia desde versiones recientes de Java.
* true
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Qué palabra clave impide que una clase sea extendida.
## Elige una opción.
- volatile
- synchronized
- static
* final
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Qué interfaz representa una secuencia ordenada con índices enteros.
## Elige una opción.
- Set
- Map
- Queue
* List
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Qué excepción indica típicamente referencia nula al invocar un método.
## Elige una opción.
- IOException
- ClassCastException
- IllegalArgumentException
* NullPointerException
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Qué nivel de acceso tiene un miembro sin modificador entre clases del mismo paquete.
## Elige una opción.
- private al paquete
- public solo en el paquete
- protected restringido al paquete únicamente
* package-private (por defecto)
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Java solo permite herencia simple de [?]; la palabra clave [?] invoca al constructor de la superclase.
## Completa con una palabra por hueco.
* clases
* super
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Una clase abstracta puede tener métodos [?] y también métodos [?].
## Un hueco por palabra.
* abstractos
* concretos
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# El método `equals` debe ser coherente con [?] para colecciones basadas en hash.
## Una sola palabra.
* hashCode
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# El contrato `Comparable` define el método [?] para orden natural.
## Nombre del método.
* compareTo
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Diseña en pseudocódigo una jerarquía `Figura` con `area()` y dos subclases `Rectangulo` y `Circulo`.
## Indica campos mínimos y cómo se cumple el polimorfismo al imprimir áreas en una lista.
- `abstract class Figura { abstract double area(); }` con `Rectangulo(ancho,alto)` y `Circulo(radio)`. Lista `List<Figura>` y bucle que llama `area()` sin ramas por tipo concreto. (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Explica cómo implementarías un `equals` seguro y simétrico para una clase `Persona` con `dni` único.
## Menciona `getClass`, nulos y consistencia con `hashCode`.
- Comprobar referencia, nulos, clase exacta, comparar `dni`; `hashCode` basado en `dni`; campos inmutables o defensivos si aplica. (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Propón una interfaz `Repositorio<T>` con operaciones mínimas y una implementación en memoria con `Map`.
## Sin código completo, pero con firmas claras.
- `void guardar(T id, T valor); Optional<T> buscar(T id); void borrar(T id);` Implementación con `ConcurrentHashMap` o `HashMap` según necesidad de concurrencia. (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Describe cómo manejarías errores de validación en un servicio de dominio sin mezclar detalles de consola.
## Usa excepciones de dominio o resultados tipados.
- Lanzar `ValidacionException` con mensaje estable o devolver `Resultado<T>` con error; la capa de presentación traduce a mensaje de usuario. (100.00%)
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: qué colección no permite duplicados según `equals`.
## Una opción.
- ArrayList
- LinkedList
- Vector
* HashSet
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: qué anotación marca un método que redefine uno de la superclase.
## Una opción.
- Deprecated
- FunctionalInterface
- SuppressWarnings
* Override
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: patrón que encapsula la creación de objetos tras una interfaz común.
## Una opción.
- Singleton
- Adapter
- Decorator
* Factory
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: cuáles son tipos referencia en Java.
## Cualquiera de las opciones correctas vale como acierto.
- int
- boolean
* String
* Integer
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: principios SOLID que aparecen en el nombre.
## Cualquiera de las opciones correctas vale como acierto.
- DRY
* Open/Closed
* Liskov Substitution
* Dependency Inversion
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: modificadores de acceso en Java.
## Cualquiera de las opciones correctas vale como acierto.
- friend
* public
* protected
* private
---
