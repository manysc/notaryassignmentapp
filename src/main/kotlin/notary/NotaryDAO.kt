package notary

import framework.HttpResponseStatus
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.CORS
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json

class NotaryDAO {

    var responseStatus: HttpResponseStatus = HttpResponseStatus.NOT_FOUND

    suspend fun fetchNotaries(): List<Notary> {
        val notaries = window.fetch(NotaryURIs.GET_NOTARIES.uri)
            .await()
            .json()
            .await()
            .unsafeCast<Array<Notary>>()

        return notaries.toList()
    }

    suspend fun fetchNotary(name: String, lastName: String): Notary? {
        val response = window.fetch(NotaryURIs.GET_NOTARIES.uri + name + "/" + lastName).await()
        when (response.status) {
            HttpResponseStatus.OK.status -> {
                responseStatus = HttpResponseStatus.OK
                return response.json().await().unsafeCast<Notary>()
            }
            HttpResponseStatus.NOT_FOUND.status -> {
                responseStatus = HttpResponseStatus.NOT_FOUND
            }
            HttpResponseStatus.INTERNAL_SERVER_ERROR.status -> {
                responseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR
            }
            else -> {
                responseStatus = HttpResponseStatus.NOT_SUPPORTED
            }
        }

        return null
    }

    suspend fun postNotary(notary: Notary): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val notaryBody = json(
            "name" to notary.name,
            "lastName" to notary.lastName,
            "address" to notary.address,
            "phoneNumber" to notary.phoneNumber,
            "email" to notary.email,
            "company" to notary.company,
            "certification" to json(
                "realStateAgent" to notary.certification.realStateAgent,
                "signingAgent" to notary.certification.signingAgent,
                "attorney" to notary.certification.attorney,
                "loanOfficer" to notary.certification.loanOfficer
            ),
            "licenseExpirationDate" to notary.licenseExpirationDate,
            "errorOmissionInsuranceAmount" to notary.errorOmissionInsuranceAmount,
            "experienceYears" to notary.experienceYears,
            "score" to notary.score
        )

        val requestInit = RequestInit(
            method = "POST",
            headers = headers,
            body = JSON.stringify(notaryBody),
            mode = RequestMode.CORS
        )

        return window.fetch(NotaryURIs.GET_NOTARIES.uri, requestInit)
            .await()
            .status
    }

    suspend fun deleteNotary(name: String, lastName: String): Short {
        val requestInit = RequestInit(method = "DELETE", mode = RequestMode.CORS)

        return window.fetch(NotaryURIs.GET_NOTARIES.uri + name + "/" + lastName, requestInit)
            .await()
            .status
    }

    suspend fun putNotary(notary: Notary): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val notaryBody = json(
            "name" to notary.name,
            "lastName" to notary.lastName,
            "address" to notary.address,
            "phoneNumber" to notary.phoneNumber,
            "email" to notary.email,
            "company" to notary.company,
            "certification" to json(
                "realStateAgent" to notary.certification.realStateAgent,
                "signingAgent" to notary.certification.signingAgent,
                "attorney" to notary.certification.attorney,
                "loanOfficer" to notary.certification.loanOfficer
            ),
            "licenseExpirationDate" to notary.licenseExpirationDate,
            "errorOmissionInsuranceAmount" to notary.errorOmissionInsuranceAmount,
            "experienceYears" to notary.experienceYears,
            "score" to notary.score
        )

        val requestInit = RequestInit(
            method = "PUT",
            headers = headers,
            body = JSON.stringify(notaryBody),
            mode = RequestMode.CORS
        )

        return window.fetch(NotaryURIs.GET_NOTARIES.uri + notary.id, requestInit)
            .await()
            .status
    }
}