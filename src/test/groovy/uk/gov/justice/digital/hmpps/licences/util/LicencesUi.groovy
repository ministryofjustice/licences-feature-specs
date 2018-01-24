package uk.gov.justice.digital.hmpps.licences.util

class LicencesUi {

    def properties

    def dbServer
    def dbUser
    def dbPassword
    def dbDatabase

    LicencesUi() {
        loadProperties()

        dbServer = System.env.TEST_DB_SERVER ?: properties.TEST_DB_SERVER
        dbUser = System.env.TEST_DB_USER ?: properties.TEST_DB_USER
        dbPassword = System.env.TEST_DB_PASS ?: properties.TEST_DB_PASS
        dbDatabase = System.env.TEST_DB ?: properties.TEST_DB
    }

    def loadProperties() {
        properties = new Properties()
        new File('config.properties').withInputStream {
            properties.load(it)
        }
    }
}
