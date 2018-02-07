package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CurfewAddressReviewPage extends Page {

    static at = {
        browser.currentUrl.contains('/curfewAddressReview/')
    }

    static content = {
        header { module(HeaderModule) }
    }
}
