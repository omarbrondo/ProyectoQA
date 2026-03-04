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
import com.kms.katalon.core.configuration.RunConfiguration

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Engranaje
WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/btn_Engranaje'))

WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/a_Backoffice_dropdown-item'))

// ===============================
// Clic en Ayuda
// ===============================
TestObject opcionAyuda = new TestObject()
opcionAyuda.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/Company/Files' and normalize-space()='Ayuda']"
)

// ===============================
// Hacer clic en + Agregar nuevo archivo
// ===============================

WebUI.waitForElementClickable(opcionAyuda, 10)
WebUI.click(opcionAyuda)

TestObject btnAgregarArchivo = new TestObject()
btnAgregarArchivo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'btn-file-edit') and contains(normalize-space(), 'Agregar nuevo archivo')]"
)

WebUI.waitForElementClickable(btnAgregarArchivo, 10)
WebUI.click(btnAgregarArchivo)

// ===============================
// Completar el campo Título
// ===============================

TestObject inputTitulo = new TestObject()
inputTitulo.addProperty(
	"id",
	ConditionType.EQUALS,
	"Title"
)

WebUI.waitForElementVisible(inputTitulo, 10)
WebUI.setText(inputTitulo, "Ayuda QA")

// ===============================
// Tildar Visible por usuarios
// ===============================

TestObject chkUsuarios = new TestObject()
chkUsuarios.addProperty(
	"id",
	ConditionType.EQUALS,
	"VisibleByUser"
)

WebUI.waitForElementClickable(chkUsuarios, 10)
WebUI.click(chkUsuarios)

// ===============================
// Tildar Visible por proveedores
// ===============================

TestObject chkProveedores = new TestObject()
chkProveedores.addProperty(
	"id",
	ConditionType.EQUALS,
	"VisibleBySupplier"
)

WebUI.waitForElementClickable(chkProveedores, 10)
WebUI.click(chkProveedores)

// ===============================
// SUBIR ARCHIVO
// ===============================


String projectDir = RunConfiguration.getProjectDir()
String rutaArchivo = projectDir + "/Include/files/ayuda.pdf"

TestObject inputArchivo = new TestObject()
inputArchivo.addProperty("id", ConditionType.EQUALS, "File")

WebUI.uploadFile(inputArchivo, rutaArchivo)

// ===============================
// VALIDAR QUE EL NOMBRE APARECE
// ===============================

TestObject fileNameContainer = new TestObject()
fileNameContainer.addProperty("id", ConditionType.EQUALS, "file-name-container")

String textoArchivo = WebUI.getText(fileNameContainer)

WebUI.verifyMatch(textoArchivo, ".*ayuda\\.pdf.*", true)

// ===============================
// CLICK EN GUARDAR (MODAL NUEVO)
// ===============================

TestObject btnGuardarArchivo = new TestObject()
btnGuardarArchivo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//form[@id='form-edit-file']//button[@type='submit' and .//strong[normalize-space()='Guardar']]"
)

WebUI.waitForElementClickable(btnGuardarArchivo, 10)
WebUI.click(btnGuardarArchivo)


// ===============================
// CERRAR CUALQUIER GROWL VISIBLE
// ===============================

TestObject btnCerrarGrowl = new TestObject()
btnCerrarGrowl.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-dismissible')]//button[contains(@class,'btn-close')]"
)

if (WebUI.waitForElementVisible(btnCerrarGrowl, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnCerrarGrowl)
	WebUI.delay(1)
}

// ===============================
// Proceso de logout
// ===============================

TestObject linkCerrarSesion = new TestObject()
linkCerrarSesion.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/User/Logout' and contains(normalize-space(),'Cerrar sesión')]"
)

TestObject avatarUsuario = new TestObject()
avatarUsuario.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//li[contains(@class,'user-profile')]//a[contains(@class,'dropdown-toggle')]"
)

// ===============================
// LOGOUT ROBUSTO
// ===============================

// Si el link ya está visible, clic directo
if (WebUI.waitForElementVisible(linkCerrarSesion, 3, FailureHandling.OPTIONAL)) {
	WebUI.click(linkCerrarSesion)
} else {
	// Si no está visible, desplegar menú
	WebUI.waitForElementVisible(avatarUsuario, 10)
	WebUI.mouseOver(avatarUsuario)
	WebUI.delay(1)

	WebUI.waitForElementClickable(linkCerrarSesion, 10)
	WebUI.click(linkCerrarSesion)
}

// ===============================
// Login otra vez como usuario interno
// ===============================

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// Validar que existe el ícono de Ayuda (signo de pregunta)
// ===============================

TestObject iconoAyuda = new TestObject()
iconoAyuda.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//li[contains(@class,'nav-item')]//i[contains(@class,'fa-question-circle')]"
)

WebUI.waitForElementVisible(iconoAyuda, 10)

WebUI.click(iconoAyuda)
WebUI.delay(1)

// ===============================
// Validar que el menú desplegado está visible
// ===============================

TestObject menuAyuda = new TestObject()
menuAyuda.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'dropdown-menu') and contains(@class,'show')]"
)

WebUI.waitForElementVisible(menuAyuda, 10)

// ===============================
// Clic en “Ayuda QA” para descargar el archivo
// ===============================

TestObject linkAyudaQA = new TestObject()
linkAyudaQA.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'dropdown-menu')]//a[contains(@class,'dropdown-item') and normalize-space()='Ayuda QA']"
)

WebUI.waitForElementClickable(linkAyudaQA, 10)
WebUI.click(linkAyudaQA)
