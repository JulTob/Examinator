---
> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0
# Explica el ciclo de vida básico de una `Activity` en Android.
## Enumera estados principales y qué suele hacerse en `onCreate` y `onPause`.
* Estados típicos: Created, Started, Resumed, Paused, Stopped, Destroyed. `onCreate` infla layout y enlaza vistas; `onPause` persiste estado ligero y libera recursos sensibles al foco.
---
> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0
# Qué es un `Intent` explícito frente a uno implícito.
## Da un caso de uso de cada uno.
* Explícito: nombra la clase destino (`Intent(ctx, DetalleActivity::class)`). Implícito: describe una acción y MIME (`ACTION_VIEW` con `https://`), el sistema resuelve la app.
---
> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0
# Describe el papel de `RecyclerView` y por qué se separa en `LayoutManager`, `Adapter` y `ViewHolder`.
## Enfócate en responsabilidades.
* `LayoutManager` coloca ítems; `Adapter` crea/enlaza vistas con datos; `ViewHolder` cachea referencias para evitar `findViewById` repetido y mejorar scroll.
---
> tipo: TEORICA
> nota: 4.0
> penalizacion: 0.0
# Qué implicaciones tiene ejecutar trabajo de red en el hilo principal de la UI.
## Cómo lo evitarías a alto nivel.
* Provoca `NetworkOnMainThreadException` o ANR. Usar corrutinas, `Executor`, `WorkManager` o APIs que desacoplen IO del main thread.
---
> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25
# Un `Fragment` siempre debe estar anidado dentro de otra `Fragment`.
* false
---
> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25
# `ViewModel` está pensado para sobrevivir a cambios de configuración como rotaciones.
* true
---
> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25
# Los permisos peligrosos en tiempo de ejecución se conceden automáticamente al instalar la app en todas las versiones recientes.
* false
---
> tipo: VERDADERO_FALSO
> nota: 1.0
> penalizacion: 0.25
# `ConstraintLayout` permite posicionar vistas con reglas entre ellas y guías.
* true
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Archivo mínimo donde se declaran el paquete y componentes de la app.
## Una opción.
- build.gradle.kts del módulo raíz
* AndroidManifest.xml
- proguard-rules.pro
- settings.gradle
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# API recomendada para tareas diferibles con garantías de ejecución y restricciones de batería.
## Una opción.
- AsyncTask
* WorkManager
- HandlerThread sin cola
- IntentService clásico sin migración
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Qué recurso suele definir textos reutilizables y traducibles.
## Una opción.
- res/raw
* res/values/strings.xml
- res/layout/colors.xml
- assets/txt
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Patrón de navegación que reemplaza en muchos proyectos a `FragmentTransaction` manual con grafos XML y tipos seguros.
## Una opción.
- Jetpack Compose Router
* Navigation Component
- TabLayout exclusivo
- Coordinator sin destinos
---
> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0
# Para observar datos reactivos desde la UI se usa a menudo [?] en combinación con corrutinas.
## Una palabra (clase de AndroidX).
* LiveData
---
> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0
# El fichero [?] describe dependencias y plugins del módulo Android.
## Nombre típico del script Gradle del módulo app.
* build.gradle
---
> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0
# La carpeta [?] contiene layouts XML como `activity_main.xml`.
## Carpeta estándar.
* res/layout
---
> tipo: RELLENAR
> nota: 2.0
> penalizacion: 0.0
# El método [?] de `Activity` se llama cuando la actividad ya no es visible en primer plano.
## Nombre del callback.
* onStop
---
> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0
# Diseña una pantalla de lista de tareas: modelo `Tarea`, `RecyclerView.Adapter` mínimo y cómo notificar cambios.
## Pseudocódigo o pasos claros.
* Modelo con `id` y `titulo`. Adapter con `DiffUtil` o `notifyItem*` según cambio. ViewHolder enlaza `TextView`; click delegado a lambda del fragmento/activity.
---
> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0
# Explica cómo pedirías permiso de cámara en runtime y qué harías si el usuario lo deniega permanentemente.
## Flujo UI + `ActivityResultContracts`.
* Registrar `ActivityResultContracts.RequestPermission`, lanzar solicitud, si `shouldShowRequestPermissionRationale` mostrar UI educativa; si denegado con “no volver a preguntar”, abrir ajustes.
---
> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0
# Describe persistencia local simple para preferencias de usuario frente a base de datos estructurada.
## Cuándo usar cada una.
* `DataStore`/`SharedPreferences` para pares clave-valor pequeños; Room/SQLite para entidades relacionadas, consultas y migraciones.
---
> tipo: DESARROLLO
> nota: 5.0
> penalizacion: 0.0
# Cómo separarías lógica de presentación (`Fragment`) de lógica de datos (`Repository`).
## Contratos y pruebas.
* Fragment observa `ViewModel`; `ViewModel` llama `Repository` con interfaces; `Repository` oculta fuentes (red/local). Facilita tests sustituyendo fakes.
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra test: componente que muestra mensajes breves en la parte inferior.
## Una opción.
- AlertDialog modal
* Snackbar
- Toast permanente
- Notification full-screen
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra test: capa de abstracción sobre SQLite con anotaciones y DAOs.
## Una opción.
* Room
- Realm obligatorio
- Firebase solo
- ContentProvider sin tablas
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra test: archivo donde se suelen definir dimensiones reutilizables (`dimens`).
## Una opción.
- res/mipmap
* res/values/dimens.xml
- res/xml
- res/font/colors.xml
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra selección múltiple: piezas habituales del patrón MVVM en Android.
## Marca todas las correctas.
* ViewModel
* LiveData u observadores equivalentes
- Service sin UI siempre obligatorio
* View (Activity/Fragment)
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra selección múltiple: almacenes locales comunes en apps Android.
## Marca todas las correctas.
* Room
* DataStore
- ServletContainer
* SQLite directo
---
> tipo: OPCIONES
> nota: 1.0
> penalizacion: 0.25
# Extra selección múltiple: tipos de recurso bajo `res/`.
## Marca todas las correctas.
* drawable
* mipmap
- java (como carpeta hermana de res)
* values
---
