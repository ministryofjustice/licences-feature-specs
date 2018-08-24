package uk.gov.justice.digital.hmpps.licences.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class TestData {

    LicencesApi licences

    TestData() {
        licences = new LicencesApi()
    }

    def deleteLicences() {
        licences.deleteAll()
    }

    def loadLicence(filename, bookingId = '1200635') {
        deleteLicences()
        addLicence(filename, bookingId)
    }

    def addLicence(filename, bookingId = '1200635') {

        def licenceFile = TestData.class.getResource("/licences/${filename}.json")

        if(licenceFile == null){
            throw new Exception("No licence file found: '${filename}'")
        }

        def sampleText = licenceFile.text
        def sample = new JsonSlurper().parseText(sampleText)

        licences.create(bookingId, sample)
    }
}
