import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import java.util.Arrays

// ===============================
// 69) IR A DATOS GENERALES Y EXTRAER CÓDIGO
// ===============================

TestObject menuDatosGenerales = new TestObject('menuDatosGenerales')
menuDatosGenerales.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Datos Generales')]")

WebUI.waitForElementClickable(menuDatosGenerales, 10)
WebUI.click(menuDatosGenerales)
WebUI.comment("✔ Navegando a 'Datos Generales'...")
WebUI.waitForPageLoad(10)
WebUI.delay(2)

TestObject headerCodigo = new TestObject('headerCodigo')
// Buscamos el h4 que arranca con el símbolo '#'
headerCodigo.addProperty("xpath", ConditionType.EQUALS, "//h4[contains(text(), '#')]")

WebUI.waitForElementVisible(headerCodigo, 10)
String textoCodigo = WebUI.getText(headerCodigo).trim()

// Limpiamos el código sacando el '#'
String codigoLimpio = textoCodigo.replace("#", "").trim()

// Lo guardamos en nuestra variable global
GlobalVariable.CodigoLicitacion = codigoLimpio
WebUI.comment("✔ Código de licitación extraído y guardado en GlobalVariable: " + GlobalVariable.CodigoLicitacion)


// ===============================
// 70) VOLVER A LA PANTALLA PRINCIPAL DE LA LICITACIÓN
// ===============================

TestObject linkVolverLicitacion = new TestObject('linkVolverLicitacion')
// Como el nombre tiene fecha y hora (cambia siempre), lo buscamos por su clase y porque el href contiene '/Tendering/Details/'
linkVolverLicitacion.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'text-decoration-none') and contains(@href, '/Tendering/Details/')]")

WebUI.waitForElementClickable(linkVolverLicitacion, 10)
WebUI.click(linkVolverLicitacion)
WebUI.comment("✔ Volviendo a la pantalla principal de la licitación...")
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 71) CLIC EN "INICIAR"
// ===============================

TestObject btnIniciarLicitacion = new TestObject('btnIniciarLicitacion')
btnIniciarLicitacion.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-tendering-start') and contains(normalize-space(.), 'Iniciar')]")

WebUI.waitForElementClickable(btnIniciarLicitacion, 10)
WebUI.click(btnIniciarLicitacion)
WebUI.comment("✔ Se hizo clic en el botón 'Iniciar'. Esperando el modal de confirmación...")


// ===============================
// 72) CONFIRMAR INICIO EN EL MODAL
// ===============================

TestObject modalConfirmarInicio = new TestObject('modalConfirmarInicio')
modalConfirmarInicio.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Confirmar')]")

WebUI.waitForElementVisible(modalConfirmarInicio, 10)
WebUI.delay(1) // Pausa vital para que el modal termine de aparecer (fade)
WebUI.comment("✔ Modal de confirmación visible.")

TestObject btnConfirmarModal = new TestObject('btnConfirmarModal')
btnConfirmarModal.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-success') and contains(normalize-space(.), 'Confirmar')])[last()]")

WebUI.waitForElementClickable(btnConfirmarModal, 5)
WebUI.click(btnConfirmarModal)
WebUI.comment("✔ Se hizo clic en 'Confirmar' inicio de licitación.")


// ===============================
// 73) VALIDAR Y CERRAR GROWL DE ÉXITO ("Operación exitosa")
// ===============================

TestObject growlInicioExitoso = new TestObject('growlInicioExitoso')
growlInicioExitoso.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlInicio = new TestObject('btnCerrarGrowlInicio')
btnCerrarGrowlInicio.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlInicioExitoso, 15) // Le damos un poco más de tiempo por si el servidor tarda en procesar el inicio
String textoGrowlInicio = WebUI.getText(growlInicioExitoso).trim()

if (textoGrowlInicio.contains("Operación exitosa")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Operación exitosa'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje esperado no apareció. Se leyó: " + textoGrowlInicio)
}

// Cerramos la alerta con JS
WebElement elCerrarGrowlInicio = WebUI.findWebElement(btnCerrarGrowlInicio)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlInicio))
WebUI.delay(2)


// ===============================
// 74) VERIFICAR ESTADO "EN PROCESO"
// ===============================

// Al iniciar, la página suele recargarse o actualizar el DOM, esperamos a que cargue
WebUI.waitForPageLoad(10)
WebUI.delay(2)

TestObject lblEnProceso = new TestObject('lblEnProceso')
// Buscamos el <strong> que contenga el texto "EN PROCESO"
lblEnProceso.addProperty("xpath", ConditionType.EQUALS, "//span[contains(@class, 'span-col-value')]//strong[contains(normalize-space(text()), 'EN PROCESO')]")

// ¡ACÁ ESTÁ LA CORRECCIÓN! Usamos waitForElementVisible que SÍ acepta el número 10
boolean isEnProceso = WebUI.waitForElementVisible(lblEnProceso, 10)

if (isEnProceso) {
    WebUI.comment("✔ ¡ÉXITO TOTAL! La licitación ahora figura en estado 'EN PROCESO'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontró el texto 'EN PROCESO' en la pantalla principal.")
}