import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling

// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ COMPRAS
// ===============================
TestObject menuComprasNav = new TestObject('menuComprasNav')
menuComprasNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//li[contains(@class,'nav-item') and contains(@class,'dropdown')]//a[contains(@class,'nav-link') and .//span[normalize-space()='Compras']]"
)

WebUI.waitForElementVisible(menuComprasNav, 10)
WebUI.mouseOver(menuComprasNav)
WebUI.delay(1)


// ===============================
// 2) FORZAR APERTURA DEL DROPDOWN (Evita fallos de Bootstrap)
// ===============================
String jsOpenComprasNav = """
    document.querySelectorAll("li.nav-item.dropdown.dropend").forEach(function(li){
        var span = li.querySelector("span.w-md.text-truncate");
        if(span && span.innerText.trim() === "Compras"){
            var menu = li.querySelector(".dropdown-menu");
            if(menu){
                menu.classList.add("show");
                menu.style.display = "block";
            }
        }
    });
"""
WebUI.executeJavaScript(jsOpenComprasNav, null)
WebUI.delay(1)


// ===============================
// 3) CLICK EN LA OPCIÓN "Compras" (Submenú)
// ===============================
TestObject opcionComprasSub = new TestObject('opcionComprasSub')
opcionComprasSub.addProperty(
    "xpath",
    ConditionType.EQUALS,
    // Usamos el href "Tendering/List" para que sea exacto
    "//div[contains(@class,'dropdown-menu')]//a[contains(@class,'dropdown-item') and contains(@href, 'Tendering/List')]"
)

WebUI.waitForElementClickable(opcionComprasSub, 10)
WebUI.click(opcionComprasSub)

WebUI.comment("✔ Se ingresó correctamente al módulo Compras")