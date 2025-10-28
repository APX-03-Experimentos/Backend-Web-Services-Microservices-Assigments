package apx.inc.assignments_service.assignments.application.internal.queryservices;

import apx.inc.assignments_service.assignments.domain.model.aggregates.Assignment;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAllAssignmentsQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentByIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentsByCourseIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetFilesByAssignmentIdQuery;
import apx.inc.assignments_service.assignments.domain.services.AssignmentQueryService;
import apx.inc.assignments_service.assignments.infrastructure.persistence.jpa.repositories.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentQueryServiceImpl implements AssignmentQueryService {

    final private AssignmentRepository assignmentRepository;

    public AssignmentQueryServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }


    @Override
    public Optional<Assignment> handle(GetAssignmentByIdQuery query) {
        // ✅ SIMPLIFICADO: Solo busca el assignment
        // La autorización se maneja en la capa de aplicación/controladores
        return assignmentRepository.findById(query.assignmentId());
    }

    @Override
    public List<Assignment> handle(GetAllAssignmentsQuery query) {
        return assignmentRepository.findAll();
    }

    @Override
    public List<Assignment> handle(GetAssignmentsByCourseIdQuery query) {
        // ✅ SIMPLIFICADO: Solo busca por courseId
        // La validación de que el usuario tiene acceso al curso se hace externamente
        return assignmentRepository.findByCourseId(query.courseId());
    }

    @Override
    public List<String> handle(GetFilesByAssignmentIdQuery query) {
        return assignmentRepository.findById(query.assignmentId())
                .map(Assignment::getFileUrls)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }
}
