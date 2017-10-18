package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class HeaderModule extends Module {

    static content = {

        user { $('#userLoggerIn').text() }

        logoutLink {$('a', text: 'Logout')}
    }
}
