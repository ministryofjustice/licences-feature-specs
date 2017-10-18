package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ButtonsModule extends Module {

    static content = {
        continueBtns { $('#continueBtns') }
        continueButton { continueBtns.find('.requiredButton', 0) }
        backButton { continueBtns.find('.requiredButton', 1) }
    }
}
