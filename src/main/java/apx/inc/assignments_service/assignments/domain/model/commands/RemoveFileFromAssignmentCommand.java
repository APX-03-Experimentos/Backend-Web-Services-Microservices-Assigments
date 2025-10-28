package apx.inc.assignments_service.assignments.domain.model.commands;

public record RemoveFileFromAssignmentCommand(
        Long assignmentId,
        String fileUrl
) {
    public RemoveFileFromAssignmentCommand {
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("Assignment ID must be greater than 0");
        }
        if (fileUrl == null || fileUrl.isBlank()) {
            throw new IllegalArgumentException("File URL cannot be null or blank");
        }
    }
}
