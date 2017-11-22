package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
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
            $('label', for: 'check-' + conditionId).click()
        }
    }

}
