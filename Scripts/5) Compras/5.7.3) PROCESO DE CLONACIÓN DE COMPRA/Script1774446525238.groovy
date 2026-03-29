import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import org.openqa.selenium.Keys
// IMPORTANTE: Esta importación permite leer y escribir en el Profile
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement
import java.util.Arrays
import java.util.Date
import java.util.Random
import java.util.Collections




// ===============================
// 8) PROCESO DE CLONACIÓN DE COMPRA
// ===============================

// --- DEFINICIÓN DE OBJETOS ---

// Botón de los tres puntos
TestObject btnMenuEllipsis = new TestObject('btnMenuEllipsis')
btnMenuEllipsis.addProperty("id", ConditionType.EQUALS, "menu-ellipsis")

// Opción "Clonar" del menú desplegable
TestObject opcionClonarMenu = new TestObject('opcionClonarMenu')
opcionClonarMenu.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'show')]//a[contains(normalize-space(.), 'Clonar')]")

// Botón "Clonar" azul dentro del MODAL
TestObject btnConfirmarClonacion = new TestObject('btnConfirmarClonacion')
btnConfirmarClonacion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(normalize-space(.), 'Clonar')]")

// ---------------------------

// 8.1) Clic en el menú ellipsis
WebUI.waitForElementClickable(btnMenuEllipsis, 10)
WebUI.click(btnMenuEllipsis)

// 8.2) Clic en la opción "Clonar" (Usando JS para evitar el error de interactividad)
WebUI.waitForElementPresent(opcionClonarMenu, 5)
WebElement elClonar = WebUI.findWebElement(opcionClonarMenu)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elClonar))

WebUI.comment("✔ Se abrió el modal de clonación.")

// 8.3) Esperar a que el MODAL sea visible y hacer clic en el botón "Clonar" final
WebUI.waitForElementVisible(btnConfirmarClonacion, 10)
WebUI.click(btnConfirmarClonacion)

WebUI.comment("✔ Se confirmó la clonación de la compra: " + GlobalVariable.CodigoLicitacion)

// 8.4) Esperamos a que el sistema procese la copia
WebUI.waitForPageLoad(10)
WebUI.delay(3)

// ===============================
// 8.5) VALIDAR Y CERRAR ALERTA DE ÉXITO
// ===============================

// --- DEFINICIÓN DE OBJETOS PARA LA ALERTA ---
TestObject alertaExito = new TestObject('alertaExito')
alertaExito.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-success')]")

TestObject btnCerrarAlerta = new TestObject('btnCerrarAlerta')
btnCerrarAlerta.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-success')]//button[@data-bs-dismiss='alert']")
// --------------------------------------------

// 1. Esperamos a que la alerta aparezca
WebUI.waitForElementVisible(alertaExito, 10)

// 2. Validamos que el texto sea el correcto
String textoAlerta = WebUI.getText(alertaExito).trim()
if (textoAlerta.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Se visualizó el mensaje 'Cambios guardados'.")
} else {
	WebUI.comment("⚠ El mensaje de la alerta es distinto: " + textoAlerta)
}

// 3. Hacemos clic en la 'X' para cerrar la alerta y limpiar la pantalla
WebUI.waitForElementClickable(btnCerrarAlerta, 5)
WebUI.click(btnCerrarAlerta)

WebUI.comment("✔ Se cerró la alerta de éxito.")

// ===============================
// 8.6) CAPTURAR NUEVO CÓDIGO CLONADO Y ACTUALIZAR PROFILE
// ===============================

// --- DEFINICIÓN DEL OBJETO PARA EL NUEVO CÓDIGO ---
TestObject spanNuevoCodigo = new TestObject('spanNuevoCodigo')
// Apuntamos al span con la clase específica que me pasaste
spanNuevoCodigo.addProperty("xpath", ConditionType.EQUALS, "//span[contains(@class, 'span-col-value') and @data-value]")

// 1. Esperamos que el elemento sea visible (indicativo de que la clonación terminó)
WebUI.waitForElementVisible(spanNuevoCodigo, 15)

// 2. Extraemos el texto del nuevo código
String nuevoCodigoClonado = WebUI.getText(spanNuevoCodigo).trim()

// 3. REEMPLAZAMOS el valor en la Variable Global
if (nuevoCodigoClonado != "" && nuevoCodigoClonado != null) {
	GlobalVariable.CodigoLicitacion = nuevoCodigoClonado
	WebUI.comment("✔ Profile Actualizado: El nuevo Código de Licitación es " + GlobalVariable.CodigoLicitacion)
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se pudo capturar el nuevo código clonado.")
}

WebUI.delay(1)

// ===============================
// 8.7) TRUCO: Clic en "SIN INICIAR" para cerrar menús superpuestos
// ===============================

TestObject badgeSinIniciar = new TestObject('badgeSinIniciar')
badgeSinIniciar.addProperty("xpath", ConditionType.EQUALS, "//strong[normalize-space(text())='SIN INICIAR']")

WebUI.waitForElementVisible(badgeSinIniciar, 10)
WebUI.click(badgeSinIniciar)
WebUI.delay(1) // Pequeña pausa para que el menú del usuario desaparezca
WebUI.comment("✔ Se hizo clic en el estado para despejar la vista y habilitar el ellipsis.")