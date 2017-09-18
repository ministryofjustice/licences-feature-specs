package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.util.LicencesUi

class SsoProfilePage extends Page{

    static LicencesUi config = new LicencesUi()

    static url = config.profileUri

    static at = {
        browser.currentUrl.startsWith(url)
    }

    static content = {
        signOut {
            $('a', href: '/users/sign_out').click()
        }
    }

}
