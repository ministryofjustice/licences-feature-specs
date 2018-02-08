package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ReportingInstructionsPage extends Page {

    static at = {
        browser.currentUrl.contains('/reporting/')
    }

    static content = {
        header { module(HeaderModule) }
    }
}
