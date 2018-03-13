package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ResidentDetailsModule extends Module {

    static content = {

       preferred {
            $('div.resident.preferred').collect { resident ->
                [
                        name    : resident.find(id: startsWith("preferredresidentName-")).text(),
                        age     : resident.find(id: startsWith("preferredresidentAge-")).text(),
                        relation: resident.find(id: startsWith("preferredresidentRelation-")).text()
                ]
            }
        }

        alternative {
            $('div.resident.alternative').collect { resident ->
                [
                        name    : resident.find(id: startsWith("alternativeresidentName-")).text(),
                        age     : resident.find(id: startsWith("alternativeresidentAge-")).text(),
                        relation: resident.find(id: startsWith("alternativeresidentRelation-")).text()
                ]
            }
        }
    }
}
