package uk.gov.justice.digital.hmpps.licences.site

import groovy.json.JsonSlurper
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.FeedbackPage
import uk.gov.justice.digital.hmpps.licences.pages.HealthPage
import uk.gov.justice.digital.hmpps.licences.pages.IndexPage
import uk.gov.justice.digital.hmpps.licences.pages.LoggedinPage
import uk.gov.justice.digital.hmpps.licences.util.SignOnBaseSpec

@Stepwise
class WebsiteSpec extends SignOnBaseSpec {

    def setupSpec() {
        signIn()
    }

    def cleanupSpec() {
        signOut()
    }

    def 'Application title is shown'() {

        when: 'Viewing the website'
        to LoggedinPage

        then: 'application title is shown'
        header.applicationTitle == 'Licences Application'
    }

    def 'feedback link shows feedback page'() {

        given: 'Viewing the website'
        to LoggedinPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'I see the feedback page'
        at FeedbackPage

        and: 'I see an email link'
        feedbackMailtoLink.isDisplayed()
    }

    def 'health page shows application status'() {

        when: 'Viewing the health page'
        to HealthPage

        then: 'I see health status OK'

        def json = driver.pageSource - '<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">'
        def response = new JsonSlurper().parseText(json)

        response.healthy == true
        response.checks.dummy == 'ok'
    }
}
