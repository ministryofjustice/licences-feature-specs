package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class TaskListSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('CA_USER')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows details of the prisoner'() {

        given:
        def prisonerDetails = [
                '#prisonerName'        : 'Andrews, Mark',
                '#prisonerAliases'     : 'Marky Mark, Big Mark',
                '#prisonerPrisonNumber': 'A1235HG',
                '#prisonerDob'         : '22/10/1989',

//                '#prisonerLocation'    : 'HMP Berwyn',
//                '#prisonerOffences'    : 'Robbery, conspiracy to rob',
//                '#prisonerCrd'         : '13/06/2018',
//                '#prisonerHdced'       : '11/01/2018',
//                '#prisonerComName'     : 'Emma Spinks',

                '#prisonerPhotoDate'   : 'Uploaded: 09/04/2017'
        ]

        when: 'I view the task list page'
        actions.toTaskListPageFor('A1235HG')
        at TaskListPage

        then: 'I see the expected prisoner details data'
        prisonerDetails.each { item, value ->
            assert prisonerPersonalDetails.find(item).text() == value
        }
    }

    @Ignore('todo')
    def 'Back link goes back to caselist'() {

        when: 'I view the page'
        at TaskListPage

        and: 'I click the back to dashboard button'
        $('a', text: 'Back').click()

        then: 'I go back to the dashboard'
        at CaselistPage
    }

    @Ignore('todo')
    def 'Shows buttons for eligibility check and print address form'() {

        when: 'I view the page'
        actions.toTaskListPageFor('A1235HG')
        at TaskListPage

        then: 'I see a start button for the eligibility check'
        eligibilityCheckStartButton.value() == 'Start'

        and: 'I see a print button for the proposed address form'
        printAddressFormButton.text() == 'Print form'
    }

    @Ignore('todo')
    def 'Start eligibility check button goes to eligibility check page'() {

        given: 'Viewing the task page'
        at TaskListPage

        when: 'I click to start eligibility check'
        eligibilityCheckStartButton.click()

        then: 'I see the eligibility check page'
        at EligibilityCheckPage
    }

    @Ignore('todo')
    def 'Change answers link shown when eligibility check done'() {

        given: 'Eligibility checks already done'
        testData.createLicence([
                'nomisId'    : 'A1235HG',
                'eligibility' : [
                        'excluded': 'false',
                        'unsuitable': 'true',
                        'investigation': 'true'
                ]
        ], 'ELIGIBILITY_CHECKED')

        when: 'I view the tasklist page'
        actions.toTaskListPageFor('A1235HG')
        at TaskListPage

        then: 'I see the change answers link'
        eligibilityCheckUpdateLink.value() == 'Change these answers'
    }

    @Ignore('todo')
    def 'Eligibility answers shown after eligibility check done'() {

        given: 'Eligibility checks already done'
        // follow on from previous

        when: 'Viewing the task page'
        at TaskListPage

        then: 'I see the eligibility answers'
        excludedAnswer == 'No'
        unsuitableAnswer == 'Yes'
        investigationAnswer == 'Yes'

    }

    @Ignore('todo')
    def 'Change answers option removed after form is printed'() {

        given: 'Eligibility checks already done'
        // follow on from previous

        when: 'I view the task page'
        at TaskListPage

        and: 'I press the print form button'
        printEligibilityFormButton.click()

        then: 'I do not see the change answers option'
        !eligibilityCheckUpdateLink.isDisplayed() // not sure this will work
    }

    @Ignore('todo')
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
