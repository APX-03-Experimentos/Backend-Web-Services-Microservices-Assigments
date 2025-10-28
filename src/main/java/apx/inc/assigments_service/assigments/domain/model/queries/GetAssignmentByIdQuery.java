package apx.inc.assigments_service.assigments.domain.model.queries;

public record GetAssignmentByIdQuery(Long assignmentId) {

    public GetAssignmentByIdQuery {
        if (assignmentId == null || assignmentId <= 0)
            throw new IllegalArgumentException("Assignment ID must be a positive number.");
    }
}
