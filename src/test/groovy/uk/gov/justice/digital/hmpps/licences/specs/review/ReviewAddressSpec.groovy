package uk.gov.justice.digital.hmpps.licences.specs.review

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewAddressPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReviewAddressSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows values entered by RO'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('review/normal')

        when: 'I view the page'
        to ReviewAddressPage, 'A0001XX'

        then: 'I see the address details'
        curfew.address.line1 == 'Street'
        curfew.address.town == 'Town'
        curfew.address.postCode == 'AB1 1AB'
        curfew.address.telephone == '0123 456789'

        and: 'I see the occupier details'
        curfew.occupier.name == 'Main Occupier'
        curfew.occupier.age == '21'
        curfew.occupier.relationship == 'Brother'

        and: 'I see the other residents details'
        curfew.residents.size() == 2

        curfew.residents[0].name == 'Other Resident'
        curfew.residents[0].age == '10'
        curfew.residents[0].relationship == 'Son'

        curfew.residents[1].name == 'Yet Another'
        curfew.residents[1].age == '20'
        curfew.residents[1].relationship == 'Wife'

        and: 'I see the review details'
        curfew.reviewAnswers.cautioned == 'No'
        curfew.reviewAnswers.consent == 'Yes'
        curfew.reviewAnswers.homeVisit == 'Yes'
        curfew.reviewAnswers.electricity == 'Yes'
        curfew.reviewAnswers.safety == 'Yes'
    }

    @Unroll
    def 'Does not show subsequent questions when rejected for #reason'() {

        given: 'Address rejected for a reason'
        testData.loadLicence(sample)

        when: 'I view the page'
        to ReviewAddressPage, 'A0001XX'

        then: 'I see the review questions up to the point of rejection'
        curfew.reviewAnswers == answers

        where:
        reason           | sample                                    | answers
        'no consent'     | 'assessment/address-rejected'             | [consent: 'No', electricity: null, homeVisit: null, safety: null, cautioned: 'No']
        'no electricity' | 'assessment/address-rejected-electricity' | [consent: 'Yes', electricity: 'No', homeVisit: null, safety: null, cautioned: 'No']
    }
}
