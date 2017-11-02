package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class SentPage extends Page{

    static at = {
        browser.currentUrl.contains('/sent/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        agencyName { $('#agencyName').text() }
        agencyTel { $('#agencyTel').text() }
    }

}
