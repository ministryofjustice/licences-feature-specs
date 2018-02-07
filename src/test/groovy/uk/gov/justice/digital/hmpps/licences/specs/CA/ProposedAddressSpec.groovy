package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressCurfewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressConfirmPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressSubmissionConfirmationPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressSubmitPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressBassReferralPage
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
        testData.deleteLicences()
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Starts with opt out page with nothing selected'() {

        when: 'I view the opt out page'
        actions.toOptOutPageFor('A0001XX')
        at ProposedAddressOptOutPage

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
        testData.deleteLicences()
        testData.createLicence([
                'nomisId'        : 'A0001XX',
                'eligibility'    : [
                        'excluded'  : ['decision': 'No'],
                        'unsuitable': ['decision': 'No']
                ],
                'proposedAddress': [
                        optOut: ['decision': 'Yes',
                                 'reason'  : 'Reason']
                ]
        ])

        when: 'I view the eligibility checks page'
        actions.toOptOutPageFor('A0001XX')
        at ProposedAddressOptOutPage

        then: 'I see the previous values'
        decisionRadios.checked == 'Yes'
        detailsForm.isDisplayed()
        detailsForm == 'Reason'

    }

    def 'The task list is shown next if opt out is Yes' () {
        given: 'On opt out page'
        at ProposedAddressOptOutPage

        when: 'I select to opt out'
        decisionRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the task list'
        at TaskListPage
    }

    def 'The BASS referral page is shown next if opt out is No' () {

        when: 'I view the opt out page'
        actions.toOptOutPageFor('A0001XX')
        at ProposedAddressOptOutPage

        and: 'I select to not opt out'
        decisionRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the BASS referral form'
        at ProposedAddressBassReferralPage

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
        at ProposedAddressBassReferralPage

        when: 'I select yes'
        decisionRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the task list'
        at TaskListPage
    }

    def 'The proposed address form is shown next if BASS referral is No' () {
        when: 'I view the opt out page'
        actions.toBassReferralPage('A0001XX')
        at ProposedAddressBassReferralPage

        and: 'I select no'
        decisionRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the proposed Address Form'
        at ProposedAddressCurfewAddressPage
    }

    def 'Inputted choices are saved after save and continue' () {

        given: 'On Curfew Address page'
        at ProposedAddressCurfewAddressPage

        when: 'I fill in the form and save'
        address1 = 'Address 1'
        address2 = 'Address 2'
        addressTown = 'Town'
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
                '#address1'                : 'Address 1',
                '#address2'                : 'Address 2',
                '#addressTown'             : 'Town',
                '#addressPostCode'        : 'Post code',
                '#telephone'               : '001',

                '#occupierName'            : 'Name',
                '#occupierAge'             : '12',
                '#occupierRelation'         : 'Relation',
                '#cautionedAgainstResident': 'No',
                '#electricity'             : 'Yes'

        ]

        then: 'I see the expected data for the address'
        addressInput.each { item, value ->
            assert addressDetails.find(item).text() == value
        }

    }

    def 'I can submit the address to the RO' () {
        given: 'On confirm address page'
        at ProposedAddressConfirmPage

        when: 'I press save and continue'
        find('#continueBtn').click()

        then: 'I see the submit to RO page'
        at ProposedAddressSubmitPage

        and: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I move to confirmation page'
        at ProposedAddressSubmissionConfirmationPage

        when: 'I click return to task list'
        find('#backBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage
    }
}
