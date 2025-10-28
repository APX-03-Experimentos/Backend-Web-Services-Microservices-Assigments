package apx.inc.assignments_service.assignments.domain.model.queries;

public record GetSubmissionsByStudentIdAndCourseIdQuery(Long studentId, Long courseId) {

    public GetSubmissionsByStudentIdAndCourseIdQuery {
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive number");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Group ID must be a positive number");
        }
    }
}
