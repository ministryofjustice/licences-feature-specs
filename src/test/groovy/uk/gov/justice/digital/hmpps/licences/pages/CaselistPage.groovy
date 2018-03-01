package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CaselistPage extends Page {

    static at = {
        $("#pageHeading").text() == 'HDC eligible offenders'
    }

    static content = {
        header { module(HeaderModule) }

        hdcEligible(required: false) { $('tr.hdcEligible') }

        viewTaskListFor { nomisId ->
            $('a', href: contains(nomisId)).click()
        }
    }

}
