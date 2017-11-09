package uk.gov.justice.digital.hmpps.licences.specs.OM

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.DischargeAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReportingInstructionsSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('OM')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'I can enter an address. contact, and time, and they are saved to the licence'() {

        given:
        actions.toReportingInstructionsPageFor('A1235HG')
        at ReportingInstructionsPage

        when: 'I enter the reporting instructions'
        usingFormInputs([
                name  : 'contact name',
                address1 : 'line 1',
                address2 : 'line 2',
                address3 : 'line 3',
                postCode : 'post code',
                telephone: '1234',
                date     : 'adate',
                time     : 'atime'
        ])

        and: 'I continue'
        footerButtons.clickContinue

        then: 'The details are saved to the database'
        def result = testData.findLicenceFor('A1235HG').reportingInstructions
        result.address1 == 'line 1'
        result.address2 == 'line 2'
        result.address3 == 'line 3'
        result.postCode == 'post code'
        result.name == 'contact name'
        result.telephone == '1234'
        result.date == 'adate'
        result.time == 'atime'
    }


    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the page'
        actions.toReportingInstructionsPageFor('A1235HG')
        at ReportingInstructionsPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the page'
        at ReportingInstructionsPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at(new TasklistPage(forUser: 'OM'))
    }
}