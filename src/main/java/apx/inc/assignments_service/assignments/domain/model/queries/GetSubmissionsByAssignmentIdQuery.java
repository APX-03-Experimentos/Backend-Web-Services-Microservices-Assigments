package apx.inc.assignments_service.assignments.domain.model.queries;

public record GetSubmissionsByAssignmentIdQuery(Long assignmentId) {

    public GetSubmissionsByAssignmentIdQuery {
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("ChallengeId must be greater than 0");
        }
    }
}
