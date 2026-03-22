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
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory



//0) Acceder a compras
WebUI.callTestCase(findTestCase('5) Compras/5.1) Abrir Solapa Compra'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// 4) EXTRAER CÓDIGO AL AZAR Y PROBAR BUSCADOR
// ===============================

// 4.1) Esperamos que la tabla de Compras cargue
TestObject tablaCompras = new TestObject('tablaCompras')
tablaCompras.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='tendering-pager']/tbody")
WebUI.waitForElementVisible(tablaCompras, 10)

// 4.2) Extraemos una fila al azar y obtenemos su código
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> filasCompras = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

String codigoBuscado = ""

if (filasCompras.size() > 0) {
	Random rand = new Random()
	int indiceAlAzar = rand.nextInt(filasCompras.size())
	WebElement filaSeleccionada = filasCompras.get(indiceAlAzar)
	
	// El código está en el primer 'td' (columna 1), dentro del span
	WebElement celdaCodigo = filaSeleccionada.findElement(By.xpath("./td[1]//span"))
	codigoBuscado = celdaCodigo.getText().trim()
	
	WebUI.comment("✔ Se seleccionó al azar el Código de Compra: " + codigoBuscado)
	
} else {
	// Si la tabla está vacía, no podemos probar el buscador, así que abortamos este paso.
	throw new com.kms.katalon.core.exception.StepFailedException("La tabla de compras está vacía, no se puede probar el buscador.")
}

// 4.3) Interactuar con el buscador
TestObject inputBuscador = new TestObject('inputBuscador')
inputBuscador.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'keyword-filter')]")

TestObject btnBuscar = new TestObject('btnBuscar')
btnBuscar.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-keyword-filter')]")

// Escribimos el código y buscamos
WebUI.waitForElementVisible(inputBuscador, 5)
WebUI.setText(inputBuscador, codigoBuscado)
WebUI.click(btnBuscar)

// Le damos tiempo al sistema para que filtre la tabla
WebUI.delay(3)

// 4.4) Validamos el resultado de la búsqueda
// Volvemos a leer la tabla después del filtro
List<WebElement> filasDespuesFiltro = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

if (filasDespuesFiltro.size() == 0) {
	throw new com.kms.katalon.core.exception.StepFailedException("La búsqueda falló: La tabla quedó vacía tras buscar el código " + codigoBuscado)
}

// Tomamos el código del primer resultado visible
WebElement celdaResultado = filasDespuesFiltro.get(0).findElement(By.xpath("./td[1]//span"))
String codigoResultado = celdaResultado.getText().trim()

if (codigoResultado.equals(codigoBuscado)) {
	WebUI.comment("✔ ÉXITO: El buscador funcionó. Se encontró el código: " + codigoResultado)
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("La búsqueda falló: Se esperaba " + codigoBuscado + " pero se encontró " + codigoResultado)
}



// ===============================
// 5) BÚSQUEDA POR FILTROS AVANZADOS
// ===============================

// 5.1) Resetear la vista yendo a la URL
WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Tendering/List')
WebUI.delay(2) // Esperamos que cargue la página fresca

// --- CREACIÓN DE OBJETOS PARA LOS FILTROS ---
TestObject btnFiltrosAvanzados = new TestObject('btnFiltrosAvanzados')
btnFiltrosAvanzados.addProperty("xpath", ConditionType.EQUALS, "//a[@href='#filters-collapse' and contains(@class, 'btn-link')]")

TestObject btnAgregarFiltro = new TestObject('btnAgregarFiltro')
btnAgregarFiltro.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'add-filter')]")

TestObject dropChosenCampo = new TestObject('dropChosenCampo')
dropChosenCampo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'chosen-container')]//a[contains(@class, 'chosen-single')]")

TestObject inputChosenCampo = new TestObject('inputChosenCampo')
inputChosenCampo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'chosen-container')]//input[contains(@class, 'chosen-search-input')]")

TestObject selectOperador = new TestObject('selectOperador')
selectOperador.addProperty("xpath", ConditionType.EQUALS, "//select[contains(@class, 'drp-operator')]")

TestObject inputValorFiltro = new TestObject('inputValorFiltro')
inputValorFiltro.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'filter-value')]")

TestObject btnAplicarFiltros = new TestObject('btnAplicarFiltros')
btnAplicarFiltros.addProperty("xpath", ConditionType.EQUALS, "//button[@type='submit' and contains(normalize-space(.), 'APLICAR FILTROS')]")
// ---------------------------------------------


// 5.2) Abrir panel de filtros avanzados
WebUI.waitForElementClickable(btnFiltrosAvanzados, 10)
WebUI.click(btnFiltrosAvanzados)
WebUI.delay(1)

// 5.3) Agregar un nuevo filtro
WebUI.waitForElementClickable(btnAgregarFiltro, 5)
WebUI.click(btnAgregarFiltro)

// 5.4) Seleccionar el Campo "Código" en el plugin Chosen
WebUI.waitForElementClickable(dropChosenCampo, 5)
WebUI.click(dropChosenCampo)
WebUI.waitForElementVisible(inputChosenCampo, 5)
// Escribimos "Código" y simulamos presionar la tecla ENTER
WebUI.setText(inputChosenCampo, "Código")
WebUI.sendKeys(inputChosenCampo, Keys.chord(Keys.ENTER))

// 5.5) Seleccionar el Operador "Igual a" (usamos el Value 'Equal' que estaba en el HTML)
WebUI.selectOptionByValue(selectOperador, "Equal", false)

// 5.6) Escribir el valor a buscar (usamos la variable codigoBuscado que guardaste en el paso anterior)
WebUI.setText(inputValorFiltro, codigoBuscado)

// 5.7) Aplicar los filtros
WebUI.click(btnAplicarFiltros)
WebUI.delay(3) // Esperamos a que la grilla procese el filtro y se recargue


// ===============================
// 6) VALIDAR RESULTADO DEL FILTRO AVANZADO
// ===============================

WebDriver driverFiltro = DriverFactory.getWebDriver()
List<WebElement> filasFiltroAvanzado = driverFiltro.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

if (filasFiltroAvanzado.size() == 0) {
	throw new com.kms.katalon.core.exception.StepFailedException("El filtro avanzado falló: La tabla quedó vacía tras buscar el código " + codigoBuscado)
}

// Extraemos el código de la primera fila resultante (Columna 1)
WebElement celdaResultadoFiltro = filasFiltroAvanzado.get(0).findElement(By.xpath("./td[1]//span"))
String codigoResultadoFiltro = celdaResultadoFiltro.getText().trim()

if (codigoResultadoFiltro.equals(codigoBuscado)) {
	WebUI.comment("✔ ÉXITO TOTAL: El filtro avanzado funcionó a la perfección. Se filtró la tabla por el código: " + codigoResultadoFiltro)
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("El filtro avanzado falló: Se esperaba " + codigoBuscado + " pero la tabla muestra " + codigoResultadoFiltro)
}