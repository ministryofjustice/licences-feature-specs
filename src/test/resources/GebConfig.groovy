import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities


driver = {

    // Ensure JS enabled for PhantomJS
    def capabilities = new DesiredCapabilities()
    capabilities.setJavascriptEnabled(true)

    // Use the following to run tests in headless mode
    def driverInstance = new PhantomJSDriver(capabilities)

    // Use the following to set path to chromedriver if not using linux chromedriver
    // System.setProperty('webdriver.chrome.driver', '/usr/local/bin/chromedriver')

    // Use the following path on mac if chomdriver is in applcations
    // System.setProperty('webdriver.chrome.driver', '/Applications/chromedriver')

    // Use the following to run tests with Chromedriver
    // def driverInstance = new ChromeDriver()


    driverInstance.manage().window().maximize()
    driverInstance
}

baseUrl = System.getenv('LICENCES_URI') ?: "http://localhost:3000/"

reportsDir = "build/geb-reports"
