package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ReportingInstructionsPage extends Page{

    static at = {
        browser.currentUrl.contains('/reporting/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        usingFormInputs { criteria ->
            criteria.each { key, value ->
                $('form')[key] = value
            }
        }
    }

}
