package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.*
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
        testData.loadLicence('processing-ro/conditions-multiple')

        when: 'I view the conditions summary page'
        actions.toConditionsSummaryPageFor('A0001XX')
        at ConditionsSummaryPage

        then: 'I see the previously selected values'
        additionalConditions.size() == 5
        additionalConditionsWithText('sample input').size() == 1
    }

    def 'Add another condition button returns to additional conditions page' () {

        given: 'On the conditions summary page'
        at ConditionsSummaryPage

        when: 'I choose to add another condition'
        find('#addAnother').click()

        then: 'I see the additional conditions page'
        at AdditionalConditionsPage
    }

    def 'Edit condition returns to the additional conditions screen' () {

        when: 'I view the conditions summary page'
        find('#continueBtn').click()
        at ConditionsSummaryPage

        then: 'I see an edit condition link for each condition in the sample licence we loaded'
        editConditionLinks.size() == 5
        editConditionLinks[0].getAttribute('href').endsWith('#NOCONTACTPRISONER')
        editConditionLinks[1].getAttribute('href').endsWith('#NORESIDE')
        editConditionLinks[2].getAttribute('href').endsWith('#NOTIFYRELATIONSHIP')
        editConditionLinks[3].getAttribute('href').endsWith('#HOMEVISITS')
        editConditionLinks[4].getAttribute('href').endsWith('#bespoke-0')

        when: 'I click edit condition'
        editConditionLinks[0].click()

        then: 'I see the additional conditions screen'
        at AdditionalConditionsPage

        and: 'The link goes to the named anchor for the condition'
        browser.currentUrl.endsWith('#NOCONTACTPRISONER')
    }

    def 'Delete condition removes the condition and redisplays the summary screen' () {

        when: 'I view the conditions summary page'
        find('#continueBtn').click()
        at ConditionsSummaryPage

        then: 'I see a delete condition link for each condition'
        deleteConditionLinks.size() == 5

        and: 'I see the condition that will be deleted'
        additionalConditionsWithText('sample input').size() == 1

        when: 'I click delete'
        deleteConditionLinks[3].click()

        then: 'I return to the conditions summary page'
        at ConditionsSummaryPage

        and: 'The deleted condition is no longer shown'
        additionalConditions.size() == 4
        editConditionLinks.size() == 4
        deleteConditionLinks.size() == 4
        additionalConditionsWithText('sample input').size() == 0
    }

    def 'Can also delete bespoke conditions' () {

        given: 'Viewing the conditions summary'
        at ConditionsSummaryPage

        and: 'I can see the bespoke condition'
        additionalConditions.size() == 4
        additionalConditionsWithText('bespoke text').size() == 1

        when: 'I delete the bespoke condition'
        deleteConditionLinks[3].click()

        then: 'The deleted condition is no longer shown'
        additionalConditions.size() == 3
        editConditionLinks.size() == 3
        deleteConditionLinks.size() == 3
        additionalConditionsWithText('sample input').size() == 0
    }
}
