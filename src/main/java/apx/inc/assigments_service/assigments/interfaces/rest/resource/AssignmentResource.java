package apx.inc.assigments_service.assigments.interfaces.rest.resource;

import java.util.Date;
import java.util.List;

public record AssignmentResource(
        Long id,
        String title,
        String description,
        Long courseId,
        Date deadline,
        String imageUrl,
        List<String> fileUrls) {
}
