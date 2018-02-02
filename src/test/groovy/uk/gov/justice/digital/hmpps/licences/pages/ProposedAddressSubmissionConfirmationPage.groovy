package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class ProposedAddressSubmissionConfirmationPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/proposedAddress/confirmation/')
    }

}
