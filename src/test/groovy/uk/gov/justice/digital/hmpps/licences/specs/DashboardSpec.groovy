package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import groovy.json.JsonSlurper
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.DashboardPage
import uk.gov.justice.digital.hmpps.licences.pages.FeedbackPage
import uk.gov.justice.digital.hmpps.licences.pages.HealthPage
import uk.gov.justice.digital.hmpps.licences.pages.IndexPage

@Stepwise
class DashboardSpec extends GebReportingSpec {


    def 'Shows licences requiring information'() {

        when: 'I view the dashboard'
        to DashboardPage

        then: 'I see two licences with information required'
        infoRequiredLicences.size() == 2

    }

    def 'Shows the right button text depending on licence processing status'() {

        when: 'I view the dashboard'
        to DashboardPage

        then: 'I see a start button for the not started licence'
        infoRequiredLicences[0].find('#viewLicence').text() == 'Start'

        and: 'I see a continue button for the in progress licence'
        infoRequiredLicences[1].find('#viewLicence').text() == 'Continue'

    }

    def 'Shows the licence summary data'() {

        when: 'I view the dashboard'
        to DashboardPage

        then: 'I see the expected data'
        infoRequiredLicences[0].find(item).text() == value

        where:

        item                     | value
        '.requiredName'          | 'Andrews, Mark'
        '.requiredNomisId'       | 'A1235HG'
        '.requiredEstablishment' | 'HMP Manchester'
        '.requiredDischargeDate' | '01/11/2017'
    }

}
