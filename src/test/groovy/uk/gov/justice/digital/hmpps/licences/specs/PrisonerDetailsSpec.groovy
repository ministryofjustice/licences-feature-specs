package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class PrisonerDetailsSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn()
    }

    def cleanupSpec() {
        actions.logOut()
        testData.deleteLicences()
    }

    def 'Shows details of the prisoner'() {

        given:
        def prisonerDetails = [
                '#prisonerName'        : 'Andrews, Mark',
                '#prisonerAliases'     : 'Marky Mark',
                '#prisonerPrisonNumber': 'A1235HG',
                '#prisonerDob'         : '22/10/1989',
                '#prisonerSex'         : 'Male',
                '#prisonerLocation'    : 'HB1 | L2 | Cell 3'
        ]

        when: 'I view the prisoner details page'
        actions.toDetailsPageFor('A1235HG')
        at PrisonerDetailsPage

        then: 'I see the expected prisoner details data'
        prisonerDetails.each { item, value ->
            assert prisonerPersonalDetails.find(item).text() == value
        }
    }

    def 'Shows key dates'() {

        given:
        def keyDates = [
                '#releaseDate'    : '10/11/2017',
                '#licenceExpires' : '10/06/2018',
                '#sentenceExpires': '10/06/2018'
        ]

        when: 'I view the prisoner details page'
        at PrisonerDetailsPage

        then: 'I see the key dates data'
        keyDates.each { item, value ->
            assert prisonerKeyDates.find(item).text() == value
        }
    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the prisoner details page'
        at PrisonerDetailsPage

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {

        when: 'I view the prisoner details page'
        at PrisonerDetailsPage

        and: 'I click the back to dashboard button'
        footerButtons.clickBack

        then: 'I go back to the dashboard'
        at TasklistPage
    }
}