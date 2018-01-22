package uk.gov.justice.digital.hmpps.licences.util

import groovy.sql.Sql
import spock.lang.Shared

class Database {

    @Shared
    LicencesUi licencesUi = new LicencesUi()

    def sql

    Database() {
        connect()
        addShutdownHook { sql.close() }
    }

    private Sql connect() {
        def server = licencesUi.dbServer
        def user = licencesUi.dbUser
        def password = licencesUi.dbPassword
        def database = licencesUi.dbDatabase

        def driver = 'net.sourceforge.jtds.jdbc.Driver'
        def url = "jdbc:jtds:sqlserver://$server/$database"

        println "Connecting to: $url"

        sql = Sql.newInstance(url, user, password, driver)
    }

    def deleteAll() {
        println 'deleteAll'
        //sql.execute("DELETE FROM LICENCES WHERE NOMIS_ID like '%XX'")
        sql.execute("DELETE FROM LICENCES")
    }

    def create(licence, nomisId, status) {
        if (!nomisId.endsWith('XX')) {
            println 'Nomis ID must end with XX for stage tests'
            return
        }
        println "create $licence : $nomisId"
        sql.execute('INSERT INTO LICENCES VALUES (?,?,?)', [licence, nomisId, status])
    }

    def find(nomisId) {
        return sql.firstRow("SELECT * FROM LICENCES WHERE NOMIS_ID like (?)", nomisId)
    }
}
