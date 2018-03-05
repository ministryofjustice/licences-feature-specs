package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ConditionsSummaryPage extends Page {

    static at = {
        browser.currentUrl.contains('/conditionsSummary/')
    }

    static content = {
        header { module(HeaderModule) }

        additionalConditions(required: false) { $('div.additional') }
        additionalConditionsContent(required: false) { $('div.additionalContent') }

        additionalConditionsWithText(required: false) { toFind ->
            $('div.additionalContent').find(text: contains(toFind))
        }

        editConditionLinks { $('a', text: 'Edit condition') }
        deleteConditionLinks { $('input', value: 'Delete') }
    }
}
