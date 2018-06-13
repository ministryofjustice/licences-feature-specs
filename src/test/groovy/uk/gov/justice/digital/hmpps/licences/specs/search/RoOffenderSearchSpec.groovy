package uk.gov.justice.digital.hmpps.licences.specs.search

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewLicencePage
import uk.gov.justice.digital.hmpps.licences.pages.search.SearchOffenderPage
import uk.gov.justice.digital.hmpps.licences.pages.search.SearchOffenderResultsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class RoOffenderSearchSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'invalid search shows error'() {

        given: 'On offender search page'
        to SearchOffenderPage

        when: 'I submit an invalid search'
        search.input << '222'
        search.execute.click()

        then: 'I see an error message'
        errors.heading.isDisplayed()
    }

    def 'search leads to search results page'() {

        given: 'On offender search page'
        to SearchOffenderPage

        when: 'I submit a valid search'
        search.input << 'AA0001XX'
        search.execute.click()

        then: 'I see the search results page'
        at SearchOffenderResultsPage
    }

    def 'search from results page returns to search results page'() {

        given: 'On offender search results page'
        at SearchOffenderResultsPage

        when: 'I submit a valid search'
        search.input << 'AA0001XX'
        search.execute.click()

        then: 'I see the search results page'
        at SearchOffenderResultsPage
    }
}