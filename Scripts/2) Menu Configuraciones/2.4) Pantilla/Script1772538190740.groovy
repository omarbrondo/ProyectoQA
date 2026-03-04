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
import com.kms.katalon.core.testobject.ConditionType


WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// Engranaje
WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/btn_Engranaje'))
WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Proveedores/a_Backoffice_dropdown-item'))
WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Company/Index')
WebUI.click(findTestObject('Object Repository/2.4) Plantillas/Page_Config/a_Monedas_text-decoration-none'))

// ===============================
// BOTÓN "NUEVO TEMPLATE"
// ===============================

TestObject btnNuevoTemplate = new TestObject()
btnNuevoTemplate.addProperty("id", ConditionType.EQUALS, "newEmailTemplate")

WebUI.waitForElementClickable(btnNuevoTemplate, 10)
WebUI.click(btnNuevoTemplate)

// ===============================
// MODAL "Nuevo Template"
// ===============================

TestObject modalTemplate = new TestObject()
modalTemplate.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'modal-content') and .//h3[contains(text(),'Template')]]")

WebUI.waitForElementVisible(modalTemplate, 10)

// ===============================
// COMPLETAR NOMBRE
// ===============================

TestObject inputNombre = new TestObject()
inputNombre.addProperty("id", ConditionType.EQUALS, "Name")

WebUI.waitForElementVisible(inputNombre, 10)
WebUI.setText(inputNombre, "Plantilla QA")

// ===============================
// COMPLETAR DESCRIPCIÓN
// ===============================

TestObject inputDescripcion = new TestObject()
inputDescripcion.addProperty("id", ConditionType.EQUALS, "Description")

WebUI.setText(inputDescripcion, "Plantilla QA")

// ===============================
// SELECCIONAR MÓDULO "Proveedores"
// ===============================

TestObject chosenModule = new TestObject()
chosenModule.addProperty("xpath", ConditionType.EQUALS, "//div[@id='ModuleList_chosen']//ul")

WebUI.click(chosenModule)
WebUI.delay(1)

TestObject opcionProveedores = new TestObject()
opcionProveedores.addProperty("xpath", ConditionType.EQUALS, "//li[contains(@class,'active-result') and text()='Proveedores']")

WebUI.click(opcionProveedores)

// ===============================
// COMPLETAR CUERPO
// ===============================

TestObject inputBody = new TestObject()
inputBody.addProperty("id", ConditionType.EQUALS, "Body")

WebUI.setText(inputBody, "Plantilla QA")

// ===============================
// GUARDAR
// ===============================

TestObject btnGuardar = new TestObject()
btnGuardar.addProperty("id", ConditionType.EQUALS, "btn-save")

WebUI.waitForElementClickable(btnGuardar, 10)
WebUI.click(btnGuardar)

// ===============================
// VALIDAR MENSAJE "Guardar cambios"
// ===============================

TestObject alertaGuardar = new TestObject()
alertaGuardar.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'alert-success') and contains(normalize-space(),'Guardar cambios')]")

WebUI.waitForElementVisible(alertaGuardar, 10)
WebUI.verifyElementText(alertaGuardar, "Guardar cambios")

// ===============================
// HOVER SOBRE "Proveedores"
// ===============================

TestObject menuProveedores = new TestObject()
menuProveedores.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class,'nav-link') and .//span[normalize-space()='Proveedores']]")

WebUI.waitForElementVisible(menuProveedores, 10)
WebUI.mouseOver(menuProveedores)
WebUI.delay(1)

// ===============================
// CLICK EN "Maestro"
// ===============================

TestObject opcionMaestro = new TestObject()
opcionMaestro.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'dropdown-menu')]//a[normalize-space()='Maestro']")

WebUI.waitForElementClickable(opcionMaestro, 10)
WebUI.click(opcionMaestro)

// ===============================
// CLICK EN PROVEEDOR "AGROBLACK JB SOCIEDAD ANONIMA"
// ===============================

TestObject proveedorAgroblack = new TestObject()
proveedorAgroblack.addProperty("xpath", ConditionType.EQUALS, "//table//tr//span[normalize-space()='AGROBLACK JB SOCIEDAD ANONIMA']")

WebUI.waitForElementClickable(proveedorAgroblack, 10)
WebUI.click(proveedorAgroblack)

// ===============================
// CLICK EN "Nuevo comentario"
// ===============================

TestObject btnNuevoComentario = new TestObject()
btnNuevoComentario.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class,'btn-new-comment')]")

WebUI.waitForElementClickable(btnNuevoComentario, 10)
WebUI.click(btnNuevoComentario)

// ===============================
// NOTIFICAR USUARIOS
// ===============================

TestObject chkNotificar = new TestObject()
chkNotificar.addProperty("id", ConditionType.EQUALS, "NotifyUser")

WebUI.waitForElementClickable(chkNotificar, 10)
WebUI.click(chkNotificar)
WebUI.delay(1)

// Abrir chosen
TestObject chosenUser = new TestObject()
chosenUser.addProperty("xpath", ConditionType.EQUALS, "//div[@id='UserIdList_chosen']//ul")

WebUI.click(chosenUser)
WebUI.delay(1)

// Seleccionar Omar
TestObject opcionOmar = new TestObject()
opcionOmar.addProperty("xpath", ConditionType.EQUALS, "//li[contains(@class,'active-result') and normalize-space()='Omar Eduardo Brondo']")

WebUI.click(opcionOmar)

// ===============================
// USAR PLANTILLA
// ===============================

TestObject btnUsarPlantilla = new TestObject()
btnUsarPlantilla.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class,'btn-select-template')]")

WebUI.waitForElementClickable(btnUsarPlantilla, 10)
WebUI.click(btnUsarPlantilla)

// Modal plantillas
TestObject modalPlantilla = new TestObject()
modalPlantilla.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'modal-content') and .//h3[contains(text(),'Seleccione una opción')]]")

WebUI.waitForElementVisible(modalPlantilla, 10)

// Seleccionar plantilla
TestObject selectPlantilla = new TestObject()
selectPlantilla.addProperty("id", ConditionType.EQUALS, "comment-template-list")

WebUI.selectOptionByLabel(selectPlantilla, "Plantilla QA", false)

// Aplicar
TestObject btnAplicar = new TestObject()
btnAplicar.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class,'btn-apply-template')]")

WebUI.waitForElementClickable(btnAplicar, 10)
WebUI.click(btnAplicar)

// ===============================
// ESPERAR QUE SE CIERRE EL MODAL DE PLANTILLAS
// ===============================

TestObject modalPlantillaCerrar = new TestObject()
modalPlantillaCerrar.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'modal-content') and .//h3[contains(text(),'Seleccione una opción')]]")

WebUI.waitForElementNotVisible(modalPlantillaCerrar, 10)
WebUI.delay(1) // deja que el modal principal se re-renderice

// ===============================
// RE-CAPTURAR EL BOTÓN GUARDAR DEL MODAL PRINCIPAL
// ===============================

TestObject btnGuardarComentario = new TestObject()
btnGuardarComentario.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//form[contains(@class,'form-elementcomment-create')]//button[contains(@class,'btn-submit-form')]"
)

WebUI.waitForElementClickable(btnGuardarComentario, 10)
WebUI.click(btnGuardarComentario)
