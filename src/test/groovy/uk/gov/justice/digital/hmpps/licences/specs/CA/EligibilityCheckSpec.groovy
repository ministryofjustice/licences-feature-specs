package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class EligibilityCheckSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('CA_USER')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows link to return to caselist'() {

        when: 'I view the page'
        at PrisonerDetailsPage

        then: 'I see a start button for the eligibility check'
        find('#eligibilityCheckStart').value() == 'Start'

        and: 'I see a print button for the proposed address form'
        find('#addressFormPrint').text() == 'Print form'
    }
}
