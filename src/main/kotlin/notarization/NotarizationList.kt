package notarization

import escrowOfficer.EscrowOfficer
import framework.DateUtils
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import notary.Notary
import react.*
import react.dom.*
import styled.css
import styled.styledLabel
import styled.styledTable
import styled.styledTd
import kotlin.Float
import kotlin.js.Date

external interface NotarizationListProps : RProps {
    var componentHeader: String
    var notarizations: List<Notarization>
    var selectedNotarization: Notarization
    var escrowOfficers: List<EscrowOfficer>
    var notaries: List<Notary>

    var onEscrowNumberClicked: () -> Unit
    var onEscrowOfficerClicked: () -> Unit
    var onCategoryClicked: () -> Unit
    var onAttorneyPowerSignClicked: () -> Unit
    var onFeeClicked: () -> Unit
    var onDateClicked: () -> Unit
    var onClientClicked: () -> Unit
    var onNotaryClicked: () -> Unit
    var onStatusClicked: () -> Unit
    var onDeliveryMethodClicked: () -> Unit

    var onSelectedNotarization: (Notarization) -> Unit

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
    var onShowDashboardFilters: () -> Any
}

external interface NotarizationListState : RState {
    var showEscrowNumberColumn: Boolean
    var showEscrowOfficerColumn: Boolean
    var showCategoryColumn: Boolean
    var showAttorneyPowerSignColumn: Boolean
    var showFeeColumn: Boolean
    var showDateColumn: Boolean
    var showClientColumn: Boolean
    var showAddressColumn: Boolean
    var showNotaryColumn: Boolean
    var showStatusColumn: Boolean
    var showDeliveryMethodColumn: Boolean

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
    var deliveryMethodFilter: NotarizationDeliveryMethod
}

class NotarizationList : RComponent<NotarizationListProps, NotarizationListState>() {

    override fun NotarizationListState.init() {
        showEscrowNumberColumn = true
        showEscrowOfficerColumn = true
        showCategoryColumn = true
        showAttorneyPowerSignColumn = true
        showFeeColumn = true
        showDateColumn = true
        showClientColumn = true
        showAddressColumn = true
        showNotaryColumn = true
        showStatusColumn = true
        showDeliveryMethodColumn = true

        escrowNumberFilter = null
        escrowOfficerFilter = null
        categoryFilter = NotarizationCategory.NOT_SET
        attorneyPowerSignFilter = null
        feeFilter = null
        dateFilter = null
        clientFilter = null
        addressFilter = null
        notaryFilter = null
        statusFilter = NotarizationStatus.NOT_SET
        deliveryMethodFilter = NotarizationDeliveryMethod.NOT_SET
    }

