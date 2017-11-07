package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class TasklistPage extends Page {

    String forUser

    static headings = [
            'OM' : 'Upcoming releases',
            'OMU': 'Licences',
            'PM' : 'Licences for approval',
    ]

    static at = {
        $("#pageHeading").text() == headings[forUser]
    }

    static content = {
        header { module(HeaderModule) }

        infoRequiredLicences(required: false) { $('tr.required') }
        awaitingApprovalLicences(required: false) { $('tr.sent') }

        viewDetailsFor { nomisId ->
            $('a', href: contains(nomisId)).click()
        }
    }

}
