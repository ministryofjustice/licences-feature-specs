package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class HeaderModule extends Module {

    static content = {

        feedbackLink { $('a', text: 'feedback') }

        applicationTitle { $('#proposition-name')[0].text() }
    }
}
