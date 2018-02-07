package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class RiskManagementPage extends Page {

    static at = {
        browser.currentUrl.contains('/riskManagement/')
    }

    static content = {
        header { module(HeaderModule) }
    }
}
