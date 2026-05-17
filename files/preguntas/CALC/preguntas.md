---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Enuncia la definición formal de límite de una función en un punto interior del dominio.
## Usa notación con épsilon y delta.
* Para todo `ε>0` existe `δ>0` tal que si `0<|x-a|<δ` entonces `|f(x)-L|<ε`; entonces `L` es el límite en `a`.
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5107
> penalizacion: 0.0
# Relación entre continuidad y existencia de límite en un punto.
## Incluye la condición sobre `f(a)`.
* Continuidad en `a` exige que exista `lim f(x)` al tender a `a`, que coincida con `f(a)` y que `f(a)` esté definido.
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Qué establece el teorema fundamental del cálculo (parte que relaciona integral y derivada).
## Enunciado breve.
* Si `F` es primitiva de `f` continua en `[a,b]`, entonces `∫_a^b f = F(b)-F(a)`; y la derivada de la integral acumulada recupera `f` bajo hipótesis adecuadas.
---
> tipo: TEORICA
> nota: 4.0000
> dificultad: 0.5178
> penalizacion: 0.0
# Define derivada por el límite del cociente incremental.
## Función real de variable real.
* `f'(a)=lim_{h→0} (f(a+h)-f(a))/h` si el límite existe finito.
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Si una función es derivable en un punto, entonces es continua en ese punto.
* true
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Toda función continua en un intervalo cerrado acotado alcanza máximo y mínimo absolutos.
* true
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# La integral impropia de `1/x` de 1 a ∞ converge a un valor finito.
* false
---
> tipo: VERDADERO_FALSO
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# La regla de L’Hôpital aplica siempre sin comprobar las hipótesis de indeterminación `0/0` o `∞/∞`.
* false
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Derivada de `sin(x)`.
## Una opción.
- -cos(x)
- sin(x)
- tan(x)
* cos(x)
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Integral indefinida de `1/x` para `x>0`.
## Una opción.
- x^2/2 + C
- -1/x^2 + C
- e^x + C
* ln(x) + C
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.4625
> penalizacion: 0.2500
# Límite cuando `x→0` de `(sin x)/x`.
## Una opción.
- 0
- ∞
- no existe
* 1
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Derivada de `e^{kx}` respecto de `x`.
## Una opción.
- e^{kx}
- k ln(x)
- e^{k}/k
* k e^{kx}
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# La derivada de `x^n` respecto de `x` es ? para entero `n≠0`.
## Expresión en términos de `n` y `x`.
* n x^(n-1)
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# El desarrollo de Taylor de `e^x` alrededor de 0 comienza `1 + x + ? + ...`.
## Siguiente término no nulo.
* x^2/2
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Si `f''(x)>0` en un intervalo, la función es ? en ese intervalo.
## Una palabra (convexidad).
* convexa
---
> tipo: RELLENAR
> nota: 2.0000
> dificultad: 0.5000
> penalizacion: 0.0
# La integral definida interpreta geométricamente el área con signo bajo la curva respecto del eje ?.
## Eje horizontal estándar.
* x
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Esboza cómo estudiarías extremos locales de `f` derivable dos veces en un abierto.
## Criterio de la segunda derivada y puntos críticos.
- Buscar `f'=0`, clasificar con `f''`: `f''(c)>0` mínimo local, `<0` máximo local; si `0` usar otro criterio; revisar frontera si el dominio es cerrado. (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5154
> penalizacion: 0.0
# Explica el método de sustitución para integrales indefinidas con un ejemplo abstracto `∫ g(u(x)) u'(x) dx`.
## Sin calcular números concretos.
- Poner `u=u(x)`, `du=u'dx`, reescribir la integral en `u` e integrar respecto a `u`, luego deshacer cambio. (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5031
> penalizacion: 0.0
# Describe la integración por partes y cuándo suele ser útil.
## Fórmula y caso típico.
- `∫ u dv = uv - ∫ v du`; útil cuando un factor se simplifica al derivar (log, polinomio) y el otro es fácil de integrar al multiplicar (exp, trig). (100.00%)
---
> tipo: DESARROLLO
> nota: 5.0000
> dificultad: 0.5000
> penalizacion: 0.0
# Cómo determinarías convergencia de una serie numérica de términos positivos.
## Menciona al menos dos pruebas.
- Comparación / límite, ratio, raíz, integral; acotar término general y ver si tiende a 0 (necesario pero no suficiente). (100.00%)
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.4680
> penalizacion: 0.2500
# Test: valor de la derivada de `ln(x)` en `x=1`.
## Una opción.
- 0
- e
- 1/e
* 1
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.4722
> penalizacion: 0.2500
# Test: qué resultado simbólico es habitual para `∫ cos(x) dx`.
## Una opción.
- -sin(x) + C
- cos(x) + C
- tan(x) + C
* sin(x) + C
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5444
> penalizacion: 0.2500
# Test: condiciones típicas del teorema de Rolle en `[a,b]`.
## Cualquiera de las opciones correctas vale como acierto.
- f(a) distinto de f(b) obligatorio
* continuidad en el cerrado
* derivabilidad en el abierto
* f(a)=f(b)
---
> tipo: OPCIONES
> nota: 1.0000
> dificultad: 0.5000
> penalizacion: 0.2500
# Test: funciones que suelen aparecer en sustituciones trigonométricas.
## Cualquiera de las opciones correctas vale como acierto.
- cosh en sustitución circular básica
* sin
* tan
* sec
---
