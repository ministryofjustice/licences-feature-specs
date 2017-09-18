package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule


class LoggedinPage extends Page {

    static url = '/loggedin'

    static at = {
        waitFor(5) {
            browser.currentUrl.endsWith(url) || browser.currentUrl.endsWith(url + '#')
        }
    }

    static content = {
        header { module(HeaderModule) }
    }
}
