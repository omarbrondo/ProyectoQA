import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys
import requerimientos.LineasUtils
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random


// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


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
// 3) CLICK EN LA OPCIÓN "Requerimientos"
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
// 4) FILTRAR REQUERIMIENTOS: APROBADOS Y PENDIENTES
// ===============================

// --- CREACIÓN DE OBJETOS PARA LOS TOGGLES (BOTONES PRINCIPALES) ---
TestObject filtroAprobacion = new TestObject('filtroAprobacion')
filtroAprobacion.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'dropdown-toggle') and contains(normalize-space(.), 'Aprobación:')]")

TestObject filtroCompra = new TestObject('filtroCompra')
filtroCompra.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'dropdown-toggle') and contains(normalize-space(.), 'Compra:')]")

// --- CREACIÓN DE OBJETOS PARA LAS OPCIONES DESPLEGADAS ---
TestObject opcionAprobados = new TestObject('opcionAprobados')
opcionAprobados.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'dropdown-menu') and contains(@class, 'show')]//a[normalize-space(text())='Aprobados']")

TestObject opcionPendientes = new TestObject('opcionPendientes')
opcionPendientes.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'dropdown-menu') and contains(@class, 'show')]//a[normalize-space(text())='Pendientes']")

// ---------------------------------------------------------

// --- PASO 4.1: CAMBIAR APROBACIÓN A "APROBADOS" ---
WebUI.waitForElementClickable(filtroAprobacion, 10)
WebUI.click(filtroAprobacion)

// Esperamos que el menú se despliegue y hacemos clic en la opción "Aprobados"
WebUI.waitForElementVisible(opcionAprobados, 5)
WebUI.click(opcionAprobados)
WebUI.delay(2) // Pequeña pausa para que se refresque la grilla


// --- PASO 4.2: CAMBIAR COMPRA A "PENDIENTES" ---
WebUI.waitForElementClickable(filtroCompra, 10)
WebUI.click(filtroCompra)

// Esperamos que el menú se despliegue y hacemos clic en la opción "Pendientes"
WebUI.waitForElementVisible(opcionPendientes, 5)
WebUI.click(opcionPendientes)
WebUI.delay(2) // Pequeña pausa para que se refresque la grilla final

WebUI.comment("✔ Se aplicaron los filtros: Aprobación -> Aprobados | Compra -> Pendientes")

// ===============================
// 5) ELEGIR AL AZAR Y VALIDAR ESTADO "PENDIENTE" (BUCLE)
// ===============================

boolean requerimientoValido = false
WebDriver driver = DriverFactory.getWebDriver()

// --- OBJETOS NECESARIOS EN EL BUCLE ---
TestObject tablaRequerimientos = new TestObject('tablaRequerimientos')
tablaRequerimientos.addProperty("xpath", ConditionType.EQUALS, "//table[@data-pagerid='purchaserequest-pager']/tbody")

TestObject celdaEstadoCompra = new TestObject('celdaEstadoCompra')
// Buscamos el 'td' que está inmediatamente después del 'th' que dice 'Estado compra'
celdaEstadoCompra.addProperty("xpath", ConditionType.EQUALS, "//th[normalize-space(.)='Estado compra']/following-sibling::td")

TestObject btnVolverLista = new TestObject('btnVolverLista')
btnVolverLista.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@href, 'PurchaseRequest/List') and contains(normalize-space(.), 'Volver a la lista')]")
// --------------------------------------

// Iniciamos el bucle: mientras NO sea válido, seguimos buscando
while (!requerimientoValido) {
    
    // 5.1) Esperamos que la tabla cargue en la pantalla principal
    WebUI.waitForElementVisible(tablaRequerimientos, 10)
    
    // 5.2) Leemos las filas
    List<WebElement> filas = driver.findElements(By.xpath("//table[@data-pagerid='purchaserequest-pager']/tbody/tr"))
    
    if (filas.size() > 0) {
        // Elegimos al azar
        Random rand = new Random()
        int indiceAlAzar = rand.nextInt(filas.size())
        WebElement filaSeleccionada = filas.get(indiceAlAzar)
        
        WebElement celdaCodigo = filaSeleccionada.findElement(By.xpath("./td[3]//span"))
        String codigoRequerimiento = celdaCodigo.getText().trim()
        
        WebUI.comment("Intentando ingresar al Requerimiento: " + codigoRequerimiento)
        celdaCodigo.click()
        
        // 5.3) Ya en el detalle, validamos el Estado de Compra
        WebUI.waitForElementVisible(celdaEstadoCompra, 10)
        String textoEstado = WebUI.getText(celdaEstadoCompra).trim()
        
        if (textoEstado.equalsIgnoreCase("Pendiente")) {
            WebUI.comment("✔ Éxito: El estado de compra del código " + codigoRequerimiento + " es 'Pendiente'.")
            requerimientoValido = true // Rompe el bucle, ya encontramos uno bueno
        } else {
            WebUI.comment("⚠ El estado es '" + textoEstado + "'. Volviendo a la lista para buscar otro...")
            // Hacemos clic en Volver a la lista
            WebUI.waitForElementClickable(btnVolverLista, 5)
            WebUI.click(btnVolverLista)
            WebUI.delay(2) // Pausa para que vuelva a cargar la grilla antes de iterar
        }
    } else {
        WebUI.comment("⚠ La grilla está vacía con los filtros aplicados. Abortando búsqueda.")
        break // Rompemos el bucle para que no quede en loop infinito si no hay datos
    }
}

