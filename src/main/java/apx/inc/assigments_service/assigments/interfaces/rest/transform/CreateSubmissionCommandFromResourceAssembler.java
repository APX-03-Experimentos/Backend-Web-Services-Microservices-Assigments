package apx.inc.assigments_service.assigments.interfaces.rest.transform;


import apx.inc.assigments_service.assigments.domain.model.commands.CreateSubmissionCommand;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.CreateSubmissionResource;

public class CreateSubmissionCommandFromResourceAssembler {
    public static CreateSubmissionCommand toCommandFromResource(CreateSubmissionResource createSubmissionResource, Long studentId) {
        return new CreateSubmissionCommand(
                createSubmissionResource.assignmentId(),
                studentId,
                createSubmissionResource.content()
        );
    }
}