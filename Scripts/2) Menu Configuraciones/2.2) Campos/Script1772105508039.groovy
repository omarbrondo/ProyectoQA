import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType

// LOGIN
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// NAVEGAR A CAMPOS
WebUI.click(findTestObject('Object Repository/2.1) Empresas/Page_Proveedores/btn_Engranaje'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Proveedores/a_Backoffice_dropdown-item'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Config/a_Empresa_text-decoration-none'))

// CREAR CAMPO "Campor QA"
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/a_Obligatorio_btn btn-link btn-edit-field b_c09cb9'))
WebUI.setText(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Nombre del campo_auxdata-title'), 'Campor QA')

WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Autocrear_IsEditableUser'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Editable por usuarios_IncludeInSearchResult'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Incluir en bsquedas_IsVisibleInTables'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Mostrar en tablas_IsVisibleSupplier'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Visible para proveedores_IsEditableSupplier'))
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/input_Editable por proveedores_Mandatory'))

WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/strong'))

// ===============================
// LÁPIZ DEL CAMPO "Campor QA"
// ===============================

// 1) Esperar a que aparezca el card del campo
TestObject cardCamporQA = new TestObject()
cardCamporQA.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'auxdata-card-single')]" +
    "[.//div[contains(@class,'col-md-5') and contains(normalize-space(),'Campor QA')]]"
)

WebUI.waitForElementVisible(cardCamporQA, 10)
WebUI.delay(1)

// 2) Expandir el card si está colapsado
WebUI.click(cardCamporQA)
WebUI.delay(1)

// 3) Hacer hover sobre el header (esto revela el lápiz)
TestObject headerCamporQA = new TestObject()
headerCamporQA.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "(//div[contains(@class,'auxdata-card-single')]" +
    "[.//div[contains(@class,'col-md-5') and contains(normalize-space(),'Campor QA')]])" +
    "//div[contains(@class,'card-header')]"
)

WebUI.mouseOver(headerCamporQA)
WebUI.delay(1)

// 4) Clic en el lápiz
TestObject lapizCamporQA = new TestObject()
lapizCamporQA.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "(//div[contains(@class,'auxdata-card-single')]" +
    "[.//div[contains(@class,'col-md-5') and contains(normalize-space(),'Campor QA')]])" +
    "//a[contains(@class,'btn-edit-field')]//i[contains(@class,'fa-pen')]"
)

WebUI.waitForElementClickable(lapizCamporQA, 10)
WebUI.click(lapizCamporQA)

// ABRIR VALIDACIÓN
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/span_Agregar validacin_btn-underline'))

// CONFIRMAR TEXTO DEL MODAL
WebUI.verifyElementText(findTestObject('Object Repository/2.2) Campos/Page_Campos/p'), '¿Está seguro que quiere eliminar Campor QA?')

// CANCELAR
WebUI.click(findTestObject('Object Repository/2.2) Campos/Page_Campos/button_Cancelar_btn btn-danger'))

// MENSAJE DE ÉXITO
WebUI.verifyElementText(
    findTestObject('Object Repository/2.2) Campos/Page_Campos/div_Cerrar sesin_alert alert-success alert-_d9b77b'),
    'Campo eliminado con éxito'
)

WebUI.closeBrowser()
