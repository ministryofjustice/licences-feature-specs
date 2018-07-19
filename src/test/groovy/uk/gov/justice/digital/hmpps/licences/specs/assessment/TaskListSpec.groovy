package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.CurfewAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsStandardPage
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

        when: 'I view the task list page'
        to TaskListPage, 'A0001XX'

        then: 'I see the expected offender details data'
        offender.details.name == 'Mark Andrews'
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

    def 'Shows start button for all tasks except submit'() {

        given: 'An unprocessed licence'
        testData.loadLicence('assessment/unstarted')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see the task buttons and the submit button'
        taskListActions.size() == 7

        and: 'The buttons all say Start'
        taskListActions.take(5).every { it.text() == 'Start' }
        taskListActions.last().text() == 'Continue'
    }

    @Unroll
    def '#task button links to page'() {

        given: 'Viewing task list'
        to TaskListPage, 'A0001XX'

        when: 'I start the task'
        taskListAction(task).click()

        then: 'I see the journey page'
        at page

        where:
        task             | page
        tasks.address    | CurfewAddressReviewPage
        tasks.conditions | LicenceConditionsStandardPage
        tasks.risk       | RiskManagementPage
        tasks.reporting  | ReportingInstructionsPage
    }

    def 'Shows view button for tasks that have been started'() {

        given: 'Tasks started except reporting instructions'
        testData.loadLicence('assessment/risks-no')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see the task buttons and the submit button'
        taskListActions.size() == 7

        and: 'The buttons for started tasks all say View'
        taskListAction(tasks.address).text() == 'View'
        taskListAction(tasks.conditions).text() == 'View'
        taskListAction(tasks.risk).text() == 'View'
        taskListAction(tasks.reporting).text() == 'Start'
    }

    def 'Shows Submit button even when tasks are not done'() {

        given: 'Tasks not all done'
        testData.loadLicence('assessment/unstarted')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see the task buttons'
        taskListActions.size() == 7

        and: 'There is a submit to OMU button'
        taskListAction(tasks.submit).text() == 'Continue'
    }

    // @Stage todo prep data on stage
    def 'I can submit the licence back to the CA'() {

        given: 'All tasks done'
        testData.loadLicence('assessment/done')

        when: 'I press submit to PCA'
        to TaskListPage, 'A0001XX'
        taskListAction(tasks.submit).click()

        then: 'I see the licence details page'
        at LicenceDetailsPage

        when: 'I click continue'
        find('#continueBtn').click()

        then: 'I see the submit to CA page'
        at SendPage

        and: 'I see contact details for the prison'
        prison.text() == 'HMP Licence Test Prison'
        phones.size() == 2

        and: 'I can click to submit'
        find('#continueBtn').click()

        then: 'I see the confirmation page'
        at SentPage

        when: 'I click return to case list'
        find('#backBtn').click()

        then: 'I return to the case list'
        at CaselistPage
    }

    def 'Rejecting address obviates subsequent tasks but still allows submission'() {

        given: 'The address has been rejected'
        testData.loadLicence('assessment/address-rejected')

        when: 'I view the tasklist'
        to TaskListPage, 'A0001XX'

        then: 'I see only the address and submit tasks'
        taskListActions.size() == 2
        taskListAction(tasks.address).text() == 'View'
        taskListAction(tasks.submit).text() == 'Continue'

        and: 'The licence is ready to submit'
        $('#submitPcaStatus').text() == 'Ready to submit'
    }
}































