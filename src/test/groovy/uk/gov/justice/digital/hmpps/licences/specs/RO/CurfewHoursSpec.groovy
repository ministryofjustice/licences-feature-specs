package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
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
        testData.deleteLicences()
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

    def 'Modified Curfew hours not saved on return to tasklist' () {

        when: 'I enter new values'
        $('#mondayFrom').value('21:20')
        $('#mondayUntil').value('09:30')

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the curfew hours page'
        actions.toCurfewHoursPageFor('A0001XX')
        at CurfewHoursPage

        then: 'I see the original values'
        $('#mondayFrom').value() == '19:00'
        $('#mondayUntil').value() == '07:00'
    }

    def 'Modified Curfew hours saved on save and continue' () {

        when: 'I enter new values'
        $('#mondayFrom').value('21:20')
        $('#mondayUntil').value('09:30')

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I view the curfew hours page'
        actions.toCurfewHoursPageFor('A0001XX')
        at CurfewHoursPage

        then: 'I see the original values'
        $('#mondayFrom').value() == '21:20'
        $('#mondayUntil').value() == '09:30'
    }
}
