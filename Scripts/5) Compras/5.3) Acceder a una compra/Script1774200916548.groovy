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
WebUI.callTestCase(findTestCase('5) Compras/5.2) Buscar una compra'), [:], FailureHandling.STOP_ON_FAILURE)



// ===============================
// 7) ACCEDER AL DETALLE DE LA COMPRA
// ===============================

// 7.1) Esperamos que la tabla sea visible antes de interactuar
TestObject tablaResultados = new TestObject('tablaResultados')
tablaResultados.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='tendering-pager']/tbody")
WebUI.waitForElementVisible(tablaResultados, 10)

// 7.2) Obtenemos el WebDriver y localizamos las filas actuales en el DOM
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> filasActuales = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

// 7.3) Verificamos que existan resultados y accedemos al primero
if (filasActuales.size() > 0) {
    // Tomamos la primera fila
    WebElement primeraFila = filasActuales.get(0)
    
    // Buscamos la celda que contiene el link (clase is-link)
    // En este caso, el primer TD que tiene el Código (SC-00000)
    WebElement linkAcceso = primeraFila.findElement(By.xpath("./td[contains(@class, 'is-link')]"))
    
    String codigoAccedido = linkAcceso.getText().trim()
    WebUI.comment("Intentando acceder al detalle de la compra: " + codigoAccedido)
    
    // Hacemos el clic para entrar
    linkAcceso.click()
    
    // Esperamos que la nueva página cargue
    WebUI.waitForPageLoad(10)
    WebUI.delay(2)
    
    WebUI.comment("✔ Se accedió exitosamente al detalle de la compra.")
} else {
    // Si llegamos aquí y no hay filas, el test debe fallar
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontraron registros en la tabla para acceder.")
}