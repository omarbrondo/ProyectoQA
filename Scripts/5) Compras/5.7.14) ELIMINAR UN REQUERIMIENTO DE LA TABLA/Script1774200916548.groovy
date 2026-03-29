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
// 20) ELIMINAR UN REQUERIMIENTO DE LA TABLA
// ===============================

// --- 20.1) Identificar la primera fila y atrapar su ID ---
WebDriver driverReqList = DriverFactory.getWebDriver()
List<WebElement> filasRequerimientos = driverReqList.findElements(By.xpath("//tr[contains(@class, 'tr-purchaserequestline-select')]"))

if (filasRequerimientos.size() == 0) {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: La tabla de requerimientos está vacía, no hay nada para eliminar.")
}

// Guardamos el data-id del primer registro para validarlo después
String idAEliminar = filasRequerimientos.get(0).getAttribute("data-id")
WebUI.comment("✔ Se eligió para eliminar el requerimiento con ID: " + idAEliminar)

// --- 20.2) MouseOver y clic en el tacho de basura ---
TestObject filaTarget = new TestObject('filaTarget')
filaTarget.addProperty("xpath", ConditionType.EQUALS, "//tr[@data-id='${idAEliminar}']")

// Hacemos hover sobre la fila
WebUI.mouseOver(filaTarget)
WebUI.delay(1)

TestObject btnBasurero = new TestObject('btnBasurero')
btnBasurero.addProperty("xpath", ConditionType.EQUALS, "//tr[@data-id='${idAEliminar}']//a[contains(@class, 'btn-delete-purchaserequestline')]")

// ¡JS al rescate de nuevo!
WebUI.waitForElementPresent(btnBasurero, 5)
WebElement elBasurero = WebUI.findWebElement(btnBasurero)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elBasurero))
WebUI.comment("✔ Se hizo clic en el icono de eliminar (Basurero).")


// --- 20.3) Confirmar en el modal ---
TestObject btnConfirmarEliminarReq = new TestObject('btnConfirmarEliminarReq')
btnConfirmarEliminarReq.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']")

WebUI.waitForElementClickable(btnConfirmarEliminarReq, 10)
WebUI.click(btnConfirmarEliminarReq)
WebUI.comment("✔ Se confirmó la eliminación en el modal.")

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
// 21) VERIFICAR QUE EL REGISTRO DESAPARECIÓ DE LA TABLA
// ===============================

// Esperamos que la grilla se actualice (le damos un margen de tiempo)
WebUI.delay(3)

// Volvemos a buscar en el DOM a ver si la fila con ese ID sigue existiendo
List<WebElement> comprobacionFilas = driverReqList.findElements(By.xpath("//tr[@data-id='${idAEliminar}']"))

if (comprobacionFilas.size() == 0) {
	WebUI.comment("✔ ¡ÉXITO TOTAL! El requerimiento " + idAEliminar + " ya no existe en la tabla.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El requerimiento no se eliminó de la tabla (o la vista no se refrescó).")
}

