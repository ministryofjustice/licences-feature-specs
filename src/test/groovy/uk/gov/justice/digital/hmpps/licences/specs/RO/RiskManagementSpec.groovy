package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class RiskManagementSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        testData.loadLicence('processing-ro/unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Options initially blank' () {

        given: 'At task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the risk management task'
        taskListAction('Risk management and victim liaison').click()

        then: 'I see the risk management page'
        at RiskManagementPage

        and: 'The options are unset'
        riskManagementRadios.checked == null
        awaitingInformationRadios.checked == null
        victimLiaisonRadios.checked == null
    }

    def 'Risk management details shown when YES' () {

        when: 'At risk management page'
        at RiskManagementPage

        then: 'I dont see the details form'
        !riskManagementForm.isDisplayed()

        when: 'I select yes for risk management'
        riskManagementRadios.checked = 'Yes'

        then: 'I see the details form'
        riskManagementForm.isDisplayed()
    }

    def 'Awaiting information details shown when YES' () {

        when: 'At risk management page'
        at RiskManagementPage

        then: 'I dont see the details form'
        !awaitingInformationForm.isDisplayed()

        when: 'I select yes for awaiting information'
        awaitingInformationRadios.checked = 'Yes'

        then: 'I see the details form'
        awaitingInformationForm.isDisplayed()
    }

    def 'Victim liaison details shown when YES' () {

        when: 'At risk management page'
        at RiskManagementPage

        then: 'I dont see the details form'
        !victimLiaisonForm.isDisplayed()

        when: 'I select yes for victim liaison'
        victimLiaisonRadios.checked = 'Yes'

        then: 'I see the details form'
        victimLiaisonForm.isDisplayed()
    }

    def 'Modified options not saved on return to tasklist' () {

        given:  'At risk management page'
        at RiskManagementPage

        when: 'I select new options'
        riskManagementRadios.checked = 'Yes'
        victimLiaisonRadios.checked = 'Yes'

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the risk management page'
        actions.toRiskManagementPageFor('A0001XX')
        at RiskManagementPage

        then: 'I see the original values'
        riskManagementRadios.checked == null
        victimLiaisonRadios.checked == null
    }

    def 'Modified choices are saved after save and continue' () {


        given:  'At risk management page'
        at RiskManagementPage

        when: 'I select new options'
        riskManagementRadios.checked = 'Yes'
        victimLiaisonRadios.checked = 'No'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I return to the risk management page'
        actions.toRiskManagementPageFor('A0001XX')
        at RiskManagementPage

        then: 'I see the previously entered values'
        riskManagementRadios.checked == 'Yes'
        victimLiaisonRadios.checked == 'No'
    }
}
