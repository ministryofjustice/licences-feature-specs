package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CaselistPage extends Page {

    static at = {
        $("#pageHeading").text() == 'Prisoner licences'
    }

    static content = {
        header { module(HeaderModule) }

        hdcEligible(required: false) { $('tr') }

        viewDetailsFor { nomisId ->
            $('a', href: contains(nomisId)).click()
        }
    }

}
