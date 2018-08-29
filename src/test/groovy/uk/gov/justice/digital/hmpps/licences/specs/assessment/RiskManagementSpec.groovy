package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.assessment.RiskManagementPage
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
        testData.loadLicence('assessment/unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Options initially blank' () {

        given: 'At task list page'
        to TaskListPage, testData.markAndrewsBookingId

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

    def 'Modified choices are saved after save and continue' () {


        given:  'At risk management page'
        at RiskManagementPage

        when: 'I select new options'
        riskManagementRadios.checked = 'Yes'
        victimLiaisonRadios.checked = 'No'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I return to the risk management page'
        to RiskManagementPage, testData.markAndrewsBookingId

        then: 'I see the previously entered values'
        riskManagementRadios.checked == 'Yes'
        victimLiaisonRadios.checked == 'No'
    }
}
