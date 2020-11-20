package notarization

import framework.ApplicationConfiguration

enum class NotarizationURIs(val uri: String) {
    GET_NOTARIZATIONS("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/notarizations/"),
    GET_NOTARIZATIONS_BY_STATUS("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/notarizations/?status="),
    GET_ACTIVE_NOTARIZATIONS("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/notarizations/active"),
    GET_INACTIVE_NOTARIZATIONS("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/notarizations/inactive")
}