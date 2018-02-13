package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class SendPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/send/')
    }

    static content = {
        header { module(HeaderModule) }

        prison(required: false) { $("#premise") }
        city(required: false) { $("#city") }
        locality(required: false) { $("#locality") }
        postCode(required: false) { $("#postCode") }

        phones(required: false) { $("div", id: startsWith("phone")) }
    }

}
