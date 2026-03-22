import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.Random
import org.openqa.selenium.WebElement
import java.util.Arrays


// ===============================
// 0) LOGIN
// ===============================
WebUI.callTestCase(findTestCase('1) Logins/1.1) Login Usuario Interno'), [:], FailureHandling.STOP_ON_FAILURE)

// ===============================
// 1) MOUSEOVER SOBRE EL MENÚ "REQUERIMIENTOS"
// ===============================
TestObject menuRequerimientos = new TestObject('menuRequerimientos')
menuRequerimientos.addProperty(
    "xpath", 
    ConditionType.EQUALS, 
    "//li[contains(@class, 'dropdown')]//a[contains(@class, 'nav-link') and .//span[normalize-space()='Requerimientos']]"
)

WebUI.waitForElementVisible(menuRequerimientos, 10)
WebUI.mouseOver(menuRequerimientos)
// Pequeño delay para darle tiempo a la animación del dropdown de Bootstrap
WebUI.delay(1) 


