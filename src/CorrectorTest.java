import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class CorrectorTest {

    public static void main(String[] args) {
        // 1. Definimos los archivos normalizados
        String rutaSolucion = "Solucion_S38.csv"; 
        String directorio = "s38";
        String rutaAlumno = directorio + File.separator +"Respuestas_S38_Javier.csv";

        Map<String, String> mapaSoluciones = new HashMap<>();
        

        // 2. CARGAR SOLUCIONES (Pregunta;Respuesta)
        try (BufferedReader br = new BufferedReader(new FileReader(rutaSolucion))) {
            br.readLine(); // Saltar cabecera
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 2) {
                    // partes[0] es la Pregunta, partes[1] es la Respuesta Correcta
                    mapaSoluciones.put(limpiar(partes[0]), limpiar(partes[1]));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar el solucionario: " + e.getMessage());
        }

        // 3. CORREGIR EXAMEN DEL ALUMNO
        int aciertos = 0;
        int fallos = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaAlumno))) {
            br.readLine(); // Saltar cabecera
            String linea;
            System.out.println("--- RESULTADOS DE LA EVALUACIÓN ---");
            StringBuilder reporteFallos = new StringBuilder("DETALLE DE ERRORES:\n\n");
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
               
                if (partes.length >= 2) {
                    String preguntaAlu = limpiar(partes[0]);
                    String respuestaAlu = limpiar(partes[1]);
                    
                    // Buscamos en el mapa usando la pregunta como clave
                    String respuestaCorrecta = mapaSoluciones.get(preguntaAlu);
                   
                    if (respuestaCorrecta != null) {
                        // Comparamos los dos primeros caracteres (ej: "A)")
                        if (respuestaAlu.substring(0, 2).equalsIgnoreCase(respuestaCorrecta.substring(0, 2))) {
                            aciertos++;
                        } else {
                            fallos++; 
                            // Ventana visual (para mostrar al alumno)
                            reporteFallos.append("Pregunta: ").append(preguntaAlu).append("\n");
                            reporteFallos.append("   > Tu respuesta: ").append(respuestaAlu).append("\n");
                            reporteFallos.append("   > La correcta era: ").append(respuestaCorrecta).append("\n");
                            reporteFallos.append("------------------------------------------\n");
                            // Bloque por si el pane es demasiado pequeño 
                            
                            System.out.println("X " +  preguntaAlu);
                            System.out.println("   > Alumno puso: " + respuestaAlu);
                            System.out.println("   > La correcta era: " + respuestaCorrecta);
                            
                        }
                    } else {
                        // Si no la encuentra, avisamos (útil para detectar fallos de formato)
                        System.out.println("! Pregunta no encontrada en plantilla: " + preguntaAlu);
                    }                   
                }
               
            }
            if (fallos > 0) {
                JOptionPane.showMessageDialog(null, reporteFallos.toString(), "Revisión de Errores", JOptionPane.WARNING_MESSAGE);
            }
            // Informe final para el docente
            System.out.println("\n==================================");
            System.out.println("ALUMNO");
            int totalRevisado = aciertos + fallos;
            System.out.println("TOTAL PREGUNTAS: " + totalRevisado);
            System.out.println("TOTAL ACIERTOS: " + aciertos);
            System.out.println("TOTAL FALLOS: " + fallos);
            double nota = (double)aciertos * 10 / totalRevisado;
            System.out.println("NOTA FINAL: " + String.format("%.2f", nota) + "/10");
            System.out.println("==================================");
            

        } catch (IOException e) {
            System.err.println("Error al procesar el examen: " + e.getMessage());
        }
    }

    /**
     * Limpia comillas y espacios en blanco.
     * El CSV del alumno suele traer comillas y el de plantilla no siempre.
     */
    private static String limpiar(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "").trim();
    }
}