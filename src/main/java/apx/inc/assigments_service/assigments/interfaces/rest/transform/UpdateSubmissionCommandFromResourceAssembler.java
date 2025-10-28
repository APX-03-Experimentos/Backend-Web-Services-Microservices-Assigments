package apx.inc.assigments_service.assigments.interfaces.rest.transform;


import apx.inc.assigments_service.assigments.domain.model.commands.UpdateSubmissionCommand;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.UpdateSubmissionResource;

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
