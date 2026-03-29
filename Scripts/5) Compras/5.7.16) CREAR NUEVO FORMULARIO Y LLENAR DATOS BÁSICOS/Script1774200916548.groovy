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
// 23) CREAR NUEVO FORMULARIO Y LLENAR DATOS BÁSICOS
// ===============================

TestObject btnNuevoFormulario = new TestObject('btnNuevoFormulario')
btnNuevoFormulario.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-form') and contains(normalize-space(.), 'Nuevo formulario')]")

WebUI.waitForElementClickable(btnNuevoFormulario, 15)
WebUI.click(btnNuevoFormulario)
WebUI.comment("✔ Se abrió el modal de Nuevo formulario.")

// --- Completar Título y Descripción ---
TestObject inputTitle = new TestObject('inputTitle')
inputTitle.addProperty("id", ConditionType.EQUALS, "Title")
WebUI.waitForElementVisible(inputTitle, 20) 

TestObject textareaDesc = new TestObject('textareaDesc')
textareaDesc.addProperty("id", ConditionType.EQUALS, "Description")

WebUI.setText(inputTitle, "Formulario QA")
WebUI.setText(textareaDesc, "Formulario QA")

// --- Seleccionar Etapa al azar ---
TestObject selectEtapaForm = new TestObject('selectEtapaForm')
selectEtapaForm.addProperty("id", ConditionType.EQUALS, "TenderingSupplierStageId")

WebDriver driverForm = DriverFactory.getWebDriver()
List<WebElement> opcionesEtapaForm = driverForm.findElements(By.xpath("//select[@id='TenderingSupplierStageId']/option"))
if (opcionesEtapaForm.size() > 1) {
    int indexAzarEtapa = new Random().nextInt(opcionesEtapaForm.size() - 1) + 1
    WebUI.selectOptionByIndex(selectEtapaForm, indexAzarEtapa)
}

// --- Tildar los checkboxes ---
TestObject chkSupplierCanEdit = new TestObject('chkSupplierCanEdit')
chkSupplierCanEdit.addProperty("id", ConditionType.EQUALS, "SupplierCanEdit")
TestObject chkFormTypeReq = new TestObject('chkFormTypeReq')
chkFormTypeReq.addProperty("id", ConditionType.EQUALS, "chk-formtype-purchaserequests")
TestObject chkClosedEnvelope = new TestObject('chkClosedEnvelope')
chkClosedEnvelope.addProperty("id", ConditionType.EQUALS, "ClosedEnvelope")

WebUI.check(chkSupplierCanEdit)
WebUI.check(chkFormTypeReq)
WebUI.check(chkClosedEnvelope)
WebUI.comment("✔ Checkboxes marcados.")


// ===============================
// 24) VALIDACIONES NEGATIVAS DE SOBRE CERRADO
// ===============================

TestObject btnGuardarForm = new TestObject('btnGuardarForm')
btnGuardarForm.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-submit-form')]")

// --- VALIDACIÓN 1: Tipo de Apertura vacío ---
WebUI.click(btnGuardarForm)
TestObject growlInvalido = new TestObject('growlInvalido')
growlInvalido.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-warning') and contains(normalize-space(.), 'Se ingresaron datos inválidos')]")

WebUI.waitForElementVisible(growlInvalido, 5)
WebUI.comment("✔ Validación 1 exitosa: Apareció 'Se ingresaron datos inválidos'.")

TestObject btnCerrarWarning = new TestObject('btnCerrarWarning')
btnCerrarWarning.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-warning')]//button[@data-bs-dismiss='alert']")
WebElement elCerrarWarn1 = WebUI.findWebElement(btnCerrarWarning)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarWarn1))
WebUI.delay(1)


// --- VALIDACIÓN 2: Roles vacíos ---
TestObject selectApertura = new TestObject('selectApertura')
selectApertura.addProperty("id", ConditionType.EQUALS, "ClosedEnvelopeType")
// Seleccionamos Manual explícitamente para que pida Roles y Etapa
WebUI.selectOptionByValue(selectApertura, "Manual", false)

