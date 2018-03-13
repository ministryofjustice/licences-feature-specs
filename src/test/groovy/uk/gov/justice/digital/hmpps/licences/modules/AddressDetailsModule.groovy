package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class AddressDetailsModule extends Module {

    static content = {

        preferred {
            [
                    line1    : $("#preferredaddress1"),
                    line2    : $("#preferredaddress2"),
                    town     : $("#preferredtown"),
                    postCode : $("#preferredpostCode"),
                    telephone: $("#preferredtelephone")
            ]
        }

        alternative {
            [
                    line1    : $("#alternativeaddress1"),
                    line2    : $("#alternativeaddress2"),
                    town     : $("#alternativetown"),
                    postCode : $("#alternativepostCode"),
                    telephone: $("#alternativetelephone")
            ]
        }
    }
}
