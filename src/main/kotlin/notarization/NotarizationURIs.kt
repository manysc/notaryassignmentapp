package notarization

enum class NotarizationURIs(val uri: String) {
    GET_NOTARIZATIONS("http://localhost:8081/api/notarizations/"),
    GET_NOTARIZATIONS_BY_STATUS("http://localhost:8081/api/notarizations/?status="),
    GET_ACTIVE_NOTARIZATIONS("http://localhost:8081/api/notarizations/active"),
    GET_INACTIVE_NOTARIZATIONS("http://localhost:8081/api/notarizations/inactive")
}