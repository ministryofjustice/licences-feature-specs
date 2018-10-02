package uk.gov.justice.digital.hmpps.licences.specs.assessment

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
        actions.logIn('RO')
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
        type        | sample                 | status               | tab
        'Unstarted' | 'assessment/unstarted' | 'Address provided'   | 'ready'
        'Doing'     | 'assessment/reporting' | 'Assessment ongoing' | 'checking'
        'Done'      | 'assessment/done'      | 'Assessment ongoing' | 'checking'
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
        status               | label       | sample                 | tab
        'RAddress provided'  | 'Start now' | 'assessment/unstarted' | 'ready'
        'Assessment ongoing' | 'Continue'  | 'assessment/reporting' | 'checking'
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
        stage         | sample
        'UNSTARTED'   | 'unstarted/unstarted'
        'ELIGIBILITY' | 'eligibility/unstarted'
    }

    @Unroll
    def 'Button links to #target when stage is #stage'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist tab'
        to CaselistPage, tab

        then: 'Button target depends on stage'
        find('a.button').getAttribute('href').contains(target)

        where:
        stage           | sample                  | target            | tab
        'PROCESSING_RO' | 'assessment/unstarted'  | '/taskList'       | 'ready'
        'PROCESSING_CA' | 'finalchecks/unstarted' | '/review/licence' | 'withPrison'
        'APPROVAL'      | 'decision/unstarted'    | '/review/licence' | 'withPrison'
        'DECIDED'       | 'decision/approved'     | '/taskList'       | 'approved'
    }
}
