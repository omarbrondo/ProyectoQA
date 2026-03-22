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

// ===============================
// 0) LOGIN (Abre el navegador y loguea al usuario)
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ REQUERIMIENTOS
// ===============================
TestObject menuRequerimientos = new TestObject('menuRequerimientos')
menuRequerimientos.addProperty(
    "xpath", 
    ConditionType.EQUALS, 
    "//li[contains(@class, 'dropdown')]//a[contains(@class, 'nav-link') and .//span[normalize-space()='Requerimientos']]"
)

WebUI.waitForElementVisible(menuRequerimientos, 10)
WebUI.mouseOver(menuRequerimientos)
WebUI.delay(1) 


// ===============================
// 2) CLICK EN "MIS APROBACIONES"
// ===============================
TestObject opcionMisAprobaciones = new TestObject('opcionMisAprobaciones')
opcionMisAprobaciones.addProperty(
    "xpath", 
    ConditionType.EQUALS, 
    "//div[contains(@class, 'dropdown-menu') and contains(@class, 'show')]//a[contains(@class, 'dropdown-item') and contains(@href, 'ParticipatedList')]"
)

WebUI.waitForElementVisible(opcionMisAprobaciones, 5)
WebUI.click(opcionMisAprobaciones)

WebUI.comment("✔ Se ingresó correctamente a 'Mis aprobaciones'")



// ===============================
// 3) VERIFICAR EXISTENCIA DE ELEMENTOS EN LA TABLA
// ===============================

// 3.1) Esperamos que la tabla sea visible antes de contar
TestObject tablaMisAprobaciones = new TestObject('tablaMisAprobaciones')
tablaMisAprobaciones.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table[@data-pagerid='purchaserequestparticipated-pager']/tbody"
)
WebUI.waitForElementVisible(tablaMisAprobaciones, 10)

// 3.2) Usamos WebDriver para contar las filas
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> filasAprobadas = driver.findElements(By.xpath("//table[@data-pagerid='purchaserequestparticipated-pager']/tbody/tr"))

int cantidadAprobaciones = filasAprobadas.size()

// 3.3) Validamos que haya al menos 1 elemento
if (cantidadAprobaciones > 0) {
	WebUI.comment("✔ ÉXITO: Se encontraron " + cantidadAprobaciones + " requerimientos aprobados por el usuario.")
} else {
	WebUI.comment("⚠ FALLO: La tabla de 'Mis aprobaciones' está vacía.")
	// Forzamos el fallo del Test Case si la tabla está vacía
	throw new com.kms.katalon.core.exception.StepFailedException("No se encontraron registros en la tabla de Mis Aprobaciones.")
}