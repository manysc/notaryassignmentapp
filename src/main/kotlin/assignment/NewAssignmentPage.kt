package assignment

import escrowOfficer.EscrowOfficer
import escrowOfficer.EscrowOfficerDAO
import framework.HttpResponseStatus
import framework.workerFormButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.LinearDimension
import kotlinx.css.px
import notarization.*
import notary.Notary
import notary.NotaryDAO
import react.*
import kotlin.js.Date

external interface NewAssignmentPageState : RState {
    var notarizationMessage: String
    var notarizationErrorMessage: String
    var notarizationClientMessage: String
    var notarizationClientErrorMessage: String

    var notarization: Notarization
    var notarizationDAO: NotarizationDAO

    var escrowOfficerDAO: EscrowOfficerDAO
    var escrowOfficers: List<EscrowOfficer>

    var notaryDAO: NotaryDAO
    var notaries: List<Notary>

    var mainScope: CoroutineScope
}

class NewAssignmentPage : RComponent<RProps, NewAssignmentPageState>() {

    override fun NewAssignmentPageState.init() {
        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        notarization = Notarization(
            0, "", 0, NotarizationCategory.NOT_SET, false, 0F, Date(),
            NotarizationStatus.NOT_SET, NotarizationDeliveryMethod.NOT_SET, "",
            NotarizationClient("", "", "", "", ""), 0
        )
        notarizationDAO = NotarizationDAO()

        escrowOfficerDAO = EscrowOfficerDAO()
        escrowOfficers = listOf()

        notaryDAO = NotaryDAO()
        notaries = listOf()

        mainScope = MainScope()

        mainScope.launch {

            val fetchedEscrowOfficers = escrowOfficerDAO.fetchEscrowOfficers()
            val fetchedNotaries = notaryDAO.fetchNotaries()

            setState {
                escrowOfficers = fetchedEscrowOfficers
                notaries = fetchedNotaries
            }
        }
    }

    override fun RBuilder.render() {
        // Notarization
        notarizationForm {
            pageMessage = state.notarizationMessage
            pageErrorMessage = state.notarizationErrorMessage
            formClass = "formLeft"
            notarization = state.notarization
            escrowOfficers = state.escrowOfficers

            onChangedEscrowNumber = { escrowNumber ->
                setState {
                    notarization.escrowNumber = escrowNumber
                }
            }

            onChangedEscrowOfficer = { selectedEscrowOfficer ->
                // Get Escrow Officer from Full Name and Phone Number.
                for (escrowOfficer in escrowOfficers) {
                    val escrowOfficerFullName =
                        "${escrowOfficer.name} ${escrowOfficer.lastName} ${escrowOfficer.phoneNumber}"
                    if (escrowOfficerFullName == selectedEscrowOfficer) {
                        setState {
                            notarization.escrowOfficerId = escrowOfficer.id
                        }
                        break
                    }
                }
            }

            onChangedCategory = { category ->
                setState {
                    notarization.category = category
                }
            }

            onChangedPowerOfAttorneySigning = {
                setState {
                    notarization.powerOfAttorneySigning = !state.notarization.powerOfAttorneySigning
                }
            }

            onChangedFee = { fee ->
                setState {
                    notarization.fee = fee
                }
            }

            onChangedDate = { date ->
                setState {
                    notarization.date = date
                }
            }

            onChangedStatus = { status ->
                setState {
                    notarization.status = status
                }
            }

            onChangedDeliveryMethod = { deliveryMethod ->
                setState {
                    notarization.deliveryMethod = deliveryMethod
                }
            }

            onChangedComments = { comments ->
                setState {
                    notarization.comments = comments
                }
            }
        }

        // Notarization Client
        notarizationClientForm {
            pageMessage = state.notarizationClientMessage
            pageErrorMessage = state.notarizationClientErrorMessage
            formClass = "formCenter"
            notarization = state.notarization
            notaries = state.notaries

            onChangedClientName = { name ->
                setState {
                    notarization.client.name = name
                }
            }

            onChangedClientLastName = { lastName ->
                setState {
                    notarization.client.lastName = lastName
                }
            }

            onChangedClientAddress = { address ->
                setState {
                    notarization.client.address = address
                }
            }

            onChangedClientPhoneNumber = { phoneNumber ->
                setState {
                    notarization.client.phoneNumber = phoneNumber
                }
            }

            onChangedClientEmail = { email ->
                setState {
                    notarization.client.email = email
                }
            }

            onChangedNotary = { selectedNotary ->
                if (selectedNotary.isNotEmpty()) {
                    // Get Notary from Full Name and Phone Number.
                    for (notary in notaries) {
                        val notaryFullName = "${notary.name} ${notary.lastName} ${notary.phoneNumber}"
                        if (notaryFullName == selectedNotary) {
                            setState {
                                notarization.notaryId = notary.id
                            }
                            break
                        }
                    }
                } else {
                    setState {
                        notarization.notaryId = null
                    }
                }
            }
        }

        // Notarization Form Buttons
        workerFormButtons {
            marginTop = 130.px

            showAddButton = true
            showRemoveButton = true
            showSearchButton = true
            showUpdateButton = true
            showClearButton = true

            onAddClicked = {
                addNotarization(state.notarization)
            }

            onRemoveClicked = {
                removeNotarization(state.notarization)
            }

            onSearchClicked = {
                searchNotarization(state.notarization)
            }

            onUpdateClicked = {
                updateNotarization(state.notarization)
            }

            onClearClicked = {
                clearNotarizationForm()
            }
        }
    }

