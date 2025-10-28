package apx.inc.assigments_service.assigments.domain.services;


import apx.inc.assigments_service.assigments.domain.model.entities.Submission;
import apx.inc.assigments_service.assigments.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface SubmissionQueryService {

    Optional<Submission> handle(GetSubmissionByIdQuery getSubmissionByIdQuery);

    List<Submission> handle(GetAllSubmissionsQuery query);

    List<Submission> handle(GetSubmissionsByAssignmentIdQuery query);

    List<Submission> handle(GetSubmissionsByStudentIdQuery query);

    List<Submission> handle(GetSubmissionsByStudentIdAndAssignmentIdQuery query);

    List<Submission> handle(GetSubmissionsByStudentIdAndCourseIdQuery query);

    List<Submission> handle(GetSubmissionsByCourseIdQuery query);

    List<String> handle(GetFilesBySubmissionIdQuery query);
}
