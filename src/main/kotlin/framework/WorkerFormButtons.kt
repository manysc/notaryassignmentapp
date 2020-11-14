package framework

import kotlinx.css.LinearDimension
import kotlinx.css.marginTop
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.br
import react.dom.button
import react.dom.div
import styled.styledButton

external interface WorkerFormButtonProps : RProps {
    var marginTop: LinearDimension

    var showAddButton: Boolean
    var showRemoveButton: Boolean
    var showSearchButton: Boolean
    var showUpdateButton: Boolean
    var showClearButton: Boolean
    var showCancelButton: Boolean

    var onAddClicked:    () -> Unit
    var onRemoveClicked: () -> Unit
    var onSearchClicked: () -> Unit
    var onUpdateClicked: () -> Unit
    var onClearClicked:  () -> Unit
    var onCancelClicked:  () -> Unit
}

class WorkerFormButtons : RComponent<WorkerFormButtonProps, RState>() {

    override fun RBuilder.render() {
        var topMarginSet = false

        div(classes = "btn-group") {
            if (props.showAddButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Add"
                    attrs {
                        onClickFunction = {
                            props.onAddClicked()
                        }
                    }
                }
                br {}
            }

            if (props.showRemoveButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Remove"
                    attrs {
                        onClickFunction = {
                            props.onRemoveClicked()
                        }
                    }
                }
                br {}
            }

            if (props.showSearchButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Search"
                    attrs {
                        onClickFunction = {
                            props.onSearchClicked()
                        }
                    }
                }
                br {}
            }

            if (props.showUpdateButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Update"
                    attrs {
                        onClickFunction = {
                            props.onUpdateClicked()
                        }
                    }
                }
                br {}
            }

            if (props.showClearButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Clear"
                    attrs {
                        onClickFunction = {
                            props.onClearClicked()
                        }
                    }
                }
            }

            if (props.showCancelButton) {
                styledButton {
                    if(!topMarginSet) {
                        css.marginTop = props.marginTop
                        topMarginSet = true
                    }

                    +"Cancel"
                    attrs {
                        onClickFunction = {
                            props.onCancelClicked()
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.workerFormButtons(handler: WorkerFormButtonProps.() -> Unit): ReactElement {
    return child(WorkerFormButtons::class) {
        this.attrs(handler)
    }
}