    private fun addNotarization(notarization: Notarization) {
        setState {
            notarizationMessage = ""
            notarizationErrorMessage = ""
            notarizationClientMessage = ""
            notarizationClientErrorMessage = ""
        }

        state.mainScope.launch {
            val addedNotarization = state.notarizationDAO.addNotarization(notarization)

            setState {
                if (addedNotarization) {
                    notarizationMessage = state.notarizationDAO.notarizationMessage
                    notarizationClientMessage = state.notarizationDAO.notarizationClientMessage
                } else {
                    notarizationErrorMessage = state.notarizationDAO.notarizationErrorMessage
                    notarizationClientErrorMessage = state.notarizationDAO.notarizationClientErrorMessage
                }
            }
        }
    }

    private fun removeNotarization(notarization: Notarization) {
        setState {
            notarizationMessage = ""
            notarizationErrorMessage = ""
            notarizationClientMessage = ""
            notarizationClientErrorMessage = ""
        }

        state.mainScope.launch {
            val removedNotarization = state.notarizationDAO.removeNotarization(notarization)

            if (removedNotarization) {
                setState {
                    notarizationMessage = state.notarizationDAO.notarizationMessage
                    notarizationClientMessage = state.notarizationDAO.notarizationClientMessage
                }
            } else {
                setState {
                    notarizationErrorMessage = state.notarizationDAO.notarizationErrorMessage
                    notarizationClientErrorMessage = state.notarizationDAO.notarizationClientErrorMessage
                }
            }
        }
    }

    private fun searchNotarization(notarization: Notarization) {
        setState {
            notarizationMessage = ""
            notarizationErrorMessage = ""
            notarizationClientMessage = ""
            notarizationClientErrorMessage = ""
        }

        state.mainScope.launch {
            val fetchedNotarization = state.notarizationDAO.searchNotarization(notarization)

            when {
                fetchedNotarization != null -> {
                    setState {
                        notarizationMessage = state.notarizationDAO.notarizationMessage
                        notarizationClientMessage = state.notarizationDAO.notarizationClientMessage
                        this.notarization = fetchedNotarization
                    }
                }
                else -> {
                    setState {
                        notarizationErrorMessage = state.notarizationDAO.notarizationErrorMessage
                        notarizationClientErrorMessage = state.notarizationDAO.notarizationClientErrorMessage
                    }
                }
            }
        }
    }

    private fun updateNotarization(notarization: Notarization) {
        setState {
            notarizationMessage = ""
            notarizationErrorMessage = ""
            notarizationClientMessage = ""
            notarizationClientErrorMessage = ""
        }

        state.mainScope.launch {
            val updatedNotarization = state.notarizationDAO.updateNotarization(notarization)

            if (updatedNotarization) {
                setState {
                    notarizationMessage = state.notarizationDAO.notarizationMessage
                    notarizationClientMessage = state.notarizationDAO.notarizationClientMessage
                }
            } else {
                setState {
                    notarizationErrorMessage = state.notarizationDAO.notarizationErrorMessage
                    notarizationClientErrorMessage = state.notarizationDAO.notarizationClientErrorMessage
                }
            }
        }
    }

    private fun clearNotarizationForm() {
        setState {
            notarizationMessage = ""
            notarizationErrorMessage = ""
            notarizationClientMessage = ""
            notarizationClientErrorMessage = ""
            notarization = Notarization(
                0, "", 0, NotarizationCategory.NOT_SET, false, 0F, Date(),
                NotarizationStatus.NOT_SET, NotarizationDeliveryMethod.NOT_SET, "",
                NotarizationClient("", "", "", "", ""), 0
            )
        }
    }
}

fun RBuilder.newAssignmentPage(handler: RProps.() -> Unit): ReactElement {
    return child(NewAssignmentPage::class) {
        this.attrs(handler)
    }
}