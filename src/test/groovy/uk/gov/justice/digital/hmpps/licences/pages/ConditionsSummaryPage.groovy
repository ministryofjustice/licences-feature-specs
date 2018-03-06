package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import geb.module.Checkbox
import uk.gov.justice.digital.hmpps.licences.modules.ConditionsSummaryModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ConditionsSummaryPage extends Page {

    static at = {
        browser.currentUrl.contains('/conditionsSummary/')
    }

    static content = {
        header { module(HeaderModule) }
        conditions { module(ConditionsSummaryModule) }
    }
}
