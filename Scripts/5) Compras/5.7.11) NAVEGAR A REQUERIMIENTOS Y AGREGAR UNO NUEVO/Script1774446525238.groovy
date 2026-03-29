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
// 17) NAVEGAR A REQUERIMIENTOS Y AGREGAR UNO NUEVO
// ===============================

// --- 17.1) Clic en el menú lateral "Requerimientos" ---
TestObject menuRequerimientos = new TestObject('menuRequerimientos')
// Buscamos el enlace dentro del menú lateral (nav-sidebar)
menuRequerimientos.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Requerimientos')]")

WebUI.waitForElementClickable(menuRequerimientos, 10)
WebUI.click(menuRequerimientos)
WebUI.comment("✔ Navegando a la sección de 'Requerimientos'...")

// Esperamos que cargue la nueva pantalla
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// --- 17.2) Clic en el botón "Agregar requerimiento" ---
TestObject btnAgregarRequerimiento = new TestObject('btnAgregarRequerimiento')
// Apuntamos directo a la clase específica que me pasaste en el HTML
btnAgregarRequerimiento.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-add-purchaserequestline') and contains(normalize-space(.), 'Agregar requerimiento')]")

WebUI.waitForElementClickable(btnAgregarRequerimiento, 10)
WebUI.click(btnAgregarRequerimiento)
WebUI.comment("✔ Se hizo clic en 'Agregar requerimiento'.")

// Le damos un segundito para que reaccione (seguramente se abra un modal o una tabla de selección)
WebUI.delay(2)
