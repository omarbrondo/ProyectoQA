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
// 60) BUSCAR "ESTADO QA" Y ABRIR EDICIÓN
// ===============================

// --- 60.1) Buscar la fila en la tabla y hacer hover ---
String xpathFilaEstado = "//table//tr[td[normalize-space(text())='Estado QA']]"

TestObject filaEstadoQA = new TestObject('filaEstadoQA')
filaEstadoQA.addProperty("xpath", ConditionType.EQUALS, xpathFilaEstado)

WebUI.waitForElementVisible(filaEstadoQA, 10)
WebUI.mouseOver(filaEstadoQA)
WebUI.delay(1) // Esperamos que la animación CSS muestre el lápiz


// --- 60.2) Clic en el lápiz oculto ---
TestObject btnEditarEstado = new TestObject('btnEditarEstado')
btnEditarEstado.addProperty("xpath", ConditionType.EQUALS, xpathFilaEstado + "//a[contains(@class, 'btn-edit-status')]")

WebUI.waitForElementPresent(btnEditarEstado, 5)

// JS Click para evitar problemas de hover o tooltips
WebElement elPenEstado = WebUI.findWebElement(btnEditarEstado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenEstado))
WebUI.comment("✔ Se hizo clic en el lápiz para editar 'Estado QA'.")


// ===============================
// 61) ELIMINAR DESDE EL PRIMER MODAL
// ===============================

TestObject modalEditarEstado = new TestObject('modalEditarEstado')
modalEditarEstado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Editar estado')]")

WebUI.waitForElementVisible(modalEditarEstado, 10)
WebUI.comment("✔ Modal 'Editar estado' visible.")

TestObject btnEliminarLinkEstado = new TestObject('btnEliminarLinkEstado')
// Usamos el data-bs-target que es específico para abrir el modal de borrado de estado
btnEliminarLinkEstado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//a[@data-bs-target='#modal-delete-status']")

WebUI.waitForElementClickable(btnEliminarLinkEstado, 5)
WebUI.click(btnEliminarLinkEstado)
WebUI.comment("✔ Se hizo clic en 'Eliminar' (el enlace con tachito).")


// ===============================
// 62) CONFIRMAR ELIMINACIÓN (SEGUNDO MODAL)
// ===============================

TestObject modalConfirmacionEstado = new TestObject('modalConfirmacionEstado')
modalConfirmacionEstado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Confirmar eliminación')]")

WebUI.waitForElementVisible(modalConfirmacionEstado, 5)
WebUI.delay(1) // Pausa vital para que el modal termine de aparecer (fade)
WebUI.comment("✔ Modal de confirmación de borrado visible.")

WebDriver driverDelete = DriverFactory.getWebDriver()
// Buscamos los botones rojos de eliminar (por si hay modales viejos ocultos)
List<WebElement> botonesRojosEstado = driverDelete.findElements(By.xpath("//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']"))

boolean hizoClicDelete = false
for (WebElement btn : botonesRojosEstado) {
    if (btn.isDisplayed() && btn.isEnabled()) {
        btn.click() // Usamos el click nativo de Selenium
        hizoClicDelete = true
        WebUI.comment("✔ Se hizo clic en el botón rojo 'Eliminar' visible.")
        break
    }
}

if (!hizoClicDelete) {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró ningún botón rojo 'Eliminar' visible.")
}


// ===============================
// 63) VALIDAR Y CERRAR GROWL DE "ELIMINADO"
// ===============================

TestObject growlEstadoEliminado = new TestObject('growlEstadoEliminado')
growlEstadoEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlEstadoEliminado = new TestObject('btnCerrarGrowlEstadoEliminado')
btnCerrarGrowlEstadoEliminado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlEstadoEliminado, 10)

String txtGrowlEliminado = WebUI.getText(growlEstadoEliminado).trim()

if (txtGrowlEliminado.contains("Eliminado")) {
    WebUI.comment("✔ Confirmado: Se eliminó el estado y apareció el mensaje 'Eliminado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje final no fue 'Eliminado'. Se encontró: " + txtGrowlEliminado)
}

WebElement elCerrarGrowlEstDel = WebUI.findWebElement(btnCerrarGrowlEstadoEliminado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlEstDel))
WebUI.delay(2)

// Opcional: Verificamos que el "Estado QA" ya no esté en la tabla
List<WebElement> estadoSobreviviente = driverDelete.findElements(By.xpath(xpathFilaEstado))

if (estadoSobreviviente.size() == 0) {
    WebUI.comment("✔ ¡ÉXITO TOTAL! El 'Estado QA' desapareció de la tabla de estados.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El 'Estado QA' sigue visible en la tabla.")
}