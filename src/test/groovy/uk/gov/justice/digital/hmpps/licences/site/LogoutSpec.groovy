package uk.gov.justice.digital.hmpps.licences.site

import uk.gov.justice.digital.hmpps.licences.pages.IndexPage
import uk.gov.justice.digital.hmpps.licences.pages.LogoutPage
import uk.gov.justice.digital.hmpps.licences.util.SignOnBaseSpec

class LogoutSpec extends SignOnBaseSpec {

    def setupSpec() {
        signOut()
    }

    def 'Logout link shown when logged in'() {

        given:
        loggedIn()

        when: 'I open the index page'
        to IndexPage

        then: 'I see the logout link'
        header.logoutLink.isDisplayed()
    }

    def 'logout redirects to SSO profile'(){

        given:
        loggedIn()

        when: 'I log out'
        via LogoutPage

        then: 'I see the SSO profile page'
        browser.currentUrl.contains('/profile')
    }

    def loggedIn() {
        signIn()
    }
}
