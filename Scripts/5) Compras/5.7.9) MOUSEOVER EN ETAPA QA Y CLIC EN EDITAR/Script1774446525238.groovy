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
// 14) MOUSEOVER EN "ETAPA QA" Y CLIC EN EDITAR
// ===============================

// --- 14.1) Identificar la fila exacta que dice "Etapa QA" ---
TestObject filaEtapaQA = new TestObject('filaEtapaQA')
filaEtapaQA.addProperty("xpath", ConditionType.EQUALS, "//tr[contains(@class, 'stage-row') and .//span[normalize-space(text())='Etapa QA']]")

WebUI.waitForElementVisible(filaEtapaQA, 10)

// Hacemos el MouseOver para que se desplieguen los botones ocultos
WebUI.mouseOver(filaEtapaQA)
WebUI.delay(1) // Le damos tiempo a la animación de Bootstrap/CSS para que muestre los botones


// --- 14.2) Clic en el lapicito (Editar) ---
TestObject btnEditarEtapa = new TestObject('btnEditarEtapa')
// Apuntamos directo a la etiqueta <a> que tiene el ícono del lápiz, DENTRO de la fila de Etapa QA
btnEditarEtapa.addProperty("xpath", ConditionType.EQUALS, "//tr[contains(@class, 'stage-row') and .//span[normalize-space(text())='Etapa QA']]//a[contains(@class, 'btn-edit-stage') and .//i[contains(@class, 'fa-pen')]]")

// Para evitar cualquier fallo del Hover, inyectamos el clic con JS
WebUI.waitForElementPresent(btnEditarEtapa, 5)
WebElement elPenEditar = WebUI.findWebElement(btnEditarEtapa)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenEditar))

WebUI.comment("✔ ¡Hackmate! Se hizo clic en el botón de edición de 'Etapa QA' a prueba de fallos.")


// Esperamos que el modal de edición vuelva a aparecer
WebUI.delay(2)