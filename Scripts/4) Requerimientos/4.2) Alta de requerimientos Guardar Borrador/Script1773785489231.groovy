import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys
import requerimientos.LineasUtils
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement


// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// 1) CERRAR DROPDOWN (SI HAY H3)
// ===============================
new utils.UIUtils().cerrarDropdownUsuarioSiEstaAbierto()


// ===============================
// 2) ABRIR MENÚ "REQUERIMIENTOS"
// ===============================
TestObject menuRequerimientos = new TestObject()
menuRequerimientos.addProperty("xpath", ConditionType.EQUALS,
    "//span[normalize-space(text())='Requerimientos']/parent::a"
)

WebUI.mouseOver(menuRequerimientos)
WebUI.delay(1)


// ===============================
// 3) CLIC EN OPCIÓN "REQUERIMIENTOS"
// ===============================
TestObject opcionRequerimientos = new TestObject()
opcionRequerimientos.addProperty("xpath", ConditionType.EQUALS,
    "//a[@class='dropdown-item' and @href='/es/PurchaseRequest/List']"
)

WebUI.waitForElementClickable(opcionRequerimientos, 10)
WebUI.click(opcionRequerimientos)


// ===============================
// 4) CLIC EN "NUEVO REQUERIMIENTO"
// ===============================
TestObject btnNuevoRequerimiento = new TestObject()
btnNuevoRequerimiento.addProperty("xpath", ConditionType.EQUALS,
    "//a[@href='/es/PurchaseRequest/Edit' and contains(@class,'btn-link')]"
)

WebUI.waitForElementClickable(btnNuevoRequerimiento, 10)
WebUI.click(btnNuevoRequerimiento)


// ===============================
// 5) SELECCIONAR ÁREA SOLICITANTE
// ===============================
TestObject dropdownArea = new TestObject()
dropdownArea.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='list01']//a[contains(@class,'chosen-single')]"
)

WebUI.waitForElementClickable(dropdownArea, 10)
WebUI.click(dropdownArea)

TestObject buscadorArea = new TestObject()
buscadorArea.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='list01']//input[contains(@class,'chosen-search-input')]"
)

WebUI.waitForElementVisible(buscadorArea, 10)
WebUI.setText(buscadorArea, "Administracion")
WebUI.sendKeys(buscadorArea, Keys.chord(Keys.ENTER))


// ===============================
// 6) FECHA LÍMITE DE ENTREGA
// ===============================
TestObject fechaEntrega = new TestObject()
fechaEntrega.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='data02']//input[contains(@class,'edit-column-value')]"
)

WebUI.waitForElementVisible(fechaEntrega, 10)
WebUI.click(fechaEntrega)
WebUI.setText(fechaEntrega, "20/03/2026")
WebUI.sendKeys(fechaEntrega, Keys.chord(Keys.TAB))

TestObject labelFechaEntrega = new TestObject()
labelFechaEntrega.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='data02']//label"
)

WebUI.click(labelFechaEntrega)


// ===============================
// 7) REQUIERE DETALLE TÉCNICO
// ===============================
TestObject dropdownDetalleTecnico = new TestObject()
dropdownDetalleTecnico.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='list02']//a[contains(@class,'chosen-single')]"
)

WebUI.waitForElementClickable(dropdownDetalleTecnico, 10)
WebUI.click(dropdownDetalleTecnico)

TestObject buscadorDetalleTecnico = new TestObject()
buscadorDetalleTecnico.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='list02']//input[contains(@class,'chosen-search-input')]"
)

WebUI.waitForElementVisible(buscadorDetalleTecnico, 10)
WebUI.setText(buscadorDetalleTecnico, "Si")
WebUI.sendKeys(buscadorDetalleTecnico, Keys.chord(Keys.ENTER))


// ===============================
// 8) DETALLE TÉCNICO (DINÁMICO)
// ===============================
TestObject detalleTecnico = new TestObject()
detalleTecnico.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='data03']//textarea"
)

WebUI.waitForElementVisible(detalleTecnico, 10)
WebUI.click(detalleTecnico)

// Generamos timestamp único
String timestamp = new Date().format("dd/MM/yyyy HH:mm")
String detalleCompleto = "Detalle técnico QA – " + timestamp

