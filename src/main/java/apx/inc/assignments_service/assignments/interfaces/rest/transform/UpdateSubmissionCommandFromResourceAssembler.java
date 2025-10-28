package apx.inc.assignments_service.assignments.interfaces.rest.transform;


import apx.inc.assignments_service.assignments.domain.model.commands.UpdateSubmissionCommand;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.UpdateSubmissionResource;

public class UpdateSubmissionCommandFromResourceAssembler {
    public static UpdateSubmissionCommand toUpdateCommandFromResource(
            Long submissionId,
            UpdateSubmissionResource resource,
            Long studentId  // Del contexto de seguridad
    ) {
        return new UpdateSubmissionCommand(
                submissionId,
                resource.assignmentId(),  // ✅ Del resource
                studentId,               // ✅ Del contexto de seguridad
                resource.content()       // ✅ Del resource
        );
    }
}
