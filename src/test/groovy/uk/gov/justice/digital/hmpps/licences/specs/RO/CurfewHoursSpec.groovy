package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import org.openqa.selenium.Keys
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CurfewHoursSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('processing-ro/unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Curfew hours initially shows defaults of 7pm to 7am' () {

        when: 'I view the curfew hours page'
        actions.toCurfewHoursPageFor('A0001XX')
        at CurfewHoursPage

        then: 'I see the default values'
        $('#mondayFrom').value() == '19:00'
        $('#mondayUntil').value() == '07:00'
    }

    // NB the way time fields work with ChromeDriver and PhantomJS is different and buggy so it is
    // hard to find a way to test entering values that works in both drivers. Instead, it's sufficient
    // to prove that if values were previously saved then they are shown
    def 'Shows previously saved values' () {

        given: 'a licence containing curfew hours details'
        testData.loadLicence('processing-ro/address-approved-curfew-hours')

        when: 'I view the curfew hours page'
        actions.toCurfewHoursPageFor('A0001XX')
        at CurfewHoursPage

        then: 'I see the save values'
        $('#mondayFrom').value() == '21:22'
        $('#mondayUntil').value() == '08:09'
        $('#sundayFrom').value() == '18:19'
        $('#sundayUntil').value() == '06:07'
    }
}
