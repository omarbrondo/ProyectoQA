import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.util.Arrays

// ===============================
// 52) BUSCAR GRUPO Y ABRIR EDICIÓN
// ===============================

// --- 52.1) Buscar la fila que dice "Evaluador QA" y hacer hover ---
String xpathFilaGrupo = "//table//tr[td[normalize-space(text())='Evaluador QA']]"

TestObject filaGrupo = new TestObject('filaGrupo')
filaGrupo.addProperty("xpath", ConditionType.EQUALS, xpathFilaGrupo)

WebUI.waitForElementVisible(filaGrupo, 10)
WebUI.mouseOver(filaGrupo)
WebUI.delay(1) // Esperamos que el hover active el lápiz oculto


// --- 52.2) Clic en el lápiz ---
TestObject btnEditarGrupo = new TestObject('btnEditarGrupo')
btnEditarGrupo.addProperty("xpath", ConditionType.EQUALS, xpathFilaGrupo + "//a[contains(@class, 'btn-edit-user-group')]")

WebUI.waitForElementPresent(btnEditarGrupo, 5)

// Inyectamos JS para el clic por si las moscas
WebElement elPenGrupo = WebUI.findWebElement(btnEditarGrupo)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenGrupo))
WebUI.comment("✔ Se hizo clic en el lápiz para editar el grupo 'Evaluador QA'.")


// ===============================
// 53) ELIMINAR DESDE EL MODAL DE EDICIÓN
// ===============================

// --- 53.1) Esperar el primer modal ---
TestObject modalEditarGrupo = new TestObject('modalEditarGrupo')
modalEditarGrupo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Editar grupo de evaluadores')]")

WebUI.waitForElementVisible(modalEditarGrupo, 10)
WebUI.comment("✔ Modal 'Editar grupo de evaluadores' visible.")


// --- 53.2) Clic en "Eliminar" (el enlace con el tachito) ---
TestObject btnEliminarLink = new TestObject('btnEliminarLink')
// Apuntamos usando el data-bs-target que abre el segundo modal, es súper seguro
btnEliminarLink.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//a[@data-bs-target='#modal-delete-user-group']")

WebUI.waitForElementClickable(btnEliminarLink, 10)
WebUI.click(btnEliminarLink)
WebUI.comment("✔ Se hizo clic en el enlace 'Eliminar'.")


// ===============================
// 54) CONFIRMAR ELIMINACIÓN (EL SEGUNDO MODAL)
// ===============================

TestObject modalConfirmacion = new TestObject('modalConfirmacion')
modalConfirmacion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Confirmar eliminación')]")

WebUI.waitForElementVisible(modalConfirmacion, 5)
WebUI.delay(1) // Pausa obligatoria para que termine la animación (fade) del modal
WebUI.comment("✔ Se abrió el modal de confirmación de borrado.")

// ¡Nuestra magia anti-rebotes! Buscamos el botón rojo que esté realmente visible
WebDriver driverEliminar = DriverFactory.getWebDriver()
List<WebElement> botonesRojos = driverEliminar.findElements(By.xpath("//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']"))

boolean hizoClic = false
for (WebElement btn : botonesRojos) {
    if (btn.isDisplayed() && btn.isEnabled()) {
        btn.click() // Clic tradicional para evitar el error de token de seguridad
        hizoClic = true
        WebUI.comment("✔ Se hizo clic en el botón rojo 'Eliminar' visible.")
        break
    }
}

if (!hizoClic) {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró ningún botón rojo 'Eliminar' visible en pantalla.")
}


// ===============================
// 55) VALIDAR Y CERRAR GROWL DE ÉXITO ("Eliminado")
// ===============================

TestObject growlGrupoEliminado = new TestObject('growlGrupoEliminado')
growlGrupoEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlGrupoEliminado = new TestObject('btnCerrarGrowlGrupoEliminado')
btnCerrarGrowlGrupoEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlGrupoEliminado, 10)

String textoGrowlGrupoElim = WebUI.getText(growlGrupoEliminado).trim()

if (textoGrowlGrupoElim.contains("Eliminado")) {
    WebUI.comment("✔ Confirmado: Se eliminó el grupo y apareció el mensaje 'Eliminado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje final no fue 'Eliminado'. Se encontró: " + textoGrowlGrupoElim)
}

WebElement elCerrarGrowlGrupo = WebUI.findWebElement(btnCerrarGrowlGrupoEliminado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlGrupo))
WebUI.delay(2)

// Opcional: Verificamos que la fila ya no exista en la tabla
List<WebElement> filaSobreviviente = driverEliminar.findElements(By.xpath(xpathFilaGrupo))

if (filaSobreviviente.size() == 0) {
    WebUI.comment("✔ ¡ÉXITO TOTAL! El grupo 'Evaluador QA' desapareció de la tabla.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El grupo 'Evaluador QA' sigue visible en la tabla.")
}