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


// ===============================
// 10) COMPLETAR FORMULARIO DE DATOS GENERALES
// ===============================

// --- 10.1) Cambiar el nombre de la licitación con Fecha y Hora ---
TestObject inputNombre = new TestObject('inputNombre')
// Buscamos por la clase 'form-control-xl' que es propia de este título principal
inputNombre.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'form-control-xl') and @type='text']")

String fechaActual = new Date().format('dd/MM/yyyy HH:mm:ss')
String nombreLicitacion = "Licitación QA " + fechaActual

WebUI.waitForElementVisible(inputNombre, 10)
WebUI.clearText(inputNombre)
WebUI.setText(inputNombre, nombreLicitacion)
WebUI.comment("✔ Nombre de la compra actualizado a: " + nombreLicitacion)


// --- 10.2) Seleccionar categoría al azar ---
TestObject selectCategoria = new TestObject('selectCategoria')
selectCategoria.addProperty("xpath", ConditionType.EQUALS, "//select[contains(@class, 'form-control-m')]")

// Le cambiamos el nombre a 'driverConfig' para que no choque con el 'driver' de arriba
WebDriver driverConfig = DriverFactory.getWebDriver()
List<WebElement> opcionesCategoria = driverConfig.findElements(By.xpath("//select[contains(@class, 'form-control-m')]/option"))

if (opcionesCategoria.size() > 1) {
    Random rand = new Random()
    // Generamos un índice aleatorio, saltando el 0 que es "Seleccione una opción"
    int indexAzar = rand.nextInt(opcionesCategoria.size() - 1) + 1
    WebUI.selectOptionByIndex(selectCategoria, indexAzar)
    WebUI.comment("✔ Categoría seleccionada al azar (Índice: " + indexAzar + ")")
}


// --- 10.3) Cargar un monto al azar entre 1,000,000 y 5,000,000 ---
TestObject inputMonto = new TestObject('inputMonto')
inputMonto.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@class, 'mask-numeric')]")

// Fórmula para rango: rand.nextInt((max - min) + 1) + min
int montoAzar = new Random().nextInt(4000001) + 1000000

WebUI.waitForElementVisible(inputMonto, 5)
WebUI.click(inputMonto) // Hacemos foco
// Vaciamos usando atajos de teclado para que la "máscara" de moneda no se buguee
WebUI.sendKeys(inputMonto, Keys.chord(Keys.CONTROL, "a"))
WebUI.sendKeys(inputMonto, Keys.chord(Keys.BACK_SPACE))
// Enviamos el número plano (el plugin JS se encargará de ponerle los puntos y comas)
WebUI.setText(inputMonto, montoAzar.toString())
WebUI.comment("✔ Monto actualizado a: " + montoAzar)


// --- 10.4) Verificar y agregar evaluador "Admin" si no está ---
// Usamos el 'driverConfig' que definimos recién
List<WebElement> adminPill = driverConfig.findElements(By.xpath("//div[contains(@class, 'chosen-container-multi')]//li[@class='search-choice']//span[normalize-space(text())='Admin']"))

if (adminPill.size() == 0) {
    WebUI.comment("⚠ 'Admin' no está seleccionado. Agregándolo...")
    TestObject inputChosenAdmin = new TestObject('inputChosenAdmin')
    inputChosenAdmin.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'chosen-container-multi')]//li[@class='search-field']/input")
    
    WebUI.waitForElementClickable(inputChosenAdmin, 5)
    WebUI.click(inputChosenAdmin)
    WebUI.setText(inputChosenAdmin, "Admin")
    WebUI.delay(1) // Pausa breve para que el plugin filtre las opciones
    WebUI.sendKeys(inputChosenAdmin, Keys.chord(Keys.ENTER))
    WebUI.comment("✔ Evaluador 'Admin' agregado exitosamente.")
} else {
    WebUI.comment("✔ El evaluador 'Admin' ya estaba presente.")
}

// --- 10.5) Guardar los cambios ---
TestObject btnGuardarDatos = new TestObject('btnGuardarDatos')
btnGuardarDatos.addProperty("xpath", ConditionType.EQUALS, "//button[@type='submit' and contains(@class, 'btn-submit-form') and contains(normalize-space(.), 'GUARDAR')]")

WebUI.waitForElementClickable(btnGuardarDatos, 5)
WebUI.click(btnGuardarDatos)
WebUI.comment("✔ Formulario enviado. Esperando confirmación...")


// ===============================
// 11) VALIDAR Y CERRAR GROWL DE ÉXITO ("Cambios guardados")
// ===============================
TestObject growlGuardado = new TestObject('growlGuardado')
growlGuardado.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlExito = new TestObject('btnCerrarGrowlExito')
btnCerrarGrowlExito.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlGuardado, 15)
String textoGrowlExito = WebUI.getText(growlGuardado).trim()

if (textoGrowlExito.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Se visualizó el mensaje '" + textoGrowlExito + "'.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: Se esperaba 'Cambios guardados' pero apareció: " + textoGrowlExito)
}

WebUI.waitForElementClickable(btnCerrarGrowlExito, 5)
WebUI.click(btnCerrarGrowlExito)
WebUI.comment("✔ Se cerró el growl de éxito.")

// ===============================
// 12) NAVEGAR A ETAPAS Y CREAR "ETAPA QA"
// ===============================

// --- 12.1) Clic en el menú lateral "Etapas" ---
TestObject menuEtapas = new TestObject('menuEtapas')
menuEtapas.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'nav-sidebar')]//a[contains(normalize-space(.), 'Etapas')]")

WebUI.waitForElementClickable(menuEtapas, 10)
WebUI.click(menuEtapas)
WebUI.comment("✔ Navegando a la sección 'Etapas'...")

