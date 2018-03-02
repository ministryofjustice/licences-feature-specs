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

        cautionedRadios { $(name: "preferred[cautionedAgainstResident]").module(RadioButtons) }
        alternativeAddress { $(name: "alternativeAddress").module(RadioButtons) }
        altCautionedRadios(required: false) { $(name: "alternative[cautionedAgainstResident]").module(RadioButtons) }

        address1 { $("#preferred-addressLine1") }
        address2 { $("#preferred-addressLine2") }
        town { $("#preferred-addressTown") }
        postCode { $("#preferred-postCode") }
        telephone { $("#preferred-telephone") }

        occupierName { $("#preferred-occupierName") }
        occupierAge { $("#preferred-occupierAge") }
        occupierRelation { $("#preferred-occupierRelationship") }

        otherResidents { $(".otherResidentsInput", 0).find('a') }

        alternativeAddressForm(required: false) { $('#alternativeAddress')}

        altAddress1(required: false) { $("#alternative-addressLine1") }
        altAddress2(required: false) { $("#alternative-addressLine2") }
        altTown(required: false) { $("#alternative-addressTown") }
        altPostCode(required: false) { $("#alternative-postCode") }
        altTelephone(required: false) { $("#alternative-telephone") }

        altOccupierName(required: false) { $("#alternative-occupierName") }
        altOccupierAge(required: false) { $("#alternative-occupierAge") }
        altOccupierRelation(required: false) { $("#alternative-occupierRelationship") }
        altOtherResidents { $(".otherResidentsInput", 1).find('a') }
    }
}
