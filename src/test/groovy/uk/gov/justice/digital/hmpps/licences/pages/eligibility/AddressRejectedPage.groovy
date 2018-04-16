package uk.gov.justice.digital.hmpps.licences.pages.eligibility

import geb.Page
import geb.module.Checkbox
import geb.module.RadioButtons
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class AddressRejectedPage extends Page {

    static url = '/hdc/proposedAddress/rejected'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }

        submitAlternativeForm(required: false) { $("#submitAlternativeForm") }
        submitAlternative(required: false) { $(name: "submitAlternative") }

        enterAlternativeForm(required: false) { $("#enterAlternativeForm") }
        enterAlternative(required: false) { $(name: "enterAlternative") }
    }
}
