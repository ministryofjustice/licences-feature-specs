package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class IndexPage extends Page{

    static url = '/'

    static content = {
        header { module(HeaderModule) }
    }

}
