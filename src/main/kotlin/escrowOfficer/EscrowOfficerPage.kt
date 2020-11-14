package escrowOfficer

import framework.HttpResponseStatus
import framework.workerFormButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.px
import react.*

external interface EscrowOfficerPageState : RState {
    var pageMessage: String
    var pageErrorMessage: String

    var escrowOfficerDAO: EscrowOfficerDAO
    var escrowOfficers: List<EscrowOfficer>
    var selectedEscrowOfficer: EscrowOfficer

    var mainScope: CoroutineScope
}

class EscrowOfficerPage : RComponent<RProps, EscrowOfficerPageState>() {

    override fun EscrowOfficerPageState.init() {
        pageMessage = ""
        pageErrorMessage = ""
        escrowOfficerDAO = EscrowOfficerDAO()
        escrowOfficers = listOf()
        selectedEscrowOfficer = EscrowOfficer(0, "", "", "", "", "", "")
        mainScope = MainScope()

        mainScope.launch {
            val fetchedEscrowOfficers = escrowOfficerDAO.fetchEscrowOfficers()
            setState {
                escrowOfficers = fetchedEscrowOfficers
            }
        }
    }

    override fun RBuilder.render() {
        // Escrow Officers List
        escrowOfficerList {
            workers = state.escrowOfficers
            selectedWorker = state.selectedEscrowOfficer
            onSelectedWorker = { escrowOfficer ->
                setState {
                    pageMessage = ""
                    pageErrorMessage = ""
                    selectedEscrowOfficer = escrowOfficer
                }
            }
        }

        // Escrow Officer Form
        escrowOfficerForm {
            pageMessage = state.pageMessage
            pageErrorMessage = state.pageErrorMessage
            escrowOfficer = state.selectedEscrowOfficer

            onChangedName = { name ->
                setState {
                    escrowOfficer.name = name
                }
            }

            onChangedLastName = { lastName ->
                setState {
                    escrowOfficer.lastName = lastName
                }
            }

            onChangedAddress = { address ->
                setState {
                    escrowOfficer.address = address
                }
            }

            onChangedPhoneNumber = { phoneNumber ->
                setState {
                    escrowOfficer.phoneNumber = phoneNumber
                }
            }

            onChangedEmail = { email ->
                setState {
                    escrowOfficer.email = email
                }
            }

            onChangedCompany = { company ->
                setState {
                    escrowOfficer.company = company
                }
            }
        }

        // Escrow Officer Form Buttons
        workerFormButtons {
            marginTop = 130.px

            showAddButton = true
            showRemoveButton = true
            showSearchButton = true
            showUpdateButton = true
            showClearButton = true

            onAddClicked = {
                addEscrowOfficer(state.selectedEscrowOfficer)
            }

            onRemoveClicked = {
                removeEscrowOfficer(state.selectedEscrowOfficer)
            }

            onSearchClicked = {
                searchEscrowOfficer(state.selectedEscrowOfficer)
            }

            onUpdateClicked = {
                updateEscrowOfficer(state.selectedEscrowOfficer)
            }

            onClearClicked = {
                clearEscrowOfficerForm()
            }
        }
    }

