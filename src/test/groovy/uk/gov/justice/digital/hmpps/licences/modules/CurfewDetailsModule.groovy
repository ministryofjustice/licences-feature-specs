package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class CurfewDetailsModule extends Module {

    static content = {

        address {
            [
                    line1    : $("#address1").text(),
                    line2    : $("#address2").text(),
                    town     : $("#town").text(),
                    postCode : $("#postCode").text(),
                    telephone: $("#telephone").text()
            ]
        }

        occupier {
            [
                    name    : $("#occupierName").text(),
                    age     : $("#occupierAge").text(),
                    relation: $("#occupierRelation").text(),
            ]
        }

        residents {
            $('div.resident').collect { resident ->
                [
                        name    : resident.find(id: startsWith("residentName-")).text(),
                        age     : resident.find(id: startsWith("residentAge-")).text(),
                        relation: resident.find(id: startsWith("residentRelation-")).text()
                ]
            }
        }

        reviewAnswers {
            [
                    cautioned  : $("#cautioned").text(),
                    consent    : $("#consent").text(),
                    homeVisit  : $("#homeVisit").text(),
                    electricity: $("#electricity").text(),
                    safety     : $("#deemedSafe").text()
            ]
        }

        curfewHours { day ->
            $("#${day.uncapitalize().replaceAll("\\s","")}").text()
        }
    }
}
