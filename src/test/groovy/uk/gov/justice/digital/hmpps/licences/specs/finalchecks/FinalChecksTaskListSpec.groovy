package uk.gov.justice.digital.hmpps.licences.specs.finalchecks

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksPostponePage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewCurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewReportingPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewRiskPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class FinalChecksTaskListSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    @Shared
    def tasks = [
            address    : 'Proposed curfew address',
            curfewHours: 'Curfew hours',
            conditions : 'Additional conditions',
            risk       : 'Risk management and victim liaison',
            reporting  : 'Reporting instructions',
            final      : 'Final checks',
            postpone   : 'Postponement',
            submit     : 'Submit to decision maker'
    ]

    def setupSpec() {
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    @Stage
    def 'Shows details of the prisoner (from nomis)'() {

        when: 'I view the task list page'
        to TaskListPage, 'A0001XX'

        then: 'I see the expected offender details data'
        offender.details.name == 'Andrews, Mark'
        offender.details.nomisId == 'A0001XX'
        offender.details.dob == '22/10/1989'
        offender.details.roName == 'Jessy Jones'
        offender.details.externalLocation == 'Licence Auto Test Prison'
        offender.details.offences == "Cause exceed max permitted wt of artic' vehicle - No of axles/configuration (No MOT/Manufacturer's Plate)"
        offender.details.crd == '15/10/2019'
        offender.details.hdced == '13/07/2019'
        offender.details.photoDate == 'Uploaded: 05/07/2017'

//        Pending stage data
//        offender.details.internalLocation == 'A-1-1'
//        offender.details.sed == '01/08/2019'
//        offender.details.led == '02/08/2019'
//        offender.details.pssed == '03/08/2019'
    }

    def 'Back link goes back to case list'() {

        when: 'I view the page'
        at TaskListPage

        and: 'I click the back to dashboard button'
        $('a', text: 'Back to case list').click()

        then: 'I go back to the dashboard'
        at CaselistPage
    }

    def 'Shows buttons for all tasks with correct label'() {

        given: 'An licence ready for final checks'
        testData.loadLicence('finalchecks/final-checks')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see 8 task buttons'
        taskListActions.size() == 8

        and: 'The tasks for reviewing RO input have View buttons'
        taskListActions.take(5).every { it.text() == 'View' }

        and: 'The final checks task has a Start button'
        taskListAction(tasks.final).text() == 'Start'

        and: 'The postpone task has a Postpone button'
        taskListAction(tasks.postpone).value() == 'Postpone'  // NB value, not text - button, not link
    }

    def 'Shows submit button when all tasks done'() {

        given: 'An licence ready for final checks'
        testData.loadLicence('finalchecks/final-checks-done')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see 9 task buttons'
        taskListActions.size() == 9

        and: 'The submit task has a Continue button'
        taskListAction(tasks.submit).text() == 'Continue'
    }

    @Unroll
    def '#task button links to #page'() {

        given: 'Viewing task list'
        to TaskListPage, 'A0001XX'

        when: 'I start the task'
        taskListAction(task).click()

        then: 'I see the journey page'
        at page

        when: 'I click back'
        find('#backBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage

        where:
        task              | page
        tasks.address     | ReviewAddressPage
        tasks.curfewHours | ReviewCurfewHoursPage
        tasks.conditions  | ReviewConditionsPage
        tasks.risk        | ReviewRiskPage
        tasks.reporting   | ReviewReportingPage
        tasks.final       | FinalChecksSeriousOffencePage
        tasks.submit      | SendPage
    }

    def 'I can submit the licence to the DM'() {

        given: 'At task list'
        to TaskListPage, 'A0001XX'

        when: 'I press submit to decision maker'
        taskListAction(tasks.submit).click()

        then: 'I see the submit to DM page'
        at SendPage

        and: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I see the confirmation page'
        at SentPage

        when: 'I click return to case list'
        find('#backBtn').click()

        then: 'I return to the case list'
        at CaselistPage
    }

    def 'When address has been rejected other licence review tasks not shown'() {

        given: 'The address has been rejected'
        testData.loadLicence('finalchecks/address-rejected')

        when: 'I view the tasklist'
        to TaskListPage, 'A0001XX'

        then: 'I see only address, final checks, postpone, submit'
        taskListActions.size() == 4
    }
}
