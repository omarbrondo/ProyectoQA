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


// ======================================================================
// TEST CASE: Reset de filtros rápidos
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Reset de filtros rápidos a 'Todos' ")
WebUI.comment("==============================================")

seleccionarTodosEnFiltro(2)
validarFiltroEnTodos(2)

seleccionarTodosEnFiltro(1)
validarFiltroEnTodos(1)

WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Ambos filtros quedaron en 'Todos' ")
WebUI.comment("==============================================")



// ======================================================================
// FUNCIÓN: Obtener lista de columnas visibles
// ======================================================================
List<String> obtenerColumnas() {

    TestObject columnas = new TestObject("columnasTabla")
    columnas.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//table//thead//th//span"
    )

    List<WebElement> lista = WebUI.findWebElements(columnas, 10)

    List<String> textos = []
    for (WebElement e : lista) {
        textos.add(e.getText().trim())
    }

    return textos
}


// ======================================================================
// FUNCIÓN: Validar columnas
// ======================================================================
void validarColumnas(List<String> esperadas) {

    List<String> actuales = obtenerColumnas()

    for (String col : esperadas) {
        if (!actuales.contains(col)) {
            WebUI.comment("❌ No se encontró la columna '${col}' en la tabla actual")
            WebUI.verifyEqual(actuales.contains(col), true)
        }
    }

    WebUI.comment("✔ Columnas validadas correctamente: ${esperadas}")
}



// ======================================================================
// ⭐ FUNCIÓN CORREGIDA: Toggle Vista por líneas (click al SLIDER)
// ======================================================================
void toggleVistaLineas() {

    TestObject sliderVista = new TestObject("sliderVista")
    sliderVista.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//input[@id='toggleListLineView']/following-sibling::span[contains(@class,'slider')]"
    )

    WebUI.waitForElementClickable(sliderVista, 10)
    WebUI.click(sliderVista)
    WebUI.delay(1)

    WebUI.comment("✔ Se cambió el estado del switch de Vista por líneas")
}



// ======================================================================
// TEST CASE: Validación del switch Vista por líneas
// ======================================================================

WebUI.comment("==============================================")
WebUI.comment(" INICIO TEST: Validación de Vista por líneas ")
WebUI.comment("==============================================")

List<String> columnasRequerimientos = [
    "Requirente",
    "Código",
    "Fecha límite de entrega",
    "Estado Aprobación",
    "Estado compra",
    "Fecha"
]

validarColumnas(columnasRequerimientos)

toggleVistaLineas()

List<String> columnasLineas = [
    "Requerimiento",
    "Código",
    "Producto",
    "UM",
    "Cantidad",
    "Pendiente de compra",
    "Estado Licitación",
    "Estado compra"
]

validarColumnas(columnasLineas)

toggleVistaLineas()
validarColumnas(columnasRequerimientos)

WebUI.comment("==============================================")
WebUI.comment(" ✔ TEST FINALIZADO: Vista por líneas validada correctamente ")
WebUI.comment("==============================================")
