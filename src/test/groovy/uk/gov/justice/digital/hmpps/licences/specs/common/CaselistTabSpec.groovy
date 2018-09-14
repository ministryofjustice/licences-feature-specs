package uk.gov.justice.digital.hmpps.licences.specs.common

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import spock.lang.Ignore
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
@Ignore
class CaselistTabSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    @Shared
    def markAndrews = testData.markAndrewsBookingId

    @Shared
    def licences = [
            (markAndrews): 'eligibility/unstarted',
            '2': 'assessment/unstarted',
            '3': 'finalchecks/unstarted',
            '4': 'finalchecks/postponed',
            '5': 'decision/unstarted',
            '6': 'decision/approved',
            '7': 'decision/refused',

            '8': 'eligibility/started',
            '9': 'assessment/reporting',
            '10': 'decision/address-rejected',
            '11': 'eligibility/optedOut',
    ]

    def setupSpec() {
        testData.deleteLicences()
        licences.each { bookingId, file ->
            testData.addLicence(file, bookingId)
        }
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'put some licences in my database'() {

        when: 'I want some sample licences in my local DB'

        then: 'I can just run this test'

        and: 'Now I can log in as XX_USER_MULTI and see lots of records'
    }

    @Unroll
    def 'shows some licences on the #tab tab for CA'() {

        given: 'logged in'
        actions.logIn('CA_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab           | count
        'ready'       | 13
        'submittedRo' | 2
        'reviewCase'  | 2
        'submittedDm' | 2
        'create'      | 1
    }

    @Unroll
    def 'shows some licences on the #tab tab for RO'() {

        given: 'logged in'
        actions.logOut()
        actions.logIn('RO_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab          | count
        'ready'      | 1
        'checking'   | 1
        'withPrison' | 4
        'approved'   | 1
    }

    @Unroll
    def 'shows some licences on the #tab tab for DM'() {

        given: 'logged in'
        actions.logOut()
        actions.logIn('DM_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab         | count
        'ready'     | 2
        'approved'  | 1
        'postponed' | 1
    }
}
