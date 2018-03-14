package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class RiskDetailsModule extends Module {

    static content = {

        answers {
            [
                    planningActions      : $("#planningActions").text(),
                    planningActionsDetail: $("#planningActionsDetails").text(),
                    information          : $("#awaitingInformation").text(),
                    informationDetail    : $("#awaitingInformationDetails").text(),
                    victimLiaison        : $("#victimLiaison").text(),
                    victimLiaisonDetail  : $("#victimLiaisonDetails").text()
            ]
        }
    }
}
