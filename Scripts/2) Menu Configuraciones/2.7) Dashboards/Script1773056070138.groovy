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

// ===============================
// CLIC EN GUARDAR
// ===============================
TestObject btnGuardar = new TestObject()
btnGuardar.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@type='submit' and .//strong[normalize-space()='GUARDAR']]"
)

WebUI.waitForElementClickable(btnGuardar, 10)
WebUI.click(btnGuardar)


// ===============================
// VALIDAR GROWL "Cambios guardados"
// ===============================
TestObject growlGuardado = new TestObject()
growlGuardado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//div[contains(@class,'alert-success') and contains(.,'Cambios guardados')]"
)

WebUI.waitForElementVisible(growlGuardado, 10)


// ===============================
// CERRAR EL GROWL
// ===============================
TestObject btnCerrarGrowl = new TestObject()
btnCerrarGrowl.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowl, 10)
WebUI.click(btnCerrarGrowl)

// ===============================
// CLIC EN TAB "Dashboards"
// ===============================
TestObject tabDashboards = new TestObject()
tabDashboards.addProperty(
	"id",
	ConditionType.EQUALS,
	"nav-dashboards-tab"
)

WebUI.waitForElementClickable(tabDashboards, 10)
WebUI.click(tabDashboards)

// ===============================
// CLIC EN "+ Nuevo" (Dashboards)
// ===============================
TestObject btnNuevoDashboard = new TestObject()
btnNuevoDashboard.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/Dashboard/Edit' and contains(@class,'btn-link')]"
)

WebUI.waitForElementClickable(btnNuevoDashboard, 10)
WebUI.click(btnNuevoDashboard)

// ===============================
// COMPLETAR NOMBRE DEL DASHBOARD
// ===============================
TestObject inputNombreDashboard = new TestObject()
inputNombreDashboard.addProperty("id", ConditionType.EQUALS, "Name")

WebUI.waitForElementVisible(inputNombreDashboard, 10)
WebUI.setText(inputNombreDashboard, "Dashboard QA")


// ===============================
// ROLES → Seleccionar "Admin"
// ===============================

// Abrir chosen-multi
TestObject chosenRoles = new TestObject()
chosenRoles.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='RoleIdList_chosen']//input"
)
WebUI.click(chosenRoles)

// Seleccionar "Admin"
TestObject opcionAdmin = new TestObject()
opcionAdmin.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='RoleIdList_chosen']//li[contains(@class,'active-result') and normalize-space()='Admin']"
)
WebUI.waitForElementClickable(opcionAdmin, 10)
WebUI.click(opcionAdmin)


// ===============================
// CLIC EN "Agregar" (Gráficos)
// ===============================
TestObject btnAgregarGrafico = new TestObject()
btnAgregarGrafico.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@type='button' and @data-bs-target='#modal-add-graphmodule']"
)

WebUI.waitForElementClickable(btnAgregarGrafico, 10)
WebUI.click(btnAgregarGrafico)

// ===============================
// VALIDAR / SELECCIONAR "Dashboard QA" EN CHOSEN
// ===============================

// Obtener texto actual del chosen
TestObject spanGraphModule = new TestObject()
spanGraphModule.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='drp_graphmodule_chosen']//a/span"
)

String valorActual = WebUI.getText(spanGraphModule)

// Si NO es "Dashboard QA", abrir chosen y seleccionarlo
if (valorActual != "Dashboard QA") {

	// Abrir chosen
	TestObject chosenGraphModule = new TestObject()
	chosenGraphModule.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[@id='drp_graphmodule_chosen']//a"
	)
	WebUI.click(chosenGraphModule)

	// Seleccionar "Dashboard QA"
	TestObject opcionDashboardQA = new TestObject()
	opcionDashboardQA.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[@id='drp_graphmodule_chosen']//li[contains(@class,'active-result') and normalize-space()='Dashboard QA']"
	)

	WebUI.waitForElementClickable(opcionDashboardQA, 10)
	WebUI.click(opcionDashboardQA)
}


// ===============================
// CLIC EN BOTÓN "Agregar" DEL MODAL
// ===============================
TestObject btnAgregarModal = new TestObject()
btnAgregarModal.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@type='submit' and contains(@class,'btn-submit-form')]"
)

WebUI.waitForElementClickable(btnAgregarModal, 10)
WebUI.click(btnAgregarModal)

// ===============================
// CLIC EN GUARDAR (Dashboard)
// ===============================
TestObject btnGuardarDashboard = new TestObject()
btnGuardarDashboard.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//button[@type='submit' and .//strong[normalize-space()='GUARDAR']]"
)

WebUI.waitForElementClickable(btnGuardarDashboard, 10)
WebUI.click(btnGuardarDashboard)


// ===============================
// VALIDAR GROWL "Cambios guardados"
// ===============================
TestObject growlGuardadoDashboard = new TestObject()
growlGuardadoDashboard.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'growl')]//div[contains(@class,'alert-success') and contains(.,'Cambios guardados')]"
)

WebUI.waitForElementVisible(growlGuardadoDashboard, 10)


// ===============================
// CERRAR EL GROWL
// ===============================
TestObject btnCerrarGrowlDashboard  = new TestObject()
btnCerrarGrowlDashboard .addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'growl')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowlDashboard , 10)
WebUI.click(btnCerrarGrowlDashboard )

// ===============================
// VOLVER A LA PANTALLA PRINCIPAL (clic en el logo)
// ===============================
TestObject btnHome = new TestObject()
btnHome.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@id='company']//a[@class='navbar-brand']"
)

WebUI.waitForElementClickable(btnHome, 10)
WebUI.click(btnHome)

// ===============================
// VALIDAR QUE EL GRÁFICO "Dashboard QA" ESTÁ VISIBLE
// ===============================
TestObject graficoDashboardQA = new TestObject()
graficoDashboardQA.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'graph-container')]//h5[contains(normalize-space(),'Dashboard QA')]"
)

WebUI.waitForElementVisible(graficoDashboardQA, 15)

