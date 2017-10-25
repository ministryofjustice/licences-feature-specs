package uk.gov.justice.digital.hmpps.licences.util

import groovy.sql.Sql

class Database {

    def sql
    def properties

    Database() {
        loadProperties()
        connect()
        addShutdownHook { sql.close() }
    }
    def loadProperties ( ) {
        properties = new Properties()
        new File('config.properties').withInputStream {
            properties.load(it)
        }
    }

    private Sql connect() {
        def server = System.env.TEST_DB_SERVER ?: properties.TEST_DB_SERVER
        def user = System.env.TEST_DB_USER ?: properties.TEST_DB_USER
        def password = System.env.TEST_DB_PASS ?: properties.TEST_DB_PASS

        def driver = 'net.sourceforge.jtds.jdbc.Driver'
        def url = "jdbc:jtds:sqlserver://$server/licences"

        println "Connecting to: $url"

        sql = Sql.newInstance(url, user, password, driver)
    }

    def deleteAll() {
        println 'deleteAll'
        sql.execute('DELETE FROM LICENCES')
    }

    def create(licence, nomisId, status) {
        println "create $licence : $nomisId"
        sql.execute('INSERT INTO LICENCES VALUES (?,?, ?)', [licence, nomisId, status])
    }
}
