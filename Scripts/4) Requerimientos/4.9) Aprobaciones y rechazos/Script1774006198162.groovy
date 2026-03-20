import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.WebElement

// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)


import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory

// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ REQUERIMIENTOS
// ===============================
TestObject menuReqNav = new TestObject('menuReqNav')
menuReqNav.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//li[contains(@class,'nav-item') and contains(@class,'dropdown')]//a[contains(@class,'nav-link') and .//span[normalize-space()='Requerimientos']]"
)

WebUI.waitForElementVisible(menuReqNav, 10)
WebUI.mouseOver(menuReqNav)
WebUI.delay(1)


// ===============================
// 2) FORZAR APERTURA DEL DROPDOWN (MISMO QUE ANTES)
// ===============================
String jsOpenReqNav = """
document.querySelectorAll("li.nav-item.dropdown.dropend").forEach(function(li){
    var span = li.querySelector("span.w-md.text-truncate");
    if(span && span.innerText.trim() === "Requerimientos"){
        var menu = li.querySelector(".dropdown-menu");
        if(menu){
            menu.classList.add("show");
            menu.style.display = "block";
        }
    }
});
"""
WebUI.executeJavaScript(jsOpenReqNav, null)
WebUI.delay(1)


// ===============================
// 3) CLICK EN "Aprobaciones pendientes" VIA JS (IGNORANDO BADGE)
// ===============================
TestObject opcionAprobPend = new TestObject('opcionAprobPend')
opcionAprobPend.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//div[contains(@class,'dropdown-menu')]//a[contains(@class,'dropdown-item') and contains(normalize-space(.),'Aprobaciones pendientes')]"
)

WebUI.waitForElementVisible(opcionAprobPend, 10)

// CLICK REAL VIA JAVASCRIPT (evita overlay invisible)
WebUI.executeJavaScript(
    "arguments[0].click();",
    Arrays.asList(WebUI.findWebElement(opcionAprobPend))
)

WebUI.comment("✔ Se ingresó correctamente a Aprobaciones pendientes")
