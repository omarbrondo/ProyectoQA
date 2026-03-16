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
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.configuration.RunConfiguration

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// CLIC EN "Invitar proveedor"
// ===============================
TestObject btnInvitarProveedor = new TestObject()

btnInvitarProveedor.addProperty('xpath', ConditionType.EQUALS, '//a[@href=\'/es/Form/InviteSupplier\' and contains(@class,\'btn-link\')]')

WebUI.waitForElementClickable(btnInvitarProveedor, 10)

WebUI.click(btnInvitarProveedor)

// ======================================================
// 1) COMPLETAR CUIT (ID DINÁMICO) USANDO VARIABLE GLOBAL
// ======================================================
TestObject inputCUIT = new TestObject()

inputCUIT.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'CUIT\')]/following::input[@type=\'text\'][1]')

WebUI.waitForElementVisible(inputCUIT, 10)

WebUI.setText(inputCUIT, GlobalVariable.CuitProveedor)

// TAB para disparar validación del CUIT
WebUI.sendKeys(inputCUIT, Keys.chord(Keys.TAB))

// ======================================================
// 2) ESPERAR A QUE SE COMPLETE AUTOMÁTICAMENTE EL NOMBRE
// ======================================================
TestObject inputNombre = new TestObject()

inputNombre.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'Nombre\')]/following::input[@type=\'text\'][1]')

String nombreProveedor = ''

for (int i = 0; i < 10; i++) {
    nombreProveedor = WebUI.getAttribute(inputNombre, 'value')

    if ((nombreProveedor != null) && (nombreProveedor.trim() != '')) {
        break
    }
    
    WebUI.delay(1)
}

// Guardar nombre para uso futuro
GlobalVariable.NombreProveedor = nombreProveedor

println('Nombre detectado automáticamente: ' + nombreProveedor)

// ======================================================
// 3) TIPO DE ALTA = "Alta normal"
// ======================================================
TestObject selectTipoAlta = new TestObject()

selectTipoAlta.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'Tipo de alta\')]/following::select[1]')

WebUI.waitForElementClickable(selectTipoAlta, 10)

WebUI.selectOptionByLabel(selectTipoAlta, 'Alta normal', false)

// ======================================================
// 4) EMAIL = "omarbrondo@gmail.com"
// ======================================================
TestObject inputEmail = new TestObject()

inputEmail.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'Email\')]/following::input[@type=\'email\'][1]')

WebUI.waitForElementVisible(inputEmail, 10)

WebUI.setText(inputEmail, 'omarbrondo@gmail.com')

// ======================================================
// 5) EJECUTIVO ASIGNADO = "Juan"
// ======================================================
TestObject selectEjecutivo = new TestObject()

selectEjecutivo.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'Ejecutivo Asignado\')]/following::select[1]')

WebUI.waitForElementClickable(selectEjecutivo, 10)

WebUI.selectOptionByLabel(selectEjecutivo, 'Juan', false)

// ======================================================
// 6) CATEGORÍA = "Insumos"
// ======================================================
TestObject selectCategoria = new TestObject()

selectCategoria.addProperty('xpath', ConditionType.EQUALS, '//label[contains(normalize-space(),\'Categoria\')]/following::select[1]')

WebUI.waitForElementClickable(selectCategoria, 10)

WebUI.selectOptionByLabel(selectCategoria, 'Insumos', false)

// ======================================================
// 7) CLIC EN "ENVIAR"
// ======================================================
TestObject btnEnviar = new TestObject()

btnEnviar.addProperty('xpath', ConditionType.EQUALS, '//button[@type=\'submit\' and contains(.,\'ENVIAR\')]')

WebUI.waitForElementClickable(btnEnviar, 10)

WebUI.click(btnEnviar)

// ======================================================
// 8) VALIDAR GROWL "Operación exitosa"
// ======================================================
TestObject growlExito = new TestObject()

growlExito.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'alert-success\') and contains(.,\'Operación exitosa\')]')

WebUI.waitForElementVisible(growlExito, 10)

// ======================================================
// 9) CERRAR EL GROWL
// ======================================================
TestObject btnCerrarGrowl = new TestObject()

btnCerrarGrowl.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'alert-success\')]//button[contains(@class,\'btn-close\')]')

WebUI.waitForElementClickable(btnCerrarGrowl, 10)

WebUI.click(btnCerrarGrowl)

WebUI.closeBrowser()

// ===============================
// Login como usuario proveedor
// ===============================

WebUI.callTestCase(findTestCase('1) Logins/1.2) Login Proveedor'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// CLIC EN "Formularios"
// ===============================
TestObject menuFormularios = new TestObject()
menuFormularios.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@href,'/WorkflowLink/MyActiveLinks') and contains(.,'Formularios')]"
)

WebUI.waitForElementClickable(menuFormularios, 10)
WebUI.click(menuFormularios)

