package notarization

enum class NotarizationStatus {
    NOT_SET,
    AVAILABLE,
    ASSIGNED,
    RESCHEDULE,
    IN_PROGRESS,
    SIGNED,
    DELIVERED,
    COMPLETED,
    REJECTED,
    CANCELLED
}