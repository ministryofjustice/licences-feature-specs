package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage

@Stepwise
class TasklistSpec extends GebReportingSpec {

    def setupSpec() {
        to SigninPage
        signIn
    }

    def 'Shows licences requiring information'() {

        when: 'I view the dashboard'
        to TasklistPage

        then: 'I see two licences with information required'
        infoRequiredLicences.size() == 2

    }

    def 'Shows the right button text depending on licence processing status'() {

        when: 'I view the dashboard'
        to TasklistPage

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
        to TasklistPage

        then: 'I see the expected data'
        offenderDetails.each { item, value ->
            infoRequiredLicences[0].find(item).text() == value
        }
    }

}
