package apx.inc.assigments_service.assigments.domain.model.commands;

public record GradeSubmissionCommand(
        Long submissionId,
        int score
) {
    public GradeSubmissionCommand{
        if (score < 0 || score > 20) {
            throw new IllegalArgumentException("Score must be between 0 and 20");
        }
    }
}
