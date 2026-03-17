package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TextoUtils {

	@Keyword
	def escribirTextoDinamico(TestObject campo, String textoBase = "Test QA") {

		// Generar fecha y hora actual
		LocalDateTime ahora = LocalDateTime.now()
		String fechaHora = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

		// Texto final
		String textoFinal = textoBase + " – " + fechaHora

		// Escribir en el campo
		WebUI.waitForElementVisible(campo, 10)
		WebUI.setText(campo, textoFinal)
	}
}
