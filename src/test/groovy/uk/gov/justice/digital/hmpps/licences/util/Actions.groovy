package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import uk.gov.justice.digital.hmpps.licences.pages.PrisonerDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage


class Actions {

    def logIn(user = 'CA_USER') {
        Browser.drive {
            to SigninPage
            signInAs(user)
            at CaselistPage
        }
    }

    def logOut() {
        Browser.drive {
            go '/logout'
        }
    }

    def toDetailsPageFor(nomisId) {
        Browser.drive {
            via CaselistPage
            viewDetailsFor(nomisId)
            at PrisonerDetailsPage
        }
    }
}
