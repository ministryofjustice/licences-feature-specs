package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class ResidentDetailsModule extends Module {

    static content = {

        preferred {
            resident('preferred')
        }

        alternative {
            resident('alternative')
        }

        resident { type ->
            $("div.resident.${type}").collect { resident ->
                [
                        name    : resident.find(id: startsWith("${type}-residentName-")).text(),
                        age     : resident.find(id: startsWith("${type}-residentAge-")).text(),
                        relation: resident.find(id: startsWith("${type}-residentRelation-")).text()
                ]
            }
        }
    }
}
