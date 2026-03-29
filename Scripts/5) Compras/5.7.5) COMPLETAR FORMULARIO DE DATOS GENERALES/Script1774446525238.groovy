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
// 10) COMPLETAR FORMULARIO DE DATOS GENERALES
// ===============================

// --- 10.1) Cambiar el nombre de la licitación con Fecha y Hora ---
TestObject inputNombre = new TestObject('inputNombre')
// Buscamos por la clase 'form-control-xl' que es propia de este título principal
inputNombre.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'form-control-xl') and @type='text']")

String fechaActual = new Date().format('dd/MM/yyyy HH:mm:ss')
String nombreLicitacion = "Licitación QA " + fechaActual

WebUI.waitForElementVisible(inputNombre, 10)
WebUI.clearText(inputNombre)
WebUI.setText(inputNombre, nombreLicitacion)
WebUI.comment("✔ Nombre de la compra actualizado a: " + nombreLicitacion)


// --- 10.2) Seleccionar categoría al azar ---
TestObject selectCategoria = new TestObject('selectCategoria')
selectCategoria.addProperty("xpath", ConditionType.EQUALS, "//select[contains(@class, 'form-control-m')]")

// Le cambiamos el nombre a 'driverConfig' para que no choque con el 'driver' de arriba
WebDriver driverConfig = DriverFactory.getWebDriver()
List<WebElement> opcionesCategoria = driverConfig.findElements(By.xpath("//select[contains(@class, 'form-control-m')]/option"))

if (opcionesCategoria.size() > 1) {
	Random rand = new Random()
	// Generamos un índice aleatorio, saltando el 0 que es "Seleccione una opción"
	int indexAzar = rand.nextInt(opcionesCategoria.size() - 1) + 1
	WebUI.selectOptionByIndex(selectCategoria, indexAzar)
	WebUI.comment("✔ Categoría seleccionada al azar (Índice: " + indexAzar + ")")
}


// --- 10.3) Cargar un monto al azar entre 1,000,000 y 5,000,000 ---
TestObject inputMonto = new TestObject('inputMonto')
inputMonto.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'mask-numeric')]")

// Fórmula para rango: rand.nextInt((max - min) + 1) + min
int montoAzar = new Random().nextInt(4000001) + 1000000

WebUI.waitForElementVisible(inputMonto, 5)
WebUI.click(inputMonto) // Hacemos foco
// Vaciamos usando atajos de teclado para que la "máscara" de moneda no se buguee
WebUI.sendKeys(inputMonto, Keys.chord(Keys.CONTROL, "a"))
WebUI.sendKeys(inputMonto, Keys.chord(Keys.BACK_SPACE))
// Enviamos el número plano (el plugin JS se encargará de ponerle los puntos y comas)
WebUI.setText(inputMonto, montoAzar.toString())
WebUI.comment("✔ Monto actualizado a: " + montoAzar)


// --- 10.4) Verificar y agregar evaluador "Admin" si no está ---
// Usamos el 'driverConfig' que definimos recién
List<WebElement> adminPill = driverConfig.findElements(By.xpath("//div[contains(@class, 'chosen-container-multi')]//li[@class='search-choice']//span[normalize-space(text())='Admin']"))

if (adminPill.size() == 0) {
	WebUI.comment("⚠ 'Admin' no está seleccionado. Agregándolo...")
	TestObject inputChosenAdmin = new TestObject('inputChosenAdmin')
	inputChosenAdmin.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'chosen-container-multi')]//li[@class='search-field']/input")
	
	WebUI.waitForElementClickable(inputChosenAdmin, 5)
	WebUI.click(inputChosenAdmin)
	WebUI.setText(inputChosenAdmin, "Admin")
	WebUI.delay(1) // Pausa breve para que el plugin filtre las opciones
	WebUI.sendKeys(inputChosenAdmin, Keys.chord(Keys.ENTER))
	WebUI.comment("✔ Evaluador 'Admin' agregado exitosamente.")
} else {
	WebUI.comment("✔ El evaluador 'Admin' ya estaba presente.")
}

// --- 10.5) Guardar los cambios ---
TestObject btnGuardarDatos = new TestObject('btnGuardarDatos')
btnGuardarDatos.addProperty("xpath", ConditionType.EQUALS, "//button[@type='submit' and contains(@class, 'btn-submit-form') and contains(normalize-space(.), 'GUARDAR')]")

WebUI.waitForElementClickable(btnGuardarDatos, 5)
WebUI.click(btnGuardarDatos)
WebUI.comment("✔ Formulario enviado. Esperando confirmación...")
