package uk.gov.justice.digital.hmpps.licences.pages.eligibility

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressConfirmPage extends Page {

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }
        addressDetails { $("#preferred-addressDetails") }
        altAddressDetails { $("#alternative-addressDetails") }
        editDetails { $("#editLink") }
    }
}
