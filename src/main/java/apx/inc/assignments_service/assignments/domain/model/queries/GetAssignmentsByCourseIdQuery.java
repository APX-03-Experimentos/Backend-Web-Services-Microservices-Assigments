package apx.inc.assignments_service.assignments.domain.model.queries;

public record GetAssignmentsByCourseIdQuery(Long courseId) {

    public GetAssignmentsByCourseIdQuery {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Assignment ID must be a positive number.");
        }
    }
}
