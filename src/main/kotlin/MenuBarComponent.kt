import kotlinx.css.background
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledLi

external interface MenuBarProps : RProps {
    var onEscrowOfficersClicked: () -> Unit
    var onNotariesClicked: () -> Unit
    var onManageAssignmentClicked: () -> Unit
    var onAssignmentDashboardClicked: () -> Unit
    var onReportsClicked: () -> Unit
}

external interface MenuBarState : RState {
    var showEscrowOfficers: Boolean
    var showNotaries: Boolean
    var showNewAssignment: Boolean
    var showAssignmentDashboard: Boolean
    var showReports: Boolean
}

class MenuBarComponent : RComponent<MenuBarProps, MenuBarState>() {

    override fun RBuilder.render() {
        // Menu Bar
        ul {
            div(classes = "dropdown") {
                styledLi {
                    if(state.showEscrowOfficers) {
                        css {
                            background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                        }
                    } else {
                        css {
                            background = "transparent"
                        }
                    }

                    a ("#escrowOfficers") {
                        +"Escrow Officers"
                        attrs {
                            onClickFunction = {
                                setState {
                                    showEscrowOfficers = true
                                    showNotaries = false
                                    showNewAssignment = false
                                    showAssignmentDashboard = false
                                    showReports = false
                                }

                                props.onEscrowOfficersClicked()
                            }
                        }
                    }
                }

                styledLi {
                    if(state.showNotaries) {
                        css {
                            background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                        }
                    } else {
                        css {
                            background = "transparent"
                        }
                    }

                    a("#notaries") {
                        +"Notaries"
                        attrs {
                            onClickFunction = {
                                setState {
                                    showEscrowOfficers = false
                                    showNotaries = true
                                    showNewAssignment = false
                                    showAssignmentDashboard = false
                                    showReports = false
                                }

                                props.onNotariesClicked()
                            }
                        }
                    }
                }

                styledLi {
                    if (state.showNewAssignment || state.showAssignmentDashboard) {
                        css {
                            background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                        }
                    } else {
                        css {
                            background = "transparent"
                        }
                    }

                    a {
                        +"Assignments"
                    }

                    div(classes = "dropdown-content") {
                        a("#manageAssignment") {
                            +"Manage Assignment"
                            attrs {
                                onClickFunction = {
                                    setState {
                                        showEscrowOfficers = false
                                        showNotaries = false
                                        showNewAssignment = true
                                        showAssignmentDashboard = false
                                        showReports = false
                                    }

                                    props.onManageAssignmentClicked()
                                }
                            }
                        }

                        a("#dashboard") {
                            +"Dashboard"
                            attrs {
                                onClickFunction = {
                                    setState {
                                        showEscrowOfficers = false
                                        showNotaries = false
                                        showNewAssignment = false
                                        showAssignmentDashboard = true
                                        showReports = false
                                    }

                                    props.onAssignmentDashboardClicked()
                                }
                            }
                        }
                    }
                }

                styledLi {
                    if(state.showReports) {
                        css {
                            background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                        }
                    } else {
                        css {
                            background = "transparent"
                        }
                    }

                    a("#reports") {
                        +"Reports"
                        attrs {
                            onClickFunction = {
                                setState {
                                    showEscrowOfficers = false
                                    showNotaries = false
                                    showNewAssignment = false
                                    showAssignmentDashboard = false
                                    showReports = true
                                }

                                props.onReportsClicked()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.menuBar(handler: MenuBarProps.() -> Unit): ReactElement {
    return child(MenuBarComponent::class) {
        this.attrs(handler)
    }
}