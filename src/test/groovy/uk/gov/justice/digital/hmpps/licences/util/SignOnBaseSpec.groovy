package uk.gov.justice.digital.hmpps.licences.util

import geb.spock.GebReportingSpec
import uk.gov.justice.digital.hmpps.iis.pages.*
import uk.gov.justice.digital.hmpps.licences.pages.IndexPage
import uk.gov.justice.digital.hmpps.licences.pages.LoggedinPage
import uk.gov.justice.digital.hmpps.licences.pages.LogoutPage
import uk.gov.justice.digital.hmpps.licences.pages.SsoLoginPage
import uk.gov.justice.digital.hmpps.licences.pages.SsoProfilePage

class SignOnBaseSpec extends GebReportingSpec{

    def signIn() {
        to IndexPage
        if (browser.isAt(SsoLoginPage)) {
            println 'Interactive sign on detected'
            signIn
        }
        at LoggedinPage
    }

    def signOut() {
        to LogoutPage
        if (browser.isAt(SsoProfilePage)) {
            println 'Interactive sign out detected'
            signOut
        }
    }
}
