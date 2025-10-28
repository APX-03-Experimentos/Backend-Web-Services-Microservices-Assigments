package apx.inc.assigments_service.assigments.infrastructure.authotization.sfs.jwt.configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Schema(description = "Multipart request for submission creation", type = "object")
public class MultipartSubmissionRequest {

    @Schema(
            description = "Submission data as JSON string",
            type = "string",
            example = "{\"assignmentId\": , \"content\": , \"imageUrl\": }"
    )
    private String submissionData;

    @Schema(
            description = "Files to upload",
            type = "array",
            format = "binary"
    )
    private MultipartFile[] files;
}
