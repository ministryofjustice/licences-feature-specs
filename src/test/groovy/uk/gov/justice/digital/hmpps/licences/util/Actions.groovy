package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressBassReferralPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressCurfewAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage


class Actions {

    @Shared
    LicencesUi licencesUi = new LicencesUi()

    def users = [
            'CA'  : 'CA_USER_TEST',
            'RO'  : 'RO_USER_TEST',
            'DM'  : 'DM_USER_TEST',
            'NONE': 'NONE'
    ]

    def logIn(role = 'CA') {
        Browser.drive {
            to SigninPage
            def userName = users[role]
            println "Logging in as ${userName}"
            signInAs(userName)
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
        Browser.drive {
            go '/hdc/eligibility/' + nomisId
            at EligibilityCheckPage
        }
    }

    def toOptOutPageFor(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/optOut/' + nomisId
            at ProposedAddressOptOutPage
        }
    }

    def toBassReferralPage(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/bassReferral/' + nomisId
            at ProposedAddressBassReferralPage
        }
    }

    def toCurfewAddressPage(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/curfewAddress/' + nomisId
            at ProposedAddressCurfewAddressPage
        }
    }
}
