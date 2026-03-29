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
// 39) NAVEGAR A FORMULARIOS DE EVALUACIÓN
// ===============================

TestObject menuEvalForms = new TestObject('menuEvalForms')
// Buscamos el enlace dentro del menú lateral (nav-sidebar)
menuEvalForms.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Formularios de evaluación')]")

WebUI.waitForElementClickable(menuEvalForms, 10)
WebUI.click(menuEvalForms)
WebUI.comment("✔ Navegando a la sección de 'Formularios de evaluación'...")

// Esperamos que cargue la nueva pantalla
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 40) CLIC EN "NUEVO FORMULARIO" Y ABRIR MODAL
// ===============================

TestObject btnNuevoEvalForm = new TestObject('btnNuevoEvalForm')
// Buscamos el enlace con la clase btn-edit-form y el texto exacto
btnNuevoEvalForm.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-form') and contains(normalize-space(.), 'Nuevo formulario')]")

WebUI.waitForElementClickable(btnNuevoEvalForm, 10)
WebUI.click(btnNuevoEvalForm)
WebUI.comment("✔ Se hizo clic en 'Nuevo formulario'.")

// Esperamos a que el modal se haga visible
TestObject modalNuevoEvalForm = new TestObject('modalNuevoEvalForm')
modalNuevoEvalForm.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Nuevo formulario')]")

WebUI.waitForElementVisible(modalNuevoEvalForm, 10)
WebUI.comment("✔ El modal de 'Nuevo formulario' (Evaluación) se abrió correctamente.")