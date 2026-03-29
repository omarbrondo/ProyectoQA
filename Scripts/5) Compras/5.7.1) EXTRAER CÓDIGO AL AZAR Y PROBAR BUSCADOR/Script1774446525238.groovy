import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import org.openqa.selenium.Keys
// IMPORTANTE: Esta importación permite leer y escribir en el Profile
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement
import java.util.Arrays
import java.util.Date
import java.util.Random
import java.util.Collections




// ===============================
// 4) EXTRAER CÓDIGO AL AZAR Y PROBAR BUSCADOR
// ===============================

TestObject tablaCompras = new TestObject('tablaCompras')
tablaCompras.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='tendering-pager']/tbody")
WebUI.waitForElementVisible(tablaCompras, 10)

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> filasCompras = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

String codigoBuscado = ""

if (filasCompras.size() > 0) {
	Random rand = new Random()
	int indiceAlAzar = rand.nextInt(filasCompras.size())
	WebElement filaSeleccionada = filasCompras.get(indiceAlAzar)
	WebElement celdaCodigo = filaSeleccionada.findElement(By.xpath("./td[1]//span"))
	codigoBuscado = celdaCodigo.getText().trim()
	WebUI.comment("✔ Se seleccionó al azar el Código de Compra: " + codigoBuscado)
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("La tabla de compras está vacía.")
}

// 4.3) Interactuar con el buscador
TestObject inputBuscador = new TestObject('inputBuscador')
inputBuscador.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'keyword-filter')]")
TestObject btnBuscar = new TestObject('btnBuscar')
btnBuscar.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-keyword-filter')]")

WebUI.setText(inputBuscador, codigoBuscado)
WebUI.click(btnBuscar)
WebUI.delay(3)

// 4.4) Validamos el resultado
List<WebElement> filasDespuesFiltro = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))
if (filasDespuesFiltro.size() == 0) {
	throw new com.kms.katalon.core.exception.StepFailedException("La búsqueda falló para el código " + codigoBuscado)
}


// ===============================
// 6) VALIDAR RESULTADO Y GUARDAR EN PROFILE
// ===============================

WebDriver driverFiltro = DriverFactory.getWebDriver()
List<WebElement> filasFiltroAvanzado = driverFiltro.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

if (filasFiltroAvanzado.size() > 0) {
	WebElement celdaResultadoFiltro = filasFiltroAvanzado.get(0).findElement(By.xpath("./td[1]//span"))
	String codigoResultadoFiltro = celdaResultadoFiltro.getText().trim()

	if (codigoResultadoFiltro.equals(codigoBuscado)) {
		// --- AQUÍ GUARDAMOS EL VALOR EN EL PROFILE ---
		GlobalVariable.CodigoLicitacion = codigoResultadoFiltro
		
		WebUI.comment("✔ ÉXITO: El código " + codigoResultadoFiltro + " se guardó en GlobalVariable.CodigoLicitacion")
	} else {
		throw new com.kms.katalon.core.exception.StepFailedException("El filtro falló: se esperaba " + codigoBuscado)
	}
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("La tabla quedó vacía.")
}