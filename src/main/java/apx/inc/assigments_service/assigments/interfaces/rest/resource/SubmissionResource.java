package apx.inc.assigments_service.assigments.interfaces.rest.resource;

import java.util.List;

public record SubmissionResource(
        Long id,
        Long assignmentId,
        Long studentId,
        String content,
        int score,
        String imageUrl,
        String status,
        List<String> fileUrls
) {
}
