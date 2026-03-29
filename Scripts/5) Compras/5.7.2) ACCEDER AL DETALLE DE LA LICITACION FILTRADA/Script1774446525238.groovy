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
// 7) ACCEDER AL DETALLE DE LA LICITACIÓN FILTRADA
// ===============================

// Creamos un objeto dinámico que busca el TD con clase 'is-link'
// que contenga el código guardado en nuestro Profile
TestObject registroLicitacion = new TestObject('registroLicitacion')
registroLicitacion.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table[@data-pagerid='tendering-pager']//tbody//td[contains(@class, 'is-link') and .//span[normalize-space()='${GlobalVariable.CodigoLicitacion}']]"
)

// 7.1) Esperamos que el registro sea visible (el filtro ya debería estar aplicado)
WebUI.waitForElementVisible(registroLicitacion, 10)

// 7.2) Hacemos clic para ingresar al detalle
WebUI.click(registroLicitacion)

WebUI.comment("✔ Se hizo clic en el registro " + GlobalVariable.CodigoLicitacion + " para acceder al detalle.")

// 7.3) Esperamos que cargue la página de detalles
WebUI.waitForPageLoad(10)
WebUI.delay(2)
