package notarization

import kotlinx.css.background
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.br
import styled.css
import styled.styledLabel

external interface NotarizationListColumnsProps : RProps {
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

    var onShowEscrowNumberClicked: () -> Any
    var onShowEscrowOfficerClicked: () -> Any
    var onShowCategoryClicked: () -> Any
    var onShowAttorneyPowerSignClicked: () -> Any
    var onShowFeeClicked: () -> Any
    var onShowDateClicked: () -> Any
    var onShowClientClicked: () -> Any
    var onShowAddressClicked: () -> Any
    var onShowNotaryClicked: () -> Any
    var onShowStatusClicked: () -> Any
    var onShowDeliveryMethodClicked: () -> Any
}

class NotarizationListColumns : RComponent<NotarizationListColumnsProps, RState>() {
    override fun RBuilder.render() {
        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showEscrowNumberColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Escrow Number"
            } else {
                +"✅ "
                +"Show Escrow Number"
            }

            attrs {
                onClickFunction = {
                    props.onShowEscrowNumberClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showEscrowOfficerColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Escrow Officer"
            } else {
                +"✅ "
                +"Show Escrow Officer"
            }

            attrs {
                onClickFunction = {
                    props.onShowEscrowOfficerClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showCategoryColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Category"
            } else {
                +"✅ "
                +"Show Category"
            }

            attrs {
                onClickFunction = {
                    props.onShowCategoryClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showAttorneyPowerSignColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Attorney Power Sign"
            } else {
                +"✅ "
                +"Show Attorney Power Sign"
            }

            attrs {
                onClickFunction = {
                    props.onShowAttorneyPowerSignClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showFeeColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Fee"
            } else {
                +"✅ "
                +"Show Fee"
            }

            attrs {
                onClickFunction = {
                    props.onShowFeeClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showDateColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Date"
            } else {
                +"✅ "
                +"Show Date"
            }

            attrs {
                onClickFunction = {
                    props.onShowDateClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showClientColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Client Name"
            } else {
                +"✅ "
                +"Show Client Name"
            }

            attrs {
                onClickFunction = {
                    props.onShowClientClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showAddressColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Client Address"
            } else {
                +"✅ "
                +"Show Client Address"
            }

            attrs {
                onClickFunction = {
                    props.onShowAddressClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showNotaryColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Notary"
            } else {
                +"✅ "
                +"Show Notary"
            }

            attrs {
                onClickFunction = {
                    props.onShowNotaryClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showStatusColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Status"
            } else {
                +"✅ "
                +"Show Status"
            }

            attrs {
                onClickFunction = {
                    props.onShowStatusClicked()
                }
            }
        }

        br { }

        styledLabel {
            css {
                background = "transparent"
            }

            if (props.showDeliveryMethodColumn) {
                css {
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                }
                +"❌ "
                +"Hide Delivery Method"
            } else {
                +"✅ "
                +"Show Delivery Method"
            }

            attrs {
                onClickFunction = {
                    props.onShowDeliveryMethodClicked()
                }
            }
        }
    }
}

fun RBuilder.notarizationListColumns(handler: NotarizationListColumnsProps.() -> Unit): ReactElement {
    return child(NotarizationListColumns::class) {
        this.attrs(handler)
    }
}