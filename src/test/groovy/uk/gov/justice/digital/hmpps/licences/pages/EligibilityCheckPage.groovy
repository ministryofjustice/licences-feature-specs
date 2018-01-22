package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class EligibilityCheckPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/eligibility/')
    }

    static content = {
        header { module(HeaderModule) }

        excludedRadios { $(name: "excluded").module(RadioButtons) }

        excludedReasonsForm(required: false) { $("#excludedForm") }

        excludedReasons(required: false) { $(name: "excludedReasons") }

        excludedReasonsItem { int number ->
            $("input", number, name: "excludedReasons").module(Checkbox)
        }
    }
}
