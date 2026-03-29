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
// 56) NAVEGAR A "ESTADOS DE PROVEEDORES"
// ===============================

TestObject menuEstados = new TestObject('menuEstados')
// Buscamos el enlace dentro del menú lateral
menuEstados.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Estados de proveedores')]")

WebUI.waitForElementClickable(menuEstados, 10)
WebUI.click(menuEstados)
WebUI.comment("✔ Navegando a la sección 'Estados de proveedores'...")

// Esperamos que cargue la nueva pantalla
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 57) VERIFICAR EXISTENCIA DE ESTADO "ACTIVO"
// ===============================

WebDriver driverEstados = DriverFactory.getWebDriver()
// Buscamos en la tabla cualquier etiqueta <strong> que tenga la clase text-success y diga "Activo"
List<WebElement> estadosActivos = driverEstados.findElements(By.xpath("//table//td/strong[contains(@class, 'text-success') and normalize-space(text())='Activo']"))

if (estadosActivos.size() > 0) {
    WebUI.comment("✔ Se verificó correctamente: Existe al menos " + estadosActivos.size() + " estado(s) 'Activo' en la tabla.")
} else {
    WebUI.comment("⚠ Nota: No se encontraron estados 'Activos' en la tabla actual.")
}


// ===============================
// 58) CREAR NUEVO ESTADO "ESTADO QA"
// ===============================

// --- 58.1) Clic en "Nuevo" ---
TestObject btnNuevoEstado = new TestObject('btnNuevoEstado')
btnNuevoEstado.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-status') and contains(normalize-space(.), 'Nuevo')]")

WebUI.waitForElementClickable(btnNuevoEstado, 5)
// Usamos JS por si el botón quedó fuera de foco
WebElement elNuevoEstado = WebUI.findWebElement(btnNuevoEstado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoEstado))
WebUI.comment("✔ Se hizo clic en 'Nuevo' estado.")


// --- 58.2) Esperar modal y llenar datos ---
TestObject modalNuevoEstado = new TestObject('modalNuevoEstado')
modalNuevoEstado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Nuevo estado')]")

WebUI.waitForElementVisible(modalNuevoEstado, 10)
WebUI.comment("✔ Modal 'Nuevo estado' visible.")

TestObject inputNombreEstado = new TestObject('inputNombreEstado')
inputNombreEstado.addProperty("id", ConditionType.EQUALS, "Name")
WebUI.setText(inputNombreEstado, "Estado QA")
WebUI.comment("✔ Nombre seteado a 'Estado QA'.")


// --- 58.3) Marcar Checkboxes ---
TestObject chkActivo = new TestObject('chkActivo')
chkActivo.addProperty("id", ConditionType.EQUALS, "Active")
WebUI.check(chkActivo)

TestObject chkDefault = new TestObject('chkDefault')
chkDefault.addProperty("id", ConditionType.EQUALS, "Default")
WebUI.check(chkDefault)
WebUI.comment("✔ Checkboxes 'Activo' y 'Estado por defecto' marcados.")


// --- 58.4) Guardar ---
TestObject btnGuardarEstado = new TestObject('btnGuardarEstado')
// Usamos last() por si hay modales viejos ocultos en el DOM
btnGuardarEstado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(normalize-space(.), 'Guardar')])[last()]")

WebUI.waitForElementClickable(btnGuardarEstado, 5)
WebUI.click(btnGuardarEstado)
WebUI.comment("✔ Guardando nuevo estado...")


// ===============================
// 59) VALIDAR Y CERRAR GROWL DE ÉXITO
// ===============================

TestObject growlEstadoGuardado = new TestObject('growlEstadoGuardado')
growlEstadoGuardado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlEstado = new TestObject('btnCerrarGrowlEstado')
btnCerrarGrowlEstado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlEstadoGuardado, 10)
String txtGrowlEstado = WebUI.getText(growlEstadoGuardado).trim()

if (txtGrowlEstado.contains("Guardado")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Guardado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró el texto esperado en el growl. Se leyó: " + txtGrowlEstado)
}

WebElement elCerrarGrowlEst = WebUI.findWebElement(btnCerrarGrowlEstado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlEst))

WebUI.waitForPageLoad(10)
WebUI.delay(2)