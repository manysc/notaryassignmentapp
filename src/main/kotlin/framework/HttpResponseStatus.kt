package framework

enum class HttpResponseStatus(val status: Short) {
    OK(200),
    CREATED(201),
    MULTIPLE_CHOICES(300),
    NOT_MODIFIED(304),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    NOT_SUPPORTED(-1)
}