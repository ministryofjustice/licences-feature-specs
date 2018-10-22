package uk.gov.justice.digital.hmpps.licences.specs.finalchecks

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityExclusionPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.BassOfferPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksConfiscationOrderPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksOnRemandPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class BassOfferSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        actions.logIn('CA')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'BASS offer starts with nothing selected'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('finalchecks/bassOffer-unstarted')

        when: 'I view the BASS offer page'
        to BassOfferPage, testData.markAndrewsBookingId

        then: 'No radio option is selected'
        bassAcceptedRadios.checked == null

        and: ' The address form is not shown'
        !bassAddressForm.isDisplayed()
    }

    def 'Address form shown when option is yes'(){

        when: 'I view the bass offers page'
        at BassOfferPage

        and: 'I select yes for offer outcome'
        bassAcceptedRadios.checked = 'Yes'

        then: 'I see the BASS address form'
        bassAddressForm.isDisplayed()
    }

    def 'Shows previously saved values'() {

        given: 'BASS offer already done'
        testData.loadLicence('finalchecks/bassOffer-complete')

        when: 'I view the serious offence page'
        to BassOfferPage, testData.markAndrewsBookingId

        then: 'I see the previous values'
        bassAcceptedRadios.checked == 'Yes'

        bass.proposed.town == 'BASS Town'
        bass.proposed.county == 'BASS County'

        bass.offer.area == 'Area'
        bass.offer.street == 'Street'
        bass.offer.town == 'Town'
        bass.offer.postCode == 'AB11AB'
        bass.offer.telephone == '111'
    }

}
