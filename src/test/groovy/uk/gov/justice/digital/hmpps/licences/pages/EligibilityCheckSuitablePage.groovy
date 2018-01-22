package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class EligibilityCheckSuitablePage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/eligibility/suitability')
    }

    static content = {
        header { module(HeaderModule) }

        unsuitableRadios { $(name: "unsuitable").module(RadioButtons) }

        unsuitableReasonsForm(required: false) { $("#unsuitableForm") }

        unsuitableReasons(required: false) { $(name: "unsuitableReasons") }

        unsuitableReasonsItem { int number ->
            $("input", number, name: "unsuitableReasons").module(Checkbox)
        }
    }
}
