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

WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Company/Index')

// ===============================
// CLIC EN "Dashboards"
// ===============================
TestObject opcionDashboards = new TestObject()
opcionDashboards.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//ul[@class='list-unstyled']//a[@href='/es/Dashboard/List' and normalize-space()='Dashboards']"
)

WebUI.waitForElementClickable(opcionDashboards, 10)
WebUI.click(opcionDashboards)

// ===============================
// CLIC EN "+ Nuevo" (Gráficos)
// ===============================
TestObject btnNuevoGrafico = new TestObject()
btnNuevoGrafico.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/DashboardModule/Edit' and contains(@class,'btn-link')]"
)

WebUI.waitForElementClickable(btnNuevoGrafico, 10)
WebUI.click(btnNuevoGrafico)

// ===============================
// COMPLETAR TÍTULO
// ===============================
TestObject inputTitulo = new TestObject()
inputTitulo.addProperty("id", ConditionType.EQUALS, "Title")

WebUI.waitForElementVisible(inputTitulo, 10)
WebUI.setText(inputTitulo, "Dashboard QA")


// ===============================
// COMPLETAR DESCRIPCIÓN
// ===============================
TestObject inputDescripcion = new TestObject()
inputDescripcion.addProperty("id", ConditionType.EQUALS, "Description")

WebUI.waitForElementVisible(inputDescripcion, 10)
WebUI.setText(inputDescripcion, "Dashboard QA")


// ===============================
// TIPO → Elegir "Anillo"
// ===============================

// Abrir chosen
TestObject chosenTipo = new TestObject()
chosenTipo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='DashboardType_chosen']//a"
)
WebUI.click(chosenTipo)

// Seleccionar "Anillo"
TestObject opcionAnillo = new TestObject()
opcionAnillo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='DashboardType_chosen']//li[contains(@class,'active-result') and normalize-space()='Anillo']"
)
WebUI.click(opcionAnillo)


// ===============================
// MÓDULO → Elegir "Requerimiento"
// ===============================

// Abrir chosen
TestObject chosenModulo = new TestObject()
chosenModulo.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='DashboardVariable_chosen']//a"
)
WebUI.click(chosenModulo)

// Seleccionar "Requerimiento"
TestObject opcionRequerimiento = new TestObject()
opcionRequerimiento.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='DashboardVariable_chosen']//li[contains(@class,'active-result') and normalize-space()='Requerimiento']"
)
WebUI.click(opcionRequerimiento)


// ===============================
// VARIABLE EJE X → Elegir "Area Solicitante"
// ===============================

// Abrir chosen
TestObject chosenAreaSolicitante = new TestObject()
chosenAreaSolicitante.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='GroupingByAuxDataFieldConfigId_chosen']//a"
)
WebUI.click(chosenAreaSolicitante)

// Seleccionar "Area Solicitante"
TestObject opcionAreaSolicitante = new TestObject()
opcionAreaSolicitante.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='GroupingByAuxDataFieldConfigId_chosen']//li[contains(@class,'active-result') and normalize-space()='Area Solicitante']"
)
WebUI.click(opcionAreaSolicitante)

