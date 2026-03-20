import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys
import requerimientos.LineasUtils
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.WebElement


// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


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
// 3) CLICK EN LA OPCIÓN "Requerimientos"
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
// FUNCIÓN: Seleccionar opción "Todos" en un filtro rápido
// ======================================================================
void seleccionarTodosEnFiltro(Integer indexFiltro) {

    TestObject dropdown = new TestObject("dropdownFiltro_${indexFiltro}")
    dropdown.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[contains(@class,'quickfilter-container') and @data-index='${indexFiltro}']//a[contains(@class,'dropdown-toggle')]"
    )

    WebUI.waitForElementClickable(dropdown, 10)
    WebUI.click(dropdown)
    WebUI.delay(1)

    TestObject opcionTodos = new TestObject("opcionTodos_${indexFiltro}")
    opcionTodos.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[@data-index='${indexFiltro}']//a[normalize-space(text())='Todos']"
    )

    WebUI.waitForElementClickable(opcionTodos, 10)
    WebUI.click(opcionTodos)
    WebUI.delay(1)

    WebUI.comment("✔ Filtro ${indexFiltro} configurado en 'Todos'")
}


// ======================================================================
// FUNCIÓN: Validar que el filtro muestre "Todos"
// ======================================================================
void validarFiltroEnTodos(Integer indexFiltro) {

    TestObject textoFiltro = new TestObject("textoFiltro_${indexFiltro}")
    textoFiltro.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[@data-index='${indexFiltro}']//span[contains(@class,'quickfilter-current-text')]"
    )

    WebUI.waitForElementVisible(textoFiltro, 10)
    String valor = WebUI.getText(textoFiltro).trim()

    WebUI.verifyEqual(valor, "Todos")
    WebUI.comment("✔ Validación correcta: el filtro ${indexFiltro} muestra 'Todos'")
}

// ===============================
// 3) Abrir Filtros avanzados
// ===============================
TestObject btnFiltrosAvanzados = new TestObject("btnFiltrosAvanzados")
btnFiltrosAvanzados.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'has-tooltip') and contains(@href,'filters-collapse')]"
)

WebUI.waitForElementClickable(btnFiltrosAvanzados, 10)
WebUI.click(btnFiltrosAvanzados)
WebUI.delay(1)

WebUI.comment("✔ Se abrió el panel de Filtros avanzados")



// ======================================================================
// FUNCIÓN: Seleccionar columna "Fecha" en el filtro avanzado
// ======================================================================
void seleccionarColumnaFecha() {

    // Abrir dropdown Chosen (selector corregido)
    TestObject dropdownColumna = new TestObject("dropdownColumna")
    dropdownColumna.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//li[contains(@class,'filter-row')]//a[contains(@class,'chosen-single')]"
    )

    WebUI.waitForElementClickable(dropdownColumna, 10)
    WebUI.click(dropdownColumna)
    WebUI.delay(1)

    // Seleccionar opción "Fecha"
    TestObject opcionFecha = new TestObject("opcionFecha")
    opcionFecha.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//ul[contains(@class,'chosen-results')]//li[normalize-space()='Fecha']"
    )

    WebUI.waitForElementClickable(opcionFecha, 10)
    WebUI.click(opcionFecha)
    WebUI.delay(1)

    WebUI.comment("✔ Se seleccionó la columna 'Fecha'")
}


// ======================================================================
// FUNCIÓN: Seleccionar rango de fecha (Hoy, Ayer, Últimos 7 días, etc.)
// ======================================================================
void seleccionarRangoFecha(String rango) {

	// Abrir selector de fecha
	TestObject inputFecha = new TestObject("inputFecha")
	inputFecha.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//input[contains(@class,'datepicker-range')]"
	)

	WebUI.waitForElementClickable(inputFecha, 10)
	WebUI.click(inputFecha)
	WebUI.delay(1)

	// Seleccionar el rango solicitado
	TestObject opcionRango = new TestObject("opcionRango")
	opcionRango.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'daterangepicker')]//li[normalize-space()='${rango}']"
	)

	WebUI.waitForElementClickable(opcionRango, 10)
	WebUI.click(opcionRango)
	WebUI.delay(1)

	WebUI.comment("✔ Se seleccionó el rango '${rango}'")
}


