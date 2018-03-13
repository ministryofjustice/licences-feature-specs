package uk.gov.justice.digital.hmpps.licences.pages.eligibility

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.AddressDetailsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.OccupierDetailsModule
import uk.gov.justice.digital.hmpps.licences.modules.ResidentDetailsModule

class ProposedAddressConfirmPage extends Page {

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {

        header { module(HeaderModule) }

        address { module(AddressDetailsModule) }
        occupier { module(OccupierDetailsModule) }
        residents { module(ResidentDetailsModule) }

        editDetails { $("#editLink") }
    }
}