// ===============================
// BUSCAR LA FILA DEL PROVEEDOR Y HACER CLIC EN "Completar"
// ===============================
TestObject linkCompletar = new TestObject()
linkCompletar.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//a[contains(.,'Completar')]"
)

WebUI.waitForElementClickable(linkCompletar, 10)
WebUI.click(linkCompletar)

import com.kms.katalon.core.testobject.ConditionType

// ======================================================
// 1) RAZÓN SOCIAL (readonly, no se completa)
// ======================================================
// No hacemos nada, solo validamos que exista
TestObject razonSocial = new TestObject()
razonSocial.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Razón Social')]/following::input[1]"
)
WebUI.verifyElementPresent(razonSocial, 10)


// ======================================================
// 2) PAÍS = "Argentina"
// ======================================================
TestObject selectPais = new TestObject()
selectPais.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'País')]/following::select[1]"
)

WebUI.waitForElementClickable(selectPais, 10)
WebUI.selectOptionByLabel(selectPais, "Argentina", false)


// ======================================================
// 3) DOMICILIO (texto random)
// ======================================================
TestObject inputDomicilio = new TestObject()
inputDomicilio.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Domicilio')]/following::input[@type='text'][1]"
)

WebUI.waitForElementVisible(inputDomicilio, 10)
WebUI.setText(inputDomicilio, "Av. Libertador 1234")


// ======================================================
// 4) TELÉFONO (obligatorio)
// ======================================================
TestObject inputTelefono = new TestObject()
inputTelefono.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Telefono')]/following::input[@type='text'][1]"
)

WebUI.waitForElementVisible(inputTelefono, 10)
WebUI.setText(inputTelefono, "11-4567-8910")


// ======================================================
// 5) EMAIL (obligatorio)
// ======================================================
TestObject inputEmailProv = new TestObject()
inputEmailProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Email')]/following::input[@type='text'][1]"
)

WebUI.waitForElementVisible(inputEmailProv, 10)
WebUI.setText(inputEmailProv, "contacto.proveedor@test.com")


// ======================================================
// 6) CONTACTO (obligatorio)
// ======================================================
TestObject inputContacto = new TestObject()
inputContacto.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Contacto')]/following::input[@type='text'][1]"
)

WebUI.waitForElementVisible(inputContacto, 10)
WebUI.setText(inputContacto, "Juan Pérez")

// ======================================================
// SUBIR ARCHIVO - Comprobante Inscripción Fiscal
// ======================================================
TestObject inputArchivoFiscal = new TestObject()
inputArchivoFiscal.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Comprobante Inscripción Fiscal')]/following::input[@type='file'][1]"
)

// Ruta del archivo (ajustar si está en otro lado)
String rutaArchivo = RunConfiguration.getProjectDir() + "/include/files/ayuda.pdf"

WebUI.waitForElementVisible(inputArchivoFiscal, 10)
WebUI.uploadFile(inputArchivoFiscal, rutaArchivo)

// ======================================================
// EMPLEADOR = "No soy empleador"
// ======================================================
TestObject selectEmpleador = new TestObject()
selectEmpleador.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//label[contains(normalize-space(),'Empleador')]/following::select[1]"
)

WebUI.waitForElementClickable(selectEmpleador, 10)
WebUI.selectOptionByLabel(selectEmpleador, "No soy empleador", false)



// ===============================
// 1) CLIC EN BOTÓN "ENVIAR" (abre modal)
// ===============================
TestObject btnAbrirModalEnviarProv = new TestObject()
btnAbrirModalEnviarProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'btn-send-forms-enabled') and contains(.,'ENVIAR')]"
)

WebUI.waitForElementClickable(btnAbrirModalEnviarProv, 10)
WebUI.click(btnAbrirModalEnviarProv)


// ===============================
// 2) ESPERAR MODAL "Confirmar envío"
// ===============================
TestObject modalConfirmarProv = new TestObject()
modalConfirmarProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@class='modal-content']//h3[contains(.,'Confirmar envío')]"
)

WebUI.waitForElementVisible(modalConfirmarProv, 10)


// ===============================
// 3) CLIC EN "Enviar" DENTRO DEL MODAL
// ===============================
TestObject btnEnviarModalProv = new TestObject()
btnEnviarModalProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@class='modal-content']//button[@type='submit' and contains(.,'Enviar')]"
)

WebUI.waitForElementClickable(btnEnviarModalProv, 10)
WebUI.click(btnEnviarModalProv)


// ===============================
// 4) VALIDAR GROWL DE ÉXITO
// ===============================
TestObject growlExitoProv = new TestObject()
growlExitoProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success') and contains(.,'Se ha guardado con éxito')]"
)

WebUI.waitForElementVisible(growlExitoProv, 10)


// ===============================
// 5) CERRAR GROWL
// ===============================
TestObject btnCerrarGrowlProv = new TestObject()
btnCerrarGrowlProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowlProv, 10)
WebUI.click(btnCerrarGrowlProv)
