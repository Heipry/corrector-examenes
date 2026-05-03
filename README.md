# Corrector de Exámenes

Proyecto Java de consola para corregir exámenes tipo test que se entregan al alumno como archivo HTML y después se exportan a CSV para su corrección.

El objetivo del proyecto es automatizar la corrección de respuestas y mostrar un resumen final con aciertos, fallos y nota. Además, conserva un detalle de las preguntas falladas para facilitar la revisión.
El HTML actúa como formato de entrega del examen y el corrector trabaja con el CSV resultante de esa exportación.

Este proyecto está realizado sobre Java como muestra de trabajo real para el curso IFCD0052PO Programación en Java.

## Formatos soportados

Este corrector trabaja con dos formatos de CSV, según el tipo de examen:

### 1. Formato de entrenamiento

Se utiliza para exámenes de práctica. El CSV usa `;` como separador y las preguntas y respuestas van entre comillas.

Ejemplo:

```csv
Pregunta;Respuesta_Alumno
"¿Qué palabra reservada se utiliza en Java para declarar una variable como constante?";"C) final"
```

Este formato lo procesa la clase `CorrectorTest`.

### 2. Formato de examen final

Se utiliza como medida de seguridad para separar los exámenes de entrenamiento de los exámenes finales. El CSV usa `,` como separador y no lleva comillas en los campos.

Ejemplo:

```csv
ID Pregunta,Pregunta,Respuesta Correcta
1,¿Qué palabra reservada se utiliza en Java para declarar una variable como constante impidiendo que su valor sea modificado?,C) final
```

Este formato lo procesa la clase `CorrectorExamenes`.

## Exámenes reales del curso

En la carpeta `01 - Examenes` se incluyen varios HTML reales de este mismo curso para mostrar el contexto completo del flujo de trabajo:

- `S38-Test-Repaso.html`
- `S39-Test_Fundamentos_Core.html`
- `S39-Test_SimulacroB.html`
- `Simulacro examen final.html`

Estos archivos no son solo una maqueta visual. Cada HTML contiene las preguntas dentro del mismo archivo, el sistema de anti-copia básico con bloqueo de acciones habituales del navegador y del teclado, y la lógica JavaScript encargada de gestionar el test y exportar el CSV de respuestas en un único paso.

## Estructura de los HTML

Los HTML del curso siguen una estructura pensada para simular una prueba real:

- Las preguntas y opciones están embebidas directamente en el propio archivo HTML.
- La interfaz incluye un sistema anti-copia básico para dificultar la extracción de contenido.
- El JavaScript controla el arranque del examen, la navegación entre preguntas y la recogida de respuestas.
- Al finalizar, el propio HTML genera y descarga el CSV del alumno.
- El archivo resultante sirve como entrada para este corrector Java.

## Qué hace el proyecto

- Carga un solucionario CSV.
- Carga el CSV contestado por el alumno.
- Compara la respuesta del alumno con la respuesta correcta.
- Calcula la nota sobre 10.
- Muestra un resumen por consola.
- Si hay errores, muestra el detalle de las preguntas falladas.

## Estructura del proyecto

- `src/CorrectorTest.java`: corrector para el formato de entrenamiento.
- `src/CorrectorExamenes.java`: corrector para el formato de examen final.
- `Solucion_S38.csv`: solucionario de ejemplo para entrenamiento.
- `s38/Respuestas_S38_Javier.csv`: respuestas de ejemplo del alumno para entrenamiento.
- `Solucion_Examen_Simulacro.csv`: solucionario de ejemplo para el formato de examen final.
- `simulacro/examen_aa.csv`: respuestas de ejemplo del alumno para el formato de examen final.

## Cómo funciona

1. El programa abre el solucionario.
2. Crea un mapa con la pregunta y su respuesta correcta.
3. Abre el archivo del alumno.
4. Recorre las respuestas y compara cada una con la solución.
5. Cuenta aciertos y fallos.
6. Muestra la nota final y, si existen, los fallos detectados.

## Ejecución

El proyecto está pensado para ejecutarse desde el entorno Java con las rutas definidas dentro de cada clase principal.
Puede lanzarse desde consola con `javac` y `java`, pero está pensado sobre todo para mostrar la estructura real de trabajo dentro de Eclipse, que es el entorno habitual del curso.

## Configuración de rutas

Las rutas de los archivos están definidas directamente en el código fuente. Si cambias los nombres de los CSV o su ubicación, debes actualizar estas variables:

- `rutaSolucion`
- `directorio`
- `rutaAlumno`

## Notas importantes

- El corrector compara la respuesta por su inicio, por ejemplo `A)` o `C)`, para evitar diferencias menores de texto.

## Requisitos

- Java instalado.
- Archivos CSV disponibles en las rutas configuradas.
