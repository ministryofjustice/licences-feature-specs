package uk.gov.justice.digital.hmpps.licences.pages

import geb.Browser
import geb.Page
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class AdditionalConditionsPage extends Page{

    static at = {
        browser.currentUrl.contains('/additionalConditions/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        standardConditionsLink { $('a', href: contains('standard')) }

        noAdditionalConditions { $('a', text: 'No additional conditions required') }

        selectCondition { conditionId ->

            WebElement element = driver.findElement(By.id('check-' + conditionId))

            Browser.drive {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", element)
            }

            $('label', for: 'check-' + conditionId).click()
        }
    }

}