WebUI.click(btnGuardarForm)
TestObject growlRoles = new TestObject('growlRoles')
growlRoles.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-warning') and contains(normalize-space(.), 'Roles y Usuarios con permiso para abrir el Sobre')]")

WebUI.waitForElementVisible(growlRoles, 5)
WebUI.comment("✔ Validación 2 exitosa: Apareció alerta de Roles obligatorios.")

WebElement elCerrarWarn2 = WebUI.findWebElement(btnCerrarWarning)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarWarn2))
WebUI.delay(1)


// --- VALIDACIÓN 3: Etapa de apertura vacía ---
TestObject inputChosenRoles = new TestObject('inputChosenRoles')
// Buscamos el input del Multi-Select Chosen de Roles
inputChosenRoles.addProperty("xpath", ConditionType.EQUALS, "//select[@name='CanOpenClosedEnvelopeIdList']/following-sibling::div//input[contains(@class, 'chosen-search-input')]")

WebUI.click(inputChosenRoles)
WebUI.delay(1)
WebUI.sendKeys(inputChosenRoles, Keys.chord(Keys.ARROW_DOWN, Keys.ENTER)) // Elegimos el primer rol
WebUI.delay(1)

WebUI.click(btnGuardarForm)
TestObject growlEtapaApertura = new TestObject('growlEtapaApertura')
growlEtapaApertura.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-warning') and contains(normalize-space(.), 'Etapa en la que se habilita la apertura del sobre')]")

WebUI.waitForElementVisible(growlEtapaApertura, 5)
WebUI.comment("✔ Validación 3 exitosa: Apareció alerta de Etapa obligatoria.")

WebElement elCerrarWarn3 = WebUI.findWebElement(btnCerrarWarning)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarWarn3))
WebUI.delay(1)


// ===============================
// 25) COMPLETAR ÚLTIMO CAMPO Y GUARDAR CON ÉXITO
// ===============================

// --- 25.1) Abrir el dropdown de la Etapa ---
TestObject dropdownChosenEtapa = new TestObject('dropdownChosenEtapa')
dropdownChosenEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[@id='ClosedEnvelopeTenderingSupplierStageId_chosen']//a[contains(@class, 'chosen-single')]")

WebUI.waitForElementClickable(dropdownChosenEtapa, 5)
WebUI.click(dropdownChosenEtapa)
WebUI.delay(1) // Esperamos que se despliegue el menú

// --- 25.2) Seleccionar usando el teclado (¡A prueba de fallos!) ---
TestObject inputSearchEtapa = new TestObject('inputSearchEtapa')
// Apuntamos al input de búsqueda que aparece al abrir el dropdown
inputSearchEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[@id='ClosedEnvelopeTenderingSupplierStageId_chosen']//input[contains(@class, 'chosen-search-input')]")

WebUI.waitForElementVisible(inputSearchEtapa, 5)
// Flecha Abajo salta el "Seleccionar" y pasa a la primera Etapa. El Enter confirma y cierra el menú.
WebUI.sendKeys(inputSearchEtapa, Keys.chord(Keys.ARROW_DOWN, Keys.ENTER))
WebUI.comment("✔ Se completó la Etapa de apertura usando el teclado. El menú debería estar cerrado.")
WebUI.delay(1) // Le damos tiempo a la animación de cierre


// --- 25.3) Clic final para guardar ---
TestObject btnGuardarFormulario = new TestObject('btnGuardarFormulario')
btnGuardarFormulario.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-primary') and contains(normalize-space(.), 'Guardar')]")

WebUI.waitForElementClickable(btnGuardarFormulario, 5)
WebUI.click(btnGuardarFormulario)
WebUI.comment("✔ Formulario completado correctamente. Guardando...")


