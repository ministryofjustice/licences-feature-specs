package uk.gov.justice.digital.hmpps.licences.specs.OM

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.Database
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class TasklistSpec extends GebReportingSpec {

    @Shared TestData testData = new TestData()
    @Shared Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('OM')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows licences requiring information'() {

        given: 'No licences started'
        testData.deleteLicences()

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see two licences with information required'
        infoRequiredLicences.size() == 2
    }

    def 'Shows the right button text depending on licence processing status'() {

        given: 'Licence for A6627JH has been started'
        testData.createLicence(['nomisId' : 'A6627JH'], 'STARTED')

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see a start button for the not started licence'
        infoRequiredLicences[0].find('.requiredButton').text() == 'Start'

        and: 'I see a continue button for the in progress licence'
        infoRequiredLicences[1].find('.requiredButton').text() == 'Continue'

    }

    def 'Shows the licence summary data'() {

        given:
        def offenderDetails = [
                '.requiredName'         : 'Andrews, Mark',
                '.requiredNomisId'      : 'A1235HG',
                '.requiredEstablishment': 'HMP Manchester',
                '.requiredDischargeDate': '01/11/2017'
        ]

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see the expected data'
        offenderDetails.each { item, value ->
            assert infoRequiredLicences[0].find(item).text() == value
        }
    }

    def 'Shows licences awaiting OMU approval'() {

        given:
        def offenderDetails = [
                '.sentName'         : 'Andrews, Mark',
                '.sentNomisId'      : 'A1235HG',
                '.sentEstablishment': 'HMP Manchester',
                '.sentDischargeDate': '01/11/2017'
        ]

        and: 'A sent licence'
        testData.createLicence([
                'nomisId'         : 'A1235HG',
                'agencyLocationId': 'ABC'
        ], 'SENT')

        when: 'I view the dashboard'
        via TasklistPage

        then: 'I see the licence awaiting approval'
        awaitingApprovalLicences.size() == 1

        and: 'I see the expected data'
        offenderDetails.each { item, value ->
            assert awaitingApprovalLicences[0].find(item).text() == value
        }
    }
}
