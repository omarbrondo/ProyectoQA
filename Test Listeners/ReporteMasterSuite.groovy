import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory // <-- NUEVO: Para saber el navegador
import java.io.FileOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

// Librerías de iText
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Font
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Image
import com.itextpdf.text.Chunk
import com.itextpdf.text.pdf.PdfPTable // <-- NUEVO: Para crear tablas
import com.itextpdf.text.pdf.PdfPCell  // <-- NUEVO: Para las celdas de las tablas

class ReporteMasterSuite {

	def listaResultados = []
	String rutaDirectorio = ""
	long tiempoInicio = 0 

	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
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
		String nombreTest = testCaseContext.getTestCaseId()
		String nombreLimpio = nombreTest.substring(nombreTest.lastIndexOf('/') + 1)
		String estadoTest = testCaseContext.getTestCaseStatus()
		String rutaCaptura = ""
		
		// 1. Capturamos el Navegador
		String navegador = "Desconocido"
		try {
			navegador = DriverFactory.getExecutedBrowser().getName()
		} catch (Exception e) {} // Por si es un test de API sin navegador
		
		// 2. Capturamos el mensaje de error técnico exacto
		String errorMsg = ""
		if(estadoTest.equals("FAILED") || estadoTest.equals("ERROR")) {
			def mensajeOriginal = testCaseContext.getMessage()
			errorMsg = mensajeOriginal != null ? mensajeOriginal : "Error desconocido"
			
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
			"foto": rutaCaptura,
			"navegador": navegador,
			"error": errorMsg
		])
	}

	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		long tiempoFin = System.currentTimeMillis()
		long tiempoTotalMillis = tiempoFin - tiempoInicio
		long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempoTotalMillis)
		long segundos = TimeUnit.MILLISECONDS.toSeconds(tiempoTotalMillis) - TimeUnit.MINUTES.toSeconds(minutos)
		String tiempoEjecucion = String.format("%02d min, %02d seg", minutos, segundos)

		String nombreSuite = testSuiteContext.getTestSuiteId()
		String nombreLimpioSuite = nombreSuite.substring(nombreSuite.lastIndexOf('/') + 1)
		
		SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss")
		String timestamp = fileFormatter.format(new Date())
		String rutaArchivoPDF = rutaDirectorio + "/Reporte_SUITE_" + nombreLimpioSuite + "_" + timestamp + ".pdf"

		try {
			Document document = new Document()
			PdfWriter.getInstance(document, new FileOutputStream(rutaArchivoPDF))
			document.open()

			Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY)
			Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK)
			Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK) // Achicamos un poco la letra para la tabla
			Font fuenteChica = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.DARK_GRAY)
			Font fuenteVerde = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.GREEN)
			Font fuenteRoja = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED)

			// ================= LOGO DE LA EMPRESA =================
			try {
				// Puedes cambiar esta URL por la del logo real de tu empresa (ej: formato PNG)
				String urlLogo = "https://tse2.mm.bing.net/th/id/OIP.JJcyrvUOMh__EJMAhkWQwAAAAA?rs=1&pid=ImgDetMain&o=7&rm=3"
				Image logo = Image.getInstance(new java.net.URL(urlLogo))
				logo.setAlignment(Element.ALIGN_RIGHT)
				logo.scaleToFit(120, 50)
				document.add(logo)
			} catch (Exception exLogo) {
				println("No se pudo cargar el logo.")
			}
			// =======================================================

			Paragraph titulo = new Paragraph("Reporte Consolidado de Test Suite", fuenteTitulo)
			titulo.setAlignment(Element.ALIGN_CENTER)
			document.add(titulo)
			
			document.add(new Paragraph(" "))
			document.add(new Paragraph("Datos Generales:", fuenteSubtitulo))
			document.add(new Paragraph("▶ Nombre de la Suite: " + nombreLimpioSuite, fuenteNormal))
			document.add(new Paragraph("▶ Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), fuenteNormal))
			document.add(new Paragraph("▶ Tests ejecutados: " + listaResultados.size(), fuenteNormal))
			document.add(new Paragraph("▶ Tiempo total: " + tiempoEjecucion, fuenteNormal))
			document.add(new Paragraph(" "))
			
			int totalPasados = listaResultados.count { it.estado.equals("PASSED") }
			int totalFallados = listaResultados.size() - totalPasados
			
			try {
				String configGrafico = "{type:'pie',data:{labels:['PASSED (" + totalPasados + ")','FAILED (" + totalFallados + ")'],datasets:[{data:[" + totalPasados + "," + totalFallados + "],backgroundColor:['rgb(0, 180, 0)','rgb(220, 0, 0)']}]}}"
				String urlGrafico = "https://quickchart.io/chart?w=350&h=200&c=" + java.net.URLEncoder.encode(configGrafico, "UTF-8")
				Image imagenPieChart = Image.getInstance(new java.net.URL(urlGrafico))
				imagenPieChart.scaleToFit(200, 120) 
				imagenPieChart.setAlignment(Element.ALIGN_CENTER)
				document.add(imagenPieChart)
			} catch (Exception exGrafico) {}
			
			document.add(new Paragraph(" "))
			
			// ================= TABLA DE RESULTADOS =================
			document.add(new Paragraph("Detalle de Ejecución:", fuenteSubtitulo))
			document.add(new Paragraph(" "))

			// Crear tabla de 3 columnas
			PdfPTable tabla = new PdfPTable(3)
			tabla.setWidthPercentage(100)
			tabla.setWidths([35, 45, 20] as float[]) // Proporción de ancho de columnas

			// Encabezados grises
			def headers = ["Test Case", "Detalles / Error", "Estado"]
			headers.each { texto ->
				PdfPCell celda = new PdfPCell(new Paragraph(texto, fuenteSubtitulo))
				celda.setBackgroundColor(BaseColor.LIGHT_GRAY)
				celda.setPadding(5)
				celda.setHorizontalAlignment(Element.ALIGN_CENTER)
				tabla.addCell(celda)
			}

			// Llenar datos
			for (def resultado : listaResultados) {
				// Columna 1: Nombre
				PdfPCell celdaNombre = new PdfPCell(new Paragraph(resultado.nombre, fuenteNormal))
				celdaNombre.setPadding(5)
				tabla.addCell(celdaNombre)

				// Columna 2: Detalles (Navegador y Error si aplica)
				Paragraph pDetalles = new Paragraph("Navegador: " + resultado.navegador, fuenteChica)
				if (resultado.estado.equals("FAILED") || resultado.estado.equals("ERROR")) {
					pDetalles.add(new Paragraph("Error: " + resultado.error, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.RED)))
				}
				PdfPCell celdaDetalles = new PdfPCell(pDetalles)
				celdaDetalles.setPadding(5)
				tabla.addCell(celdaDetalles)

				// Columna 3: Estado y Foto
				PdfPCell celdaEstado = new PdfPCell()
				celdaEstado.setPadding(5)
				celdaEstado.setHorizontalAlignment(Element.ALIGN_CENTER)
				
				if (resultado.estado.equals("PASSED")) {
					celdaEstado.addElement(new Paragraph("PASSED", fuenteVerde))
				} else {
					celdaEstado.addElement(new Paragraph("FAILED", fuenteRoja))
					if (!resultado.foto.equals("") && !resultado.foto.equals("ERROR_CAPTURA")) {
						try {
							Image imgMini = Image.getInstance(resultado.foto)
							imgMini.scaleToFit(100, 100) // Foto chiquita dentro de la tabla
							imgMini.setAlignment(Element.ALIGN_CENTER)
							celdaEstado.addElement(imgMini)
						} catch (Exception e) {}
					}
				}
				tabla.addCell(celdaEstado)
			}

			document.add(tabla) // Agregamos la tabla terminada al documento
			// =======================================================

			document.close()
			println("✅ Reporte de SUITE v2 generado con éxito en: " + rutaArchivoPDF)
			
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