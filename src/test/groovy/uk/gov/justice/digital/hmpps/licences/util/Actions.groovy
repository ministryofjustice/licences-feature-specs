package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.DischargeAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.TasklistPage


class Actions {

    def logIn() {
        Browser.drive {
            to SigninPage
            signIn
            at TasklistPage
        }
    }

    def logOut() {
        Browser.drive {
            go '/logout'
        }
    }

    def toDetailsPageFor(nomisId) {
        Browser.drive {
            to TasklistPage
            viewDetailsFor(nomisId)
            at PrisonerDetailsPage
        }
    }

    def toDischargeAddressPageFor(nomisId) {
        toDetailsPageFor(nomisId)
        Browser.drive {
            at PrisonerDetailsPage
            footerButtons.clickContinue
            at DischargeAddressPage
        }
    }

    def toAdditionalConditionsPageFor(nomisId) {
        toDischargeAddressPageFor(nomisId)
        Browser.drive {
            at DischargeAddressPage
            footerButtons.clickContinue
            at AdditionalConditionsPage
        }
    }

    def toReportingInstructionsPageFor(nomisId) {
        toAdditionalConditionsPageFor(nomisId)
        Browser.drive {
            at AdditionalConditionsPage
            footerButtons.clickContinue
            at ReportingInstructionsPage
        }
    }
}