// --- 25.4) Validar Growl de Éxito ---
TestObject growlExitoForm = new TestObject('growlExitoForm')
growlExitoForm.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-success') and contains(normalize-space(.), 'Guardado')]")

WebUI.waitForElementVisible(growlExitoForm, 10)
WebUI.comment("✔ ¡Éxito! Formulario guardado correctamente.")

TestObject btnCerrarGrowlExitoFormulario = new TestObject('btnCerrarGrowlExitoFormulario')
btnCerrarGrowlExitoFormulario.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'alert-success')]//button[@data-bs-dismiss='alert']")

// Cerramos usando JS por seguridad, por si hay algo más superpuesto
WebElement elCerrarExito = WebUI.findWebElement(btnCerrarGrowlExitoFormulario)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarExito))

// Damos tiempo a que se recargue la tabla de fondo
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// ===============================
// 26) ABRIR "FORMULARIO QA" (SI ESTÁ CERRADO) Y AGREGAR CAMPO
// ===============================

// Guardamos el XPath del contenedor base en una variable para no repetir tanto código
String xpathContenedorFormQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]"

// --- 26.1) Definir los objetos ---
TestObject btnNuevoCampo = new TestObject('btnNuevoCampo')
btnNuevoCampo.addProperty("xpath", ConditionType.EQUALS, xpathContenedorFormQA + "//a[contains(@class, 'btn-new-row') and contains(normalize-space(.), 'Nuevo campo')]")

TestObject iconoDesplegar = new TestObject('iconoDesplegar')
iconoDesplegar.addProperty("xpath", ConditionType.EQUALS, xpathContenedorFormQA + "//i[contains(@class, 'fa-chevron-down')]")


// --- 26.2) Chequear visibilidad y desplegar SOLO si es necesario ---
// Usamos FailureHandling.OPTIONAL para que Katalon no aborte el test si el botón está oculto
boolean isBotonVisible = WebUI.verifyElementVisible(btnNuevoCampo, FailureHandling.OPTIONAL)

if (!isBotonVisible) {
    WebUI.comment("ℹ El botón 'Nuevo campo' está oculto. Desplegando el acordeón de 'Formulario QA'...")
    WebUI.waitForElementClickable(iconoDesplegar, 5)
    WebUI.click(iconoDesplegar)
    WebUI.delay(2) // Pausa para darle tiempo a la animación (slide down) de Bootstrap
} else {
    WebUI.comment("✔ El botón 'Nuevo campo' ya está visible. Nos ahorramos el clic en el desplegable.")
}


// --- 26.3) Hacer clic en "Nuevo campo" ---
WebUI.waitForElementClickable(btnNuevoCampo, 10)

// Usamos JS click por si el scroll o la caja del acordeón molestan a nivel UI
WebElement elNuevoCampo = WebUI.findWebElement(btnNuevoCampo)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoCampo))

WebUI.comment("✔ ¡Adentro! Se hizo clic en 'Nuevo campo' para editar el Formulario QA.")
WebUI.delay(2)

// ===============================
// 27) CREAR "CAMPO QA 1" (TIPO TEXTO CORTO)
// ===============================

// --- 27.1) Esperar a que el modal de Crear Campo esté visible ---
TestObject modalCrearCampo = new TestObject('modalCrearCampo')
modalCrearCampo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Crear campo')]")

WebUI.waitForElementVisible(modalCrearCampo, 10)
WebUI.comment("✔ Modal 'Crear campo' visible.")


// --- 27.2) Completar el Título ---
TestObject inputRowTitle = new TestObject('inputRowTitle')
inputRowTitle.addProperty("id", ConditionType.EQUALS, "RowTitle")

WebUI.setText(inputRowTitle, "Campo QA 1")
WebUI.comment("✔ Título seteado a 'Campo QA 1'.")


