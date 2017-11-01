package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class TasklistPage extends Page{

    static at = {
        $("#pageHeading").text() == "Upcoming releases"
    }

    static content = {
        header { module(HeaderModule) }

        infoRequiredLicences(required: false) { $('tr.infoRequired') }

        viewDetailsFor{ nomisId ->
            $('a', href: contains(nomisId)).click()
        }
    }

}
