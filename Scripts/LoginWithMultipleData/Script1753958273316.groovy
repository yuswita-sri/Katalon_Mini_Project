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

//Daftar akun untuk login
def accounts = [[('username') : 'John Doe', ('password') : 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM', ('encrypted') : true] //valid
    , [('username') : 'wrongUser', ('password') : 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM', ('encrypted') : true] // salah user
    , [('username') : 'John Doe', ('password') : 'wrongPassword', ('encrypted') : false]]

for (int i = 0; i < accounts.size(); i++) {
    WebUI.openBrowser('')

    WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

    WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/Humberger_CURA Healthcare_menu'))

    WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/humberger_link_Login'))

    WebUI.setText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Username_username'), accounts[i].username)

    if (accounts[i].encrypted) {
        WebUI.setEncryptedText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Password_password'), 
            accounts[i].password)
    } else {
        WebUI.setText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Password_password'), accounts[
            i].password)
    }
    
    WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/button_Login'))

    //	Verifikasi hasil login
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_CURA Healthcare Service/Title_Make_Appointment'), 
        5, FailureHandling.OPTIONAL)) {
        println('Login berhasil untuk akun : ' + accounts[i].username) //		ambil eror message
    } else {
        String currentUrl = WebUI.getUrl()

        switch (currentUrl) {
            case ~('.*profile\\.php.*') :
                println('Login gagal tapi masuk ke profile page')

                break
            case ~('.*login\\.php.*') :
                println('Login gagal, tapi masuk ke halaman login untuk user :' + accounts[i].username)

                break
            default:
                println('Tidak dikenali: ' + currentUrl)}
    }
    
    WebUI.closeBrowser()
}

//WebUI.setEncryptedText(findTestObject('Page_CURA Healthcare Service/input_Password_password'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')

