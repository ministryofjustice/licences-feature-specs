package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class BassRequestModule extends Module {

    static content = {

        proposed {
            [
                    town  : $("#proposedTown").text(),
                    county: $("#proposedCounty").text()
            ]
        }

        area {
            [
                    areaSuitable: $("#suitableArea").text(),
                    areaReason: $("#areaReason").text(),
            ]
        }

        offer {
            [
                    area: $("#bassArea").value(),
                    street: $("#address1").value(),
                    town: $("#town").value(),
                    postCode: $("#postCode").value(),
                    telephone: $("#telephone").value(),
            ]
        }
    }
}
