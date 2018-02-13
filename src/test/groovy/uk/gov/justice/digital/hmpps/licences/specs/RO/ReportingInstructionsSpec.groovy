package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReportingInstructionsSpec extends GebReportingSpec {

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
    def 'Reporting instructions initially blank' () {

    }

    @PendingFeature
    def 'Modified Reporting instructions not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified Reporting instructions saved on save and continue' () {

    }
}
