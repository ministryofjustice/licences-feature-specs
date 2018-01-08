package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class EligibilityCheckPage extends Page {

    static at = {
        browser.currentUrl.contains('/eligibility/')
    }

    static content = {
        header { module(HeaderModule) }

        setExcluded { yesOrNo ->
            $('input', name: 'excluded').value(yesOrNo)
        }

        setUnsuitable { yesOrNo ->
            $('input', name: 'unsuitable').value(yesOrNo)
        }

        setInvestigation { yesOrNo ->
            $('input', name: 'investigation').value(yesOrNo)
        }


        isExcluded {
            $('input', name: 'excluded') == 'yes'
        }

        isUnsuitable {
            $('input', name: 'unsuitable') == 'yes'
        }

        isInvestigation {
            $('input', name: 'investigation') == 'yes'
        }
    }
}
