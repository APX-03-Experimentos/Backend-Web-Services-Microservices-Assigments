package apx.inc.assignments_service.assignments.interfaces.rest.resource;

public record CreateSubmissionResource(
        Long assignmentId,
        String content) {

    public CreateSubmissionResource{
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("Assignment ID must be greater than 0");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
    }

}
