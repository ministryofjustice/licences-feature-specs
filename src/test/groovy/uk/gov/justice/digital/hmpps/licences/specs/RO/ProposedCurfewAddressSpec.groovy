package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CurfewAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ProposedCurfewAddressSpec extends GebReportingSpec {

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

    def 'Shows address details' () {

        given: 'A licence record with a proposed curfew address'
        testData.createLicenceWithJson('A0001XX', '{"proposedAddress":{"optOut":{"decision":"No"},"bassReferral":{"decision":"No"},"curfewAddress":{"addressLine1":"street","addressLine2":"","addressTown":"town","postCode":"PC11PC","telephone":"4444444","electricity":"Yes","occupier":{"name":"","age":"","relationship":""},"residents":[{"name":"","age":"","relation":""},{"name":"","age":"","relation":""},{"name":"","age":"","relation":""}]}}}')

        and: 'At task list page'
        actions.toTaskListPageFor('A0001XX')
        at TaskListPage

        when: 'I start the address review task'
        taskListAction('Proposed curfew address').click()

        then: 'I see the address details page'
        at CurfewAddressReviewPage

        and: 'I see the address details'
        street.text() == 'street'
        town.text() == 'town'
        postCode.text() == 'PC11PC'

        // todo these all need formatting/capitalising
        // todo check other values
    }

    def 'Confirmation options initially unselected' () {

        when: 'At address review page'
        at CurfewAddressReviewPage

        then:
        landlordConsentRadios.checked == null
        manageSafelyRadios.checked == null
    }

    @PendingFeature
    def 'Further questions not shown when landlord consent is no' () {

    }

    @PendingFeature
    def 'Further questions shown when landlord consent is yes' () {

    }

    @PendingFeature
    def 'Further details not shown when managed safely is yes' () {

    }

    @PendingFeature
    def 'Further details shown when landlord consent is no' () {

    }

    @PendingFeature
    def 'Modified choices are not saved on return to tasklist' () {

    }

    @PendingFeature
    def 'Modified choices are saved after save and continue' () {

    }
}
