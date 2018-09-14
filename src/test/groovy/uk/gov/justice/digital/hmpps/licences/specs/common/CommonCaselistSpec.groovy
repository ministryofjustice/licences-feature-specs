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
        testData.deleteLicences()
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
        offenders.summary[0].name == 'Mark Andrews'
        offenders.summary[0].nomisId == 'A5001DY'
//        offenders.summary[0].location == 'T-T1-001 - HMP Albany'
//        offenders.summary[0].hdced == '23/08/2019'

        where:
        user | sample
        'CA' | 'eligibility/unstarted'
        'RO' | 'assessment/unstarted'
        'DM' | 'decision/unstarted'
    }

    @Ignore
    def 'paginates large lists'() {

        when: 'I view the case list'
        actions.logOut()
        actions.logIn('CA_MULTI')
        via CaselistPage, 'ready'

        then: 'I see the first 20 offenders'
        hdcEligible.size() == 22
        hdcEligible.getAt(19).isDisplayed() == true
        hdcEligible.getAt(20).isDisplayed() == false
        hdcEligible.getAt(21).isDisplayed() == false
        paginationText.text().contains('Offenders 1 - 20 of 22')

        when: 'I click the next link'
        paginateNext.click()

        then: 'I see the 20th offender and the rest are hidden'
        hdcEligible.getAt(19).isDisplayed() == false
        hdcEligible.getAt(20).isDisplayed() == true
        hdcEligible.getAt(21).isDisplayed() == true
        paginationText.text().contains('Offenders 21 - 22 of 22')

        when: 'I click the previous link'
        paginatePrev.click()

        then: 'I see the fist 20 offenders are displayed again'
        hdcEligible.getAt(19).isDisplayed() == true
        hdcEligible.getAt(20).isDisplayed() == false
        hdcEligible.getAt(21).isDisplayed() == false
        paginationText.text().contains('Offenders 1 - 20 of 22')
    }

    def 'Search for offenders is available for RO'() {

        when: 'I view the case list as an RO'
        actions.logIn('RO')
        via CaselistPage, 'ready'

        then: 'I see the search for an offender option'
        searchOffenderControl.isDisplayed()
    }

    def 'Search for offenders is not available when not RO'() {

        when: 'I view the case list and I am not an RO'
        actions.logIn(user)
        via CaselistPage, 'ready'

        then: 'I do not see the search for an offender option'
        !searchOffenderControl.isDisplayed()

        where:
        user << ['CA', 'DM']
    }
}
