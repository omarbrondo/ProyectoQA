import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Arrays
import java.util.List

// ===============================
// 45) ELIMINAR "EVALUACION QA"
// ===============================

// --- 45.1) Buscar la cabecera de "EVALUACION QA" y hacer hover ---
String xpathCabeceraEvalQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'EVALUACION QA')]]//div[contains(@class, 'accordion-row')]"

TestObject filaCabeceraEvalQA = new TestObject('filaCabeceraEvalQA')
filaCabeceraEvalQA.addProperty("xpath", ConditionType.EQUALS, xpathCabeceraEvalQA)

WebUI.waitForElementVisible(filaCabeceraEvalQA, 10)
WebUI.mouseOver(filaCabeceraEvalQA)
WebUI.delay(1) // Esperamos que el hover active la visibilidad del botón oculto


// --- 45.2) Clic en el lápiz principal del formulario ---
TestObject btnEditarFormEval = new TestObject('btnEditarFormEval')
// Buscamos el lápiz (btn-edit-form) dentro de la cabecera de Evaluación QA
btnEditarFormEval.addProperty("xpath", ConditionType.EQUALS, xpathCabeceraEvalQA + "//a[contains(@class, 'btn-edit-form')]")

WebUI.waitForElementPresent(btnEditarFormEval, 5)

// Usamos JS Click por las dudas de que el hover falle o el tooltip moleste
WebElement elPenFormEval = WebUI.findWebElement(btnEditarFormEval)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenFormEval))
WebUI.comment("✔ Se hizo clic en el lápiz de edición de 'EVALUACION QA'.")


// --- 45.3) Esperar el modal de Edición ---
TestObject modalEditarFormEval = new TestObject('modalEditarFormEval')
modalEditarFormEval.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Editar formulario')]")

WebUI.waitForElementVisible(modalEditarFormEval, 10)
WebUI.comment("✔ Modal 'Editar formulario' visible.")


// --- 45.4) Clic en "Eliminar" ---
TestObject btnEliminarFormModalEval = new TestObject('btnEliminarFormModalEval')
// Apuntamos directo al ID "btn-delete-form"
btnEliminarFormModalEval.addProperty("id", ConditionType.EQUALS, "btn-delete-form")

WebUI.waitForElementClickable(btnEliminarFormModalEval, 10)
WebUI.click(btnEliminarFormModalEval)
WebUI.comment("✔ Se hizo clic en 'Eliminar' dentro del modal.")


// --- 45.5) Validar el texto del segundo modal y confirmar la eliminación ---
TestObject modalConfirmacionDeleteEval = new TestObject('modalConfirmacionDeleteEval')
modalConfirmacionDeleteEval.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Confirmar eliminación')]")

WebUI.waitForElementVisible(modalConfirmacionDeleteEval, 5)
WebUI.delay(1) // Pausa para que termine el fade in del modal
WebUI.comment("✔ Se abrió el modal de confirmación de borrado.")

// ¡ACÁ ESTÁ LA MAGIA NUEVA! Buscamos el botón que realmente está visible
WebDriver driverModal = DriverFactory.getWebDriver()
List<WebElement> botonesRojos = driverModal.findElements(By.xpath("//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']"))

boolean hizoClic = false
for (WebElement btn : botonesRojos) {
    if (btn.isDisplayed() && btn.isEnabled()) {
        btn.click() // Clic normal de Selenium al botón correcto
        hizoClic = true
        WebUI.comment("✔ Se encontró y se hizo clic en el botón rojo 'Eliminar' visible.")
        break // Salimos del bucle apenas hacemos clic
    }
}

if (!hizoClic) {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró ningún botón rojo 'Eliminar' visible para hacer clic.")
}

WebUI.delay(1)
// ===============================
// 46) VALIDAR Y CERRAR GROWL DE "ELIMINADO" (FORMULARIO EVAL)
// ===============================

TestObject growlFormEvalEliminado = new TestObject('growlFormEvalEliminado')
growlFormEvalEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlFormEvalEliminado = new TestObject('btnCerrarGrowlFormEvalEliminado')
btnCerrarGrowlFormEvalEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlFormEvalEliminado, 10)

String textoGrowlFormEvalEliminado = WebUI.getText(growlFormEvalEliminado).trim()

if (textoGrowlFormEvalEliminado.contains("Eliminado")) {
    WebUI.comment("✔ Confirmado: Se eliminó el formulario de evaluación y apareció el mensaje 'Eliminado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje final no fue 'Eliminado'. Se encontró: " + textoGrowlFormEvalEliminado)
}

WebElement elCerrarGrowlDelFormEval = WebUI.findWebElement(btnCerrarGrowlFormEvalEliminado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlDelFormEval))
WebUI.delay(2)

// Opcional: Verificamos que el contenedor ya no exista en el DOM
WebDriver driverCheckFormEval = DriverFactory.getWebDriver()
List<WebElement> formEvalSobreviviente = driverCheckFormEval.findElements(By.xpath("//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'EVALUACION QA')]]"))

if (formEvalSobreviviente.size() == 0) {
    WebUI.comment("✔ ¡ÉXITO TOTAL! El formulario 'EVALUACION QA' desapareció completamente de la vista.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El formulario 'EVALUACION QA' sigue visible en el HTML.")
}