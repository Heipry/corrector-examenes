import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
/**
 * El nuevo archivo CSV utiliza la coma como separador, 
 * pero el texto de las preguntas también contiene comas internas 
 * esto provoca que un simple split(",") rompa la pregunta en varios trozos 
 * y desplace la respuesta a una columna inexistente o incorrecta. 
 * Al modificar la lógica para que la respuesta sea siempre el 
 * último elemento del array (partes.length - 1) 
 * y la pregunta sea la unión de las partes intermedias, 
 * garantizamos que el programa recupere la información correctamente sin importar 
 * cuántas comas haya escrito dentro del enunciado.
 */
public class CorrectorExamenes {

	public static void main(String[] args) {
		String rutaSolucion = "Solucion_Examen_Simulacro.csv";
		String directorio = "simulacro";
		String rutaAlumno = directorio + File.separator + "examen_aa.csv";

		Map<String, String> mapaSoluciones = new HashMap<>();
		// 2. CARGAR SOLUCIONES
		try (BufferedReader br = new BufferedReader(new FileReader(rutaSolucion))) {
		    br.readLine(); // Saltar cabecera
		    String linea;
		    
		    while ((linea = br.readLine()) != null) {
		        // Usamos la coma, pero sabiendo que puede haber muchas
		        String[] partes = linea.split(",");
		        
		        if (partes.length >= 3) {
		            // TRUCO DOCENTE:
		            // El ID es partes[0]
		            // La Respuesta es SIEMPRE el último elemento
		            String respuestaCorrecta = limpiar(partes[partes.length - 1]);
		            
		            // La Pregunta puede estar troceada en varias partes si tiene comas.
		            // La reconstruimos o simplemente cogemos la parte principal.
		            // Para este examen, lo más seguro es limpiar bien la parte 1 y 2.
		            String preguntaCompleta = "";
		            for (int i = 1; i < partes.length - 1; i++) {
		                preguntaCompleta += partes[i];
		            }
		            
		            mapaSoluciones.put(limpiar(preguntaCompleta), respuestaCorrecta);
		        }
		    }
		} catch (IOException e) {
		    System.err.println("Error en el solucionario: " + e.getMessage());
		}
		// 3. CORREGIR EXAMEN DEL ALUMNO
        int aciertos = 0;
        int fallos = 0;
        StringBuilder reporteFallos = new StringBuilder("DETALLE DE ERRORES:\n\n");
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaAlumno))) {
            br.readLine(); // Saltar cabecera
            String linea;
            
            while ((linea = br.readLine()) != null) {
            	// CAMBIO : Usamos coma como separador
                String[] partes = linea.split(",");
               // CAMBIO : las preguntas llevan numero
                if (partes.length >= 3) {
                    String preguntaAlu = limpiar(partes[1]);
                    String respuestaAlu = limpiar(partes[2]);
                    
                    String respuestaCorrecta = mapaSoluciones.get(preguntaAlu);
                    
                    if (respuestaCorrecta != null) {
                        // Comparamos de forma segura (evitando errores si la respuesta es corta)
                        if (esCorrecta(respuestaAlu, respuestaCorrecta)) {
                            aciertos++;
                        } else {
                            fallos++; 
                            reporteFallos.append("- ").append(preguntaAlu).append("\n");
                            reporteFallos.append("  Tuya: ").append(respuestaAlu).append("\n");
                            reporteFallos.append("  Correcta: ").append(respuestaCorrecta).append("\n\n");
                        }
                    }
                }
            }
            
            // 4. INFORME FINAL
            mostrarResultados(aciertos, fallos, reporteFallos);

        } catch (IOException e) {
            System.err.println("Error al procesar el examen: " + e.getMessage());
        }
    }

    /**
     * Compara las respuestas mirando solo el inicio (ej: "A)")
     */
    private static boolean esCorrecta(String resAlu, String resCor) {
        if (resAlu.length() < 2 || resCor.length() < 2) {
            return resAlu.equalsIgnoreCase(resCor);
        }
        // Comparamos los dos primeros caracteres (ej: "A)" contra "A)")
        return resAlu.substring(0, 2).equalsIgnoreCase(resCor.substring(0, 2));
    }

    private static String limpiar(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "").trim();
    }

    private static void mostrarResultados(int aciertos, int fallos, StringBuilder reporte) {
        int total = aciertos + fallos;
        double nota = (total == 0) ? 0 : (double) aciertos * 10 / total;
        
        String resumen = "TOTAL PREGUNTAS: " + total + 
                         "\nACIERTOS: " + aciertos + 
                         "\nFALLOS: " + fallos + 
                         "\nNOTA FINAL: " + String.format("%.2f", nota) + "/10";
        
        System.out.println(resumen);
        if (fallos > 00) {
        	System.out.println(reporte.toString());
        }else if (fallos > 0) {
            JOptionPane.showMessageDialog(null, reporte.toString(), "Revisión de Errores", JOptionPane.WARNING_MESSAGE);
        }
        //JOptionPane.showMessageDialog(null, resumen, "Nota Final", JOptionPane.INFORMATION_MESSAGE);
    }
}