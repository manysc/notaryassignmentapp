package notary

import framework.ApplicationConfiguration

enum class NotaryURIs(val uri: String) {
    GET_NOTARIES("http://${ApplicationConfiguration.getServiceIP()}:${ApplicationConfiguration.getServicePort()}/api/notaries/")
}