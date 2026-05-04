# Exam Automator: Motor de Corrección Java

Motor de ejecución en consola desarrollado en Java para la corrección masiva de exámenes tipo test. Esta herramienta automatiza el procesamiento de datos exportados desde entornos de evaluación interactivos, permitiendo una gestión ágil y precisa de las calificaciones.

Este proyecto está realizado sobre Java como muestra de trabajo real para el curso **IFCD0052PO Programación en Java**, formando parte de un ecosistema de innovación educativa.

## 🔄 El Ecosistema de Evaluación

Para ver el flujo de trabajo completo, este repositorio se complementa con:
*   **[Java Tests Collection](https://github.com/heipry/java-tests-IFCD0052PO):** Repositorio de artefactos HTML/JS donde los alumnos realizan las pruebas y generan los archivos CSV que procesa este motor.

## 🛠️ Formatos soportados

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

## 🚀 Qué hace el proyecto

*   **Mapeo de Solucionarios:** Carga un archivo CSV maestro y genera un mapa de claves (pregunta/respuesta) en memoria.
*   **Validación de Respuestas:** Compara las entradas del alumno basándose en el inicio de la cadena (ej. `A)` o `C)`) para evitar falsos negativos por diferencias menores de texto.
*   **Generación de Estadísticas:** Calcula la nota sobre base 10 y genera un informe detallado de aciertos y errores directamente en consola.

## 📁 Estructura del Proyecto

- `src/CorrectorTest.java`: corrector para el formato de entrenamiento.
- `src/CorrectorExamenes.java`: corrector para el formato de examen final.

- `Solucion_[S38|Fundamentos_Core|SimulacroB].csv`: solucionario de ejemplo para entrenamiento.
- `s38/Respuestas_S38_Javier.csv`: respuestas de ejemplo del alumno para entrenamiento.
- `core/Resultados_Fundamentos_Core_Sample.csv`: respuestas de ejemplo del alumno para entrenamiento.
- `modeloB/Respuestas_SimulacroB_Javier.csv`: respuestas de ejemplo del alumno para entrenamiento.

- `Solucion_Examen_Simulacro.csv`: solucionario de ejemplo para el formato de examen final.
- `simulacro/examen_aa.csv`: respuestas de ejemplo del alumno para el formato de examen final.

## 💻 Ejecución y Configuración

El proyecto está optimizado para su ejecución en **Eclipse IDE**, simulando el entorno real de desarrollo del curso.

Puede lanzarse desde consola con `javac` y `java`, pero está pensado sobre todo para mostrar la estructura real de trabajo dentro de Eclipse, que es el entorno habitual del curso.

Para procesar nuevos exámenes, simplemente actualice las constantes de ruta en la clase principal:
*   `rutaSolucion`
*   `directorio`
*   `rutaAlumno`

## Requisitos

- Java instalado.
- Archivos CSV disponibles en las rutas configuradas.
---
📍 Parte de mi [Portfolio de Recursos Docentes](https://recursos.javierdiaz.com.es)

---

### Sobre el autor

Para conocer más sobre mi trayectoria profesional, puedes visitar mi [**CV online**](https://javierdiaz.com.es/).




