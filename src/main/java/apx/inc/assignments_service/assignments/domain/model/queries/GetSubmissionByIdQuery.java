package apx.inc.assignments_service.assignments.domain.model.queries;

public record GetSubmissionByIdQuery(Long submissionId) {

    public GetSubmissionByIdQuery {
        if (submissionId == null || submissionId <= 0) {
            throw new IllegalArgumentException("SubmissionId must be greater than 0");
        }
    }
}