// --- 27.3) Seleccionar el Tipo "Texto corto" ---
TestObject selectRowType = new TestObject('selectRowType')
selectRowType.addProperty("id", ConditionType.EQUALS, "RowType")

// Seleccionamos por el Value exacto que me pasaste en el HTML ("TextShort")
WebUI.selectOptionByValue(selectRowType, "TextShort", false)
WebUI.comment("✔ Tipo de campo seteado a 'Texto corto'.")


// --- 27.4) Guardar el campo ---
TestObject btnGuardarCampo = new TestObject('btnGuardarCampo')
// Apuntamos por el ID del botón que me compartiste
btnGuardarCampo.addProperty("id", ConditionType.EQUALS, "btnRowEditSubmit")

WebUI.waitForElementClickable(btnGuardarCampo, 5)
WebUI.click(btnGuardarCampo)
WebUI.comment("✔ Guardando campo...")


// ===============================
// 28) VALIDAR Y CERRAR GROWL DE ÉXITO
// ===============================

TestObject growlCampoGuardado = new TestObject('growlCampoGuardado')
growlCampoGuardado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlCampo = new TestObject('btnCerrarGrowlCampo')
btnCerrarGrowlCampo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlCampoGuardado, 10)
String textoGrowlCampo = WebUI.getText(growlCampoGuardado).trim()

if (textoGrowlCampo.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Cambios guardados'.")
}

// Cerramos la alerta
WebElement elCerrarGrowl = WebUI.findWebElement(btnCerrarGrowlCampo)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowl))
WebUI.delay(2) // Damos tiempo a que se cierre el modal y se recargue la vista de fondo


// ===============================
// 29) VOLVER A HACER CLIC EN "NUEVO CAMPO"
// ===============================

// Reciclamos el XPath seguro que usamos antes para el "Formulario QA"
String xpathContenedorQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]"

TestObject btnNuevoCampo2 = new TestObject('btnNuevoCampo2')
btnNuevoCampo2.addProperty("xpath", ConditionType.EQUALS, xpathContenedorQA + "//a[contains(@class, 'btn-new-row') and contains(normalize-space(.), 'Nuevo campo')]")

WebUI.waitForElementClickable(btnNuevoCampo2, 10)

// Inyectamos el clic con JS para evitar problemas
WebElement elNuevoCampo2 = WebUI.findWebElement(btnNuevoCampo2)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoCampo2))

WebUI.comment("✔ Se hizo clic nuevamente en 'Nuevo campo'. Esperando el modal para el segundo ítem...")
WebUI.delay(2)

// ===============================
// 30) CREAR "CAMPO QA 2" (TIPO NUMÉRICO POR DEFECTO)
// ===============================

// --- 30.1) Esperar a que el modal de Crear Campo esté visible de nuevo ---
TestObject modalCrearCampo2 = new TestObject('modalCrearCampo2')
modalCrearCampo2.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Crear campo')]")

WebUI.waitForElementVisible(modalCrearCampo2, 10)
WebUI.comment("✔ Modal 'Crear campo' visible para el segundo ítem.")


// --- 30.2) Completar el Título ---
TestObject inputRowTitle2 = new TestObject('inputRowTitle2')
inputRowTitle2.addProperty("id", ConditionType.EQUALS, "RowTitle")

// Limpiamos por las dudas y escribimos
WebUI.clearText(inputRowTitle2)
WebUI.setText(inputRowTitle2, "Campo QA 2")
WebUI.comment("✔ Título seteado a 'Campo QA 2'.")


// --- 30.3) Guardar el campo ---
TestObject btnGuardarCampo2 = new TestObject('btnGuardarCampo2')
btnGuardarCampo2.addProperty("id", ConditionType.EQUALS, "btnRowEditSubmit")

WebUI.waitForElementClickable(btnGuardarCampo2, 5)
WebUI.click(btnGuardarCampo2)
WebUI.comment("✔ Guardando Campo QA 2...")


