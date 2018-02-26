package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class TaskListSpec extends GebReportingSpec {

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
            submit    : 'Submit to PCA'
    ]

    def setupSpec() {
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    @Stage
    def 'Shows details of the prisoner (from nomis)'() {

        given:
        def prisonerDetails = [
                '#prisonerName'        : 'Andrews, Mark',
                '#prisonerAliases'     : 'Marky Andrews',
                '#prisonerPrisonNumber': 'A0001XX',

                '#prisonerDob'         : '22/10/1989',
                '#prisonerLocation'    : 'Licence Auto Test Prison',
                '#prisonerOffences'    : "Cause exceed max permitted wt of artic' vehicle - No of axles/configuration (No MOT/Manufacturer's Plate)",

                '#prisonerCrd'         : '15/10/2019',
                '#prisonerHdced'       : '13/07/2019',
                '#prisonerComName'     : 'Jessy Jones',

                '#prisonerPhotoDate'   : 'Uploaded: 05/07/2017'
        ]

        when: 'I view the task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        then: 'I see the expected prisoner details data'
        prisonerDetails.each { item, value ->
            assert prisonerPersonalDetails.find(item).text() == value
        }
    }

    def 'Back link goes back to case list'() {

        when: 'I view the page'
        at TaskListPage

        and: 'I click the back to dashboard button'
        $('a', text: 'Back to case list').click()

        then: 'I go back to the dashboard'
        at CaselistPage
    }

    def 'Shows start button for all tasks'() {

        given: 'An unprocessed licence'
        testData.loadLicence('processing-ro/unstarted')

        when: 'I view the page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        then: 'I see 4 task buttons'
        taskListActions.size() == 4

        and: 'The buttons all say Start'
        taskListActions.every { it.text() == 'Start' }
    }

    @Unroll
    def '#task button links to #page'() {

        given: 'Viewing task list'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the task'
        taskListAction(task).click()

        then: 'I see the journey page'
        at page

        when: 'I click back'
        find('#backBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage

        where:
        task             | page
        tasks.address    | ProposedAddressReviewPage
        tasks.conditions | StandardConditionsPage
        tasks.risk       | RiskManagementPage
        tasks.reporting  | ReportingInstructionsPage
    }

    def 'Shows view button for tasks that have been started'() {

        given: 'Tasks started except reporting instructions'
        testData.loadLicence('processing-ro/risks-no')

        when: 'I view the page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        then: 'I see 4 task buttons (no submit button shown)'
        taskListActions.size() == 4

        and: 'The buttons for started tasks all say View'
        taskListAction(tasks.address).text() == 'View'
        taskListAction(tasks.conditions).text() == 'View'
        taskListAction(tasks.risk).text() == 'View'
        taskListAction(tasks.reporting).text() == 'Start'
    }

    def 'Shows Submit button when all tasks are done'() {

        given: 'All tasks done'
        testData.loadLicence('processing-ro/done')

        when: 'I view the page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        then: 'I see 5 task buttons'
        taskListActions.size() == 5

        and: 'There is a submit to OMU button'
        taskListAction(tasks.submit).text() == 'Continue'
    }

    // @Stage todo prep data on stage
    def 'I can submit the licence back to the CA'() {
        given: 'At task list'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I press submit to PCA'
        taskListAction(tasks.submit).click()

        then: 'I see the submit to CA page'
        at SendPage

        and: 'I see contact details for the prison'
        prison.text() == 'HMP Licence Test Prison'
        phones.size() == 2

        and: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I see the confirmation page'
        at SentPage

        when: 'I click return to task list'
        find('#backBtn').click()

        then: 'I return to the tasklist'
        at TaskListPage
    }
}































