package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class RiskDetailsModule extends Module {

    static content = {

        answers {
            [
                    planningActions      : $("#planningActions").text(),
                    planningActionsDetail: $("#planningActionsDetail").text(),
                    information          : $("#awaitingInformation").text(),
                    informationDetail    : $("#awaitingInformationDetail").text(),
                    victimLiaison        : $("#victimLiaison").text(),
                    victimLiaisonDetail  : $("#victimLiaisonDetail").text()
            ]
        }
    }
}
