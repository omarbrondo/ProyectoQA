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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Engranaje
WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/btn_Engranaje'))

WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/a_Backoffice_dropdown-item'))

WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Company/Index')

// ===============================
// Opcion Extensiones
// ===============================

TestObject opcionExtensiones = new TestObject()
opcionExtensiones.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/Company/Extensions' and normalize-space()='Extensiones']"
)

WebUI.waitForElementClickable(opcionExtensiones, 10)
WebUI.click(opcionExtensiones)

// ===============================
// INGRESAR ".rar" EN EL CAMPO
// ===============================

TestObject inputExtensiones = new TestObject()
inputExtensiones.addProperty(
	"id",
	ConditionType.EQUALS,
	"ExtensionsCsv"
)

WebUI.waitForElementVisible(inputExtensiones, 10)
WebUI.setText(inputExtensiones, ".rar")

// ===============================
// CLICK EN GUARDAR
// ===============================

TestObject btnGuardarExt = new TestObject()
btnGuardarExt.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@type='submit' and .//strong[normalize-space()='GUARDAR']]"
)

WebUI.waitForElementClickable(btnGuardarExt, 10)
WebUI.click(btnGuardarExt)

// ===============================
// VALIDAR "Cambios guardados"
// ===============================

TestObject alertaCambios = new TestObject()
alertaCambios.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success') and contains(normalize-space(),'Cambios guardados')]"
)

WebUI.waitForElementVisible(alertaCambios, 10)
WebUI.verifyElementText(alertaCambios, "Cambios guardados")

// ===============================
// CERRAR EL GROWL
// ===============================

TestObject btnCerrarGrowlCambios = new TestObject()
btnCerrarGrowlCambios.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowlCambios, 10)
WebUI.click(btnCerrarGrowlCambios)
WebUI.delay(1)

// ===============================
// SELECCIONAR "Permitidas"
// ===============================

TestObject radioPermitidas = new TestObject()
radioPermitidas.addProperty(
	"id",
	ConditionType.EQUALS,
	"Allowed"
)

WebUI.waitForElementClickable(radioPermitidas, 10)
WebUI.click(radioPermitidas)

// Guardar nuevamente
WebUI.waitForElementClickable(btnGuardarExt, 10)
WebUI.click(btnGuardarExt)

// Validar growl otra vez
WebUI.waitForElementVisible(alertaCambios, 10)
WebUI.verifyElementText(alertaCambios, "Cambios guardados")

// Cerrar growl
WebUI.waitForElementClickable(btnCerrarGrowlCambios, 10)
WebUI.click(btnCerrarGrowlCambios)
WebUI.delay(1)

// ===============================
// LIMPIAR EL CAMPO Y GUARDAR
// ===============================

WebUI.setText(inputExtensiones, "")

WebUI.waitForElementClickable(btnGuardarExt, 10)
WebUI.click(btnGuardarExt)

// Validar growl
WebUI.waitForElementVisible(alertaCambios, 10)
WebUI.verifyElementText(alertaCambios, "Cambios guardados")

// Cerrar growl
WebUI.waitForElementClickable(btnCerrarGrowlCambios, 10)
WebUI.click(btnCerrarGrowlCambios)
WebUI.delay(1)

WebUI.closeBrowser()
