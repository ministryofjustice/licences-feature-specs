package uk.gov.justice.digital.hmpps.licences.specs.CA

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

        then: 'I see two HDC eligible prisoners'
        hdcEligible.size() == 1
    }

    @Stage
    def 'Shows licence case summary details (from nomis)'() {

        given:
        def offenderDetails = [
                '.name'      : 'Andrews, Mark',
                '.offenderNo': 'A0001XX',
                '.location'  : 'A-1-1 - Licence Auto Test Prison',
                '.hdced'     : '13/07/2019',
                '.crd'       : '15/10/2019',
                '.status'    : 'Not Started',
        ]

        when: 'I view the case list'
        via CaselistPage

        then: 'I see the expected data for the prisoner'
        offenderDetails.each { item, value ->
            assert hdcEligible[0].find(item).text() == value
        }
    }

    @Unroll
    def 'Shows correct status message when #type'() {

        given: 'a licence exists'
        testData.loadLicence(sample)

        when: 'I view the caselist'
        to CaselistPage

        then: ' The status indicates that the processing has begun'
        hdcEligible[0].find('.status').text() == status

        where:
        type         | sample                    | status
        'Unstarted'  | 'eligibility/started'     | 'Eligibility checks ongoing'
        'Excluded'   | 'eligibility/excluded'    | 'Excluded (Ineligible)'
        'Opted out'  | 'eligibility/optedOut'    | 'Opted out'
        'Sent to RO' | 'processing-ro/unstarted' | 'Submitted to RO'
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
        status                 | style      | sample                           | css
        'Address not suitable' | 'bold red' | 'processing-ca/address-rejected' | '.terminalStateAlert'
        'Postponed'            | 'bold'     | 'processing-ca/postponed'        | '.terminalStateWarn'

    }
}
