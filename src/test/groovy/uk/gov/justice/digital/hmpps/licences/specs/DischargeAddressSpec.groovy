package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Ignore
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.pages.DischargeAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class DischargeAddressSpec extends GebReportingSpec {

    @Shared TestData testData = new TestData()
    @Shared Actions actions = new Actions()

    def setupSpec() {
        actions.logIn()
    }

    def cleanupSpec() {
        actions.logOut()
        testData.deleteLicences()
    }

    def 'I can enter an address and it is saved to the licence'() {

        given:
        actions.toDischargeAddressPageFor('A1235HG')
        at DischargeAddressPage

        when: 'I enter the address details'
        usingFormInputs([
                address1 : 'line 1',
                address2 : 'line 2',
                address3 : 'line 3',
                postCode : 'post code',
        ])

        and: 'I continue'
        footerButtons.clickContinue

        then: 'The address is saved to the database'
        def result = testData.findLicenceFor('A1235HG').dischargeAddress
        result.address1 == 'line 1'
        result.address2 == 'line 2'
        result.address3 == 'line 3'
        result.postCode == 'post code'
    }

    @Ignore
    def 'Shows discharge address from nomis'() {

        given:
        def dischargeAddressItems = [
                '#address1'         : '19 Grantham Road'
        ]

        when: 'I view the discharge address page'
        actions.toDischargeAddressPageFor('A1235HG')
        at DischargeAddressPage

        then: 'I see the expected personal details data'
        dischargeAddressItems.each { item, value ->
            assert dischargeAddressDetails.find(item).text() == value
        }
    }

    @Ignore
    def 'Reveals form if I click yes radio, hides if click no'() {

        when: 'I view the personal details page'
        at DischargeAddressPage

        and: 'I click radio yes'
        radioYes.click()

        then: 'Address form is hidden'
        addressFormIsVisible

        and: 'I click radio no'
        radioNo.click()

        then: 'Address form is hidden'
        !addressFormIsVisible

    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the page'
        actions.toDischargeAddressPageFor('A1235HG')
        at DischargeAddressPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the page'
        at DischargeAddressPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at TasklistPage
    }
}