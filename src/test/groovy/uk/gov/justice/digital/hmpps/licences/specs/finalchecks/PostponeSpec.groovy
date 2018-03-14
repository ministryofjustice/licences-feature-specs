package uk.gov.justice.digital.hmpps.licences.specs.finalchecks

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksOnRemandPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class PostponeSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Postpone task button starts as Postpone and toggles to Resume'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('finalchecks/unstarted')

        when: 'I view the tasklist'
        to TaskListPage, 'A0001XX'

        then: 'The button label is Postpone'
        taskListAction('Postponement').value() == 'Postpone'

        when: 'I postpone'
        taskListAction('Postponement').click()

        then: 'The button label is Resume'
        at TaskListPage
        taskListAction('Postponement').value() == 'Resume'

        when: 'I resume'
        taskListAction('Postponement').click()

        then: 'The button label is Postpone'
        at TaskListPage
        taskListAction('Postponement').value() == 'Postpone'
    }
}
