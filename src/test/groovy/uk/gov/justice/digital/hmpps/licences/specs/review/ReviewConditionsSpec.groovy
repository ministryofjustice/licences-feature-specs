package uk.gov.justice.digital.hmpps.licences.specs.review

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewConditionsPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ReviewConditionsSpec extends GebReportingSpec {

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

    def 'Shows conditions details entered by RO'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('review/normal')

        when: 'I view the page'
        to ReviewConditionsPage, 'A0001XX'

        then: 'I see the licence conditions details'
        conditions.additionalConditions.size() == 2

        conditions.additionalConditionsTitle[0] == 'Condition 1'
        conditions.additionalConditionsName[0] == 'Technology -- Cameras and photos'

        conditions.additionalConditionsTitle[1] == 'Condition 2'
        conditions.additionalConditionsContent[1] == 'First bespoke condition'
    }

    def 'Shows message when no additional conditions entered by RO'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('review/no-conditions')

        when: 'I view the page'
        to ReviewConditionsPage, 'A0001XX'

        then: 'I see the licence conditions details'
        conditions.message == 'No additional conditions have been selected.'
    }
}
