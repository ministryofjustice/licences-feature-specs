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

    @Stage
    def 'Shows the caseload of HDC eligible prisoners'() {

        given: 'No licences started'
        testData.deleteLicences()

        when: 'I view the caselist'
        via CaselistPage

        then: 'I see one HDC eligible prisoners'
        hdcEligible.size() == 1
    }

    @Stage
    def 'Shows licence case summary details (from nomis)'() {

        when: 'I view the case list'
        via CaselistPage

        then: 'I see the expected data for the prisoner'
        offenders.summary[0].name == 'Andrews, Mark'
        offenders.summary[0].nomisId == 'A0001XX'
        offenders.summary[0].location == 'Licence Auto Test Prison'
        offenders.summary[0].hdced == '13/07/2019'
        offenders.summary[0].crd == '15/10/2019'
        offenders.summary[0].status == 'Not started'
    }

    @Unroll
    def 'Shows correct status message when #type'() {

        given: 'a licence exists in a particular state'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        to CaselistPage

        then: 'The appropriate status is shown'
        hdcEligible[0].find('.status').text() == status

        where:
        type        | sample               | status
        'Unstarted' | 'decision/unstarted' | 'Awaiting decision'
        'Approved'  | 'decision/approved'  | 'Approved'
        'Refused'   | 'decision/refused'   | 'Refused'
    }

    @Unroll
    'Shows #label button when status is #status'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage

        then: 'Button label depends on status'
        find('a.button').text() == label

        where:
        status              | label   | sample
        'Awaiting decision' | 'Start' | 'decision/unstarted'
        'Approved'          | 'View'  | 'decision/approved'
        'Refused'           | 'View'  | 'decision/refused'
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
    }

    @Unroll
    def 'Button links to #target when stage is #stage'() {

        given: 'A licence'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage

        then: 'Button target depends on stage'
        find('a.button').getAttribute('href').contains(target)

        where:
        stage      | sample               | target
        'APPROVAL' | 'decision/unstarted' | '/taskList'
        'DECIDED'  | 'decision/approved'  | '/taskList'
    }
}