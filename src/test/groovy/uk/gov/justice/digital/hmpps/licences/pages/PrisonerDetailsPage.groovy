package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class PrisonerDetailsPage extends Page{

    static url = '/details/'

    static at = {
        browser.currentUrl.contains('/details/')
    }

    static content = {
        header { module(HeaderModule) }

        prisonerPersonalDetails { $('#personalDetails') }

        prisonerKeyDates { $('#keyDates') }

        continueBtns { $('#continueBtns') }
    }

}
