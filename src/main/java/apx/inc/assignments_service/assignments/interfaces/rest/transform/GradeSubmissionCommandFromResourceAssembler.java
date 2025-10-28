package apx.inc.assignments_service.assignments.interfaces.rest.transform;


import apx.inc.assignments_service.assignments.domain.model.commands.GradeSubmissionCommand;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.GradeSubmissionResource;

public class GradeSubmissionCommandFromResourceAssembler {
    public static GradeSubmissionCommand toCommandFromResource(Long submissionId, GradeSubmissionResource resource) {
        return new GradeSubmissionCommand(submissionId, resource.score());
    }
}
