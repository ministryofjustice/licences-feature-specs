package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class SigninPage extends Page{

    static url = '/signin'

    static content = {
        signIn {
            $('form').username = 'any'
            $('form').password = 'any'

            assert $('form').username == 'any'
            assert $('form').password == 'any'

            assert $('#submit').text() == 'Login'

            $('#submit').click()
        }
    }

}
