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
// 9) INGRESAR A CONFIGURACIÓN DE LA COMPRA CLONADA
// ===============================

// --- (Opcional) TRUCO: Clic neutro para cerrar menús superpuestos de usuario ---
TestObject clicNeutro = new TestObject('clicNeutro')
clicNeutro.addProperty("xpath", ConditionType.EQUALS, "//strong[normalize-space(text())='SIN INICIAR']")
// Usamos OPTIONAL por si el estado cambió o no está visible, que no rompa el test
WebUI.click(clicNeutro, FailureHandling.OPTIONAL)
WebUI.delay(1)


// --- 9.1) Clic en el menú Ellipsis ---
TestObject btnMenuEllipsisConfig = new TestObject('btnMenuEllipsisConfig')
btnMenuEllipsisConfig.addProperty("id", ConditionType.EQUALS, "menu-ellipsis")

WebUI.waitForElementClickable(btnMenuEllipsisConfig, 10)
WebUI.click(btnMenuEllipsisConfig)


// --- 9.2) Elegir la opción "Configuración" ---
TestObject opcionConfiguracion = new TestObject('opcionConfiguracion')
opcionConfiguracion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'show')]//a[contains(@class, 'text-secondary') and contains(normalize-space(.), 'Configuración')]")

WebUI.waitForElementPresent(opcionConfiguracion, 5)

// ¡La vieja confiable! Clic con JS para saltarnos el bloqueo de la UI
WebElement elConfig = WebUI.findWebElement(opcionConfiguracion)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elConfig))

WebUI.comment("✔ Se seleccionó 'Configuración'. Entrando a la vista de edición...")


// --- 9.3) Esperar a que cargue la nueva pantalla ---
WebUI.waitForPageLoad(15)
WebUI.delay(2) // Breve pausa para que termine de renderizar el formulario