package apx.inc.assigments_service.assigments.interfaces.rest.resource;

public record UpdateSubmissionResource(
        Long assignmentId,  // ✅ NUEVO: Para validar en el command service
        String content
) {
    public UpdateSubmissionResource {
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("Assignment ID must be greater than 0");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
    }
}