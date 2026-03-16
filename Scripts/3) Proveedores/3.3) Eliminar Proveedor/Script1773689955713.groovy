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

import com.kms.katalon.core.testobject.ConditionType

// ===============================
// BUSCAR FILA DEL PROVEEDOR POR CUIT
// ===============================
TestObject filaProveedorProv = new TestObject()
filaProveedorProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tr[td/span[contains(normalize-space(),'" + GlobalVariable.CuitProveedor + "')]]"
)

WebUI.waitForElementVisible(filaProveedorProv, 10)
WebUI.click(filaProveedorProv)


// ===============================
// 1) CLIC EN "Acciones"
// ===============================
TestObject btnAccionesProv = new TestObject()
btnAccionesProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'dropdown-toggle') and contains(.,'Acciones')]"
)

WebUI.waitForElementClickable(btnAccionesProv, 10)
WebUI.click(btnAccionesProv)


// ===============================
// 2) CLIC EN "Eliminar proveedor"
// ===============================
TestObject btnEliminarProveedorProv = new TestObject()
btnEliminarProveedorProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'dropdown-item') and contains(.,'Eliminar proveedor')]"
)

WebUI.waitForElementClickable(btnEliminarProveedorProv, 10)
WebUI.click(btnEliminarProveedorProv)


// ===============================
// 3) ESPERAR MODAL VISIBLE "Confirmar eliminación"
// ===============================
TestObject modalEliminarProv = new TestObject()
modalEliminarProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'modal') and contains(@class,'show')]//h3[contains(.,'Confirmar eliminación')]"
)

WebUI.waitForElementVisible(modalEliminarProv, 10)


// ===============================
// 4) CLIC EN BOTÓN "Eliminar" DEL MODAL VISIBLE
// ===============================
TestObject btnEliminarModalProv = new TestObject()
btnEliminarModalProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'modal') and contains(@class,'show')]//button[@type='submit' and contains(.,'Eliminar')]"
)

WebUI.waitForElementClickable(btnEliminarModalProv, 10)
WebUI.click(btnEliminarModalProv)


// ===============================
// 5) VALIDAR GROWL "Eliminado"
// ===============================
TestObject growlEliminadoProv = new TestObject()
growlEliminadoProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success') and contains(.,'Eliminado')]"
)

WebUI.waitForElementVisible(growlEliminadoProv, 10)


// ===============================
// 6) CERRAR GROWL
// ===============================
TestObject btnCerrarGrowlEliminadoProv = new TestObject()
btnCerrarGrowlEliminadoProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-success')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowlEliminadoProv, 10)
WebUI.click(btnCerrarGrowlEliminadoProv)
