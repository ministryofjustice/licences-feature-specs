package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ReviewInformationPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class SendSpec extends GebReportingSpec {

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

    def 'After send to OMU, I see the sent confirmation showing agency/establishment'() {

        given: 'An unsent licence'
        testData.createLicence([
                'nomisId'         : 'A6627JH',
                'agencyLocationId': 'ABC'
        ], 'STARTED')

        when: 'I send the licence'
        go '/send/A6627JH'
        at SendPage
        sendLicence

        then: 'The licence record shows that it has been sent'
        testData.findLicenceStatusFor('A6627JH') == 'SENT'

        and: 'Then I see the sent confirmation showing the responsible agency'
        at SentPage
        agencyName == 'HMP Manchester'
    }

}