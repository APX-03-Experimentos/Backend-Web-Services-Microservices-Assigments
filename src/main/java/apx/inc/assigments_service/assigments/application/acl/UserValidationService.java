//NO SE USA
//package apx.inc.assigments_service.assigments.application.acl;

//import apx.inc.assigments_service.assigments.domain.services.IamServiceClient;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserValidationService {
//
//    private final IamServiceClient iamServiceClient;
//
//    public UserValidationService(IamServiceClient iamServiceClient) {
//        this.iamServiceClient = iamServiceClient;
//    }
//
//    public boolean isUserAuthenticated(String authHeader) {
//        try {
//            return iamServiceClient.validateToken(authHeader);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public IamServiceClient.UserInfo getCurrentUserInfo(String authHeader) {
//        try {
//            return iamServiceClient.getUserInfo(authHeader);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
