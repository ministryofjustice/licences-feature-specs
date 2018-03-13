package uk.gov.justice.digital.hmpps.licences.pages.review

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ReviewReportingPage extends Page {

    static url = '/hdc/review/reporting'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }
    }
}
