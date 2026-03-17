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


// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// 1) CERRAR DROPDOWN (SI HAY H3)
// ===============================
new utils.UIUtils().cerrarDropdownUsuarioSiEstaAbierto()


// ===============================
// 2) DEFINIR CAMPO DE BÚSQUEDA
// ===============================
TestObject campoBusquedaProveedor = new TestObject()
campoBusquedaProveedor.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//input[contains(@class,'keyword-filter') and @placeholder='Ingrese su búsqueda...']"
)


// ===============================
// 3) ESPERAR Y ESCRIBIR CUIT
// ===============================
WebUI.waitForElementVisible(campoBusquedaProveedor, 10)
WebUI.setText(campoBusquedaProveedor, GlobalVariable.CuitProveedor)


// ===============================
// 4) DEFINIR BOTÓN DE BUSCAR
// ===============================
TestObject btnBuscarProveedor = new TestObject()
btnBuscarProveedor.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//button[contains(@class,'btn-keyword-filter') and @data-url='/es/Supplier/List']"
)


// ===============================
// 5) ESPERAR Y HACER CLIC EN BUSCAR
// ===============================
WebUI.waitForElementClickable(btnBuscarProveedor, 10)
WebUI.click(btnBuscarProveedor)


// ===============================
// 6) DEFINIR ELEMENTO DEL CUIT EN LA TABLA
// ===============================
TestObject cuitEnTabla = new TestObject()
cuitEnTabla.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//table//tbody//tr//td//span[normalize-space(text())='" + GlobalVariable.CuitProveedor + "']"
)


// ===============================
// 7) VALIDAR QUE EL CUIT APAREZCA EN LA TABLA
// ===============================
boolean aparece = WebUI.verifyElementPresent(
    cuitEnTabla,
    10,
    FailureHandling.OPTIONAL
)

if (!aparece) {
    WebUI.comment("❌ El CUIT no apareció en la tabla: " + GlobalVariable.CuitProveedor)
    WebUI.takeScreenshot()
    WebUI.verifyEqual(aparece, true)   // Fuerza el FAIL
} else {
    WebUI.comment("✅ El CUIT apareció correctamente en la tabla: " + GlobalVariable.CuitProveedor)
}


// ===============================
// 8) RESET: IR A LISTA DE PROVEEDORES
// ===============================
WebUI.navigateToUrl("https://staging.proveedores.intiza.com/es/Supplier/List")

// Esperar a que cargue el campo de búsqueda
WebUI.waitForElementVisible(campoBusquedaProveedor, 10)


// ===============================
// 9) ABRIR PANEL DE FILTROS AVANZADOS
// ===============================
TestObject btnFiltrosAvanzados = new TestObject()
btnFiltrosAvanzados.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//a[@data-bs-toggle='collapse' and @href='#filters-collapse']"
)

WebUI.waitForElementClickable(btnFiltrosAvanzados, 10)
WebUI.click(btnFiltrosAvanzados)


// ===============================
// 10) VALIDAR QUE EL PANEL DE FILTROS SE ABRIÓ
// ===============================
TestObject panelFiltros = new TestObject()
panelFiltros.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'filter-card') and @data-type='supplier']"
)

WebUI.waitForElementVisible(panelFiltros, 10)


// ===============================
// 11) ABRIR DROPDOWN DE COLUMNA
// ===============================
TestObject dropdownColumna = new TestObject()
dropdownColumna.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'chosen-container')]//a[contains(@class,'chosen-single')]"
)

WebUI.waitForElementClickable(dropdownColumna, 10)
WebUI.click(dropdownColumna)


// ===============================
// 12) ESCRIBIR 'CUIT' EN EL BUSCADOR DEL DROPDOWN
// ===============================
TestObject buscadorChosen = new TestObject()
buscadorChosen.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//input[contains(@class,'chosen-search-input')]"
)

WebUI.waitForElementVisible(buscadorChosen, 10)
WebUI.setText(buscadorChosen, "CUIT")
WebUI.sendKeys(buscadorChosen, Keys.chord(Keys.ENTER))


// ===============================
// 13) SELECCIONAR OPERADOR 'IGUAL A'
// ===============================
TestObject operadorFiltro = new TestObject()
operadorFiltro.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//select[contains(@class,'drp-operator')]"
)

WebUI.waitForElementClickable(operadorFiltro, 10)
WebUI.selectOptionByValue(operadorFiltro, "Equal", false)


// ===============================
// 14) ESCRIBIR EL CUIT EN EL CAMPO DE VALOR
// ===============================
TestObject valorFiltro = new TestObject()
valorFiltro.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//input[contains(@class,'filter-value')]"
)

WebUI.waitForElementVisible(valorFiltro, 10)
WebUI.setText(valorFiltro, GlobalVariable.CuitProveedor)


// ===============================
// 15) APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltros = new TestObject()
btnAplicarFiltros.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//button[@type='submit' and contains(.,'APLICAR FILTROS')]"
)

WebUI.waitForElementClickable(btnAplicarFiltros, 10)
WebUI.click(btnAplicarFiltros)


// ===============================
// 16) VALIDAR QUE EL CUIT APAREZCA EN LA TABLA (POST-FILTROS)
// ===============================
TestObject cuitEnTablaFiltrado = new TestObject()
cuitEnTablaFiltrado.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//table//tbody//tr//td//span[normalize-space(text())='" + GlobalVariable.CuitProveedor + "']"
)

boolean apareceFiltrado = WebUI.verifyElementPresent(cuitEnTablaFiltrado, 10, FailureHandling.OPTIONAL)

WebUI.verifyEqual(apareceFiltrado, true)
