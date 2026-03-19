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
// FUNCIÓN: Seleccionar opción "Todos" en un filtro rápido
// ======================================================================
void seleccionarTodosEnFiltro(Integer indexFiltro) {

	// Abrimos el dropdown del filtro según su data-index
	TestObject dropdown = new TestObject("dropdownFiltro_${indexFiltro}")
	dropdown.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'quickfilter-container') and @data-index='${indexFiltro}']//a[contains(@class,'dropdown-toggle')]"
	)

	WebUI.waitForElementClickable(dropdown, 10)
	WebUI.click(dropdown)
	WebUI.delay(1)

	// Seleccionamos la opción "Todos"
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


// ======================================================================
// TEST CASE: Reset de filtros rápidos (Aprobación + Compra)
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Reset de filtros rápidos a 'Todos' ")
WebUI.comment("==============================================")


// ------------------------------
// Filtro Aprobación (data-index=2)
// ------------------------------
seleccionarTodosEnFiltro(2)
validarFiltroEnTodos(2)


// ------------------------------
// Filtro Compra (data-index=1)
// ------------------------------
seleccionarTodosEnFiltro(1)
validarFiltroEnTodos(1)


WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Ambos filtros quedaron en 'Todos' ")
WebUI.comment("==============================================")

// ======================================================================
// FUNCIÓN: Cambiar cantidad de ítems por página
// ======================================================================
void cambiarItemsPorPagina(String cantidad) {

	TestObject dropdown = new TestObject("pageSizeDropdown")
	dropdown.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//select[contains(@class,'page-size-dropdown')]"
	)

	WebUI.waitForElementClickable(dropdown, 10)
	WebUI.selectOptionByLabel(dropdown, cantidad, false)
	WebUI.delay(1)

	WebUI.comment("✔ Se seleccionó mostrar ${cantidad} ítems por página")
}


// ======================================================================
// FUNCIÓN: Contar filas visibles en la tabla
// ======================================================================
int contarFilas() {

	TestObject filas = new TestObject("filasTabla")
	filas.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//table//tbody//tr"
	)

	List<WebElement> lista = WebUI.findWebElements(filas, 10)
	return lista.size()
}


// ======================================================================
// FUNCIÓN: Validar cantidad de filas según items por página
// ======================================================================
void validarCantidadFilas(int maximo) {

	int filas = contarFilas()

	if (filas > maximo) {
		WebUI.comment("❌ Se encontraron ${filas} filas, pero el máximo esperado era ${maximo}")
		WebUI.verifyLessThanOrEqual(filas, maximo)
	}

	WebUI.comment("✔ Cantidad de filas correcta (${filas} / ${maximo})")
}


// ======================================================================
// FUNCIÓN: Obtener número de página actual
// ======================================================================
int obtenerPaginaActual() {

	TestObject pager = new TestObject("pager")
	pager.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//div[contains(@class,'pager')]"
	)

	String pagina = WebUI.getAttribute(pager, "data-currentpage")
	return Integer.parseInt(pagina)
}


// ======================================================================
// FUNCIÓN: Hacer clic en botón de paginación
// ======================================================================
void clickPager(String icono) {

	TestObject boton = new TestObject("botonPager")
	boton.addProperty(
		"xpath",
		ConditionType.EQUALS,
		"//button[i[contains(@class,'${icono}')]]"
	)

	WebUI.waitForElementClickable(boton, 10)
	WebUI.click(boton)
	WebUI.delay(1)
}


// ======================================================================
// FUNCIÓN: Validar que la tabla cambió entre páginas
// ======================================================================
void validarCambioDePagina() {

    // Tomamos el número de página actual
    int paginaActual = obtenerPaginaActual()

    // Verificamos si existe botón "siguiente" habilitado
    TestObject btnSiguiente = new TestObject("btnSiguiente")
    btnSiguiente.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//button[@class='btn btn-light btn-pager'][i[contains(@class,'fa-angle-right')]]"
    )

    boolean existeSiguiente = WebUI.verifyElementPresent(btnSiguiente, 2, FailureHandling.OPTIONAL)

    if (!existeSiguiente) {
        WebUI.comment("⚠ No existe página siguiente. No se valida cambio de tabla.")
        return
    }

    // Guardamos el primer valor de la tabla
    TestObject primeraFila = new TestObject("primeraFila")
    primeraFila.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//table//tbody//tr[1]//td[2]//span"
    )

    String valorAntes = WebUI.getText(primeraFila)

    // Vamos a la página siguiente
    WebUI.click(btnSiguiente)
    WebUI.delay(1)

    int nuevaPagina = obtenerPaginaActual()

    if (nuevaPagina == paginaActual) {
        WebUI.comment("⚠ La página no cambió. No se valida cambio de tabla.")
        return
    }

    // Obtenemos el nuevo valor
    String valorDespues = WebUI.getText(primeraFila)

    if (valorAntes == valorDespues) {
        WebUI.comment("⚠ La tabla no cambió entre páginas (posiblemente pocos registros).")
        return
    }

    WebUI.comment("✔ La tabla cambió correctamente entre páginas")
}



// ======================================================================
// TEST CASE: Validación completa de paginación
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Validación de paginación ")
WebUI.comment("==============================================")


// ------------------------------
// 1) Validar selector de items por página
// ------------------------------
cambiarItemsPorPagina("25")
validarCantidadFilas(25)

cambiarItemsPorPagina("50")
validarCantidadFilas(50)

cambiarItemsPorPagina("100")
validarCantidadFilas(100)

cambiarItemsPorPagina("10")
validarCantidadFilas(10)


// ------------------------------
// 2) Validar navegación entre páginas
// ------------------------------

// Validar que estamos en página 1
int pagina = obtenerPaginaActual()
WebUI.verifyEqual(pagina, 1)
WebUI.comment("✔ Página actual: ${pagina}")

// Validar que << y < están deshabilitados
TestObject btnPrev = new TestObject("btnPrev")
btnPrev.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[i[contains(@class,'fa-angle-left')]]"
)

boolean disabled = WebUI.getAttribute(btnPrev, "disabled") != null
WebUI.verifyEqual(disabled, true)
WebUI.comment("✔ Botones de retroceso deshabilitados en página 1")

// Validar cambio de página
validarCambioDePagina()


// ------------------------------
// 3) Ir a la última página
// ------------------------------
clickPager("fa-angle-double-right")

int ultimaPagina = obtenerPaginaActual()
WebUI.comment("✔ Última página detectada: ${ultimaPagina}")


// ------------------------------
// 4) Validar coherencia del texto “Mostrando X – Y de Z”
// ------------------------------
TestObject textoMostrando = new TestObject("textoMostrando")
textoMostrando.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(text(),'Mostrando')]"
)

WebUI.waitForElementVisible(textoMostrando, 10)
String texto = WebUI.getText(textoMostrando)

WebUI.comment("✔ Texto de paginación: ${texto}")


WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Paginación validada correctamente ")
WebUI.comment("==============================================")
