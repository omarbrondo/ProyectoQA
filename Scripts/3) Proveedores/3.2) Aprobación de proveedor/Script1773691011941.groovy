import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling

// LOGIN
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


// ===============================
// APROBACIÓN NIVEL 1
// ===============================
CustomKeywords.'proveedores.WorkflowProv.aprobarProveedor'()


// ===============================
// APROBACIÓN NIVEL 2
// ===============================
CustomKeywords.'proveedores.WorkflowProv.aprobarProveedor'()
