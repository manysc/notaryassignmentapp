package framework

import kotlinx.browser.document
import org.w3c.dom.get

class ApplicationConfiguration {

    companion object {
        fun getServiceIP(): String? {
            return document.getElementById("serviceIP")?.attributes?.get("value")?.nodeValue
        }

        fun getServicePort(): String? {
            return document.getElementById("servicePort")?.attributes?.get("value")?.nodeValue
        }
    }
}