package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class EligibilityCheckCrdTimePage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/eligibility/crdTime')
    }

    static content = {
        header { module(HeaderModule) }

        crdTimeRadios { $(name: "crdTime").module(RadioButtons) }

    }
}
