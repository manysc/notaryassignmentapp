package escrowOfficer

import kotlinx.browser.window
import kotlinx.coroutines.await
import framework.HttpResponseStatus
import org.w3c.fetch.CORS
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json

class EscrowOfficerDAO {

    var responseStatus: HttpResponseStatus = HttpResponseStatus.NOT_FOUND

    suspend fun postEscrowOfficer(escrowOfficer: EscrowOfficer): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val escrowOfficerBody = json(
            "name" to escrowOfficer.name,
            "lastName" to escrowOfficer.lastName,
            "address" to escrowOfficer.address,
            "phoneNumber" to escrowOfficer.phoneNumber,
            "email" to escrowOfficer.email,
            "company" to escrowOfficer.company
        )

        val requestInit = RequestInit(
            method = "POST",
            headers = headers,
            body = JSON.stringify(escrowOfficerBody),
            mode = RequestMode.CORS
        )

        return window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri, requestInit)
            .await()
            .status
    }

    suspend fun fetchEscrowOfficers(): List<EscrowOfficer> {
        val escrowOfficers = window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri)
            .await()
            .json()
            .await()
            .unsafeCast<Array<EscrowOfficer>>()

        return escrowOfficers.toList()
    }

    suspend fun fetchEscrowOfficerById(id: Long): EscrowOfficer =
        window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri + id)
            .await()
            .json()
            .await()
            .unsafeCast<EscrowOfficer>()

    suspend fun fetchEscrowOfficer(name: String, lastName: String): EscrowOfficer? {
        val response = window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri + name + "/" + lastName).await()
        when (response.status) {
            HttpResponseStatus.OK.status -> {
                responseStatus = HttpResponseStatus.OK
                return response.json().await().unsafeCast<EscrowOfficer>()
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

    suspend fun deleteEscrowOfficer(name: String, lastName: String): Short {
        val requestInit = RequestInit(method = "DELETE", mode = RequestMode.CORS)

        return window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri + name + "/" + lastName, requestInit)
            .await()
            .status
    }

    suspend fun putEscrowOfficer(escrowOfficer: EscrowOfficer): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val escrowOfficerBody = json(
            "name" to escrowOfficer.name,
            "lastName" to escrowOfficer.lastName,
            "address" to escrowOfficer.address,
            "phoneNumber" to escrowOfficer.phoneNumber,
            "email" to escrowOfficer.email,
            "company" to escrowOfficer.company
        )

        val requestInit = RequestInit(
            method = "PUT",
            headers = headers,
            body = JSON.stringify(escrowOfficerBody),
            mode = RequestMode.CORS
        )

        return window.fetch(EscrowOfficerURIs.GET_ESCROW_OFFICERS.uri + escrowOfficer.id, requestInit)
            .await()
            .status
    }

}