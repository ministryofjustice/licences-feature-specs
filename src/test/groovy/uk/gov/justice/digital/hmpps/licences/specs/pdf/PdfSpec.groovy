package uk.gov.justice.digital.hmpps.licences.specs.pdf

import geb.Browser
import geb.spock.GebReportingSpec
import groovyx.net.http.RESTClient
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksOnRemandPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.pages.pdf.CreatePdfStartPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewLicencePage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class PdfSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {

    }

    def cleanupSpec() {
        actions.logOut()
    }

    @Ignore
    def 'No option to create PDF if not CA user'() {

        given: 'An approved licence'
        testData.loadLicence('decision/approved')

        when: 'I log in and view the licence details'
        actions.logIn(user)
        to ReviewLicencePage, 'A5001DY'

        then: 'There is no option to create PDF'
        !createLicenceControl.isDisplayed()

        where:
        user << ['RO', 'DM']
    }

    @Ignore
    def 'No option to create PDF if not approved licence'() {

        given: 'A refused licence'
        actions.logIn('CA')
        testData.loadLicence('decision/refused')

        when: 'I view the licence details'
        to ReviewLicencePage, 'A5001DY'

        then: 'There is no option to create PDF'
        !createLicenceControl.isDisplayed()
    }

    @Ignore
    def 'Option to create PDF for CA and approved licence'() {

        given: 'An approved licence'
        testData.loadLicence('decision/approved')

        when: 'I view the licence details'
        to ReviewLicencePage, 'A5001DY'

        then: 'There is an option to create PDF'
        createLicenceControl.isDisplayed()
    }

    @Ignore
    def 'Shows missing values on confirmation page'() {

        given: 'An approved licence with some fields missing'
        testData.loadLicence('decision/approved-missing')

        when: 'I begin creating the PDF licence'
        to CreatePdfStartPage, 'hdc_ap_pss', 'A5001DY'

        then: 'I see some missing details'
        missingDetails*.text().containsAll([
                'Reporting at',
                'Reporting on',
                'Monitoring company telephone number'
        ])
    }
}