// ===============================
// 31) VALIDAR Y CERRAR GROWL DE ÉXITO ("CAMPO QA 2")
// ===============================

TestObject growlCampoGuardado2 = new TestObject('growlCampoGuardado2')
growlCampoGuardado2.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlCampo2 = new TestObject('btnCerrarGrowlCampo2')
btnCerrarGrowlCampo2.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlCampoGuardado2, 10)
String textoGrowlCampo2 = WebUI.getText(growlCampoGuardado2).trim()

if (textoGrowlCampo2.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Cambios guardados'.")
}

// Cerramos la alerta
WebElement elCerrarGrowl2 = WebUI.findWebElement(btnCerrarGrowlCampo2)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowl2))
WebUI.delay(2) // Damos tiempo a que se cierre el modal y se recargue la vista de fondo


// ===============================
// 32) VOLVER A HACER CLIC EN "NUEVO CAMPO" (TERCER ÍTEM)
// ===============================

// Reciclamos el XPath seguro del contenedor "Formulario QA"
String xpathContenedorQA2 = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]"

TestObject btnNuevoCampo3 = new TestObject('btnNuevoCampo3')
btnNuevoCampo3.addProperty("xpath", ConditionType.EQUALS, xpathContenedorQA2 + "//a[contains(@class, 'btn-new-row') and contains(normalize-space(.), 'Nuevo campo')]")

// Verificamos si el botón está visible o quedó oculto bajo el acordeón
TestObject iconoDesplegar2 = new TestObject('iconoDesplegar2')
iconoDesplegar2.addProperty("xpath", ConditionType.EQUALS, xpathContenedorQA2 + "//i[contains(@class, 'fa-chevron-down')]")

boolean isBotonVisible3 = WebUI.verifyElementVisible(btnNuevoCampo3, FailureHandling.OPTIONAL)

if (!isBotonVisible3) {
	WebUI.comment("ℹ El botón 'Nuevo campo' quedó oculto. Desplegando acordeón...")
	WebUI.waitForElementClickable(iconoDesplegar2, 5)
	WebUI.click(iconoDesplegar2)
	WebUI.delay(2)
}

WebUI.waitForElementClickable(btnNuevoCampo3, 10)

// Inyectamos el clic con JS
WebElement elNuevoCampo3 = WebUI.findWebElement(btnNuevoCampo3)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elNuevoCampo3))

WebUI.comment("✔ Se hizo clic nuevamente en 'Nuevo campo'. Esperando el modal para el tercer ítem...")
WebUI.delay(2)

// ===============================
// 33) CREAR "CAMPO QA 3" (TIPO LISTA)
// ===============================

// --- 33.1) Esperar a que el modal de Crear Campo esté visible de nuevo ---
TestObject modalCrearCampo3 = new TestObject('modalCrearCampo3')
modalCrearCampo3.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Crear campo')]")

WebUI.waitForElementVisible(modalCrearCampo3, 10)
WebUI.comment("✔ Modal 'Crear campo' visible para el tercer ítem.")


// --- 33.2) Completar el Título ---
TestObject inputRowTitle3 = new TestObject('inputRowTitle3')
inputRowTitle3.addProperty("id", ConditionType.EQUALS, "RowTitle")

WebUI.clearText(inputRowTitle3)
WebUI.setText(inputRowTitle3, "Campo QA 3")
WebUI.comment("✔ Título seteado a 'Campo QA 3'.")


// --- 33.3) Seleccionar el Tipo "Lista" ---
TestObject selectRowType3 = new TestObject('selectRowType3')
selectRowType3.addProperty("id", ConditionType.EQUALS, "RowType")

// Seleccionamos por el Value exacto del HTML para "Lista"
WebUI.selectOptionByValue(selectRowType3, "ListSingle", false)
WebUI.comment("✔ Tipo de campo seteado a 'Lista'.")


