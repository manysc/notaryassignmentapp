package notarization

enum class NotarizationURIs(val uri: String) {
    GET_NOTARIZATIONS("http://192.168.0.114:8081/api/notarizations/"),
    GET_NOTARIZATIONS_BY_STATUS("http://192.168.0.114:8081/api/notarizations/?status=")
}