package uk.gov.justice.digital.hmpps.licences.specs.decision

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewReportingPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewRiskPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ApprovalTaskListSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    @Shared
    def tasks = [
            address   : 'Proposed curfew address',
            conditions: 'Additional conditions',
            risk      : 'Risk management and victim liaison',
            reporting : 'Reporting instructions',
            decision  : 'Final decision'
    ]

    def setupSpec() {
        actions.logIn('DM')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows buttons for all tasks with correct label'() {

        given: 'An licence ready for final checks'
        testData.loadLicence('decision/unstarted')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see the right number of task buttons'
        taskListActions.size() == 7

        and: 'The tasks for reviewing RO and CA input have View buttons'
        taskListActions.take(5).every { it.text() == 'View' }

        and: 'The final decision task has a Start button'
        taskListAction(tasks.decision).text() == 'Start'
    }


    def 'When address has been rejected other licence review tasks not shown'() {

        given: 'The address has been rejected'
        testData.loadLicence('decision/address-rejected')

        when: 'I view the tasklist'
        to TaskListPage, 'A0001XX'

        then: 'I see only address, final checks, decision'
        taskListActions.size() == 3 // final checks has no button
    }
}
