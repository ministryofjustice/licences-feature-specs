package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/curfewAddress/')
    }

    static content = {
        header { module(HeaderModule) }

        electricityRadios { $(name: "preferred[electricity]").module(RadioButtons) }
        cautionedRadios { $(name: "preferred[cautionedAgainstResident]").module(RadioButtons) }

        address1 { $("#preferred-addressLine1") }
        address2 { $("#preferred-addressLine2") }
        town { $("#preferred-addressTown") }
        postCode { $("#preferred-postCode") }
        telephone { $("#preferred-telephone") }

        occupierName { $("#preferred-occupierName") }
        occupierAge { $("#preferred-occupierAge") }
        occupierRelation { $("#preferred-occupierRelationship") }

        otherResidents { $(".otherResident") }
    }
}
