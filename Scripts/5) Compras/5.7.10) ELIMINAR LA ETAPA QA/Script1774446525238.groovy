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
// 15) ELIMINAR LA "ETAPA QA"
// ===============================

// --- 15.1) Clic en el botón "Eliminar" (ícono de tacho de basura) dentro del primer modal ---
TestObject btnEliminarEtapaModal = new TestObject('btnEliminarEtapaModal')
// Buscamos el enlace con clase 'btn-link' y 'text-primary' que abre el modal de borrado
btnEliminarEtapaModal.addProperty("xpath", ConditionType.EQUALS, "//a[@data-bs-target='#modal-delete-stage' and contains(@class, 'text-primary')]")

WebUI.waitForElementClickable(btnEliminarEtapaModal, 10)
WebUI.click(btnEliminarEtapaModal)
WebUI.comment("✔ Se hizo clic en el enlace 'Eliminar' del modal de edición.")


// --- 15.2) Validar el texto del segundo modal (Confirmación) ---
TestObject textoConfirmacionEliminar = new TestObject('textoConfirmacionEliminar')
// Buscamos el párrafo dentro del modal-body del segundo modal
textoConfirmacionEliminar.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//div[@class='modal-body']//p[contains(text(), '¿Está seguro que quiere eliminar')]")

WebUI.waitForElementVisible(textoConfirmacionEliminar, 5)
String mensajeBorrado = WebUI.getText(textoConfirmacionEliminar).trim()

if (mensajeBorrado.equals("¿Está seguro que quiere eliminar Etapa QA?")) {
	WebUI.comment("✔ Confirmado: El modal pregunta exactamente por la 'Etapa QA'.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El texto de confirmación no es el esperado. Se encontró: " + mensajeBorrado)
}


// --- 15.3) Confirmar la eliminación (Botón rojo) ---
TestObject btnConfirmarBorradoEtapa = new TestObject('btnConfirmarBorradoEtapa')
btnConfirmarBorradoEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']")

WebUI.waitForElementClickable(btnConfirmarBorradoEtapa, 5)
WebUI.click(btnConfirmarBorradoEtapa)
WebUI.comment("✔ Se hizo clic en el botón rojo 'Eliminar'.")
