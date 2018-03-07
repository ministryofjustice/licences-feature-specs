package uk.gov.justice.digital.hmpps.licences.util

import geb.Browser
import spock.lang.Shared
import uk.gov.justice.digital.hmpps.licences.pages.AdditionalConditionsPage
import uk.gov.justice.digital.hmpps.licences.pages.ConditionsSummaryPage
import uk.gov.justice.digital.hmpps.licences.pages.CurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.LicenceDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.BassReferralPage
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressPage
import uk.gov.justice.digital.hmpps.licences.pages.EligibilityCheckPage
import uk.gov.justice.digital.hmpps.licences.pages.HdcOptOutPage
import uk.gov.justice.digital.hmpps.licences.pages.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.pages.StandardConditionsPage
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
            go '/hdc/taskList/' + nomisId
            at TaskListPage
        }
    }

    def toEligibilityCheckPageFor(nomisId) {
        Browser.drive {
            go '/hdc/eligibility/excluded/' + nomisId
            at EligibilityCheckPage
        }
    }

    def toOptOutPageFor(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/optOut/' + nomisId
            at HdcOptOutPage
        }
    }

    def toBassReferralPage(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/bassReferral/' + nomisId
            at BassReferralPage
        }
    }

    def toCurfewAddressPage(nomisId) {
        Browser.drive {
            go '/hdc/proposedAddress/curfewAddress/' + nomisId
            at ProposedAddressPage
        }
    }

    def toAddressReviewPageFor(nomisId) {
        Browser.drive {
            go '/hdc/curfew/curfewAddressReview/' + nomisId
            at ProposedAddressReviewPage
        }
    }

    def toStandardConditionsPageFor(nomisId) {
        Browser.drive {
            go '/hdc/licenceConditions/standard/' + nomisId
            at StandardConditionsPage
        }
    }

    def toAdditionalConditionsPageFor(nomisId) {
        Browser.drive {
            go '/hdc/licenceConditions/additionalConditions/' + nomisId
            at AdditionalConditionsPage
        }
    }

    def toConditionsSummaryPageFor(nomisId) {
        Browser.drive {
            go '/hdc/licenceConditions/conditionsSummary/' + nomisId
            at ConditionsSummaryPage
        }
    }

    def toRiskManagementPageFor(nomisId) {
        Browser.drive {
            go '/hdc/risk/riskManagement/' + nomisId
            at RiskManagementPage
        }
    }

    def toReportingInstructionsPageFor(nomisId) {
        Browser.drive {
            go '/hdc/reporting/reportingInstructions/' + nomisId
            at ReportingInstructionsPage
        }
    }

    def toCurfewHoursPageFor(nomisId) {
        Browser.drive {
            go '/hdc/curfew/curfewHours/' + nomisId
            at CurfewHoursPage
        }
    }

    def toLicenceDetailsPageFor(nomisId) {
        Browser.drive {
            go '/hdc/licenceDetails/' + nomisId
            at LicenceDetailsPage
        }
    }
}
