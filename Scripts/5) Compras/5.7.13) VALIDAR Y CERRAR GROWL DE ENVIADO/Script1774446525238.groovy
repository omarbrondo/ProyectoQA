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
// 19) VALIDAR Y CERRAR GROWL DE "ENVIADO"
// ===============================

TestObject growlEnviado = new TestObject('growlEnviado')
growlEnviado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEnviado = new TestObject('btnCerrarGrowlEnviado')
btnCerrarGrowlEnviado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEnviado, 10)
String textoGrowlEnviado = WebUI.getText(growlEnviado).trim()

if (textoGrowlEnviado.contains("Enviado") || textoGrowlEnviado.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito de la asociación.")
}

WebUI.waitForElementClickable(btnCerrarGrowlEnviado, 5)
WebUI.click(btnCerrarGrowlEnviado)
WebUI.delay(1)