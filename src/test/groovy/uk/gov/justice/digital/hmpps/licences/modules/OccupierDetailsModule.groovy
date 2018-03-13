package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class OccupierDetailsModule extends Module {

    static content = {

        preferred {
            [
                    name     : $("#preferredoccupierName"),
                    age      : $("#preferredoccupierAge"),
                    relation : $("#preferredoccupierRelation"),
                    cautioned: $("#preferredcautioned")
            ]
        }

        alternative {
            [
                    name     : $("#alternativeoccupierName"),
                    age      : $("#alternativeoccupierAge"),
                    relation : $("#alternativeoccupierRelation"),
                    cautioned: $("#alternativecautioned")
            ]
        }
    }
}
