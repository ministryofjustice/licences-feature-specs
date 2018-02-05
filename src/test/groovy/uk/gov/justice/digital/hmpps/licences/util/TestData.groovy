package uk.gov.justice.digital.hmpps.licences.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class TestData {

    Database licences

    TestData() {
        licences = new Database()
    }

    def deleteLicences() {
        licences.deleteAll()
    }

    def createLicence(Map data, status = '') {
        checkAndCreateLicence(JsonOutput.toJson(data), data.nomisId, status)
    }

    def createLicenceWithJson(nomisId, status = '', String data) {
        checkAndCreateLicence(data, nomisId, status)
    }

    private checkAndCreateLicence(json, id, status) {
        if (findLicenceFor(id) != null) {
            throw new Error('Licence already exists for id: ' + id)
        }

        licences.create(json, id, status)
    }

    def findLicenceFor(nomisId) {
        def licence = licences.find(nomisId)

        if (licence == null) {
            return null
        }

        def licenceText = licence.LICENCE.asciiStream.text
        def licenceJson = new JsonSlurper().parseText(licenceText)
        println licenceJson
        return licenceJson
    }

    def findLicenceStatusFor(nomisId) {
        return licences.find(nomisId).STATUS
    }
}
