package apx.inc.assignments_service.assignments.interfaces.rest.transform;


import apx.inc.assignments_service.assignments.domain.model.entities.Submission;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.SubmissionResource;

public class SubmissionResourceFromEntityAssembler {
    public static SubmissionResource toResourceFromEntity(Submission submissionEntity){
        return new SubmissionResource(
                submissionEntity.getId(),
                submissionEntity.getAssignment().getId(),
                submissionEntity.getStudentId(),
                submissionEntity.getContent(),
                submissionEntity.getScore(),
                submissionEntity.getImageUrl(),
                submissionEntity.getState().name(),
                submissionEntity.getFileUrls()
        );
    }
}
