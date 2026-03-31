import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List

// ===============================
// 75) IR A MAESTRO DE PROVEEDORES Y EXTRAER NOMBRES
// ===============================

WebUI.comment("✔ Navegando directamente a la URL de Proveedores...")
WebUI.navigateToUrl("https://staging.proveedores.intiza.com/es/Supplier/List")
WebUI.waitForPageLoad(10)

WebDriver driverSup = DriverFactory.getWebDriver()

// Buscamos las filas de la tabla de proveedores. La columna 2 (índice 2 en XPath) tiene el Nombre.
List<WebElement> nombresProveedores = driverSup.findElements(By.xpath("//table[@data-pagerid='supplier-pager']/tbody/tr/td[2]//span"))

if (nombresProveedores.size() >= 2) {
    // Guardamos los primeros dos proveedores
    GlobalVariable.Proveedor1 = nombresProveedores.get(0).getText().trim()
    GlobalVariable.Proveedor2 = nombresProveedores.get(1).getText().trim()
    
    WebUI.comment("✔ Proveedor 1 guardado: " + GlobalVariable.Proveedor1)
    WebUI.comment("✔ Proveedor 2 guardado: " + GlobalVariable.Proveedor2)
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No hay suficientes proveedores en la tabla para extraer (se necesitan al menos 2).")
}


// ===============================
// 76) IR A LICITACIONES Y BUSCAR POR CÓDIGO
// ===============================

WebUI.comment("✔ Navegando directamente a la URL de Licitaciones...")
WebUI.navigateToUrl("https://staging.proveedores.intiza.com/es/Tendering/List")
WebUI.waitForPageLoad(10)

TestObject inputBuscador = new TestObject('inputBuscador')
inputBuscador.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'keyword-filter')]")

TestObject btnLupa = new TestObject('btnLupa')
btnLupa.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-keyword-filter')]")

WebUI.waitForElementVisible(inputBuscador, 10)
WebUI.setText(inputBuscador, GlobalVariable.CodigoLicitacion)
WebUI.comment("✔ Se escribió el código de licitación en el buscador: " + GlobalVariable.CodigoLicitacion)

WebUI.click(btnLupa)
WebUI.comment("✔ Se hizo clic en la lupa para buscar.")
WebUI.delay(3) // Pausa obligatoria para darle tiempo a la grilla a filtrar los resultados por AJAX


// ===============================
// 77) ACCEDER AL DETALLE DE LA LICITACIÓN FILTRADA
// ===============================

// Creamos un objeto dinámico que busca el TD con clase 'is-link'
// que contenga el código guardado en nuestro Profile
TestObject registroLicitacion = new TestObject('registroLicitacion')
registroLicitacion.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table[@data-pagerid='tendering-pager']//tbody//td[contains(@class, 'is-link') and .//span[normalize-space()='${GlobalVariable.CodigoLicitacion}']]"
)

// Esperamos que el registro sea visible (el filtro ya debería estar aplicado)
WebUI.waitForElementVisible(registroLicitacion, 10)

// Hacemos clic normal para ingresar al detalle
WebUI.click(registroLicitacion)
WebUI.comment("✔ Se hizo clic en el registro " + GlobalVariable.CodigoLicitacion + " para acceder al detalle.")

// Esperamos que cargue la página de detalles
WebUI.waitForPageLoad(10)
WebUI.delay(2)