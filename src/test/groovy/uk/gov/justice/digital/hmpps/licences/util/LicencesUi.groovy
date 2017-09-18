package uk.gov.justice.digital.hmpps.licences.util

class LicencesUi {
    String indexUri
    String user
    String password
    String signonUri
    String profileUri

    int pageSize = 5

    LicencesUi() {
        indexUri = (System.getenv('LICENCES_URI') ?: 'http://localhost:3000')
        user = (System.getenv('LICENCES_USER') ?: 'user')
        password = (System.getenv('LICENCES_PASSWORD') ?: 'password')
        signonUri = (System.getenv('SIGNON_URI') ?: 'http://localhost:3001/oauth/authorize')
        profileUri = (System.getenv('PROFILE_URI') ?: 'http://localhost:3001/profile')
    }

}
