package apx.inc.assignments_service.assignments.interfaces.rest.transform;

import apx.inc.assignments_service.assignments.domain.model.commands.UpdateAssignmentCommand;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.UpdateAssignmentResource;

public class UpdateAssignmentCommandFromResourceAssembler {
    public static UpdateAssignmentCommand toCommandFromResource(Long assignmentId, UpdateAssignmentResource resource){
        return new UpdateAssignmentCommand(
                assignmentId,
                resource.title(),
                resource.description(),
                resource.courseId(),
                resource.deadline(),
                resource.imageUrl()
        );
    }
}