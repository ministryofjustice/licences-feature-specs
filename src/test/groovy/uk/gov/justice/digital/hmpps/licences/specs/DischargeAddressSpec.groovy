package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
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

    def 'Shows personal details of the prisoner'() {

        given:
        def dischargeAddressItems = [
                '#address1'         : '19 Grantham Road',
                '#contact'          : 'Alison Andrews',
                '#contactNumber'    : '07889814455',
                '#homeAddress'      : 'No'
        ]

        when: 'I view the discharge address page'
        actions.toDischargeAddressPageFor('A1235HG')
        at DischargeAddressPage

        then: 'I see the expected personal details data'
        dischargeAddressItems.each { item, value ->
            dischargeAddressDetails.find(item).text() == value
        }
    }

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

        when: 'I view the personal details page'
        at DischargeAddressPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the personal details page'
        at DischargeAddressPage

        and: 'I click the back to dashboard button'
        footerButtons.backButton.click()

        then: 'I go back to the dashboard'
        at TasklistPage
    }
}