package uk.gov.justice.digital.hmpps.licences.specs.common

import geb.spock.GebReportingSpec
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
        testData.deleteLicences()

        when: 'I view the caselist'
        via CaselistPage

        then: 'I see one HDC eligible prisoner'
        hdcEligible.size() == 1

        where:
        user << ['CA', 'RO', 'DM']
    }

    @Unroll
    @Stage
    def 'Shows licence case summary details (from nomis) for #user'() {

        when: 'I view the case list'
        actions.logIn(user)
        via CaselistPage

        then: 'I see the expected data for the prisoner'
        offenders.summary[0].name == 'Andrews, Mark'
        offenders.summary[0].nomisId == 'A0001XX'
        offenders.summary[0].location == 'A-1-1 - Licence Auto Test Prison'
        offenders.summary[0].hdced == '13/07/2019'
        offenders.summary[0].status == 'Not started'

        where:
        user << ['CA', 'RO', 'DM']
    }

    @Unroll
    def 'Chooses #choose when #present available'() {

        when: 'I view the caselist'
        actions.logIn(user)
        via CaselistPage

        then: 'I see the right date'
        offenders.summary[0].crdArd == expected

        where:
        user | present    | choose | expected
        'CA' | 'CRD, ARD' | 'CRD'  | '15/10/2019'
        'RO' | 'CRD'      | 'CRD'  | '15/10/2019'
        'DM' | 'ARD'      | 'ARD'  | '02/02/2020'
    }
}
