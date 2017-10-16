package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import groovy.json.JsonSlurper
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage

@Stepwise
class WebsiteSpec extends GebReportingSpec {

    def setupSpec() {
        to SigninPage
        signIn
    }

    def 'health page shows application status'() {

        when: 'Viewing the health page'
        to HealthPage

        then: 'I see health status OK'

        def json = driver.pageSource - '<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">'
        def response = new JsonSlurper().parseText(json)

        response.healthy == true
        response.checks.db == 'OK'
        response.checks.nomis == 'OK'
    }


    def toDetailsPageFor(nomisId) {
        to TasklistPage
        viewDetailsFor(nomisId)
        at PrisonerDetailsPage
    }
}
