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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// ======================================================
// DECLARACIÓN DE TODOS LOS TESTOBJECTS DINÁMICOS (UNA SOLA VEZ)
// ======================================================
// Engranaje (CORREGIDO)
TestObject iconoEngranaje = new TestObject()

iconoEngranaje.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'nav-item\')]//i[contains(@class,\'fa-cog\')]')

TestObject opcionConfiguracion = new TestObject()

opcionConfiguracion.addProperty('xpath', ConditionType.EQUALS, '//ul[contains(@class,\'dropdown-menu\')]//a[@href=\'/es/Company/Index\' and contains(normalize-space(),\'Configuración\')]')

// Ayuda dentro de Configuración
TestObject opcionAyuda = new TestObject()

opcionAyuda.addProperty('xpath', ConditionType.EQUALS, '//a[@href=\'/es/Company/Files\' and normalize-space()=\'Ayuda\']')

// Botón agregar archivo
TestObject btnAgregarArchivo = new TestObject()

btnAgregarArchivo.addProperty('xpath', ConditionType.EQUALS, '//a[contains(@class,\'btn-file-edit\') and contains(normalize-space(),\'Agregar nuevo archivo\')]')

// Campos del modal
TestObject inputTitulo = new TestObject()

inputTitulo.addProperty('id', ConditionType.EQUALS, 'Title')

TestObject chkUsuarios = new TestObject()

chkUsuarios.addProperty('id', ConditionType.EQUALS, 'VisibleByUser')

TestObject chkProveedores = new TestObject()

chkProveedores.addProperty('id', ConditionType.EQUALS, 'VisibleBySupplier')

TestObject inputArchivo = new TestObject()

inputArchivo.addProperty('id', ConditionType.EQUALS, 'File')

TestObject fileNameContainer = new TestObject()

fileNameContainer.addProperty('id', ConditionType.EQUALS, 'file-name-container')

TestObject btnGuardarArchivo = new TestObject()

btnGuardarArchivo.addProperty('xpath', ConditionType.EQUALS, '//form[@id=\'form-edit-file\']//button[@type=\'submit\' and .//strong[normalize-space()=\'Guardar\']]')

TestObject btnCerrarGrowl = new TestObject()

btnCerrarGrowl.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'alert-dismissible\')]//button[contains(@class,\'btn-close\')]')

// Logout
TestObject linkCerrarSesion = new TestObject()

linkCerrarSesion.addProperty('xpath', ConditionType.EQUALS, '//a[@href=\'/es/User/Logout\' and contains(normalize-space(),\'Cerrar sesión\')]')

TestObject avatarUsuario = new TestObject()

avatarUsuario.addProperty('xpath', ConditionType.EQUALS, '//li[contains(@class,\'user-profile\')]//a[contains(@class,\'dropdown-toggle\')]')

// Ayuda en el header
TestObject iconoAyudaHeader = new TestObject()

iconoAyudaHeader.addProperty('xpath', ConditionType.EQUALS, '//li[contains(@class,\'nav-item\')]//i[contains(@class,\'fa-question-circle\')]')

TestObject menuAyuda = new TestObject()

menuAyuda.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'dropdown-menu\') and contains(@class,\'show\')]')

TestObject linkAyudaQA = new TestObject()

linkAyudaQA.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'dropdown-menu\')]//a[normalize-space()=\'Ayuda QA\']')

// ======================================================
// INICIO DEL TEST
// ======================================================
// Login interno
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Engranaje → Configuración → Ayuda
WebUI.waitForElementClickable(iconoEngranaje, 10)

WebUI.click(iconoEngranaje)

WebUI.waitForElementClickable(opcionConfiguracion, 10)

WebUI.click(opcionConfiguracion)

WebUI.waitForElementClickable(opcionAyuda, 10)

WebUI.click(opcionAyuda)

// Agregar archivo
WebUI.waitForElementClickable(btnAgregarArchivo, 10)

WebUI.click(btnAgregarArchivo)

// Completar formulario
WebUI.waitForElementVisible(inputTitulo, 10)

WebUI.setText(inputTitulo, 'Ayuda QA')

WebUI.click(chkUsuarios)

WebUI.click(chkProveedores)

// Subir archivo
String projectDir = RunConfiguration.getProjectDir()

String rutaArchivo = projectDir + '/Include/files/ayuda.pdf'

WebUI.uploadFile(inputArchivo, rutaArchivo)

// Validar nombre
String textoArchivo = WebUI.getText(fileNameContainer)

WebUI.verifyMatch(textoArchivo, '.*ayuda\\.pdf.*', true)

// Guardar
WebUI.waitForElementClickable(btnGuardarArchivo, 10)

