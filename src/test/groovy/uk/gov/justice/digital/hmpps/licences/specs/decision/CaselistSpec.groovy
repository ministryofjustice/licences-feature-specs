package uk.gov.justice.digital.hmpps.licences.specs.decision

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CaselistSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('DM')
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
        type        | sample                  | status          | tab
        'Unstarted' | 'decision/unstarted'    | 'Make decision' | 'ready'
        'Approved'  | 'decision/approved'     | 'Approved'      | 'approved'
        'Postponed' | 'finalchecks/postponed' | 'Postponed'     | 'postponed'
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
        status          | label       | sample               | tab
        'Make decision' | 'Start now' | 'decision/unstarted' | 'ready'
        'Approved'      | 'View'      | 'decision/approved'  | 'approved'
    }

    @Unroll
    def 'Does not show button when stage is #stage'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage

        then: 'Button depends on stage'
        find('a.button').size() == 0

        where:
        stage           | sample
        'UNSTARTED'     | 'unstarted/unstarted'
        'ELIGIBILITY'   | 'eligibility/unstarted'
        'PROCESSING_RO' | 'assessment/unstarted'
        'PROCESSING_CA' | 'finalchecks/unstarted'
        'PROCESSING_CA' | 'finalchecks/postponed'
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
        stage      | sample               | target      | tab
        'APPROVAL' | 'decision/unstarted' | '/taskList' | 'ready'
        'DECIDED'  | 'decision/approved'  | '/taskList' | 'approved'
    }
}
