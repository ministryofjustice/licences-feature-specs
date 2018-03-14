package uk.gov.justice.digital.hmpps.licences.specs.finalchecks

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.pages.SendPage
import uk.gov.justice.digital.hmpps.licences.pages.SentPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.eligibility.EligibilityExclusionPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksOnRemandPage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksPostponePage
import uk.gov.justice.digital.hmpps.licences.pages.finalchecks.FinalChecksSeriousOffencePage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewReportingPage
import uk.gov.justice.digital.hmpps.licences.pages.review.ReviewRiskPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class FinalChecksSpec extends GebReportingSpec {

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

    def 'Serious offence starts with nothing selected'() {

        given: 'A licence ready for final checks'
        testData.loadLicence('finalchecks/unstarted')

        when: 'I view the serious offence page'
        to FinalChecksSeriousOffencePage, 'A0001XX'

        then: 'Neither radio option is selected'
        seriousOffenceRadios.checked == null
    }

    def 'Shows previously saved values'() {

        given: 'Serious offence already done'
        testData.loadLicence('finalchecks/serious-offence')

        when: 'I view the serious offence page'
        to FinalChecksSeriousOffencePage, 'A0001XX'

        then: 'I see the previous values'
        seriousOffenceRadios.checked == 'Yes'
    }

    def 'On remand shown next' () {

        given: 'Serious offence already done'
        testData.loadLicence('finalchecks/serious-offence')

        when: 'I view the serious offence page'
        to FinalChecksSeriousOffencePage, 'A0001XX'

        and: 'I continue'
        find('#continueBtn').click()

        then: 'I see the on remand page'
        at FinalChecksOnRemandPage
    }

    def 'Saved answers shown on tasklist' () {

        given: 'Viewing on remand page'
        at FinalChecksOnRemandPage

        when: 'I choose a value and continue'
        onRemandRadios.checked = 'No'
        find('#continueBtn').click()

        then: 'I see the task list'
        at TaskListPage

        and: 'I see the summary test for the saved values'
        seriousOffenceAnswer.text() == 'The prisoner is under investigation or been charged for a serious offence in custody'
        onRemandAnswer.text() == 'The prisoner is not on remand'
    }

    def 'Tasklist shows answers with alert styling when answers are Yes' () {

        given: 'Serious Offence and On Remand'
        testData.loadLicence('finalchecks/serious-offence-on-remand')

        when: 'I view the task list'
        to TaskListPage, 'A0001XX'

        then: 'I see the the final check status summary'
        seriousOffenceAnswer.text() == 'The prisoner is under investigation or been charged for a serious offence in custody'
        onRemandAnswer.text() == 'The prisoner is on remand'

        and: 'The summary has the alert styling on the word "is"'
        seriousOffenceAnswer.find('.alert').text() == 'is'
        onRemandAnswer.find('.alert').text() == 'is'
    }

}
