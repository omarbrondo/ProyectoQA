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
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory




//0) Acceder a compras
WebUI.callTestCase(findTestCase('5) Compras/5.3) Acceder a una compra'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// 8) NAVEGACIÓN POR PESTAÑAS DE LA LICITACIÓN
// ===============================

// Definimos los IDs de las pestañas en una lista para iterar (más limpio)
def tabs = [
	"nav-suppliers-tab",
	"nav-matrix-tab",
	"nav-evaluatormatrix-tab",
	"nav-tenderingaward-tab",
	"nav-messages-tab",
	"nav-logs-tab"
]

tabs.each { tabId ->
	// Creamos el objeto dinámico por ID
	TestObject tabObj = new TestObject(tabId)
	tabObj.addProperty("id", ConditionType.EQUALS, tabId)
	
	// Esperamos que sea cliqueable y hacemos clic
	WebUI.waitForElementClickable(tabObj, 10)
	WebUI.click(tabObj)
	
	// Extraemos el nombre para el log (opcional, para que sea prolijo)
	String nombreTab = WebUI.getText(tabObj).replaceAll(/[0-9]/, "").trim()
	WebUI.comment("✔ Navegando a la pestaña: " + nombreTab)
	
	// Pausa breve para ver la carga de la solapa
	WebUI.delay(1)
}

WebUI.comment("✔ Recorrido de pestañas finalizado correctamente.")