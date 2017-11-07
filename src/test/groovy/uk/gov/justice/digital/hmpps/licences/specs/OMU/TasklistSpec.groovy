package uk.gov.justice.digital.hmpps.licences.specs.OMU

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class TasklistSpec extends GebReportingSpec {

    @Shared TestData testData = new TestData()
    @Shared Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('OMU')
    }

    def cleanupSpec() {
        actions.logOut()
        testData.deleteLicences()
    }

    def 'Shows licences ready to check'() {

        given:
        def offenderDetails = [
                '.checkingName'         : 'Andrews, Mark',
                '.checkingNomisId'      : 'A1235HG',
                '.checkingEstablishment': 'HMP Manchester',
                '.checkingDischargeDate': '01/11/2017'
        ]

        and: 'A ready to check licence'
        testData.createLicence([
                'nomisId'         : 'A1235HG',
                'agencyLocationId': 'ABC'
        ], 'SENT')

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see the licence awaiting checking'
        checkingLicences.size() == 1

        and: 'I see the expected data'
        offenderDetails.each { item, value ->
            assert checkingLicences[0].find(item).text() == value
        }
    }

    def 'Shows licences awaiting PM approval'() {

        given:
        def offenderDetails = [
                '.checkSentName'         : 'Bryanston, David',
                '.checkSentNomisId'      : 'A6627JH',
                '.checkSentEstablishment': 'HMP Birmingham',
                '.checkSentDischargeDate': '10/07/2017'
        ]

        and: 'A licence sent for PM approval'
        testData.createLicence([
                'nomisId'         : 'A6627JH',
                'agencyLocationId': 'ABC'
        ], 'CHECK_SENT')

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see the licence awaiting approval'
        checkSentLicences.size() == 1

        and: 'I see the expected data'
        offenderDetails.each { item, value ->
            assert checkSentLicences[0].find(item).text() == value
        }
    }
}
