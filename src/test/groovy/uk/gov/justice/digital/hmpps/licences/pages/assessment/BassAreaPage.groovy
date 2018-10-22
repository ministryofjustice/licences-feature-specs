package uk.gov.justice.digital.hmpps.licences.pages.assessment

import geb.Page
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.BassRequestModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class BassAreaPage extends Page {

    static url = '/hdc/bassReferral/bassAreaCheck'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }

        bass { module(BassRequestModule) }

        areaReasons { $('#bassAreaReason') }

        areaRadios { $(name: "bassAreaSuitable").module(RadioButtons) }
    }
}
