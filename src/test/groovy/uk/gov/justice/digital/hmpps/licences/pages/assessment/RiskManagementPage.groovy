package uk.gov.justice.digital.hmpps.licences.pages.assessment

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class RiskManagementPage extends Page {

    static url = '/hdc/risk/riskManagement'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }

        riskManagementRadios { $(name: "planningActions").module(RadioButtons) }
        awaitingInformationRadios { $(name: "awaitingInformation").module(RadioButtons) }
        victimLiaisonRadios { $(name: "victimLiaison").module(RadioButtons) }

        riskManagementForm(required: false) { $("#planningActionsDetails") }
        awaitingInformationForm(required: false) { $("#awaitingInformationDetails") }
        victimLiaisonForm(required: false) { $("#victimLiaisonDetails") }
    }
}
