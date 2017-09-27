package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class DashboardPage extends Page{

    static url = '/dashboard'

    static content = {
        header { module(HeaderModule) }

        infoRequiredLicences(required: false) { $('tr.infoRequired') }
    }

}
