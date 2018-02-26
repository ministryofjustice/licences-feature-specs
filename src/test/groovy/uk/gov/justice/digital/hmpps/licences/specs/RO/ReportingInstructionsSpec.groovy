package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
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
        testData.loadLicence('processing-ro/unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Reporting instructions initially blank' () {

        given: 'At task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the reporting instructions task'
        taskListAction('Reporting instructions').click()

        then: 'I see the reporting instructions page'
        at ReportingInstructionsPage

        and: 'The options are unset'
        name.value() == ''
        street.value() == ''
        town.value() == ''
        postcode.value() == ''
        telephone.value() == ''
    }

    def 'Modified Reporting instructions not saved on return to tasklist' () {

        given:  'At reporting instructions page'
        at ReportingInstructionsPage

        when: 'I enter new values'
        name << 'sample name'
        street << 'sample street'
        town << 'sample town'
        postcode << 'AB1 1AB'
        telephone << '0123456789'

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the reporting instructions page'
        actions.toReportingInstructionsPageFor('A0001XX')
        at ReportingInstructionsPage

        then: 'I see the original values'
        name.value() == ''
        street.value() == ''
        town.value() == ''
        postcode.value() == ''
        telephone.value() == ''
    }

    def 'Modified choices are saved after save and continue' () {

        given:  'At reporting instructions page'
        at ReportingInstructionsPage

        when: 'I enter new values'
        name << 'sample name'
        street << 'sample street'
        town << 'sample town'
        postcode << 'AB1 1AB'
        telephone << '0123456789'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I return to the reporting instructions page'
        actions.toReportingInstructionsPageFor('A0001XX')
        at ReportingInstructionsPage

        then: 'I see the previously entered values'
        name.value() == 'sample name'
        street.value() == 'sample street'
        town.value() == 'sample town'
        postcode.value() == 'AB1 1AB'
        telephone.value() == '0123456789'

    }
}
