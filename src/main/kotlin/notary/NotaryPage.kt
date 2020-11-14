package notary

import framework.HttpResponseStatus
import framework.workerFormButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.px
import react.*
import kotlin.js.Date

external interface NotaryPageState : RState {
    var pageMessage: String
    var pageErrorMessage: String

    var notaryDAO: NotaryDAO
    var notaries: List<Notary>
    var selectedNotary: Notary

    var mainScope: CoroutineScope
}

class NotaryPage : RComponent<RProps, NotaryPageState>() {

    override fun NotaryPageState.init() {
        notaryDAO = NotaryDAO()
        pageMessage = ""
        pageErrorMessage = ""
        notaries = listOf()
        selectedNotary = Notary(0, "", "", "", "", "", "", certification = NotaryCertification(), licenseExpirationDate = Date(), "25 K", 0, 0)
        mainScope = MainScope()

        mainScope.launch {
            val fetchedNotaries = notaryDAO.fetchNotaries()

            setState {
                notaries = fetchedNotaries
            }
        }
    }

    override fun RBuilder.render() {
        notaryList {
            workers = state.notaries
            selectedWorker = state.selectedNotary
            onSelectedWorker = { notary ->
                setState {
                    pageMessage = ""
                    pageErrorMessage = ""
                    selectedNotary = notary
                }
            }
        }

        notaryForm {
            pageMessage = state.pageMessage
            pageErrorMessage = state.pageErrorMessage
            notary = state.selectedNotary

            onChangedName = { name ->
                setState {
                    notary.name = name
                }
            }

            onChangedLastName = { lastName ->
                setState {
                    notary.lastName = lastName
                }
            }

            onChangedAddress = { address ->
                setState {
                    notary.address = address
                }
            }

            onChangedPhoneNumber = { phoneNumber ->
                setState {
                    notary.phoneNumber = phoneNumber
                }
            }

            onChangedEmail = { email ->
                setState {
                    notary.email = email
                }
            }

            onChangedCompany = { company ->
                setState {
                    notary.company = company
                }
            }

            onChangedRealEstateAgentCert = {
                setState {
                    notary.certification.realStateAgent = !notary.certification.realStateAgent
                }
            }

            onChangedSigningAgentCert = {
                setState {
                    notary.certification.signingAgent = !notary.certification.signingAgent
                }
            }

            onChangedAttorneyCert = {
                setState {
                    notary.certification.attorney = !notary.certification.attorney
                }
            }

            onChangedLoanOfficerCert = {
                setState {
                    notary.certification.loanOfficer = !notary.certification.loanOfficer
                }
            }

            onChangedLicenseExpirationDate = { date ->
                setState {
                    notary.licenseExpirationDate = date
                }
            }

            onChangedErrorOmissionInsuranceAmount = { amount ->
                setState {
                    notary.errorOmissionInsuranceAmount = amount
                }
            }

            onChangedExperienceYears = { experienceYears ->
                setState {
                    notary.experienceYears = experienceYears
                }

            }

            onChangedScore = { score ->
                setState {
                    notary.score = score
                }

            }
        }

        // Notary Form Buttons
        workerFormButtons {
            marginTop = 130.px

            showAddButton = true
            showRemoveButton = true
            showSearchButton = true
            showUpdateButton = true
            showClearButton = true

            onAddClicked = {
                addNotary(state.selectedNotary)
            }

            onRemoveClicked = {
                removeNotary(state.selectedNotary)
            }

            onSearchClicked = {
                searchNotary(state.selectedNotary)
            }

            onUpdateClicked = {
                updateNotary(state.selectedNotary)
            }

            onClearClicked = {
                clearNotaryForm()
            }
        }
    }

