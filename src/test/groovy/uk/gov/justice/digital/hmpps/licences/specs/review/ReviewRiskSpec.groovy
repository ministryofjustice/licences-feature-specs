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

    def 'Shows risk answers entered by RO'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('finalchecks/final-checks')

        when: 'I view the page'
        to ReviewRiskPage, 'A0001XX'

        then: 'I see the risk management answers'
        risk.answers.planningActions == 'No'
        risk.answers.information == 'No'
        risk.answers.victimLiaison == 'No'
    }

    def 'Also shows risk details entered by RO when there are risk issues'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('finalchecks/risks')

        when: 'I view the page'
        to ReviewRiskPage, 'A0001XX'

        then: 'I see the risk management details'
        risk.answers.planningActionsDetail == 'Risk details'
        risk.answers.informationDetail == 'Information details'
        risk.answers.victimLiaisonDetail == 'Victim details'
    }

}
