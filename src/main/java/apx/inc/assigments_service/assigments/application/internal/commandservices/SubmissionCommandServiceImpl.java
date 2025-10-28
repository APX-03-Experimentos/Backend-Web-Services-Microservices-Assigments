package apx.inc.assigments_service.assigments.application.internal.commandservices;

import apx.inc.assigments_service.assigments.domain.model.commands.*;
import apx.inc.assigments_service.assigments.domain.model.entities.Submission;
import apx.inc.assigments_service.assigments.domain.model.valueobjects.States;
import apx.inc.assigments_service.assigments.domain.services.SubmissionCommandService;
import apx.inc.assigments_service.assigments.infrastructure.persistence.jpa.repositories.AssignmentRepository;
import apx.inc.assigments_service.assigments.infrastructure.persistence.jpa.repositories.SubmissionRepository;
import apx.inc.assigments_service.assigments.application.acl.CloudinaryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubmissionCommandServiceImpl implements SubmissionCommandService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final CloudinaryService cloudinaryService ;

    public SubmissionCommandServiceImpl(SubmissionRepository submissionRepository, AssignmentRepository assignmentRepository,CloudinaryService cloudinaryService) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.cloudinaryService=cloudinaryService;
    }

    @Override
    public Long handle(CreateSubmissionCommand command) {
        var assignment = assignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment with ID " + command.assignmentId() + " not found"));

        if (submissionRepository.existsByAssignmentIdAndStudentId(command.assignmentId(), command.studentId())) {
            throw new IllegalArgumentException("Student already has a submission for this assignment");
        }

        var submission = new Submission(assignment, command);
        try {
            submissionRepository.save(submission);
            return submission.getId();
        } catch (Exception e) {
            throw new RuntimeException("Error creating submission: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Submission> handle(UpdateSubmissionCommand command) {
        var submission = submissionRepository.findById(command.submissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission with ID " + command.submissionId() + " not found"));

        var assignment = assignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment with ID " + command.assignmentId() + " not found"));

        try {
            var updatedSubmission = submissionRepository.save(submission
                    .updateSubmission(
                            command
                    ));
            return Optional.of(updatedSubmission);
        } catch (Exception e) {
            throw new RuntimeException("Error updating submission: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteSubmissionCommand command) {
        if (!submissionRepository.existsById(command.submissionId())) {
            throw new IllegalArgumentException("Submission with ID " + command.submissionId() + " not found");
        }
        try {
            submissionRepository.deleteById(command.submissionId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting submission: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Submission> handle(GradeSubmissionCommand command) {
        var submission = submissionRepository.findById(command.submissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));

        submission.gradeSubmission(command.score());
        submission.changeState(States.GRADED);

        try {
            submissionRepository.save(submission);
            return Optional.of(submission);
        } catch (Exception e) {
            throw new RuntimeException("Error grading submission: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(AddFilesToSubmissionCommand command) {
        Submission submission = submissionRepository.findById(command.submissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + command.submissionId()));

        for (String fileUrl : command.fileUrls()) {
            submission.addFileUrl(fileUrl);
        }

        try {
            submissionRepository.save(submission);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to add files to submission: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(RemoveFileFromSubmissionCommand command) {
        Submission submission = submissionRepository.findById(command.submissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + command.submissionId()));

        submission.removeFileUrl(command.fileUrl());

        try {
            submissionRepository.save(submission);
            // ✅ PERMITIDO: CloudinaryService es servicio técnico
            cloudinaryService.deleteFile(command.fileUrl());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to remove file from submission: " + e.getMessage(), e);
        }
    }


}
