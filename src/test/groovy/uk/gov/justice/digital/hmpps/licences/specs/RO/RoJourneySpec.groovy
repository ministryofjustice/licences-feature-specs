package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class RoJourneySpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    @PendingFeature
    def 'From first task, choosing save and continue presents task pages in order' () {

    }

    @PendingFeature
    def 'From last task, choosing back presents task pages in reverse order' () {

    }
}
