package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.HealthPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage
import uk.gov.justice.digital.hmpps.licences.pages.IndexPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class WebsiteSpec extends GebReportingSpec {

    @Shared TestData testData = new TestData()
    @Shared Actions actions = new Actions()

    def setupSpec() {
        actions.logIn()
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'User name is shown'() {
        when: 'Viewing the website'
        to IndexPage

        then: 'logged in user is shown'
        header.user.contains('staff, user')
    }

    def 'User can log out'() {
        given: 'I am viewing the website'
        to IndexPage

        when: 'I click the logout link'
        header.logoutLink.click()

        then: 'I return to login page'
        at SigninPage
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
}
