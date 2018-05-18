package uk.gov.justice.digital.hmpps.licences.pages.pdf

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.OffenderDetailsModule

class CreatePdfStartPage extends Page {

    static url = '/hdc/pdf/view'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {

        missingDetails(required: false) { $('.missing') }
    }
}
