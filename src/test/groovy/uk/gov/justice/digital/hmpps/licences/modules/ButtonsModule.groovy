package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ButtonsModule extends Module {

    static content = {
        continueButton(required: false) { $('#continueBtn') }
        backButton (required: false) { $('#backBtn') }

        clickContinue { continueButton.click() }
        clickBack { backButton.click() }
    }
}