    private fun addNotary(notary: Notary) {
        val name = notary.name
        val lastName = notary.lastName
        val address = notary.address
        val phoneNumber = notary.phoneNumber
        val email = notary.email
        val company = notary.company

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty() or address.isEmpty() or phoneNumber.isEmpty()
            or email.isEmpty() or company.isEmpty()
        ) {
            setState {
                pageErrorMessage = "Please check Notary information."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.notaryDAO.postNotary(notary)) {
                    HttpResponseStatus.CREATED.status -> {
                        val fetchedNotaries = state.notaryDAO.fetchNotaries()
                        setState {
                            pageMessage = "Notary $name $lastName has been added."
                            notaries = fetchedNotaries
                        }
                    }
                    HttpResponseStatus.NOT_MODIFIED.status -> {
                        setState {
                            pageErrorMessage = "Notary $name $lastName already exists. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when adding Notary $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun removeNotary(notary: Notary) {
        val name = notary.name
        val lastName = notary.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() || lastName.isEmpty()) {
            setState {
                pageErrorMessage = "Please select a Notary to remove."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.notaryDAO.deleteNotary(name, lastName)) {
                    HttpResponseStatus.OK.status -> {
                        val fetchedNotaries = state.notaryDAO.fetchNotaries()
                        setState {
                            pageMessage = "Notary $name $lastName has been deleted."
                            notaries = fetchedNotaries

                            selectedNotary = Notary(0, "", "", "", "", "", "", certification = NotaryCertification(), licenseExpirationDate = Date(), "25 K", 0, 0)
                        }
                    }
                    HttpResponseStatus.NOT_FOUND.status -> {
                        val fetchedNotaries = state.notaryDAO.fetchNotaries()
                        setState {
                            pageErrorMessage = "Notary $name $lastName doesn't exist. Code $returnCode"
                            notaries = fetchedNotaries
                        }
                    }
                    HttpResponseStatus.MULTIPLE_CHOICES.status -> {
                        setState {
                            pageErrorMessage = "There is more than one Notary named $name $lastName. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when deleting Notary $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun searchNotary(notary: Notary) {
        val name = notary.name
        val lastName = notary.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty()) {
            setState {
                pageErrorMessage = "Please set Notary name and last name."
            }
        } else {
            state.mainScope.launch {
                val fetchedNotary = state.notaryDAO.fetchNotary(name, lastName)

                when {
                    fetchedNotary != null -> {
                        setState {
                            pageMessage = "Notary $name $lastName has been found."
                            selectedNotary = fetchedNotary
                        }
                    }
                    else -> {
                        when (val responseStatus = state.notaryDAO.responseStatus) {
                            HttpResponseStatus.NOT_FOUND -> {
                                setState {
                                    pageErrorMessage =
                                        "Notary $name $lastName was not found. Status $responseStatus"
                                }
                            }
                            HttpResponseStatus.INTERNAL_SERVER_ERROR -> {
                                setState {
                                    pageErrorMessage =
                                        "Notary $name $lastName has been found multiple times. Status $responseStatus"
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

    private fun updateNotary(notary: Notary) {
        val name = notary.name
        val lastName = notary.lastName

        setState {
            pageMessage = ""
            pageErrorMessage = ""
        }

        if (name.isEmpty() or lastName.isEmpty()
        ) {
            setState {
                pageErrorMessage = "Please check Notary information."
            }
        } else {
            state.mainScope.launch {
                when (val returnCode = state.notaryDAO.putNotary(notary)) {
                    HttpResponseStatus.OK.status -> {
                        setState {
                            pageMessage = "Notary $name $lastName has been updated."
                        }
                    }
                    HttpResponseStatus.NOT_FOUND.status -> {
                        setState {
                            pageErrorMessage = "Notary $name $lastName was not found. Code $returnCode"
                        }
                    }
                    HttpResponseStatus.NOT_SUPPORTED.status -> {
                        setState {
                            pageErrorMessage =
                                "Unsupported request state when adding Notary $name $lastName: $returnCode"
                        }
                    }
                }
            }
        }
    }

    private fun clearNotaryForm() {
        setState {
            pageMessage = ""
            pageErrorMessage = ""
            selectedNotary = Notary(0, "", "", "", "", "", "", certification = NotaryCertification(), licenseExpirationDate = Date(), "25 K", 0, 0)
        }
    }
}

fun RBuilder.notaryPage(handler: RProps.() -> Unit): ReactElement {
    return child(NotaryPage::class) {
        this.attrs(handler)
    }
}