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
        actions.logIn('CA')
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

        then: 'I see one HDC eligible prisoner'
        hdcEligible.size() == 1
    }

    @Stage
    def 'Shows licence case summary details (from nomis)'() {

        when: 'I view the case list'
        via CaselistPage

        then: 'I see the expected data for the prisoner'
        offenders.summary[0].name == 'Andrews, Mark'
        offenders.summary[0].nomisId == 'A0001XX'
        offenders.summary[0].location == 'A-1-1 - Licence Auto Test Prison'
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
        type         | sample                 | status
        'Unstarted'  | 'eligibility/started'  | 'Eligibility checks ongoing'
        'Excluded'   | 'eligibility/excluded' | 'Excluded (Ineligible)'
        'Opted out'  | 'eligibility/optedOut' | 'Opted out'
        'Sent to RO' | 'assessment/unstarted' | 'Submitted to RO'
    }

    @Unroll
    def 'Shows status in #style when status is #status'() {

        given: 'A licence where #condition'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage

        then: 'The status is marked with #style'
        hdcEligible[0].find(css).text() == status

        where:
        status                 | style      | sample                         | css
        'Address not suitable' | 'bold red' | 'finalchecks/address-rejected' | '.terminalStateAlert'
        'Postponed'            | 'bold'     | 'finalchecks/postponed'        | '.terminalStateWarn'
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
        status         | label   | sample
        'Not started'  | 'Start' | 'eligibility/unstarted'
        'Final checks' | 'Start' | 'finalchecks/unstarted'
        'Postponed'    | 'View'  | 'finalchecks/postponed'
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
        stage           | sample                  | target
        'UNSTARTED'     | 'unstarted/unstarted'   | '/taskList'
        'ELIGIBILITY'   | 'eligibility/unstarted' | '/taskList'
        'PROCESSING_RO' | 'assessment/unstarted'  | '/review/licence'
        'PROCESSING_CA' | 'finalchecks/unstarted' | '/taskList'
        'APPROVAL'      | 'decision/unstarted'    | '/review/licence'
        'DECIDED'       | 'decision/approved'     | '/review/licence'
    }

    def 'Review button shows licence review with return to caselist option' () {

        given: 'A licence in a review stage'
        testData.loadLicence('assessment/unstarted')

        when: 'I click review'
        via CaselistPage
        find('a.button').click()

        then: 'I see the licence review page'
        at ReviewLicencePage

        when: 'I click return to caselist'
        find('a.button').click()

        then: 'I see the caselist'
        at CaselistPage
    }
}