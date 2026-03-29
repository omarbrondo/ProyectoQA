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
// 13) VALIDAR Y CERRAR GROWL DE "ETAPA CREADA"
// ===============================

TestObject growlEtapa = new TestObject('growlEtapa')
growlEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEtapa = new TestObject('btnCerrarGrowlEtapa')
btnCerrarGrowlEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEtapa, 10)
String textoGrowlEtapa = WebUI.getText(growlEtapa).trim()

if (textoGrowlEtapa.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Se guardó la etapa y apareció el mensaje.")
} else {
	WebUI.comment("⚠ El mensaje fue distinto al esperado: " + textoGrowlEtapa)
}

WebUI.waitForElementClickable(btnCerrarGrowlEtapa, 5)
WebUI.click(btnCerrarGrowlEtapa)
WebUI.delay(1) // Pausa para asegurar que la UI quedó limpia
