import assignment.assignmentDashboardPage
import assignment.newAssignmentPage
import escrowOfficer.escrowOfficerPage
import notary.notaryPage
import react.*
import report.reportsPage

external interface MainPageState : RState {
    var showEscrowOfficers: Boolean
    var showNotaries: Boolean
    var showNewAssignment: Boolean
    var showAssignmentDashboard: Boolean
    var showReports: Boolean
}

class MainPage : RComponent<RProps, MainPageState>() {

    override fun RBuilder.render() {
        // Header
        header {
            onLogoutClicked = {
                setState {
                    showEscrowOfficers = false
                    showNotaries = false
                    showNewAssignment = false
                    showAssignmentDashboard = false
                    showReports = false
                }
            }
        }

        // Menu Bar
        menuBar {
            onEscrowOfficersClicked = {
                setState {
                    showEscrowOfficers = true
                    showNotaries = false
                    showNewAssignment = false
                    showAssignmentDashboard = false
                    showReports = false
                }
            }

            onNotariesClicked = {
                setState {
                    showEscrowOfficers = false
                    showNotaries = true
                    showNewAssignment = false
                    showAssignmentDashboard = false
                    showReports = false
                }
            }

            onManageAssignmentClicked = {
                setState {
                    showEscrowOfficers = false
                    showNotaries = false
                    showNewAssignment = true
                    showAssignmentDashboard = false
                    showReports = false
                }
            }

            onAssignmentDashboardClicked = {
                setState {
                    showEscrowOfficers = false
                    showNotaries = false
                    showNewAssignment = false
                    showAssignmentDashboard = true
                    showReports = false
                }
            }

            onReportsClicked = {
                setState {
                    showEscrowOfficers = false
                    showNotaries = false
                    showNewAssignment = false
                    showAssignmentDashboard = false
                    showReports = true
                }
            }
        }

        if (state.showEscrowOfficers) {
            escrowOfficerPage { }
        }

        if (state.showNotaries) {
            notaryPage { }
        }

        if (state.showNewAssignment) {
            newAssignmentPage { }
        }

        if (state.showAssignmentDashboard) {
            assignmentDashboardPage { }
        }

        if (state.showReports) {
            reportsPage {  }
        }
    }
}