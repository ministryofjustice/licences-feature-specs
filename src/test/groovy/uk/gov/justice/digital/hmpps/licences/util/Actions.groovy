package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
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

    def toTaskListPageFor(nomisId) {
        Browser.drive {
            via CaselistPage
            viewTaskListFor(nomisId)
            at TaskListPage
        }
    }

    def toEligibilityCheckPageFor(nomisId) {
        // toTaskListPageFor(nomisId)
        Browser.drive {
            // at TaskListPage
            // click button
            go '/hdc/eligibility/A1235HG'
            at EligibilityCheckPage
        }
    }
}