// Escribimos el texto dinámico
new utils.TextoUtils().escribirTextoDinamico(detalleTecnico, detalleCompleto)


// ===============================
// 9) AGREGAR LÍNEAS
// ===============================
def lineas = new LineasUtils()
lineas.agregarLinea()

WebUI.comment("✔ Se agregó 1 línea correctamente")


// ===============================
// 10) SUBIR ARCHIVO
// ===============================
TestObject inputArchivoRequerimiento = new TestObject()
inputArchivoRequerimiento.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//input[@id='input-uploaded-file']"
)

String rutaPDF = RunConfiguration.getProjectDir() + "/Include/files/ayuda.pdf"
WebUI.uploadFile(inputArchivoRequerimiento, rutaPDF)

WebUI.comment("✔ Archivo PDF subido correctamente")
WebUI.delay(2)


// ===============================
// 11) GUARDAR COMO BORRADOR
// ===============================
TestObject btnGuardarBorrador = new TestObject('btnGuardarBorrador')
btnGuardarBorrador.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//button[@class='btn btn-light btn-save-draft' and contains(normalize-space(.),'GUARDAR COMO BORRADOR')]"
)

WebUI.scrollToElement(btnGuardarBorrador, 10)
WebUI.waitForElementClickable(btnGuardarBorrador, 10)
WebUI.click(btnGuardarBorrador)

WebUI.comment("✔ Se hizo clic en GUARDAR COMO BORRADOR")
WebUI.delay(2)


// ===============================
// 12) FORZAR APERTURA DEL MENÚ REQUERIMIENTOS
// ===============================
String jsOpen = """
    var li = document.querySelector("li.pending-items.active");
    if (li) {
        var menu = li.querySelector(".dropdown-menu");
        if (menu) {
            menu.classList.add("show");
            menu.style.display = "block";
        }
    }
"""
WebUI.executeJavaScript(jsOpen, null)
WebUI.delay(1)


// ===============================
// 13) CLICK EN BORRADORES
// ===============================
TestObject opcionBorradores = new TestObject('opcionBorradores')
opcionBorradores.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//a[@class='dropdown-item' and contains(normalize-space(text()),'Borradores')]"
)

WebUI.waitForElementClickable(opcionBorradores, 10)
WebUI.click(opcionBorradores)

WebUI.comment("✔ Se ingresó a la sección Borradores")


// ===============================
// 14) BUSCAR EL BORRADOR RECIÉN CREADO
// ===============================
TestObject filaBorrador = new TestObject('filaBorrador')
filaBorrador.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//td[span[contains(normalize-space(.), '${detalleCompleto}')]]/parent::tr"
)

WebUI.waitForElementVisible(filaBorrador, 10)
WebUI.click(filaBorrador)

WebUI.comment("✔ Se encontró y se abrió el borrador con detalle: ${detalleCompleto}")

// ===============================
// 15) GUARDAR EL REQUERIMIENTO DESDE EL BORRADOR
// ===============================

TestObject btnGuardarFinal = new TestObject('btnGuardarFinal')
btnGuardarFinal.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@id='btn-save-purchaserequest' and contains(normalize-space(.),'GUARDAR')]"
)

WebUI.scrollToElement(btnGuardarFinal, 10)
WebUI.waitForElementClickable(btnGuardarFinal, 10)
WebUI.click(btnGuardarFinal)

WebUI.comment("✔ Se hizo clic en GUARDAR dentro del borrador")

// ===============================
// 16) VALIDAR Y CERRAR GROWL "Enviado"
// ===============================

// Validar que aparece el mensaje de éxito
TestObject growlEnviado = new TestObject('growlEnviado')
growlEnviado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//div[contains(@class,'alert-success')]"
)

WebUI.waitForElementVisible(growlEnviado, 10)
WebUI.comment("✔ Growl de éxito visible: 'Enviado'")

// Cerrar el growl
TestObject btnCerrarGrowl = new TestObject('btnCerrarGrowl')
btnCerrarGrowl.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowl, 10)
WebUI.click(btnCerrarGrowl)

WebUI.comment("✔ Growl cerrado correctamente")

