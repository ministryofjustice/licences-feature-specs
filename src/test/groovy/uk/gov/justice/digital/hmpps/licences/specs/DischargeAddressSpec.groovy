package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.pages.DischargeAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage

@Stepwise
class DischargeAddressSpec extends GebReportingSpec {

    def setupSpec() {
        to SigninPage
        signIn
        at TasklistPage
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
        toDischargeAddressPageFor('A1235HG')

        then: 'I see the expected personal details data'
        dischargeAddressItems.each { item, value ->
            dischargeAddressDetails.find(item).text() == value
        }
    }

    def 'Reveals form if I click yes radio, hides if click no'() {

        when: 'I view the personal details page'
        toDischargeAddressPageFor('A1235HG')

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
        toDischargeAddressPageFor('A1235HG')

        then: 'I see a continue button'
        continueBtns.find('.requiredButton', 0).value() == 'Continue'

        and: 'I see a back to dashboard button'
        continueBtns.find('.requiredButton', 1).text() == 'Back to dashboard'
    }

    def toDischargeAddressPageFor(nomisId) {
        to TasklistPage
        viewDetailsFor(nomisId)
        at PrisonerDetailsPage
        createLicence()
        at DischargeAddressPage
    }
}