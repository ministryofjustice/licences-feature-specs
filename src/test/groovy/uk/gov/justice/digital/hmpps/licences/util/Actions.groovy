package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.TaskListPage
import uk.gov.justice.digital.hmpps.licences.pages.SigninPage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage


class Actions {

    @Shared
    LicencesUi licencesUi = new LicencesUi()

    def users = [
            'mock' :[
                    'CA': 'CA_USER',
                    'RO': 'RO_USER',
                    'DM': 'DM_USER'
            ],
            'stage':[
                    'CA': 'CA_USER_TEST',
                    'RO': 'RO_USER_TEST',
                    'DM': 'DM_USER_TEST'
            ]
    ]

    def logIn(role = 'CA') {
        Browser.drive {
            to SigninPage
            def userName = users[licencesUi.testEnv][role]
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
        // toTaskListPageFor(nomisId)
        Browser.drive {
            // at TaskListPage
            // click button
            go '/hdc/eligibility/A1235HG'
            at EligibilityCheckPage
        }
    }
}
