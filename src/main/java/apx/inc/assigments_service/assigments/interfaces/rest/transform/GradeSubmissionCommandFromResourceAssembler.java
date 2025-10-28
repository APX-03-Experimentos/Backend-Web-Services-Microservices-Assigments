package apx.inc.assigments_service.assigments.interfaces.rest.transform;


import apx.inc.assigments_service.assigments.domain.model.commands.GradeSubmissionCommand;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.GradeSubmissionResource;

public class GradeSubmissionCommandFromResourceAssembler {
    public static GradeSubmissionCommand toCommandFromResource(Long submissionId, GradeSubmissionResource resource) {
        return new GradeSubmissionCommand(submissionId, resource.score());
    }
}
