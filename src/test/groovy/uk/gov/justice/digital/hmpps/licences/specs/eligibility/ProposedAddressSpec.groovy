package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAdressAddressProposedPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressCurfewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressConfirmPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.ProposedAddressBassReferralPage
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
        address.preferred.postCode.value('Post code')
        address.preferred.telephone .value('001')

        occupier.preferred.name.value('Name')
        occupier.preferred.age.value('11')
        occupier.preferred.relation.value('Relation')

        cautionedRadios.checked = 'No'

        find('#continueBtn').click()

        then: 'I see the confirm address page'
        at ProposedAddressConfirmPage

        and: 'I see the expected data for the address'
        address.preferred.line1.text() == 'Address 1'
        address.preferred.line2.text()  == 'Address 2'
        address.preferred.town.text()  == 'Town'
        address.preferred.postCode.text()  == 'Post code'
        address.preferred.telephone.text()  == '001'

        occupier.preferred.name.text()  == 'Name'
        occupier.preferred.age.text()  == '11'
        occupier.preferred.relation.text()  == 'Relation'
        occupier.preferred.cautioned.text()  == 'No'
    }

    def 'I can enter values for an alternative address' () {

        given: 'I am on the proposed curfew address page'
        to ProposedAddressCurfewAddressPage, 'A0001XX'

        when: 'I select to add an alternative address'
        alternativeAddressRadios.checked = 'Yes'

        then: 'I see the form for an alternative address'
        alternativeAddressForm.isDisplayed()

        when: 'I enter details for this address'
        address.alternative.line1.value('Alternative 1')
        address.alternative.line2.value('Alternative 2')
        address.alternative.town.value('Alternative Town')
        address.alternative.postCode.value('Alt code')
        address.alternative.telephone.value('002')

        occupier.alternative.name.value('Alternative Name')
        occupier.alternative.age.value('22')
        occupier.alternative.relation.value('Alternative Relation')

        cautionedRadiosAlternative.checked = 'No'

        then: 'I click to save and continue'
        find('#continueBtn').click()

        and: 'I see the confirm address page'
        at ProposedAddressConfirmPage

        and: 'I see the expected data for the address'
        address.alternative.line1.text()  == 'Alternative 1'
        address.alternative.line2.text()  == 'Alternative 2'
        address.alternative.town.text()  == 'Alternative Town'
        address.alternative.postCode.text() == 'Alt code'
        address.alternative.telephone.text()  == '002'

        occupier.alternative.name.text()  == 'Alternative Name'
        occupier.alternative.age.text()  == '22'
        occupier.alternative.relation.text()  == 'Alternative Relation'
        occupier.alternative.cautioned.text()  == 'No'
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
        $('input', name: '[addresses][0][residents][3][relation]').value('Relation')

        and: 'I select to add a resident to an alternative address'
        alternativeAddressRadios.checked = 'Yes'
        addResidentLinkAlternative.click()

        and: 'I add values for that resident'
        $('input', name: '[addresses][1][residents][3][name]').value('Alternative Name')
        $('input', name: '[addresses][1][residents][3][age]').value('22')
        $('input', name: '[addresses][1][residents][3][relation]').value('Alternative Relation')

        and: 'I click to save and continue'
        find('#continueBtn').click()

        then: 'I see the values on the confirm address page'
        at ProposedAddressConfirmPage
        residents.preferred[0].name == 'Name'
        residents.preferred[0].age == '11'
        residents.preferred[0].relation == 'Relation'

        residents.alternative[0].name == 'Alternative Name'
        residents.alternative[0].age == '22'
        residents.alternative[0].relation == 'Alternative Relation'
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

        when: 'I click return to case list'
        find('#backBtn').click()

        then: 'I return to the case list'
        at CaselistPage
    }
}