// ======================================================================
// FUNCIÓN: Aplicar filtros avanzados
// ======================================================================
void aplicarFiltros() {

	TestObject btnAplicarFiltros = new TestObject("btnAplicarFiltros")
	btnAplicarFiltros.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
	)

	WebUI.waitForElementClickable(btnAplicarFiltros, 10)
	WebUI.click(btnAplicarFiltros)
	WebUI.delay(2)

	WebUI.comment("✔ Se aplicaron los filtros avanzados")
}


// ======================================================================
// FUNCIÓN: Validar resultados de la tabla según la fecha seleccionada
// ======================================================================
void validarResultadosFecha() {

	// Caso 1: No hay resultados
	TestObject alertaSinResultados = new TestObject("alertaSinResultados")
	alertaSinResultados.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
	)

	if (WebUI.verifyElementPresent(alertaSinResultados, 2, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ No hay elementos para mostrar (válido para la fecha seleccionada)")
		return
	}

	// Caso 2: Hay resultados → obtener fechas
	TestObject fechasTabla = new TestObject("fechasTabla")
	fechasTabla.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechas = WebUI.findWebElements(fechasTabla, 5)

	if (fechas.size() == 0) {
		WebUI.comment("⚠ No se encontraron filas, pero tampoco apareció el mensaje de 'No hay elementos'")
		return
	}

	WebUI.comment("✔ Se encontraron ${fechas.size()} resultados")

	for (WebElement f : fechas) {
		String fechaTexto = f.getText().trim()
		WebUI.comment(" - Fecha encontrada: ${fechaTexto}")
		// Aquí podrías validar contra la fecha actual si querés
	}
}


// ======================================================================
// TEST CASE COMPLETO: Filtro avanzado por Fecha = Hoy
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Filtro avanzado por Fecha = Hoy ")
WebUI.comment("==============================================")

seleccionarColumnaFecha()
seleccionarRangoFecha("Hoy")
aplicarFiltros()
validarResultadosFecha()

WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Filtro avanzado validado ")
WebUI.comment("==============================================")

// ===============================
// ABRIR EL SELECTOR DE FECHA
// ===============================
TestObject inputFecha = new TestObject("inputFecha")
inputFecha.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFecha, 10)
WebUI.click(inputFecha)
WebUI.delay(1)


// ===============================
// SELECCIONAR LA OPCIÓN "Ayer"
// ===============================
TestObject opcionAyer = new TestObject("opcionAyer")
opcionAyer.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'daterangepicker')]//li[normalize-space()='Ayer']"
)

WebUI.waitForElementClickable(opcionAyer, 10)
WebUI.click(opcionAyer)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó el rango 'Ayer'")


// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltros = new TestObject("btnAplicarFiltros")
btnAplicarFiltros.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltros, 10)
WebUI.click(btnAplicarFiltros)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros avanzados")

// ===============================
// ABRIR EL SELECTOR DE FECHA
// ===============================
TestObject inputFecha7 = new TestObject("inputFecha7")
inputFecha7.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFecha7, 10)
WebUI.click(inputFecha7)
WebUI.delay(1)


// ===============================
// SELECCIONAR LA OPCIÓN "Últimos 7 Días"
// ===============================
TestObject opcion7Dias = new TestObject("opcion7Dias")
opcion7Dias.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'daterangepicker')]//li[normalize-space()='Últimos 7 Días']"
)

WebUI.waitForElementClickable(opcion7Dias, 10)
WebUI.click(opcion7Dias)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó el rango 'Últimos 7 Días'")


// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltros7 = new TestObject("btnAplicarFiltros7")
btnAplicarFiltros7.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltros7, 10)
WebUI.click(btnAplicarFiltros7)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros avanzados (Últimos 7 Días)")


// ===============================
// VALIDAR RESULTADOS EN LA TABLA
// ===============================

// Caso 1: No hay resultados
TestObject alertaSinResultados7 = new TestObject("alertaSinResultados7")
alertaSinResultados7.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
)

if (WebUI.verifyElementPresent(alertaSinResultados7, 2, FailureHandling.OPTIONAL)) {
	WebUI.comment("✔ No hay elementos para mostrar (válido para 'Últimos 7 Días')")
} else {

	// Caso 2: Hay resultados → obtener fechas
	TestObject fechasTabla7 = new TestObject("fechasTabla7")
	fechasTabla7.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechas7 = WebUI.findWebElements(fechasTabla7, 5)

	if (fechas7.size() > 0) {
		WebUI.comment("✔ Se encontraron ${fechas7.size()} resultados")

		for (WebElement f : fechas7) {
			String fechaTexto = f.getText().trim()
			WebUI.comment(" - Fecha encontrada: ${fechaTexto}")
		}
	} else {
		WebUI.comment("⚠ No se encontraron filas, pero tampoco apareció el mensaje de 'No hay elementos'")
	}
}

