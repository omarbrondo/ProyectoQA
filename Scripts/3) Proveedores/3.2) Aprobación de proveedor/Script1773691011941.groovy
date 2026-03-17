import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling

// LOGIN
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// 1) Gestor aprueba
CustomKeywords.'proveedores.WorkflowProv.aprobarProveedor'()

// 2) Admin rechaza
CustomKeywords.'proveedores.WorkflowProv.rechazarProveedor'()

// 3) Gestor vuelve a aprobar
CustomKeywords.'proveedores.WorkflowProv.aprobarProveedor'()

// 4) Admin aprueba definitivamente
CustomKeywords.'proveedores.WorkflowProv.aprobarProveedor'()

