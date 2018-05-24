package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.AddressRejectedPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressConfirmPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressCurfewAddressPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class AddressRejectedSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('eligibility/addressRejected')
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Displays enter alternative address form if there is no existing alternative'() {

        when: 'I view the address rejected page'
        to AddressRejectedPage, 'A0001XX'

        then: 'The enter alternative address form is displayed'
        enterAlternativeForm.isDisplayed()
    }

    def 'Returns to tasklist of answer is No'() {

        given: 'On address rejected form with no alternative address'
        at AddressRejectedPage

        when: 'I select not to enter an alternative'
        enterAlternative = 'No'
        find('#continueBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage
    }

    def 'Goes to curfew address form if answer is Yes'() {

        given: 'On address rejected form with no alternative address'
        to AddressRejectedPage, 'A0001XX'

        when: 'I select to submit an alternative'
        enterAlternative = 'Yes'
        find('#continueBtn').click()

        then: 'I see the curfewAddress page'
        at ProposedAddressCurfewAddressPage
    }

    def 'Displays submit alternative address form if there is an existing alternative'() {

        given: 'There is a rejected address and an alternative address on the licence'
        testData.loadLicence('eligibility/addressRejectedAlternative')

        when: 'I view the address rejected page'
        to AddressRejectedPage, 'A0001XX'

        then: 'The alternative address is displayed'
        $("#address1-alt").text() == 'AltStreet'

        and: 'The submit alternative address form is displayed'
        submitAlternativeForm.isDisplayed()
    }

    def 'Goes to curfewAddress page if No is selected'() {

        given: 'On address rejected form with alternative address'
        at AddressRejectedPage

        when: 'I select not to submit the alternative'
        submitAlternative = 'No'
        find('#continueBtn').click()

        then: 'I see the curfew address page'
        at ProposedAddressCurfewAddressPage

        and: 'The alternative address has been deleted'
        to AddressRejectedPage, 'A0001XX'
        enterAlternativeForm.isDisplayed()

    }

    def 'Goes to curfew address confirmation page if answer is Yes'() {

        given: 'On address rejected form with alternative address'
        testData.loadLicence('eligibility/addressRejectedAlternative')
        to AddressRejectedPage, 'A0001XX'

        when: 'I select to submit the alternative'
        submitAlternative = 'Yes'
        find('#continueBtn').click()

        then: 'I see the curfewAddress confirmation page'
        at ProposedAddressConfirmPage

        and: 'I see the alternative address'
        address.preferred.line1.text() == 'AltStreet'
    }
}
