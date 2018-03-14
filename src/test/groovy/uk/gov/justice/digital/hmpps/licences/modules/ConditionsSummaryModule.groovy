package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ConditionsSummaryModule extends Module {

    static content = {

        additionalConditions(required: false) { $('div.additional') }

        additionalConditionsTitle(required: false) { $('span.additionalTitle')*.text() }
        additionalConditionsName(required: false) { $('span.additionalName')*.text() }
        additionalConditionsApproved(required: false) { $('span.additionalApproved')*.text() }
        additionalConditionsContent(required: false) { $('div.additionalContent')*.text() }

        editConditionLinks { $('a', text: 'Edit condition') }
        deleteConditionLinks { $('input', value: 'Delete') }

        message(required: false) { $('#message').text() }
    }
}
