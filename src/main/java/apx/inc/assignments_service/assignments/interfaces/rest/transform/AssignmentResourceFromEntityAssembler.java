package apx.inc.assignments_service.assignments.interfaces.rest.transform;

import apx.inc.assignments_service.assignments.domain.model.aggregates.Assignment;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.AssignmentResource;

public class AssignmentResourceFromEntityAssembler {
    public static AssignmentResource toResourceFromEntity(Assignment entity) {
        return new AssignmentResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCourseId(),
                entity.getDeadline(),
                entity.getImageUrl(),
                entity.getFileUrls()
        );
    }
}
