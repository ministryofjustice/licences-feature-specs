package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class StandardConditionsPage extends Page {

    static at = {
        browser.currentUrl.contains('/standardConditions/')
    }

    static content = {
        header { module(HeaderModule) }
    }
}
