package notarization

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import react.*
import react.dom.br
import react.dom.input
import styled.css
import styled.styledOption
import styled.styledSelect
import kotlin.Float

external interface NotarizationListFiltersProps : RProps {
    var escrowNumberFilter: String?
    var escrowOfficerFilter: String?
    var categoryFilter: NotarizationCategory
    var attorneyPowerSignFilter: Boolean?
    var feeFilter: Float?
    var dateFilter: String?
    var clientFilter: String?
    var addressFilter: String?
    var notaryFilter: String?
    var statusFilter: NotarizationStatus
    var deliveryMethodFilter: NotarizationDeliveryMethod?

    var onEscrowNumberFilterChanged: (String) -> Any
    var onEscrowOfficerFilterChanged: (String) -> Any
    var onCategoryFilterChanged: (NotarizationCategory) -> Any
    var onAttorneyPowerSignFilterChanged: (Boolean?) -> Any
    var onFeeFilterChanged: (Float?) -> Any
    var onDateFilterChanged: (String?) -> Any
    var onClientFilterChanged: (String) -> Any
    var onAddressFilterChanged: (String?) -> Any
    var onNotaryFilterChanged: (String?) -> Any
    var onStatusFilterChanged: (NotarizationStatus) -> Any
    var onDeliveryMethodFilterChanged: (NotarizationDeliveryMethod) -> Any
}

class NotarizationListFilters : RComponent<NotarizationListFiltersProps, RState>() {

