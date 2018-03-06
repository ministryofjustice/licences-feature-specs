package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ConditionsSummaryModule extends Module {

    static content = {

        additionalConditions(required: false) { $('div.additional') }

        additionalConditionsTitle(required: false) { $('div.additionalTitle')*.text() }
        additionalConditionsName(required: false) { $('div.additionalName')*.text() }
        additionalConditionsContent(required: false) { $('div.additionalContent')*.text() }

        editConditionLinks { $('a', text: 'Edit condition') }
        deleteConditionLinks { $('input', value: 'Delete') }
    }
}
