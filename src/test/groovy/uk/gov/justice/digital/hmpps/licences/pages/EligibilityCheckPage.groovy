package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class EligibilityCheckPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/eligibility/excluded')
    }

    static content = {
        header { module(HeaderModule) }

        excludedRadios { $(name: "decision").module(RadioButtons) }

        excludedReasonsForm(required: false) { $("#excludedForm") }

        excludedReasons(required: false) { $(name: "reason") }

        excludedReasonsItem { int number ->
            $("input", number, name: "reason").module(Checkbox)
        }
    }
}
