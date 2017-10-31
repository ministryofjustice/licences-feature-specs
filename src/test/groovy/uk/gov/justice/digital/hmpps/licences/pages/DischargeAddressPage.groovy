package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ButtonsModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class DischargeAddressPage extends Page{

    static at = {
        browser.currentUrl.contains('/dischargeAddress/')
    }

    static content = {
        header { module(HeaderModule) }

        footerButtons { module(ButtonsModule) }

        dischargeAddressDetails { $('#addressDetails') }

        acceptanceForm { $('#acceptanceForm') }

        radioYes { $('#radioYes') }

        radioNo { $('#radioNo') }

        addressForm { $('#addressForm') }

        addressFormIsVisible { addressForm.hasClass('js-hidden') }

        usingFormInputs { criteria ->
            criteria.each { key, value ->
                $('form')[key] = value
            }
        }
    }

}
