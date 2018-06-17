package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAdressAddressProposedPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressCurfewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressBassReferralPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewCurfewAddressPage
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
        to ProposedAddressOptOutPage, 'A0001XX'

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

        when: 'I view the opt out page'
        to ProposedAddressOptOutPage, 'A0001XX'

        then: 'I see the previous values'
        decisionRadios.checked == 'Yes'
        detailsForm.isDisplayed()
        detailsForm == 'Reason for opt out'

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

    def 'The address proposed question page is shown next if opt out is No' () {

        when: 'I view the opt out page'
        to ProposedAddressOptOutPage, 'A0001XX'

        and: 'I select to not opt out'
        decisionRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the address proposed form'
        at ProposedAdressAddressProposedPage

        and: 'Nothing is selected'
        decisionRadios.checked == null
    }

    def 'The BASS referral page is shown next if address proposed is No' () {

        given: 'On address proposed page'
        at ProposedAdressAddressProposedPage

        when: 'I select to not propose an address'
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

    def 'The proposed address form is shown next if address proposed is Yes' () {

        when: 'I view the address proposed page'
        to ProposedAdressAddressProposedPage, 'A0001XX'

        and: 'I select yes'
        decisionRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the proposed Address Form'
        at ProposedAddressCurfewAddressPage
    }

    def 'Entered values are saved after save and continue' () {

        given: 'On Curfew Address page'
        to ProposedAddressCurfewAddressPage, 'A0001XX'

        when: 'I fill in the form and save'

        address.preferred.line1.value('Address 1')
        address.preferred.line2.value('Address 2')
        address.preferred.town.value('Town')
        address.preferred.postCode.value('S1 4JQ')
        address.preferred.telephone .value('001')

        occupier.preferred.name.value('Name')
        occupier.preferred.relationship.value('Relation')

        cautionedRadios.checked = 'No'

        find('#continueBtn').click()

        then: 'I see the review page'
        at ReviewCurfewAddressPage

        and: 'I see the expected data for the address'
        curfew.address.line1 == 'Address 1'
        curfew.address.line2  == 'Address 2'
        curfew.address.town  == 'Town'
        curfew.address.postCode  == 'S1 4JQ'
        curfew.address.telephone  == '001'

        curfew.occupier.name  == 'Name'
        curfew.occupier.relationship  == 'Relation'
    }

    def 'I can enter extra residents to addresses' () {

        given: 'I am on the proposed curfew address page'
        to ProposedAddressCurfewAddressPage, 'A0001XX'

        when: 'I click to add another resident'
        addResidentLink.click()

        then: 'Another resident is added to the list'
        $(name: '[addresses][0][residents][3][name]').isDisplayed()

        when: 'I set values'
        $('input', name: '[addresses][0][residents][3][name]').value('Name')
        $('input', name: '[addresses][0][residents][3][age]').value('11')
        $('input', name: '[addresses][0][residents][3][relationship]').value('Relation')

        and: 'I click to save and continue'
        find('#continueBtn').click()

        then: 'I see the values on the review page'
        at ReviewCurfewAddressPage
        curfew.residents[0].name == 'Name'
        curfew.residents[0].age == '11'
        curfew.residents[0].relationship == 'Relation'
    }

    def 'Input is validated on the review page' () {

        given: 'On Curfew Address page'
        to ProposedAddressCurfewAddressPage, 'A0001XX'

        when: 'I omit a required field'
        address.preferred.line1.value('')
        find('#continueBtn').click()

        then: 'I see the review page'
        at ReviewCurfewAddressPage

        and: 'I see the error details'
        errors.heading.isDisplayed()
        $('#address1-curfew-error').isDisplayed()

        when: 'I click on the correct address link'
        correctAddressLink.click()

        then: 'I see the Curfew Address page'
        at ProposedAddressCurfewAddressPage

        when: 'I enter a value for the missed field'
        address.preferred.line1.value('Address 1')
        find('#continueBtn').click()

        then: 'I see the review page with no errors'
        at ReviewCurfewAddressPage
        !errors.heading.isDisplayed()
        !$('#address1-curfew-error').isDisplayed()

        and: 'Can click to continue'
        find('#continueBtn').click()

        then: 'I see the send page'
        at SendPage
    }

    def 'I can submit the address to the RO' () {

        given: 'On the send page'
        at SendPage

        when: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I move to confirmation page'
        at SentPage

        when: 'I click return to case list'
        find('#backBtn').click()

        then: 'I return to the case list'
        at CaselistPage
    }
}
