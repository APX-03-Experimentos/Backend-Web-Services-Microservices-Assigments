package apx.inc.assignments_service.assignments.domain.model.commands;

public record CreateSubmissionCommand(
    Long assignmentId,
    Long studentId,
    String content
) {

    public CreateSubmissionCommand {
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("Challenge ID must be greater than 0");
        }
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
    }
}
