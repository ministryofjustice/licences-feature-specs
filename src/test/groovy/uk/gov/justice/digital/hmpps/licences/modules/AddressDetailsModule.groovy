package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class AddressDetailsModule extends Module {

    static content = {

        preferred {
            address('0')
        }

        alternative {
            address('1')
        }

        address { type ->
            [
                    line1    : $("#address1-${type}"),
                    line2    : $("#address2-${type}"),
                    town     : $("#town-${type}"),
                    postCode : $("#postCode-${type}"),
                    telephone: $("#telephone-${type}")
            ]
        }
    }
}
