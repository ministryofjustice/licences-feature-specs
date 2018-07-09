package uk.gov.justice.digital.hmpps.licences.pages.search

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.ErrorModule
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule
import uk.gov.justice.digital.hmpps.licences.modules.SearchModule

class SearchOffenderResultsPage extends Page {

    static url = '/hdc/search/offender/results'

    static at = {
        browser.currentUrl.contains(url)
    }

    static content = {

        header { module(HeaderModule) }
        errors { module(ErrorModule) }
        search { module(SearchModule) }
    }
}