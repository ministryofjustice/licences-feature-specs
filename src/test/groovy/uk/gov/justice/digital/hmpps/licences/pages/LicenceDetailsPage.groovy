package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ConditionsSummaryModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.PrisonerDetailsModule

class LicenceDetailsPage extends Page {

    static at = {
        browser.currentUrl.contains('/licenceDetails/')
    }

    static content = {
        header { module(HeaderModule) }

        offender { module(PrisonerDetailsModule) }

        conditions { module(ConditionsSummaryModule) }

        changeDetailsLink { section ->
            $("#${section}EditLink")
        }

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
            $("#${day.toLowerCase()}").text()
        }

        riskAnswers {
            [
                    planningActions: $("#planningActions").text(),
                    information    : $("#awaitingInformation").text(),
                    victimLiaison  : $("#victimLiaison").text()
            ]
        }

        reportingAddress {
            [
                    line1    : $("#reportingAddress1").text(),
                    line2    : $("#reportingAddress2").text(),
                    town     : $("#reportingTown").text(),
                    postCode : $("#reportingPostCode").text(),
                    telephone: $("#reportingTelephone").text()
            ]
        }

        reportingName { $("#reportingName").text() }
    }

}
