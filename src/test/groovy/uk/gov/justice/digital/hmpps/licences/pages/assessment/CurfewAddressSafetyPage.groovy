package uk.gov.justice.digital.hmpps.licences.pages.assessment

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CurfewAddressSafetyPage extends Page {

    static url = '/hdc/curfew/addressSafety'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }

        street(required: false) { $("#preferred-address1") }
        town(required: false) { $("#preferred-addressTown") }
        postCode(required: false) { $("#preferred-addressPostCode") }

        manageSafelyRadios { $(name: "deemedSafe").module(RadioButtons) }
        reason(required: false) { $("#reason") }

    }
}
