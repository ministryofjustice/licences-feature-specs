package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
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
        actions.logIn('CA_USER')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Starts with nothing selected because there is no default '() {

        when: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A1235HG')
        at EligibilityCheckPage

        then: 'Neither radio option is selected'
        excludedRadios.checked == null
        unsuitableRadios.checked == null
        investigationRadios.checked == null
    }

    def 'Reasons not shown when option is no'() {

        when: 'I view the eligibility checks page'
        at EligibilityCheckPage

        then: 'I do not see reason options'
        !excludedReasonsForm.isDisplayed()
        !unsuitableReasonsForm.isDisplayed()
    }

    def 'Reasons are shown when option is yes'(){

        when: 'I view the eligibility checks page'
        at EligibilityCheckPage

        and: 'I select yes for excluded'
        excludedRadios.checked = 'true'

        then: 'I see 7 reason options'
        excludedReasonsForm.isDisplayed()
        excludedReasons.size() == 7

        when: 'I select yes for unsuitable'
        unsuitableRadios.checked = 'true'

        then: 'I see 4 reason options'
        unsuitableReasonsForm.isDisplayed()
        unsuitableReasons.size() == 4
    }

    def 'Can view eligibility checks when already started'() {

        given: 'Eligibility checks already done'
        testData.createLicence([
                'nomisId'    : 'A1235HG',
                'eligibility' : [
                    'excluded': 'false',
                    'unsuitable': 'true'
                ]
        ], 'ELIGIBILITY_CHECKED')

        when: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A1235HG')
        at EligibilityCheckPage

        then: 'I see the previous values'
        excludedRadios.checked == 'false'
        unsuitableRadios.checked == 'true'

        and: 'No value is shown when none was set'
        investigationRadios.checked == null
    }

    def 'Modified choices are not saved after return to tasklist'() {

        given: 'On the eligibility checks page'
        at EligibilityCheckPage

        when: 'I select new options'
        excludedRadios.checked = 'true'
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
        actions.toEligibilityCheckPageFor('A1235HG') // todo update with buttons when ready
        at EligibilityCheckPage

        then: 'I see the original values'
        excludedRadios.checked == 'false'
        excludedReasonsItem(0).unchecked
        excludedReasonsItem(1).unchecked
        excludedReasonsItem(4).unchecked
    }

    def 'Modified choices are saved after save and continue'() {

        given: 'On the eligibility checks page'
        at EligibilityCheckPage

        when: 'I select new options'
        excludedRadios.checked = 'true'
        excludedReasonsItem(2).check()
        excludedReasonsItem(3).check()
        excludedReasonsItem(5).check()

        then: 'Those options are selected'
        excludedReasonsItem(2).checked
        excludedReasonsItem(3).checked
        excludedReasonsItem(5).checked

        when: 'I choose save and continue'
        find('#continueBtn').click()
        at TaskListPage

        and: 'I view the eligibility checks page'
        actions.toEligibilityCheckPageFor('A1235HG') // todo update with buttons when ready
        at EligibilityCheckPage

        then: 'I see the new values'
        excludedRadios.checked == 'true'
        excludedReasonsItem(2).checked
        excludedReasonsItem(3).checked
        excludedReasonsItem(5).checked
    }
}
