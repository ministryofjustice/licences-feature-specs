package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressCurfewAddressPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/curfewAddress/')
    }

    static content = {
        header { module(HeaderModule) }

        electricityRadios { $(name: "electricity").module(RadioButtons) }
        cautionedRadios { $(name: "cautionedAgainstResident").module(RadioButtons) }

        address1 { $("#addressLine1") }
        address2 { $("#addressLine2") }
        town { $("#addressTown") }
        postCode { $("#postCode") }
        telephone { $("#telephone") }

        occupierName { $("#occupierName") }
        occupierAge { $("#occupierAge") }
        occupierRelation { $("#occupierRelationship") }

        otherResidents { $(".otherResident") }
    }
}
