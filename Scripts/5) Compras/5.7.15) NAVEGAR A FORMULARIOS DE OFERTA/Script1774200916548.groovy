import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ===============================
// 22) NAVEGAR A FORMULARIOS DE OFERTA
// ===============================

TestObject menuFormulariosOferta = new TestObject('menuFormulariosOferta')
// Buscamos el enlace dentro del menú lateral (nav-sidebar)
menuFormulariosOferta.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Formularios de oferta')]")

WebUI.waitForElementClickable(menuFormulariosOferta, 10)
WebUI.click(menuFormulariosOferta)
WebUI.comment("✔ Navegando a la sección de 'Formularios de oferta'...")

// Esperamos que cargue la nueva pantalla
WebUI.waitForPageLoad(10)
WebUI.delay(2)