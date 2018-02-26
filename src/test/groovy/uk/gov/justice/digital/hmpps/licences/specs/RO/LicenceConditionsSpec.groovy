package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.LicenceDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class LicenceConditionsSpec extends GebReportingSpec {

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

    def 'Standard conditions page shown first' () {

        given: 'At task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the additional conditions task'
        taskListAction('Additional conditions').click()

        then: 'I see the standard conditions page'
        at StandardConditionsPage
    }

    def 'Options initially unset' () {

        when: 'I view the standard conditions page'
        at StandardConditionsPage

        then:
        additionalConditionsRadios.checked == null
    }

    def 'When additional conditions NOT required, does NOT show additional conditions page' () {

        given: 'At standard page'
        at StandardConditionsPage

        when: 'I select no additional conditions'
        additionalConditionsRadios = 'No'

        and: 'I continue'
        find('#continueBtn').click()

        then: 'I see the risk management page'
        at RiskManagementPage
    }

    def 'When additional conditions required, shows additional conditions page' () {

        when: 'I view the standard conditions page'
        actions.toStandardConditionsPageFor('A0001XX')
        at StandardConditionsPage

        and: 'I select additional conditions required'
        additionalConditionsRadios = 'Yes'

        and: 'I continue'
        find('#continueBtn').click()

        then: 'I see the additional conditions page'
        at AdditionalConditionsPage
    }

    def 'Additional conditions initially unset' () {

        when: 'At additional conditions page'
        at AdditionalConditionsPage

        then: 'Options not set'
        conditions.every { !it.value() }
    }

    def 'Select a condition reveals the input form' () {

        when: 'At additional conditions page'
        at AdditionalConditionsPage

        then: 'The input form is not shown'
        !$("#groupsOrOrganisation").isDisplayed()

        when: 'I select a condition requiring additional input'
        $("form").additionalConditions = 'NOCONTACTASSOCIATE'

        then: 'The input form is shown'
        $("#groupsOrOrganisation").isDisplayed()
    }


    def 'Modified additional conditions not saved on return to tasklist' () {

        when: 'At additional conditions page'
        at AdditionalConditionsPage

        and: 'I select some conditions'
        $("form").additionalConditions = ['NOCONTACTPRISONER', 'NOCONTACTASSOCIATE', 'NORESIDE']

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the additional conditions page'
        actions.toAdditionalConditionsPageFor('A0001XX')
        at AdditionalConditionsPage

        then: 'Options not set'
        conditions.every { !it.value() }
    }

    def 'Modified Additional conditions saved on save and continue' () {

        when: 'At additional conditions page'
        at AdditionalConditionsPage

        and: 'I select some conditions'
        $("form").additionalConditions = ['NOCONTACTPRISONER', 'NOCONTACTASSOCIATE']
        $("#groupsOrOrganisation") << 'sample input'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I view the additional conditions page'
        actions.toAdditionalConditionsPageFor('A0001XX')
        at AdditionalConditionsPage

        then: 'I see the previously entered values'
        conditionsItem('NOCONTACTPRISONER').checked
        conditionsItem('NOCONTACTASSOCIATE').checked
        $("#groupsOrOrganisation").value() == 'sample input'
    }

    def 'Saved values shown on the review screen' () {

        when: 'I have already entered some conditions'
        at AdditionalConditionsPage

        and: 'I save and continue'
        find('#continueBtn').click()

        then: 'I see the licence details page'
        at LicenceDetailsPage

        and: 'I see the previously selected values'
        additionalConditions.size() == 2
        $('div.additionalContent').find(text: contains('sample input')).size() == 1
    }

    def 'Add another condition button returns to additional conditions page' () {

        given: 'On licence details page'
        at LicenceDetailsPage

        when: 'I choose to add another condition'
        find('#addAnother').click()

        then: 'I see the additional conditions page'
        at AdditionalConditionsPage
    }
}
