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
// 1) MOUSE OVER en "Proveedores"
// ===============================
TestObject menuProveedoresProv = new TestObject()
menuProveedoresProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'nav-link') and contains(.,'Proveedores')]"
)

WebUI.waitForElementVisible(menuProveedoresProv, 10)
WebUI.mouseOver(menuProveedoresProv)


// ===============================
// 2) ESPERAR QUE SE DESPLIEGUE EL MENÚ
// ===============================
TestObject dropdownProveedoresProv = new TestObject()
dropdownProveedoresProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'dropdown-menu')]//h6[contains(.,'Proveedores')]"
)

WebUI.waitForElementVisible(dropdownProveedoresProv, 10)


// ===============================
// 3) CLIC EN "Aprobaciones pendientes"
// ===============================
TestObject opcionAprobPendProv = new TestObject()
opcionAprobPendProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'dropdown-item') and contains(.,'Aprobaciones pendientes')]"
)

WebUI.waitForElementClickable(opcionAprobPendProv, 10)
WebUI.click(opcionAprobPendProv)

import com.kms.katalon.core.testobject.ConditionType

// ===============================
// 1) BUSCAR LA FILA DEL PROVEEDOR POR NOMBRE
// ===============================
TestObject filaAprobacionProv = new TestObject()
filaAprobacionProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]"
)

WebUI.waitForElementVisible(filaAprobacionProv, 10)


// ===============================
// 2) HACER MOUSEOVER SOBRE EL <td> QUE CONTIENE EL ÍCONO
// ===============================
TestObject celdaIconoProv = new TestObject()
celdaIconoProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//td[contains(@class,'text-end')]"
)

WebUI.waitForElementVisible(celdaIconoProv, 10)
WebUI.mouseOver(celdaIconoProv)


// ===============================
// 3) CLIC EN EL ÍCONO DE FLECHA (REVISAR / APROBAR)
// ===============================
TestObject iconoAbrirWorkflowProv = new TestObject()
iconoAbrirWorkflowProv.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//a[contains(@href,'/WorkflowLink/Review')]"
)

WebUI.waitForElementClickable(iconoAbrirWorkflowProv, 10)
WebUI.click(iconoAbrirWorkflowProv)
