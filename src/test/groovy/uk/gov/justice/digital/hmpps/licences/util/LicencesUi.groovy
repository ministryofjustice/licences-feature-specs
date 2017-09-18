package uk.gov.justice.digital.hmpps.licences.util

class LicencesUi {
    String indexUri

    LicencesUi() {
        indexUri = (System.getenv('LICENCES_URI') ?: 'http://localhost:3000')
    }

}