    override fun RBuilder.render() {
        div(classes = "formDashboardFilters") {
            styledLabel {
                css {
                    fontSize = 40.px
                }
                +"☰"

                attrs {
                    onClickFunction = {
                        props.onShowDashboardFilters()
                    }
                }
            }

            if (props.showDashboardFilters) {
                br { }
                br { }

                styledLabel {
                    css {
                        background = "transparent"
                        fontWeight = FontWeight.bold
                    }
                    +"Show / Hide Columns:"
                }

                br { }

                notarizationListColumns {
                    showEscrowNumberColumn = state.showEscrowNumberColumn
                    showEscrowOfficerColumn = state.showEscrowOfficerColumn
                    showCategoryColumn = state.showCategoryColumn
                    showAttorneyPowerSignColumn = state.showAttorneyPowerSignColumn
                    showFeeColumn = state.showFeeColumn
                    showDateColumn = state.showDateColumn
                    showClientColumn = state.showClientColumn
                    showAddressColumn = state.showAddressColumn
                    showNotaryColumn = state.showNotaryColumn
                    showStatusColumn = state.showStatusColumn
                    showDeliveryMethodColumn = state.showDeliveryMethodColumn

                    onShowEscrowNumberClicked = {
                        setState {
                            showEscrowNumberColumn = !showEscrowNumberColumn
                        }
                    }

                    onShowEscrowOfficerClicked = {
                        setState {
                            showEscrowOfficerColumn = !showEscrowOfficerColumn
                        }
                    }

                    onShowCategoryClicked = {
                        setState {
                            showCategoryColumn = !showCategoryColumn
                        }
                    }

                    onShowAttorneyPowerSignClicked = {
                        setState {
                            showAttorneyPowerSignColumn = !showAttorneyPowerSignColumn
                        }
                    }

                    onShowFeeClicked = {
                        setState {
                            showFeeColumn = !showFeeColumn
                        }
                    }

                    onShowDateClicked = {
                        setState {
                            showDateColumn = !showDateColumn
                        }
                    }

                    onShowClientClicked = {
                        setState {
                            showClientColumn = !showClientColumn
                        }
                    }

                    onShowAddressClicked = {
                        setState {
                            showAddressColumn = !showAddressColumn
                        }
                    }

                    onShowNotaryClicked = {
                        setState {
                            showNotaryColumn = !showNotaryColumn
                        }
                    }

                    onShowStatusClicked = {
                        setState {
                            showStatusColumn = !showStatusColumn
                        }
                    }

                    onShowDeliveryMethodClicked = {
                        setState {
                            showDeliveryMethodColumn = !showDeliveryMethodColumn
                        }
                    }
                }

                br { }
                br { }

                styledLabel {
                    css {
                        background = "transparent"
                        fontWeight = FontWeight.bold
                    }
                    +"Filter by:"
                }

                br { }

                notarizationListFilters {
                    escrowNumberFilter = state.escrowNumberFilter
                    escrowOfficerFilter = state.escrowOfficerFilter
                    categoryFilter = state.categoryFilter
                    attorneyPowerSignFilter = state.attorneyPowerSignFilter
                    feeFilter = state.feeFilter
                    dateFilter = state.dateFilter
                    clientFilter = state.clientFilter
                    addressFilter = state.addressFilter
                    notaryFilter = state.notaryFilter
                    statusFilter = state.statusFilter
                    deliveryMethodFilter = state.deliveryMethodFilter

                    onEscrowNumberFilterChanged = { escrowNumber ->
                        setState {
                            escrowNumberFilter = escrowNumber
                        }
                    }

                    onEscrowOfficerFilterChanged = { escrowOfficer ->
                        setState {
                            escrowOfficerFilter = escrowOfficer
                        }
                    }

                    onCategoryFilterChanged = { category ->
                        setState {
                            categoryFilter = category
                        }
                    }

                    onAttorneyPowerSignFilterChanged = { attorneyPowerSign ->
                        setState {
                            attorneyPowerSignFilter = attorneyPowerSign
                        }
                    }

                    onFeeFilterChanged = { fee ->
                        setState {
                            feeFilter = fee
                        }
                    }

                    onDateFilterChanged = { date ->
                        setState {
                            dateFilter = date
                        }
                    }

                    onClientFilterChanged = { client ->
                        setState {
                            clientFilter = client
                        }
                    }

                    onAddressFilterChanged = { address ->
                        setState {
                            addressFilter = address
                        }
                    }

                    onNotaryFilterChanged = { notary ->
                        setState {
                            notaryFilter = notary
                        }
                    }

                    onStatusFilterChanged = { status ->
                        setState {
                            statusFilter = status
                        }
                    }

                    onDeliveryMethodFilterChanged = { deliveryMethod ->
                        setState {
                            deliveryMethodFilter = deliveryMethod
                        }
                    }
                }
            }
        }

        var divClass = "formDashboardLeft"
        if (props.showDashboardFilters) {
            divClass = "formDashboard"
        }

        div(classes = divClass) {
            h2 {
                +props.componentHeader
            }

            div(classes = "notarizationTable") {
                styledTable {
                    var count = props.notarizations.size

                    thead {
                        tr {
                            if (state.showEscrowNumberColumn) {
                                styledTd() {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onEscrowNumberClicked()
                                        }
                                    }
                                    +"Escrow Number"

                                    if (props.sortByEscrowNumber) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showEscrowOfficerColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onEscrowOfficerClicked()
                                        }
                                    }
                                    +"Escrow Officer"

                                    if (props.sortByEscrowOfficer) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showCategoryColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onCategoryClicked()
                                        }
                                    }
                                    +"Category"

                                    if (props.sortByCategory) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showAttorneyPowerSignColumn) {
                                styledTd {
                                    css {
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onAttorneyPowerSignClicked()
                                        }
                                    }
                                    +"Attorney Power Sign"

                                    if (props.sortByAttorneyPowerSign) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showFeeColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onFeeClicked()
                                        }
                                    }
                                    +"Fee"

