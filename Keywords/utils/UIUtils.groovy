package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

class UIUtils {

	@Keyword
	def cerrarDropdownUsuarioSiEstaAbierto() {

		// ===============================
		// 1) DEFINIR ELEMENTO H3
		// ===============================
		TestObject tituloH3 = new TestObject()
		tituloH3.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//h3"
				)

		// ===============================
		// 2) INTENTAR CLICKEAR EL H3 (SI EXISTE)
		// ===============================
		if (WebUI.verifyElementPresent(tituloH3, 3, FailureHandling.OPTIONAL)) {
			WebUI.click(tituloH3)
		}
	}
}
