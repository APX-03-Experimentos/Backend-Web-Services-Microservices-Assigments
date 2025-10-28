package apx.inc.assigments_service.assigments.infrastructure.persistence.jpa.repositories;

import apx.inc.assigments_service.assigments.domain.model.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // Additional query methods can be defined here if needed
    List<Submission> findByAssignmentId(Long assignmentId);

    List<Submission> findByStudentId(Long studentId);

    List<Submission> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);


    // NUEVO: Búsqueda por curso (a través del assignment)
    @Query("SELECT s FROM Submission s WHERE s.assignment.courseId = :courseId")
    List<Submission> findByCourseId(@Param("courseId") Long courseId);

    // NUEVO: Búsqueda estudiante + curso
    @Query("SELECT s FROM Submission s WHERE s.studentId = :studentId AND s.assignment.courseId = :courseId")
    List<Submission> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    boolean existsByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
}
