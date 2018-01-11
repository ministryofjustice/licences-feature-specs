package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
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
                '#prisonerPhotoDate'   : '09/04/2017'
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
}
