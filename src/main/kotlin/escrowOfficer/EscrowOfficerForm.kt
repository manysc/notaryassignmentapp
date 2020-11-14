package escrowOfficer

import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.css
import styled.styledInput
import styled.styledLabel
import styled.styledTextArea

external interface EscrowOfficerFormProps : RProps {
    var pageMessage: String
    var pageErrorMessage: String
    var escrowOfficer: EscrowOfficer

    var onChangedName: (String) -> Any
    var onChangedLastName: (String) -> Any
    var onChangedAddress: (String) -> Any
    var onChangedPhoneNumber: (String) -> Any
    var onChangedEmail: (String) -> Any
    var onChangedCompany: (String) -> Any
}

class EscrowOfficerForm : RComponent<EscrowOfficerFormProps, RState>() {

    override fun RBuilder.render() {
        form(classes = "formCenter") {
            br {  }
            br {  }

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

            if (props.pageMessage.isNotEmpty() || props.pageErrorMessage.isNotEmpty()) {
                br {}
            }

            br {}

            styledInput(type = InputType.text, name = "escrowOfficerName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "First Name"
                    value = props.escrowOfficer.name
                    required = true
                    onChangeFunction = ::onNameChanged
                }
            }

            br {}

            styledInput(type = InputType.text, name = "escrowOfficerLastName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Last Name"
                    value = props.escrowOfficer.lastName
                    required = true
                    onChangeFunction = ::onLastNameChanged
                }
            }

            styledTextArea(rows = "1") {
                css {
                    color = Color.white
                }

                attrs {
                    if (props.escrowOfficer.address.isNotEmpty()) {
                        rows = "2"
                    }

                    name = "escrowOfficerAddress"
                    placeholder = "Address"
                    value = props.escrowOfficer.address
                    required = true
                    onChangeFunction = ::onAddressChanged
                }
            }

            styledInput(type = InputType.text, name = "escrowOfficerPhone") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Phone Number"
                    value = props.escrowOfficer.phoneNumber
                    required = true
                    onChangeFunction = ::onPhoneNumberChanged
                }
            }

            styledInput(type = InputType.text, name = "escrowOfficerEmail") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Email"
                    value = props.escrowOfficer.email
                    required = true
                    onChangeFunction = ::onEmailChanged
                }
            }

            styledInput(type = InputType.text, name = "escrowOfficerCompany") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Company"
                    value = props.escrowOfficer.company
                    required = true
                    onChangeFunction = ::onCompanyChanged
                }
            }
        }
    }

    private fun onNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedName(value)
    }

    private fun onLastNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedLastName(value)
    }

    private fun onAddressChanged(event: Event) {
        val target = event.target as HTMLTextAreaElement
        val value = target.value

        this.props.onChangedAddress(value)
    }

    private fun onPhoneNumberChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedPhoneNumber(value)
    }

    private fun onEmailChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedEmail(value)
    }

    private fun onCompanyChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedCompany(value)
    }
}

fun RBuilder.escrowOfficerForm(handler: EscrowOfficerFormProps.() -> Unit): ReactElement {
    return child(EscrowOfficerForm::class) {
        this.attrs(handler)
    }
}