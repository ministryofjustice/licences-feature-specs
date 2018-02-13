package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class AdditionalConditionsSpec extends GebReportingSpec {

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
    def 'Standard conditions page shown first' () {

    }

    @PendingFeature
    def 'Options initially unset' () {

    }

    @PendingFeature
    def 'Modified options not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified options saved on save and continue' () {

    }

    @PendingFeature
    def 'When additional conditions NOT required, does NOT show additional conditions page' () {

    }

    @PendingFeature
    def 'When additional conditions required, shows additional conditions page' () {

    }

    @PendingFeature
    def 'Additional conditions initially unset' () {

    }

    @PendingFeature
    def 'Modified Additional conditions not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified Additional conditions saved on save and continue' () {

    }
}
