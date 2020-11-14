package notarization

import PageName
import escrowOfficer.EscrowOfficer
import framework.DateUtils
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.enableViewState
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.*
import kotlin.Float
import kotlin.js.Date

external interface NotarizationFormProps : RProps {
    var pageName: PageName
    var pageMessage: String
    var pageErrorMessage: String

    var formClass: String
    var notarization: Notarization
    var escrowOfficers: List<EscrowOfficer>

    var onChangedEscrowNumber: (String) -> Any
    var onChangedEscrowOfficer: (String) -> Any
    var onChangedCategory: (NotarizationCategory) -> Any
    var onChangedPowerOfAttorneySigning: () -> Any
    var onChangedFee: (Float) -> Any
    var onChangedDate: (Date) -> Any
    var onChangedStatus: (NotarizationStatus) -> Any
    var onChangedDeliveryMethod: (NotarizationDeliveryMethod) -> Any
    var onChangedComments: (String) -> Any
}

class NotarizationForm : RComponent<NotarizationFormProps, RState>() {
    override fun RBuilder.render() {
        form(classes = props.formClass) {
            h2 {
                +"Notarization"
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

            styledInput(type = InputType.text, name = "escrowNumber") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Escrow Number"
                    value = props.notarization.escrowNumber
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onEscrowNumberChanged
                }
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
                    name = "escrowOfficer"

                    // Get Escrow Officer from FullName and Phone Number
                    for (escrowOfficer in props.escrowOfficers) {
                        if (escrowOfficer.id == props.notarization.escrowOfficerId) {
                            value = "${escrowOfficer.name} ${escrowOfficer.lastName} ${escrowOfficer.phoneNumber}"
                        }
                    }

                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onEscrowOfficerChanged
                }

                for (escrowOfficer in props.escrowOfficers.sortedWith(compareBy({ it.name }, { it.lastName }))) {
                    styledOption {
                        css {
                            backgroundColor = Color.gray
                            color = Color.white
                        }
                        +"${escrowOfficer.name} ${escrowOfficer.lastName} ${escrowOfficer.phoneNumber}"
                    }
                }
            }

            br { }

            styledSelect {
                css {
                    color = Color.white
                    background = "transparent"
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 600.px
                }

                attrs {
                    name = "category"
                    value = props.notarization.category.toString()
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onCategoryChanged
                }

                for (category in NotarizationCategory.values()) {
                    styledOption {
                        css {
                            backgroundColor = Color.gray
                            color = Color.white
                        }
                        if (category == NotarizationCategory.NOT_SET) {
                            +"Select Category"
                        } else {
                            +category.name
                        }
                    }
                }
            }

            br { }

            styledLabel {
                css {
                    background = "transparent"
                    marginLeft = 20.px
                }

                if (props.notarization.powerOfAttorneySigning) {
                    css {
                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    }
                    +"✅ "
                } else {
                    +"❌ "
                }

                +"Power of Attorney Signing"

                attrs {
                    if (props.pageName != PageName.DASHBOARD && props.pageName != PageName.REPORTS) {
                        onClickFunction = {
                            props.onChangedPowerOfAttorneySigning()
                        }
                    }
                }
            }

            styledInput(type = InputType.text, name = "fee") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Fee"
                    value = if (props.notarization.fee > 0) {
                        props.notarization.fee.toString()
                    } else {
                        ""
                    }
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onFeeChanged
                }
            }

            styledLabel {
                css {
                    marginLeft = 20.px
                }
                +"Date:  "
            }

            styledInput(type = InputType.dateTimeLocal, name = "date") {
                css {
                    color = Color.white
                    backgroundColor = Color.transparent
                    fontFamily = "Arial"
                    fontSize = 30.px
                }

                attrs {
                    value = DateUtils.getDateTimeFromLocalDateFormat(props.notarization.date)
                    required = true
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onDateChanged
                }
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
                    name = "status"
                    value = props.notarization.status.toString()
                    required = true
                    if (props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onStatusChanged
                }

                for (status in NotarizationStatus.values()) {
                    styledOption {
                        css {
                            backgroundColor = Color.gray
                            color = Color.white
                        }

                        if (status == NotarizationStatus.NOT_SET) {
                            +"Select Status"
                        } else {
                            +status.name
                        }
                    }
                }
            }

            br { }

            styledSelect {
                css {
                    color = Color.white
                    background = "transparent"
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 600.px
                }

                attrs {
                    name = "deliveryMethod"
                    value = props.notarization.deliveryMethod.toString()
                    required = true
                    if (props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onDeliveryMethodChanged
                }

                for (deliveryMethod in NotarizationDeliveryMethod.values()) {
                    styledOption {
                        css {
                            backgroundColor = Color.gray
                            color = Color.white
                        }

                        if (deliveryMethod == NotarizationDeliveryMethod.NOT_SET) {
                            +"Select Delivery Method"
                        } else {
                            +deliveryMethod.name
                        }
                    }
                }
            }

            br { }

            styledTextArea(rows = "1") {
                css {
                    color = Color.white
                    overflowWrap = OverflowWrap.normal
                }

                attrs {
                    if (props.notarization.comments.isNotEmpty()) {
                        rows = "3"
                    }

                    name = "comments"
                    placeholder = "Comments"
                    value = props.notarization.comments
                    required = false
                    if (props.pageName == PageName.DASHBOARD || props.pageName == PageName.REPORTS) {
                        disabled = true
                    }
                    onChangeFunction = ::onCommentsChanged
                }
            }
        }
    }

    private fun onEscrowNumberChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedEscrowNumber(value)
    }

    private fun onEscrowOfficerChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        val value = target.value

        this.props.onChangedEscrowOfficer(value)
    }

    private fun onCategoryChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Select Category") {
            value = NotarizationCategory.NOT_SET.toString()
        }

        this.props.onChangedCategory(NotarizationCategory.valueOf(value))
    }

    private fun onFeeChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedFee(value.toFloat())
    }

    private fun onDateChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value
        // Parse String to Date
        val valueToDate = Date(value)

        this.props.onChangedDate(valueToDate)
    }

    private fun onStatusChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Select Status") {
            value = NotarizationStatus.NOT_SET.toString()
        }

        this.props.onChangedStatus(NotarizationStatus.valueOf(value))
    }

    private fun onDeliveryMethodChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Select Delivery Method") {
            value = NotarizationStatus.NOT_SET.toString()
        }

        this.props.onChangedDeliveryMethod(NotarizationDeliveryMethod.valueOf(value))
    }

    private fun onCommentsChanged(event: Event) {
        val target = event.target as HTMLTextAreaElement
        val value = target.value

        this.props.onChangedComments(value)
    }
}

fun RBuilder.notarizationForm(handler: NotarizationFormProps.() -> Unit): ReactElement {
    return child(NotarizationForm::class) {
        this.attrs(handler)
    }
}