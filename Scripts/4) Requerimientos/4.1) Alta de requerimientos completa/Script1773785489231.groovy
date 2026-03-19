import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys
import requerimientos.LineasUtils
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration




// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// 1) CERRAR DROPDOWN (SI HAY H3)
// ===============================
new utils.UIUtils().cerrarDropdownUsuarioSiEstaAbierto()

// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ REQUERIMIENTOS
// ===============================
TestObject menuReqNav = new TestObject('menuReqNav')
menuReqNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//li[contains(@class,'nav-item') and contains(@class,'dropdown')]//a[contains(@class,'nav-link') and .//span[normalize-space()='Requerimientos']]"
)

WebUI.waitForElementVisible(menuReqNav, 10)
WebUI.mouseOver(menuReqNav)
WebUI.delay(1)


// ===============================
// 2) FORZAR APERTURA DEL DROPDOWN
// ===============================
String jsOpenReqNav = """
    document.querySelectorAll("li.nav-item.dropdown.dropend").forEach(function(li){
        var span = li.querySelector("span.w-md.text-truncate");
        if(span && span.innerText.trim() === "Requerimientos"){
            var menu = li.querySelector(".dropdown-menu");
            if(menu){
                menu.classList.add("show");
                menu.style.display = "block";
            }
        }
    });
"""
WebUI.executeJavaScript(jsOpenReqNav, null)
WebUI.delay(1)


// ===============================
// 3) CLICK EN LA OPCIÓN "Requerimientos" (SIN IMPORTAR EL HREF)
// ===============================
TestObject opcionReqNav = new TestObject('opcionReqNav')
opcionReqNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'dropdown-menu')]//a[@class='dropdown-item' and normalize-space()='Requerimientos']"
)

WebUI.waitForElementClickable(opcionReqNav, 10)
WebUI.click(opcionReqNav)

WebUI.comment("✔ Se ingresó correctamente a la sección Requerimientos")


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

// ===============================
// 10) Subimos un archivo 
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
// 11) Guardar requerimiento
// ===============================

TestObject btnGuardarRequerimiento = new TestObject()
btnGuardarRequerimiento.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//button[@id='btn-save-purchaserequest']"
)

WebUI.waitForElementClickable(btnGuardarRequerimiento, 10)
WebUI.click(btnGuardarRequerimiento)

WebUI.comment("✔ Se hizo clic en GUARDAR del requerimiento")

// ===============================
// 12) Validar Growl
// ===============================

TestObject growlEnviado = new TestObject()
growlEnviado.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//div[contains(@class,'alert-success') and contains(.,'Enviado')]"
)

WebUI.waitForElementVisible(growlEnviado, 10)
WebUI.comment("✔ Growl 'Enviado' visible")

// ===============================
// 13) Cerrar Growl
// ===============================

TestObject btnCerrarGrowl = new TestObject()
btnCerrarGrowl.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[contains(@class,'growl')]//button[contains(@class,'btn-close')]"
)

WebUI.waitForElementClickable(btnCerrarGrowl, 10)
WebUI.click(btnCerrarGrowl)

WebUI.comment("✔ Growl cerrado correctamente")
