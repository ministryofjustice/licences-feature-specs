package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CurfewAddressReviewPage extends Page {

    static at = {
        browser.currentUrl.contains('/curfewAddressReview/')
    }

    static content = {
        header { module(HeaderModule) }

        street(required: false) { $("#address1") }
        town(required: false) { $("#addressTown") }
        postCode(required: false) { $("#addressPostCode") }

        landlordConsentRadios { $(name: "consent").module(RadioButtons) }
        manageSafelyRadios { $(name: "deemedSafe").module(RadioButtons) }
    }
}
