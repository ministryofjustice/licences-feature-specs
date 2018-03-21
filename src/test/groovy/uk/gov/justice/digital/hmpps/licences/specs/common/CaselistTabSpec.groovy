package uk.gov.justice.digital.hmpps.licences.specs.common

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CaselistTabSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    @Shared
            licences = [
                    'A0001XX': 'eligibility/unstarted',
                    'A0002XX': 'assessment/unstarted',
                    'A0003XX': 'finalchecks/unstarted',
                    'A0004XX': 'finalchecks/postponed',
                    'A0005XX': 'decision/unstarted',
                    'A0006XX': 'decision/approved',
                    'A0007XX': 'decision/refused',

                    'A0008XX': 'eligibility/started',
                    'A0009XX': 'assessment/reporting',
                    'A0010XX': 'decision/address-rejected',
            ]

    def setupSpec() {
        licences.each { nomisId, file ->
            testData.loadLicence(file, nomisId)
        }
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'shows licences on tabs'() {

        given: 'logged in as CA'
        actions.logIn('CA_MULTI')

        when: 'I view the caselist'
        to CaselistPage

        then: 'I see the right number of prisoners in each tab'
        // tab('Ready to process').click()
        hdcEligible.size() == 10

        // tab('Approved').click()
        // etc
    }

    // log in as RO_MULTI

    // etc
}
