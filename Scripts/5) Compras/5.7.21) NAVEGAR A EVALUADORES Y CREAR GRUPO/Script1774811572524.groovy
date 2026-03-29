import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import java.util.Arrays

// ===============================
// 47) NAVEGAR A "EVALUADORES"
// ===============================

TestObject menuEvaluadores = new TestObject('menuEvaluadores')
// Buscamos el enlace dentro del menú lateral
menuEvaluadores.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Evaluadores')]")

WebUI.waitForElementClickable(menuEvaluadores, 10)
WebUI.click(menuEvaluadores)
WebUI.comment("✔ Navegando a la sección 'Evaluadores'...")

// Esperamos que cargue la nueva pantalla (la tabla vacía)
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 48) CLIC EN "NUEVO GRUPO"
// ===============================

TestObject btnNuevoGrupo = new TestObject('btnNuevoGrupo')
// Apuntamos al botón usando su clase específica
btnNuevoGrupo.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-user-group') and contains(normalize-space(.), 'Nuevo grupo')]")

WebUI.waitForElementClickable(btnNuevoGrupo, 10)

// Como es un <a href="javascript:void(0)">, le inyectamos el clic con JS para evitar bloqueos
WebElement elNuevoGrupo = WebUI.findWebElement(btnNuevoGrupo)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoGrupo))

WebUI.comment("✔ Se hizo clic en 'Nuevo grupo'. Esperando el modal de creación...")
WebUI.delay(2)