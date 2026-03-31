import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.FileOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit // <-- NUEVA: Para calcular el tiempo de ejecución

// Librerías de iText
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Font
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Image
import com.itextpdf.text.Chunk 

class ReporteMasterSuite {

	// Esta lista guardará los resultados de todos los tests mientras corre la suite
	def listaResultados = []
	String rutaDirectorio = ""
	long tiempoInicio = 0 // <-- NUEVA: Variable para nuestro cronómetro

	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		// 1. Iniciar cronómetro y preparar terreno
		tiempoInicio = System.currentTimeMillis() 
		listaResultados.clear() 
		
		String directorioProyecto = RunConfiguration.getProjectDir()
		rutaDirectorio = directorioProyecto + "/Reportes_PDF"
		
		File directorio = new File(rutaDirectorio)
		if (!directorio.exists()){
			directorio.mkdir() 
		}
	}

	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		// 2. Tomamos nota de cada test
		String nombreTest = testCaseContext.getTestCaseId()
		String nombreLimpio = nombreTest.substring(nombreTest.lastIndexOf('/') + 1)
		String estadoTest = testCaseContext.getTestCaseStatus()
		String rutaCaptura = ""

		if(estadoTest.equals("FAILED") || estadoTest.equals("ERROR")) {
			rutaCaptura = rutaDirectorio + "/Error_" + nombreLimpio + "_" + System.currentTimeMillis() + ".png"
			try {
				WebUI.takeScreenshot(rutaCaptura)
				Thread.sleep(1000)
			} catch (Exception e) {
				rutaCaptura = "ERROR_CAPTURA"
			}
		}

		listaResultados.add([
			"nombre": nombreLimpio,
			"estado": estadoTest,
			"foto": rutaCaptura
		])
	}

	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		
		// 👇================ CÁLCULO DEL TIEMPO ================👇
		long tiempoFin = System.currentTimeMillis()
		long tiempoTotalMillis = tiempoFin - tiempoInicio
		long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempoTotalMillis)
		long segundos = TimeUnit.MILLISECONDS.toSeconds(tiempoTotalMillis) - TimeUnit.MINUTES.toSeconds(minutos)
		String tiempoEjecucion = String.format("%02d min, %02d seg", minutos, segundos)
		// 👆===================================================👆

		String nombreSuite = testSuiteContext.getTestSuiteId()
		String nombreLimpioSuite = nombreSuite.substring(nombreSuite.lastIndexOf('/') + 1)
		
		SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss")
		String timestamp = fileFormatter.format(new Date())
		String rutaArchivoPDF = rutaDirectorio + "/Reporte_SUITE_" + nombreLimpioSuite + "_" + timestamp + ".pdf"

		try {
			Document document = new Document()
			PdfWriter.getInstance(document, new FileOutputStream(rutaArchivoPDF))
			document.open()

			// Estilos
			Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY)
			Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK)
			Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK)
			Font fuenteVerde = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GREEN)
			Font fuenteRoja = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED)

			// Encabezado
			Paragraph titulo = new Paragraph("Reporte Consolidado de Test Suite", fuenteTitulo)
			titulo.setAlignment(Element.ALIGN_CENTER)
			document.add(titulo)
			
			document.add(new Paragraph(" "))
			document.add(new Paragraph("Datos Generales:", fuenteSubtitulo))
			document.add(new Paragraph("▶ Nombre de la Suite: " + nombreLimpioSuite, fuenteNormal))
			document.add(new Paragraph("▶ Fecha de finalización: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), fuenteNormal))
			document.add(new Paragraph("▶ Total de Test Cases ejecutados: " + listaResultados.size(), fuenteNormal))
			document.add(new Paragraph("▶ Tiempo de ejecución: " + tiempoEjecucion, fuenteNormal)) // <-- AGREGADO AL REPORTE
			document.add(new Paragraph(" "))
			document.add(new Paragraph("------------------------------------------------------------------------------------------------", fuenteNormal))
			document.add(new Paragraph(" "))
			
			// ================= GRÁFICO CIRCULAR =================
			int totalPasados = listaResultados.count { it.estado.equals("PASSED") }
			int totalFallados = listaResultados.size() - totalPasados
			
			try {
				String configGrafico = "{type:'pie',data:{labels:['PASSED (" + totalPasados + ")','FAILED (" + totalFallados + ")'],datasets:[{data:[" + totalPasados + "," + totalFallados + "],backgroundColor:['rgb(0, 180, 0)','rgb(220, 0, 0)']}]}}"
				String urlGrafico = "https://quickchart.io/chart?w=350&h=200&c=" + java.net.URLEncoder.encode(configGrafico, "UTF-8")
				
				Image imagenPieChart = Image.getInstance(new java.net.URL(urlGrafico))
				imagenPieChart.scaleToFit(250, 150) // <-- AJUSTE DE TAMAÑO DEL GRÁFICO
				imagenPieChart.setAlignment(Element.ALIGN_CENTER)
				document.add(imagenPieChart)
				
				document.add(new Paragraph(" ")) 
				
			} catch (Exception exGrafico) {
				document.add(new Paragraph("(Gráfico no disponible. Comprueba conexión a internet)", fuenteNormal))
				println("⚠️ Aviso: No se pudo generar el gráfico - " + exGrafico.getMessage())
			}
			// =====================================================
			
			document.add(new Paragraph("Detalle por Test Case:", fuenteSubtitulo))
			document.add(new Paragraph(" "))

			for (def resultado : listaResultados) {
				
				Paragraph lineaTest = new Paragraph()
				lineaTest.add(new Chunk("📝 " + resultado.nombre + "  ............  ", fuenteNormal))
				
				if (resultado.estado.equals("PASSED")) {
					lineaTest.add(new Chunk("PASSED", fuenteVerde))
					document.add(lineaTest)
				} else {
					lineaTest.add(new Chunk(resultado.estado, fuenteRoja))
					document.add(lineaTest)
					
					if (!resultado.foto.equals("") && !resultado.foto.equals("ERROR_CAPTURA")) {
						document.add(new Paragraph("      ⚠️ Evidencia visual:", fuenteRoja))
						try {
							Image imagenFallo = Image.getInstance(resultado.foto)
							imagenFallo.scaleToFit(400, 400) 
							imagenFallo.setAlignment(Element.ALIGN_CENTER)
							document.add(imagenFallo)
						} catch (Exception ex) {
							document.add(new Paragraph("      (Error al incrustar la imagen)", fuenteNormal))
						}
					}
				}
				document.add(new Paragraph(" ")) 
			}

			document.close()
			println("✅ Reporte de SUITE generado con éxito en: " + rutaArchivoPDF)
			
			for (def resultado : listaResultados) {
				if (!resultado.foto.equals("") && !resultado.foto.equals("ERROR_CAPTURA")) {
					File archivoImagen = new File(resultado.foto)
					if(archivoImagen.exists()) archivoImagen.delete()
				}
			}

		} catch (Exception e) {
			println("❌ Error crítico al generar reporte de Suite: " + e.getMessage())
		}
	}
}