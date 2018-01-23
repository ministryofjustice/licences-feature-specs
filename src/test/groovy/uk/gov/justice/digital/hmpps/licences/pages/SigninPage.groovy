package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class SigninPage extends Page{

    static at = {
        browser.currentUrl.contains('/login')
    }

    static content = {
        signInAs { user ->
            $('form').username = user
            $('form').password = 'password123456'

            assert $('form').username == user
            assert $('form').password == 'password123456'

            assert $('#submit').text() == 'Login'

            $('#submit').click()
        }
    }

}
