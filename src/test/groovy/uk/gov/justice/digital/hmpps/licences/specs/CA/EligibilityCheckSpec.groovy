package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckCrdTimePage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckSuitablePage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class EligibilityCheckSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Starts with nothing selected because there is no default '() {

        when: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        then: 'Neither radio option is selected'
        excludedRadios.checked == null
    }

    def 'Reasons not shown when option is no'() {

        when: 'I view the eligibility checks page'
        at EligibilityCheckPage

        then: 'I do not see reason options'
        !excludedReasonsForm.isDisplayed()
    }

    def 'Reasons are shown when option is yes'(){

        when: 'I view the eligibility checks page'
        at EligibilityCheckPage

        and: 'I select yes for excluded'
        excludedRadios.checked = 'Yes'

        then: 'I see 7 reason options'
        excludedReasonsForm.isDisplayed()
        excludedReasons.size() == 7
    }

    def 'Can view eligibility checks when already started'() {

        given: 'Eligibility checks already done'
        testData.deleteLicences()
        testData.createLicence([
                'nomisId'    : 'A0001XX',
                'eligibility'    : [
                        'excluded'  : ['decision': 'No'],
                        'unsuitable': ['decision': 'Yes']
                ]
        ], 'ELIGIBILITY_CHECKED')

        when: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        then: 'I see the previous values'
        excludedRadios.checked == 'No'
    }

    def 'Modified choices are not saved after return to tasklist'() {

        given: 'On the eligibility checks page'
        at EligibilityCheckPage

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
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        then: 'I see the original values'
        excludedRadios.checked == 'No'
        excludedReasonsItem(0).unchecked
        excludedReasonsItem(1).unchecked
        excludedReasonsItem(4).unchecked
    }

    def 'The suitability form is shown after the excluded form' () {
        given: 'On the eligibility checks page'
        at EligibilityCheckPage

        when: 'I select new options and save'
        excludedRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the suitability form'
        at EligibilityCheckSuitablePage

        when: 'I select yes for unsuitable'
        unsuitableRadios.checked = 'Yes'

        then: 'I see 4 reason options'
        unsuitableReasonsForm.isDisplayed()
        unsuitableReasons.size() == 4
    }

    def 'Modified choices are saved after save and continue'() {

        given: 'On the eligibility checks page'
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        when: 'I select new options'
        excludedRadios.checked = 'Yes'
        excludedReasonsItem(2).check()
        excludedReasonsItem(3).check()
        excludedReasonsItem(5).check()

        then: 'Those options are selected'
        excludedReasonsItem(2).checked
        excludedReasonsItem(3).checked
        excludedReasonsItem(5).checked

        when: 'I choose save and continue'
        find('#continueBtn').click()
        at EligibilityCheckSuitablePage

        find('#backBtn').click()
        at TaskListPage

        and: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        then: 'I see the new values'
        excludedRadios.checked == 'Yes'
        excludedReasonsItem(2).checked
        excludedReasonsItem(3).checked
        excludedReasonsItem(5).checked
    }

    def 'The CRD time form is shown after the suitability form' () {
        given: 'On the eligibility checks page'
        at EligibilityCheckPage

        when: 'I select new options and save'
        excludedRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the suitability form'
        at EligibilityCheckSuitablePage

        when: 'I select yes for unsuitable and continue'
        unsuitableRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I see the crd time form'
        at EligibilityCheckCrdTimePage
    }

    def 'All selections are saved and shown on the task list' () {
        given: 'On the eligibility checks page'
        actions.toEligibilityCheckPageFor('A0001XX')
        at EligibilityCheckPage

        when: 'I select new options and save'
        excludedRadios.checked = 'Yes'
        find('#continueBtn').click()
        at EligibilityCheckSuitablePage

        and: 'I select No for unsuitable and continue'
        unsuitableRadios.checked = 'No'
        find('#continueBtn').click()
        at EligibilityCheckCrdTimePage

        and: 'I select Yes for CRD time and continue'
        crdTimeRadios.checked = 'Yes'
        find('#continueBtn').click()

        then: 'I return to the task list page'
        at TaskListPage

        and: 'I can see my saved answers'
        excludedAnswer.text() == 'Yes'
        unsuitableAnswer.text() == 'No'
        crdTimeAnswer.text() == 'Yes'
    }
}
