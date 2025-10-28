package apx.inc.assignments_service.assignments.domain.services;


import apx.inc.assignments_service.assignments.domain.model.aggregates.Assignment;
import apx.inc.assignments_service.assignments.domain.model.commands.*;

import java.util.Optional;

public interface AssignmentCommandService {

    Long handle(CreateAssignmentCommand createAssignmentCommand);

    Optional<Assignment> handle(UpdateAssignmentCommand updateAssignmentCommand);

    void handle(DeleteAssignmentCommand deleteAssignmentCommand);


    void handle(AddFilesToAssignmentCommand addFilesToAssignmentCommand);  // Para 1 o múltiples archivos
    void handle(RemoveFileFromAssignmentCommand removeFileFromAssignmentCommand); // Para eliminar 1 archivo

}
