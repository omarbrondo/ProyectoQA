import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory 
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
import com.itextpdf.text.pdf.PdfPTable 
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.draw.LineSeparator 
import com.itextpdf.text.Rectangle 

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
		
		String navegador = "Desconocido"
		try {
			navegador = DriverFactory.getExecutedBrowser().getName()
		} catch (Exception e) {} 
		
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

		String usuarioEjecutor = System.getProperty("user.name")
		String sistemaOperativo = System.getProperty("os.name")

		String nombreSuite = testSuiteContext.getTestSuiteId()
		String nombreLimpioSuite = nombreSuite.substring(nombreSuite.lastIndexOf('/') + 1)
		
		SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss")
		String timestamp = fileFormatter.format(new Date())
		String rutaArchivoPDF = rutaDirectorio + "/Reporte_SUITE_" + nombreLimpioSuite + "_" + timestamp + ".pdf"

		try {
			Document document = new Document()
			PdfWriter.getInstance(document, new FileOutputStream(rutaArchivoPDF))
			document.open()

			// ================= PALETA DE COLORES Y FUENTES PREMIUM =================
			BaseColor colorPrimario = new BaseColor(24, 114, 240) 
			BaseColor colorFondoCard = new BaseColor(245, 247, 250) 
			
			Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, colorPrimario)
			Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY)
			Font fuenteCabeceraTabla = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE)
			Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY) 
			Font fuenteChica = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GRAY)
			Font fuenteVerde = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(10, 160, 60)) 
			Font fuenteRoja = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(220, 30, 30))
			// =========================================================================

			// LOGO LOCAL
			try {
				String rutaLogo = RunConfiguration.getProjectDir() + "/Imagenes/LogoIntiza.png"
				File archivoLogo = new File(rutaLogo)
				
				if (archivoLogo.exists()) {
					Image logo = Image.getInstance(rutaLogo)
					logo.setAlignment(Element.ALIGN_RIGHT)
					logo.scaleToFit(140, 60) 
					document.add(logo)
				}
			} catch (Exception exLogo) {}

			// TÍTULO PRINCIPAL
			Paragraph titulo = new Paragraph("Reporte de Automatización", fuenteTitulo)
			titulo.setAlignment(Element.ALIGN_CENTER)
			titulo.setSpacingAfter(10)
			document.add(titulo)
			
			// LÍNEA SEPARADORA ELEGANTE
			LineSeparator lineaDivisoria = new LineSeparator(1.5f, 100f, colorPrimario, Element.ALIGN_CENTER, -2f)
			document.add(new Chunk(lineaDivisoria))
			document.add(new Paragraph(" ")) 
			
			// ================= TARJETA DE RESUMEN (CARD) =================
			PdfPTable tablaResumen = new PdfPTable(1)
			tablaResumen.setWidthPercentage(100)
			tablaResumen.setSpacingBefore(10)
			tablaResumen.setSpacingAfter(20)

			PdfPCell celdaResumen = new PdfPCell()
			celdaResumen.setBackgroundColor(colorFondoCard)
			celdaResumen.setBorderColor(colorPrimario)
			celdaResumen.setBorderWidthLeft(4f) 
			celdaResumen.setBorderWidthRight(0f)
			celdaResumen.setBorderWidthTop(0f)
			celdaResumen.setBorderWidthBottom(0f)
			celdaResumen.setPadding(15f)

			celdaResumen.addElement(new Paragraph("📋 Datos de la Ejecución", fuenteSubtitulo))
			celdaResumen.addElement(new Paragraph(" "))
			celdaResumen.addElement(new Paragraph("Suite de Pruebas:   " + nombreLimpioSuite, fuenteNormal))
			celdaResumen.addElement(new Paragraph("Fecha y Hora:        " + new SimpleDateFormat("dd/MM/yyyy • HH:mm").format(new Date()), fuenteNormal))
			celdaResumen.addElement(new Paragraph("Entorno (OS):         " + sistemaOperativo + " (" + usuarioEjecutor + ")", fuenteNormal))
			celdaResumen.addElement(new Paragraph("Tiempo de Vuelo:   " + tiempoEjecucion, fuenteNormal))
			celdaResumen.addElement(new Paragraph("Total Evaluados:     " + listaResultados.size() + " Test Cases", fuenteNormal))
			
			tablaResumen.addCell(celdaResumen)
			document.add(tablaResumen)
			// ==============================================================
			
			// ================= CÁLCULO DE PORCENTAJES PARA EL GRÁFICO =================
			int totalTests = listaResultados.size()
			int totalPasados = listaResultados.count { it.estado.equals("PASSED") }
			int totalFallados = totalTests - totalPasados
			
			// Hacemos el cálculo de porcentajes protegiendo contra división por cero
			int pctPasados = totalTests > 0 ? Math.round((totalPasados * 100.0f) / totalTests) : 0
			int pctFallados = totalTests > 0 ? (100 - pctPasados) : 0 // Restamos para asegurar que siempre sume exacto 100%
			
			// Armamos las etiquetas hermosas con cantidad y porcentaje
			String labelPasados = "PASSED: " + totalPasados + " (" + pctPasados + "%)"
			String labelFallados = "FAILED: " + totalFallados + " (" + pctFallados + "%)"
			// =========================================================================

			// GRÁFICO
			try {
				// Inyectamos nuestras nuevas etiquetas con porcentajes al JSON del gráfico
				String configGrafico = "{type:'pie',data:{labels:['" + labelPasados + "','" + labelFallados + "'],datasets:[{data:[" + totalPasados + "," + totalFallados + "],backgroundColor:['rgb(10, 160, 60)','rgb(220, 30, 30)']}]}}"
				String urlGrafico = "https://quickchart.io/chart?w=400&h=200&c=" + java.net.URLEncoder.encode(configGrafico, "UTF-8")
				Image imagenPieChart = Image.getInstance(new java.net.URL(urlGrafico))
				imagenPieChart.scaleToFit(220, 130) 
				imagenPieChart.setAlignment(Element.ALIGN_CENTER)
				imagenPieChart.setSpacingAfter(20) 
				document.add(imagenPieChart)
			} catch (Exception exGrafico) {}
			
			// TABLA PRINCIPAL
			Paragraph tituloTabla = new Paragraph("Desglose de Test Cases", fuenteSubtitulo)
			tituloTabla.setSpacingAfter(10)
			document.add(tituloTabla)

			PdfPTable tabla = new PdfPTable(3)
			tabla.setWidthPercentage(100)
			tabla.setWidths([35, 45, 20] as float[]) 

			// CABECERAS AZULES
			def headers = ["Nombre del Test", "Detalles / Trazabilidad", "Estado"]
			headers.each { texto ->
				PdfPCell celda = new PdfPCell(new Paragraph(texto, fuenteCabeceraTabla))
				celda.setBackgroundColor(colorPrimario) 
				celda.setBorderColor(BaseColor.WHITE)
				celda.setPaddingTop(8)
				celda.setPaddingBottom(8)
				celda.setHorizontalAlignment(Element.ALIGN_CENTER)
				tabla.addCell(celda)
			}

			for (def resultado : listaResultados) {
				// Celda Nombre
				PdfPCell celdaNombre = new PdfPCell(new Paragraph(resultado.nombre, fuenteNormal))
				celdaNombre.setPadding(8)
				celdaNombre.setBorderColor(BaseColor.LIGHT_GRAY)
				tabla.addCell(celdaNombre)

				// Celda Detalles
				Paragraph pDetalles = new Paragraph("Navegador: " + resultado.navegador, fuenteChica)
				if (resultado.estado.equals("FAILED") || resultado.estado.equals("ERROR")) {
					pDetalles.add(new Paragraph("Causa: " + resultado.error, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, fuenteRoja.getColor())))
				}
				PdfPCell celdaDetalles = new PdfPCell(pDetalles)
				celdaDetalles.setPadding(8)
				celdaDetalles.setBorderColor(BaseColor.LIGHT_GRAY)
				tabla.addCell(celdaDetalles)

				// Celda Estado
				PdfPCell celdaEstado = new PdfPCell()
				celdaEstado.setPadding(8)
				celdaEstado.setBorderColor(BaseColor.LIGHT_GRAY)
				celdaEstado.setHorizontalAlignment(Element.ALIGN_CENTER)
				celdaEstado.setVerticalAlignment(Element.ALIGN_MIDDLE)
				
				if (resultado.estado.equals("PASSED")) {
					celdaEstado.addElement(new Paragraph("PASSED", fuenteVerde))
				} else {
					celdaEstado.addElement(new Paragraph("FAILED", fuenteRoja))
					Paragraph avisoAnexo = new Paragraph("🔍 Ver Anexo", fuenteChica)
					avisoAnexo.setAlignment(Element.ALIGN_CENTER)
					celdaEstado.addElement(avisoAnexo)
				}
				tabla.addCell(celdaEstado)
			}
			document.add(tabla) 
			
			// ================= SECCIÓN DE ANEXO =================
			boolean hayFallos = listaResultados.any { it.estado.equals("FAILED") || it.estado.equals("ERROR") }
			
			if (hayFallos) {
				document.newPage() 
				
				Paragraph tituloAnexo = new Paragraph("Anexo: Evidencia Visual", fuenteTitulo)
				tituloAnexo.setAlignment(Element.ALIGN_CENTER)
				document.add(tituloAnexo)
				document.add(new Chunk(lineaDivisoria)) 
				document.add(new Paragraph(" "))
				
				for (def resultado : listaResultados) {
					if ((resultado.estado.equals("FAILED") || resultado.estado.equals("ERROR")) && !resultado.foto.equals("") && !resultado.foto.equals("ERROR_CAPTURA")) {
						
						Paragraph nombreFallo = new Paragraph("📸 Test Case: " + resultado.nombre, fuenteSubtitulo)
						nombreFallo.setSpacingBefore(15)
						nombreFallo.setSpacingAfter(10)
						document.add(nombreFallo)
						
						try {
							Image imgGrande = Image.getInstance(resultado.foto)
							imgGrande.scaleToFit(500, 600) 
							imgGrande.setAlignment(Element.ALIGN_CENTER)
							
							imgGrande.setBorder(Rectangle.BOX)
							imgGrande.setBorderColor(BaseColor.LIGHT_GRAY)
							imgGrande.setBorderWidth(1f)
							
							document.add(imgGrande)
						} catch (Exception e) {
							println("❌ Error al incrustar imagen en el anexo: " + e.getMessage())
						}
						
						document.add(new Paragraph(" "))
					}
				}
			}
			// ====================================================================

			document.close()
			println("✅ Reporte Premium generado en: " + rutaArchivoPDF)
			
			for (def resultado : listaResultados) {
				if (!resultado.foto.equals("") && !resultado.foto.equals("ERROR_CAPTURA")) {
					File archivoImagen = new File(resultado.foto)
					if(archivoImagen.exists()) archivoImagen.delete()
				}
			}

		} catch (Exception e) {
			println("❌ Error crítico al generar reporte: " + e.getMessage())
		}
	}
}