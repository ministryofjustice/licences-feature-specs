package uk.gov.justice.digital.hmpps.licences.specs.common

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

class CommonCaselistSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setup() {

    }

    def cleanup() {
        actions.logOut()
    }

    @Unroll
    @Stage
    def 'Shows the caseload of HDC eligible prisoners for #user'() {

        given: 'No licences started'
        actions.logIn(user)
        testData.loadLicence(sample)

        when: 'I view the caselist'
        via CaselistPage, 'ready'

        then: 'I see one HDC eligible prisoner'
        hdcEligible.size() == 1

        where:
        user | sample
        'CA' | 'eligibility/unstarted'
        'RO' | 'assessment/unstarted'
        'DM' | 'decision/unstarted'
    }

    @Unroll
    @Stage
    def 'Shows licence case summary details (from nomis) for #user'() {

        when: 'I view the case list'
        actions.logIn(user)
        testData.loadLicence(sample)
        via CaselistPage, 'ready'

        then: 'I see the expected data for the prisoner'
        offenders.summary[0].name == 'Andrews, Mark'
        offenders.summary[0].nomisId == 'A0001XX'
        offenders.summary[0].location == 'A-1-1 - Licence Auto Test Prison'
        offenders.summary[0].hdced == '13/07/2019'

        where:
        user | sample
        'CA' | 'eligibility/unstarted'
        'RO' | 'assessment/unstarted'
        'DM' | 'decision/unstarted'
    }

    @Ignore
    @Unroll
    def 'Chooses #choose when #present available'() {

        when: 'I view the caselist'
        actions.logIn(user)
        testData.loadLicence(sample)
        via CaselistPage, 'ready'

        then: 'I see the right date'
        offenders.summary[0].crdArd == expected

        where:
        user | present    | choose | expected     | sample
        'CA' | 'CRD, ARD' | 'CRD'  | '15/10/2019' | 'eligibility/unstarted'
        'RO' | 'CRD'      | 'CRD'  | '15/10/2019' | 'assessment/unstarted'
        'DM' | 'ARD'      | 'ARD'  | '02/02/2020' | 'decision/unstarted'
    }
}
