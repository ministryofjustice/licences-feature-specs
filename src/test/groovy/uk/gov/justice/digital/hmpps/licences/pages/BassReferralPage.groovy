package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class BassReferralPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/bassReferral/')
    }

    static content = {
        header { module(HeaderModule) }

        decisionRadios { $(name: "decision").module(RadioButtons) }

        proposedTownInput(required: false) { $("#proposedTown") }

        proposedCountyInput(required: false) { $("#proposedCounty") }
    }
}
