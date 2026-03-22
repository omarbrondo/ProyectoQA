import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

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
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import java.util.Arrays




//0) Acceder a compras
WebUI.callTestCase(findTestCase('5) Compras/5.4) Abrir las subsolapas de la compra'), [:], FailureHandling.STOP_ON_FAILURE)



// ===============================
// 9) ENTRAR A CONFIGURACIÓN (CON CLIC FORZADO)
// ===============================

// --- CREACIÓN DE OBJETOS ---
TestObject btnMenuEllipsis = new TestObject('btnMenuEllipsis')
btnMenuEllipsis.addProperty("id", ConditionType.EQUALS, "menu-ellipsis")

TestObject opcionConfiguracion = new TestObject('opcionConfiguracion')
// Ajustamos el XPath para que sea bien específico al menú que está "show"
opcionConfiguracion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'show')]//a[contains(normalize-space(.), 'Configuración')]")

// ---------------------------

// 9.1) Clic en el menú de los tres puntos
WebUI.waitForElementClickable(btnMenuEllipsis, 10)
WebUI.click(btnMenuEllipsis)

// 9.2) Clic en "Configuración" vía JAVASCRIPT
// Primero esperamos que el elemento esté presente en el DOM
WebUI.waitForElementPresent(opcionConfiguracion, 5)

// Ejecutamos el clic de JS para saltarnos el error de interactividad
WebElement elConfig = WebUI.findWebElement(opcionConfiguracion)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elConfig))

WebUI.comment("✔ Se ingresó a Configuración usando JavaScript para evitar bloqueo de UI.")

// Esperamos la carga de la siguiente página
WebUI.waitForPageLoad(10)
WebUI.delay(2)

// ===============================
// 10) NAVEGACIÓN POR EL MENÚ LATERAL DE CONFIGURACIÓN
// ===============================

// Definimos la lista de secciones exactamente como aparecen en el texto del HTML
def seccionesConfig = [
	"Datos Generales",
	"Etapas",
	"Requerimientos",
	"Formularios de oferta",
	"Formularios de evaluación",
	"Evaluadores",
	"Estados de proveedores",
	"Actas de Adjudicación",
	"Correos"
]

seccionesConfig.each { nombreSeccion ->
	// Creamos el objeto dinámico buscando por el texto dentro de la sidebar
	TestObject itemSidebar = new TestObject(nombreSeccion)
	itemSidebar.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), '${nombreSeccion}')]"
	)

	// 10.1) Esperamos a que sea cliqueable
	WebUI.waitForElementClickable(itemSidebar, 10)
	
	// 10.2) Hacemos clic
	WebUI.click(itemSidebar)
	
	WebUI.comment("✔ Navegando a la sección de configuración: " + nombreSeccion)
	
	// 10.3) Como esto suele cargar una vista nueva, esperamos que la página esté lista
	WebUI.waitForPageLoad(10)
	WebUI.delay(1)
}

WebUI.comment("✔ Se recorrieron todas las secciones de configuración exitosamente.")