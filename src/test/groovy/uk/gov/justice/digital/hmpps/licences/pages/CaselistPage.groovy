package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.OffenderSummaryModule

class CaselistPage extends Page {

    static at = {
        $("#pageHeading").text() == 'HDC eligible offenders'
    }

    static url = '/caseList'

    static content = {

        header { module(HeaderModule) }

        offenders { module(OffenderSummaryModule) }

        hdcEligible(required: false) { $('tr.hdcEligible') }

        viewTaskListFor { nomisId ->
            $('a', href: contains(nomisId)).click()
        }

        viewTabFor { tabName ->
            $('a', href: tabName).click()
        }

        paginateNext(required: false) {$('#pagination a.next')}
        paginatePrev(required: false) {$('#pagination a.prev')}
        paginationText(required: false) {$('#paginationInfo')}
    }

}
