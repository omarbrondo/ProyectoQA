import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.util.ArrayList
import java.util.Arrays

// ===============================
// 1) LEER PROVEEDORES YA INVITADOS A LA LICITACIÓN
// ===============================
WebUI.comment("➤ Revisando proveedores que ya están en la licitación para no repetirlos...")

WebDriver driver = DriverFactory.getWebDriver()
// Buscamos la segunda columna de la tabla principal de proveedores en la licitación (que tiene el nombre)
List<WebElement> provYaInvitadosEles = driver.findElements(By.xpath("//table[contains(@class, 'shadow-hover')]//tbody//tr/td[2][contains(@class, 'is-link')]"))

List<String> excluidos = new ArrayList<>()
for (WebElement el : provYaInvitadosEles) {
    excluidos.add(el.getText().trim())
}
WebUI.comment("✔ Proveedores excluidos (ya invitados): " + excluidos.toString())


// ===============================
// 2) IR A MAESTRO DE PROVEEDORES Y ELEGIR 2 NUEVOS
// ===============================
WebUI.comment("➤ Yendo al Maestro de Proveedores para buscar 2 candidatos nuevos...")
WebUI.navigateToUrl("https://staging.proveedores.intiza.com/es/Supplier/List")
WebUI.waitForPageLoad(10)

List<WebElement> nombresTablaMaestro = driver.findElements(By.xpath("//table[@data-pagerid='supplier-pager']/tbody/tr/td[2]//span"))
List<String> seleccionados = new ArrayList<>()

for (WebElement el : nombresTablaMaestro) {
    String nombreCandidato = el.getText().trim()
    
    // Si el nombre NO está en la lista de excluidos y no está vacío, lo sumamos
    if (!excluidos.contains(nombreCandidato) && !nombreCandidato.isEmpty()) {
        seleccionados.add(nombreCandidato)
    }
    
    // Apenas juntamos 2, cortamos el bucle
    if (seleccionados.size() == 2) {
        break
    }
}

if (seleccionados.size() < 2) {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: No hay suficientes proveedores nuevos en la tabla para extraer.")
}

GlobalVariable.Proveedor1 = seleccionados.get(0)
GlobalVariable.Proveedor2 = seleccionados.get(1)
WebUI.comment("✔ ¡Nuevos candidatos encontrados! Prov1: " + GlobalVariable.Proveedor1 + " | Prov2: " + GlobalVariable.Proveedor2)


// ===============================
// 3) VOLVER A LA LICITACIÓN
// ===============================
WebUI.comment("➤ Volviendo a la lista de Licitaciones...")
WebUI.navigateToUrl("https://staging.proveedores.intiza.com/es/Tendering/List")
WebUI.waitForPageLoad(10)

TestObject inputBuscador = new TestObject('inputBuscador')
inputBuscador.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'keyword-filter')]")

TestObject btnLupa = new TestObject('btnLupa')
btnLupa.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-keyword-filter')]")

WebUI.waitForElementVisible(inputBuscador, 10)
WebUI.setText(inputBuscador, GlobalVariable.CodigoLicitacion)
WebUI.click(btnLupa)
WebUI.delay(3) // Pausa para AJAX

TestObject registroLicitacion = new TestObject('registroLicitacion')
registroLicitacion.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='tendering-pager']//tbody//td[contains(@class, 'is-link') and .//span[normalize-space()='${GlobalVariable.CodigoLicitacion}']]")

WebUI.waitForElementVisible(registroLicitacion, 10)
WebUI.click(registroLicitacion)
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 4) AGREGAR LOS PROVEEDORES (BUCLE)
// ===============================
WebUI.comment("➤ Iniciando carga manual de los proveedores...")

List<String> listaAgregar = [GlobalVariable.Proveedor1, GlobalVariable.Proveedor2]

for (int i = 0; i < listaAgregar.size(); i++) {
    String nombreProveedor = listaAgregar.get(i)
    WebUI.comment("--- Agregando a: " + nombreProveedor + " ---")

    // --- Abrir dropdown "Agregar" ---
    TestObject btnDropdownAgregar = new TestObject("btnDropdownAgregar_${i}")
    btnDropdownAgregar.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'dropdown-toggle') and contains(normalize-space(.), 'Agregar')]")
    WebUI.waitForElementPresent(btnDropdownAgregar, 5)
    WebElement elDrop = WebUI.findWebElement(btnDropdownAgregar)
    WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elDrop))
    WebUI.delay(1)

    // --- Clic en "Manual" ---
    TestObject btnManual = new TestObject("btnManual_${i}")
    btnManual.addProperty("xpath", ConditionType.EQUALS, "//a[@data-bs-target='#modal-add-supplier']")
    WebUI.waitForElementPresent(btnManual, 5)
    WebElement elManual = WebUI.findWebElement(btnManual)
    WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elManual))
    WebUI.delay(1)

    // --- Esperar Modal ---
    TestObject modalAddSupplier = new TestObject("modalAddSupplier_${i}")
    modalAddSupplier.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Agregar proveedor a la compra')]")
    WebUI.waitForElementVisible(modalAddSupplier, 10)

    // --- Clic en el Select2 Container ---
    TestObject select2Container = new TestObject("select2Container_${i}")
    select2Container.addProperty("xpath", ConditionType.EQUALS, "//span[contains(@class, 'select2-selection--single') and @aria-labelledby='select2-select2-new-supplier-container']")
    WebUI.waitForElementClickable(select2Container, 5)
    WebUI.click(select2Container)
    WebUI.delay(1)

    // --- Escribir en el buscador invisible ---
    TestObject inputSelect2 = new TestObject("inputSelect2_${i}")
    inputSelect2.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'select2-search__field')]")
    WebUI.waitForElementVisible(inputSelect2, 5)
    WebUI.setText(inputSelect2, nombreProveedor)
    WebUI.delay(2) // Dar tiempo a que AJAX traiga el resultado

    // --- Hacer clic en la sugerencia ---
    TestObject optionResult = new TestObject("optionResult_${i}")
    optionResult.addProperty("xpath", ConditionType.EQUALS, "//li[contains(@class, 'select2-results__option') and contains(normalize-space(.), '${nombreProveedor}')]")
    WebUI.waitForElementClickable(optionResult, 5)
    WebUI.click(optionResult)
    WebUI.delay(1)

    // --- Guardar ---
    TestObject btnAgregarSubmit = new TestObject("btnAgregarSubmit_${i}")
    btnAgregarSubmit.addProperty("xpath", ConditionType.EQUALS, "//button[@id='butAddSupplierSubmit']")
    WebUI.waitForElementClickable(btnAgregarSubmit, 5)
    WebUI.click(btnAgregarSubmit)

    // --- Validar Growl y Cerrarlo ---
    TestObject growlAddExitoso = new TestObject("growlAddExitoso_${i}")
    growlAddExitoso.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')])[last()]")
    WebUI.waitForElementVisible(growlAddExitoso, 10)
    
    TestObject btnCerrarGrowl = new TestObject("btnCerrarGrowl_${i}")
    btnCerrarGrowl.addProperty("xpath", ConditionType.EQUALS, "(//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert'])[last()]")
    
    try {
        WebElement elGrowl = WebUI.findWebElement(btnCerrarGrowl)
        WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elGrowl))
    } catch (Exception e) { }
    
    WebUI.delay(2) // Pausa para limpiar el DOM antes de la próxima vuelta
}

WebUI.comment("✔ ¡Se agregaron exitosamente todos los proveedores de la lista!")