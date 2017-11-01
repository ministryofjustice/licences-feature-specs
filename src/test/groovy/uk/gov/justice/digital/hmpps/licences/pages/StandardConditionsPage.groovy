package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class StandardConditionsPage extends Page{

    static at = {
        browser.currentUrl.contains('/additionalConditions/standard/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        backLink { $('a', text: 'Back') }
    }

}
