package apx.inc.assigments_service.assigments.application.internal.commandservices;


import apx.inc.assigments_service.assigments.domain.model.aggregates.Assignment;
import apx.inc.assigments_service.assigments.domain.model.commands.*;
import apx.inc.assigments_service.assigments.domain.services.AssignmentCommandService;
import apx.inc.assigments_service.assigments.infrastructure.persistence.jpa.repositories.AssignmentRepository;
import apx.inc.assigments_service.assigments.infrastructure.persistence.jpa.repositories.SubmissionRepository;
import apx.inc.assigments_service.assigments.application.acl.CloudinaryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentCommandServiceImpl implements AssignmentCommandService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CloudinaryService cloudinaryService ;

    public AssignmentCommandServiceImpl(AssignmentRepository assignmentRepository, SubmissionRepository submissionRepository, CloudinaryService cloudinaryService) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.cloudinaryService=cloudinaryService;
    }

    @Override
    public Long handle(CreateAssignmentCommand command) {
        // Validar título único en el curso
        if (assignmentRepository.existsByTitleAndCourseId(command.title(), command.courseId())) {
            throw new IllegalArgumentException("An assignment with this title already exists in the course");
        }

        var assignment = new Assignment(command);
        try {
            assignmentRepository.save(assignment);
            return assignment.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create assignment: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Assignment> handle(UpdateAssignmentCommand command) {
        var assignment = assignmentRepository.findById(command.assigmentId());
        if (assignment.isEmpty()) {
            throw new IllegalArgumentException("Assignment not found: " + command.assigmentId());
        }

        var existingAssignmentWithSameTitle = assignmentRepository.findByTitleAndCourseId(
                command.title(), command.courseId()
        );

        if (existingAssignmentWithSameTitle.isPresent() &&
                !existingAssignmentWithSameTitle.get().getId().equals(command.assigmentId())) {
            throw new IllegalArgumentException("An assignment with this title already exists in the course");
        }

        var assignmentToUpdate = assignment.get();
        try {
            var updatedAssignment = assignmentRepository.save(assignmentToUpdate
                    .updateInformation(
                            command.title(),
                            command.description(),
                            command.courseId(),
                            command.deadline(),
                            command.imageUrl()
                    ));
            return Optional.of(updatedAssignment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to update assignment: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteAssignmentCommand command) {
        var assignment = assignmentRepository.findById(command.assigmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + command.assigmentId()));

        // ✅ NUEVO: Verificar si tiene submissions antes de eliminar
        var submissions = submissionRepository.findByAssignmentId(command.assigmentId());
        if (!submissions.isEmpty()) {
            throw new IllegalStateException("Cannot delete assignment with existing submissions. Delete submissions first.");
        }

        try {
            assignmentRepository.delete(assignment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete assignment: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(AddFilesToAssignmentCommand command) {
        Assignment assignment = assignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + command.assignmentId()));

        for (String fileUrl : command.fileUrls()) {
            assignment.addFileUrl(fileUrl);
        }

        try {
            assignmentRepository.save(assignment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to add files to assignment: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(RemoveFileFromAssignmentCommand command) {
        Assignment assignment = assignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + command.assignmentId()));

        assignment.removeFileUrl(command.fileUrl());

        try {
            assignmentRepository.save(assignment);
            cloudinaryService.deleteFile(command.fileUrl());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to remove file from assignment: " + e.getMessage(), e);
        }
    }


}