// Esperamos que cargue la nueva vista
WebUI.waitForPageLoad(10)
WebUI.delay(2)


// --- 12.2) Clic en el botón "Nueva etapa" ---
TestObject btnNuevaEtapa = new TestObject('btnNuevaEtapa')
btnNuevaEtapa.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-edit-stage') and contains(normalize-space(.), 'Nueva etapa')]")

WebUI.waitForElementClickable(btnNuevaEtapa, 10)
WebUI.click(btnNuevaEtapa)
WebUI.comment("✔ Abriendo el modal de 'Nueva etapa'...")


// --- 12.3) Completar el nombre de la etapa en el modal ---
TestObject inputNombreEtapa = new TestObject('inputNombreEtapa')
// Como el campo tiene el id="Name", es súper directo apuntarle
inputNombreEtapa.addProperty("id", ConditionType.EQUALS, "Name")

WebUI.waitForElementVisible(inputNombreEtapa, 10)
WebUI.setText(inputNombreEtapa, "Etapa QA")
WebUI.comment("✔ Se ingresó el nombre 'Etapa QA'")


// --- 12.4) Guardar la nueva etapa ---
TestObject btnGuardarEtapa = new TestObject('btnGuardarEtapa')
btnGuardarEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'modal-content')]//button[@type='submit' and contains(@class, 'btn-primary') and contains(normalize-space(.), 'Guardar')]")

WebUI.waitForElementClickable(btnGuardarEtapa, 5)
WebUI.click(btnGuardarEtapa)
WebUI.comment("✔ Se hizo clic en 'Guardar' etapa. Esperando que se procese...")

// Damos un tiempito para que el modal se cierre y la grilla de etapas se actualice
WebUI.delay(3)


// ===============================
// 13) VALIDAR Y CERRAR GROWL DE "ETAPA CREADA"
// ===============================

TestObject growlEtapa = new TestObject('growlEtapa')
growlEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEtapa = new TestObject('btnCerrarGrowlEtapa')
btnCerrarGrowlEtapa.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEtapa, 10)
String textoGrowlEtapa = WebUI.getText(growlEtapa).trim()

if (textoGrowlEtapa.contains("Cambios guardados")) {
	WebUI.comment("✔ Confirmado: Se guardó la etapa y apareció el mensaje.")
} else {
	WebUI.comment("⚠ El mensaje fue distinto al esperado: " + textoGrowlEtapa)
}

WebUI.waitForElementClickable(btnCerrarGrowlEtapa, 5)
WebUI.click(btnCerrarGrowlEtapa)
WebUI.delay(1) // Pausa para asegurar que la UI quedó limpia


// ===============================
// 14) MOUSEOVER EN "ETAPA QA" Y CLIC EN EDITAR
// ===============================

// --- 14.1) Identificar la fila exacta que dice "Etapa QA" ---
TestObject filaEtapaQA = new TestObject('filaEtapaQA')
filaEtapaQA.addProperty("xpath", ConditionType.EQUALS, "//tr[contains(@class, 'stage-row') and .//span[normalize-space(text())='Etapa QA']]")

WebUI.waitForElementVisible(filaEtapaQA, 10)

// Hacemos el MouseOver para que se desplieguen los botones ocultos
WebUI.mouseOver(filaEtapaQA)
WebUI.delay(1) // Le damos tiempo a la animación de Bootstrap/CSS para que muestre los botones


// --- 14.2) Clic en el lapicito (Editar) ---
TestObject btnEditarEtapa = new TestObject('btnEditarEtapa')
// Apuntamos directo a la etiqueta <a> que tiene el ícono del lápiz, DENTRO de la fila de Etapa QA
btnEditarEtapa.addProperty("xpath", ConditionType.EQUALS, "//tr[contains(@class, 'stage-row') and .//span[normalize-space(text())='Etapa QA']]//a[contains(@class, 'btn-edit-stage') and .//i[contains(@class, 'fa-pen')]]")

// Para evitar cualquier fallo del Hover, inyectamos el clic con JS
WebUI.waitForElementPresent(btnEditarEtapa, 5)
WebElement elPenEditar = WebUI.findWebElement(btnEditarEtapa)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(elPenEditar))

WebUI.comment("✔ ¡Hackmate! Se hizo clic en el botón de edición de 'Etapa QA' a prueba de fallos.")


// Esperamos que el modal de edición vuelva a aparecer
WebUI.delay(2)

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


// ===============================
// 16) VALIDAR Y CERRAR GROWL DE "ELIMINADO"
// ===============================

TestObject growlEtapaEliminada = new TestObject('growlEtapaEliminada')
growlEtapaEliminada.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//div[contains(@class, 'alert-success')]")

TestObject btnCerrarGrowlEtapaEliminada = new TestObject('btnCerrarGrowlEtapaEliminada')
btnCerrarGrowlEtapaEliminada.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'growl')]//button[@data-bs-dismiss='alert']")

WebUI.waitForElementVisible(growlEtapaEliminada, 10)
String textoGrowlEliminado = WebUI.getText(growlEtapaEliminada).trim()

if (textoGrowlEliminado.contains("Eliminado")) {
	WebUI.comment("✔ Confirmado: Se eliminó la etapa y apareció el mensaje 'Eliminado'.")
} else {
	throw new com.kms.katalon.core.exception.StepFailedException("❌ ERROR: El mensaje final no fue 'Eliminado'. Se encontró: " + textoGrowlEliminado)
}

WebUI.waitForElementClickable(btnCerrarGrowlEtapaEliminada, 5)
WebUI.click(btnCerrarGrowlEtapaEliminada)
WebUI.delay(1)
WebUI.comment("✔ Flujo de Alta y Baja de Etapa QA completado con éxito.")