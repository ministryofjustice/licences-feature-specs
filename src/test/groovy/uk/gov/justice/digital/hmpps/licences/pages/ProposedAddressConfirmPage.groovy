package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressConfirmPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/confirmAddress/')
    }

    static content = {
        header { module(HeaderModule) }
        addressDetails { $("#preferred-addressDetails") }
        editDetails { $("#editLink") }
    }
}
