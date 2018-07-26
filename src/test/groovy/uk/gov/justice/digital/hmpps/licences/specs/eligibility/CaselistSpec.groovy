package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewLicencePage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CaselistSpec extends GebReportingSpec {

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

    @Unroll
    def 'Shows correct status message when #type'() {

        given: 'a licence exists in a particular state'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'The appropriate status is shown'
        hdcEligible[0].find('.status').text() == status

        where:
        type         | sample                 | status                     | tab
        'Unstarted'  | 'eligibility/started'  | 'Checking eligibility'     | 'ready'
        'Excluded'   | 'eligibility/excluded' | 'Excluded (Ineligible)'    | 'ready'
        'Sent to RO' | 'assessment/unstarted' | 'With responsible officer' | 'submittedRo'
    }

    @Unroll
    'Shows #label button when status is #status'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage, tab

        then: 'Button label depends on status'
        find('a.button').text() == label

        where:
        status         | label   | sample                  | tab
        'Not started'  | 'Start' | 'eligibility/unstarted' | 'ready'
        'Final checks' | 'Start' | 'finalchecks/unstarted' | 'reviewCase'
        'Postponed'    | 'View'  | 'finalchecks/postponed' | 'reviewCase'
    }

    @Unroll
    def 'Button links to #target when stage is #stage'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage, tab

        then: 'Button target depends on stage'
        find('a.button').getAttribute('href').contains(target)

        where:
        stage           | sample                  | target            | tab
        'UNSTARTED'     | 'unstarted/unstarted'   | '/taskList'       | 'ready'
        'ELIGIBILITY'   | 'eligibility/unstarted' | '/taskList'       | 'ready'
        'PROCESSING_RO' | 'assessment/unstarted'  | '/review/licence' | 'submittedRo'
        'PROCESSING_CA' | 'finalchecks/unstarted' | '/taskList'       | 'reviewCase'
        'APPROVAL'      | 'decision/unstarted'    | '/review/licence' | 'submittedDm'
        'DECIDED'       | 'decision/approved'     | '/taskList' | 'create'
    }

    def 'Review button shows licence review with return to caselist option'() {

        given: 'A licence in a review stage'
        testData.loadLicence('assessment/unstarted')

        when: 'I click review'
        via CaselistPage, 'submittedRo'
        find('a.button').click()

        then: 'I see the licence review page'
        at ReviewLicencePage

        when: 'I click return to caselist'
        find('a.button').click()

        then: 'I see the caselist'
        at CaselistPage
    }
}
