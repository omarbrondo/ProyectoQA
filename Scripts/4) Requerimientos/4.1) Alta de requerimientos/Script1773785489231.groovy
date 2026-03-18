import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys
import requerimientos.LineasUtils
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling



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
// 8) DETALLE TÉCNICO
// ===============================
TestObject detalleTecnico = new TestObject()
detalleTecnico.addProperty("xpath", ConditionType.EQUALS,
    "//div[@data-columncode='data03']//textarea"
)

WebUI.waitForElementVisible(detalleTecnico, 10)
WebUI.click(detalleTecnico)

new utils.TextoUtils().escribirTextoDinamico(detalleTecnico, "Detalle técnico QA")

// ===============================
// 9) AGREGAR LÍNEAS (MODULARIZADO)
// ===============================
def lineas = new LineasUtils()

lineas.agregarLinea()   // ← primera línea
lineas.agregarLinea()   // ← segunda línea

WebUI.comment("✔ Se agregaron 2 líneas correctamente")
