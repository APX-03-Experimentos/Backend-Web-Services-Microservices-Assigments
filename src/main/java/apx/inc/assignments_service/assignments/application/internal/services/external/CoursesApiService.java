package apx.inc.assignments_service.assignments.application.internal.services.external;

import apx.inc.assignments_service.shared.infrastructure.http.dtos.CourseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CoursesApiService {

    private final RestTemplate restTemplate;

    @Value("${services.courses.url:http://localhost:8082}")
    private String coursesBaseUrl;

    public CoursesApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getCurrentToken() {
        try {
            var requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    return authHeader;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ No se pudo obtener el token del request: " + e.getMessage());
        }
        return null;
    }

    public boolean courseExists(Long courseId) {
        try {
            String token = getCurrentToken();
            if (token == null) return false;

            String url = coursesBaseUrl + "/api/v1/courses/{courseId}";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<CourseResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, CourseResponse.class, courseId
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.out.println("❌ Error validando curso en Courses: " + e.getMessage());
            return false;
        }
    }

    public CourseResponse getCourseById(Long courseId) {
        try {
            String token = getCurrentToken();
            if (token == null) return null;

            String url = coursesBaseUrl + "/api/v1/courses/{courseId}";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<CourseResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, CourseResponse.class, courseId
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo curso de Courses: " + e.getMessage());
            return null;
        }
    }

    public boolean isStudentInCourse(Long studentId, Long courseId) {
        try {
            CourseResponse course = getCourseById(courseId);
            return course != null && course.getStudentIds().contains(studentId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCourseTeacher(Long teacherId, Long courseId) {
        try {
            CourseResponse course = getCourseById(courseId);
            return course != null && course.getTeacherId().equals(teacherId);
        } catch (Exception e) {
            return false;
        }
    }
}