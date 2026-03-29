import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import java.util.Arrays
import com.kms.katalon.core.model.FailureHandling

// ===============================
// 43) CREAR "CAMPO EVALUACIÓN QA" (EN EL CONTENEDOR CORRECTO)
// ===============================

// --- 43.1) Buscar el contenedor específico de "EVALUACION QA" ---
String xpathContenedorEvalQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'EVALUACION QA')]]"

TestObject btnNuevoCampoEval = new TestObject('btnNuevoCampoEval')
btnNuevoCampoEval.addProperty("xpath", ConditionType.EQUALS, xpathContenedorEvalQA + "//a[contains(@class, 'btn-new-row') and contains(normalize-space(.), 'Nuevo campo')]")

TestObject iconoDesplegarEval = new TestObject('iconoDesplegarEval')
iconoDesplegarEval.addProperty("xpath", ConditionType.EQUALS, xpathContenedorEvalQA + "//i[contains(@class, 'fa-chevron-down')]")


// --- 43.2) Chequear visibilidad y desplegar el acordeón si está cerrado ---
boolean isBotonVisible = WebUI.verifyElementVisible(btnNuevoCampoEval, FailureHandling.OPTIONAL)

if (!isBotonVisible) {
    WebUI.comment("ℹ El botón 'Nuevo campo' de EVALUACION QA está oculto. Desplegando el acordeón...")
    WebUI.waitForElementClickable(iconoDesplegarEval, 5)
    WebUI.click(iconoDesplegarEval)
    WebUI.delay(2) // Pausa para darle tiempo a la animación de despliegue
} else {
    WebUI.comment("✔ El botón 'Nuevo campo' de EVALUACION QA ya está visible.")
}


// --- 43.3) Clic en "Nuevo campo" ---
WebUI.waitForElementClickable(btnNuevoCampoEval, 10)

// Inyectamos JS para asegurar el clic por si hay scroll o elementos superpuestos
WebElement elNuevoCampoEval = WebUI.findWebElement(btnNuevoCampoEval)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoCampoEval))
WebUI.comment("✔ Se hizo clic en 'Nuevo campo' dentro del formulario correcto (EVALUACION QA).")


// --- 43.4) Esperar a que abra el modal ---
TestObject modalCrearCampoEval = new TestObject('modalCrearCampoEval')
modalCrearCampoEval.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Crear campo')]")

WebUI.waitForElementVisible(modalCrearCampoEval, 15)
WebUI.comment("✔ Modal 'Crear campo' visible.")


// --- 43.5) Completar Título y Descripción ---
TestObject inputTituloCampoEval = new TestObject('inputTituloCampoEval')
inputTituloCampoEval.addProperty("id", ConditionType.EQUALS, "RowTitle")

TestObject inputDescCampoEval = new TestObject('inputDescCampoEval')
inputDescCampoEval.addProperty("id", ConditionType.EQUALS, "Description")

WebUI.setText(inputTituloCampoEval, "Campo Evaluacion QA")
WebUI.setText(inputDescCampoEval, "Campo Evaluacion QA")
WebUI.comment("✔ Título y Descripción completados.")


// --- 43.6) Marcar checkbox "Obligatorio" ---
TestObject chkObligatorioEval = new TestObject('chkObligatorioEval')
chkObligatorioEval.addProperty("id", ConditionType.EQUALS, "Mandatory")

WebUI.check(chkObligatorioEval)
WebUI.comment("✔ Se marcó el campo como 'Obligatorio'.")


// --- 43.7) Guardar el campo (VERSIÓN BLINDADA) ---
TestObject btnGuardarCampoEval = new TestObject('btnGuardarCampoEval')
// Buscamos específicamente el botón que dice "Guardar" adentro del modal visible
btnGuardarCampoEval.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'modal-content')]//button[@id='btnRowEditSubmit' and contains(normalize-space(.), 'Guardar')])[last()]")

WebUI.waitForElementClickable(btnGuardarCampoEval, 5)

// Usamos JS Click para evitar cualquier tipo de intercepción
WebElement elGuardarEval = WebUI.findWebElement(btnGuardarCampoEval)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elGuardarEval))
WebUI.comment("✔ Guardando Campo Evaluacion QA...")


// ===============================
// 44) VALIDAR Y CERRAR GROWL DE ÉXITO
// ===============================

TestObject growlCampoEvalGuardado = new TestObject('growlCampoEvalGuardado')
// Buscamos el último growl que haya aparecido en pantalla
growlCampoEvalGuardado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlCampoEval = new TestObject('btnCerrarGrowlCampoEval')
btnCerrarGrowlCampoEval.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlCampoEvalGuardado, 10)
String textoGrowlCampoEval = WebUI.getText(growlCampoEvalGuardado).trim()

if (textoGrowlCampoEval.contains("Cambios guardados")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Cambios guardados'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró el texto esperado. Se leyó: " + textoGrowlCampoEval)
}

// Cerramos la alerta
WebElement elCerrarGrowlEval = WebUI.findWebElement(btnCerrarGrowlCampoEval)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlEval))

WebUI.waitForPageLoad(10)
WebUI.delay(2)