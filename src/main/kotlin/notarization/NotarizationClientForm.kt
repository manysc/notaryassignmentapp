package notarization

import PageName
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import notary.Notary
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.*
import react.dom.br
import react.dom.div
import react.dom.h2
import react.dom.value
import styled.*

external interface NotarizationClientFormProps : RProps {
    var pageName: PageName
    var pageMessage: String
    var pageErrorMessage: String

    var formClass: String
    var notarization: Notarization
    var notaries: List<Notary>

    var onChangedClientName: (String) -> Any
    var onChangedClientLastName: (String) -> Any
    var onChangedClientAddress: (String) -> Any
    var onChangedClientPhoneNumber: (String) -> Any
    var onChangedClientEmail: (String) -> Any

    var onChangedNotary: (String) -> Any
}

class NotarizationClientForm : RComponent<NotarizationClientFormProps, RState>() {

    override fun RBuilder.render() {
        div(classes = props.formClass) {
            h2 {
                +"Client"
            }

            if (props.pageMessage.isNotEmpty() || props.pageErrorMessage.isNotEmpty()) {
                styledLabel {
                    css {
                        color = Color.limeGreen
                    }

                    attrs {
                        +props.pageMessage
                    }
                }

                styledLabel {
                    css {
                        color = Color.red.darken(15)
                    }

                    attrs {
                        +props.pageErrorMessage
                    }
                }


                br {}
                br {}
            }

            styledInput(type = InputType.text, name = "clientName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "First Name"
                    value = props.notarization.client.name
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onClientNameChanged
                }
            }

            br {}

            styledInput(type = InputType.text, name = "clientLastName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Last Name"
                    value = props.notarization.client.lastName
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onClientLastNameChanged
                }
            }

            styledTextArea(rows = "1") {
                css {
                    color = Color.white
                }

                attrs {
                    if (props.notarization.client.address.isNotEmpty()) {
                        rows = "2"
                    }

                    name = "clientAddress"
                    placeholder = "Address"
                    value = props.notarization.client.address
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onClientAddressChanged
                }
            }

            styledInput(type = InputType.text, name = "clientPhone") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Phone Number"
                    value = props.notarization.client.phoneNumber
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onClientPhoneNumberChanged
                }
            }

            styledInput(type = InputType.text, name = "clientEmail") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Email"
                    value = props.notarization.client.email
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onClientEmailChanged
                }
            }

            h2 {
                +"Notary"
            }

            styledSelect {
                css {
                    color = Color.white
                    background = "transparent"
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 600.px
                }

                attrs {
                    name = "notary"

                    // Get Notary from FullName and Phone Number
                    for (notary in props.notaries) {
                        if (notary.id == props.notarization.notaryId) {
                            value = "${notary.name} ${notary.lastName} ${notary.phoneNumber}"
                        }
                    }

                    required = false
                    if (props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onNotaryChanged
                }

                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }
                    +"Select Notary"
                }

                for (notaries in props.notaries.sortedWith(compareBy({ it.name }, { it.lastName }))) {
                    styledOption {
                        css {
                            backgroundColor = Color.gray
                            color = Color.white
                        }
                        +"${notaries.name} ${notaries.lastName} ${notaries.phoneNumber}"
                    }
                }
            }
        }
    }

    private fun onClientNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedClientName(value)
    }

    private fun onClientLastNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedClientLastName(value)
    }

    private fun onClientAddressChanged(event: Event) {
        val target = event.target as HTMLTextAreaElement
        val value = target.value

        this.props.onChangedClientAddress(value)
    }

    private fun onClientPhoneNumberChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedClientPhoneNumber(value)
    }

    private fun onClientEmailChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedClientEmail(value)
    }

    private fun onNotaryChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Select Notary") {
            value = ""
        }

        this.props.onChangedNotary(value)
    }
}

fun RBuilder.notarizationClientForm(handler: NotarizationClientFormProps.() -> Unit): ReactElement {
    return child(NotarizationClientForm::class) {
        this.attrs(handler)
    }
}