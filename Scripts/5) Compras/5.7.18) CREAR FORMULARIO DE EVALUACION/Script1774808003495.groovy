import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import java.util.Arrays

// ===============================
// 41) COMPLETAR DATOS DEL NUEVO FORMULARIO DE EVALUACIÓN
// ===============================

// --- Completar Título ---
TestObject inputTitleEval = new TestObject('inputTitleEval')
inputTitleEval.addProperty("id", ConditionType.EQUALS, "Title")

WebUI.waitForElementVisible(inputTitleEval, 10)
WebUI.setText(inputTitleEval, "Evaluacion QA")
WebUI.comment("✔ Título seteado a 'Evaluacion QA'.")


// --- Seleccionar Etapa al azar ---
TestObject selectEtapaEval = new TestObject('selectEtapaEval')
selectEtapaEval.addProperty("id", ConditionType.EQUALS, "TenderingSupplierStageId")

WebDriver driverEval = DriverFactory.getWebDriver()
List<WebElement> opcionesEtapaEval = driverEval.findElements(By.xpath("//select[@id='TenderingSupplierStageId']/option"))

// Verificamos que haya opciones más allá del "Seleccione una opción"
if (opcionesEtapaEval.size() > 1) {
    int indexAzarEtapaEval = new Random().nextInt(opcionesEtapaEval.size() - 1) + 1
    WebUI.selectOptionByIndex(selectEtapaEval, indexAzarEtapaEval)
    WebUI.comment("✔ Etapa seleccionada al azar (Índice: " + indexAzarEtapaEval + ")")
} else {
    WebUI.comment("⚠ No hay etapas disponibles para seleccionar en el desplegable.")
}


// --- Tildar el checkbox 'UserCanEditUntilClosed' ---
TestObject chkUserCanEdit = new TestObject('chkUserCanEdit')
chkUserCanEdit.addProperty("id", ConditionType.EQUALS, "UserCanEditUntilClosed")

WebUI.check(chkUserCanEdit)
WebUI.comment("✔ Checkbox 'Evaluador puede editar...' marcado.")


// --- Guardar ---
TestObject btnGuardarEval = new TestObject('btnGuardarEval')
btnGuardarEval.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-submit-form')]")

WebUI.waitForElementClickable(btnGuardarEval, 5)
WebUI.click(btnGuardarEval)
WebUI.comment("✔ Guardando nuevo formulario de evaluación...")


// ===============================
// 42) VALIDAR Y CERRAR GROWL DE ÉXITO ("Guardado")
// ===============================

TestObject growlEvalGuardado = new TestObject('growlEvalGuardado')
growlEvalGuardado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEval = new TestObject('btnCerrarGrowlEval')
btnCerrarGrowlEval.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEvalGuardado, 10)
String textoGrowlEval = WebUI.getText(growlEvalGuardado).trim()

if (textoGrowlEval.contains("Guardado")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Guardado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró el texto 'Guardado'. Se leyó: " + textoGrowlEval)
}

// Cerramos la alerta con JS para asegurar
WebElement elCerrarGrowlEval = WebUI.findWebElement(btnCerrarGrowlEval)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlEval))

// Damos tiempo a que se cierre el modal y se recargue la tabla de fondo
WebUI.waitForPageLoad(10)
WebUI.delay(2)