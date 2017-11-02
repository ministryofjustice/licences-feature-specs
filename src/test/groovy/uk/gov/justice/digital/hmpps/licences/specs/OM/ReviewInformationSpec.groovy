package uk.gov.justice.digital.hmpps.licences.specs.OM

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.ReviewInformationPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReviewInformationSpec extends GebReportingSpec {

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

    def 'Shows the previously entered licence data'() {

        given: 'A licence with details:'
        testData.createLicence([
                'nomisId'         : 'A1235HG',
                'dischargeAddress': [
                        'address1': 'line 1',
                        'address2': 'line 2',
                        'address3': 'line 3',
                        'postCode': 'post code',
                ],
                'reportingInstructions' : [
                        'name': 'some name'
                ]
        ], 'STARTED')

        when: 'I review the licence information'
        go '/licenceDetails/A1235HG'
        at ReviewInformationPage

        then: 'I see the correct data'
        dischargeAddress.contains('line 1')
        dischargeAddress.contains('line 2')
        dischargeAddress.contains('line 3')
        dischargeAddress.contains('post code')

        reportName.contains('some name')
    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the page'
        actions.toReviewInformationPageFor('A1235HG')
        at ReviewInformationPage

        then: 'I see a continue button'
        footerButtons.continueButton.text() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the page'
        at ReviewInformationPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at TasklistPage
    }
}