WebUI.click(btnGuardarArchivo)

// Cerrar growl si aparece
if (WebUI.waitForElementVisible(btnCerrarGrowl, 5, FailureHandling.OPTIONAL)) {
    WebUI.click(btnCerrarGrowl)

    WebUI.delay(1)
}

// Logout robusto
if (WebUI.waitForElementVisible(linkCerrarSesion, 3, FailureHandling.OPTIONAL)) {
    WebUI.click(linkCerrarSesion)
} else {
    WebUI.mouseOver(avatarUsuario)

    WebUI.delay(1)

    WebUI.click(linkCerrarSesion)
}

// Login interno otra vez
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Descargar Ayuda QA (usuario interno)
WebUI.waitForElementVisible(iconoAyudaHeader, 10)

WebUI.click(iconoAyudaHeader)

WebUI.waitForElementVisible(menuAyuda, 10)

WebUI.waitForElementClickable(linkAyudaQA, 10)

WebUI.click(linkAyudaQA)

// Login proveedor
WebUI.callTestCase(findTestCase('1) Logins/1.2) Login Proveedor'), [:], FailureHandling.STOP_ON_FAILURE)

// Descargar Ayuda QA (proveedor)
WebUI.waitForElementVisible(iconoAyudaHeader, 10)

WebUI.click(iconoAyudaHeader)

WebUI.waitForElementVisible(menuAyuda, 10)

WebUI.waitForElementClickable(linkAyudaQA, 10)

WebUI.click(linkAyudaQA)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Engranaje → Configuración → Ayuda
WebUI.waitForElementClickable(iconoEngranaje, 10)

WebUI.click(iconoEngranaje)

WebUI.waitForElementClickable(opcionConfiguracion, 10)

WebUI.click(opcionConfiguracion)

WebUI.waitForElementClickable(opcionAyuda, 10)

WebUI.click(opcionAyuda)

// ===============================
// HOVER SOBRE LA FILA DE "Ayuda QA"
// ===============================
TestObject filaAyudaQA = new TestObject()

filaAyudaQA.addProperty('xpath', ConditionType.EQUALS, '//tbody[@id=\'container-sortable\']//tr[.//a[normalize-space()=\'Ayuda QA\']]')

WebUI.waitForElementVisible(filaAyudaQA, 10)

WebUI.mouseOver(filaAyudaQA)

WebUI.delay(1)

// ===============================
// CLIC EN EL LÁPIZ (btn-file-edit)
// ===============================
TestObject lapizEditar = new TestObject()

lapizEditar.addProperty('xpath', ConditionType.EQUALS, '//tbody[@id=\'container-sortable\']//tr[.//a[normalize-space()=\'Ayuda QA\']]//a[contains(@class,\'btn-file-edit\')]')

WebUI.waitForElementClickable(lapizEditar, 10)

WebUI.click(lapizEditar)

// ===============================
// MODAL DE EDICIÓN → CLIC EN "Eliminar"
// ===============================
TestObject btnEliminarEnEdicion = new TestObject()
btnEliminarEnEdicion.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//a[@data-bs-target='#confirm-delete-file']"
)

WebUI.waitForElementClickable(btnEliminarEnEdicion, 10)
WebUI.click(btnEliminarEnEdicion)

// ===============================
// ESPERAR A QUE EL MODAL DE CONFIRMACIÓN ESTÉ VISIBLE
// ===============================
TestObject modalConfirmacion = new TestObject()
modalConfirmacion.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[@class='modal-content']//h3[normalize-space()='Confirmar']"
)

WebUI.waitForElementVisible(modalConfirmacion, 10)
WebUI.delay(1) // deja que termine la animación del modal


// ===============================
// BOTÓN "Eliminar" DEL MODAL DE CONFIRMACIÓN
// ===============================
TestObject btnEliminarConfirmacion = new TestObject()
btnEliminarConfirmacion.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[@class='modal-content']//button[@type='submit' and contains(@class,'btn-danger')]"
)

WebUI.waitForElementClickable(btnEliminarConfirmacion, 10)
WebUI.click(btnEliminarConfirmacion)

// ===============================
// VALIDAR GROWL "Archivo eliminado con éxito"
// ===============================
TestObject growlExito = new TestObject()
growlExito.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//div[contains(@class,'alert-success') and contains(.,'Archivo eliminado con éxito')]"
)

WebUI.waitForElementVisible(growlExito, 10)


// ===============================
// CERRAR EL GROWL
// ===============================
TestObject btnCerrarGrowlFinal = new TestObject()
btnCerrarGrowlFinal.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowlFinal, 10)
WebUI.click(btnCerrarGrowlFinal)
