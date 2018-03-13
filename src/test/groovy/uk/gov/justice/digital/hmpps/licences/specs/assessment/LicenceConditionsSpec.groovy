package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.*
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsAdditionalPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsStandardPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class LicenceConditionsSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('processing-ro/unstarted')
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Standard conditions page shown first' () {

        given: 'At task list page'
        to TaskListPage, 'A0001XX'

        when: 'I start the additional conditions task'
        taskListAction('Additional conditions').click()

        then: 'I see the standard conditions page'
        at LicenceConditionsStandardPage
    }

    def 'Options initially unset' () {

        when: 'I view the standard conditions page'
        at LicenceConditionsStandardPage

        then:
        additionalConditionsRadios.checked == null
    }

    def 'When additional conditions NOT required, does NOT show additional conditions page' () {

        given: 'At standard page'
        at LicenceConditionsStandardPage

        when: 'I select no additional conditions'
        additionalConditionsRadios = 'No'

        and: 'I continue'
        find('#continueBtn').click()

        then: 'I see the risk management page'
        at RiskManagementPage
    }

    def 'When additional conditions required, shows additional conditions page' () {

        when: 'I view the standard conditions page'
        to LicenceConditionsStandardPage, 'A0001XX'

        and: 'I select additional conditions required'
        additionalConditionsRadios = 'Yes'

        and: 'I continue'
        find('#continueBtn').click()

        then: 'I see the additional conditions page'
        at LicenceConditionsAdditionalPage
    }

    def 'Additional conditions initially unset' () {

        when: 'At additional conditions page'
        at LicenceConditionsAdditionalPage

        then: 'Options not set'
        conditions.every { !it.value() }
    }

    def 'Select a condition reveals the input form' () {

        when: 'At additional conditions page'
        at LicenceConditionsAdditionalPage

        then: 'The input form is not shown'
        !$("#groupsOrOrganisation").isDisplayed()

        when: 'I select a condition requiring additional input'
        $("form").additionalConditions = 'NOCONTACTASSOCIATE'

        then: 'The input form is shown'
        $("#groupsOrOrganisation").isDisplayed()
    }


    def 'Modified additional conditions not saved on return to tasklist' () {

        when: 'At additional conditions page'
        at LicenceConditionsAdditionalPage

        and: 'I select some conditions'
        $("form").additionalConditions = ['NOCONTACTPRISONER', 'NOCONTACTASSOCIATE', 'NORESIDE']

        and: 'I choose return to tasklist'
        find('#backBtn').click()
        at TaskListPage

        and: 'I view the additional conditions page'
        to LicenceConditionsAdditionalPage, 'A0001XX'

        then: 'Options not set'
        conditions.every { !it.value() }
    }

    def 'Modified Additional conditions saved on save and continue' () {

        when: 'At additional conditions page'
        at LicenceConditionsAdditionalPage

        and: 'I select some conditions'
        $("form").additionalConditions = ['NOCONTACTPRISONER', 'NOCONTACTASSOCIATE']
        $("#groupsOrOrganisation") << 'sample input'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I view the additional conditions page'
        to LicenceConditionsAdditionalPage, 'A0001XX'

        then: 'I see the previously entered values'
        conditionsItem('NOCONTACTPRISONER').checked
        conditionsItem('NOCONTACTASSOCIATE').checked
        $("#groupsOrOrganisation").value() == 'sample input'
    }
}
