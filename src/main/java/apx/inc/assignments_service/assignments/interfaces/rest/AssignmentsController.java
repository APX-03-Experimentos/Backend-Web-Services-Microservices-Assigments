package apx.inc.assignments_service.assignments.interfaces.rest;

//import apx.inc.assigments_service.assigments.application.acl.UserValidationService;
import apx.inc.assignments_service.assignments.domain.model.commands.AddFilesToAssignmentCommand;
import apx.inc.assignments_service.assignments.domain.model.commands.DeleteAssignmentCommand;
import apx.inc.assignments_service.assignments.domain.model.commands.RemoveFileFromAssignmentCommand;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAllAssignmentsQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentByIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentsByCourseIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetFilesByAssignmentIdQuery;
import apx.inc.assignments_service.assignments.domain.services.AssignmentCommandService;
import apx.inc.assignments_service.assignments.domain.services.AssignmentQueryService;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.AssignmentResource;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.CreateAssignmentResource;
import apx.inc.assignments_service.assignments.interfaces.rest.resource.UpdateAssignmentResource;
import apx.inc.assignments_service.assignments.interfaces.rest.transform.AssignmentResourceFromEntityAssembler;
import apx.inc.assignments_service.assignments.interfaces.rest.transform.CreateAssignmentCommandFromResourceAssembler;
import apx.inc.assignments_service.assignments.interfaces.rest.transform.UpdateAssignmentCommandFromResourceAssembler;
import apx.inc.assignments_service.assignments.application.acl.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/assignments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Assignments", description = "Operations related to assignments")
@RequiredArgsConstructor
public class AssignmentsController {

    private final AssignmentCommandService assignmentAppCommandService;
    private final AssignmentQueryService assignmentAppQueryService;
    private final CloudinaryService cloudinaryService;

    //private final UserValidationService userValidationService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    @Operation(summary = "Create a new assignment", description = "Creates a new assignment with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assignment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<AssignmentResource> createAssignment(@RequestBody CreateAssignmentResource resource) {
        try {
            var command = CreateAssignmentCommandFromResourceAssembler.toCommandFromResource(resource);
            Long assignmentId = assignmentAppCommandService.handle(command);

            var assignment = assignmentAppQueryService.handle(new GetAssignmentByIdQuery(assignmentId))
                    .orElseThrow(() -> new RuntimeException("Failed to retrieve created assignment"));

            return new ResponseEntity<>(AssignmentResourceFromEntityAssembler.toResourceFromEntity(assignment), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{assignmentId}")
    @Operation(summary = "Update an existing assignment", description = "Updates the details of an existing assignment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<AssignmentResource> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody UpdateAssignmentResource resource) {
        try {
            var command = UpdateAssignmentCommandFromResourceAssembler.toCommandFromResource(assignmentId, resource);
            var assignment = assignmentAppCommandService.handle(command);

            return assignment.map(a -> ResponseEntity.ok(AssignmentResourceFromEntityAssembler.toResourceFromEntity(a)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{assignmentId}")
    @Operation(summary = "Delete a assignment", description = "Deletes an existing assignment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
        try {
            assignmentAppCommandService.handle(new DeleteAssignmentCommand(assignmentId));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{assignmentId}")
    @Operation(summary = "Get a assignment by ID", description = "Retrieves the details of a challenge by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<AssignmentResource> getAssignmentById(@PathVariable Long assignmentId) {
        var assignment = assignmentAppQueryService.handle(new GetAssignmentByIdQuery(assignmentId));
        return assignment.map(a -> ResponseEntity.ok(AssignmentResourceFromEntityAssembler.toResourceFromEntity(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all assignment", description = "Retrieves a list of all assignment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No assignment found")
    })
    public ResponseEntity<List<AssignmentResource>> getAllAssignments() {
        var assignments = assignmentAppQueryService.handle(new GetAllAssignmentsQuery());
        var resources = assignments.stream()
                .map(AssignmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get assignments by course ID", description = "Retrieves a list of assignments associated with a specific course ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No Assignments found for the group")
    })
    public ResponseEntity<List<AssignmentResource>> getAssignmentsByCourseId(@PathVariable Long courseId) {
        var assignments = assignmentAppQueryService.handle(new GetAssignmentsByCourseIdQuery(courseId));
        var resources = assignments.stream()
                .map(AssignmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/{assignmentId}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add files to assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<String>> addFilesToAssignment(
            @Parameter(description = "ID of the assignment", required = true)
            @PathVariable Long assignmentId,

            @Parameter(description = "Files to upload", required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")))
            @RequestPart("files") MultipartFile[] files) {

        try {
            List<String> uploadedUrls = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileUrl = cloudinaryService.uploadFile(file);
                    uploadedUrls.add(fileUrl);
                }
            }

            var command = new AddFilesToAssignmentCommand(assignmentId, uploadedUrls);
            assignmentAppCommandService.handle(command);

            return ResponseEntity.ok(uploadedUrls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{assignmentId}/files")
    @Operation(summary = "Remove file from assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "File removed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> removeFileFromAssignment(
            @PathVariable Long assignmentId,
            @RequestParam String fileUrl) {
        try {
            var command = new RemoveFileFromAssignmentCommand(assignmentId, fileUrl);
            assignmentAppCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{assignmentId}/files")
    @Operation(summary = "Get all files from an assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    public ResponseEntity<List<String>> getFilesByAssignmentId(@PathVariable Long assignmentId) {
        try {
            var fileUrls = assignmentAppQueryService.handle(new GetFilesByAssignmentIdQuery(assignmentId));
            return ResponseEntity.ok(fileUrls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}