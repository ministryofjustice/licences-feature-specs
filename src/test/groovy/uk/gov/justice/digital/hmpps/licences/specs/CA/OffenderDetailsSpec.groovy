package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class OffenderDetailsSpec extends GebReportingSpec {

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
                '#prisonerLocation'    : 'HMP Berwyn',
                '#prisonerOffences'    : 'Robbery, conspiracy to rob',
                '#prisonerCrd'         : '13/06/2018',
                '#prisonerHdced'       : '11/01/2018',
                '#prisonerComName'     : 'Emma Spinks',
        ]

        when: 'I view the prisoner details page'
        actions.toDetailsPageFor('A1235HG')
        at PrisonerDetailsPage

        then: 'I see the expected prisoner details data'
        prisonerDetails.each { item, value ->
            assert prisonerPersonalDetails.find(item).text() == value
        }
    }

    def 'Back link goes back to caselist'() {

        when: 'I view the page'
        at PrisonerDetailsPage

        and: 'I click the back to dashboard button'
        $('a', text: 'Back').click()

        then: 'I go back to the dashboard'
        at CaselistPage
    }

    def 'Shows buttons for eligibility check and print address form'() {

        when: 'I view the page'
        actions.toDetailsPageFor('A1235HG')
        at PrisonerDetailsPage

        then: 'I see a start button for the eligibility check'
        find('#eligibilityCheckStart').value() == 'Start'

        and: 'I see a print button for the proposed address form'
        find('#addressFormPrint').text() == 'Print form'
    }

    def 'Start eligibility check button goes to eligibility check page'() {

        given: 'Viewing the page'
        at PrisonerDetailsPage

        when: 'I click to start eligibility check'
        find('#eligibilityCheckStart').click()

        then: 'I see the eligibility check page'
        at EligibilityCheckPage
    }

    // todo is the button text meant to say View instead of Start when check done previously?
}
