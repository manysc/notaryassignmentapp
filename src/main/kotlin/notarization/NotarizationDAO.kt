package notarization

import framework.HttpResponseStatus
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.CORS
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json

class NotarizationDAO {

    var responseStatus: HttpResponseStatus = HttpResponseStatus.NOT_FOUND
    var notarizationMessage: String = ""
    var notarizationErrorMessage: String = ""
    var notarizationClientMessage: String = ""
    var notarizationClientErrorMessage: String = ""

    suspend fun addNotarization(notarization: Notarization): Boolean {
        val escrowNumber = notarization.escrowNumber
        val clientName = notarization.client.name
        val clientLastName = notarization.client.lastName
        val clientAddress = notarization.client.address
        val clientPhoneNumber = notarization.client.phoneNumber
        val clientEmail = notarization.client.email

        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        if (escrowNumber.isEmpty()) {
            notarizationErrorMessage = "Please check Notarization information."
        }

        if (clientName.isEmpty() or clientLastName.isEmpty() or clientAddress.isEmpty() or clientPhoneNumber.isEmpty()
            or clientEmail.isEmpty()
        ) {
            notarizationClientErrorMessage = "Please check Notarization Client information."
        } else {
            when (val returnCode = postNotarization(notarization)) {
                HttpResponseStatus.CREATED.status -> {
                    notarizationMessage = "Notarization $escrowNumber has been added."
                    return true
                }
                HttpResponseStatus.NOT_MODIFIED.status -> {
                    notarizationErrorMessage = "Notarization $escrowNumber already exists. Code $returnCode"
                }
                HttpResponseStatus.NOT_SUPPORTED.status -> {
                    notarizationErrorMessage =
                        "Unsupported request state when adding Notarization $escrowNumber: $returnCode"
                }
            }
        }

        return false
    }

    suspend fun removeNotarization(notarization: Notarization): Boolean {
        val escrowNumber = notarization.escrowNumber

        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        if (escrowNumber.isEmpty()) {
            notarizationErrorMessage = "Please specify a Notarization Escrow Number."
        } else {
            when (val returnCode = deleteNotarization(escrowNumber)) {
                HttpResponseStatus.OK.status -> {
                    notarizationMessage = "Notarization $escrowNumber has been deleted."
                    return true
                }
                HttpResponseStatus.NOT_FOUND.status -> {
                    notarizationErrorMessage = "Notarization $escrowNumber doesn't exist. Code $returnCode"
                }
                HttpResponseStatus.MULTIPLE_CHOICES.status -> {
                    notarizationErrorMessage =
                        "There is more than one Notarization with escrow number $escrowNumber. Code $returnCode"
                }
                HttpResponseStatus.NOT_SUPPORTED.status -> {
                    notarizationErrorMessage =
                        "Unsupported request state when deleting Notarization $escrowNumber: $returnCode"
                }
            }
        }

        return false
    }

    suspend fun searchNotarization(notarization: Notarization): Notarization? {
        val escrowNumber = notarization.escrowNumber

        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        if (escrowNumber.isEmpty()) {
            notarizationErrorMessage = "Please set Escrow Number."
        } else {
            val fetchedNotarization = fetchNotarization(escrowNumber)

            when {
                fetchedNotarization != null -> {
                    notarizationMessage = "Notarization $escrowNumber has been found."
                    return fetchedNotarization
                }
                else -> {
                    when (val responseStatus = responseStatus) {
                        HttpResponseStatus.NOT_FOUND -> {
                            notarizationErrorMessage =
                                "Notarization $escrowNumber was not found. Status $responseStatus"
                        }
                        HttpResponseStatus.INTERNAL_SERVER_ERROR -> {
                            notarizationErrorMessage =
                                "Notarization $escrowNumber has been found multiple times. Status $responseStatus"
                        }
                        HttpResponseStatus.NOT_SUPPORTED -> {
                            notarizationErrorMessage =
                                "Unable to process response status. Status $responseStatus"
                        }
                    }
                }
            }
        }

        return null
    }

    suspend fun updateNotarization(notarization: Notarization): Boolean {
        val escrowNumber = notarization.escrowNumber

        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        if (escrowNumber.isEmpty()
        ) {
            notarizationErrorMessage = "Please check Notarization information, i.e. Escrow Number."
        } else {
            when (val returnCode = putNotarization(notarization)) {
                HttpResponseStatus.OK.status -> {
                    notarizationMessage = "Notarization $escrowNumber has been updated."
                    return true
                }
                HttpResponseStatus.NOT_FOUND.status -> {
                    notarizationErrorMessage = "Notarization $escrowNumber was not found. Code $returnCode"
                }
                HttpResponseStatus.NOT_SUPPORTED.status -> {
                    notarizationErrorMessage =
                        "Unsupported request state when adding Notarization $escrowNumber: $returnCode"
                }
            }
        }

        return false
    }

