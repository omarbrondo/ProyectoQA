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
        CustomKeywords.'proveedores.NavegacionProv.irAprobacionesPendientes'()

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
}
