package requerimientos

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.WebElement
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase


class LineasUtils {

	void agregarLinea() {

		// ===============================
		// 1) CLIC EN "Nueva línea"
		// ===============================
		TestObject btnNuevaLinea = new TestObject()
		btnNuevaLinea.addProperty("xpath", ConditionType.EQUALS,
				"//a[contains(@class,'btn-edit-line') and contains(normalize-space(text()),'Nueva linea')]"
				)

		WebUI.waitForElementClickable(btnNuevaLinea, 10)
		WebUI.click(btnNuevaLinea)

		// ===============================
		// 2) SELECCIONAR ÍTEM RANDOM
		// ===============================
		TestObject filasInventario = new TestObject()
		filasInventario.addProperty("xpath", ConditionType.EQUALS,
				"//tr[contains(@class,'btn-assign-inventory')]"
				)

		List<WebElement> listaFilas = WebUI.findWebElements(filasInventario, 10)
		int indiceRandom = new Random().nextInt(listaFilas.size())
		listaFilas.get(indiceRandom).click()

		WebUI.comment("✔ Fila seleccionada al azar: índice " + indiceRandom)

		// ===============================
		// 3) ESPERAR MODAL EDITAR LÍNEA
		// ===============================
		TestObject modalEditarLinea = new TestObject()
		modalEditarLinea.addProperty("xpath", ConditionType.EQUALS,
				"//h3[contains(normalize-space(text()),'Editar linea')]"
				)

		WebUI.waitForElementVisible(modalEditarLinea, 10)

		// ===============================
		// 4) CANTIDAD — CLICK + JS VALUE
		// ===============================
		TestObject campoCantidad = new TestObject()
		campoCantidad.addProperty("xpath", ConditionType.EQUALS,
				"//div[@data-columncode='Quantity']//input[contains(@class,'mask-numeric')]"
				)

		WebUI.waitForElementVisible(campoCantidad, 10)
		WebUI.scrollToElement(campoCantidad, 5)
		WebUI.delay(0.2)

		WebUI.click(campoCantidad)
		WebUI.delay(0.2)

		int cantidadRandom = new Random().nextInt(100) + 1

		WebUI.executeJavaScript(
				"""
            const input = arguments[0];
            input.value = arguments[1];
            input.dispatchEvent(new Event('input', { bubbles: true }));
            input.dispatchEvent(new Event('change', { bubbles: true }));
            """,
				Arrays.asList(WebUI.findWebElement(campoCantidad, 10), cantidadRandom.toString())
				)

		WebUI.comment("✔ Cantidad seleccionada: " + cantidadRandom)

		// ===============================
		// 5) SUBIR ARCHIVO
		// ===============================
		TestObject inputArchivoLinea = new TestObject()
		inputArchivoLinea.addProperty("xpath", ConditionType.EQUALS,
				"//div[@data-columncode='data04']//input[@type='file']"
				)

		String rutaPDF = RunConfiguration.getProjectDir() + "/Include/files/ayuda.pdf"
		WebUI.uploadFile(inputArchivoLinea, rutaPDF)
		WebUI.delay(3)

		// ===============================
		// 6) CLIC EN SIGUIENTE DEL MODAL
		// ===============================
		TestObject btnSiguienteModal = new TestObject()
		btnSiguienteModal.addProperty("xpath", ConditionType.EQUALS,
				"//div[@id='modal-purchaserequestline-edit']//button[contains(@class,'btn-submit-form')]"
				)

		WebUI.waitForElementClickable(btnSiguienteModal, 10)
		WebUI.click(btnSiguienteModal)

		WebUI.comment("✔ Línea agregada correctamente")
	}
}
