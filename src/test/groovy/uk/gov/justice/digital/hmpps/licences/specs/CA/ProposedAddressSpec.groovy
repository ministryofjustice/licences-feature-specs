package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressConfirmPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.HdcOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.BassReferralPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ProposedAddressSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('eligibility/unstarted')
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Starts with opt out page with nothing selected'() {

        when: 'I view the opt out page'
        actions.toOptOutPageFor('A0001XX')
        at HdcOptOutPage

        then: 'Neither radio option is selected'
        decisionRadios.checked == null

        and: 'details form is not shown'
        !detailsForm.isDisplayed()
    }

    def 'Details box is not shown when Yes is selected'() {

        given: 'I am on the opt out page'

        when: 'I select yes for opt out'
        decisionRadios.checked = 'Yes'

        then: 'I see details form'
        detailsForm.isDisplayed()

        when: 'I select no for opt out'
        decisionRadios.checked = 'No'

        then: 'I don not see details form'
        !detailsForm.isDisplayed()
    }

    def 'Can view eligibility checks when already started'() {

        given: 'Opt out form already done'
        testData.loadLicence('eligibility/optedOut')

        when: 'I view the eligibility checks page'
        actions.toOptOutPageFor('A0001XX')
        at HdcOptOutPage

        then: 'I see the previous values'
        decisionRadios.checked == 'Yes'
        detailsForm.isDisplayed()
        detailsForm == 'Reason for opt out'

    }

    def 'The task list is shown next if opt out is Yes' () {
        given: 'On opt out page'
        at HdcOptOutPage

        when: 'I select to opt out'
        decisionRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the task list'
        at TaskListPage
    }

    def 'The BASS referral page is shown next if opt out is No' () {

        when: 'I view the opt out page'
        actions.toOptOutPageFor('A0001XX')
        at HdcOptOutPage

        and: 'I select to not opt out'
        decisionRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the BASS referral form'
        at BassReferralPage

        and: 'Nothing is selected'
        decisionRadios.checked == null

        and: 'details form is not shown'
        !proposedTownInput.isDisplayed()
        !proposedCountyInput.isDisplayed()

        when: 'i select Yes'
        decisionRadios.checked = 'Yes'

        then: 'details form is shown'
        proposedTownInput.isDisplayed()
        proposedCountyInput.isDisplayed()

        when: 'i select No'
        decisionRadios.checked = 'No'

        then: 'details form is not shown'
        !proposedTownInput.isDisplayed()
        !proposedCountyInput.isDisplayed()
    }

    def 'The task list is shown next if BASS referral is Yes' () {
        given: 'On BASS referral page'
        at BassReferralPage

        when: 'I select yes'
        decisionRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the task list'
        at TaskListPage
    }

    def 'The proposed address form is shown next if BASS referral is No' () {
        when: 'I view the opt out page'
        actions.toBassReferralPage('A0001XX')
        at BassReferralPage

        and: 'I select no'
        decisionRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the proposed Address Form'
        at ProposedAddressPage
    }

    def 'Entered values are saved after save and continue' () {

        given: 'On Curfew Address page'
        at ProposedAddressPage

        when: 'I fill in the form and save'
        address1 = 'Address 1'
        address2 = 'Address 2'
        town = 'Town'
        postCode = 'Post code'
        telephone = '001'
        occupierName = 'Name'
        occupierAge = '12'
        occupierRelation = 'Relation'
        electricityRadios.checked = 'Yes'
        cautionedRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the confirm address page'
        at ProposedAddressConfirmPage

        and: 'I see the address details entered'

        def addressInput = [
                '#preferred-address1'                : 'Address 1',
                '#preferred-address2'                : 'Address 2',
                '#preferred-addressTown'             : 'Town',
                '#preferred-addressPostCode'         : 'Post code',
                '#preferred-telephone'               : '001',

                '#preferred-occupierName'            : 'Name',
                '#preferred-occupierAge'             : '12',
                '#preferred-occupierRelation'        : 'Relation',
                '#preferred-cautionedAgainstResident': 'No',
                '#preferred-electricity'             : 'Yes'

        ]

        then: 'I see the expected data for the address'
        addressInput.each { item, value ->
            assert addressDetails.find(item).text() == value
        }

    }

    def 'I can enter values for an alternative address' () {
        given: 'I am on the proposed curfew address page'
        actions.toCurfewAddressPage('A0001XX')
        at ProposedAddressPage

        when: 'I select to add an alternative address'
        alternativeAddress.checked = 'Yes'

        then: 'I see the form for an alternative address'
        alternativeAddressForm.isDisplayed()

        when: 'I enter details for this address'
        altAddress1 = 'Address 1'
        altAddress2 = 'Address 2'
        altTown = 'Town'
        altPostCode = 'Post code'
        altTelephone = '001'
        altOccupierName = 'Name'
        altOccupierAge = '12'
        altOccupierRelation = 'Relation'
        altElectricityRadios.checked = 'Yes'
        altCautionedRadios.checked = 'No'

        then: 'I click to save and continue'
        find('#continueBtn').click()

        and: 'I see the confirm address page'
        at ProposedAddressConfirmPage

        def addressInput = [
                '#alternative-address1'                : 'Address 1',
                '#alternative-address2'                : 'Address 2',
                '#alternative-addressTown'             : 'Town',
                '#alternative-addressPostCode'         : 'Post code',
                '#alternative-telephone'               : '001',

                '#alternative-occupierName'            : 'Name',
                '#alternative-occupierAge'             : '12',
                '#alternative-occupierRelation'        : 'Relation',
                '#alternative-cautionedAgainstResident': 'No',
                '#alternative-electricity'             : 'Yes'

        ]

        and: 'I see the expected data for the address'
        addressInput.each { item, value ->
            assert altAddressDetails.find(item).text() == value
        }
    }

    def 'I can submit the address to the RO' () {
        given: 'On confirm address page'
        at ProposedAddressConfirmPage

        when: 'I press save and continue'
        find('#continueBtn').click()

        then: 'I see the submit to RO page'
        at SendPage

        and: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I move to confirmation page'
        at SentPage

        when: 'I click return to task list'
        find('#backBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage
    }
}