    private fun addEscrowOfficer(escrowOfficer: EscrowOfficer) {
        val name = escrowOfficer.name
        val lastName = escrowOfficer.lastName
        val address = escrowOfficer.address
        val phoneNumber = escrowOfficer.phoneNumber
        val email = escrowOfficer.email
        val company = escrowOfficer.company

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty() or address.isEmpty() or phoneNumber.isEmpty()
            or email.isEmpty() or company.isEmpty()
        ) {
            setState {
                pageErrorMessage = "Please check Escrow Officer information."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.escrowOfficerDAO.postEscrowOfficer(escrowOfficer)) {
                    HttpResponseStatus.CREATED.status -> {
                        val fetchedEscrowOfficers = state.escrowOfficerDAO.fetchEscrowOfficers()
                        setState {
                            pageMessage = "Escrow Officer $name $lastName has been added."
                            escrowOfficers = fetchedEscrowOfficers
                        }
                    }
                    HttpResponseStatus.NOT_MODIFIED.status -> {
                        setState {
                            pageErrorMessage = "Escrow Officer $name $lastName already exists. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when adding Escrow Officer $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun removeEscrowOfficer(escrowOfficer: EscrowOfficer) {
        val name = escrowOfficer.name
        val lastName = escrowOfficer.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() || lastName.isEmpty()) {
            setState {
                pageErrorMessage = "Please select an Escrow Officer to remove."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.escrowOfficerDAO.deleteEscrowOfficer(name, lastName)) {
                    HttpResponseStatus.OK.status -> {
                        val fetchedEscrowOfficers = state.escrowOfficerDAO.fetchEscrowOfficers()
                        setState {
                            pageMessage = "Escrow Officer $name $lastName has been deleted."
                            escrowOfficers = fetchedEscrowOfficers

                            selectedEscrowOfficer = EscrowOfficer(0, "", "", "", "", "", "")
                        }
                    }
                    HttpResponseStatus.NOT_FOUND.status -> {
                        val fetchedEscrowOfficers = state.escrowOfficerDAO.fetchEscrowOfficers()
                        setState {
                            pageErrorMessage = "Escrow Officer $name $lastName doesn't exist. Code $returnCode"
                            escrowOfficers = fetchedEscrowOfficers
                        }
                    }
                    HttpResponseStatus.MULTIPLE_CHOICES.status -> {
                        setState {
                            pageErrorMessage =
                                "There is more than one Escrow Officer named $name $lastName. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when deleting Escrow Officer $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun searchEscrowOfficer(escrowOfficer: EscrowOfficer) {
        val name = escrowOfficer.name
        val lastName = escrowOfficer.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty()) {
            setState {
                pageErrorMessage = "Please set Escrow Officer name and last name."
            }
        } else {
            state.mainScope.launch {
                val fetchedEscrowOfficer = state.escrowOfficerDAO.fetchEscrowOfficer(name, lastName)

                when {
                    fetchedEscrowOfficer != null -> {
                        setState {
                            pageMessage = "Escrow Officer $name $lastName has been found."
                            selectedEscrowOfficer = fetchedEscrowOfficer
                        }
                    }
                    else -> {
                        when (val responseStatus = state.escrowOfficerDAO.responseStatus) {
                            HttpResponseStatus.NOT_FOUND -> {
                                setState {
                                    pageErrorMessage =
                                        "Escrow Officer $name $lastName was not found. Status $responseStatus"
                                }
                            }
                            HttpResponseStatus.INTERNAL_SERVER_ERROR -> {
                                setState {
                                    pageErrorMessage =
                                        "Escrow Officer $name $lastName has been found multiple times. Status $responseStatus"
                                }
                            }
                            HttpResponseStatus.NOT_SUPPORTED -> {
                                setState {
                                    pageErrorMessage =
                                        "Unable to process response status. Status $responseStatus"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateEscrowOfficer(escrowOfficer: EscrowOfficer) {
        val name = escrowOfficer.name
        val lastName = escrowOfficer.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty()
        ) {
            setState {
                pageErrorMessage = "Please check Escrow Officer information."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.escrowOfficerDAO.putEscrowOfficer(escrowOfficer)) {
                    HttpResponseStatus.OK.status -> {
                        setState {
                            pageMessage = "Escrow Officer $name $lastName has been updated."
                        }
                    }
                    HttpResponseStatus.NOT_FOUND.status -> {
                        setState {
                            pageErrorMessage = "Escrow Officer $name $lastName was not found. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when adding Escrow Officer $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun clearEscrowOfficerForm() {
        setState {
            pageMessage = ""
            pageErrorMessage = ""
            selectedEscrowOfficer = EscrowOfficer(0, "", "", "", "", "", "")
        }
    }
}

fun RBuilder.escrowOfficerPage(handler: RProps.() -> Unit): ReactElement {
    return child(EscrowOfficerPage::class) {
        this.attrs(handler)
    }
}