package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class AddressDetailsModule extends Module {

    static content = {

        preferred {
            address('preferred')
        }

        alternative {
            address('alternative')
        }

        address { type ->
            [
                    line1    : $("#${type}-address1"),
                    line2    : $("#${type}-address2"),
                    town     : $("#${type}-town"),
                    postCode : $("#${type}-postCode"),
                    telephone: $("#${type}-telephone")
            ]
        }
    }
}
