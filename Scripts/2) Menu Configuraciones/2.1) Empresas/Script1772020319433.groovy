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

WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/2.1) Empresas/Page_Proveedores/btn_Engranaje'))

WebUI.click(findTestObject('Object Repository/2.1) Empresas/Page_Proveedores/opc_Configuracion'))

WebUI.navigateToUrl('https://staging.proveedores.intiza.com/es/Company/Index')

WebUI.verifyElementText(findTestObject('Object Repository/2.1) Empresas/Page_Config/lbl_NombreDeLaEmpresa'), 'Nombre de la empresa')

WebUI.click(findTestObject('Object Repository/2.1) Empresas/Page_Config/btn_Guardar'))

WebUI.verifyElementText(findTestObject('Object Repository/2.1) Empresas/Page_Config/toastify_SuccessVerde'), 
    'Empresa guardada correctamente')

WebUI.closeBrowser()

