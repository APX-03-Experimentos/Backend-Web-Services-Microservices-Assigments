package apx.inc.assigments_service.assigments.domain.model.queries;

public record GetSubmissionsByAssignmentIdQuery(Long assignmentId) {

    public GetSubmissionsByAssignmentIdQuery {
        if (assignmentId == null || assignmentId <= 0) {
            throw new IllegalArgumentException("ChallengeId must be greater than 0");
        }
    }
}
