package apx.inc.assigments_service.assigments.interfaces.rest.transform;

import apx.inc.assigments_service.assigments.domain.model.commands.UpdateAssignmentCommand;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.UpdateAssignmentResource;

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