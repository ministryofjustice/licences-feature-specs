package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ProposedAddressReviewSpec extends GebReportingSpec {

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

    def 'Shows address details' () {

        given: 'A licence record with a proposed curfew address'
        testData.loadLicence('processing-ro/unstarted')

        when: 'I go to the address review page'
        actions.toAddressReviewPageFor('A0001XX')
        at ProposedAddressReviewPage

        then: 'I see the address details'
        street.text() == 'Street'
        town.text() == 'Town'
        postCode.text() == 'AB1 1AB'

        // todo these all need formatting/capitalising
        // todo check other values
    }

    def 'Confirmation options initially unselected' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        then: 'Options not set'
        landlordConsentRadios.checked == null
        manageSafelyRadios.checked == null
    }

    def 'Further questions not shown when landlord consent is no' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        then: 'I do not see the further questions'
        !landlordConsentForm.isDisplayed()
    }

    def 'Further questions shown when landlord consent is yes' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        and: 'I select yes for excluded'
        landlordConsentRadios.checked = 'Yes'

        then: 'I see the further questions'
        landlordConsentForm.isDisplayed()
    }

    def 'Further details not shown when managed safely is yes' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        then: 'I do not see the further questions'
        !managedSafelyForm.isDisplayed()
    }

    def 'Further details shown when managed safely is no' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        and: 'I select yes for excluded'
        manageSafelyRadios.checked = 'No'

        then: 'I see the further questions'
        managedSafelyForm.isDisplayed()
    }

    def 'Modified choices are not saved on return to tasklist' () {

        given:  'At address review page'
        at ProposedAddressReviewPage

        when: 'I select new options'
        landlordConsentRadios.checked = 'Yes'
        manageSafelyRadios.checked = 'Yes'

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the address review page'
        actions.toAddressReviewPageFor('A0001XX')
        at ProposedAddressReviewPage

        then: 'I see the original values'
        landlordConsentRadios.checked == null
        manageSafelyRadios.checked == null
    }

    def 'Modified choices are saved after save and continue' () {


        given:  'At address review page'
        at ProposedAddressReviewPage

        when: 'I select new options'
        landlordConsentRadios.checked = 'Yes'
        electricitySupplyRadios.checked = 'Yes'
        landlordConsentRadios.checked = 'Yes'
        homeVisitRadios.checked = 'No'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I return to the address review page'
        actions.toAddressReviewPageFor('A0001XX')
        at ProposedAddressReviewPage

        then: 'I see the previously entered values'
        landlordConsentRadios.checked == 'Yes'
        electricitySupplyRadios.checked == 'Yes'
        landlordConsentRadios.checked == 'Yes'
        homeVisitRadios.checked == 'No'
    }
}
