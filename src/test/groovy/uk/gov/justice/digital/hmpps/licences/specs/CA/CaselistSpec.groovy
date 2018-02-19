package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
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
        testData.deleteLicences()
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
    def 'Shows licence case summary details'() {

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

    def 'Shows in progress status when licence record exists and tasks have started'() {

        given: 'a licence exists'
        testData.createLicenceWithJson('A0001XX', '{"nomisId": "A0001XX", "licence": {"eligibility": { "excluded": "No"}}}', 'ELIGIBILITY')

        when: 'I view the caselist'
        via CaselistPage

        then: ' The status indicates that the processing has begun'
        hdcEligible[0].find('.status').text() == 'Eligibility checks ongoing'
    }

}
