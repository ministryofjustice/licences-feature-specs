package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class OccupierDetailsModule extends Module {

    static content = {

        preferred {
            occupier('preferred')
        }

        alternative {
            occupier('alternative')
        }

        occupier { type ->
            [
                    name     : $("#${type}-occupierName"),
                    age      : $("#${type}-occupierAge"),
                    relation : $("#${type}-occupierRelation"),
                    cautioned: $("#${type}-cautioned")
            ]
        }


    }
}
