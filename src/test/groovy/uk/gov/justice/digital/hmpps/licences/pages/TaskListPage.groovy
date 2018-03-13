package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.PrisonerDetailsModule

class TaskListPage extends Page {

    static url = '/hdc/taskList'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {
        header { module(HeaderModule) }

        offender { module(PrisonerDetailsModule) }

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

        taskListActions(required: false) { $('.taskListAction') }

        taskListAction(required: false){ taskName ->
            $('h2', text: contains(taskName)).closest('div').find('.taskListAction')
        }
    }
}
