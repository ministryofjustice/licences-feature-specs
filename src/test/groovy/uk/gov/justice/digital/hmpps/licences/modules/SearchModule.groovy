package uk.gov.justice.digital.hmpps.licences.modules

import geb.Module


class SearchModule extends Module {

    static content = {

        input { $('#searchTerm') }

        execute { $('#searchButton') }
    }
}
