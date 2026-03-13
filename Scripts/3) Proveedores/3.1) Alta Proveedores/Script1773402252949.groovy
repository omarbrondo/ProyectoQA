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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.WebElement


WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// CLIC EN "Invitar proveedor"
// ===============================
TestObject btnInvitarProveedor = new TestObject()
btnInvitarProveedor.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/Form/InviteSupplier' and contains(@class,'btn-link')]"
)

WebUI.waitForElementClickable(btnInvitarProveedor, 10)
WebUI.click(btnInvitarProveedor)




// ======================================================
// 1) COMPLETAR CUIT (ID DINÁMICO) USANDO VARIABLE GLOBAL
// ======================================================
TestObject inputCUIT = new TestObject()
inputCUIT.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'CUIT')]/following::input[@type='text'][1]"
)

WebUI.waitForElementVisible(inputCUIT, 10)
WebUI.setText(inputCUIT, GlobalVariable.CuitProveedor)

// TAB para disparar validación del CUIT
WebUI.sendKeys(inputCUIT, Keys.chord(Keys.TAB))


// ======================================================
// 2) ESPERAR A QUE SE COMPLETE AUTOMÁTICAMENTE EL NOMBRE
// ======================================================
TestObject inputNombre = new TestObject()
inputNombre.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Nombre')]/following::input[@type='text'][1]"
)

String nombreProveedor = ""
for (int i = 0; i < 10; i++) {
	nombreProveedor = WebUI.getAttribute(inputNombre, "value")
	if (nombreProveedor != null && nombreProveedor.trim() != "") {
		break
	}
	WebUI.delay(1)
}

// Guardar nombre para uso futuro
GlobalVariable.NombreProveedor = nombreProveedor
println "Nombre detectado automáticamente: " + nombreProveedor


// ======================================================
// 3) TIPO DE ALTA = "Alta normal"
// ======================================================
TestObject selectTipoAlta = new TestObject()
selectTipoAlta.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Tipo de alta')]/following::select[1]"
)

WebUI.waitForElementClickable(selectTipoAlta, 10)
WebUI.selectOptionByLabel(selectTipoAlta, "Alta normal", false)


// ======================================================
// 4) EMAIL = "omarbrondo@gmail.com"
// ======================================================
TestObject inputEmail = new TestObject()
inputEmail.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Email')]/following::input[@type='email'][1]"
)

WebUI.waitForElementVisible(inputEmail, 10)
WebUI.setText(inputEmail, "omarbrondo@gmail.com")


// ======================================================
// 5) EJECUTIVO ASIGNADO = "Juan"
// ======================================================
TestObject selectEjecutivo = new TestObject()
selectEjecutivo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Ejecutivo Asignado')]/following::select[1]"
)

WebUI.waitForElementClickable(selectEjecutivo, 10)
WebUI.selectOptionByLabel(selectEjecutivo, "Juan", false)


// ======================================================
// 6) CATEGORÍA = "Insumos"
// ======================================================
TestObject selectCategoria = new TestObject()
selectCategoria.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Categoria')]/following::select[1]"
)

WebUI.waitForElementClickable(selectCategoria, 10)
WebUI.selectOptionByLabel(selectCategoria, "Insumos", false)


// ======================================================
// 7) CLIC EN "ENVIAR"
// ======================================================
TestObject btnEnviar = new TestObject()
btnEnviar.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@type='submit' and contains(.,'ENVIAR')]"
)

WebUI.waitForElementClickable(btnEnviar, 10)
WebUI.click(btnEnviar)


// ======================================================
// 8) VALIDAR GROWL "Operación exitosa"
// ======================================================
TestObject growlExito = new TestObject()
growlExito.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success') and contains(.,'Operación exitosa')]"
)

WebUI.waitForElementVisible(growlExito, 10)


// ======================================================
// 9) CERRAR EL GROWL
// ======================================================
TestObject btnCerrarGrowl = new TestObject()
btnCerrarGrowl.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowl, 10)
WebUI.click(btnCerrarGrowl)

