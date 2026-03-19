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

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ REQUERIMIENTOS
// ===============================
TestObject menuReqNav = new TestObject('menuReqNav')
menuReqNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//li[contains(@class,'nav-item') and contains(@class,'dropdown')]//a[contains(@class,'nav-link') and .//span[normalize-space()='Requerimientos']]"
)

WebUI.waitForElementVisible(menuReqNav, 10)
WebUI.mouseOver(menuReqNav)
WebUI.delay(1)


// ===============================
// 2) FORZAR APERTURA DEL DROPDOWN
// ===============================
String jsOpenReqNav = """
    document.querySelectorAll("li.nav-item.dropdown.dropend").forEach(function(li){
        var span = li.querySelector("span.w-md.text-truncate");
        if(span && span.innerText.trim() === "Requerimientos"){
            var menu = li.querySelector(".dropdown-menu");
            if(menu){
                menu.classList.add("show");
                menu.style.display = "block";
            }
        }
    });
"""
WebUI.executeJavaScript(jsOpenReqNav, null)
WebUI.delay(1)


// ===============================
// 3) CLICK EN LA OPCIÓN "Requerimientos" (SIN IMPORTAR EL HREF)
// ===============================
TestObject opcionReqNav = new TestObject('opcionReqNav')
opcionReqNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'dropdown-menu')]//a[@class='dropdown-item' and normalize-space()='Requerimientos']"
)

WebUI.waitForElementClickable(opcionReqNav, 10)
WebUI.click(opcionReqNav)

WebUI.comment("✔ Se ingresó correctamente a la sección Requerimientos")



// ======================================================================
// FUNCIÓN: Aplicar filtro rápido de Aprobación
// ======================================================================
void aplicarFiltroAprobacion(String opcion) {

	// Abrimos el dropdown del filtro rápido
	TestObject dropdownFiltro = new TestObject('dropdownFiltro')
	dropdownFiltro.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'quickfilter-container')]//a[contains(@class,'dropdown-toggle')]"
	)

	WebUI.waitForElementClickable(dropdownFiltro, 10)
	WebUI.click(dropdownFiltro)
	WebUI.delay(1)

	// Seleccionamos la opción deseada
	TestObject opcionFiltro = new TestObject('opcionFiltro')
	opcionFiltro.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//a[@class='dropdown-item btn-quickfilter-option px-2' and normalize-space(text())='${opcion}']"
	)

	WebUI.waitForElementClickable(opcionFiltro, 10)
	WebUI.click(opcionFiltro)
	WebUI.delay(1)

	WebUI.comment("✔ Filtro aplicado correctamente: ${opcion}")
}


// ======================================================================
// FUNCIÓN: Validar columna 'Estado Aprobación' según filtro aplicado
// ======================================================================
void validarEstadoAprobacion(String estadoEsperado) {

	// Para "Todos" y "Abiertos" NO validamos nada
	if (estadoEsperado == "Todos" || estadoEsperado == "Abiertos") {
		WebUI.comment("✔ No se valida estado para '${estadoEsperado}' (comportamiento esperado)")
		return
	}

	// Selector de la columna Estado Aprobación (columna 5)
	TestObject estados = new TestObject('estados')
	estados.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[5]//span"
	)

	List<WebElement> listaEstados = WebUI.findWebElements(estados, 10)

	if (listaEstados.isEmpty()) {
		WebUI.comment("⚠ No hay filas para validar en la tabla")
		return
	}

	// Validamos fila por fila
	for (WebElement e : listaEstados) {
		String valor = e.getText().trim()

		if (!valor.equalsIgnoreCase(estadoEsperado)) {
			WebUI.comment("❌ Estado encontrado: '${valor}' — esperado: '${estadoEsperado}'")
			WebUI.verifyEqual(valor, estadoEsperado)  // Fuerza el fail
		}
	}

	WebUI.comment("✔ Todas las filas coinciden con el estado '${estadoEsperado}'")
}


// ======================================================================
// TEST CASE: Validación de filtros rápidos de Aprobación
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Validación de filtros de Aprobación ")
WebUI.comment("==============================================")


// ------------------------------
// Filtro: Pendientes
// ------------------------------
aplicarFiltroAprobacion("Pendientes")
validarEstadoAprobacion("Pendiente")


// ------------------------------
// Filtro: Aprobados
// ------------------------------
aplicarFiltroAprobacion("Aprobados")
validarEstadoAprobacion("Aprobado")


// ------------------------------
// Filtro: Rechazados
// ------------------------------
aplicarFiltroAprobacion("Rechazados")   // ← PLURAL (dropdown)
validarEstadoAprobacion("Rechazado")    // ← SINGULAR (tabla)



// ------------------------------
// Filtro: Abiertos (NO valida)
// ------------------------------
aplicarFiltroAprobacion("Abiertos")
validarEstadoAprobacion("Abiertos")


// ------------------------------
// Filtro: Todos (NO valida)
// ------------------------------
aplicarFiltroAprobacion("Todos")
validarEstadoAprobacion("Todos")


WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Filtros validados correctamente ")
WebUI.comment("==============================================")