// --- 33.4) Agregar valor a la lista ---
TestObject inputNewListValue = new TestObject('inputNewListValue')
inputNewListValue.addProperty("id", ConditionType.EQUALS, "newListValue")

TestObject btnAddListValue = new TestObject('btnAddListValue')
btnAddListValue.addProperty("id", ConditionType.EQUALS, "addListValue")

// Esperamos que el input esté visible (probablemente se muestra al elegir 'Lista')
WebUI.waitForElementVisible(inputNewListValue, 5)

WebUI.setText(inputNewListValue, "Si")
WebUI.click(btnAddListValue)
WebUI.comment("✔ Se agregó la opción 'Si' a la lista de valores.")
WebUI.delay(1) // Pausa para que el elemento se sume visualmente al DOM


// --- 33.5) Guardar el campo ---
TestObject btnGuardarCampo3 = new TestObject('btnGuardarCampo3')
btnGuardarCampo3.addProperty("id", ConditionType.EQUALS, "btnRowEditSubmit")

WebUI.waitForElementClickable(btnGuardarCampo3, 5)
WebUI.click(btnGuardarCampo3)
WebUI.comment("✔ Guardando Campo QA 3...")


// ===============================
// 34) VALIDAR Y CERRAR GROWL DE ÉXITO ("CAMPO QA 3")
// ===============================

TestObject growlCampoGuardado3 = new TestObject('growlCampoGuardado3')
growlCampoGuardado3.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlCampo3 = new TestObject('btnCerrarGrowlCampo3')
btnCerrarGrowlCampo3.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlCampoGuardado3, 10)
String textoGrowlCampo3 = WebUI.getText(growlCampoGuardado3).trim()

if (textoGrowlCampo3.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Apareció el mensaje de éxito 'Cambios guardados'.")
}

// Cerramos la alerta
WebElement elCerrarGrowl3 = WebUI.findWebElement(btnCerrarGrowlCampo3)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowl3))

// ===============================
// 35) EDITAR "CAMPO QA 2" PARA AGREGAR DEPENDENCIA
// ===============================

// --- 35.1) Buscar "Campo QA 2" y hacer click en editar ---
// Usamos el XPath base del Formulario QA para no salirnos de contexto
String xpathFormularioQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]"

TestObject filaCampoQA2 = new TestObject('filaCampoQA2')
filaCampoQA2.addProperty("xpath", ConditionType.EQUALS, xpathFormularioQA + "//div[contains(@class, 'custom-form-row') and .//div[contains(normalize-space(.), 'Campo QA 2')]]")

WebUI.waitForElementVisible(filaCampoQA2, 10)
WebUI.mouseOver(filaCampoQA2)
WebUI.delay(1)

TestObject btnEditarCampo2 = new TestObject('btnEditarCampo2')
// Buscamos el lapicito (btn-edit-form / edit-row) dentro de la fila de Campo QA 2
btnEditarCampo2.addProperty("xpath", ConditionType.EQUALS, xpathFormularioQA + "//div[contains(@class, 'custom-form-row') and .//div[contains(normalize-space(.), 'Campo QA 2')]]//a[contains(@class, 'edit-row')]")

WebUI.waitForElementPresent(btnEditarCampo2, 5)
WebElement elPenCampo2 = WebUI.findWebElement(btnEditarCampo2)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenCampo2))
WebUI.comment("✔ Se hizo clic en editar para el 'Campo QA 2'.")


// --- 35.2) Tildar checkbox de dependencia en el modal ---
TestObject modalEditarCampo = new TestObject('modalEditarCampo')
modalEditarCampo.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h5[contains(normalize-space(.), 'Editar campo')]")
WebUI.waitForElementVisible(modalEditarCampo, 10)

TestObject chkDependencia = new TestObject('chkDependencia')
chkDependencia.addProperty("id", ConditionType.EQUALS, "chk-has-dependency")

