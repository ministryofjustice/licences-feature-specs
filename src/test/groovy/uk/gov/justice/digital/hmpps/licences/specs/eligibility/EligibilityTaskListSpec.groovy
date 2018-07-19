package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityExclusionPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class EligibilityTaskListSpec extends GebReportingSpec {

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

    def 'Shows buttons for eligibility check and print address form'() {

        given: 'An unstarted licence'
        testData.loadLicence('eligibility/unstarted')

        when: 'I view the page'
        to TaskListPage, 'A0001XX'

        then: 'I see a start button for the eligibility check'
        eligibilityCheckStartButton.value() == 'Start'

//        and: 'I see a print button for the proposed address form'
//        printAddressFormButton.text() == 'Print form'
    }

    def 'Start eligibility check button goes to eligibility check page'() {

        given: 'Viewing the task page'
        at TaskListPage

        when: 'I click to start eligibility check'
        eligibilityCheckStartButton.click()

        then: 'I see the eligibility check page'
        at EligibilityExclusionPage
    }

    def 'Change answers link shown when eligibility check done'() {

        given: 'Eligibility checks already done'
        testData.loadLicence('eligibility/done')

        when: 'I view the tasklist page'
        to TaskListPage, 'A0001XX'

        then: 'I see the change answers link'
        eligibilityCheckUpdateLink.text() == 'Change'
    }

    def 'Eligibility answers shown after eligibility check done'() {

        given: 'Eligibility checks already done'
        // follow on from previous

        when: 'Viewing the task page'
        at TaskListPage

        then: 'I see the eligibility answers'
        excludedAnswer.text() == 'No'
        unsuitableAnswer.text() == 'No'
        crdTimeAnswer.text() == 'No'
    }

    def 'Address check start button is not shown when offender is #condition'() {

        when: 'Viewing the tasklist'
        testData.loadLicence("eligibility/${condition}")
        to TaskListPage, 'A0001XX'

        then: 'The address check start button is not shown'
        !taskListAction('Proposed address / opt out request').isDisplayed()

        where:
        condition << ['unstarted', 'excluded', 'unsuitable', 'insufficientTime']
    }

    def 'Address check start button is shown when elgibile'() {

        when: 'Viewing the tasklist'
        testData.loadLicence("eligibility/eligible")
        to TaskListPage, 'A0001XX'

        then: 'The address check start button is shown'
        taskListAction('Proposed address / opt out request').isDisplayed()
    }

    @PendingFeature
    def 'Change answers option removed after form is printed'() {

        given: 'Eligibility checks already done'
        // follow on from previous

        when: 'I view the task page'
        at TaskListPage

        and: 'I press the print form button'
        printEligibilityFormButton.click()

        then: 'I do not see the change answers option'
        !eligibilityCheckUpdateLink.isDisplayed()
    }

    @PendingFeature
    def 'Form printed status shown after form is printed'() {

        given: 'Form has been printed'
        // follow on from previous

        when: 'I view the task page'
        at TaskListPage

        then: 'I see the form printed text'
        eligibilityFormPrintStatusText.conatins('printed')

        and: 'I see the form printed status'
        eligibilityFormPrintStatusIcon.isDisplayed()

        and: 'The form print button text is updated'
        printAddressFormButton.text() == 'Print form again'
    }


}