    override fun RBuilder.render() {
        input(classes = "filterInput", type = InputType.text, name = "escrowNumber") {

            attrs {
                placeholder = "Escrow Number"
                value = when {
                    props.escrowNumberFilter != null -> {
                        props.escrowNumberFilter!!
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onEscrowNumberFilterChanged
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "escrowOfficer") {

            attrs {
                placeholder = "Escrow Officer"
                value = when {
                    !props.escrowOfficerFilter.isNullOrEmpty() -> {
                        props.escrowOfficerFilter!!
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onEscrowOfficerFilterChanged
            }
        }

        br { }

        styledSelect {
            css {
                color = Color.white
                background = "transparent"
                fontFamily = "Arial"
                fontSize = 25.px
                width = 95.pct
                padding = "12px 0px"
            }

            attrs {
                name = "category"
                value = props.categoryFilter.toString()
                onChangeFunction = ::onCategoryFilterChanged
            }

            for (category in NotarizationCategory.values()) {
                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }

                    if (category == NotarizationCategory.NOT_SET) {
                        +"Category"
                    } else {
                        +category.name
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
                fontSize = 25.px
                width = 95.pct
                padding = "12px 0px"
            }

            attrs {
                name = "attorneyPowerSign"
                value = when {
                    props.attorneyPowerSignFilter == null -> {
                        "Attorney Power Sign"
                    }
                    props.attorneyPowerSignFilter!! -> {
                        "Yes"
                    }
                    else -> {
                        "No"
                    }
                }
                onChangeFunction = ::onAttorneyPowerSignFilterChanged
            }

            styledOption {
                css {
                    backgroundColor = Color.gray
                    color = Color.white
                }
                +"Attorney Power Sign"
            }
            styledOption {
                css {
                    backgroundColor = Color.gray
                    color = Color.white
                }
                +"No"
            }
            styledOption {
                css {
                    backgroundColor = Color.gray
                    color = Color.white
                }
                +"Yes"
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "fee") {

            attrs {
                placeholder = "Fee"
                value = when {
                    props.feeFilter != null -> {
                        props.feeFilter.toString()
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onFeeFilterChanged
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "date") {

            attrs {
                placeholder = "Date"
                value = when {
                    !props.dateFilter.isNullOrEmpty() -> {
                        props.dateFilter!!
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onDateFilterChanged
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "client") {

            attrs {
                placeholder = "Client"
                value = when {
                    !props.clientFilter.isNullOrEmpty() -> {
                        props.clientFilter!!
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onClientFilterChanged
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "address") {

            attrs {
                placeholder = "Address"
                value = when {
                    props.addressFilter != null -> {
                        props.addressFilter!!.toString()
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onAddressFilterChanged
            }
        }

        br { }

        input(classes = "filterInput", type = InputType.text, name = "notary") {

            attrs {
                placeholder = "Notary"
                value = when {
                    !props.notaryFilter.isNullOrEmpty() -> {
                        props.notaryFilter!!
                    }
                    else -> {
                        ""
                    }
                }
                onChangeFunction = ::onNotaryFilterChanged
            }
        }

        br { }

        styledSelect {
            css {
                color = Color.white
                background = "transparent"
                fontFamily = "Arial"
                fontSize = 25.px
                width = 95.pct
                padding = "12px 0px"
            }

            attrs {
                name = "status"
                value = props.statusFilter.toString()
                onChangeFunction = ::onStatusFilterChanged
            }

            for (status in NotarizationStatus.values()) {
                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }

                    if (status == NotarizationStatus.NOT_SET) {
                        +"Status"
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
                fontSize = 25.px
                width = 95.pct
                padding = "12px 0px"
            }

            attrs {
                name = "deliveryMethod"
                value = props.deliveryMethodFilter.toString()
                onChangeFunction = ::onDeliveryMethodFilterChanged
            }

            for (deliveryMethod in NotarizationDeliveryMethod.values()) {
                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }

                    if (deliveryMethod == NotarizationDeliveryMethod.NOT_SET) {
                        +"Delivery Method"
                    } else {
                        +deliveryMethod.name
                    }
                }
            }
        }
    }

    private fun onEscrowNumberFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onEscrowNumberFilterChanged(value)
    }

    private fun onEscrowOfficerFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onEscrowOfficerFilterChanged(value)
    }

    private fun onCategoryFilterChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Category") {
            value = NotarizationCategory.NOT_SET.toString()
        }

        this.props.onCategoryFilterChanged(NotarizationCategory.valueOf(value))
    }

    private fun onAttorneyPowerSignFilterChanged(event: Event) {
        val target = event.target as HTMLSelectElement

        when (target.value) {
            "Attorney Power Sign" -> {
                this.props.onAttorneyPowerSignFilterChanged(null)
            }
            "No" -> {
                this.props.onAttorneyPowerSignFilterChanged(false)
            }
            "Yes" -> {
                this.props.onAttorneyPowerSignFilterChanged(true)
            }
        }
    }

    private fun onFeeFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        when {
            target.value.isEmpty() -> {
                this.props.onFeeFilterChanged(null)
            }
            else -> {
                this.props.onFeeFilterChanged(target.value.toFloat())
            }
        }
    }

    private fun onDateFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        when {
            target.value.isEmpty() -> {
                this.props.onDateFilterChanged(null)
            }
            else -> {
                this.props.onDateFilterChanged(target.value)
            }
        }
    }

    private fun onClientFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onClientFilterChanged(value)
    }

    private fun onAddressFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onAddressFilterChanged(value)
    }

    private fun onNotaryFilterChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onNotaryFilterChanged(value)
    }

    private fun onStatusFilterChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Status") {
            value = NotarizationStatus.NOT_SET.toString()
        }

        this.props.onStatusFilterChanged(NotarizationStatus.valueOf(value))
    }

    private fun onDeliveryMethodFilterChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        var value = target.value

        if (value == "Delivery Method") {
            value = NotarizationDeliveryMethod.NOT_SET.toString()
        }

        this.props.onDeliveryMethodFilterChanged(NotarizationDeliveryMethod.valueOf(value))
    }
}

fun RBuilder.notarizationListFilters(handler: NotarizationListFiltersProps.() -> Unit): ReactElement {
    return child(NotarizationListFilters::class) {
        this.attrs(handler)
    }
}