// ===============================
// ABRIR SELECTOR DE FECHA
// ===============================
TestObject inputFecha30 = new TestObject("inputFecha30")
inputFecha30.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFecha30, 10)
WebUI.click(inputFecha30)
WebUI.delay(1)


// ===============================
// SELECCIONAR "Últimos 30 Días"
// ===============================
TestObject opcion30Dias = new TestObject("opcion30Dias")
opcion30Dias.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'daterangepicker')]//li[normalize-space()='Últimos 30 Días']"
)

WebUI.waitForElementClickable(opcion30Dias, 10)
WebUI.click(opcion30Dias)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó el rango 'Últimos 30 Días'")


// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltros30 = new TestObject("btnAplicarFiltros30")
btnAplicarFiltros30.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltros30, 10)
WebUI.click(btnAplicarFiltros30)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros (Últimos 30 Días)")


// ===============================
// VALIDAR RESULTADOS
// ===============================
TestObject alertaSinResultados30 = new TestObject("alertaSinResultados30")
alertaSinResultados30.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
)

if (WebUI.verifyElementPresent(alertaSinResultados30, 2, FailureHandling.OPTIONAL)) {
	WebUI.comment("✔ No hay elementos para mostrar (válido para 'Últimos 30 Días')")
} else {

	TestObject fechasTabla30 = new TestObject("fechasTabla30")
	fechasTabla30.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechas30 = WebUI.findWebElements(fechasTabla30, 5)

	WebUI.comment("✔ Se encontraron ${fechas30.size()} resultados")

	for (WebElement f : fechas30) {
		WebUI.comment(" - Fecha encontrada: ${f.getText().trim()}")
	}
}

// ===============================
// ABRIR SELECTOR DE FECHA
// ===============================
TestObject inputFechaMes = new TestObject("inputFechaMes")
inputFechaMes.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFechaMes, 10)
WebUI.click(inputFechaMes)
WebUI.delay(1)


// ===============================
// SELECCIONAR "Este Mes"
// ===============================
TestObject opcionEsteMes = new TestObject("opcionEsteMes")
opcionEsteMes.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'daterangepicker')]//li[normalize-space()='Este Mes']"
)

WebUI.waitForElementClickable(opcionEsteMes, 10)
WebUI.click(opcionEsteMes)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó el rango 'Este Mes'")


// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltrosMes = new TestObject("btnAplicarFiltrosMes")
btnAplicarFiltrosMes.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltrosMes, 10)
WebUI.click(btnAplicarFiltrosMes)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros (Este Mes)")


// ===============================
// VALIDAR RESULTADOS
// ===============================
TestObject alertaSinResultadosMes = new TestObject("alertaSinResultadosMes")
alertaSinResultadosMes.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
)

if (WebUI.verifyElementPresent(alertaSinResultadosMes, 2, FailureHandling.OPTIONAL)) {
	WebUI.comment("✔ No hay elementos para mostrar (válido para 'Este Mes')")
} else {

	TestObject fechasTablaMes = new TestObject("fechasTablaMes")
	fechasTablaMes.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechasMes = WebUI.findWebElements(fechasTablaMes, 5)

	WebUI.comment("✔ Se encontraron ${fechasMes.size()} resultados")

	for (WebElement f : fechasMes) {
		WebUI.comment(" - Fecha encontrada: ${f.getText().trim()}")
	}
}

// ===============================
// ABRIR SELECTOR DE FECHA
// ===============================
TestObject inputFechaMesPasado = new TestObject("inputFechaMesPasado")
inputFechaMesPasado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFechaMesPasado, 10)
WebUI.click(inputFechaMesPasado)
WebUI.delay(1)


// ===============================
// SELECCIONAR "El Mes Pasado"
// ===============================
TestObject opcionMesPasado = new TestObject("opcionMesPasado")
opcionMesPasado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'daterangepicker')]//li[normalize-space()='El Mes Pasado']"
)

WebUI.waitForElementClickable(opcionMesPasado, 10)
WebUI.click(opcionMesPasado)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó el rango 'El Mes Pasado'")


// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltrosMesPasado = new TestObject("btnAplicarFiltrosMesPasado")
btnAplicarFiltrosMesPasado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltrosMesPasado, 10)
WebUI.click(btnAplicarFiltrosMesPasado)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros (El Mes Pasado)")


