package uk.gov.justice.digital.hmpps.licences.util

import groovy.json.JsonOutput

class TestData {

    Database licences

    TestData(){
        licences = new Database()
    }

    def deleteLicences() {
        licences.deleteAll()
    }

    def createLicence(Map<String, String> data, status) {
        licences.create(JsonOutput.toJson(data), data.nomisId, status)
    }
}
