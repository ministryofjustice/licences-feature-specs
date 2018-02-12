package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.CurfewAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
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
            submit    : 'Submit to OMU'
    ]

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    @Stage
    def 'Shows details of the prisoner'() {

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
        tasks.address    | CurfewAddressReviewPage
        tasks.conditions | StandardConditionsPage
        tasks.risk       | RiskManagementPage
        //tasks.reporting             | ReportingInstructionsPage
    }

    def 'Shows view button for tasks that have been started'() {

        given: 'Tasks started except risk management'
        testData.createLicence([
                'nomisId'              : 'A0001XX',
                'licenceConditions'    : [
                        'curfewAddressReview': '{}',
                        'standardConditions' : [
                                'additionalConditionsRequired': 'No'
                        ]
                ],
                'reportingInstructions': '{}'
        ])

        when: 'I view the page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        then: 'I see 4 task buttons (no submit button shown)'
        taskListActions.size() == 4

        and: 'The buttons for started tasks all say View'
        taskListAction(tasks.address).text() == 'View'
        taskListAction(tasks.conditions).text() == 'View'
        taskListAction(tasks.risk).text() == 'Start'
        taskListAction(tasks.reporting).text() == 'View'
    }

    def 'Shows Submit button when all tasks are done'() {

        given: 'All tasks done'
        // todo update this when we have a definition of minimum for DONE
        // todo updte when curfewAddressReview and riskManagement moved outide licenceConditions
        testData.createLicence([
                'nomisId'              : 'A0002XX',
                'licenceConditions'    : [
                        'curfewAddressReview': '{}',
                        'riskManagement'     : '{}',
                        'standardConditions' : [
                                'additionalConditionsRequired': 'No'
                        ]
                ],
                'reportingInstructions': '{}'
        ])

        when: 'I view the page'
        actions.toTaskListPageFor('A0002XX')
        at TaskListPage

        then: 'I see 5 task buttons'
        taskListActions.size() == 5

        and: 'There is a submit to OMU button'
        taskListAction(tasks.submit).text() == 'Continue'
    }
}































