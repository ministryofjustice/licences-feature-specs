package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class TaskListPage extends Page {

    static at = {
        browser.currentUrl.contains('/hdc/taskList/')
    }

    static content = {
        header { module(HeaderModule) }

        prisonerPersonalDetails { $('#personalDetails') }

        eligibilityCheckStartButton(required: false) { $('#eligibilityCheckStart') }
        eligibilityCheckUpdateLink(required: false) { $('#eligibilityCheckUpdate') }

        excludedAnswer(required: false)  { $('#excludedAnswer') }
        unsuitableAnswer(required: false)  { $('#unsuitableAnswer') }
        crdTimeAnswer(required: false)  { $('#crdTimeAnswer') }

        printEligibilityFormButton(required: false) { $('#eligibilityFormPrint') }
        eligibilityFormPrintStatusText(required: false) { $('#eligibilityFormPrintStatusText') }
        eligibilityFormPrintStatusIcon(required: false) { $('#eligibilityFormPrintStatusIcon') }

        printAddressFormButton(required: false) { $('#addressFormPrint') }
        addressFormPrintStatusText(required: false) { $('#addressFormPrintStatusText') }
        addressFormPrintStatusIcon(required: false) { $('#addressFormPrintStatusIcon') }
    }
}
