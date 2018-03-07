package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class AddressProposedPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/addressProposed/')
    }

    static content = {
        header { module(HeaderModule) }

        decisionRadios { $(name: "decision").module(RadioButtons) }
    }
}
