package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
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

        given: 'At task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the reporting instructions task'
        taskListAction('Reporting instructions').click()

        then: 'I see the reporting instructions page'
        at ReportingInstructionsPage

        and: 'The options are unset'
        // todo
        assert(false)
    }

    @PendingFeature
    def 'Modified Reporting instructions not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified Reporting instructions saved on save and continue' () {

    }
}
