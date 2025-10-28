package apx.inc.assigments_service.assigments.interfaces.rest.transform;

import apx.inc.assigments_service.assigments.domain.model.aggregates.Assignment;
import apx.inc.assigments_service.assigments.interfaces.rest.resource.AssignmentResource;

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
