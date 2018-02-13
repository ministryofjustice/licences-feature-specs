package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class RiskManagementPage extends Page {

    static at = {
        browser.currentUrl.contains('/riskManagement/')
    }

    static content = {
        header { module(HeaderModule) }

        riskManagementRadios { $(name: "planningActions").module(RadioButtons) }
        awaitingInformationRadios { $(name: "awaitingInformation").module(RadioButtons) }
        victimLiaisonRadios { $(name: "victimLiaison").module(RadioButtons) }
    }
}