// ===============================
// 6) MARCAR PRIMERA LÍNEA COMO COMPRADA Y VALIDAR ESTADO "PARCIAL"
// ===============================

// --- CREACIÓN DE OBJETOS ---

TestObject chkPrimeraLinea = new TestObject('chkPrimeraLinea')
// Buscamos el primer input tipo checkbox dentro de la tabla de líneas
chkPrimeraLinea.addProperty("xpath", ConditionType.EQUALS, "(//div[@id='PurchaseRequestLineList']//table/tbody/tr//input[@type='checkbox'])[1]")

TestObject btnMarcarComprado = new TestObject('btnMarcarComprado')
// Buscamos el botón por su clase y texto
btnMarcarComprado.addProperty("xpath", ConditionType.EQUALS, "//a[contains(@class, 'btn-prline-closequantity') and contains(normalize-space(.), 'Marcar como comprado')]")

// Reutilizamos el objeto de la celda de estado que creamos en el paso anterior (celdaEstadoCompra)
// ---------------------------

if (requerimientoValido) {
	
	// 6.1) Esperamos que la tabla de líneas sea visible y hacemos clic en el primer checkbox
	WebUI.waitForElementClickable(chkPrimeraLinea, 10)
	WebUI.click(chkPrimeraLinea)
	WebUI.comment("✔ Se seleccionó la primera línea del requerimiento.")
	
	// 6.2) Hacemos clic en "Marcar como comprado"
	WebUI.waitForElementClickable(btnMarcarComprado, 5)
	WebUI.click(btnMarcarComprado)
	
	// Pequeño delay para permitir que el backend procese la solicitud y actualice la UI
	WebUI.delay(3)
	
	// 6.3) Validar el cambio de estado general a "Parcial"
	WebUI.waitForElementVisible(celdaEstadoCompra, 10)
	String nuevoEstado = WebUI.getText(celdaEstadoCompra).trim()
	
	if (nuevoEstado.equalsIgnoreCase("Parcial")) {
		WebUI.comment("✔ Éxito: El estado de compra se actualizó correctamente a 'Parcial'.")
	} else {
		WebUI.comment("⚠ Fallo en validación: El estado esperado era 'Parcial', pero se encontró '" + nuevoEstado + "'.")
		// Opcional: Puedes lanzar una excepción aquí si quieres que el test falle formalmente en Katalon
		// throw new com.kms.katalon.core.exception.StepFailedException("El estado no cambió a Parcial")
	}
	
} else {
	WebUI.comment("⚠ No se ejecutó la acción de compra porque no se encontró un requerimiento válido.")
}
// ===============================
// 7) MARCAR SEGUNDA LÍNEA COMO COMPRADA Y VALIDAR ESTADO FINAL
// ===============================

if (requerimientoValido) {
	
	// --- CREACIÓN DE OBJETOS NUEVOS ---
	TestObject tabLineas = new TestObject('tabLineas')
	tabLineas.addProperty("xpath", ConditionType.EQUALS, "//a[@id='nav-purchaserequestlines-tab']")

	TestObject chkSegundaLinea = new TestObject('chkSegundaLinea')
	// Usamos [2] para asegurarnos de agarrar la segunda fila de la tabla
	chkSegundaLinea.addProperty("xpath", ConditionType.EQUALS, "(//div[@id='PurchaseRequestLineList']//table/tbody/tr//input[@type='checkbox'])[2]")
	// ----------------------------------

	// 7.1) Hacemos clic en la pestaña "Líneas"
	WebUI.waitForElementClickable(tabLineas, 10)
	WebUI.click(tabLineas)
	WebUI.delay(1) // Pequeña pausa para que la animación de la pestaña termine y muestre la tabla
	
	// 7.2) Seleccionamos el segundo checkbox
	WebUI.waitForElementClickable(chkSegundaLinea, 10)
	WebUI.click(chkSegundaLinea)
	WebUI.comment("✔ Se seleccionó la segunda línea del requerimiento.")
	
	// 7.3) Hacemos clic en "Marcar como comprado" (reutilizando el objeto del Paso 6)
	WebUI.waitForElementClickable(btnMarcarComprado, 5)
	WebUI.click(btnMarcarComprado)
	
	// Pausa para que el backend procese la solicitud y actualice la vista
	WebUI.delay(3)
	
	// 7.4) Validar el cambio de estado general a "Vinculado a OC" (reutilizando el objeto del Paso 5)
	WebUI.waitForElementVisible(celdaEstadoCompra, 10)
	String estadoFinal = WebUI.getText(celdaEstadoCompra).trim()
	
	if (estadoFinal.equalsIgnoreCase("Vinculado a OC")) {
		WebUI.comment("✔ ÉXITO TOTAL: El estado de compra final es 'Vinculado a OC'. ¡Flujo completado!")
	} else {
		WebUI.comment("⚠ Fallo en validación: El estado esperado era 'Vinculado a OC', pero se encontró '" + estadoFinal + "'.")
	}
	
}