WebUI.waitForElementClickable(chkDependencia, 5)
WebUI.check(chkDependencia)
WebUI.comment("✔ Se tildó 'Dependencia'.")


// --- 35.3) Verificar el campo del que depende ---
TestObject selectRowDependency = new TestObject('selectRowDependency')
selectRowDependency.addProperty("id", ConditionType.EQUALS, "ddl-form-row-dependency")

WebUI.waitForElementVisible(selectRowDependency, 5)
// Obtenemos el texto visible de la opción seleccionada
String dependenciaSeleccionada = WebUI.getAttribute(selectRowDependency, "value") // Captura el value del option
// Como no sabemos el guid, validamos el texto
String textoOpcionSeleccionada = WebUI.executeJavaScript("return arguments[0].options[arguments[0].selectedIndex].text;", Arrays.asList(WebUI.findWebElement(selectRowDependency)))

if (textoOpcionSeleccionada.contains("Campo QA 3")) {
	WebUI.comment("✔ Confirmado: La dependencia apunta a 'Campo QA 3'.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: La dependencia seleccionada no es 'Campo QA 3'. Es: " + textoOpcionSeleccionada)
}


// --- 35.4) Elegir "Si" en los valores (Chosen Multi-Select) ---
TestObject inputValoresDependencia = new TestObject('inputValoresDependencia')
inputValoresDependencia.addProperty("xpath", ConditionType.EQUALS, "//div[@id='lb_dependency_options_chosen']//input[contains(@class, 'chosen-search-input')]")

WebUI.waitForElementClickable(inputValoresDependencia, 5)
WebUI.click(inputValoresDependencia)
WebUI.delay(1)

// Escribimos "Si" y le damos Enter
WebUI.setText(inputValoresDependencia, "Si")
WebUI.delay(1) // Esperamos a que filtre el plugin Chosen
WebUI.sendKeys(inputValoresDependencia, Keys.chord(Keys.ENTER))
WebUI.comment("✔ Opción 'Si' agregada como valor de dependencia.")


// --- 35.5) Guardar la edición ---
TestObject btnGuardarEdicion = new TestObject('btnGuardarEdicion')
btnGuardarEdicion.addProperty("id", ConditionType.EQUALS, "btnRowEditSubmit")

WebUI.waitForElementClickable(btnGuardarEdicion, 5)
WebUI.click(btnGuardarEdicion)
WebUI.comment("✔ Guardando la edición del campo...")


// ===============================
// 36) VALIDAR Y CERRAR GROWL DE EDICIÓN
// ===============================

TestObject growlEdicionGuardada = new TestObject('growlEdicionGuardada')
growlEdicionGuardada.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEdicion = new TestObject('btnCerrarGrowlEdicion')
btnCerrarGrowlEdicion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEdicionGuardada, 10)
String textoGrowlEdicion = WebUI.getText(growlEdicionGuardada).trim()

if (textoGrowlEdicion.contains("Cambios guardados") || textoGrowlEdicion.contains("Guardado")) {
	WebUI.comment("✔ Confirmado: La dependencia se guardó con éxito.")
}

WebElement elCerrarGrowlEdicion = WebUI.findWebElement(btnCerrarGrowlEdicion)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlEdicion))
WebUI.delay(2)
WebUI.delay(2) // Damos tiempo a que se cierre el modal y se recargue la vista de fondo


// ===============================
// 37) ELIMINAR EL "FORMULARIO QA" COMPLETO
// ===============================

// --- 37.1) Buscar la cabecera de "Formulario QA" y hacer clic en el lápiz principal ---
String xpathCabeceraFormQA = "//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]//div[contains(@class, 'accordion-row')]"

TestObject filaCabeceraFormQA = new TestObject('filaCabeceraFormQA')
filaCabeceraFormQA.addProperty("xpath", ConditionType.EQUALS, xpathCabeceraFormQA)

