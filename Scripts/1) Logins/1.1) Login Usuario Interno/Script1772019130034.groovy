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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

WebUI.openBrowser('')

WebUI.navigateToUrl('https://staging.proveedores.intiza.com/')

WebUI.setText(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Procurement/input_Usuario'), GlobalVariable.usuario)

WebUI.setEncryptedText(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Procurement/input_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Procurement/btn_Confirmar'))

WebUI.click(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Dashboard/Selector de Empresas'))

// ===============================
// CLIC EN "Empresa QA"
// ===============================
TestObject opcionEmpresaQA = new TestObject()
opcionEmpresaQA.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//ul[contains(@class,'dropdown-menu')]//a[normalize-space()='Empresa QA']"
)

WebUI.waitForElementClickable(opcionEmpresaQA, 10)
WebUI.click(opcionEmpresaQA)


WebUI.verifyElementText(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Proveedores/toastify_Verde'), 
    'Ahora trabajando para Empresa QA')

WebUI.click(findTestObject('Object Repository/1.1) Login Usuario Interno/Page_Proveedores/btn_CerrarToastify'))

