package proveedores

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable

class WorkflowProv {

	@Keyword
	def aprobarProveedor() {

		// ===============================
		// 1) IR A APROBACIONES PENDIENTES
		// ===============================
		new proveedores.NavegacionProv().irAprobacionesPendientes()


		// ===============================
		// 2) BUSCAR LA FILA DEL PROVEEDOR
		// ===============================
		TestObject filaAprobacionProv = new TestObject()
		filaAprobacionProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]"
				)

		WebUI.waitForElementVisible(filaAprobacionProv, 10)

		// ===============================
		// 3) MOUSEOVER EN LA CELDA DEL ÍCONO
		// ===============================
		TestObject celdaIconoProv = new TestObject()
		celdaIconoProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//td[contains(@class,'text-end')]"
				)

		WebUI.waitForElementVisible(celdaIconoProv, 10)
		WebUI.mouseOver(celdaIconoProv)

		// ===============================
		// 4) CLIC EN EL ÍCONO DE FLECHA
		// ===============================
		TestObject iconoAbrirWorkflowProv = new TestObject()
		iconoAbrirWorkflowProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//a[contains(@href,'/WorkflowLink/Review')]"
				)

		WebUI.waitForElementClickable(iconoAbrirWorkflowProv, 10)
		WebUI.click(iconoAbrirWorkflowProv)

		// ===============================
		// 5) CLIC EN BOTÓN "Aprobar"
		// ===============================
		TestObject btnAprobarProv = new TestObject()
		btnAprobarProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//button[@id='btn-approve-true' and contains(.,'Aprobar')]"
				)

		WebUI.waitForElementClickable(btnAprobarProv, 10)
		WebUI.click(btnAprobarProv)

		// ===============================
		// 6) VALIDAR Y CERRAR GROWL
		// ===============================
		TestObject growlCambiosGuardadosProv = new TestObject()
		growlCambiosGuardadosProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//div[contains(@class,'alert-success') and contains(.,'Cambios guardados')]"
				)

		WebUI.waitForElementVisible(growlCambiosGuardadosProv, 10)

		TestObject btnCerrarGrowlCambiosProv = new TestObject()
		btnCerrarGrowlCambiosProv.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//div[contains(@class,'alert-success') and contains(.,'Cambios guardados')]//button[contains(@class,'btn-close')]"
				)

		WebUI.waitForElementClickable(btnCerrarGrowlCambiosProv, 10)
		WebUI.click(btnCerrarGrowlCambiosProv)
	}


	@Keyword
def rechazarProveedor() {

    // 1) Ir a Aprobaciones Pendientes
    new proveedores.NavegacionProv().irAprobacionesPendientes()

    // 2) Buscar fila del proveedor
    TestObject filaAprobacionProv = new TestObject()
    filaAprobacionProv.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]"
    )

    WebUI.waitForElementVisible(filaAprobacionProv, 10)

    // 3) Mouseover en celda del ícono
    TestObject celdaIconoProv = new TestObject()
    celdaIconoProv.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//td[contains(@class,'text-end')]"
    )

    WebUI.waitForElementVisible(celdaIconoProv, 10)
    WebUI.mouseOver(celdaIconoProv)

    // 4) Clic en ícono de flecha
    TestObject iconoAbrirWorkflowProv = new TestObject()
    iconoAbrirWorkflowProv.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//table//tr[td[contains(normalize-space(),'" + GlobalVariable.NombreProveedor + "')]]//a[contains(@href,'/WorkflowLink/Review')]"
    )

    WebUI.waitForElementClickable(iconoAbrirWorkflowProv, 10)
    WebUI.click(iconoAbrirWorkflowProv)

    // 5) Clic en botón "Rechazar"
    TestObject btnRechazarProv = new TestObject()
    btnRechazarProv.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//a[contains(@class,'btn-danger') and contains(.,'Rechazar')]"
    )

    WebUI.waitForElementClickable(btnRechazarProv, 10)
    WebUI.click(btnRechazarProv)

    // 6) Escribir motivo en el modal
    TestObject textareaMotivo = new TestObject()
    textareaMotivo.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//textarea[@id='motive']"
    )

    new utils.TextoUtils().escribirTextoDinamico(textareaMotivo, "Motivo de rechazo QA")

    // 7) Clic en botón "Rechazar" del modal (via JavaScript)
    TestObject btnConfirmarRechazo = new TestObject()
    btnConfirmarRechazo.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//button[@id='btn-review-reject-display' and contains(normalize-space(),'Rechazar')]"
    )

    WebUI.waitForElementVisible(btnConfirmarRechazo, 10)
    WebUI.waitForElementClickable(btnConfirmarRechazo, 10)

    // Click por JavaScript (soluciona el problema de no-interactable)
    WebUI.executeJavaScript(
        "arguments[0].click();",
        Arrays.asList(WebUI.findWebElement(btnConfirmarRechazo))
    )

    // 8) Validar growl
    TestObject growlRechazo = new TestObject()
    growlRechazo.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[contains(@class,'alert-success') and contains(.,'Cambios guardados')]"
    )

    WebUI.waitForElementVisible(growlRechazo, 10)

    // 9) Cerrar growl
    TestObject btnCerrarGrowl = new TestObject()
    btnCerrarGrowl.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//div[contains(@class,'alert-success')]//button[contains(@class,'btn-close')]"
    )

    WebUI.waitForElementClickable(btnCerrarGrowl, 10)
    WebUI.click(btnCerrarGrowl)
}

}
