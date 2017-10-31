package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ReviewInformationPage extends Page{

    static at = {
        browser.currentUrl.contains('/licenceDetails/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        dischargeAddress { $('#dischargeAddress').text()  }
        reportName { $('#reportName').text()  }
    }

}
