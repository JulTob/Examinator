---

> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0

# Define variable aleatoria discreta y continua y da un ejemplo de cada una.

## Menciona función de masa vs densidad.

- Discreta: soporte numerable, masa `p(x)` suma 1. Continua: densidad `f` con integral 1 y probabilidades por intervalos. Ejemplos: lanzamiento de dado vs normal.

---

> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0

# Qué mide la varianza y cómo se relaciona con la desviación típica.

## Fórmula conceptual.

- Dispersión cuadrática alrededor de la media; desviación típica es raíz cuadrada positiva de la varianza, misma unidad que los datos.

---

> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0

# Explica el p-valor en un contraste de hipótesis.

- Probabilidad (bajo la hipótesis nula) de observar un estadístico tan extremo o más que el obtenido; p pequeño sugiere evidencia contra `H0` según el umbral elegido.

---

> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0

# Diferencia entre estimador insesgado y consistente.

## Definiciones breves.

- Insesgado: esperanza del estimador igual al parámetro. Consistente: converge en probabilidad al parámetro cuando crece el tamaño muestral.

---

> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25

# La media muestral es siempre un estimador insesgado de la media poblacional en muestreo aleatorio simple.

- true

---

> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25

# Dos eventos independientes siempre son mutuamente excluyentes si ambos tienen probabilidad positiva.

- false

---

> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25

# La correlación de Pearson mide asociación lineal entre dos variables cuantitativas.

- true

---

> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25

# Un intervalo de confianza del 95% significa que el parámetro está dentro del intervalo con probabilidad 0.95 después de observar los datos.

- false

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Esperanza de una Bernoulli con parámetro `p`.

## Una opción.

- p(1-p)

- p

- 1-p
- p^2

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Varianza de una constante `c`.

## Una opción.

- 0

- c
- c^2
- 1

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Si `Z` es N(0,1), entonces `P(Z>0)`.

## Una opción.

- 0

- 0.5

- 1
- depende de la tabla sin valor

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# En una recta de regresión simple `y = a + b x`, `b` representa habitualmente.

## Una opción.

- la correlación al cuadrado

- el cambio esperado en y por unidad de x

- la media de x
- el error estándar residual siempre

---

> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0

# La desigualdad de [?] acota la probabilidad de que un positivo se aleje de su media en términos de varianza.

## Apellido del matemático.

- Chebyshev

---

> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0

# La ley de los [?] números relaciona medias muestrales con la esperanza cuando `n` crece.

## Una sola palabra del nombre usual del resultado.

- grandes

---

> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0

# El error [?] es rechazar la hipótesis nula siendo esta verdadera.

## Tipo de error estándar.

- tipo I

---

> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0

# Si `X~Bin(n,p)`, su esperanza es [?].

## Expresión en `n` y `p`.

- n p

---

> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0

# Describe un contraste t para la media con muestra pequeña y normalidad aproximada.

## Supuestos y estadístico.

- Muestra i.i.d. normal o tamaño moderado; estadístico `t=(x̄-μ0)/(s/√n)` con `n-1` gl; comparar con cuantil de t bilateral o unilateral.

---

> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0

# Explica la diferencia entre covarianza y correlación.

## Fórmulas o propiedades clave.

- Covarianza mide co-variación en unidades producto; correlación normaliza por desviaciones, acotada en [-1,1], invariante a cambios de escala lineal comunes en cada variable (no igual a afines generales).

---

> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0

# Cómo interpretar un IC al 95% para una proporción construido con método de Wald.

## Limitación práctica.

- Interpretación frecuentista sobre el método repetido; Wald puede fallar con `p` cercano a 0/1 o `n` pequeño; preferir ajustes o Wilson en esos casos.

---

> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0

# Define función de distribución acumulada `F_X(x)` y propiedades de límites en ±∞.

## Monotonía y continuidad por la derecha.

- `F(x)=P(X≤x)`, no decreciente, límites 0 y 1 en -∞ y +∞, continua por la derecha; si continua, saltos nulos.

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Extra test: varianza de `Bin(n,p)`.

## Una opción.

- np

- np(1-p)

- p(1-p)
- n^2 p

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Extra test: si `X` e `Y` son independientes, `E[XY]` es igual a.

## Una opción.

- E[X]E[Y]

- E[X+Y]
- Var(X)Var(Y)
- Cov(X,Y)

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Extra selección múltiple: medidas de tendencia central habituales.

## Marca todas las correctas.

- mediana
- media

- varianza

- moda

---

> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25

# Extra selección múltiple: supuestos clásicos del modelo lineal simple por mínimos cuadrados (versión básica).

## Marca las que suelen enseñarse primero.

- linealidad en parámetros
- errores con media cero

- tamaño muestral infinito obligatorio

- homocedasticidad (en el paquete clásico)

---