                                    if (props.sortByFee) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showDateColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onDateClicked()
                                        }
                                    }
                                    +"Date"

                                    if (props.sortByDate) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showClientColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onClientClicked()
                                        }
                                    }
                                    +"Client"

                                    if (props.sortByClient) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showAddressColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    +"Address"
                                }
                            }

                            if (state.showNotaryColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onNotaryClicked()
                                        }
                                    }
                                    +"Notary"

                                    if (props.sortByNotary) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showStatusColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onStatusClicked()
                                        }
                                    }
                                    +"Status"

                                    if (props.sortByStatus) {
                                        +"▼"
                                    }
                                }
                            }

                            if (state.showDeliveryMethodColumn) {
                                styledTd {
                                    css {
                                        whiteSpace = WhiteSpace.nowrap
                                        textAlign = TextAlign.center
                                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                                    }
                                    attrs {
                                        onClickFunction = {
                                            props.onDeliveryMethodClicked()
                                        }
                                    }
                                    +"Delivery Method"

                                    if (props.sortByDeliveryMethod) {
                                        +"▼"
                                    }
                                }
                            }
                        }
                    }

                    for (notarization in props.notarizations.sortedBy {
                        when {
                            props.sortByEscrowNumber -> {
                                it.escrowNumber
                            }
                            props.sortByEscrowOfficer -> {
                                val escrowOfficer = getEscrowOfficer(it.escrowOfficerId)
                                "${escrowOfficer?.name} ${escrowOfficer?.lastName}"
                            }
                            props.sortByCategory -> {
                                it.category.toString()
                            }
                            props.sortByAttorneyPowerSign -> {
                                it.powerOfAttorneySigning.toString()
                            }
                            props.sortByFee -> {
                                it.fee.toString()
                            }
                            props.sortByDate -> {
                                getStringDateTime(it.date.toString())
                            }
                            props.sortByClient -> {
                                "${it.client.name} ${it.client.lastName}"
                            }
                            props.sortByNotary -> {
                                val notary = getNotary(it.notaryId!!)
                                "${notary?.name} ${notary?.lastName}"
                            }
                            props.sortByStatus -> {
                                it.status.toString()
                            }
                            props.sortByDeliveryMethod -> {
                                it.deliveryMethod.toString()
                            }
                            else -> {
                                getStringDateTime(it.date.toString())
                            }
                        }
                    }) {
                        if (!state.escrowNumberFilter.isNullOrEmpty()) {
                            if (!notarization.escrowNumber.contains(state.escrowNumberFilter!!)) {
                                continue
                            }
                        }

                        if (!state.escrowOfficerFilter.isNullOrEmpty()) {
                            val escrowOfficer = getEscrowOfficer(notarization.escrowOfficerId)
                            if (escrowOfficer != null) {
                                if (escrowOfficer.name.isNotEmpty()) {
                                    if (!escrowOfficer.name.contains(state.escrowOfficerFilter!!)) {
                                        if (escrowOfficer.lastName.isNotEmpty()) {
                                            if (!escrowOfficer.lastName.contains(state.escrowOfficerFilter!!)) {
                                                continue
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (state.categoryFilter != NotarizationCategory.NOT_SET) {
                            if (!notarization.category.toString().contains(state.categoryFilter.toString())) {
                                continue
                            }
                        }

                        if (state.attorneyPowerSignFilter != null) {
                            if (notarization.powerOfAttorneySigning != state.attorneyPowerSignFilter) {
                                continue
                            }
                        }

                        if (state.feeFilter != null) {
                            if (notarization.fee != state.feeFilter) {
                                continue
                            }
                        }

                        if (state.dateFilter != null) {
                            // Sample date 2021-01-18T09:50:00.000+00:00
                            val dateTimeArray = notarization.date.toString().split('T')
                            if (!dateTimeArray[0].contains(state.dateFilter!!)) {
                                val dateTime = DateUtils.getDateTimeFromStandardFormat(notarization.date)

                                if(!dateTime.contains(state.dateFilter!!)) {
                                    continue
                                }
                            }
                        }

                        if (!state.clientFilter.isNullOrEmpty()) {
                            if (notarization.client.name.isNotEmpty()) {
                                if (!notarization.client.name.contains(state.clientFilter!!)) {
                                    if (notarization.client.lastName.isNotEmpty()) {
                                        if (!notarization.client.lastName.contains(state.clientFilter!!)) {
                                            continue
                                        }
                                    }
                                }
                            }
                        }

                        if (!state.addressFilter.isNullOrEmpty()) {
                            if (!notarization.client.address.contains(state.addressFilter.toString())) {
                                continue
                            }
                        }

                        if (!state.notaryFilter.isNullOrEmpty()) {
                            if(notarization.notaryId != null) {
                                val notary = getNotary(notarization.notaryId!!)
                                if (notary != null) {
                                    if (notary.name.isNotEmpty()) {
                                        if (!notary.name.contains(state.notaryFilter!!)) {
                                            if (notary.lastName.isNotEmpty()) {
                                                if (!notary.lastName.contains(state.notaryFilter!!)) {
                                                    continue
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (state.statusFilter != NotarizationStatus.NOT_SET) {
                            if (!notarization.status.toString().contains(state.statusFilter.toString())) {
                                continue
                            }
                        }

                        if (state.deliveryMethodFilter != NotarizationDeliveryMethod.NOT_SET) {
                            if (!notarization.deliveryMethod.toString().contains(state.deliveryMethodFilter.toString())) {
                                continue
                            }
                        }

                        count--

                        thead {
                            tr {
                                key = notarization.id.toString()
                                attrs {
                                    onClickFunction = {
                                        props.onSelectedNotarization(notarization)
                                    }
                                }

                                if (state.showEscrowNumberColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                            +"▶ "
                                        }

                                        +notarization.escrowNumber
                                    }
                                }

                                if (state.showEscrowOfficerColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }

                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        val escrowOfficer = getEscrowOfficer(notarization.escrowOfficerId)
                                        when {
                                            escrowOfficer != null -> {
                                                +"${escrowOfficer.name} ${escrowOfficer.lastName}"
                                            }
                                            else -> {
                                                +""
                                            }
                                        }
                                    }
                                }

                                if (state.showCategoryColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }

                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }
                                        +notarization.category.toString()
                                    }
                                }

                                if (state.showAttorneyPowerSignColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            fontSize = 25.px
                                        }

                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        when {
                                            notarization.powerOfAttorneySigning -> {
                                                +"Yes"
                                            }
                                            else -> {
                                                +"No"
                                            }
                                        }
                                    }
                                }

                                if (state.showFeeColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +notarization.fee.toString()
                                    }
                                }

                                if (state.showDateColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +getStringDateTime(notarization.date.toString())
                                    }
                                }

                                if (state.showClientColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +"${notarization.client.name} ${notarization.client.lastName}"
                                    }
                                }

                                if (state.showAddressColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +notarization.client.address
                                    }
                                }

                                if (state.showNotaryColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        val notary = notarization.notaryId?.let { getNotary(it) }
                                        when {
                                            notary != null -> {
                                                +"${notary.name} ${notary.lastName}"
                                            }
                                            else -> {
                                                +""
                                            }
                                        }
                                    }
                                }

                                if (state.showStatusColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +notarization.status.toString()
                                    }
                                }

                                if (state.showDeliveryMethodColumn) {
                                    styledTd {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"
                                            whiteSpace = WhiteSpace.nowrap
                                            fontSize = 25.px
                                        }
                                        if (count == 0) {
                                            css {
                                                borderBottom = "1px solid white"
                                            }
                                        }

                                        if (notarization.escrowNumber == props.selectedNotarization.escrowNumber) {
                                            css {
                                                borderTop = "5px solid white"
                                                borderBottom = "5px solid white"
                                            }
                                        }

                                        +notarization.deliveryMethod.toString()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getEscrowOfficer(id: Long): EscrowOfficer? {
        return props.escrowOfficers.find { it.id == id }
    }

    private fun getNotary(id: Long): Notary? {
        return props.notaries.find { it.id == id }
    }

    private fun getStringDateTime(date: String): String {
        val rawToDate = Date(date)
        // Parse UTC Date, i.e. Sat, 24 Oct 2020 18:46:09 GMT
        val dateToArray = rawToDate.toUTCString().split(' ')
        val day = dateToArray[1]
        val month = DateUtils.getNumberedMonth(dateToArray[2])
        val year = dateToArray[3]
        val timeArray = dateToArray[4].split(':')
        val hour = timeArray[0]
        val minute = timeArray[1]
        return "$year-$month-$day $hour:$minute"
    }
}

fun RBuilder.notarizationList(handler: NotarizationListProps.() -> Unit): ReactElement {
    return child(NotarizationList::class) {
        this.attrs(handler)
    }
}