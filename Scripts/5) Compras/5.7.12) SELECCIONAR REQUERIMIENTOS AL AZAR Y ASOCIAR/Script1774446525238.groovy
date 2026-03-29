import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import org.openqa.selenium.Keys
// IMPORTANTE: Esta importación permite leer y escribir en el Profile
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement
import java.util.Arrays
import java.util.Date
import java.util.Random
import java.util.Collections



// ===============================
// 18) SELECCIONAR REQUERIMIENTOS AL AZAR Y ASOCIAR
// ===============================

// --- 18.1) Esperar que el modal y la tabla carguen ---
TestObject modalRequerimientos = new TestObject('modalRequerimientos')
modalRequerimientos.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content') and .//h3[contains(text(), 'Seleccione una opción')]]")

WebUI.waitForElementVisible(modalRequerimientos, 15)
WebUI.delay(2) // Breve pausa para que desaparezca el "Cargando..." si es que estaba


// --- 18.2) Obtener todos los checkboxes de requerimientos (líneas) ---
WebDriver driverReq = DriverFactory.getWebDriver()
// Buscamos específicamente los check de las líneas, no los del encabezado del acordeón
List<WebElement> checksRequerimientos = driverReq.findElements(By.xpath("//input[contains(@class, 'prline-checkbox')]"))

if (checksRequerimientos.size() >= 2) {
	// Mezclamos la lista al azar
	Collections.shuffle(checksRequerimientos)
	
	// Hacemos clic en los primeros dos de la lista mezclada
	for (int i = 0; i < 2; i++) {
		WebElement check = checksRequerimientos.get(i)
		// Usamos JS click para evitar que el scroll del modal bloquee el clic
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(check))
	}
	WebUI.comment("✔ Se seleccionaron 2 requerimientos al azar exitosamente.")
	
} else if (checksRequerimientos.size() == 1) {
	// Por si justo la prueba corre en un entorno donde quedó solo 1 requerimiento
	WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(checksRequerimientos.get(0)))
	WebUI.comment("⚠ Solo había 1 requerimiento disponible. Se seleccionó ese.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No hay requerimientos disponibles para asociar en esta vista.")
}


// --- 18.3) Hacer clic en "Asociar" ---
TestObject btnAsociar = new TestObject('btnAsociar')
btnAsociar.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[contains(@class, 'btn-submit-lines') and contains(normalize-space(.), 'Asociar')]")

// Al seleccionar los check, el botón pierde el 'disabled', así que lo esperamos clickeable
WebUI.waitForElementClickable(btnAsociar, 5)
WebUI.click(btnAsociar)

WebUI.comment("✔ Se hizo clic en 'Asociar'. Esperando que se agreguen a la licitación...")

// Esperamos que se cierre el modal y se actualice la pantalla de fondo
WebUI.waitForPageLoad(10)
WebUI.delay(3)