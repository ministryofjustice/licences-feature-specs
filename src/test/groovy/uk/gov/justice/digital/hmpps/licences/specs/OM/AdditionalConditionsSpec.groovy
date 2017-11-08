package uk.gov.justice.digital.hmpps.licences.specs.OM

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
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
        actions.logIn('OM')
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

        when: 'I click the standard consitions link'
        standardConditionsLink.click()

        then: 'I see the standard conditions page'
        at StandardConditionsPage
    }

    def 'Standard conditions page has link back to additional conditions page'() {

        given:
        at StandardConditionsPage

        when:
        backLink.click()

        then:
        at AdditionalConditionsPage
    }

    def 'Can choose no additional conditions'() {

        when: 'I choose no additional conditions'
        noAdditionalConditions.click()

        then: 'I see the next page'
        at ReportingInstructionsPage
    }

    @Ignore
    def 'Selected conditions are saved to the licence'() {

        given: 'Viewing additional conditions'
        actions.toAdditionalConditionsPageFor('A1235HG')
        at AdditionalConditionsPage

        when: 'I select conditions 8 and 9'
        selectCondition(8)
        selectCondition(9)

        and: 'I enter field values'
        $('#appointmentName') << 'Name One'
        $('#mentalHealthName') << 'Name Two'

        and: 'I continue'
        footerButtons.clickContinue

        then: 'The selected conditions are saved to the database'
        def conditions = testData.findLicenceFor('A1235HG').additionalConditions
        conditions.size() == 2
        conditions.containsKey('8')
        conditions.containsKey('9')
        conditions['8'].containsKey('appointmentName')
        conditions['8']['appointmentName'] == 'Name One'
        conditions['9']['mentalHealthName'] == 'Name Two'

        and: 'I see the next page'
        at ReportingInstructionsPage
    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the page'
        actions.toAdditionalConditionsPageFor('A1235HG')
        at AdditionalConditionsPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the page'
        actions.toAdditionalConditionsPageFor('A1235HG')
        at AdditionalConditionsPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at(new TasklistPage(forUser: 'OM'))
    }
}