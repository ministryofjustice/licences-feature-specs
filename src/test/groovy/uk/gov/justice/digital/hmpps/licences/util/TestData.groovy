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

    def deleteLicence(nomisId) {
        licences.delete(nomisId)
    }

    def loadLicence(filename, nomisId = 'A0001XX') {

        deleteLicence(nomisId)

        def licenceFile = TestData.class.getResource("/licences/${filename}.json")

        if(licenceFile == null){
            throw new Exception("No licence file found: '${filename}'")
        }

        def sampleText = licenceFile.text
        def sample = new JsonSlurper().parseText(sampleText)
        createLicenceWithJsonString(nomisId, sample.status, JsonOutput.toJson(sample.licence))
    }

    def createLicenceWithJsonString(nomisId, status, String json) {
        checkAndCreateLicence(nomisId, status, json)
    }

    private checkAndCreateLicence(nomisId, status, json) {
        if (findLicenceFor(nomisId) != null) {
            throw new Error('Licence already exists for nomisId: ' + nomisId)
        }

        licences.create(nomisId, status, json)
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
}
