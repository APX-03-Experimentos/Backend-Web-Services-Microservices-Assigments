package apx.inc.assigments_service.assigments.domain.model.commands;

public record DeleteAssignmentCommand(Long assigmentId) {

    public DeleteAssignmentCommand {
        if (assigmentId == null || assigmentId <= 0)
            throw new IllegalArgumentException("assigmentId is required and must be greater than zero!");
    }
}
