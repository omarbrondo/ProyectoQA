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
// 12) NAVEGAR A ETAPAS Y CREAR "ETAPA QA"
// ===============================

// --- 12.1) Clic en el menú lateral "Etapas" ---
TestObject menuEtapas = new TestObject('menuEtapas')
menuEtapas.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Etapas')]")

WebUI.waitForElementClickable(menuEtapas, 10)
WebUI.click(menuEtapas)
WebUI.comment("✔ Navegando a la sección 'Etapas'...")

// Esperamos que cargue la nueva vista
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// --- 12.2) Clic en el botón "Nueva etapa" ---
TestObject btnNuevaEtapa = new TestObject('btnNuevaEtapa')
btnNuevaEtapa.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-stage') and contains(normalize-space(.), 'Nueva etapa')]")

WebUI.waitForElementClickable(btnNuevaEtapa, 10)
WebUI.click(btnNuevaEtapa)
WebUI.comment("✔ Abriendo el modal de 'Nueva etapa'...")


// --- 12.3) Completar el nombre de la etapa en el modal ---
TestObject inputNombreEtapa = new TestObject('inputNombreEtapa')
// Como el campo tiene el id="Name", es súper directo apuntarle
inputNombreEtapa.addProperty("id", ConditionType.EQUALS, "Name")

WebUI.waitForElementVisible(inputNombreEtapa, 10)
WebUI.setText(inputNombreEtapa, "Etapa QA")
WebUI.comment("✔ Se ingresó el nombre 'Etapa QA'")


// --- 12.4) Guardar la nueva etapa ---
TestObject btnGuardarEtapa = new TestObject('btnGuardarEtapa')
btnGuardarEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-primary') and contains(normalize-space(.), 'Guardar')]")

WebUI.waitForElementClickable(btnGuardarEtapa, 5)
WebUI.click(btnGuardarEtapa)
WebUI.comment("✔ Se hizo clic en 'Guardar' etapa. Esperando que se procese...")

// Damos un tiempito para que el modal se cierre y la grilla de etapas se actualice
WebUI.delay(3)
