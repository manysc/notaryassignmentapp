package report

import escrowOfficer.EscrowOfficer
import escrowOfficer.EscrowOfficerDAO
import framework.workerFormButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.px
import notarization.*
import notary.Notary
import notary.NotaryDAO
import react.*
import kotlin.js.Date

external interface ReportsPageState : RState {
    var pageMessage: String
    var pageErrorMessage: String
    var notarizationMessage: String
    var notarizationErrorMessage: String
    var notarizationClientMessage: String
    var notarizationClientErrorMessage: String

    var notarizationDAO: NotarizationDAO
    var notarizations: List<Notarization>
    var selectedNotarization: Notarization

    var escrowOfficerDAO: EscrowOfficerDAO
    var escrowOfficers: List<EscrowOfficer>

    var notaryDAO: NotaryDAO
    var notaries: List<Notary>

    var sortByEscrowNumber: Boolean
    var sortByEscrowOfficer: Boolean
    var sortByCategory: Boolean
    var sortByAttorneyPowerSign: Boolean
    var sortByFee: Boolean
    var sortByDate: Boolean
    var sortByClient: Boolean
    var sortByNotary: Boolean
    var sortByStatus: Boolean
    var sortByDeliveryMethod: Boolean

    var showDashboardFilters: Boolean

    var mainScope: CoroutineScope
}

class ReportsPage : RComponent<RProps, ReportsPageState>() {

    override fun ReportsPageState.init() {
        pageMessage = ""
        pageErrorMessage = ""
        notarizationMessage = ""
        notarizationErrorMessage = ""
        notarizationClientMessage = ""
        notarizationClientErrorMessage = ""

        notarizationDAO = NotarizationDAO()
        notarizations = listOf()
        selectedNotarization = Notarization(
            0, "", 0, NotarizationCategory.NOT_SET, false, 0F, Date(),
            NotarizationStatus.NOT_SET, NotarizationDeliveryMethod.NOT_SET, "",
            NotarizationClient("", "", "", "", ""), 0
        )

        escrowOfficerDAO = EscrowOfficerDAO()
        escrowOfficers = listOf()

        notaryDAO = NotaryDAO()
        notaries = listOf()

        sortByEscrowNumber = false
        sortByEscrowOfficer = false
        sortByCategory = false
        sortByAttorneyPowerSign = false
        sortByFee = false
        sortByDate = false
        sortByClient = false
        sortByNotary = false
        sortByStatus = false
        sortByDeliveryMethod = false

        showDashboardFilters = false

        mainScope = MainScope()

        mainScope.launch {
            val inactiveNotarizations = notarizationDAO.fetchInactiveNotarizations()
            val fetchedEscrowOfficers = escrowOfficerDAO.fetchEscrowOfficers()
            val fetchedNotaries = notaryDAO.fetchNotaries()

            setState {
                notarizations = inactiveNotarizations
                escrowOfficers = fetchedEscrowOfficers
                notaries = fetchedNotaries
            }
        }
    }

    override fun RBuilder.render() {
        // Notarization List
        notarizationList {
            componentHeader = "Completed Notarizations"
            notarizations = state.notarizations
            selectedNotarization = state.selectedNotarization
            escrowOfficers = state.escrowOfficers
            notaries = state.notaries

            sortByEscrowNumber = state.sortByEscrowNumber
            sortByEscrowOfficer = state.sortByEscrowOfficer
            sortByCategory = state.sortByCategory
            sortByAttorneyPowerSign = state.sortByAttorneyPowerSign
            sortByFee = state.sortByFee
            sortByDate = state.sortByDate
            sortByClient = state.sortByClient
            sortByNotary = state.sortByNotary
            sortByStatus = state.sortByStatus
            sortByDeliveryMethod = state.sortByDeliveryMethod

            showDashboardFilters = state.showDashboardFilters

            onEscrowNumberClicked = {
                setState {
                    sortByEscrowNumber = true
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onEscrowOfficerClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = true
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onCategoryClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = true
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onAttorneyPowerSignClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = true
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onFeeClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = true
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onDateClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = true
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onClientClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = true
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onNotaryClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = true
                    sortByStatus = false
                    sortByDeliveryMethod = false
                }
            }

            onStatusClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = true
                    sortByDeliveryMethod = false
                }
            }

            onDeliveryMethodClicked = {
                setState {
                    sortByEscrowNumber = false
                    sortByEscrowOfficer = false
                    sortByCategory = false
                    sortByAttorneyPowerSign = false
                    sortByFee = false
                    sortByDate = false
                    sortByClient = false
                    sortByNotary = false
                    sortByStatus = false
                    sortByDeliveryMethod = true
                }
            }

            onSelectedNotarization = { notarization ->
                setState {
                    selectedNotarization = notarization
                }
            }

            onShowDashboardFilters = {
                setState {
                    showDashboardFilters = !showDashboardFilters
                }
            }
        }

        // Only show Notarization form when there is a selected Notarization
        if (state.selectedNotarization.escrowNumber.isNotEmpty()) {
            // Notarization
            notarizationForm {
                pageName = PageName.REPORTS
                pageMessage = state.notarizationMessage
                pageErrorMessage = state.notarizationErrorMessage
                formClass = "formLeftBottom"
                notarization = state.selectedNotarization
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
                        notarization.powerOfAttorneySigning = !state.selectedNotarization.powerOfAttorneySigning
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
                pageName = PageName.REPORTS
                pageMessage = state.notarizationClientMessage
                pageErrorMessage = state.notarizationClientErrorMessage
                formClass = "formCenterBottom"
                notarization = state.selectedNotarization
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
        }
    }
}

fun RBuilder.reportsPage(handler: RProps.() -> Unit): ReactElement {
    return child(ReportsPage::class) {
        this.attrs(handler)
    }
}