    suspend fun fetchNotarizations(): List<Notarization> {
        val notarizations = window.fetch(NotarizationURIs.GET_NOTARIZATIONS.uri)
            .await()
            .json()
            .await()
            .unsafeCast<Array<Notarization>>()

        return notarizations.toList()
    }

    suspend fun fetchNotarizationsByStatus(status: NotarizationStatus): MutableList<Notarization> {
        val notarizations = window.fetch(NotarizationURIs.GET_NOTARIZATIONS_BY_STATUS.uri + status.toString())
            .await()
            .json()
            .await()
            .unsafeCast<Array<Notarization>>()

        return notarizations.toMutableList()
    }

    suspend fun fetchActiveNotarizations(): List<Notarization> {
        val notarizations = window.fetch(NotarizationURIs.GET_ACTIVE_NOTARIZATIONS.uri)
            .await()
            .json()
            .await()
            .unsafeCast<Array<Notarization>>()

        return notarizations.toList()
    }

    suspend fun fetchInactiveNotarizations(): List<Notarization> {
        val notarizations = window.fetch(NotarizationURIs.GET_INACTIVE_NOTARIZATIONS.uri)
            .await()
            .json()
            .await()
            .unsafeCast<Array<Notarization>>()

        return notarizations.toList()
    }

    private suspend fun fetchNotarization(escrowNumber: String): Notarization? {
        val response = window.fetch(NotarizationURIs.GET_NOTARIZATIONS.uri + escrowNumber).await()
        when (response.status) {
            HttpResponseStatus.OK.status -> {
                responseStatus = HttpResponseStatus.OK
                return response.json().await().unsafeCast<Notarization>()
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

    private suspend fun postNotarization(notarization: Notarization): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val notarizationBody = json(
            "escrowNumber" to notarization.escrowNumber,
            "escrowOfficerId" to notarization.escrowOfficerId,
            "category" to notarization.category.toString(),
            "powerOfAttorneySigning" to notarization.powerOfAttorneySigning,
            "fee" to notarization.fee,
            "date" to notarization.date,
            "status" to notarization.status.toString(),
            "deliveryMethod" to notarization.deliveryMethod.toString(),
            "comments" to notarization.comments,
            "client" to json(
                "name" to notarization.client.name,
                "lastName" to notarization.client.lastName,
                "address" to notarization.client.address,
                "phoneNumber" to notarization.client.phoneNumber,
                "email" to notarization.client.email
            ),
            "notaryId" to notarization.notaryId
        )

        val requestInit = RequestInit(
            method = "POST",
            headers = headers,
            body = JSON.stringify(notarizationBody),
            mode = RequestMode.CORS
        )

        return window.fetch(NotarizationURIs.GET_NOTARIZATIONS.uri, requestInit)
            .await()
            .status
    }

    private suspend fun deleteNotarization(escrowNumber: String): Short {
        val requestInit = RequestInit(method = "DELETE", mode = RequestMode.CORS)

        return window.fetch(NotarizationURIs.GET_NOTARIZATIONS.uri + escrowNumber, requestInit)
            .await()
            .status
    }

    private suspend fun putNotarization(notarization: Notarization): Short {
        val headers = Headers()
        headers.append("Content-Type", "application/json")

        val notarizationBody = json(
            "escrowNumber" to notarization.escrowNumber,
            "escrowOfficerId" to notarization.escrowOfficerId,
            "category" to notarization.category.toString(),
            "powerOfAttorneySigning" to notarization.powerOfAttorneySigning,
            "fee" to notarization.fee,
            "date" to notarization.date,
            "status" to notarization.status.toString(),
            "deliveryMethod" to notarization.deliveryMethod.toString(),
            "comments" to notarization.comments,
            "client" to json(
                "name" to notarization.client.name,
                "lastName" to notarization.client.lastName,
                "address" to notarization.client.address,
                "phoneNumber" to notarization.client.phoneNumber,
                "email" to notarization.client.email
            ),
            "notaryId" to notarization.notaryId
        )

        val requestInit = RequestInit(
            method = "PUT",
            headers = headers,
            body = JSON.stringify(notarizationBody),
            mode = RequestMode.CORS
        )

        return window.fetch(NotarizationURIs.GET_NOTARIZATIONS.uri + notarization.escrowNumber, requestInit)
            .await()
            .status
    }

}