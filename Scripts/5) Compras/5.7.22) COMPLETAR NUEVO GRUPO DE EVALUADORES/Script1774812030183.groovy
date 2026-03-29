import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.util.ArrayList
import java.util.Collections
import java.util.Arrays

// ===============================
// 49) LLENAR DATOS DEL GRUPO DE EVALUADORES
// ===============================

// --- 49.1) Esperar a que abra el modal ---
TestObject modalNuevoGrupo = new TestObject('modalNuevoGrupo')
modalNuevoGrupo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Nuevo grupo de evaluadores')]")

WebUI.waitForElementVisible(modalNuevoGrupo, 10)
WebUI.comment("✔ Modal 'Nuevo grupo de evaluadores' visible.")


// --- 49.2) Completar Nombre y Descripción ---
TestObject inputNameGrp = new TestObject('inputNameGrp')
inputNameGrp.addProperty("id", ConditionType.EQUALS, "Name")

TestObject inputDescGrp = new TestObject('inputDescGrp')
inputDescGrp.addProperty("id", ConditionType.EQUALS, "Description")

WebUI.setText(inputNameGrp, "Evaluador QA")
WebUI.setText(inputDescGrp, "Evaluador QA")
WebUI.comment("✔ Nombre y Descripción completados.")


// ===============================
// 50) SELECCIONAR OPCIONES AL AZAR (CHOSEN SELECTS)
// ===============================
WebDriver driverGrp = DriverFactory.getWebDriver()

// --- 50.1) Elegir 3 Roles/Usuarios al azar ---
List<WebElement> optRoles = driverGrp.findElements(By.xpath("//select[@name='RoleAndUserIdList']//option[@value!='']"))
List<String> roleTexts = new ArrayList<>()

for(WebElement opt : optRoles) {
    roleTexts.add(opt.getText().trim())
}
// Mezclamos la lista de nombres para que sea al azar
Collections.shuffle(roleTexts)
// Por si hay menos de 3 opciones en el sistema, agarramos el mínimo
int rolesToPick = Math.min(3, roleTexts.size())

TestObject inputChosenRolesGrp = new TestObject('inputChosenRolesGrp')
inputChosenRolesGrp.addProperty("xpath", ConditionType.EQUALS, "//select[@name='RoleAndUserIdList']/following-sibling::div//input[contains(@class, 'chosen-search-input')]")

for(int i = 0; i < rolesToPick; i++) {
    WebUI.click(inputChosenRolesGrp)
    WebUI.setText(inputChosenRolesGrp, roleTexts.get(i))
    WebUI.delay(1) // Pausa para que el plugin filtre
    WebUI.sendKeys(inputChosenRolesGrp, Keys.chord(Keys.ENTER))
    WebUI.delay(1)
}
WebUI.comment("✔ Se seleccionaron " + rolesToPick + " roles/usuarios al azar.")


// --- 50.2) Elegir 1 Formulario de Oferta al azar ---
List<WebElement> optSupForms = driverGrp.findElements(By.xpath("//select[@name='SupplierFormIdList']//option[@value!='']"))
if(optSupForms.size() > 0) {
    List<String> supFormTexts = new ArrayList<>()
    for(WebElement opt : optSupForms) { supFormTexts.add(opt.getText().trim()) }
    Collections.shuffle(supFormTexts)

    TestObject inputChosenSupForms = new TestObject('inputChosenSupForms')
    inputChosenSupForms.addProperty("xpath", ConditionType.EQUALS, "//select[@name='SupplierFormIdList']/following-sibling::div//input[contains(@class, 'chosen-search-input')]")

    WebUI.click(inputChosenSupForms)
    WebUI.setText(inputChosenSupForms, supFormTexts.get(0))
    WebUI.delay(1)
    WebUI.sendKeys(inputChosenSupForms, Keys.chord(Keys.ENTER))
    WebUI.comment("✔ Se seleccionó 1 Formulario de Oferta al azar.")
}


// --- 50.3) Elegir 1 Formulario de Evaluación al azar ---
List<WebElement> optEvalForms = driverGrp.findElements(By.xpath("//select[@name='EvaluatorFormIdList']//option[@value!='']"))
if(optEvalForms.size() > 0) {
    List<String> evalFormTexts = new ArrayList<>()
    for(WebElement opt : optEvalForms) { evalFormTexts.add(opt.getText().trim()) }
    Collections.shuffle(evalFormTexts)

    TestObject inputChosenEvalForms = new TestObject('inputChosenEvalForms')
    inputChosenEvalForms.addProperty("xpath", ConditionType.EQUALS, "//select[@name='EvaluatorFormIdList']/following-sibling::div//input[contains(@class, 'chosen-search-input')]")

    WebUI.click(inputChosenEvalForms)
    WebUI.setText(inputChosenEvalForms, evalFormTexts.get(0))
    WebUI.delay(1)
    WebUI.sendKeys(inputChosenEvalForms, Keys.chord(Keys.ENTER))
    WebUI.comment("✔ Se seleccionó 1 Formulario de Evaluación al azar.")
}


// ===============================
// 51) GUARDAR Y VALIDAR GROWL
// ===============================

TestObject btnGuardarGrupo = new TestObject('btnGuardarGrupo')
btnGuardarGrupo.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-primary') and contains(normalize-space(.), 'Guardar')])[last()]")

WebUI.waitForElementClickable(btnGuardarGrupo, 5)
WebUI.click(btnGuardarGrupo)
WebUI.comment("✔ Guardando el Grupo de Evaluadores...")


// --- Validar Growl ---
TestObject growlGrupoGuardado = new TestObject('growlGrupoGuardado')
growlGrupoGuardado.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlGrupo = new TestObject('btnCerrarGrowlGrupo')
btnCerrarGrowlGrupo.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlGrupoGuardado, 10)
String textoGrowlGrupo = WebUI.getText(growlGrupoGuardado).trim()

if (textoGrowlGrupo.contains("Guardado")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Guardado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró el texto esperado. Se leyó: " + textoGrowlGrupo)
}

WebElement elCerrarGrowlGrp = WebUI.findWebElement(btnCerrarGrowlGrupo)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlGrp))

WebUI.waitForPageLoad(10)
WebUI.delay(2)