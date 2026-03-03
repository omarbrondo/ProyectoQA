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
WebUI.callTestCase(
    findTestCase('1) Logins/1.1) Login Usuario Interno'),
    [:],
    FailureHandling.STOP_ON_FAILURE
)

// NAVEGAR A MONEDAS
WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Proveedores/btn_Engranaje'))
WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Supplier/List')
WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Proveedores/a_Backoffice_dropdown-item'))
WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Config/a_Campos_text-decoration-none'))

// CREAR MONEDA "Albania"
WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Config/a_Automatico_newCurrency'))
WebUI.setText(findTestObject('Object Repository/2.3) Monedas/Page_Config/input_Nombre_Name'), 'Albania')

WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Config/span'))
WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Config/li_Seleccione una moneda_active-result resu_0bc2da'))

WebUI.setText(findTestObject('Object Repository/2.3) Monedas/Page_Config/input_Smbolo_Symbol'), 'ALB')
WebUI.setText(findTestObject('Object Repository/2.3) Monedas/Page_Config/input_Cdigo de importacin_ImportCode'), 'ALB')
WebUI.setText(findTestObject('Object Repository/2.3) Monedas/Page_Config/input_Cdigo de exportacin_ExportCode'), 'ALB')

WebUI.click(findTestObject('Object Repository/2.3) Monedas/Page_Config/strong'))

WebUI.verifyElementText(
    findTestObject('Object Repository/2.3) Monedas/Page_Config/div_Cerrar sesin_alert alert-success alert-_d9b77b'),
    'Moneda guardada correctamente'
)

// ===============================
// LÁPIZ DE LA MONEDA "Albania"
// ===============================

// 1) Fila de la moneda "Albania"
TestObject filaAlbania = new TestObject()
filaAlbania.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//tr[td[normalize-space()='Albania']]"
)

WebUI.waitForElementVisible(filaAlbania, 10)
WebUI.mouseOver(filaAlbania)
WebUI.delay(1)

// 2) Lápiz dentro de la fila "Albania"
TestObject lapizAlbania = new TestObject()
lapizAlbania.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//tr[td[normalize-space()='Albania']]//a[contains(@class,'currency-edit')]//i[contains(@class,'fa-pen')]"
)

WebUI.waitForElementClickable(lapizAlbania, 10)
WebUI.click(lapizAlbania)

// ===============================
// ELIMINAR DESDE EL MODAL (Albania)
// ===============================

// 1) Esperar que abra el modal de "Albania"
TestObject modalAlbania = new TestObject()
modalAlbania.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'modal-content')]" +
    "[.//h5[contains(@class,'modal-title') and normalize-space()='Albania']]"
)

WebUI.waitForElementVisible(modalAlbania, 10)

// 2) Click en "Eliminar" (botón del modal)
TestObject btnEliminarModal = new TestObject()
btnEliminarModal.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'modal-content')]" +
    "[.//h5[contains(@class,'modal-title') and normalize-space()='Albania']]" +
    "//a[@id='deleteCurrencyBtn' or contains(@id,'deleteCurrencyBtn')]" +
    "//span[normalize-space()='Eliminar']/ancestor::a[1]"
)

WebUI.waitForElementClickable(btnEliminarModal, 10)
WebUI.click(btnEliminarModal)

// 3) Si aparece confirmación, confirmar (si no aparece, no rompe)
try {
    TestObject confirmarEliminar = new TestObject()
    confirmarEliminar.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[contains(@class,'modal') and contains(@class,'show')]//button" +
        "[normalize-space()='Eliminar' or normalize-space()='Confirmar' or normalize-space()='Aceptar']"
    )

    if (WebUI.waitForElementClickable(confirmarEliminar, 3)) {
        WebUI.click(confirmarEliminar)
    }
} catch (Exception e) {
    // no-op
}

// ===============================
// VERIFICAR MENSAJE DE ÉXITO "Eliminado"
// ===============================

TestObject alertaEliminado = new TestObject()
alertaEliminado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success') and contains(normalize-space(),'Eliminado')]"
)

WebUI.waitForElementVisible(alertaEliminado, 10)
WebUI.verifyElementText(alertaEliminado, 'Eliminado')



WebUI.closeBrowser()
