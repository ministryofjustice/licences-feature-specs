package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsAdditionalPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsSummaryPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class LicenceConditionsSummarySpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Saved values shown on the review screen' () {

        given: 'I have already entered some conditions'
        testData.loadLicence('assessment/conditions-multiple')

        when: 'I view the conditions summary page'
        to LicenceConditionsSummaryPage, 'A0001XX'

        then: 'I see the previously selected values'
        conditions.additionalConditions.size() == 5
        conditions.additionalConditionsContent[3].contains('sample input')
    }

    def 'Add another condition button returns to additional conditions page' () {

        given: 'On the conditions summary page'
        at LicenceConditionsSummaryPage

        when: 'I choose to add another condition'
        find('#addAnother').click()

        then: 'I see the additional conditions page'
        at LicenceConditionsAdditionalPage
    }

    def 'Edit condition returns to the additional conditions screen' () {

        when: 'I view the conditions summary page'
        find('#continueBtn').click()
        at LicenceConditionsSummaryPage

        then: 'I see an edit condition link for each condition in the sample licence we loaded'
        conditions.editConditionLinks.size() == 5
        conditions.editConditionLinks[0].getAttribute('href').endsWith('#NOCONTACTPRISONER')
        conditions.editConditionLinks[1].getAttribute('href').endsWith('#NORESIDE')
        conditions.editConditionLinks[2].getAttribute('href').endsWith('#NOTIFYRELATIONSHIP')
        conditions.editConditionLinks[3].getAttribute('href').endsWith('#HOMEVISITS')
        conditions.editConditionLinks[4].getAttribute('href').endsWith('#bespoke-0')

        when: 'I click edit condition'
        conditions.editConditionLinks[0].click()

        then: 'I see the additional conditions screen'
        at LicenceConditionsAdditionalPage

        and: 'The link goes to the named anchor for the condition'
        browser.currentUrl.endsWith('#NOCONTACTPRISONER')
    }

    def 'Delete condition removes the condition and redisplays the summary screen' () {

        when: 'I view the conditions summary page'
        find('#continueBtn').click()
        at LicenceConditionsSummaryPage

        then: 'I see a delete condition link for each condition'
        conditions. deleteConditionLinks.size() == 5

        and: 'I see the condition that will be deleted'
        conditions.additionalConditionsContent[3].contains('sample input')

        when: 'I click delete'
        conditions.deleteConditionLinks[3].click()

        then: 'I return to the conditions summary page'
        at LicenceConditionsSummaryPage

        and: 'The deleted condition is no longer shown'
        conditions.additionalConditions.size() == 4
        conditions.editConditionLinks.size() == 4
        conditions.deleteConditionLinks.size() == 4
    }

    def 'Can also delete bespoke conditions' () {

        given: 'Viewing the conditions summary'
        at LicenceConditionsSummaryPage

        and: 'I can see the bespoke condition'
        conditions.additionalConditions.size() == 4
        conditions.additionalConditionsContent[3].contains('bespoke text')

        when: 'I delete the bespoke condition'
        conditions.deleteConditionLinks[3].click()

        then: 'The deleted condition is no longer shown'
        conditions.additionalConditions.size() == 3
        conditions.editConditionLinks.size() == 3
        conditions.deleteConditionLinks.size() == 3
    }
}
