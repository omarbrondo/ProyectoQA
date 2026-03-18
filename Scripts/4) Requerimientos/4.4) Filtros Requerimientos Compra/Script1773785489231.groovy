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
// 2) ABRIR MENÚ "REQUERIMIENTOS" (HOVER)
// ===============================
TestObject menuRequerimientos = new TestObject()
menuRequerimientos.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[normalize-space(text())='Requerimientos']/parent::a"
)

WebUI.mouseOver(menuRequerimientos)
WebUI.delay(1) // pequeño delay para que el menú se despliegue

// ===============================
// 3) CLIC EN OPCIÓN "REQUERIMIENTOS"
// ===============================
TestObject opcionRequerimientos = new TestObject()
opcionRequerimientos.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@class='dropdown-item' and @href='/es/PurchaseRequest/List']"
)

WebUI.waitForElementClickable(opcionRequerimientos, 10)
WebUI.click(opcionRequerimientos)



// ======================================================================
// FUNCIÓN: Aplicar filtro rápido de Estado Compra
// ======================================================================
void aplicarFiltroCompra(String opcion) {

	// Abrimos el dropdown del filtro rápido "Compra"
	TestObject dropdownFiltro = new TestObject('dropdownFiltroCompra')
	dropdownFiltro.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'quickfilter-container') and @data-index='1']//a[contains(@class,'dropdown-toggle')]"
	)

	WebUI.waitForElementClickable(dropdownFiltro, 10)
	WebUI.click(dropdownFiltro)
	WebUI.delay(1)

	// Seleccionamos la opción deseada
	TestObject opcionFiltro = new TestObject('opcionFiltroCompra')
	opcionFiltro.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[@data-index='1']//a[@class='dropdown-item btn-quickfilter-option px-2' and normalize-space(text())='${opcion}']"
	)

	WebUI.waitForElementClickable(opcionFiltro, 10)
	WebUI.click(opcionFiltro)
	WebUI.delay(1)

	WebUI.comment("✔ Filtro de Compra aplicado: ${opcion}")
}


// ======================================================================
// FUNCIÓN: Validar columna 'Estado compra' según filtro aplicado
// ======================================================================
void validarEstadoCompra(String filtro) {

	// Casos donde NO validamos nada
	if (filtro == "Todos") {
		WebUI.comment("✔ No se valida estado para '${filtro}' (comportamiento esperado)")
		return
	}

	// Mapeo de filtro → valores permitidos en la tabla
	Map<String, List<String>> reglas = [
		"Abiertos"      : ["Pendiente", "Parcial"],
		"Pendientes"    : ["Pendiente"],
		"Parciales"     : ["Parcial"],
		"Vinculado a OC": ["Vinculado a OC"],
		"Anulados"      : ["Anulado"]
	]

	List<String> permitidos = reglas[filtro]

	if (permitidos == null) {
		WebUI.comment("⚠ No hay reglas definidas para el filtro '${filtro}'")
		return
	}

	// Selector de la columna Estado compra (columna 6)
	TestObject estados = new TestObject('estadosCompra')
	estados.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[6]//span"
	)

	List<WebElement> listaEstados = WebUI.findWebElements(estados, 10)

	if (listaEstados.isEmpty()) {
		WebUI.comment("⚠ No hay filas para validar en la tabla")
		return
	}

	// Validamos fila por fila
	for (WebElement e : listaEstados) {
		String valor = e.getText().trim()

		if (!permitidos.contains(valor)) {
			WebUI.comment("❌ Estado encontrado: '${valor}' — permitidos: ${permitidos}")
			WebUI.verifyEqual(valor, permitidos[0]) // fuerza el fail
		}
	}

	WebUI.comment("✔ Todas las filas cumplen con el filtro '${filtro}' → ${permitidos}")
}


// ======================================================================
// TEST CASE: Validación de filtros rápidos de Estado Compra
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Validación de filtros de Estado Compra ")
WebUI.comment("==============================================")


// ------------------------------
// Filtro: Abiertos
// ------------------------------
aplicarFiltroCompra("Abiertos")
validarEstadoCompra("Abiertos")


// ------------------------------
// Filtro: Pendientes
// ------------------------------
aplicarFiltroCompra("Pendientes")
validarEstadoCompra("Pendientes")


// ------------------------------
// Filtro: Parciales
// ------------------------------
aplicarFiltroCompra("Parciales")
validarEstadoCompra("Parciales")


// ------------------------------
// Filtro: Vinculado a OC
// ------------------------------
aplicarFiltroCompra("Vinculado a OC")
validarEstadoCompra("Vinculado a OC")


// ------------------------------
// Filtro: Anulados
// ------------------------------
aplicarFiltroCompra("Anulados")
validarEstadoCompra("Anulados")


// ------------------------------
// Filtro: Todos (NO valida)
// ------------------------------
aplicarFiltroCompra("Todos")
validarEstadoCompra("Todos")


WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Filtros de Compra validados correctamente ")
WebUI.comment("==============================================")

