package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ReviewInformationPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
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

    def 'Send button changes licence status to sent'() {

        given: 'An unsent licence'
        testData.createLicence(['nomisId' : 'A6627JH'], 'STARTED')

        when: 'I view the send page'
        go '/send/A6627JH'
        at SendPage

        and: 'I send the licence'
        sendLicence

        then: 'The licence record shows that it has been sent'
        testData.findLicenceStatusFor('A6627JH') == 'SENT'
    }
}