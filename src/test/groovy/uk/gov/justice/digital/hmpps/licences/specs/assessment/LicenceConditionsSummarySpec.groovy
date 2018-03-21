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
        conditions.additional.size() == 5
        conditions.additional[3].content.contains('sample input')
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

        conditions.additional.every { it.editControl != null }

        conditions.additional[0].editControl.getAttribute('href').endsWith('#NOCONTACTPRISONER')
        conditions.additional[1].editControl.getAttribute('href').endsWith('#NORESIDE')
        conditions.additional[2].editControl.getAttribute('href').endsWith('#NOTIFYRELATIONSHIP')
        conditions.additional[3].editControl.getAttribute('href').endsWith('#HOMEVISITS')
        conditions.additional[4].editControl.getAttribute('href').endsWith('#bespoke-0')

        when: 'I click edit condition'
        conditions.additional[0].editControl.click()

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
        conditions.additional.every { it.deleteControl != null }

        and: 'I see the condition that will be deleted'
        conditions.additional[3].content.contains('sample input')

        when: 'I click delete'
        conditions.additional[3].deleteControl.click()

        then: 'I return to the conditions summary page'
        at LicenceConditionsSummaryPage

        and: 'The deleted condition is no longer shown'
        conditions.additional.size() == 4
    }

    def 'Can also delete bespoke conditions' () {

        given: 'Viewing the conditions summary'
        at LicenceConditionsSummaryPage

        and: 'I can see the bespoke condition'
        conditions.additional.size() == 4
        conditions.additional[3].content.contains('bespoke text')

        when: 'I delete the bespoke condition'
        conditions.additional[3].deleteControl.click()

        then: 'The deleted condition is no longer shown'
        conditions.additional.size() == 3
    }
}