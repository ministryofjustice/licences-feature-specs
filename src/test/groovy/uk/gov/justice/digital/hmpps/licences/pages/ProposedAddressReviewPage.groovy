package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressReviewPage extends Page {

    static at = {
        browser.currentUrl.contains('/curfewAddressReview/')
    }

    static content = {
        header { module(HeaderModule) }

        street(required: false) { $("#preferred-address1") }
        town(required: false) { $("#preferred-addressTown") }
        postCode(required: false) { $("#preferred-addressPostCode") }

        landlordConsentRadios { $(name: "consent").module(RadioButtons) }
        electricitySupplyRadios(required: false) { $(name: "electricity").module(RadioButtons) }
        homeVisitRadios(required: false) { $(name: "homeVisitConducted").module(RadioButtons) }

        landlordConsentForm(required: false) { $("#consentForm") }
    }
}
