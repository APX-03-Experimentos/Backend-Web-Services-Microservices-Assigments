package apx.inc.assignments_service.assignments.application.internal.queryservices;

import apx.inc.assignments_service.assignments.domain.model.entities.Submission;
import apx.inc.assignments_service.assignments.domain.model.queries.*;
import apx.inc.assignments_service.assignments.domain.services.SubmissionQueryService;
import apx.inc.assignments_service.assignments.infrastructure.persistence.jpa.repositories.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionQueryServiceImpl implements SubmissionQueryService {

    private final SubmissionRepository submissionRepository;


    public SubmissionQueryServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }



    @Override
    public Optional<Submission> handle(GetSubmissionByIdQuery query) {
        // ✅ SIMPLIFICADO: Solo busca la submission
        return submissionRepository.findById(query.submissionId());
    }

    @Override
    public List<Submission> handle(GetAllSubmissionsQuery query) {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> handle(GetSubmissionsByAssignmentIdQuery query) {
        return submissionRepository.findByAssignmentId(query.assignmentId());
    }

    @Override
    public List<Submission> handle(GetSubmissionsByStudentIdQuery query) {
        return submissionRepository.findByStudentId(query.studentId());
    }

    @Override
    public List<Submission> handle(GetSubmissionsByStudentIdAndAssignmentIdQuery query) {
        return submissionRepository.findByStudentIdAndAssignmentId(
                query.studentId(),
                query.assignmentId()
        );
    }

    @Override
    public List<Submission> handle(GetSubmissionsByStudentIdAndCourseIdQuery query) {
        // ✅ OPTIMIZADO: Usando el nuevo método del repository
        return submissionRepository.findByStudentIdAndCourseId(
                query.studentId(),
                query.courseId()
        );
    }

    @Override
    public List<Submission> handle(GetSubmissionsByCourseIdQuery query) {
        // ✅ OPTIMIZADO: Usando el nuevo método del repository
        return submissionRepository.findByCourseId(query.courseId());
    }

    @Override
    public List<String> handle(GetFilesBySubmissionIdQuery query) {
        return submissionRepository.findById(query.submissionId())
                .map(Submission::getFileUrls)
                .orElse(List.of());
    }
}
