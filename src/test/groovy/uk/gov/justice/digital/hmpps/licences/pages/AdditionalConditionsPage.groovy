package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class AdditionalConditionsPage extends Page {

    static at = {
        browser.currentUrl.contains('/additionalConditions/')
    }

    static content = {
        header { module(HeaderModule) }

        conditions(required: false) { $(name: "additionalConditions") }

        conditionsItem { conditionValue ->
            $("input", value: conditionValue, name: "additionalConditions").module(Checkbox)
        }
    }
}
