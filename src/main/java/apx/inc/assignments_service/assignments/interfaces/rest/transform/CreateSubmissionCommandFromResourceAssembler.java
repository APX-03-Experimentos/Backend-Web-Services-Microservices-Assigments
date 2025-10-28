package apx.inc.assignments_service.assignments.interfaces.rest.transform;


import apx.inc.assignments_service.assignments.domain.model.commands.CreateSubmissionCommand;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.CreateSubmissionResource;

public class CreateSubmissionCommandFromResourceAssembler {
    public static CreateSubmissionCommand toCommandFromResource(CreateSubmissionResource createSubmissionResource, Long studentId) {
        return new CreateSubmissionCommand(
                createSubmissionResource.assignmentId(),
                studentId,
                createSubmissionResource.content()
        );
    }
}