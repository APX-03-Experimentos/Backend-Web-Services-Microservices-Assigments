package apx.inc.assigments_service.assigments.interfaces.rest.transform;


import apx.inc.assigments_service.assigments.domain.model.commands.CreateAssignmentCommand;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.CreateAssignmentResource;

public class CreateAssignmentCommandFromResourceAssembler {

    public static CreateAssignmentCommand toCommandFromResource(
            CreateAssignmentResource resource) {

        return new CreateAssignmentCommand(
                resource.title(),
                resource.description(),
                resource.courseId(),
                resource.deadline()
        );
    }
}
