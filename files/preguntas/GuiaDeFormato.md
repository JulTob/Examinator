# Formato limitado para usuarios

Usar markdown permite realizar exámenes de manera legible, rápida y efectiva. 

Para ser congruente con las reglas de parseo se deben seguir estas indicaciones.

En general los símbolos indican:

```txt
---
> metadatos
# Texto principal
## Texto aclaratorio
* Opcion correcta, esperada, o ejemplar.
- Opcion incorrecta
---
```

Las líneas que empiezan con `>` contienen datos técnicos.

Metadatos mínimos:

- tipo
- `nota: <valor>`
  - Puntuación añadida en éxito.

Opcionales:

- `penalizacion: <valor>`
  - Puntuación substraida en error.
  - Por defecto `0.0`
  - `0.0` significa que la pregunta no penaliza.

# Tipos Permitidos

Requerido usar nombres iguales al enum TipoPregunta.

```txt
TEORICA
VERDADERO_FALSO
OPCIONES
RELLENAR
DESARROLLO
```

# Texto Público

```txt
# Texto principal de la pregunta
## Texto aclaratorio o frase pública
```

El texto sin prefijo de línea se ignora, se puede usar para notas privadas.

# Respuestas Y Opciones

En general: 

- `-` = opción incorrecta o apartado público.
- `*` = respuesta correcta, opción correcta, o ejemplar de la solución esperada.
- `TEORICA`: `*` Ejemplar ilustrativo. Solo se muestra en la opción de print completo.
- `VERDADERO_FALSO`: `* verdadero` o `* falso`. Opcional `- falso`/`- verdadero`
- `OPCIONES`: `-` opciones incorrectas, `*` opciones correctas. 
- `RELLENAR`: cada `*` es una palabra correcta, por orden. Indica huecos con [?]
- `DESARROLLO`: `*` Ejemplar ilustrativo. Solo se muestra en la opción de print completo.

## Tipo Pregunta Teórica

```txt
---
> tipo: TEORICA
> nota: 5.0
> penalizacion: 0.0
# Texto principal
## Texto aclaratorio
* Respuesta Ejemplar (Privada)
---
```

## Tipo Verdadero/Falso

```txt
---
> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25
# Enunciado.
* true (o false) 
---
```

## Tipo Opciones

```txt
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Texto principal
## Texto aclaratorio
- Opcion incorrecta 1
- Opcion incorrecta 2 
* Opcion correcta
---
```

## Tipo Pregunta Teórica

```txt
---
> tipo: TEORICA
> nota: 5.0
> penalizacion: 0.0
# Texto principal
## Texto aclaratorio
* Respuesta Ejemplar (Privada)
---
```

