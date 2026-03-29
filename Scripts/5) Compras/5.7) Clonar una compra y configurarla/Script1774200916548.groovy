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

// ==========================================
// 🚀 EJECUCIÓN PRINCIPAL: CLONACIÓN Y CONFIGURACIÓN
// ==========================================

// 0) Acceder a compras
WebUI.callTestCase(findTestCase('5) Compras/5.1) Abrir Solapa Compra'), [:], FailureHandling.STOP_ON_FAILURE)

// --- Búsqueda y Clonación ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.1) EXTRAER CÓDIGO AL AZAR Y PROBAR BUSCADOR'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.2) ACCEDER AL DETALLE DE LA LICITACION FILTRADA'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.3) PROCESO DE CLONACIÓN DE COMPRA'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.4) INGRESAR A CONFIGURACIÓN DE LA COMPRA CLONADA'), [:], FailureHandling.STOP_ON_FAILURE)
/*
// --- Datos Generales ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.5) COMPLETAR FORMULARIO DE DATOS GENERALES'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.6) VALIDAR Y CERRAR GROWL DE ÉXITO (Cambios guardados)'), [:], FailureHandling.STOP_ON_FAILURE)

// --- Configuración de Etapas ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.7) NAVEGAR A ETAPAS Y CREAR ETAPA QA'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.8) VALIDAR Y CERRAR GROWL DE ETAPA CREADA'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.9) MOUSEOVER EN ETAPA QA Y CLIC EN EDITAR'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.10) ELIMINAR LA ETAPA QA'), [:], FailureHandling.STOP_ON_FAILURE)

// --- Asociación de Requerimientos ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.11) NAVEGAR A REQUERIMIENTOS Y AGREGAR UNO NUEVO'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.12) SELECCIONAR REQUERIMIENTOS AL AZAR Y ASOCIAR'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.13) VALIDAR Y CERRAR GROWL DE ENVIADO'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.14) ELIMINAR UN REQUERIMIENTO DE LA TABLA'), [:], FailureHandling.STOP_ON_FAILURE)


// --- Configuración de Formularios de Oferta ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.15) NAVEGAR A FORMULARIOS DE OFERTA'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.16) CREAR NUEVO FORMULARIO Y LLENAR DATOS BÁSICOS'), [:], FailureHandling.STOP_ON_FAILURE)

// --- Configuración de Formularios de Evaluacion ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.17) NAVEGAR A FORMULARIOS DE EVALUACIÓN'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.18) CREAR FORMULARIO DE EVALUACION'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.19) CREAR CAMPO EN FORMULARIO DE EVALUACION'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.20) ELIMINAR FORMULARIO DE EVALUACION'), [:], FailureHandling.STOP_ON_FAILURE)
*/
// --- Configuración de Evaluadores ---
WebUI.callTestCase(findTestCase('5) Compras/5.7.21) NAVEGAR A EVALUADORES Y CREAR GRUPO'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.22) COMPLETAR NUEVO GRUPO DE EVALUADORES'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.23) ELIMINAR GRUPO DE EVALUADORES'), [:], FailureHandling.STOP_ON_FAILURE)

//Se vuelve a ejecutar para dejar evaluadores guardados
WebUI.callTestCase(findTestCase('5) Compras/5.7.21) NAVEGAR A EVALUADORES Y CREAR GRUPO'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('5) Compras/5.7.22) COMPLETAR NUEVO GRUPO DE EVALUADORES'), [:], FailureHandling.STOP_ON_FAILURE) 
