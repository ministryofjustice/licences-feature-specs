package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CaselistPage extends Page {

    static at = {
        $("#pageHeading").text() == 'HDC eligible prisoners'
    }

    static content = {
        header { module(HeaderModule) }

        hdcEligible(required: false) { $('tr.hdcEligible') }

        viewDetailsFor { nomisId ->
            $('a', href: contains(nomisId)).click()
        }
    }

}
