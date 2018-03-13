package uk.gov.justice.digital.hmpps.licences.specs.review

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewRiskPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReviewRiskSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows risk details eneered by RO'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('processing-ca/final-checks')

        when: 'I view the page'
        to ReviewRiskPage, 'A0001XX'

        then: 'I see the risk management details'
        risk.answers.planningActions == 'No'
        risk.answers.information == 'No'
        risk.answers.victimLiaison == 'No'
    }

}
