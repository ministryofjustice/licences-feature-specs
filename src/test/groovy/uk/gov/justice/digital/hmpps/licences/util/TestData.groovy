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

    def createLicence(Map data, status) {
        licences.create(JsonOutput.toJson(data), data.nomisId, status)
    }

    def findLicenceFor(nomisId) {
        def licence = licences.find(nomisId).LICENCE.asciiStream.text
        def licenceJson =  new JsonSlurper().parseText(licence)
        println licenceJson
        return licenceJson
    }

    def findLicenceStatusFor(nomisId) {
        return licences.find(nomisId).STATUS
    }
}
