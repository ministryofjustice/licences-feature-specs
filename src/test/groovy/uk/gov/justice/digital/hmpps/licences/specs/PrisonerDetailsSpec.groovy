package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage

@Stepwise
class PrisonerDetailsSpec extends GebReportingSpec {

    def setupSpec() {
        go '/logout'
        to SigninPage
        signIn
        at TasklistPage
    }

    def 'Shows personal details of the prisoner'() {

        given:
        def prisonerDetails = [
                '#prisonerName'        : 'Andrews, Mark',
                '#prisonerAliases'     : 'Marky Mark',
                '#prisonerPrisonNumber': 'A1235HG',
                '#prisonerDob'         : '22/10/1989',
                '#prisonerSex'         : 'Male',
                '#prisonerLocation'    : 'HB1 | L2 | Cell 3'
        ]

        when: 'I view the personal details page'
        toDetailsPageFor('A1235HG')

        then: 'I see the expected personal details data'
        prisonerDetails.each { item, value ->
            prisonerPersonalDetails.find(item).text() == value
        }
    }

    def 'Shows key dates'() {

        given:
        def keyDates = [
                '#releaseDate': '10/11/2017',
                '#licenceExpires'  : '10/06/2018',
                '#sentenceExpires' : '10/06/2018'
        ]

        when: 'I view the personal details page'
        toDetailsPageFor('A1235HG')

        then: 'I see the key dates data'
        keyDates.each { item, value ->
            prisonerKeyDates.find(item).text() == value
        }
    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the personal details page'
        toDetailsPageFor('A1235HG')

        then: 'I see a continue button'
        footerButtons.continueButton.value() == 'Continue'

        and: 'I see a back to dashboard button'
        footerButtons.backButton.text() == 'Back to dashboard'
    }

    def 'Back to dashboard button goes back to dashboard'() {
        when: 'I view the personal details page'
        toDetailsPageFor('A1235HG')

        and: 'I click the back to dashboard button'
        footerButtons.backButton.click()

        then: 'I go back to the dashboard'
        at TasklistPage
    }

    def toDetailsPageFor(nomisId) {
        to TasklistPage
        viewDetailsFor(nomisId)
        at PrisonerDetailsPage
    }
}