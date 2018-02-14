package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
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

        when: 'At standard page'
        at StandardConditionsPage

        then:
        additionalConditionsRadios.checked == null
    }

    @PendingFeature
    def 'Modified options not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified options saved on save and continue' () {

    }

    @PendingFeature
    def 'When additional conditions NOT required, does NOT show additional conditions page' () {

    }

    @PendingFeature
    def 'When additional conditions required, shows additional conditions page' () {

    }

    @PendingFeature
    def 'Additional conditions initially unset' () {

    }

    @PendingFeature
    def 'Select a condition reveals the input form' () {

    }

    @PendingFeature
    def 'Modified Additional conditions not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified Additional conditions saved on save and continue' () {

    }

    @PendingFeature
    def 'A more detailed check of multiple inputs being correctly shown in the right form' () {

    }

    @PendingFeature
    def 'Saved values shown on the review screen' () { // or maybe a separate spec for the review

    }
}
