package uk.gov.justice.digital.hmpps.licences.util

class LicencesUi {

    def properties

    def dbServer
    def dbUser
    def dbPassword
    def dbDatabase

    LicencesUi() {
        properties = new Properties()
        loadProperties()

        dbServer = System.env.TEST_DB_SERVER ?: properties.TEST_DB_SERVER
        dbUser = System.env.TEST_DB_USER ?: properties.TEST_DB_USER
        dbPassword = System.env.TEST_DB_PASS ?: properties.TEST_DB_PASS
        dbDatabase = System.env.TEST_DB ?: properties.TEST_DB
    }

    def loadProperties() {
        def fileName = 'config.properties'

        File configFile = new File(fileName)

        if (configFile.isFile()) {
            configFile.withInputStream {
                properties.load(it)
            }
        } else {
            println "No config file '$fileName'"
        }
    }
}
