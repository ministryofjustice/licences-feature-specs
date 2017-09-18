package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class FeedbackPage extends Page {

    static url = '/feedback'

    static at = {
        browser.currentUrl.endsWith(url)
    }

    static content = {

        header { module(HeaderModule) }

        feedbackMailtoLink {  $('a', href: contains('mailto'))  }
    }
}