WebUI.waitForElementVisible(filaCabeceraFormQA, 10)
WebUI.mouseOver(filaCabeceraFormQA)
WebUI.delay(1) // Esperamos que aparezca el hover

TestObject btnEditarFormulario = new TestObject('btnEditarFormulario')
// Buscamos el lápiz (btn-edit-form) dentro de la cabecera del Formulario QA
btnEditarFormulario.addProperty("xpath", ConditionType.EQUALS, xpathCabeceraFormQA + "//a[contains(@class, 'btn-edit-form')]")

WebUI.waitForElementPresent(btnEditarFormulario, 5)
WebElement elPenFormulario = WebUI.findWebElement(btnEditarFormulario)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenFormulario))
WebUI.comment("✔ Se hizo clic en el lápiz de edición principal de 'Formulario QA'.")


// --- 37.2) Hacer clic en "Eliminar" (tacho de basura) en el primer modal ---
TestObject btnEliminarFormModal = new TestObject('btnEliminarFormModal')
// Apuntamos directo al ID que me pasaste
btnEliminarFormModal.addProperty("id", ConditionType.EQUALS, "btn-delete-form")

WebUI.waitForElementClickable(btnEliminarFormModal, 10)
WebUI.click(btnEliminarFormModal)
WebUI.comment("✔ Se hizo clic en 'Eliminar' dentro del modal de edición.")


// --- 37.3) Validar el texto del segundo modal y confirmar la eliminación ---
TestObject modalConfirmacionDelete = new TestObject('modalConfirmacionDelete')
modalConfirmacionDelete.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//h3[contains(normalize-space(.), 'Confirmar eliminación')]")

WebUI.waitForElementVisible(modalConfirmacionDelete, 5)
WebUI.comment("✔ Se abrió el modal de confirmación de borrado.")

TestObject btnConfirmarDeleteRed = new TestObject('btnConfirmarDeleteRed')
btnConfirmarDeleteRed.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-danger') and normalize-space(.)='Eliminar']")

WebUI.waitForElementClickable(btnConfirmarDeleteRed, 5)
WebUI.click(btnConfirmarDeleteRed)
WebUI.comment("✔ Se hizo clic en el botón rojo 'Eliminar' final.")


// ===============================
// 38) VALIDAR Y CERRAR GROWL DE "ELIMINADO" (FORMULARIO)
// ===============================

TestObject growlFormEliminado = new TestObject('growlFormEliminado')
growlFormEliminado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

// Le cambiamos el nombre al objeto por las dudas
TestObject btnCerrarGrowlFormEliminado = new TestObject('btnCerrarGrowlFormEliminado')
btnCerrarGrowlFormEliminado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlFormEliminado, 10)

// ¡ACÁ ESTÁ LA MAGIA! Le cambiamos el nombre a la variable String
String textoGrowlFormularioEliminado = WebUI.getText(growlFormEliminado).trim()

if (textoGrowlFormularioEliminado.contains("Eliminado")) {
    WebUI.comment("✔ Confirmado: Se eliminó el formulario QA y apareció el mensaje 'Eliminado'.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje final no fue 'Eliminado'. Se encontró: " + textoGrowlFormularioEliminado)
}

WebElement elCerrarGrowlDelForm = WebUI.findWebElement(btnCerrarGrowlFormEliminado)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elCerrarGrowlDelForm))
WebUI.delay(2)

// Opcional: Verificamos que el contenedor ya no exista en el DOM
WebDriver driverCheckForm = DriverFactory.getWebDriver()
List<WebElement> formSobreviviente = driverCheckForm.findElements(By.xpath("//div[contains(@class, 'form-container') and .//label[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'FORMULARIO QA')]]"))

if (formSobreviviente.size() == 0) {
    WebUI.comment("✔ ¡ÉXITO TOTAL! El 'Formulario QA' desapareció completamente de la vista.")
} else {
    throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El 'Formulario QA' sigue visible en el HTML.")
}