package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class RoJourneySpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'From first task, choosing save and continue presents task pages in order' () {

        given: 'A licence record ready to process'
        testData.loadLicence('processing-ro/unstarted')

        and: 'I view the tasklist page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the address review task'
        taskListAction('Proposed curfew address').click()

        then:
        at ProposedAddressReviewPage

        when:
        find('#continueBtn').click()

        then:
        at CurfewHoursPage

        when:
        find('#continueBtn').click()

        then:
        at StandardConditionsPage

        when:
        find('#continueBtn').click()

        then:
        at RiskManagementPage

        when:
        find('#continueBtn').click()

        then:
        at ReportingInstructionsPage

        when:
        find('#continueBtn').click()

        then:
        at TaskListPage
    }
}
