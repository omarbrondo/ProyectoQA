import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.util.Random
import java.util.Arrays

// ===============================
// 64) NAVEGAR A "ACTAS DE ADJUDICACIÓN"
// ===============================

TestObject menuActas = new TestObject('menuActas')
menuActas.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Actas de Adjudicación')]")

WebUI.waitForElementClickable(menuActas, 10)
WebUI.click(menuActas)
WebUI.comment("✔ Navegando a la sección 'Actas de Adjudicación'...")
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 65) CLIC EN "AGREGAR"
// ===============================

TestObject btnAgregarActa = new TestObject('btnAgregarActa')
btnAgregarActa.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-addassociation') and contains(normalize-space(.), 'Agregar')]")

WebUI.waitForElementClickable(btnAgregarActa, 10)
WebUI.click(btnAgregarActa)
WebUI.comment("✔ Se hizo clic en 'Agregar'. Esperando que aparezca la nueva fila...")
WebUI.delay(2)


// ===============================
// 66) COMPLETAR DROPDOWNS (CHOSEN) AL AZAR
// ===============================

WebDriver driverActas = DriverFactory.getWebDriver()
List<WebElement> emptyDropdowns = driverActas.findElements(By.xpath("//div[contains(@class, 'chosen-container-single') and .//span[normalize-space(text())='Seleccionar']]"))

if (emptyDropdowns.size() > 0) {
    for (WebElement dropdown : emptyDropdowns) {
        WebElement linkAbrir = dropdown.findElement(By.xpath("./a[contains(@class, 'chosen-single')]"))
        
        // Abrimos el dropdown usando JS por si la pantalla scrolleó
        WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(linkAbrir))
        WebUI.delay(1)

        List<WebElement> opciones = dropdown.findElements(By.xpath(".//li[contains(@class, 'active-result')]"))

        if (opciones.size() > 0) {
            int randomIndex = new Random().nextInt(opciones.size())
            WebElement opcionSeleccionada = opciones.get(randomIndex)
            
            // ¡MAGIA JS! Inyectamos el clic a la opción para evitar el ElementClickInterceptedException
            WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(opcionSeleccionada))
            WebUI.delay(1)
        }
    }
    WebUI.comment("✔ Se seleccionaron opciones al azar en los dropdowns nuevos mediante JS.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontraron campos 'Seleccionar'.")
}


// ===============================
// 67) GUARDAR Y VALIDAR GROWL
// ===============================

TestObject btnGuardarActa = new TestObject('btnGuardarActa')
btnGuardarActa.addProperty("xpath", ConditionType.EQUALS, "//button[@type='submit' and contains(@class, 'btn-submit') and contains(normalize-space(.), 'GUARDAR')]")

WebUI.waitForElementClickable(btnGuardarActa, 5)
WebUI.click(btnGuardarActa)
WebUI.comment("✔ Guardando configuración del Acta...")

TestObject growlActaExitosa = new TestObject('growlActaExitosa')
growlActaExitosa.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")

TestObject btnCerrarGrowlActa = new TestObject('btnCerrarGrowlActa')
btnCerrarGrowlActa.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")

WebUI.waitForElementVisible(growlActaExitosa, 10)
String textoGrowlActa = WebUI.getText(growlActaExitosa).trim()

if (textoGrowlActa.contains("Operación exitosa") || textoGrowlActa.contains("Guardado")) {
    WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito ('" + textoGrowlActa + "').")
}

WebElement elCerrarGrowlActa = WebUI.findWebElement(btnCerrarGrowlActa)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlActa))
WebUI.delay(2)


// ===============================
// 68) ELIMINAR LA ASOCIACIÓN RECIÉN CREADA
// ===============================

// Buscamos todas las cruces rojas de la tabla
List<WebElement> crucesRojas = driverActas.findElements(By.xpath("//div[@id='association-list']//button[contains(@class, 'btn-remove-tenderingaward-prlinesupplier')]"))
int cantidadAntes = crucesRojas.size()

if (cantidadAntes > 0) {
    // Agarramos la última cruz roja (que corresponde a la última fila que agregamos)
    WebElement ultimaCruzRoja = crucesRojas.get(cantidadAntes - 1)
    
    // Inyectamos el clic para que no falle
    WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(ultimaCruzRoja))
    WebUI.comment("✔ Se hizo clic en la 'X' roja de la última fila.")
    WebUI.delay(2) // Le damos tiempo a que el DOM remueva el elemento

    // Verificamos que ahora haya una fila menos
    List<WebElement> crucesRojasDespues = driverActas.findElements(By.xpath("//div[@id='association-list']//button[contains(@class, 'btn-remove-tenderingaward-prlinesupplier')]"))
    if (crucesRojasDespues.size() < cantidadAntes) {
        WebUI.comment("✔ ¡Éxito! La fila desapareció de la interfaz.")
    } else {
        throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: La fila no se eliminó de la vista.")
    }

    // Volvemos a Guardar para asentar la eliminación
    WebUI.waitForElementClickable(btnGuardarActa, 5)
    WebUI.click(btnGuardarActa)
    WebUI.comment("✔ Guardando cambios tras eliminar la fila...")

    // Validamos el Growl por segunda vez
    WebUI.waitForElementVisible(growlActaExitosa, 10)
    String textoGrowlDelete = WebUI.getText(growlActaExitosa).trim()
    if (textoGrowlDelete.contains("Operación exitosa") || textoGrowlDelete.contains("Guardado")) {
        WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito tras eliminar.")
    }

    WebElement elCerrarGrowlDelete = WebUI.findWebElement(btnCerrarGrowlActa)
    WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlDelete))
    WebUI.delay(2)

} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No se encontraron filas con la cruz roja para eliminar.")
}