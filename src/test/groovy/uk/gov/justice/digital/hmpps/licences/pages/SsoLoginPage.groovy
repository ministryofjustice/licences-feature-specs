package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.util.LicencesUi

class SsoLoginPage extends Page{

    static LicencesUi config = new LicencesUi()

    static url = config.signonUri

    static at = {
        browser.currentUrl.startsWith(url)
    }

    static content = {
        signIn {
            $('form')['user[email]'] = config.user
            $('form')['user[password]'] = config.password
            $('input', name: 'commit').click()
        }
    }

}
