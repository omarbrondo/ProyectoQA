import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.interactions.Actions
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
// 2) ABRIR MENÚ "REQUERIMIENTOS" (HOVER)
// ===============================
TestObject menuRequerimientos = new TestObject()
menuRequerimientos.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[normalize-space(text())='Requerimientos']/parent::a"
)

WebUI.mouseOver(menuRequerimientos)
WebUI.delay(1) // pequeño delay para que el menú se despliegue

// ===============================
// 3) CLIC EN OPCIÓN "REQUERIMIENTOS"
// ===============================
TestObject opcionRequerimientos = new TestObject()
opcionRequerimientos.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@class='dropdown-item' and @href='/es/PurchaseRequest/List']"
)

WebUI.waitForElementClickable(opcionRequerimientos, 10)
WebUI.click(opcionRequerimientos)

// ===============================
// 4) CLIC EN BOTÓN "NUEVO REQUERIMIENTO"
// ===============================
TestObject btnNuevoRequerimiento = new TestObject()
btnNuevoRequerimiento.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[@href='/es/PurchaseRequest/Edit' and contains(@class,'btn-link')]"
)

WebUI.waitForElementClickable(btnNuevoRequerimiento, 10)
WebUI.click(btnNuevoRequerimiento)

// ===============================
// 5) SELECCIONAR ÁREA SOLICITANTE
// ===============================
TestObject dropdownArea = new TestObject()
dropdownArea.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='list01']//a[contains(@class,'chosen-single')]"
)

WebUI.waitForElementClickable(dropdownArea, 10)
WebUI.click(dropdownArea)

TestObject buscadorArea = new TestObject()
buscadorArea.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='list01']//input[contains(@class,'chosen-search-input')]"
)

WebUI.waitForElementVisible(buscadorArea, 10)
WebUI.setText(buscadorArea, "Administracion")
WebUI.sendKeys(buscadorArea, Keys.chord(Keys.ENTER))

// ===============================
// 6) COMPLETAR FECHA LÍMITE DE ENTREGA (Y CERRAR CALENDARIO)
// ===============================
TestObject fechaEntrega = new TestObject()
fechaEntrega.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='data02']//input[contains(@class,'edit-column-value')]"
)

WebUI.waitForElementVisible(fechaEntrega, 10)

// Click para activar el datepicker
WebUI.click(fechaEntrega)

// Escribir la fecha
WebUI.setText(fechaEntrega, "20/03/2026")

// Cerrar el calendario con TAB
WebUI.sendKeys(fechaEntrega, Keys.chord(Keys.TAB))

// CLIC EN EL LABEL PARA CERRAR EL CALENDARIO
TestObject labelFechaEntrega = new TestObject()
labelFechaEntrega.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='data02']//label"
)

WebUI.click(labelFechaEntrega)

// ===============================
// 7) SELECCIONAR "Requiere Detalle Técnico"
// ===============================

// 1) Abrir el dropdown
TestObject dropdownDetalleTecnico = new TestObject()
dropdownDetalleTecnico.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='list02']//a[contains(@class,'chosen-single')]"
)

WebUI.waitForElementClickable(dropdownDetalleTecnico, 10)
WebUI.click(dropdownDetalleTecnico)


// 2) Escribir el valor en el buscador del Chosen
TestObject buscadorDetalleTecnico = new TestObject()
buscadorDetalleTecnico.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='list02']//input[contains(@class,'chosen-search-input')]"
)

WebUI.waitForElementVisible(buscadorDetalleTecnico, 10)
WebUI.setText(buscadorDetalleTecnico, "Si")
WebUI.sendKeys(buscadorDetalleTecnico, Keys.chord(Keys.ENTER))

// ===============================
// 8) DETALLE TÉCNICO — TEXTAREA
// ===============================
TestObject detalleTecnico = new TestObject()
detalleTecnico.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[@data-columncode='data03']//textarea"
)

