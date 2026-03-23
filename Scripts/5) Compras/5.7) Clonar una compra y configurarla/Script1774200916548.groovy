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


// 0) Acceder a compras
WebUI.callTestCase(findTestCase('5) Compras/5.1) Abrir Solapa Compra'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// 4) EXTRAER CÓDIGO AL AZAR Y PROBAR BUSCADOR
// ===============================

TestObject tablaCompras = new TestObject('tablaCompras')
tablaCompras.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='tendering-pager']/tbody")
WebUI.waitForElementVisible(tablaCompras, 10)

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> filasCompras = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

String codigoBuscado = ""

if (filasCompras.size() > 0) {
	Random rand = new Random()
	int indiceAlAzar = rand.nextInt(filasCompras.size())
	WebElement filaSeleccionada = filasCompras.get(indiceAlAzar)
	WebElement celdaCodigo = filaSeleccionada.findElement(By.xpath("./td[1]//span"))
	codigoBuscado = celdaCodigo.getText().trim()
	WebUI.comment("✔ Se seleccionó al azar el Código de Compra: " + codigoBuscado)
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("La tabla de compras está vacía.")
}

// 4.3) Interactuar con el buscador
TestObject inputBuscador = new TestObject('inputBuscador')
inputBuscador.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'keyword-filter')]")
TestObject btnBuscar = new TestObject('btnBuscar')
btnBuscar.addProperty("xpath", ConditionType.EQUALS, "//button[contains(@class, 'btn-keyword-filter')]")

WebUI.setText(inputBuscador, codigoBuscado)
WebUI.click(btnBuscar)
WebUI.delay(3)

// 4.4) Validamos el resultado
List<WebElement> filasDespuesFiltro = driver.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))
if (filasDespuesFiltro.size() == 0) {
	throw new com.kms.katalon.core.exception.StepFailedException("La búsqueda falló para el código " + codigoBuscado)
}


// ===============================
// 6) VALIDAR RESULTADO Y GUARDAR EN PROFILE
// ===============================

WebDriver driverFiltro = DriverFactory.getWebDriver()
List<WebElement> filasFiltroAvanzado = driverFiltro.findElements(By.xpath("//table[@data-pagerid='tendering-pager']/tbody/tr"))

if (filasFiltroAvanzado.size() > 0) {
	WebElement celdaResultadoFiltro = filasFiltroAvanzado.get(0).findElement(By.xpath("./td[1]//span"))
	String codigoResultadoFiltro = celdaResultadoFiltro.getText().trim()

	if (codigoResultadoFiltro.equals(codigoBuscado)) {
		// --- AQUÍ GUARDAMOS EL VALOR EN EL PROFILE ---
		GlobalVariable.CodigoLicitacion = codigoResultadoFiltro
		
		WebUI.comment("✔ ÉXITO: El código " + codigoResultadoFiltro + " se guardó en GlobalVariable.CodigoLicitacion")
	} else {
		throw new com.kms.katalon.core.exception.StepFailedException("El filtro falló: se esperaba " + codigoBuscado)
	}
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("La tabla quedó vacía.")
}

// ===============================
// 7) ACCEDER AL DETALLE DE LA LICITACIÓN FILTRADA
// ===============================

// Creamos un objeto dinámico que busca el TD con clase 'is-link'
// que contenga el código guardado en nuestro Profile
TestObject registroLicitacion = new TestObject('registroLicitacion')
registroLicitacion.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//table[@data-pagerid='tendering-pager']//tbody//td[contains(@class, 'is-link') and .//span[normalize-space()='${GlobalVariable.CodigoLicitacion}']]"
)

// 7.1) Esperamos que el registro sea visible (el filtro ya debería estar aplicado)
WebUI.waitForElementVisible(registroLicitacion, 10)

// 7.2) Hacemos clic para ingresar al detalle
WebUI.click(registroLicitacion)

WebUI.comment("✔ Se hizo clic en el registro " + GlobalVariable.CodigoLicitacion + " para acceder al detalle.")

// 7.3) Esperamos que cargue la página de detalles
WebUI.waitForPageLoad(10)
WebUI.delay(2)


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



// ===============================
// 9) INGRESAR A CONFIGURACIÓN DE LA COMPRA CLONADA
// ===============================

// --- (Opcional) TRUCO: Clic neutro para cerrar menús superpuestos de usuario ---
TestObject clicNeutro = new TestObject('clicNeutro')
clicNeutro.addProperty("xpath", ConditionType.EQUALS, "//strong[normalize-space(text())='SIN INICIAR']")
// Usamos OPTIONAL por si el estado cambió o no está visible, que no rompa el test
WebUI.click(clicNeutro, FailureHandling.OPTIONAL)
WebUI.delay(1)


// --- 9.1) Clic en el menú Ellipsis ---
TestObject btnMenuEllipsisConfig = new TestObject('btnMenuEllipsisConfig')
btnMenuEllipsisConfig.addProperty("id", ConditionType.EQUALS, "menu-ellipsis")

WebUI.waitForElementClickable(btnMenuEllipsisConfig, 10)
WebUI.click(btnMenuEllipsisConfig)


// --- 9.2) Elegir la opción "Configuración" ---
TestObject opcionConfiguracion = new TestObject('opcionConfiguracion')
opcionConfiguracion.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'show')]//a[contains(@class, 'text-secondary') and contains(normalize-space(.), 'Configuración')]")

WebUI.waitForElementPresent(opcionConfiguracion, 5)

// ¡La vieja confiable! Clic con JS para saltarnos el bloqueo de la UI
WebElement elConfig = WebUI.findWebElement(opcionConfiguracion)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elConfig))

WebUI.comment("✔ Se seleccionó 'Configuración'. Entrando a la vista de edición...")


// --- 9.3) Esperar a que cargue la nueva pantalla ---
WebUI.waitForPageLoad(15)
WebUI.delay(2) // Breve pausa para que termine de renderizar el formulario
