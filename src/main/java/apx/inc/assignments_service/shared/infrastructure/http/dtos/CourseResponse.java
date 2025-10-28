package apx.inc.assignments_service.shared.infrastructure.http.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String title;
    private Long teacherId;
    private Set<Long> studentIds;
}