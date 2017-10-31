package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class AdditionalConditionsSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn()
    }

    def cleanupSpec() {
        actions.logOut()
        testData.deleteLicences()
    }

    def 'Shows link to view standard conditions'() {

       when: 'I view the additional conditions page'
       actions.toAdditionalConditionsPageFor('A1235HG')
       at AdditionalConditionsPage

       then: 'I see a link to the standard conditions page'
       standardConditionsLink.isDisplayed()
    }



    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the additional conditions page'
        at AdditionalConditionsPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the prisoner details page'
        at AdditionalConditionsPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at TasklistPage
    }
}