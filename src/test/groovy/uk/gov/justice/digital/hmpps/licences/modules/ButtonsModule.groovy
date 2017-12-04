package uk.gov.justice.digital.hmpps.licences.modules

import geb.Browser
import geb.Module
import org.openqa.selenium.JavascriptExecutor


class ButtonsModule extends Module {

    static content = {
        continueButton(required: false) { $('#continueBtn') }
        backButton(required: false) { $('#backBtn') }

        clickContinue {
            Browser.drive {
                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, document.body.scrollHeight)")
            }
            continueButton.click()
        }

        clickBack {
            Browser.drive {
                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, document.body.scrollHeight)")
            }
            backButton.click()
        }
    }
}
