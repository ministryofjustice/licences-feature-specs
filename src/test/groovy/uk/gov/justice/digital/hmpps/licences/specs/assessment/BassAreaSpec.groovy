package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.BassAreaPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.CurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityExclusionPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class BassAreaSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('assessment/bassArea-unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Answer initially blank' () {

        given: 'At task list page'
        to TaskListPage, testData.markAndrewsBookingId

        when: 'I start the BASS area task'
        taskListAction('BASS address').click()

        then: 'I see the bass area page'
        at BassAreaPage

        and: 'The options are unset'
        areaRadios.checked == null

        and: 'The requested area is shown'
        bass.proposed.town == 'BASS Town'
        bass.proposed.county == 'BASS County'
    }

    def 'Reason input is shown when option is yes'() {

        when: 'I view the bass area page'
        at BassAreaPage

        and: 'I select yes'
        areaRadios.checked = 'Yes'

        then: 'I do not see reason options'
        areaReasons.isDisplayed()
    }

    def 'Reason input is shown when option is no'(){

        when: 'I view the bass area page'
        at BassAreaPage

        and: 'I select no'
        areaRadios.checked = 'No'

        then: 'I see 8 reason options'
        areaReasons.isDisplayed()
    }

    def 'Shows previously saved values'() {

        given: 'Bass area rejected'
        testData.loadLicence('assessment/bassArea-rejected')

        when: 'I view the bass area page'
        to BassAreaPage, testData.markAndrewsBookingId

        then: 'I see the previous values'
        areaRadios.checked == 'No'
        areaReasons.text() == 'Reason'
    }

    def 'Modified choices are not saved after return to tasklist'() {

        given: 'On the bass area page'
        at BassAreaPage

        when: 'I select new options'
        areaRadios.checked = 'Yes'

        and: 'I choose return to tasklist'
        $('#backLink').click()
        at TaskListPage

        and: 'I go back to the bass area page'
        to BassAreaPage, testData.markAndrewsBookingId

        then: 'I see the original values'
        areaRadios.checked == 'No'
    }
}
