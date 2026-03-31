import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration // <-- NUEVA IMPORTACIÓN
import java.io.FileOutputStream
import java.io.File
import java.text.SimpleDateFormat

// Librerías de iText
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Font
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Image

class GeneradorReportesPDF {

	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		
		String nombreTest = testCaseContext.getTestCaseId()
		String estadoTest = testCaseContext.getTestCaseStatus() 
		String usuarioEjecutor = System.getProperty("user.name")
		String sistemaOperativo = System.getProperty("os.name")
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
		String fechaHora = formatter.format(new Date())

		SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss")
		String timestampArchivo = fileFormatter.format(new Date())
		
		String nombreLimpio = nombreTest.substring(nombreTest.lastIndexOf('/') + 1)
		
		// --- LA SOLUCIÓN: Usar la ruta absoluta del proyecto ---
		String directorioProyecto = RunConfiguration.getProjectDir()
		String rutaDirectorio = directorioProyecto + "/Reportes_PDF"
		// -------------------------------------------------------
		
		File directorio = new File(rutaDirectorio)
		if (!directorio.exists()){
			directorio.mkdir() 
		}
		
		String rutaArchivo = rutaDirectorio + "/Reporte_" + nombreLimpio + "_" + timestampArchivo + ".pdf"
		String rutaCaptura = rutaDirectorio + "/Error_" + timestampArchivo + ".png"

		try {
			Document document = new Document()
			PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo))
			document.open()

			Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY)
			Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK)
			Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK)
			
			BaseColor colorEstado = estadoTest.equals("PASSED") ? BaseColor.GREEN : BaseColor.RED
			Font fuenteEstado = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, colorEstado)

			Paragraph titulo = new Paragraph("Reporte de Ejecución de Test Case", fuenteTitulo)
			titulo.setAlignment(Element.ALIGN_CENTER)
			document.add(titulo)
			
			document.add(new Paragraph(" ")) 
			document.add(new Paragraph("Resumen de Ejecución:", fuenteSubtitulo))
			document.add(new Paragraph(" ")) 
			
			document.add(new Paragraph("▶ Nombre del Test: " + nombreLimpio, fuenteNormal))
			document.add(new Paragraph("▶ Ruta Interna: " + nombreTest, fuenteNormal))
			
			Paragraph parrafoEstado = new Paragraph("▶ Estado Final: ")
			parrafoEstado.add(new Paragraph(estadoTest, fuenteEstado))
			document.add(parrafoEstado)
			
			document.add(new Paragraph("▶ Fecha y Hora: " + fechaHora, fuenteNormal))
			document.add(new Paragraph("▶ Usuario: " + usuarioEjecutor, fuenteNormal))
			document.add(new Paragraph("▶ Sistema Operativo: " + sistemaOperativo, fuenteNormal))
			
			if(estadoTest.equals("FAILED") || estadoTest.equals("ERROR")) {
				document.add(new Paragraph(" ")) 
				document.add(new Paragraph("⚠️ ATENCIÓN: El Test Case ha fallado. Evidencia visual a continuación:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED)))
				document.add(new Paragraph(" ")) 
				
				try {
					// Tomar la captura con la ruta absoluta
					WebUI.takeScreenshot(rutaCaptura)
					
					// Esperar 1 segundo para asegurar que Windows termine de guardar el archivo físico
					Thread.sleep(1000)
					
					Image imagenFallo = Image.getInstance(rutaCaptura)
					imagenFallo.scaleToFit(500, 500) 
					imagenFallo.setAlignment(Element.ALIGN_CENTER)
					document.add(imagenFallo)
					
				} catch (Exception exImagen) {
					// Ahora sí, imprimimos el error REAL si es que falla
					document.add(new Paragraph("(Error técnico al tomar o incrustar la captura: " + exImagen.getMessage() + ")", fuenteNormal))
				}
			}

			document.close()
			println("✅ Reporte PDF generado exitosamente en: " + rutaArchivo)
			
			File archivoImagen = new File(rutaCaptura)
			if(archivoImagen.exists()) {
				archivoImagen.delete()
			}

		} catch (Exception e) {
			println("❌ Error al generar el PDF: " + e.getMessage())
		}
	}
}