package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import groovy.json.JsonSlurper
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.FeedbackPage
import uk.gov.justice.digital.hmpps.licences.pages.HealthPage
import uk.gov.justice.digital.hmpps.licences.pages.IndexPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage

@Stepwise
class WebsiteSpec extends GebReportingSpec {


    def 'Application title is shown'() {

        when: 'Viewing the website'
        to IndexPage

        then: 'application title is shown'
        header.applicationTitle == 'Licences Application'
    }

    def 'health page shows application status'() {

        when: 'Viewing the health page'
        to HealthPage

        then: 'I see health status OK'

        def json = driver.pageSource - '<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">'
        def response = new JsonSlurper().parseText(json)

        response.healthy == true
        response.checks.db == 'OK'
        response.checks.licences == 'OK'
    }

    def 'feedback link shows feedback page'() {

        given: 'Viewing the website'
        to IndexPage

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'I see the feedback page'
        at FeedbackPage

        and: 'I see an email link'
        feedbackMailtoLink.isDisplayed()
    }

    def 'feedback page returns to previous page' () {

        given: 'Viewing a prisoner details page'
        toDetailsPageFor('A1235HG')

        when: 'I click the feedback link'
        header.feedbackLink.click()

        then: 'I see the feedback page'
        at FeedbackPage

        when: 'I click the back link'
        backLink.click()

        then: 'I see the prisoner details page again'
        at PrisonerDetailsPage
    }

    def toDetailsPageFor(nomisId) {
        to TasklistPage
        viewDetailsFor(nomisId)
        at PrisonerDetailsPage
    }
}
