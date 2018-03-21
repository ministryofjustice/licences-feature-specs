package uk.gov.justice.digital.hmpps.licences.specs.eligibility

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityExclusionPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityTimeCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilitySuitabilityPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class EligibilitySpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('eligibility/unstarted')
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Starts with nothing selected because there is no default'() {

        when: 'I view the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        then: 'Neither radio option is selected'
        excludedRadios.checked == null
    }

    def 'Reasons not shown when option is no'() {

        when: 'I view the eligibility checks page'
        at EligibilityExclusionPage

        then: 'I do not see reason options'
        !excludedReasonsForm.isDisplayed()
    }

    def 'Reasons are shown when option is yes'(){

        when: 'I view the eligibility checks page'
        at EligibilityExclusionPage

        and: 'I select yes for excluded'
        excludedRadios.checked = 'Yes'

        then: 'I see 7 reason options'
        excludedReasonsForm.isDisplayed()
        excludedReasons.size() == 7
    }

    def 'Shows previously saved values'() {

        given: 'Eligibility checks already done'
        testData.loadLicence('eligibility/unsuitable')

        when: 'I view the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        then: 'I see the previous values'
        excludedRadios.checked == 'No'
    }

    def 'Modified choices are not saved after return to tasklist'() {

        given: 'On the eligibility checks page'
        at EligibilityExclusionPage

        when: 'I select new options'
        excludedRadios.checked = 'Yes'
        excludedReasonsItem(0).check()
        excludedReasonsItem(1).check()
        excludedReasonsItem(4).check()

        then: 'Those options are selected'
        excludedReasonsItem(0).checked
        excludedReasonsItem(1).checked
        excludedReasonsItem(4).checked

        when: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        then: 'I see the original values'
        excludedRadios.checked == 'No'
        excludedReasonsItem(0).unchecked
        excludedReasonsItem(1).unchecked
        excludedReasonsItem(4).unchecked
    }

    def 'All selections are saved and shown on the task list' () {

        given: 'On the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        when: 'I select new exclusion options and save'
        excludedRadios.checked = 'No'
        find('#continueBtn').click()
        at EligibilitySuitabilityPage

        and: 'I select new suitability options and save'
        unsuitableRadios.checked = 'No'
        find('#continueBtn').click()
        at EligibilityTimeCheckPage

        and: 'I select new remaining time options and save'
        crdTimeRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I return to the task list page'
        at TaskListPage

        and: 'I can see my saved answers'
        excludedAnswer.text() == 'No'
        unsuitableAnswer.text() == 'No'
        crdTimeAnswer.text() == 'Yes'
    }

    @PendingFeature
    def 'Returns to task list when excluded' () {

        given: 'On the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        when: 'I choose excluded'
        excludedRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I am taken to the task list'
        at TaskListPage

        and: 'Subsequent answers are NA'
        excludedAnswer.text() == 'Yes'
        unsuitableAnswer.text() == 'N/A'
        crdTimeAnswer.text() == 'N/A'
    }

    @PendingFeature
    def 'Returns to task list when unsuitable' () {

        given: 'On the eligibility checks page'
        to EligibilityExclusionPage, 'A0001XX'

        when: 'I choose not exlcuded'
        excludedRadios.checked = 'No'
        find('#continueBtn').click()

        and: 'I choose unsuitable'
        at EligibilitySuitabilityPage
        unsuitableRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I am taken to the task list'
        at TaskListPage

        and: 'Subsequent answers are NA'
        excludedAnswer.text() == 'No'
        unsuitableAnswer.text() == 'Yes'
        crdTimeAnswer.text() == 'N/A'
    }
}