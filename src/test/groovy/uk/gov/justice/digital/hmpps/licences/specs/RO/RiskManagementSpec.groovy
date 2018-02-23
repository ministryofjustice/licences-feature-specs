package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
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

    @PendingFeature
    def 'Risk management details shown when YES' () {

    }

    @PendingFeature
    def 'Awaiting information details shown when YES' () {

    }
    @PendingFeature
    def 'Victim liaison details shown when YES' () {

    }

    @PendingFeature
    def 'Modified options not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified options saved on save and continue' () {

    }
}
