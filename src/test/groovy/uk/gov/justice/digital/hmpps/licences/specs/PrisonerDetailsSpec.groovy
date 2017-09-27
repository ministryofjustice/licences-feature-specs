package uk.gov.justice.digital.hmpps.licences.specs

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage

@Stepwise
class PrisonerDetailsSpec extends GebReportingSpec {


    def 'Shows personal details of the prisoner'() {

        when: 'I view the personal details page'
        to PrisonerDetailsPage

        then: 'I see the expected personal details data'
        prisonerPersonalDetails.find(item).text() == value

        where:

        item                     | value
        '#prisonerName'          | 'Andrews, Mark'
        '#prisonerAliases'       | 'Marky Mark'
        '#prisonerPrisonNumber'  | 'A1235HG'
        '#prisonerDob'           | '22/10/1989'
        '#prisonerSex'           | 'Male'
        '#prisonerLocation'      | 'HB1 | L2 | Cell 3'

    }

    def 'Shows key dates'() {

        when: 'I view the personal details page'
        to PrisonerDetailsPage

        then: 'I see the key dates data'
        prisonerKeyDates.find(item).text() == value

        where:

        item                     | value
        '#sentenceExpires'       | '08/02/2018'
        '#hdcEligibility'        | 'N/A'
        '#supervisionStart'      | '09/07/2017'
        '#supervisionEnd'        | '09/07/2018'

    }

    def 'Shows the buttons to continue and to return to dashboard'() {

        when: 'I view the personal details page'
        to PrisonerDetailsPage

        then: 'I see a continue button'
        continueBtns.find('.requiredButton', 0).value() == 'Continue'

        and: 'I see a back to dashboard button'
        continueBtns.find('.requiredButton', 1).text() == 'Back to dashboard'

    }

}