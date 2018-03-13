package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.CurfewAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsStandardPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
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
            address   : 'Proposed curfew address',
            conditions: 'Additional conditions',
            risk      : 'Risk management and victim liaison',
            reporting : 'Reporting instructions',
            final     : 'Final checks',
            postpone  : 'Postponement',
            submit    : 'Submit to decision maker'
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

    def 'Shows buttons for all tasks except submit, with correct label'() {

        given: 'An licence ready for final checks'
        testData.loadLicence('processing-ca/unstarted')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see 6 task buttons'
        taskListActions.size() == 6

        and: 'The tasks for reviewing RO input have View buttons'
        taskListAction(tasks.address).text() == 'View'
        taskListAction(tasks.conditions).text() == 'View'
        taskListAction(tasks.risk).text() == 'View'
        taskListAction(tasks.reporting).text() == 'View'

        and: 'The final checks task has a Start buttons'
        taskListAction(tasks.final).text() == 'Start'

        and: 'The postpone task has a Postpone buttons'
        taskListAction(tasks.final).text() == 'Postpone'
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
        task             | page
        tasks.address    | CurfewAddressReviewPage
        tasks.conditions | LicenceConditionsStandardPage
        tasks.risk       | RiskManagementPage
        tasks.reporting  | ReportingInstructionsPage
        tasks.final      | FinalChecksPage
        tasks.postpone   | postponePage
    }
}