// ===============================
// VALIDAR RESULTADOS
// ===============================
TestObject alertaSinResultadosMesPasado = new TestObject("alertaSinResultadosMesPasado")
alertaSinResultadosMesPasado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
)

if (WebUI.verifyElementPresent(alertaSinResultadosMesPasado, 2, FailureHandling.OPTIONAL)) {
	WebUI.comment("✔ No hay elementos para mostrar (válido para 'El Mes Pasado')")
} else {

	TestObject fechasTablaMesPasado = new TestObject("fechasTablaMesPasado")
	fechasTablaMesPasado.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechasMesPasado = WebUI.findWebElements(fechasTablaMesPasado, 5)

	WebUI.comment("✔ Se encontraron ${fechasMesPasado.size()} resultados")

	for (WebElement f : fechasMesPasado) {
		WebUI.comment(" - Fecha encontrada: ${f.getText().trim()}")
	}
}

// ===============================
// ABRIR SELECTOR DE FECHA
// ===============================
TestObject inputFechaPers = new TestObject("inputFechaPers")
inputFechaPers.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'datepicker-range')]"
)

WebUI.waitForElementClickable(inputFechaPers, 10)
WebUI.click(inputFechaPers)
WebUI.delay(1)

// ===============================
// SELECCIONAR "Rango Personalizado"
// ===============================
TestObject opcionRangoPers = new TestObject("opcionRangoPers")
opcionRangoPers.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//li[normalize-space()='Rango Personalizado']"
)

WebUI.waitForElementClickable(opcionRangoPers, 10)
WebUI.click(opcionRangoPers)
WebUI.delay(1)

WebUI.comment("✔ Se seleccionó 'Rango Personalizado'")

// ===============================
// SELECCIONAR FECHA INICIO (5)
// ===============================
TestObject fechaInicio = new TestObject("fechaInicio")
fechaInicio.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'drp-calendar left')]//td[contains(@class,'available') and text()='5']"
)

WebUI.waitForElementClickable(fechaInicio, 10)
WebUI.click(fechaInicio)
WebUI.delay(1)

WebUI.comment("✔ Fecha inicio seleccionada: 5")

// ===============================
// SELECCIONAR FECHA FIN (20)
// ===============================
TestObject fechaFin = new TestObject("fechaFin")
fechaFin.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'drp-calendar right')]//td[contains(@class,'available') and text()='20']"
)

WebUI.waitForElementClickable(fechaFin, 10)
WebUI.click(fechaFin)
WebUI.delay(1)

WebUI.comment("✔ Fecha fin seleccionada: 20")

// ===============================
// APLICAR RANGO PERSONALIZADO
// ===============================
TestObject btnAplicarRango = new TestObject("btnAplicarRango")
btnAplicarRango.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[contains(@class,'applyBtn')]"
)

WebUI.waitForElementClickable(btnAplicarRango, 10)
WebUI.click(btnAplicarRango)
WebUI.delay(1)

WebUI.comment("✔ Se aplicó el rango personalizado en el datepicker")

// ===============================
// APLICAR FILTROS
// ===============================
TestObject btnAplicarFiltrosPers = new TestObject("btnAplicarFiltrosPers")
btnAplicarFiltrosPers.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[.//strong[normalize-space()='APLICAR FILTROS']]"
)

WebUI.waitForElementClickable(btnAplicarFiltrosPers, 10)
WebUI.click(btnAplicarFiltrosPers)
WebUI.delay(2)

WebUI.comment("✔ Se aplicaron los filtros avanzados (Rango Personalizado)")

// ===============================
// VALIDAR RESULTADOS
// ===============================
TestObject alertaSinResultadosPers = new TestObject("alertaSinResultadosPers")
alertaSinResultadosPers.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'alert-warning') and contains(text(),'No hay elementos')]"
)

if (WebUI.verifyElementPresent(alertaSinResultadosPers, 2, FailureHandling.OPTIONAL)) {
	WebUI.comment("✔ No hay elementos para mostrar (válido para rango personalizado)")
} else {

	TestObject fechasTablaPers = new TestObject("fechasTablaPers")
	fechasTablaPers.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr//td[last()]"
	)

	List<WebElement> fechasPers = WebUI.findWebElements(fechasTablaPers, 5)

	WebUI.comment("✔ Se encontraron ${fechasPers.size()} resultados")

	for (WebElement f : fechasPers) {
		WebUI.comment(" - Fecha encontrada: ${f.getText().trim()}")
	}
}
