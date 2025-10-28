package apx.inc.assignments_service.assignments.interfaces.rest.transform;


import apx.inc.assignments_service.assignments.domain.model.commands.CreateAssignmentCommand;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.CreateAssignmentResource;

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
