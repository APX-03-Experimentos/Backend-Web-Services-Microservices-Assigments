// NO SE USA
//package apx.inc.assigments_service.assigments.domain.services;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//import java.util.List;
//
//@FeignClient(name = "iam-service", url = "localhost:8081")
//public interface IamServiceClient {
//
//    @GetMapping("/api/v1/authentication/validate")
//    boolean validateToken(@RequestHeader("Authorization") String token);
//
//    @GetMapping("/api/v1/users/me")
//    UserInfo getUserInfo(@RequestHeader("Authorization") String token);
//
//    // DTO para la respuesta
//    public static class UserInfo {
//        private Long id;
//        private String userName;
//        private List<String> roles;
//        // getters/setters
//    }
//}
