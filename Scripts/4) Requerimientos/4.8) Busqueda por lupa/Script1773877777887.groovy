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

// ===============================
// OBTENER TODOS LOS CÓDIGOS DE LA TABLA
// ===============================
TestObject codigosTabla = new TestObject("codigosTabla")
codigosTabla.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tbody//tr//td[3]//span"
)

List<WebElement> listaCodigos = WebUI.findWebElements(codigosTabla, 10)

if (listaCodigos.size() == 0) {
	WebUI.comment("⚠ No hay códigos en la tabla para seleccionar")
	assert false
}

// Elegir uno al azar
Random rnd = new Random()
int index = rnd.nextInt(listaCodigos.size())
String codigoSeleccionado = listaCodigos.get(index).getText().trim()

WebUI.comment("✔ Código seleccionado al azar: " + codigoSeleccionado)

// ===============================
// ESCRIBIR EL CÓDIGO EN EL INPUT DE BÚSQUEDA
// ===============================
TestObject inputBusqueda = new TestObject("inputBusqueda")
inputBusqueda.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//input[contains(@class,'keyword-filter')]"
)

WebUI.waitForElementVisible(inputBusqueda, 10)
WebUI.setText(inputBusqueda, codigoSeleccionado)
WebUI.delay(1)

// ===============================
// CLIC EN EL BOTÓN DE BÚSQUEDA
// ===============================
TestObject btnBuscar = new TestObject("btnBuscar")
btnBuscar.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[contains(@class,'btn-keyword-filter')]"
)

WebUI.waitForElementClickable(btnBuscar, 10)
WebUI.click(btnBuscar)
WebUI.delay(2)

// ===============================
// VALIDAR QUE EL CÓDIGO APARECE EN LA TABLA
// ===============================
TestObject codigosFiltrados = new TestObject("codigosFiltrados")
codigosFiltrados.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table//tbody//tr//td[3]//span"
)

List<WebElement> resultados = WebUI.findWebElements(codigosFiltrados, 10)

if (resultados.size() == 0) {
	WebUI.comment("⚠ No hay resultados después de la búsqueda")
	assert false
}

boolean encontrado = false

for (WebElement r : resultados) {
	if (r.getText().trim().equals(codigoSeleccionado)) {
		encontrado = true
		break
	}
}

WebUI.verifyEqual(encontrado, true)
WebUI.comment("✔ El código buscado aparece correctamente en la tabla")

