package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class CurfewDetailsModule extends Module {

    static content = {

        address {
            [
                    line1    : $("#curfew-address1").text(),
                    line2    : $("#curfew-address2").text(),
                    town     : $("#curfew-town").text(),
                    postCode : $("#curfew-postCode").text(),
                    telephone: $("#curfew-telephone").text()
            ]
        }

        occupier {
            [
                    name    : $("#curfew-occupierName").text(),
                    age     : $("#curfew-occupierAge").text(),
                    relation: $("#curfew-occupierRelation").text(),
            ]
        }

        residents {
            $('div.resident').collect { resident ->
                [
                        name    : resident.find(id: startsWith("curfew-residentName-")).text(),
                        age     : resident.find(id: startsWith("curfew-residentAge-")).text(),
                        relation: resident.find(id: startsWith("curfew-residentRelation-")).text()
                ]
            }
        }

        reviewAnswers {
            [
                    cautioned  : $("#curfew-cautioned").text(),
                    consent    : $("#curfew-consent").text(),
                    homeVisit  : $("#curfew-homeVisit").text(),
                    electricity: $("#curfew-electricity").text(),
                    safety     : $("#curfew-deemedSafe").text()
            ]
        }

        curfewHours { day ->
            $("#${day.uncapitalize().replaceAll("\\s","")}").text()
        }
    }
}
