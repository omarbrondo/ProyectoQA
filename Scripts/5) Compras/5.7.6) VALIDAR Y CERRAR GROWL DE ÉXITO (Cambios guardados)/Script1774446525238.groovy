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
// 11) VALIDAR Y CERRAR GROWL DE ÉXITO ("Cambios guardados")
// ===============================
TestObject growlGuardado = new TestObject('growlGuardado')
growlGuardado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlExito = new TestObject('btnCerrarGrowlExito')
btnCerrarGrowlExito.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlGuardado, 15)
String textoGrowlExito = WebUI.getText(growlGuardado).trim()

if (textoGrowlExito.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Se visualizó el mensaje '" + textoGrowlExito + "'.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: Se esperaba 'Cambios guardados' pero apareció: " + textoGrowlExito)
}

WebUI.waitForElementClickable(btnCerrarGrowlExito, 5)
WebUI.click(btnCerrarGrowlExito)
WebUI.comment("✔ Se cerró el growl de éxito.")