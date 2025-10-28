package apx.inc.assignments_service.assignments.domain.services;

import apx.inc.assignments_service.assignments.domain.model.aggregates.Assignment;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAllAssignmentsQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentByIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetAssignmentsByCourseIdQuery;
import apx.inc.assignments_service.assignments.domain.model.queries.GetFilesByAssignmentIdQuery;

import java.util.List;
import java.util.Optional;

public interface AssignmentQueryService {

    Optional<Assignment> handle(GetAssignmentByIdQuery getAssignmentByIdQuery);

    List<Assignment> handle(GetAllAssignmentsQuery getAllAssignmentsQuery);

    List<Assignment> handle(GetAssignmentsByCourseIdQuery getAssignmentsByGroupIdQuery);

    List<String> handle(GetFilesByAssignmentIdQuery query);
}
