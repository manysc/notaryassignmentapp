package escrowOfficer

import framework.ApplicationConfiguration

enum class EscrowOfficerURIs(val uri: String) {
    GET_ESCROW_OFFICERS("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/escrowOfficers/")
}