package proveedores

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class NavegacionProv {

    @Keyword
    def irAprobacionesPendientes() {

        // 1) MouseOver en Proveedores
        TestObject menuProveedoresProv = new TestObject()
        menuProveedoresProv.addProperty(
            "xpath",
            ConditionType.EQUALS,
            "//a[contains(@class,'nav-link') and contains(.,'Proveedores')]"
        )

        WebUI.waitForElementVisible(menuProveedoresProv, 10)
        WebUI.mouseOver(menuProveedoresProv)

        // 2) Esperar menú desplegado
        TestObject dropdownProveedoresProv = new TestObject()
        dropdownProveedoresProv.addProperty(
            "xpath",
            ConditionType.EQUALS,
            "//div[contains(@class,'dropdown-menu')]//h6[contains(.,'Proveedores')]"
        )

        WebUI.waitForElementVisible(dropdownProveedoresProv, 10)

        // 3) Clic en Aprobaciones pendientes
        TestObject opcionAprobPendProv = new TestObject()
        opcionAprobPendProv.addProperty(
            "xpath",
            ConditionType.EQUALS,
            "//a[contains(@class,'dropdown-item') and contains(.,'Aprobaciones pendientes')]"
        )

        WebUI.waitForElementClickable(opcionAprobPendProv, 10)
        WebUI.click(opcionAprobPendProv)
    }
}