WebUI.waitForElementVisible(detalleTecnico, 10)
WebUI.click(detalleTecnico)

// ===============================
// 9) ESCRIBIR DETALLE TÉCNICO CON TU KEYBOARD
// ===============================
new utils.TextoUtils().escribirTextoDinamico(detalleTecnico, "Detalle técnico QA")

// ===============================
// 10) CLIC EN "Nueva línea"
// ===============================
TestObject btnNuevaLinea = new TestObject()
btnNuevaLinea.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//a[contains(@class,'btn-edit-line') and contains(normalize-space(text()),'Nueva linea')]"
)

WebUI.waitForElementClickable(btnNuevaLinea, 10)
WebUI.click(btnNuevaLinea)


// ===============================
// 11) SELECCIONAR UNA LÍNEA DE INVENTARIO AL AZAR
// ===============================

// 1) Todas las filas clickeables del modal
TestObject filasInventario = new TestObject()
filasInventario.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//tr[contains(@class,'btn-assign-inventory')]"
)

// 2) Obtener la lista de elementos
List<WebElement> listaFilas = WebUI.findWebElements(filasInventario, 10)

// 3) Elegir una fila al azar
int indiceRandom = new Random().nextInt(listaFilas.size())
WebElement filaSeleccionada = listaFilas.get(indiceRandom)

// 4) Hacer clic en la fila elegida
filaSeleccionada.click()

WebUI.comment("✔ Fila seleccionada al azar: índice " + indiceRandom)

// ===============================
// ESPERAR MODAL "EDITAR LÍNEA"
// ===============================
TestObject modalEditarLinea = new TestObject()
modalEditarLinea.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//h3[contains(normalize-space(text()),'Editar linea')]"
)

WebUI.waitForElementVisible(modalEditarLinea, 10)



// ===============================
// CANTIDAD — CLICK + JS VALUE
// ===============================

// 1) Localizar el input visible por data-columncode
TestObject campoCantidad = new TestObject()
campoCantidad.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[@data-columncode='Quantity']//input[contains(@class,'mask-numeric')]"
)

// 2) Esperar a que esté visible
WebUI.waitForElementVisible(campoCantidad, 10)
WebUI.scrollToElement(campoCantidad, 5)
WebUI.delay(0.2)

// 3) CLICK REAL (Selenium normal)
WebUI.click(campoCantidad)
WebUI.delay(0.2)   // deja que la máscara active el input real

// 4) Número aleatorio
int cantidadRandom = new Random().nextInt(100) + 1

// 5) ESCRIBIR DIRECTO EN EL INPUT REAL
WebUI.executeJavaScript(
    """
    const input = arguments[0];
    input.value = arguments[1];
    input.dispatchEvent(new Event('input', { bubbles: true }));
    input.dispatchEvent(new Event('change', { bubbles: true }));
    """,
    Arrays.asList(WebUI.findWebElement(campoCantidad, 10), cantidadRandom.toString())
)

WebUI.comment("✔ Cantidad seleccionada: " + cantidadRandom)




// ===============================
// SUBIR ARCHIVO PDF (ESPECIFICACIONES TÉCNICAS)
// ===============================
TestObject inputArchivoLinea = new TestObject()
inputArchivoLinea.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@data-columncode='data04']//input[@type='file']"
)

String rutaPDF = RunConfiguration.getProjectDir() + "/Include/files/ayuda.pdf"

WebUI.uploadFile(inputArchivoLinea, rutaPDF)

WebUI.delay(5.0)

// ===============================
// CLIC EN SIGUIENTE DEL MODAL
// ===============================
TestObject btnSiguienteModal = new TestObject()
btnSiguienteModal.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[@id='modal-purchaserequestline-edit']//button[contains(@class,'btn-submit-form')]"
)

WebUI.waitForElementClickable(btnSiguienteModal, 10)
WebUI.click(btnSiguienteModal)

WebUI.comment("✔ Se hizo clic en SIGUIENTE del modal de edición")


