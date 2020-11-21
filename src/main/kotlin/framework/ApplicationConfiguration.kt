package framework

import kotlinx.browser.document
import org.w3c.dom.get

class ApplicationConfiguration {

    companion object {
        private const val DEFAULT_FREQUENCY_MILLIS: Long = 10000

        fun getServiceIP(): String? {
            return document.getElementById("serviceIP")?.attributes?.get("value")?.nodeValue
        }

        fun getServicePort(): String? {
            return document.getElementById("servicePort")?.attributes?.get("value")?.nodeValue
        }

        // Frequency in seconds
        fun getAutoRefreshResourcesFrequency(): Long {
            val frequency = document.getElementById("autoRefreshResourcesFrequency")?.attributes?.get("value")?.nodeValue?.toInt()
            if (frequency != null) {
                return (frequency * 1000).toLong()
            }

            return DEFAULT_FREQUENCY_MILLIS
        }
    }
}